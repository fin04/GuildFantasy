package com.epriest.game.guildfantasy.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.CanvasGL.util.Scene;
import com.epriest.game.guildfantasy.main.enty.ButtonEnty;
import com.epriest.game.guildfantasy.main.enty.MemberEnty;
import com.epriest.game.guildfantasy.main.play.DataManager;
import com.epriest.game.guildfantasy.util.DrawUtil;

/**
 * Created by darka on 2017-04-07.
 */

public class Scene_Party extends Scene {

    //    private Scene_Main sceneMain;
    private Game_Party gameParty;
    private Context context;

    public Scene_Party(Game_Party gameParty, Scene_Main sceneMain) {
        this.gameParty = gameParty;
//        this.sceneMain = sceneMain;
    }

    @Override
    public void initScene(ApplicationClass appClass) {
        this.context = appClass.getBaseContext();
    }

    @Override
    public void recycleScene() {
//        DrawUtil.recycleBitmap(mos_detail);
        for (int i = 0; i < gameParty.PartyCardList.size(); i++) {
            ButtonEnty mBtn = gameParty.PartyCardList.get(i);
            if (mBtn.name.equals("0")) {
                DrawUtil.recycleBitmap(mBtn.bitmap);
            }
        }
        DrawUtil.recycleBitmap(gameParty.img_title_bg);
        DrawUtil.recycleBitmap(gameParty.img_membercard);
    }

    @Override
    public void drawLoading(Canvas mCanvas, Bitmap loading) {

    }

    @Override
    public void draw(Canvas mCanvas) {
        //draw bg
        DrawUtil.drawBitmap(gameParty.img_title_bg, mCanvas, 0, 0);

        drawStatusTab(mCanvas);

        //draw party button
        drawPartyButton(mCanvas);

        //draw card
        drawPartyCard(mCanvas);

        DrawUtil.drawString(mCanvas, "PartyNum = "+gameParty.gameMain.getSelectPartyNum(), 30, Color.WHITE, Paint.Align.LEFT, 20,200);
    }

    public void drawStatusTab(Canvas mCanvas) {
        int canvasW = gameParty.gameMain.canvasW;
        int statusBarW = gameParty.gameMain.statusBarW;
        int statusBarH = gameParty.gameMain.statusBarH;
        int barNum = canvasW / statusBarW;
        for (int i = 0; i <= barNum; i++) {
            DrawUtil.drawClip(gameParty.gameMain.img_statusBar, mCanvas, 0, 0,
                    statusBarW, statusBarH, statusBarW * i, 0);
        }

        Paint paint = new Paint();
        int fontSize = 30;
        int drawY = (statusBarH-fontSize)/2;
        int drawX = (canvasW - 120) / 5 + 60;
        paint.setColor(Color.argb(255, 50, 50, 50));
        paint.setTextSize(fontSize);

        /*// Name Lv
        DrawUtil.drawString(mCanvas, userEnty.Name + "(Lv " + userEnty.LEVEL + ")", paint, 10, drawY);

        // AP
        DrawUtil.drawString(mCanvas, "AP " + userEnty.AP, paint, drawX, drawY);

        // Gold
        DrawUtil.drawClip(img_mainBtn, mCanvas, goldIcon.clipX, goldIcon.clipY,
                goldIcon.clipW, goldIcon.clipH, goldIcon.drawX, drawY);
        DrawUtil.drawString(mCanvas, Integer.toString(userEnty.GOLD), paint,
                goldIcon.drawX + goldIcon.clipW + 5, drawY + 3);

        // Member
        DrawUtil.drawClip(img_mainBtn, mCanvas, menIcon.clipX, menIcon.clipY,
                menIcon.clipW, menIcon.clipH, menIcon.drawX, drawY);
        DrawUtil.drawString(mCanvas, Integer.toString(userEnty.MEMBERLIST.size()), paint,
                menIcon.drawX + menIcon.clipW + 5, drawY + 3);*/

//        DrawUtil.drawClip(gameHome.img_mainBtn, mCanvas, partyIcon.clipX, partyIcon.clipY,
//                partyIcon.clipW, partyIcon.clipH, partyIcon.drawX, partyIcon.drawY);
//        DrawUtil.drawString(mCanvas, Integer.toString(userEnty.PARTYLIST.size()), paint,
//                partyIcon.drawX + partyIcon.clipW + 5, partyIcon.drawY + 3);

        //Turn
//        DrawUtil.drawString(mCanvas, "Turn " + userEnty.TURN, paint, canvasW - 250, drawY);

        //Option Button
        int clipY = gameParty.okBtn.clipY;
        if (gameParty.okBtn.clickState == ButtonEnty.ButtonClickOn) {
            clipY = 53;
        }
        DrawUtil.drawClip(gameParty.gameMain.img_statusBar, mCanvas, gameParty.okBtn.clipX, clipY,
                gameParty.okBtn.clipW, gameParty.okBtn.clipH, gameParty.okBtn.drawX, gameParty.okBtn.drawY);

        DrawUtil.drawClip(gameParty.gameMain.img_statusBar, mCanvas, 121, 130,
                82, 23, gameParty.okBtn.drawX + (gameParty.okBtn.clipW - 82) / 2,
                gameParty.okBtn.drawY + (gameParty.okBtn.clipH - 23) / 2);

        //Option Button
        clipY = gameParty.backBtn.clipY;
        if (gameParty.backBtn.clickState == ButtonEnty.ButtonClickOn) {
            clipY = 53;
        }
        DrawUtil.drawClip(gameParty.gameMain.img_statusBar, mCanvas, gameParty.backBtn.clipX, clipY,
                gameParty.backBtn.clipW, gameParty.backBtn.clipH, gameParty.backBtn.drawX, gameParty.backBtn.drawY);

        DrawUtil.drawClip(gameParty.gameMain.img_statusBar, mCanvas, 121, 130,
                82, 23, gameParty.backBtn.drawX + (gameParty.backBtn.clipW - 82) / 2,
                gameParty.backBtn.drawY + (gameParty.backBtn.clipH - 23) / 2);

    }

    private void drawPartyButton(Canvas mCanvas) {
        for (ButtonEnty mBtn : gameParty.PartyNumButtonList) {
            int clipX = mBtn.clipX;
            if (mBtn.clickState == ButtonEnty.ButtonClickOn || gameParty.gameMain.getSelectPartyNum() == mBtn.num) {
                clipX += 115;
            }
            DrawUtil.drawClip(gameParty.gameMain.img_mainBtn, mCanvas, clipX, mBtn.clipY,
                    mBtn.clipW, mBtn.clipH, mBtn.drawX, mBtn.drawY);
            DrawUtil.drawClip(gameParty.gameMain.img_statusBar, mCanvas, (mBtn.num + 1) * 16,
                    98, 14, 24,
                    mBtn.drawX + (mBtn.clipW - 15) / 2, mBtn.drawY + (mBtn.clipH - 24) / 2);
        }
    }

    private void drawPartyCard(Canvas mCanvas) {
        int cardY = gameParty.img_title_bg.getHeight() + 300 + 30;
        int btnX = (212 - 47) / 2;
        int btnY = (280 - 47) / 2;
        for (int i = 0; i < gameParty.PartyCardList.size(); i++) {
            ButtonEnty mBtn = gameParty.PartyCardList.get(i);

            DrawUtil.drawClip(gameParty.img_membercard, mCanvas, mBtn.clipX, mBtn.clipY,
                    mBtn.clipW, mBtn.clipH, mBtn.drawX, mBtn.drawY);

            if(mBtn.name.equals("0")) {
                // 빈 card draw
                int clipY = 0;
                if (mBtn.clickState == ButtonEnty.ButtonClickOn) {
                    clipY = 47;
                }
                DrawUtil.drawClip(gameParty.img_membercard, mCanvas, 213, clipY,
                        47, 47, mBtn.drawX + btnX, mBtn.drawY + btnY);
            }else {
                // 멤버가 정해진 card draw
//            if (gameParty.CardImgList.size() > 0 && gameParty.CardImgList.get(i) != null)
                drawMemberCard(mCanvas, mBtn);
            }
        }
    }

    private void drawMemberCard(Canvas mCanvas, ButtonEnty mBtn){//int cardNum, int cx, int cy) {
        int cardW = 212;
        int cardH = 280;
        int cardTextBoxW = 140;
        int cardTextBoxH = 90;
        int cardTextBoxY = cardH - cardTextBoxH - 15;
        //draw Character
        DrawUtil.drawClip(mBtn.bitmap, mCanvas,
                (mBtn.bitmap.getWidth() - mBtn.clipW) / 2, 80,
                mBtn.clipW - 10, mBtn.clipH - 10, mBtn.drawX + 5, mBtn.drawY + 5);
//        DrawUtil.drawBitmap(mBtn.bitmap, mCanvas, mBtn.drawX + 5, mBtn.drawY + 5);
        //draw cardNameBG
        DrawUtil.drawClip(gameParty.img_membercard, mCanvas, 176, 283,
                127, 26, mBtn.drawX + (cardW - 127) / 2, mBtn.drawY + 10);
        //draw cardTextBox
        DrawUtil.drawClip(gameParty.img_membercard, mCanvas, 35, 283,
                cardTextBoxW, cardTextBoxH, mBtn.drawX + 35, mBtn.drawY + cardTextBoxY);

        MemberEnty enty = DataManager.getMemberData(gameParty.gameMain.dbAdapter, mBtn.name);
        DrawUtil.drawString(mCanvas, enty.name, 20, Color.WHITE, Paint.Align.CENTER, mBtn.drawX + cardW / 2, mBtn.drawY + 7);
        DrawUtil.drawString(mCanvas, "LV." + Integer.toString(enty.status.LEVEL),
                20, Color.DKGRAY, Paint.Align.LEFT, mBtn.drawX + 15, mBtn.drawY + 3);
    }

    private void drawButton(Canvas mCanvas, Paint paint) {
//        int startBtnClipX = gameParty.startBtn.clipX;
//        if(gameParty.startBtn.clickState == ButtonEnty.ButtonClickOn){
//            startBtnClipX = 117;
//        }
//        drawClip(gameParty.img_mainBtn, mCanvas,
//                startBtnClipX, gameParty.startBtn.clipY,
//                gameParty.startBtn.clipW, gameParty.startBtn.clipH,
//                gameParty.startBtn.drawX, gameParty.startBtn.drawY);
//
//        int supplyBtnClipX = gameParty.supplyBtn.clipX;
//        if(gameParty.supplyBtn.clickState == ButtonEnty.ButtonClickOn){
//            supplyBtnClipX = 320;
//        }
//        drawClip(gameParty.img_mainBtn, mCanvas,
//                supplyBtnClipX, gameParty.supplyBtn.clipY,
//                gameParty.supplyBtn.clipW, gameParty.supplyBtn.clipH,
//                gameParty.supplyBtn.drawX, gameParty.supplyBtn.drawY);
    }

    private MemberEnty findMemberEnty(String inputId) {
       /* for (String  Id : gameParty.gameMain.userEnty.PARTY_MEMBERID_LIST) {
            if (Id.equals(inputId)) {
                return DataManager.getMemberEntyFromMemberDB(gameParty.gameMain.dbAdapter, Id);
            }
        }*/
        return null;
    }

    /*private void drawPartyGroup(Canvas mCanvas, Paint paint) {

        for(int i = 0 ; i < gameParty.PartyNumButtonList.size() ; i++) {
            ButtonEnty btn = gameParty.PartyNumButtonList.get(i);
            int color = Color.argb(180, 255, 255, 255);
            if(btn.clickState == ButtonEnty.ButtonClickOn)
                color = Color.argb(180, 150, 150, 255);
            DrawUtil.drawBox(mCanvas, color, true, btn.drawX, btn.drawY, btn.clipW, btn.clipH);

            if(gameParty.currentParty.playerIdList.size() > i){
                MemberEnty enty = findMemberEnty(gameParty.currentParty.playerIdList.get(i));

                drawClip(gameParty.viewMember.char_01, mCanvas,
                        (enty.iconid % 10) * gameParty.viewMember.charW, (enty.iconid / 10) * gameParty.viewMember.charH,
                        gameParty.viewMember.charW, gameParty.viewMember.charH,
                        btn.drawX, btn.drawY);

                int strX = btn.drawX+btn.clipW/2;
                int strY = btn.drawY + gameParty.viewMember.charH;
                drawString(mCanvas, "LV "+Integer.toString(enty.status.LEVEL), 22, Color.argb(255, 255, 255, 255), Paint.Align.LEFT, btn.drawX, strY-25);
                drawString(mCanvas, enty.name, 20, Color.argb(255, 30, 20, 20), Paint.Align.CENTER, strX, strY);
                drawString(mCanvas, Integer.toString(enty.status.HP)+"/"+Integer.toString(enty.status.MP), 20,
                        Color.argb(255, 0, 170, 0), Paint.Align.CENTER, strX, strY+ 25);
            }
        }
    }*/

    /*private void drawQuestDetail(Canvas mCanvas, Paint paint) {
        int paperX = 10;
        int paperY = 62;
        DrawUtil.drawBitmap(gameParty.img_questPaper, mCanvas, paperX, paperY);
        int widthCenter = paperX + (gameParty.img_questPaper.getWidth() / 2);
        paperY += 13;
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(35);
//        DrawUtil.drawString(mCanvas, gameParty.gameMain.selectQuestEnty.title, paint, widthCenter, paperY);
        paperY += 38;
//        DrawUtil.drawBitmap(mos_detail, mCanvas, widthCenter - mos_detail.getWidth() / 2, paperY+10);
        paint.setTextSize(22);
        paint.setARGB(200, 255, 10, 10);
//        paperY += mos_detail.getHeight() + 20;
//        DrawUtil.drawString(mCanvas, gameParty.gameMain.selectQuestEnty.tip, paint, widthCenter, paperY);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(24);
        paint.setARGB(200, 40, 30, 10);
//        for(int i =0; i< gameParty.gameMain.selectQuestEnty.textArr.size(); i++){
//            DrawUtil.drawString(mCanvas, gameParty.gameMain.selectQuestEnty.textArr.get(i), paint,
//                    paperX+15, (int) (paperY + i*(paint.getTextSize()+10)));
//        }
    }*/
}
