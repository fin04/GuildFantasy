package com.epriest.game.guildfantasy.main;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.epriest.game.CanvasGL.graphics.CanvasUtil;
import com.epriest.game.CanvasGL.graphics.GLUtil;
import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.CanvasGL.util.Game;
import com.epriest.game.CanvasGL.util.Scene;
import com.epriest.game.guildfantasy.enty.ClipImageEnty;
import com.epriest.game.guildfantasy.enty.ImageEnty;

import java.util.ArrayList;

/**
 * Created by darka on 2016-10-31.
 */

public class Scene_Home extends Scene {

    private Game gameHome;
    private Scene_Main sceneMain;
    private int canvasW, canvasH;

    private Bitmap bg;
    private Bitmap char_01;
//    private Bitmap menu_icon;

    private ImageEnty managerImg;

//    private String[] menuIcon = {"Home" , "Party", "Member", "Town", "Quest", "Setting"};
//    private int[] menuIconNum = {1 , 2, 3, 4, 5, 20};
//
//    private final int charRow = 10;
//    private final int charNum = 28;
//    private final int charW = 80;
//    private final int charH = 96;
//
//    private final int insigniaRow = 10;
//    private final int insigniaNum = 20;
//    private final int insigniaW = 80;
//    private final int insigniaH = 96;
//
//    private final int iconRow = 5;
//    private final int iconNum = 20;
//    private final int iconW = 120;
//    private final int iconH = 120;
//
//    private final int statusBarH = 32;

    public Scene_Home(Game gameHome, Scene_Main sceneMain) {
        this.gameHome = gameHome;
        this.sceneMain = sceneMain;
    }

    @Override
    public void initScene(ApplicationClass appClass) {
        canvasW = appClass.getGameCanvasWidth();
        canvasH = appClass.getGameCanvasHeight();

        bg = GLUtil.loadAssetsBitmap(appClass, "main/home.png", null);
        char_01 = GLUtil.loadAssetsBitmap(appClass, "main/char_01.jpg", null);
//        menu_icon = GLUtil.loadAssetsBitmap(appClass, "main/main_icon.png", null);

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
        CanvasUtil.recycleBitmap(bg);
        CanvasUtil.recycleBitmap(char_01);
        CanvasUtil.recycleBitmap(managerImg.bitmap);
    }

    @Override
    public void drawLoading(Canvas mCanvas, Bitmap loading) {
        mCanvas.drawBitmap(loading, 0, 0, null);
    }

    @Override
    public void draw(Canvas mCanvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
//        mCanvas.drawColor(Color.BLACK);

        mCanvas.drawBitmap(bg, 0, 0, null);

        //manager mode
        drawManager(mCanvas, paint);

        sceneMain.drawMain(mCanvas, paint, true);
    }





    private void drawManager(Canvas mCanvas, Paint paint) {
        CanvasUtil.drawClip(managerImg.bitmap, mCanvas, paint, 490, 156,
                228, 484, 50, canvasH-484);

        if(managerImg.animCnt < managerImg.animMaxFrame){
            managerImg.animCnt++;
        }else{
            managerImg.animCnt = 0;
        }

        for(ClipImageEnty enty : managerImg.animClipList){
            if(enty.animStartFrame <= managerImg.animCnt && enty.animEndFrame >= managerImg.animCnt){
                CanvasUtil.drawClip(managerImg.bitmap, mCanvas, paint, enty.animClipX, enty.animClipY,
                        enty.animClipW, enty.animClipH, enty.animDrawX-490+50, enty.animDrawY-156+canvasH-484);
                break;
            }
        }

    }
}
