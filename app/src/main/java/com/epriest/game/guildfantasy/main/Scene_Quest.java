package com.epriest.game.guildfantasy.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.epriest.game.CanvasGL.graphics.CanvasUtil;
import com.epriest.game.CanvasGL.graphics.GLUtil;
import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.CanvasGL.util.ButtonSprite;
import com.epriest.game.CanvasGL.util.Scene;
import com.epriest.game.CanvasGL.util.gameLog;
import com.epriest.game.guildfantasy.enty.ButtonEnty;
import com.epriest.game.guildfantasy.enty.ImageEnty;
import com.epriest.game.guildfantasy.enty.QuestEnty;

/**
 * Created by darka on 2017-03-26.
 */

public class Scene_Quest extends Scene {

    private Context context;
    private Game_Quest gameQuest;
    private Scene_Main sceneMain;
    private int canvasW, canvasH;

    private Bitmap bg;
    private Bitmap char_01;
    private Bitmap mon_01;
    private Bitmap map;



    private ImageEnty managerImg;

    public Scene_Quest(Game_Quest gameQuest, Scene_Main sceneMain) {
        this.gameQuest = gameQuest;
        this.sceneMain = sceneMain;
    }

    @Override
    public void initScene(ApplicationClass appClass) {
        this.context = appClass.getBaseContext();
        canvasW = appClass.getGameCanvasWidth();
        canvasH = appClass.getGameCanvasHeight();

        bg = GLUtil.loadAssetsBitmap(context, "main/map.png", null);
        char_01 = GLUtil.loadAssetsBitmap(context, "main/char_01.jpg", null);
        mon_01 = GLUtil.loadAssetsBitmap(context, "mon/mon0.png", null);
        map = GLUtil.loadAssetsBitmap(context, "map/map_camp0.png", null);

    }

    @Override
    public void recycleScene() {
        CanvasUtil.recycleBitmap(bg);
        CanvasUtil.recycleBitmap(map);
        CanvasUtil.recycleBitmap(char_01);
        CanvasUtil.recycleBitmap(mon_01);
    }

    @Override
    public void drawLoading(Canvas mCanvas, Bitmap loading) {

    }

    @Override
    public void draw(Canvas mCanvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
//        mCanvas.drawColor(Color.BLACK);

//        mCanvas.drawBitmap(bg, 0, 0, null);

        drawQuestMap(mCanvas);

        //manager mode
//        drawManager(mCanvas, paint);

        sceneMain.drawMain(mCanvas, paint, false);
    }

    private void drawQuestMap(Canvas mCanvas) {
        mCanvas.drawBitmap(map, 0, 0, null);

        for (QuestEnty enty : gameQuest.gameMain.playerEnty.QUESTLIST) {
            int iX = (enty.btnEnty.iconImgNum % 4 * enty.btnEnty.w);
            int iY = (enty.btnEnty.iconImgNum / 4 * enty.btnEnty.h);
            CanvasUtil.drawClip(mon_01, mCanvas, null, iX, iY, enty.btnEnty.w, enty.btnEnty.h, enty.btnEnty.x, enty.btnEnty.y);
        }
    }
}
