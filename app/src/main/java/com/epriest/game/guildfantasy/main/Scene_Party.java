package com.epriest.game.guildfantasy.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.epriest.game.CanvasGL.graphics.CanvasUtil;
import com.epriest.game.CanvasGL.graphics.GLUtil;
import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.CanvasGL.util.Scene;
import com.epriest.game.guildfantasy.main.enty.ButtonEnty;
import com.epriest.game.guildfantasy.main.enty.MemberEnty;

import static com.epriest.game.CanvasGL.graphics.CanvasUtil.drawClip;
import static com.epriest.game.CanvasGL.graphics.CanvasUtil.drawString;

/**
 * Created by darka on 2017-04-07.
 */

public class Scene_Party extends Scene{

//    private Scene_Main sceneMain;
    private Game_Party gameParty;
    private Context context;
    private int canvasW, canvasH;

    private Bitmap bg;
    private Bitmap questPaper;
    private Bitmap mos_detail;

    public Scene_Party(Game_Party gameParty, Scene_Main sceneMain) {
        this.gameParty = gameParty;
//        this.sceneMain = sceneMain;
    }

    @Override
    public void initScene(ApplicationClass appClass) {
        this.context = appClass.getBaseContext();
        canvasW = appClass.getGameCanvasWidth();
        canvasH = appClass.getGameCanvasHeight();

        bg = GLUtil.loadAssetsBitmap(context, "main/party.jpg", null);

        mos_detail = GLUtil.loadAssetsBitmap(context, "quest/" + gameParty.gameMain.selectQuestEnty.image, null);
        questPaper = GLUtil.loadAssetsBitmap(context, "main/guildpaper.png", null);

        gameParty.viewMember.initScene();

        for (int i = 0; i < gameParty.gameMain.selectQuestEnty.needMember; i++) {
            ButtonEnty btn = new ButtonEnty();
            btn.clipW = 120;
            btn.clipH = 150;
            btn.drawX = questPaper.getWidth()+ 20 + (i%3)*(btn.clipW+10);
            btn.drawY = 70 + (i/3)*(btn.clipH+10);

            gameParty.PartyButtonList.add(btn);
        }

        gameParty.startBtn = new ButtonEnty();
        gameParty.startBtn.clipW = 115;
        gameParty.startBtn.clipH = 115;
        gameParty.startBtn.clipX = 1;
        gameParty.startBtn.clipY = 172;
        gameParty.startBtn.drawX = gameParty.gameMain.appClass.getGameCanvasWidth() - gameParty.startBtn.clipW - 50;
        gameParty.startBtn.drawY = gameParty.gameMain.appClass.getGameCanvasHeight() - gameParty.startBtn.clipH - 10;

        gameParty.supplyBtn = new ButtonEnty();
        gameParty.supplyBtn.clipW = 90;
        gameParty.supplyBtn.clipH = 90;
        gameParty.supplyBtn.clipX = 231;
        gameParty.supplyBtn.clipY = 173;
        gameParty.supplyBtn.drawX = gameParty.startBtn.drawX - gameParty.supplyBtn.clipW -30 ;
        gameParty.supplyBtn.drawY = gameParty.gameMain.appClass.getGameCanvasHeight() - gameParty.supplyBtn.clipH - 10;

        gameParty.backBtn = new ButtonEnty();
        gameParty.backBtn.clipW = 90;
        gameParty.backBtn.clipH = 90;
        gameParty.backBtn.clipX = 231;
        gameParty.backBtn.clipY = 173;
        gameParty.backBtn.drawX = gameParty.supplyBtn.drawX - gameParty.backBtn.clipW -30 ;
        gameParty.backBtn.drawY = gameParty.gameMain.appClass.getGameCanvasHeight() - gameParty.backBtn.clipH - 10;
    }

    @Override
    public void recycleScene() {
        CanvasUtil.recycleBitmap(mos_detail);
        CanvasUtil.recycleBitmap(questPaper);
        gameParty.viewMember.recycleScene();
    }

    @Override
    public void drawLoading(Canvas mCanvas, Bitmap loading) {

    }

    @Override
    public void draw(Canvas mCanvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        CanvasUtil.drawBgBitmap(bg, mCanvas);

        if(gameParty.isViewMember)
            gameParty.viewMember.draw(mCanvas);
        else{
            drawQuestDetail(mCanvas, paint);
            drawPartyGroup(mCanvas, paint);
            drawButton(mCanvas, paint);
        }

        gameParty.gameMain.drawMain(mCanvas, false);
    }

    private void drawButton(Canvas mCanvas, Paint paint) {
        int startBtnClipX = gameParty.startBtn.clipX;
        if(gameParty.startBtn.clickState == ButtonEnty.ButtonClickOn){
            startBtnClipX = 117;
        }
        drawClip(gameParty.gameMain.img_mainBtn, mCanvas,
                startBtnClipX, gameParty.startBtn.clipY,
                gameParty.startBtn.clipW, gameParty.startBtn.clipH,
                gameParty.startBtn.drawX, gameParty.startBtn.drawY);

        int supplyBtnClipX = gameParty.supplyBtn.clipX;
        if(gameParty.supplyBtn.clickState == ButtonEnty.ButtonClickOn){
            supplyBtnClipX = 320;
        }
        drawClip(gameParty.gameMain.img_mainBtn, mCanvas,
                supplyBtnClipX, gameParty.supplyBtn.clipY,
                gameParty.supplyBtn.clipW, gameParty.supplyBtn.clipH,
                gameParty.supplyBtn.drawX, gameParty.supplyBtn.drawY);

        int backBtnClipX = gameParty.backBtn.clipX;
        if(gameParty.backBtn.clickState == ButtonEnty.ButtonClickOn){
            backBtnClipX = 320;
        }
        drawClip(gameParty.gameMain.img_mainBtn, mCanvas,
                backBtnClipX, gameParty.backBtn.clipY,
                gameParty.backBtn.clipW, gameParty.backBtn.clipH,
                gameParty.backBtn.drawX, gameParty.backBtn.drawY);
    }

    private MemberEnty findMemberEnty(String inputId){
        for(MemberEnty enty : gameParty.gameMain.playerEnty.MEMBERLIST){
            if(enty.id.equals(inputId)){
                return enty;
            }
        }
        return null;
    }

    private void drawPartyGroup(Canvas mCanvas, Paint paint) {

        for(int i = 0 ; i < gameParty.PartyButtonList.size() ; i++) {
            ButtonEnty btn = gameParty.PartyButtonList.get(i);
            int color = Color.argb(180, 255, 255, 255);
            if(btn.clickState == ButtonEnty.ButtonClickOn)
                color = Color.argb(180, 150, 150, 255);
            CanvasUtil.drawBox(mCanvas, color, true, btn.drawX, btn.drawY, btn.clipW, btn.clipH);

            if(gameParty.tempParty.playerIdList.size() > i){
                MemberEnty enty = findMemberEnty(gameParty.tempParty.playerIdList.get(i));

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
    }

    private void drawQuestDetail(Canvas mCanvas, Paint paint) {
        int paperX = 10;
        int paperY = 62;
        CanvasUtil.drawBitmap(questPaper, mCanvas, paperX, paperY);
        int widthCenter = paperX + (questPaper.getWidth() / 2);
        paperY += 13;
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(35);
        CanvasUtil.drawString(mCanvas, gameParty.gameMain.selectQuestEnty.title, paint, widthCenter, paperY);
        paperY += 38;
        CanvasUtil.drawBitmap(mos_detail, mCanvas, widthCenter - mos_detail.getWidth() / 2, paperY+10);
        paint.setTextSize(22);
        paint.setARGB(200, 255, 10, 10);
        paperY += mos_detail.getHeight() + 20;
//        CanvasUtil.drawString(mCanvas, gameParty.gameMain.selectQuestEnty.tip, paint, widthCenter, paperY);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(24);
        paint.setARGB(200, 40, 30, 10);
        for(int i =0; i< gameParty.gameMain.selectQuestEnty.textArr.size(); i++){
            CanvasUtil.drawString(mCanvas, gameParty.gameMain.selectQuestEnty.textArr.get(i), paint,
                    paperX+15, (int) (paperY + i*(paint.getTextSize()+10)));
        }
    }
}
