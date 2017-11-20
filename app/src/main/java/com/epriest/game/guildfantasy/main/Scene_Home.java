package com.epriest.game.guildfantasy.main;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.epriest.game.CanvasGL.graphics.CanvasUtil;
import com.epriest.game.CanvasGL.graphics.GLUtil;
import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.CanvasGL.util.Scene;
import com.epriest.game.guildfantasy.main.enty.ButtonEnty;
import com.epriest.game.guildfantasy.main.enty.ClipImageEnty;
import com.epriest.game.guildfantasy.main.enty.ImageEnty;
import com.epriest.game.guildfantasy.main.enty.UserEnty;
import com.epriest.game.guildfantasy.util.INN;

import java.util.ArrayList;

import static com.epriest.game.CanvasGL.graphics.CanvasUtil.drawClip;

/**
 * Created by darka on 2016-10-31.
 */

public class Scene_Home extends Scene {

    private Game_Home gameHome;
    //    private Scene_Main sceneMain;


    private ImageEnty managerImg;

    private int cursorTileAnimCnt;

    public Scene_Home(Game_Home gameHome, Scene_Main sceneMain) {
        this.gameHome = gameHome;
//        this.sceneMain = sceneMain;
    }

    @Override
    public void initScene(ApplicationClass appClass) {
//        canvasW = appClass.getGameCanvasWidth();
//        canvasH = appClass.getGameCanvasHeight();


        managerImg = new ImageEnty();
        managerImg.bitmap = GLUtil.loadAssetsBitmap(appClass, "main/manager_0.png", null);
        managerImg.animFrame = new ArrayList<>();
        managerImg.animMaxFrame = 100;

        managerImg.animClipList = new ArrayList<>();
        ClipImageEnty clipEnty1 = new ClipImageEnty();
        clipEnty1.animClipX = 758;
        clipEnty1.animClipY = 302;
        clipEnty1.animClipW = 96;
        clipEnty1.animClipH = 28;
        clipEnty1.animStartFrame = 85;
        clipEnty1.animEndFrame = 88;
        clipEnty1.animDrawX = 551;
        clipEnty1.animDrawY = 253;
        managerImg.animClipList.add(clipEnty1);
        ClipImageEnty clipEnty2 = new ClipImageEnty();
        clipEnty2.animClipX = 758;
        clipEnty2.animClipY = 266;
        clipEnty2.animClipW = 96;
        clipEnty2.animClipH = 28;
        clipEnty2.animStartFrame = 89;
        clipEnty2.animEndFrame = 95;
        clipEnty2.animDrawX = 551;
        clipEnty2.animDrawY = 253;
        managerImg.animClipList.add(clipEnty2);
        ClipImageEnty clipEnty3 = new ClipImageEnty();
        clipEnty3.animClipX = 758;
        clipEnty3.animClipY = 302;
        clipEnty3.animClipW = 96;
        clipEnty3.animClipH = 28;
        clipEnty3.animStartFrame = 96;
        clipEnty3.animEndFrame = 100;
        clipEnty3.animDrawX = 551;
        clipEnty3.animDrawY = 253;
        managerImg.animClipList.add(clipEnty3);

//        gameMain.menuButtonList = menuTab.setMenuIcon(canvasW, canvasH);
    }


    @Override
    public void recycleScene() {
        CanvasUtil.recycleBitmap(gameHome.bg);
        CanvasUtil.recycleBitmap(gameHome.img_char_01);
        CanvasUtil.recycleBitmap(gameHome.img_mainBtn);
        CanvasUtil.recycleBitmap(gameHome.img_homeBtn);
        CanvasUtil.recycleBitmap(gameHome.img_menuBar);
        CanvasUtil.recycleBitmap(gameHome.alertBox);

        CanvasUtil.recycleBitmap(managerImg.bitmap);
    }

    @Override
    public void drawLoading(Canvas mCanvas, Bitmap loading) {
        mCanvas.drawBitmap(loading, 0, 0, null);
    }

    @Override
    public void draw(Canvas mCanvas) {

        drawBG(mCanvas);
        //manager mode
//        drawManager(mCanvas);

//        if (viewMenuButton) {
        drawMenuButton(mCanvas);
//        }

        if (gameHome.gameMain.appClass.gameState == INN.GAME_HOME && gameHome.gameMain.userEnty.isStartTurnAlert)
            drawTurnStartAlert(mCanvas);

        drawStatusTab(mCanvas);
    }

    private void drawBG(Canvas mCanvas) {
        CanvasUtil.drawBgBitmap(gameHome.bg, mCanvas);
//        CanvasUtil.drawBox(mCanvas, Color.argb(255, 50,50,50),true,
//                0, 0,
//                gameHome.gameMain.appClass.getGameCanvasWidth(), gameHome.mMainScreenY);

        int barNum = gameHome.gameMain.appClass.getGameCanvasWidth() / gameHome.gameMain.statusBarW;
        for (int i = 0; i <= barNum; i++) {
            CanvasUtil.drawClip(gameHome.img_menuBar, mCanvas, 0, 0, gameHome.gameMain.statusBarW, gameHome.gameMain.statusBarH,
                    gameHome.gameMain.statusBarW * i, gameHome.mMenuTabBarY);
        }
    }

    public void drawMenuButton(Canvas mCanvas) {
//        int btnArea = (canvasH - statusBarH)/ gameMain.menuButtonList.size();
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

        int clipY = gameHome.optionBtn.clipY;
        if (gameHome.optionBtn.clickState == ButtonEnty.ButtonClickOn) {
            clipY = 54;
        }
        CanvasUtil.drawClip(gameHome.img_menuBar, mCanvas, gameHome.optionBtn.clipX, clipY,
                gameHome.optionBtn.clipW, gameHome.optionBtn.clipH, gameHome.optionBtn.drawX, gameHome.optionBtn.drawY);

        CanvasUtil.drawClip(gameHome.img_menuBar, mCanvas, 121, 107,
                82, 23, gameHome.optionBtn.drawX + (gameHome.optionBtn.clipW - 82) / 2, gameHome.optionBtn.drawY + (gameHome.optionBtn.clipH - 23) / 2);
//
//        switch (gameHome.gameMain.appClass.gameState) {
//            case INN.GAME_HOME:
//                CanvasUtil.drawClip(gameHome.img_mainBtn, mCanvas, 270, 0,
//                        gameHome.optionBtn.clipW, 18, gameHome.optionBtn.drawX, gameHome.optionBtn.drawY + 12);
//                break;
//            default:
//                CanvasUtil.drawClip(gameHome.img_mainBtn, mCanvas, 270, 18,
//                        gameHome.optionBtn.clipW, 18, gameHome.optionBtn.drawX, gameHome.optionBtn.drawY + 12);
//                break;
//        }
    }

    private void drawTurnStartAlert(Canvas mCanvas) {
        CanvasUtil.drawBitmap(gameHome.alertBox, mCanvas, (gameHome.gameMain.appClass.getGameCanvasWidth() - gameHome.alertBox.getWidth()) / 2
                , (gameHome.gameMain.appClass.getGameCanvasHeight() - gameHome.alertBox.getHeight()) / 2);
        int alertBtnClipX = gameHome.alertBtn.clipX;
        if (gameHome.alertBtn.clickState == ButtonEnty.ButtonClickOn) {
            alertBtnClipX += gameHome.alertBtn.clipW;
        }
        drawClip(gameHome.img_mainBtn, mCanvas,
                alertBtnClipX, gameHome.alertBtn.clipY,
                gameHome.alertBtn.clipW, gameHome.alertBtn.clipH,
                gameHome.alertBtn.drawX, gameHome.alertBtn.drawY);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
//        paint.setAntiAlias(true);
        paint.setTextSize(20);
        int strX = (gameHome.gameMain.appClass.getGameCanvasWidth() - 400) / 2 + 30;
        int strY = (gameHome.gameMain.appClass.getGameCanvasHeight() - 300) / 2;
        CanvasUtil.drawString(mCanvas, gameHome.gameMain.userEnty.TURN + " 턴", paint, strX, strY);
        CanvasUtil.drawString(mCanvas, "완료된 퀘스트 수 - ", paint, strX, strY + 30);
        CanvasUtil.drawString(mCanvas, "길드 수입 - " + gameHome.gameMain.userEnty.eventEnty.Gold + "Gold", paint, strX, strY + 80);
//        CanvasUtil.drawString(mCanvas, "충전된 AP - " + turnManager.turnEnty.AP + "point", paint, strX, strY + 130);
        CanvasUtil.drawString(mCanvas, "새로운 퀘스트 수 - " + gameHome.gameMain.userEnty.eventEnty.QuestIDList.size(), paint, strX, strY + 180);
    }

    /**
     * draw user status
     */
    private void drawStatusTab(Canvas mCanvas){
        int canvasWidth = gameHome.gameMain.appClass.getGameCanvasWidth();
        UserEnty userEnty = gameHome.gameMain.userEnty;
        Paint paint = new Paint();
        int drawY = gameHome.mMenuTabBarY+gameHome.gameMain.statusBarH/2-10;
        int drawX = (canvasWidth - 120) / 5 + 60;
        paint.setColor(Color.argb(255, 50, 50, 50));
        paint.setTextSize(20);

        // Name Lv
        CanvasUtil.drawString(mCanvas, userEnty.Name + "(Lv " + userEnty.LEVEL + ")", paint, 10, drawY);

        // AP
        CanvasUtil.drawString(mCanvas, "AP " + userEnty.AP, paint, drawX, drawY);

        // Gold
        CanvasUtil.drawClip(gameHome.img_mainBtn, mCanvas, gameHome.goldIcon.clipX, gameHome.goldIcon.clipY,
                gameHome.goldIcon.clipW, gameHome.goldIcon.clipH, gameHome.goldIcon.drawX, drawY);
        CanvasUtil.drawString(mCanvas, Integer.toString(userEnty.GOLD), paint,
                gameHome.goldIcon.drawX + gameHome.goldIcon.clipW + 5, drawY + 3);

        // Member
        CanvasUtil.drawClip(gameHome.img_mainBtn, mCanvas, gameHome.menIcon.clipX, gameHome.menIcon.clipY,
                gameHome.menIcon.clipW, gameHome.menIcon.clipH, gameHome.menIcon.drawX, drawY);
        CanvasUtil.drawString(mCanvas, Integer.toString(userEnty.MEMBERLIST.size()), paint,
                gameHome.menIcon.drawX + gameHome.menIcon.clipW + 5, drawY + 3);

//        CanvasUtil.drawClip(gameHome.img_mainBtn, mCanvas, partyIcon.clipX, partyIcon.clipY,
//                partyIcon.clipW, partyIcon.clipH, partyIcon.drawX, partyIcon.drawY);
//        CanvasUtil.drawString(mCanvas, Integer.toString(userEnty.PARTYLIST.size()), paint,
//                partyIcon.drawX + partyIcon.clipW + 5, partyIcon.drawY + 3);

        //Turn
        CanvasUtil.drawString(mCanvas, "Turn " + userEnty.TURN, paint, canvasWidth - 180, drawY);
    }

    private void drawManager(Canvas mCanvas) {
        int managerBottomY = gameHome.mMenuTabBarY - 484;
        CanvasUtil.drawClip(managerImg.bitmap, mCanvas, 490, 156,
                228, 484, 50, managerBottomY);

        if (managerImg.animCnt < managerImg.animMaxFrame) {
            managerImg.animCnt++;
        } else {
            managerImg.animCnt = 0;
        }

        for (ClipImageEnty enty : managerImg.animClipList) {
            if (enty.animStartFrame <= managerImg.animCnt && enty.animEndFrame >= managerImg.animCnt) {
                CanvasUtil.drawClip(managerImg.bitmap, mCanvas, enty.animClipX, enty.animClipY,
                        enty.animClipW, enty.animClipH, enty.animDrawX - 490 + 50, enty.animDrawY - 156 + managerBottomY);
                break;
            }
        }

    }
}
