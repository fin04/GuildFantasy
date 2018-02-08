package com.epriest.game.guildfantasy.main;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.epriest.game.CanvasGL.graphics.GLUtil;
import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.CanvasGL.util.Scene;
import com.epriest.game.guildfantasy.main.enty.ImageEnty;
import com.epriest.game.guildfantasy.util.DrawUtil;

/**
 * Created by darka on 2017-03-26.
 */

public class Scene_Temple extends Scene {

    private Game_Temple gameTown;
    private Scene_Main sceneMain;
    private int canvasW, canvasH;

    private Bitmap bg;
    private Bitmap char_01;
//    private Bitmap menu_icon;

    private ImageEnty managerImg;

    public Scene_Temple(Game_Temple gameTown, Scene_Main sceneMain) {
        this.gameTown = gameTown;
        this.sceneMain = sceneMain;
    }

    @Override
    public void initScene(ApplicationClass appClass) {
        canvasW = appClass.getGameCanvasWidth();
        canvasH = appClass.getGameCanvasHeight();

        bg = GLUtil.loadAssetsBitmap(appClass, "main/town.jpg", null);
        char_01 = GLUtil.loadAssetsBitmap(appClass, "main/char_01.jpg", null);
    }

    @Override
    public void recycleScene() {
        DrawUtil.recycleBitmap(bg);
        DrawUtil.recycleBitmap(char_01);
    }

    @Override
    public void drawLoading(Canvas mCanvas, Bitmap loading) {

    }

    @Override
    public void draw(Canvas mCanvas) {
        DrawUtil.drawBgBitmap(bg, mCanvas);

        //manager mode
//        drawManager(mCanvas, paint);

        gameTown.gameMain.drawStatusTab(mCanvas);
    }
}
