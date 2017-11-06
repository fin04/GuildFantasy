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
import com.epriest.game.guildfantasy.main.play.DataManager;

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

//        gameParty.viewMember.initScene();

//        for (int i = 0; i < gameParty.gameMain.selectQuestEnty.needMember; i++) {
//            ButtonEnty btn = new ButtonEnty();
//            btn.clipW = 120;
//            btn.clipH = 150;
//            btn.drawX = img_questPaper.getWidth()+ 20 + (i%3)*(btn.clipW+10);
//            btn.drawY = 70 + (i/3)*(btn.clipH+10);
//
//            gameParty.PartyButtonList.add(btn);
//        }

//        gameParty.startBtn = new ButtonEnty();
//        gameParty.startBtn.clipW = 115;
//        gameParty.startBtn.clipH = 115;
//        gameParty.startBtn.clipX = 1;
//        gameParty.startBtn.clipY = 172;
//        gameParty.startBtn.drawX = gameParty.gameMain.appClass.getGameCanvasWidth() - gameParty.startBtn.clipW - 50;
//        gameParty.startBtn.drawY = gameParty.gameMain.appClass.getGameCanvasHeight() - gameParty.startBtn.clipH - 10;
//
//        gameParty.supplyBtn = new ButtonEnty();
//        gameParty.supplyBtn.clipW = 90;
//        gameParty.supplyBtn.clipH = 90;
//        gameParty.supplyBtn.clipX = 231;
//        gameParty.supplyBtn.clipY = 173;
//        gameParty.supplyBtn.drawX = gameParty.startBtn.drawX - gameParty.supplyBtn.clipW -30 ;
//        gameParty.supplyBtn.drawY = gameParty.gameMain.appClass.getGameCanvasHeight() - gameParty.supplyBtn.clipH - 10;

//        gameParty.backBtn = new ButtonEnty();
//        gameParty.backBtn.clipW = 90;
//        gameParty.backBtn.clipH = 90;
//        gameParty.backBtn.clipX = 231;
//        gameParty.backBtn.clipY = 173;
//        gameParty.backBtn.drawX = gameParty.supplyBtn.drawX - gameParty.backBtn.clipW -30 ;
//        gameParty.backBtn.drawY = gameParty.gameMain.appClass.getGameCanvasHeight() - gameParty.backBtn.clipH - 10;
    }

    @Override
    public void recycleScene() {
//        CanvasUtil.recycleBitmap(mos_detail);
        for (int i = 0; i < gameParty.CardImgList.size(); i++) {
            if (gameParty.CardImgList.get(i) != null)
                recycleBitmap(gameParty.CardImgList.get(i));
        }
        CanvasUtil.recycleBitmap(gameParty.img_questPaper);
//        gameParty.viewMember.recycleScene();
    }

    @Override
    public void drawLoading(Canvas mCanvas, Bitmap loading) {

    }

    @Override
    public void draw(Canvas mCanvas) {
        //draw bg
        CanvasUtil.drawBitmap(gameParty.img_title_bg, mCanvas, 0, 0);
        CanvasUtil.drawBox(mCanvas, Color.DKGRAY, true, 0, gameParty.img_title_bg.getHeight(), gameParty.gameMain.canvasW, 300);
        CanvasUtil.drawBox(mCanvas, Color.LTGRAY, true, 0, gameParty.img_title_bg.getHeight() + 300, gameParty.gameMain.canvasW, 300);

        //draw menubar
        int barNum = gameParty.gameMain.appClass.getGameCanvasWidth() / gameParty.gameMain.statusBarW;
        for (int i = 0; i <= barNum; i++) {
            CanvasUtil.drawClip(gameParty.img_menuBar, mCanvas, 0, 0, gameParty.gameMain.statusBarW, gameParty.gameMain.statusBarH,
                    gameParty.gameMain.statusBarW * i, gameParty.mMenuTabBarY);
        }

        int clipY = gameParty.backBtn.clipY;
        if (gameParty.backBtn.clickState == ButtonEnty.ButtonClickOn) {
            clipY = 54;
        }

        CanvasUtil.drawClip(gameParty.img_menuBar, mCanvas, gameParty.backBtn.clipX, clipY,
                gameParty.backBtn.clipW, gameParty.backBtn.clipH, gameParty.backBtn.drawX, gameParty.backBtn.drawY);
        CanvasUtil.drawClip(gameParty.img_menuBar, mCanvas, 121, 130,
                82, 23, gameParty.backBtn.drawX + (gameParty.backBtn.clipW - 82) / 2, gameParty.backBtn.drawY + (gameParty.backBtn.clipH - 23) / 2);

        for (ButtonEnty mBtn : gameParty.PartyNumButtonList) {
            clipY = mBtn.clipY;
            if (mBtn.clickState == ButtonEnty.ButtonClickOn) {
                clipY = 54;
            }
            CanvasUtil.drawClip(gameParty.img_menuBar, mCanvas, mBtn.clipX, clipY,
                    mBtn.clipW, mBtn.clipH, mBtn.drawX, mBtn.drawY);
            CanvasUtil.drawClip(gameParty.img_menuBar, mCanvas, (mBtn.num + 1) * 16, 98, 16, 24,
                    mBtn.drawX + (mBtn.clipW - 15) / 2, mBtn.drawY + (mBtn.clipH - 24) / 2);
        }

        //draw card
        CanvasUtil.drawClip(gameParty.img_membercard, mCanvas, 0, 0,
                162, 253, 50, gameParty.img_title_bg.getHeight() + 30);

        int cardY = gameParty.img_title_bg.getHeight() + 300 + 30;
        for (int i = 0; i < gameParty.CardButtonList.size(); i++) {
            ButtonEnty mBtn = gameParty.CardButtonList.get(i);

            CanvasUtil.drawClip(gameParty.img_membercard, mCanvas, mBtn.clipX, mBtn.clipY,
                    mBtn.clipW, mBtn.clipH, mBtn.drawX, mBtn.drawY);

            clipY = 39;
            if (mBtn.clickState == ButtonEnty.ButtonClickOn) {
                clipY += 47;
            }
            CanvasUtil.drawClip(gameParty.img_membercard, mCanvas, 163, clipY,
                    47, 47, mBtn.drawX + (163 - 47) / 2, cardY + (253 - 47 - 25) / 2);

            if (gameParty.CardImgList.size() > 0 &&  gameParty.CardImgList.get(i) != null)
                drawMemberCard(mCanvas, i, mBtn.drawX, mBtn.drawY);
        }


//        if(gameParty.isViewMember)
//            gameParty.viewMember.draw(mCanvas);
//        else{
////            drawQuestDetail(mCanvas, paint);
////            drawPartyGroup(mCanvas, paint);
////            drawButton(mCanvas, paint);
//        }

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

        CanvasUtil.drawString(mCanvas, gameParty.PartyCardList.get(cardNum).name, 20, Color.WHITE, Paint.Align.CENTER, cx + cardW / 2, cy + 7);
        CanvasUtil.drawString(mCanvas, "LV." + Integer.toString(gameParty.PartyCardList.get(cardNum).status.LEVEL),
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

    private void drawQuestDetail(Canvas mCanvas, Paint paint) {
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
    }
}
