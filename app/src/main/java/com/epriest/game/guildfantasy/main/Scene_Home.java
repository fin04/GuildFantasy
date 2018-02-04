package com.epriest.game.guildfantasy.main;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.epriest.game.CanvasGL.graphics.CanvasUtil;
import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.CanvasGL.util.Scene;
import com.epriest.game.guildfantasy.main.enty.ButtonEnty;
import com.epriest.game.guildfantasy.main.play.AlertManager;
import com.epriest.game.guildfantasy.main.play.GameDialog;
import com.epriest.game.guildfantasy.util.DrawUtil;
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
        CanvasUtil.recycleBitmap(gameHome.img_homeBtn);
    }

    @Override
    public void drawLoading(Canvas mCanvas, Bitmap loading) {
        mCanvas.drawBitmap(loading, 0, 0, null);
    }

    @Override
    public void draw(Canvas mCanvas) {
        drawBG(mCanvas);
        gameHome.gameMain.drawStatusTab(mCanvas);
        drawMenuButton(mCanvas);

        if (gameHome.gameMain.showAlertType == GameDialog.ALERT_TYPE_CURRENT_TURNEND) {
            gameHome.turnEndDialog.draw(mCanvas, gameHome.gameMain.img_mainBtn);
        }else
        if (gameHome.gameMain.showAlertType == GameDialog.ALERT_TYPE_NEXT_TURNSTART) {
            gameHome.turnStartDialog.draw(mCanvas, gameHome.gameMain.img_mainBtn);
        }


    }

    private void drawBG(Canvas mCanvas) {
        DrawUtil.drawBgBitmap(gameHome.bg, mCanvas, gameHome.scrollX);
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

    private void drawMenuButton(Canvas mCanvas) {
//        int btnArea = (canvasH - statusBarH)/ gameMain.menuButtonList.size();

        CanvasUtil.drawClip(gameHome.img_homeBtn, mCanvas,
                gameHome.turnButton.clipX, gameHome.turnButton.clipY,
                gameHome.turnButton.clipW, gameHome.turnButton.clipH,
                gameHome.turnButton.drawX, gameHome.turnButton.drawY);
        CanvasUtil.drawString(mCanvas, gameHome.turnButton.name, 50, Color.WHITE, Paint.Align.CENTER,
                gameHome.turnButton.drawX+gameHome.turnButton.clipW/2,gameHome.turnButton.drawY+30);

        for (ButtonEnty mBtn : gameHome.menuButtonList) {
            int clipX = mBtn.clipX;
            int clipY = mBtn.clipY;
            if (mBtn.clickState == ButtonEnty.ButtonClickOn) {
                if (mBtn.num == gameHome.menuButtonList.size() - 1)
                    clipX += mBtn.clipW;
                else
                    clipY += mBtn.clipH;
            }
            //Button Image
            CanvasUtil.drawClip(gameHome.img_homeBtn, mCanvas, clipX, clipY,
                    mBtn.clipW, mBtn.clipH, mBtn.drawX, mBtn.drawY);

            int btnNameX = mBtn.drawX + (mBtn.clipW - 180) / 2;
            int btnNameY = mBtn.drawY + (mBtn.clipH - 50) / 2;
            //Button Title
            CanvasUtil.drawClip(gameHome.img_homeBtn, mCanvas, 220, mBtn.iconImgNum * 50,
                    180, 50, btnNameX, btnNameY);
            btnNameX = mBtn.drawX + (mBtn.clipW - 130);
            btnNameY = mBtn.drawY;
            //Button Subtitle
            CanvasUtil.drawClip(gameHome.img_homeBtn, mCanvas, 400, mBtn.iconImgNum * 50,
                    130, 50, btnNameX, btnNameY);
            //Button Subtitle HanGul
//            CanvasUtil.drawClip(gameHome.img_homeBtn, mCanvas, 531, mBtn.iconImgNum*50,
//                    130, 50, btnNameX, btnNameY);
//            CanvasUtil.drawClip(menu_icon, mCanvas, null, (iconNum%5)*mBtn.w, (iconNum/5)*mBtn.h,
//                    mBtn.w, mBtn.h, mBtn.x+(btnArea-mBtn.w)/2, mBtn.y);
        }
    }

}

