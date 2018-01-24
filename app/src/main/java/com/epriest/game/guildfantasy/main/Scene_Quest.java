package com.epriest.game.guildfantasy.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.epriest.game.CanvasGL.graphics.CanvasUtil;
import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.CanvasGL.util.Scene;

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
        CanvasUtil.recycleBitmap(gameQuest.bg);
        CanvasUtil.recycleBitmap(gameQuest.paper);
        CanvasUtil.recycleBitmap(gameQuest.questBitmap);
    }

    @Override
    public void drawLoading(Canvas mCanvas, Bitmap loading) {

    }

    @Override
    public void draw(Canvas mCanvas) {
        CanvasUtil.drawBgBitmap(gameQuest.bg, mCanvas);
        drawQuestInfo(mCanvas);
        drawParty(mCanvas);

        gameQuest.gameMain.drawStatusTab(mCanvas);
    }

    private void drawQuestInfo(Canvas mCanvas) {
        int drawX = 75;
        int drawY = gameQuest.gameMain.statusBarH;

        // quest paper
        CanvasUtil.drawClipResize(gameQuest.paper, mCanvas, 0, 0,
                gameQuest.paper.getWidth(), gameQuest.paper.getHeight(),
                drawX, drawY, gameQuest.canvasW-150, gameQuest.canvasH-550);

        // quest image
        CanvasUtil.drawBitmap(gameQuest.questBitmap, mCanvas,
                (gameQuest.canvasW-gameQuest.questBitmap.getWidth())/2, drawY + 70);

        // title
        CanvasUtil.drawString(mCanvas, gameQuest.questEnty.title, 40,
                Color.argb(235, 140, 20, 25),
                Paint.Align.CENTER, gameQuest.canvasW/2, drawY + 480);

        //text
        for (int j = 0; j < gameQuest.questEnty.textArr.size(); j++) {
            if (j < gameQuest.textLineLimit) {
                CanvasUtil.drawString(mCanvas, gameQuest.questEnty.textArr.get(j), 25, Color.argb(235, 40, 20, 25),
                        Paint.Align.LEFT, drawX + 40, drawY + 540 + j * 30);
            } else {
                break;
            }
        }

        //quest difficult stemp
//        for (int j = 0; j < gameQuest.questEnty.difficult; j++) {
//            CanvasUtil.drawClip(gameQuest.questcard, mCanvas, 100, 250,
//                    50, 50, drawX + j * 35, drawY + 660);
//        }
    }

    private void drawParty(Canvas mCanvas){

    }
}
