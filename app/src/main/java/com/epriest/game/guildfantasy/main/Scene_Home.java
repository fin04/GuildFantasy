package com.epriest.game.guildfantasy.main;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.epriest.game.CanvasGL.graphics.CanvasUtil;
import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.CanvasGL.util.Scene;
import com.epriest.game.guildfantasy.util.INN;

/**
 * Created by darka on 2016-10-31.
 */

public class Scene_Home extends Scene {

    private Game_Home gameHome;
    //    private Scene_Main sceneMain;

    private int cursorTileAnimCnt;

    public Scene_Home(Game_Home gameHome, Scene_Main sceneMain) {
        this.gameHome = gameHome;
//        this.sceneMain = sceneMain;
    }

    @Override
    public void initScene(ApplicationClass appClass) {
    }


    @Override
    public void recycleScene() {
        CanvasUtil.recycleBitmap(gameHome.bg);
//        CanvasUtil.recycleBitmap(gameHome.img_char_01);
    }

    @Override
    public void drawLoading(Canvas mCanvas, Bitmap loading) {
        mCanvas.drawBitmap(loading, 0, 0, null);
    }

    @Override
    public void draw(Canvas mCanvas) {
        drawBG(mCanvas);

        gameHome.gameMain.drawMenu(mCanvas);

        if (gameHome.gameMain.showAlertType == INN.ALERT_TYPE_TURNSTART)
            gameHome.gameMain.drawTurnStartAlert(mCanvas);

    }

    private void drawBG(Canvas mCanvas) {
        CanvasUtil.drawBgBitmap(gameHome.bg, mCanvas);
//        CanvasUtil.drawBox(mCanvas, Color.argb(255, 50,50,50),true,
//                0, 0,
//                gameHome.gameMain.appClass.getGameCanvasWidth(), gameHome.mMainScreenY);

//        int barNum = gameHome.gameMain.appClass.getGameCanvasWidth() / gameHome.gameMain.statusBarW;
//        for (int i = 0; i <= barNum; i++) {
//            CanvasUtil.drawClip(gameHome.gameMain.img_statusBar, mCanvas, 0, 0,
//                    gameHome.gameMain.statusBarW, gameHome.gameMain.statusBarH,
//                    gameHome.gameMain.statusBarW * i, gameHome.gameMain.mMenuTabBarY);
//        }
    }

}

