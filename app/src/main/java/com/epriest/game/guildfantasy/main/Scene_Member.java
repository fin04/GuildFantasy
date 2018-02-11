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
        DrawUtil.recycleBitmap(gameMember.img_memberSheet);
        DrawUtil.recycleBitmap(gameMember.img_membercard);
        for (int i = 0; i < gameMember.img_member.size(); i++) {
            DrawUtil.recycleBitmap(gameMember.img_member.get(i));
        }
//        recycleBitmap(classesMark);
//        gameMember.viewMember.recycleScene();
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

        int chrImgX = (200 - gameMember.img_member.get(0).getWidth()) / 2;
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

//        if (gameMember.gameMain.alertManager.showAlertType == AlertManager.ALERT_TYPE_VIEWMEMBER) {
//            gameMember.gameMain.alertManager.drawMemberAlert(mCanvas, gameMember.img_member.get(gameMember.selectMember),
//                    gameMember.memberList.get(gameMember.selectMember));
//        }
    }

    private void drawMemberCard(Canvas mCanvas, int num, int cx, int cy, int chrImgX, int chrImgY,
                                int cardTextBoxY) {
        MemberEnty enty = gameMember.memberList.get(num);

        //draw cardBG
        DrawUtil.drawClip(gameMember.img_membercard, mCanvas, 0, 0,
                gameMember.cardW, gameMember.cardH, cx, cy);
        //draw Character
        DrawUtil.drawClip(gameMember.img_member.get(num), mCanvas,
                (gameMember.img_member.get(num).getWidth() - gameMember.cardW) / 2, 80,
                gameMember.cardW - 10, gameMember.cardH - 10, cx + 5, cy + 5);
        //draw cardNameBG
        DrawUtil.drawClip(gameMember.img_membercard, mCanvas, 176, 283,
                127, 26, cx + (gameMember.cardW - 127) / 2, cy + 10);
        //draw cardTextBox
        DrawUtil.drawClip(gameMember.img_membercard, mCanvas, 35, 283,
                gameMember.cardTextBoxW, gameMember.cardTextBoxH, cx + 35, cy + cardTextBoxY);

        DrawUtil.drawString(mCanvas, enty.name, 20, Color.WHITE, Paint.Align.CENTER,
                cx + gameMember.cardW / 2, cy + 7);
        DrawUtil.drawString(mCanvas, "LV." + Integer.toString(enty.status.LEVEL), 20,
                Color.DKGRAY, Paint.Align.LEFT, cx + 15, cy + 3);

        //편성버튼
        if (gameMember.gameMain.appClass.gameState == INN.GAME_MEMBER_FROM_PARTY) {
            for (int i = 0; i < gameMember.PartyAddButtonList.size(); i++) {
                ButtonEnty mBtn = gameMember.PartyAddButtonList.get(i);
                int clipY = 0;
                if (mBtn.clickState == ButtonEnty.ButtonClickOn) {
                    clipY = 47;
                }
                DrawUtil.drawClip(gameMember.img_membercard, mCanvas, mBtn.clipX, clipY,
                        mBtn.clipW, mBtn.clipH, mBtn.drawX, cy+gameMember.cardH-mBtn.clipH);
            }
        }


//        int cardY = gameMember.img_title_bg.getHeight()+300+30;
//        for (ButtonEnty mBtn : gameMember.CardButtonList) {
//            DrawUtil.drawClip(gameMember.img_membercard, mCanvas, mBtn.clipX, mBtn.clipY,
//                    mBtn.clipW, mBtn.clipH, mBtn.drawX, mBtn.drawY);
//
//            clipY = 39;
//            if (mBtn.clickState == ButtonEnty.ButtonClickOn) {
//                clipY += 47;
//            }
//            DrawUtil.drawClip(gameMember.img_membercard, mCanvas, 163, clipY,
//                    47, 47, mBtn.drawX+(163-47)/2,  cardY+(253-47-25)/2);
//        }
    }

    private void drawPartyButton(Canvas mCanvas) {
//        for (ButtonEnty mBtn : gameMember.PartyButtonList) {
//            int clipY = mBtn.clipY;
//            if (mBtn.clickState == ButtonEnty.ButtonClickOn || gameMember.gameMain.getSelectPartyNum() == mBtn.num) {
//                clipY += 84;
//            }
//            DrawUtil.drawClip(gameMember.gameMain.img_mainBtn, mCanvas, mBtn.clipX, clipY,
//                    mBtn.clipW, mBtn.clipH, mBtn.drawX, mBtn.drawY);
////            DrawUtil.drawClip(gameMember.gameMain.img_statusBar, mCanvas, (mBtn.num + 1) * 16,
////                    98, 14, 24,
////                    mBtn.drawX + (mBtn.clipW - 15) / 2, mBtn.drawY + (mBtn.clipH - 24) / 2);
//        }
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