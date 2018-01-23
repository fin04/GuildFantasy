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

        gameQuest.gameMain.drawStatusTab(mCanvas);
    }

    private void drawQuestInfo(Canvas mCanvas) {
        CanvasUtil.drawClipResize(gameQuest.paper, mCanvas, 0, 0, gameQuest.paper.getWidth(), gameQuest.paper.getHeight(),
                20, gameQuest.gameMain.statusBarH, gameQuest.canvasW-40, gameQuest.canvasH-300);

        int drawY = gameQuest.gameMain.statusBarH;

        CanvasUtil.drawString(mCanvas, gameQuest.questEnty.title, 40, Color.argb(210, 10, 10, 25),
                Paint.Align.CENTER, gameQuest.canvasW/2, drawY + 30);

        CanvasUtil.drawBitmap(gameQuest.questBitmap, mCanvas, (gameQuest.canvasW-gameQuest.questBitmap.getWidth())/2, drawY + 80);

        //text
        for (int j = 0; j < gameQuest.questEnty.textArr.size(); j++) {
            if (j < gameQuest.textLineLimit) {
                CanvasUtil.drawString(mCanvas, gameQuest.questEnty.textArr.get(j), 24, Color.argb(210, 10, 10, 25),
                        Paint.Align.LEFT, gameQuest.questEnty.btnEnty.drawX + 45, drawY + 500 + j * 27);
            } else {
                break;
            }
        }
    }
}
