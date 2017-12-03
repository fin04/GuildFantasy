package com.epriest.game.guildfantasy.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.epriest.game.CanvasGL.graphics.CanvasUtil;
import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.CanvasGL.util.Scene;
import com.epriest.game.guildfantasy.main.enty.ButtonEnty;
import com.epriest.game.guildfantasy.main.enty.MemberEnty;

import static com.epriest.game.CanvasGL.graphics.CanvasUtil.drawString;
import static com.epriest.game.CanvasGL.graphics.CanvasUtil.recycleBitmap;

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
//        CanvasUtil.recycleBitmap(mos_detail);
        for (int i = 0; i < gameParty.CardImgList.size(); i++) {
            if (gameParty.CardImgList.get(i) != null)
                recycleBitmap(gameParty.CardImgList.get(i));
        }
        CanvasUtil.recycleBitmap(gameParty.img_title_bg);
        CanvasUtil.recycleBitmap(gameParty.img_membercard);
    }

    @Override
    public void drawLoading(Canvas mCanvas, Bitmap loading) {

    }

    @Override
    public void draw(Canvas mCanvas) {
        //draw bg
        CanvasUtil.drawBitmap(gameParty.img_title_bg, mCanvas, 0, 0);

        //draw menubar
        drawMenuBar(mCanvas);

        //draw card
        drawPartyCard(mCanvas);
    }

    private void drawMenuBar(Canvas mCanvas){
        int barNum = gameParty.gameMain.appClass.getGameCanvasWidth() / gameParty.gameMain.statusBarW;
        for (int i = 0; i <= barNum; i++) {
            CanvasUtil.drawClip(gameParty.gameMain.img_menuBar, mCanvas, 0, 0,
                    gameParty.gameMain.statusBarW, gameParty.gameMain.statusBarH,
                    gameParty.gameMain.statusBarW * i, gameParty.mMenuTabBarY);
        }

        int clipY = gameParty.backBtn.clipY;
        if (gameParty.backBtn.clickState == ButtonEnty.ButtonClickOn) {
            clipY = 54;
        }

        CanvasUtil.drawClip(gameParty.gameMain.img_menuBar, mCanvas, gameParty.backBtn.clipX, clipY,
                gameParty.backBtn.clipW, gameParty.backBtn.clipH, gameParty.backBtn.drawX, gameParty.backBtn.drawY);
        CanvasUtil.drawClip(gameParty.gameMain.img_menuBar, mCanvas, 121, 130,
                82, 23, gameParty.backBtn.drawX + (gameParty.backBtn.clipW - 82) / 2,
                gameParty.backBtn.drawY + (gameParty.backBtn.clipH - 23) / 2);

        for (ButtonEnty mBtn : gameParty.PartyNumButtonList) {
            clipY = mBtn.clipY;
            if (mBtn.clickState == ButtonEnty.ButtonClickOn) {
                clipY = 54;
            }
            CanvasUtil.drawClip(gameParty.gameMain.img_menuBar, mCanvas, mBtn.clipX, clipY,
                    mBtn.clipW, mBtn.clipH, mBtn.drawX, mBtn.drawY);
            CanvasUtil.drawClip(gameParty.gameMain.img_menuBar, mCanvas, (mBtn.num + 1) * 16,
                    98, 16, 24,
                    mBtn.drawX + (mBtn.clipW - 15) / 2, mBtn.drawY + (mBtn.clipH - 24) / 2);
        }
    }

    private void drawPartyCard(Canvas mCanvas){
        int cardY = gameParty.img_title_bg.getHeight() + 300 + 30;
        int btnX = (212 - 47) / 2;
        int btnY = (280 - 47) / 2;
        for (int i = 0; i < gameParty.PartyCardList.size(); i++) {
            ButtonEnty mBtn = gameParty.PartyCardList.get(i);

            // 빈 card draw
            CanvasUtil.drawClip(gameParty.img_membercard, mCanvas, mBtn.clipX, mBtn.clipY,
                    mBtn.clipW, mBtn.clipH, mBtn.drawX, mBtn.drawY);

            int clipY = 0;
            if (mBtn.clickState == ButtonEnty.ButtonClickOn) {
                clipY = 47;
            }

            CanvasUtil.drawClip(gameParty.img_membercard, mCanvas, 213, clipY,
                    47, 47, mBtn.drawX + btnX, mBtn.drawY + btnY);

            // 멤버가 정해진 card draw
            if (gameParty.CardImgList.size() > 0 &&  gameParty.CardImgList.get(i) != null)
                drawMemberCard(mCanvas, i, mBtn.drawX, mBtn.drawY);
        }
    }

    private void drawMemberCard(Canvas mCanvas, int cardNum, int cx, int cy) {
        int cardW = 212;
        int cardH = 280;
        int cardTextBoxW = 140;
        int cardTextBoxH = 90;
        int cardTextBoxY = cardH - cardTextBoxH - 15;
        //draw Character
        CanvasUtil.drawClip(gameParty.CardImgList.get(cardNum), mCanvas, (gameParty.CardImgList.get(cardNum).getWidth() - cardW) / 2, 80,
                cardW - 10, cardH - 10, cx + 5, cy + 5);
        //draw cardNameBG
        CanvasUtil.drawClip(gameParty.img_membercard, mCanvas, 176, 283,
                127, 26, cx + (cardW - 127) / 2, cy + 10);
        //draw cardTextBox
        CanvasUtil.drawClip(gameParty.img_membercard, mCanvas, 35, 283,
                cardTextBoxW, cardTextBoxH, cx + 35, cy + cardTextBoxY);

        CanvasUtil.drawString(mCanvas, gameParty.PartyMemberList.get(cardNum).name, 20, Color.WHITE, Paint.Align.CENTER, cx + cardW / 2, cy + 7);
        CanvasUtil.drawString(mCanvas, "LV." + Integer.toString(gameParty.PartyMemberList.get(cardNum).status.LEVEL),
                20, Color.DKGRAY, Paint.Align.LEFT, cx + 15, cy + 3);
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
            CanvasUtil.drawBox(mCanvas, color, true, btn.drawX, btn.drawY, btn.clipW, btn.clipH);

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
        CanvasUtil.drawBitmap(gameParty.img_questPaper, mCanvas, paperX, paperY);
        int widthCenter = paperX + (gameParty.img_questPaper.getWidth() / 2);
        paperY += 13;
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(35);
//        CanvasUtil.drawString(mCanvas, gameParty.gameMain.selectQuestEnty.title, paint, widthCenter, paperY);
        paperY += 38;
//        CanvasUtil.drawBitmap(mos_detail, mCanvas, widthCenter - mos_detail.getWidth() / 2, paperY+10);
        paint.setTextSize(22);
        paint.setARGB(200, 255, 10, 10);
//        paperY += mos_detail.getHeight() + 20;
//        CanvasUtil.drawString(mCanvas, gameParty.gameMain.selectQuestEnty.tip, paint, widthCenter, paperY);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(24);
        paint.setARGB(200, 40, 30, 10);
//        for(int i =0; i< gameParty.gameMain.selectQuestEnty.textArr.size(); i++){
//            CanvasUtil.drawString(mCanvas, gameParty.gameMain.selectQuestEnty.textArr.get(i), paint,
//                    paperX+15, (int) (paperY + i*(paint.getTextSize()+10)));
//        }
    }*/
}
