package com.epriest.game.guildfantasy.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.CanvasGL.util.Scene;
import com.epriest.game.guildfantasy.main.enty.ButtonEnty;
import com.epriest.game.guildfantasy.util.DrawUtil;

/**
 * Created by darka on 2018-01-23.
 */

public class Scene_Quest extends Scene {
    private Context context;
    private Game_Quest gameQuest;

    public Scene_Quest(Game_Quest gameQuest, Scene_Main sceneMain) {
        this.gameQuest = gameQuest;
    }

    @Override
    public void initScene(ApplicationClass appClass) {
        this.context = appClass.getBaseContext();
    }

    @Override
    public void recycleScene() {
        DrawUtil.recycleBitmap(gameQuest.img_bg);
        DrawUtil.recycleBitmap(gameQuest.img_paper);
        DrawUtil.recycleBitmap(gameQuest.img_questBitmap);
    }

    @Override
    public void drawLoading(Canvas mCanvas, Bitmap loading) {

    }

    @Override
    public void draw(Canvas mCanvas) {
        DrawUtil.drawBgBitmap(gameQuest.img_bg, mCanvas);
        drawQuestInfo(mCanvas);
        drawParty(mCanvas);

        ButtonEnty btnEnty = gameQuest.nextBtnEnty;
        int clipY = btnEnty.clipY;
        if (gameQuest.nextBtnEnty.clickState == ButtonEnty.ButtonClickOn) {
            clipY = btnEnty.clipY + btnEnty.clipH;
        }
        DrawUtil.drawClip(gameQuest.gameMain.img_defBtn, mCanvas, btnEnty.clipX, clipY,
                btnEnty.clipW, btnEnty.clipH, btnEnty.drawX, btnEnty.drawY);

        DrawUtil.drawString(mCanvas, btnEnty.name, 25, Color.rgb(250, 250, 250),
                Paint.Align.CENTER, btnEnty.drawX + btnEnty.clipW / 2, btnEnty.drawY + 15);

        gameQuest.gameMain.drawStatusTab(mCanvas);
    }

    private void drawQuestInfo(Canvas mCanvas) {
        int drawX = 75;
        int drawY = gameQuest.gameMain.statusBarH + 100;

        // quest paper
        DrawUtil.drawClipResize(gameQuest.img_paper, mCanvas, 0, 0,
                gameQuest.img_paper.getWidth(), gameQuest.img_paper.getHeight(),
                drawX, drawY, gameQuest.canvasW - 150, gameQuest.canvasH - 550);

        // title
        com.epriest.game.guildfantasy.util.DrawUtil.drawBoldString(mCanvas, gameQuest.questEnty.title, 30,
                Color.argb(235, 40, 20, 180),
                Paint.Align.CENTER, gameQuest.canvasW / 2, drawY + 50);

        // quest image
        DrawUtil.drawBitmap(gameQuest.img_questBitmap, mCanvas,
                (gameQuest.canvasW - gameQuest.img_questBitmap.getWidth()) / 2, drawY + 100);

        // type
        com.epriest.game.guildfantasy.util.DrawUtil.drawBoldString(mCanvas, Game_Quest.QuestType.valueOf(gameQuest.questEnty.type).getLabel(),
                25, Color.argb(235, 140, 20, 25),
                Paint.Align.CENTER, gameQuest.canvasW / 2, drawY + 500);

        //text
        for (int j = 0; j < gameQuest.questEnty.textArr.size(); j++) {
            if (j < gameQuest.textLineLimit) {
                DrawUtil.drawString(mCanvas, gameQuest.questEnty.textArr.get(j), 24,
                        Color.argb(235, 40, 20, 25),
                        Paint.Align.LEFT, drawX + 40, drawY + 540 + j * 30);
            } else {
                break;
            }
        }

        //quest difficult stemp
//        for (int j = 0; j < gameQuest.questEnty.difficult; j++) {
//            DrawUtil.drawClip(gameQuest.questcard, mCanvas, 100, 250,
//                    50, 50, drawX + j * 35, drawY + 660);
//        }
    }

    private void drawParty(Canvas mCanvas) {
        for (ButtonEnty enty : gameQuest.partyEntyList) {
            DrawUtil.drawClip(gameQuest.img_membercard, mCanvas, enty.clipX, enty.clipY,
                    enty.clipW, enty.clipH, enty.drawX, enty.drawY);
            if(enty.name != null) {
                DrawUtil.drawClip(enty.bitmap, mCanvas,
                        (enty.bitmap.getWidth() - enty.clipW) / 2, 20,
                        enty.clipW - 10, enty.clipH - 10, enty.drawX + 5, enty.drawY + 5);
            }
        }

    }
}
