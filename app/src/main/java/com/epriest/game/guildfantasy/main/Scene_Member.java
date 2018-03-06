package com.epriest.game.guildfantasy.main;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.util.Log;

import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.CanvasGL.util.Scene;
import com.epriest.game.guildfantasy.main.enty.ButtonEnty;
import com.epriest.game.guildfantasy.main.enty.MemberEnty;
import com.epriest.game.guildfantasy.util.DrawUtil;
import com.epriest.game.guildfantasy.util.INN;

/**
 * Created by darka on 2017-03-26.
 */

public class Scene_Member extends Scene {

    private Game_Member gameMember;


    public Scene_Member(Game_Member gameMember, Scene_Main sceneMain) {
        this.gameMember = gameMember;
    }

    @Override
    public void initScene(ApplicationClass appClass) {
    }

    @Override
    public void recycleScene() {
        DrawUtil.recycleBitmap(gameMember.img_bg);
        DrawUtil.recycleBitmap(gameMember.img_cardBg);
        for (int i = 0; i < gameMember.cardImageList.size(); i++) {
            DrawUtil.recycleBitmap(gameMember.cardImageList.get(i).bitmap);
        }
    }

    @Override
    public void drawLoading(Canvas mCanvas, Bitmap loading) {

    }

    @Override
    public void draw(Canvas mCanvas) {
        // 배경 드로우
        DrawUtil.drawBitmap(gameMember.img_bg, mCanvas, 0, 0);

        // 멤버가 0이면 알림 띄워줌
        if (gameMember.cardImageList.size() == 0)
            return;

        int chrImgX = (200 - gameMember.chrImgW) / 2;
        int chrImgY = 30;
        int cardTextBoxY = gameMember.cardH - gameMember.cardTextBoxH - 15;

        int startCardNum = gameMember.curDrawCardRow*gameMember.maxnum_ScreenCardColum;
        for (int i = startCardNum; i < startCardNum+gameMember.maxnum_ScreenCard-gameMember.maxnum_ScreenCardColum; i++) {
            if(i >= gameMember.cardImageList.size())
                break;
            MemberEnty enty = gameMember.gameMain.userEnty.MEMBERLIST.get(i);

//            int cx = gameMember.cardLeftX + i % gameMember.maxnum_ScreenCardColum * (gameMember.cardW + 10);
//            int cy = gameMember.cardTopY + i / gameMember.maxnum_ScreenCardColum * (gameMember.cardH + 30) - gameMember.scrollY;

            int cx = gameMember.cardImageList.get(i).drawX;
            int cy = gameMember.cardImageList.get(i).drawY - gameMember.scrollY;
//            if (cy < gameMember.cardTopY - gameMember.cardH || cy > gameMember.gameMain.canvasH) {
//            } else {
                drawMemberCard(mCanvas, i, enty, cx, cy, chrImgX, chrImgY, cardTextBoxY);
//            }
        }

//        drawPartyButton(mCanvas);
        gameMember.gameMain.drawStatusTab(mCanvas);

        // 확인용 소스, 현재 파티 넘버
        String str = "Party : "+gameMember.gameMain.getSelectPartyNum()+
                ",member:" + gameMember.gameMain.selectCardNum+
                ",scrollY:"+gameMember.scrollY+
                ",curRow:"+gameMember.curDrawCardRow;
        DrawUtil.drawBox(mCanvas, Color.DKGRAY, true, 0, Game_Main.statusBarH, gameMember.gameMain.canvasW, 30);
        DrawUtil.drawString(mCanvas, str, 25,
                Color.argb(255, 255, 255, 100), Paint.Align.LEFT, 50, Game_Main.statusBarH);
    }

    private void drawMemberCard(Canvas mCanvas, int num, MemberEnty enty, int cx, int cy, int chrImgX, int chrImgY,
                                int cardTextBoxY) {
        //draw card
        DrawUtil.drawClip(gameMember.img_cardBg, mCanvas, 0, 0,
                gameMember.cardW, gameMember.cardH, cx, cy);

        //draw 편성해제카드
        if (num == 0 && gameMember.gameMain.appClass.gameState == INN.GAME_MEMBER_FROM_PARTY) {
            ButtonEnty mBtn = gameMember.addButtonList.get(num);
            int clipX = mBtn.clipX;
            if (mBtn.clickState == ButtonEnty.ButtonClickOn) {
                clipX = mBtn.clipX + 85;
            }
            DrawUtil.drawClip(gameMember.img_cardBg, mCanvas, clipX, mBtn.clipY,
                    mBtn.clipW, mBtn.clipH, mBtn.drawX, mBtn.drawY - gameMember.scrollY);
            DrawUtil.drawClip(gameMember.img_cardBg, mCanvas, 170, 309, 85, 64,
                    mBtn.drawX, mBtn.drawY + 10 - gameMember.scrollY);
            return;
        }
        //draw Character
        int imgMemberX = 50;//(gameMember.img_member.get(num).getWidth() - gameMember.cardW) / 2;
        DrawUtil.drawClip(gameMember.cardImageList.get(num).bitmap, mCanvas,
                imgMemberX, 80,
                gameMember.cardW - 10, gameMember.cardH - 10, cx + 5, cy + 5);
        //draw cardNameTag
        int clipSize = 30;
        DrawUtil.drawClip(gameMember.img_cardBg, mCanvas, 175, 280,
                clipSize, clipSize, cx, cy);
        DrawUtil.drawClipResize(gameMember.img_cardBg, mCanvas, 180, 280,
                clipSize, clipSize, cx+clipSize, cy, gameMember.cardW-clipSize*2, clipSize);
        DrawUtil.drawClip(gameMember.img_cardBg, mCanvas, 271, 280,
                clipSize, clipSize, cx+gameMember.cardW-clipSize, cy);
        DrawUtil.drawBox(mCanvas, Color.YELLOW, true, cx+25, cy+25, 55, 90);
        DrawUtil.drawString(mCanvas, "["+num+"]", 30, Color.DKGRAY, Paint.Align.LEFT, cx+30, cy+30);
//        DrawUtil.drawString(mCanvas, "bitmap="+gameMember.cardImageList.get(num).bitmap.toString()., 30, Color.DKGRAY, Paint.Align.LEFT, cx+30, cy+50);
        //draw cardTextBox
//        DrawUtil.drawClip(gameMember.img_cardBg, mCanvas, 35, 283,
//                gameMember.cardTextBoxW, gameMember.cardTextBoxH, cx + 35, cy + cardTextBoxY);

        DrawUtil.drawString(mCanvas, enty.name, 20, Color.WHITE, Paint.Align.CENTER,
                cx + gameMember.cardW / 2, cy + 3);
        DrawUtil.drawString(mCanvas, "LV." + Integer.toString(enty.status.LEVEL), 18,
                Color.YELLOW, Paint.Align.LEFT, cx + 8, cy + 3);
        if(enty.partyNum > -1) {
            DrawUtil.drawClip(gameMember.img_cardBg, mCanvas, 260, 344,
                    25, 25, cx + gameMember.cardW - 30, cy+2);
            DrawUtil.drawString(mCanvas, Integer.toString(enty.partyNum), 20,
                    Color.RED, Paint.Align.CENTER, cx + gameMember.cardW - 18, cy+3);
        }

        //파티추가버튼
        if (gameMember.gameMain.appClass.gameState == INN.GAME_MEMBER_FROM_PARTY) {
            ButtonEnty mBtn = gameMember.addButtonList.get(num);
            int clipY = 0;
            if (mBtn.clickState == ButtonEnty.ButtonClickOn) {
                clipY = 47;
            }
            DrawUtil.drawClip(gameMember.img_cardBg, mCanvas, mBtn.clipX, clipY,
                    mBtn.clipW, mBtn.clipH, mBtn.drawX, mBtn.drawY-gameMember.scrollY);
        }


    }

    private void drawMemberSheet(Canvas mCanvas) {
//        DrawUtil.drawBitmap(gameMember.img_memberSheet, mCanvas, cx, cy);
//        DrawUtil.drawBitmap(gameMember.img_member.get(i), mCanvas, cx+chrImgX, cy+chrImgY);
//
//        DrawUtil.drawString(mCanvas, enty.name, 25, Color.WHITE, Paint.Align.CENTER, cx+gameMember.sheetW/2, cy+7);
//        DrawUtil.drawString(mCanvas, "LV."+Integer.toString(enty.status.LEVEL), 30, Color.YELLOW, Paint.Align.LEFT, cx+15, cy+3);
//
//        DrawUtil.drawString(mCanvas, enty.race, 20, Color.WHITE, Paint.Align.LEFT, cx+215, cy+60);
//        DrawUtil.drawString(mCanvas, enty._class, 20, Color.WHITE, Paint.Align.LEFT, cx+215, cy+100);
//        DrawUtil.drawString(mCanvas, enty.sex, 20, Color.WHITE, Paint.Align.LEFT, cx+215, cy+140);
//        DrawUtil.drawString(mCanvas, "age", 20, Color.WHITE, Paint.Align.LEFT, cx+215, cy+180);
//        DrawUtil.drawString(mCanvas, enty.age, 20, Color.WHITE, Paint.Align.LEFT, cx+295, cy+180);
//        DrawUtil.drawString(mCanvas, "exp", 20, Color.WHITE, Paint.Align.LEFT, cx+215, cy+220);
//        DrawUtil.drawString(mCanvas, Integer.toString(enty.status.EXP), 20, Color.WHITE, Paint.Align.LEFT, cx+295, cy+220);
    }


}