package com.epriest.game.guildfantasy.main;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import com.epriest.game.CanvasGL.graphics.CanvasUtil;
import com.epriest.game.CanvasGL.graphics.GLUtil;
import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.CanvasGL.util.Scene;
import com.epriest.game.guildfantasy.main.enty.ClipImageEnty;
import com.epriest.game.guildfantasy.main.enty.ImageEnty;

import java.util.ArrayList;

/**
 * Created by darka on 2016-10-31.
 */

public class Scene_Home extends Scene {

    private Game_Home gameHome;
    //    private Scene_Main sceneMain;
    private int canvasW, canvasH;

    private Bitmap mapBg;
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

    public Scene_Home(Game_Home gameHome, Scene_Main sceneMain) {
        this.gameHome = gameHome;
//        this.sceneMain = sceneMain;
    }

    @Override
    public void initScene(ApplicationClass appClass) {
        canvasW = appClass.getGameCanvasWidth();
        canvasH = appClass.getGameCanvasHeight();

        mapBg = GLUtil.loadAssetsBitmap(appClass, "map/tilemap.png", null);
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
        CanvasUtil.recycleBitmap(mapBg);
        CanvasUtil.recycleBitmap(char_01);
        CanvasUtil.recycleBitmap(managerImg.bitmap);
    }

    @Override
    public void drawLoading(Canvas mCanvas, Bitmap loading) {
        mCanvas.drawBitmap(loading, 0, 0, null);
    }

    @Override
    public void draw(Canvas mCanvas) {
        //draw map tile
        drawMap(mCanvas);

        //manager mode
//        drawManager(mCanvas);

        gameHome.gameMain.drawMain(mCanvas, true);
    }

    private void drawMap(Canvas mCanvas) {
        int tileW = gameHome.gameMain.mapLayer.getTileWidth();
        int tileH = gameHome.gameMain.mapLayer.getTileHeight();
        int row = gameHome.gameMain.mapLayer.mMapTileRowNum;
        int column = gameHome.gameMain.mapLayer.mMapTileColumnNum;
        int tempX = tileW / 2; // Hex DrawX per Line
        int tempY = tileH / 4 * 3; // Hex DrawY per Line
        int startY = Game_Main.statusBarH - (tileH / 4) - gameHome.gameMain.mapLayer.mMapAxis.y;
        int startTempX = gameHome.gameMain.mapLayer.mMapAxis.x;
        int mapLeftTileNum = gameHome.gameMain.mapLayer.LeftTopTileNum.x;
        int mapTopTileNum = gameHome.gameMain.mapLayer.LeftTopTileNum.y;
        for (int i = mapTopTileNum; i < column + mapTopTileNum; i++) {
            int startX = startTempX - tempX;
            if (i % 2 == 1)
                startX = startTempX;
            int drawY = i * tempY + startY;
            for (int j = mapLeftTileNum; j < row + mapLeftTileNum; j++) {
                int mapNum = gameHome.gameMain.mapLayer.terrainColumnList.get(i)[j]-1;
                int buildNum = gameHome.gameMain.mapLayer.buildiingColumnList.get(i)[j]-1;
                CanvasUtil.drawClip(mapBg, mCanvas, (mapNum % 4) * tileW, (mapNum / 4) * tileH, tileW, tileH, j * tileW + startX, drawY);
                CanvasUtil.drawClip(mapBg, mCanvas, (buildNum % 4) * tileW, (buildNum / 4) * tileH, tileW, tileH, j * tileW + startX, drawY);
            }
        }
    }

    private void drawManager(Canvas mCanvas) {
        CanvasUtil.drawClip(managerImg.bitmap, mCanvas, 490, 156,
                228, 484, 50, canvasH - 484);

        if (managerImg.animCnt < managerImg.animMaxFrame) {
            managerImg.animCnt++;
        } else {
            managerImg.animCnt = 0;
        }

        for (ClipImageEnty enty : managerImg.animClipList) {
            if (enty.animStartFrame <= managerImg.animCnt && enty.animEndFrame >= managerImg.animCnt) {
                CanvasUtil.drawClip(managerImg.bitmap, mCanvas, enty.animClipX, enty.animClipY,
                        enty.animClipW, enty.animClipH, enty.animDrawX - 490 + 50, enty.animDrawY - 156 + canvasH - 484);
                break;
            }
        }

    }
}
