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
import com.epriest.game.guildfantasy.main.play.GameDialog;
import com.epriest.game.guildfantasy.util.DrawUtil;

/**
 * Created by darka on 2017-04-07.
 */

public class Scene_Party extends Scene {

    private Game_Party gameParty;
    private Context context;

    public Scene_Party(Game_Party gameParty, Scene_Main sceneMain) {
        this.gameParty = gameParty;
    }

    @Override
    public void initScene(ApplicationClass appClass) {
        this.context = appClass.getBaseContext();
    }

    @Override
    public void recycleScene() {
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

        //draw party button
        drawPartyButton(mCanvas);

        //draw card
        drawPartyCard(mCanvas);

        DrawUtil.drawString(mCanvas, "PartyNum = "+gameParty.gameMain.getSelectPartyNum(), 30,
                Color.WHITE, Paint.Align.LEFT, 20,200);

        //파티제한 알림
        if (gameParty.gameMain.showAlertType == GameDialog.ALERT_TYPE_LIMITEDPARTYMEMBER) {
            gameParty.limitedPartyMemberDialog.draw(mCanvas, gameParty.gameMain.img_mainBtn);
        }


        gameParty.gameMain.drawStatusTab(mCanvas);
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
        DrawUtil.drawString(mCanvas, enty.name, 20, Color.WHITE, Paint.Align.CENTER,
                mBtn.drawX + cardW / 2, mBtn.drawY + 7);
        DrawUtil.drawString(mCanvas, "LV." + Integer.toString(enty.status.LEVEL),
                20, Color.DKGRAY, Paint.Align.LEFT, mBtn.drawX + 15, mBtn.drawY + 3);
    }

}
