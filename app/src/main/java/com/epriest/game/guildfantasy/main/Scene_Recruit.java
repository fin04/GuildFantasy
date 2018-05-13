package com.epriest.game.guildfantasy.main;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.CanvasGL.util.Scene;
import com.epriest.game.guildfantasy.main.play.GameDialog;
import com.epriest.game.guildfantasy.util.DrawUtil;


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
        DrawUtil.recycleBitmap(gameRecruit.bg);
        DrawUtil.recycleBitmap(gameRecruit.img_value);
        DrawUtil.recycleBitmap(gameRecruit.summonBtn.bitmap);
        DrawUtil.recycleBitmap(gameRecruit.covenantBtn.bitmap);
        DrawUtil.recycleBitmap(gameRecruit.bondageBtn.bitmap);
    }

    @Override
    public void drawLoading(Canvas mCanvas, Bitmap loading) {

    }

    @Override
    public void draw(Canvas mCanvas) {
        drawBG(mCanvas);

        gameRecruit.gameMain.drawStatusTab(mCanvas);
//        gameRecruit.gameMain.drawMenu(mCanvas);

        drawBtn(mCanvas);

//        if (gameRecruit.gameMain.showAlertType == GameDialog.ALERT_TYPE_RECRUIT_SUMMON) {
//            gameRecruit.summonDialog.draw(mCanvas, gameRecruit.gameMain.img_mainBtn);
//        } else if (gameRecruit.gameMain.showAlertType == GameDialog.ALERT_TYPE_RECRUIT_BONDAGE) {
//            gameRecruit.bondageDialog.draw(mCanvas, gameRecruit.gameMain.img_mainBtn);
//        } else if (gameRecruit.gameMain.showAlertType == GameDialog.ALERT_TYPE_RECRUIT_COVENANT) {
//            gameRecruit.covenantDialog.draw(mCanvas, gameRecruit.gameMain.img_mainBtn);
//        }
//        if(gameRecruit.gameMain.showAlertType == GameDialog.ALERT_TYPE_GETNEWMEMBER) {
//            gameRecruit.gameMain.drawMemberAlert(mCanvas, gameRecruit.recruitImg, gameRecruit.recruitEnty);
//        }else if(gameRecruit.gameMain.showAlertType == GameDialog.ALERT_TYPE_EMPTYGOLD) {
//            gameRecruit.gameMain.alertManager.drawAlert(mCanvas, "", "Gold가 없습니다.");
//        }else if(gameRecruit.gameMain.showAlertType == GameDialog.ALERT_TYPE_MAXMEMBER) {
//            gameRecruit.gameMain.alertManager.drawAlert(mCanvas, "", "멤버가 찼습니다.");
//        }else if(gameRecruit.gameMain.showAlertType == GameDialog.ALERT_TYPE_GEMNOTENOUGH){
//            gameRecruit.gameMain.alertManager.drawAlert(mCanvas, "", "보석이 모자랍니다.");
    }

    private void drawBG(Canvas mCanvas) {
        DrawUtil.drawBgBitmap(gameRecruit.bg, mCanvas);
//        DrawUtil.drawBox(mCanvas, Color.argb(255, 50,50,50),true,
//                0, 0,
//                gameHome.gameMain.appClass.getGameCanvasWidth(), gameHome.mMainScreenY);

//        int barNum = gameRecruit.gameMain.appClass.getGameCanvasWidth() / gameRecruit.gameMain.statusBarW;
//        for (int i = 0; i <= barNum; i++) {
//            DrawUtil.drawClip(gameRecruit.gameMain.img_statusBar, mCanvas, 0, 0,
//                    gameRecruit.gameMain.statusBarW, gameRecruit.gameMain.statusBarH,
//                    gameRecruit.gameMain.statusBarW * i, gameRecruit.gameMain.mMenuTabBarY);
//        }
    }

    private void drawBtn(Canvas mCanvas) {
        DrawUtil.drawBitmap(gameRecruit.summonBtn.bitmap, mCanvas, gameRecruit.summonBtn.drawX, gameRecruit.summonBtn.drawY);
        DrawUtil.drawBitmap(gameRecruit.covenantBtn.bitmap, mCanvas, gameRecruit.covenantBtn.drawX, gameRecruit.covenantBtn.drawY);
        DrawUtil.drawBitmap(gameRecruit.bondageBtn.bitmap, mCanvas, gameRecruit.bondageBtn.drawX, gameRecruit.bondageBtn.drawY);
//        DrawUtil.drawClip(gameRecruit.img_recruitBtn, mCanvas, 0,0,
//                gameRecruit.summonBtn.clipW, gameRecruit.summonBtn.clipH,
//                gameRecruit.summonBtn.drawX, gameRecruit.summonBtn.drawY);
//
//        DrawUtil.drawClip(gameRecruit.img_recruitBtn, mCanvas, 0,40,
//                gameRecruit.bondageBtn.clipW, gameRecruit.bondageBtn.clipH,
//                gameRecruit.bondageBtn.drawX, gameRecruit.bondageBtn.drawY);
//
//        DrawUtil.drawClip(gameRecruit.img_recruitBtn, mCanvas, 0,80,
//                gameRecruit.covenantBtn.clipW, gameRecruit.covenantBtn.clipH,
//                gameRecruit.covenantBtn.drawX, gameRecruit.covenantBtn.drawY);
    }
}



