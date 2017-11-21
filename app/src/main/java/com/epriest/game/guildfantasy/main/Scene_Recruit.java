package com.epriest.game.guildfantasy.main;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.epriest.game.CanvasGL.graphics.CanvasUtil;
import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.CanvasGL.util.Scene;


/**
 * Created by darka on 2017-11-06.
 */

public class Scene_Recruit extends Scene {

    private Game_Recruit gameRecruit;

    public Scene_Recruit(Game_Recruit gameRecruit, Scene_Main sceneMain) {
        super();
        this.gameRecruit = gameRecruit;
    }

    @Override
    public void initScene(ApplicationClass appClass) {

    }

    @Override
    public void recycleScene() {
        CanvasUtil.recycleBitmap(gameRecruit.bg);
        CanvasUtil.recycleBitmap(gameRecruit.img_recruitBtn);
    }

    @Override
    public void drawLoading(Canvas mCanvas, Bitmap loading) {

    }

    @Override
    public void draw(Canvas mCanvas) {
        drawBG(mCanvas);

        gameRecruit.gameMain.drawMenu(mCanvas);

        drawBtn(mCanvas);
    }

    private void drawBG(Canvas mCanvas) {
        CanvasUtil.drawBgBitmap(gameRecruit.bg, mCanvas);
//        CanvasUtil.drawBox(mCanvas, Color.argb(255, 50,50,50),true,
//                0, 0,
//                gameHome.gameMain.appClass.getGameCanvasWidth(), gameHome.mMainScreenY);

        int barNum = gameRecruit.gameMain.appClass.getGameCanvasWidth() / gameRecruit.gameMain.statusBarW;
        for (int i = 0; i <= barNum; i++) {
            CanvasUtil.drawClip(gameRecruit.gameMain.img_menuBar, mCanvas, 0, 0,
                    gameRecruit.gameMain.statusBarW, gameRecruit.gameMain.statusBarH,
                    gameRecruit.gameMain.statusBarW * i, gameRecruit.gameMain.mMenuTabBarY);
        }
    }

    private void drawBtn(Canvas mCanvas) {
        CanvasUtil.drawClip(gameRecruit.img_recruitBtn, mCanvas, 0,0,
                gameRecruit.summonBtn.clipW, gameRecruit.summonBtn.clipH,
                gameRecruit.summonBtn.drawX, gameRecruit.summonBtn.drawY);

        CanvasUtil.drawClip(gameRecruit.img_recruitBtn, mCanvas, 0,40,
                gameRecruit.bondageBtn.clipW, gameRecruit.bondageBtn.clipH,
                gameRecruit.bondageBtn.drawX, gameRecruit.bondageBtn.drawY);

        CanvasUtil.drawClip(gameRecruit.img_recruitBtn, mCanvas, 0,80,
                gameRecruit.covenantBtn.clipW, gameRecruit.covenantBtn.clipH,
                gameRecruit.covenantBtn.drawX, gameRecruit.covenantBtn.drawY);
    }
}

