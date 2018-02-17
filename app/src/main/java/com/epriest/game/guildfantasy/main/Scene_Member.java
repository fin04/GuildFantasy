package com.epriest.game.guildfantasy.main;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

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
        DrawUtil.recycleBitmap(gameMember.img_membercard);
        for (int i = 0; i < gameMember.img_member.size(); i++) {
            DrawUtil.recycleBitmap(gameMember.img_member.get(i));
        }
    }

    @Override
    public void drawLoading(Canvas mCanvas, Bitmap loading) {

    }

    @Override
    public void draw(Canvas mCanvas) {
        DrawUtil.drawBitmap(gameMember.img_bg, mCanvas, 0, 0);

        if (gameMember.img_member.size() == 0)
            return;

        int partyNum = gameMember.gameMain.getSelectPartyNum();
        DrawUtil.drawString(mCanvas, Integer.toString(partyNum), 20,
                Color.argb(255, 255, 255, 255), Paint.Align.LEFT, 200, 50);

        int chrImgX = (200 - gameMember.img_member.get(gameMember.img_member.size()-1).getWidth()) / 2;
        int chrImgY = 30;// + (180 - gameMember.img_member.get(0).getHeight()) / 2;
        int cardTextBoxY = gameMember.cardH - gameMember.cardTextBoxH - 15;

        for (int i = 0; i < gameMember.memberList.size(); i++) {
            int cx = gameMember.cardLeftX + i % gameMember.cardRowNum * (gameMember.cardW + 10);
            int cy = gameMember.cardTopY + i / gameMember.cardRowNum * (gameMember.cardH + 30) - gameMember.scrollY;

            if (cy < gameMember.cardTopY - gameMember.cardH || cy > gameMember.gameMain.canvasH) {
            } else {
                drawMemberCard(mCanvas, i, cx, cy, chrImgX, chrImgY, cardTextBoxY);
            }
        }

        DrawUtil.drawString(mCanvas, "" + gameMember.gameMain.selectCardNum, 30,
                Color.YELLOW, Paint.Align.LEFT, 300, 120);

//        drawPartyButton(mCanvas);
        gameMember.gameMain.drawStatusTab(mCanvas);
    }

    private void drawMemberCard(Canvas mCanvas, int num, int cx, int cy, int chrImgX, int chrImgY,
                                int cardTextBoxY) {
        MemberEnty enty = gameMember.memberList.get(num);

        //draw card
        DrawUtil.drawClip(gameMember.img_membercard, mCanvas, 0, 0,
                gameMember.cardW, gameMember.cardH, cx, cy);

        //draw 편성해제카드
        if (num == 0 && gameMember.gameMain.appClass.gameState == INN.GAME_MEMBER_FROM_PARTY) {
            ButtonEnty mBtn = gameMember.AddPartyButtonList.get(num);
            int clipX = mBtn.clipX;
            if (mBtn.clickState == ButtonEnty.ButtonClickOn) {
                clipX = mBtn.clipX + 85;
            }
            DrawUtil.drawClip(gameMember.img_membercard, mCanvas, clipX, mBtn.clipY,
                    mBtn.clipW, mBtn.clipH, mBtn.drawX, mBtn.drawY - gameMember.scrollY);
            DrawUtil.drawClip(gameMember.img_membercard, mCanvas, 170, 309, 85, 64,
                    mBtn.drawX, mBtn.drawY + 10 - gameMember.scrollY);
            return;
        }
        //draw Character
        DrawUtil.drawClip(gameMember.img_member.get(num), mCanvas,
                (gameMember.img_member.get(num).getWidth() - gameMember.cardW) / 2, 80,
                gameMember.cardW - 10, gameMember.cardH - 10, cx + 5, cy + 5);
        //draw cardNameTag
        int clipSize = 30;
        DrawUtil.drawClip(gameMember.img_membercard, mCanvas, 175, 280,
                clipSize, clipSize, cx, cy);
        DrawUtil.drawClipResize(gameMember.img_membercard, mCanvas, 180, 280,
                clipSize, clipSize, cx+clipSize, cy, gameMember.cardW-clipSize*2, clipSize);
        DrawUtil.drawClip(gameMember.img_membercard, mCanvas, 271, 280,
                clipSize, clipSize, cx+gameMember.cardW-clipSize, cy);
        //draw cardTextBox
//        DrawUtil.drawClip(gameMember.img_membercard, mCanvas, 35, 283,
//                gameMember.cardTextBoxW, gameMember.cardTextBoxH, cx + 35, cy + cardTextBoxY);

        DrawUtil.drawString(mCanvas, enty.name, 20, Color.WHITE, Paint.Align.CENTER,
                cx + gameMember.cardW / 2, cy + 3);
        DrawUtil.drawString(mCanvas, "LV." + Integer.toString(enty.status.LEVEL), 18,
                Color.YELLOW, Paint.Align.LEFT, cx + 8, cy + 3);
        if(enty.partyNum > -1) {
            DrawUtil.drawClip(gameMember.img_membercard, mCanvas, 260, 344,
                    25, 25, cx + gameMember.cardW - 30, cy+2);
            DrawUtil.drawString(mCanvas, Integer.toString(enty.partyNum), 20,
                    Color.RED, Paint.Align.CENTER, cx + gameMember.cardW - 18, cy+3);
        }

        //파티추가버튼
        if (gameMember.gameMain.appClass.gameState == INN.GAME_MEMBER_FROM_PARTY) {
            ButtonEnty mBtn = gameMember.AddPartyButtonList.get(num);
            int clipY = 0;
            if (mBtn.clickState == ButtonEnty.ButtonClickOn) {
                clipY = 47;
            }
            DrawUtil.drawClip(gameMember.img_membercard, mCanvas, mBtn.clipX, clipY,
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