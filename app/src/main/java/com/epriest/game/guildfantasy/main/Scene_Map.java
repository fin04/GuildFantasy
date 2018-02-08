package com.epriest.game.guildfantasy.main;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.epriest.game.CanvasGL.graphics.CanvasUtil;
import com.epriest.game.CanvasGL.graphics.GLUtil;
import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.CanvasGL.util.Scene;
import com.epriest.game.guildfantasy.util.DrawUtil;
import com.epriest.game.guildfantasy.util.INN;

/**
 * Created by darka on 2017-07-28.
 */

public class Scene_Map extends Scene {
    private Game_Map gameMap;

    private int canvasW, canvasH;

    private Bitmap img_mapBg;
    private Bitmap img_curTile;
    private Bitmap img_card;

    private int cursorTileAnimCnt;

    public Scene_Map(Game_Map gameMap, Scene_Main sceneMain) {
        this.gameMap = gameMap;
        //        this.sceneMain = sceneMain;
    }

    @Override
    public void initScene(ApplicationClass appClass) {
        canvasW = appClass.getGameCanvasWidth();
        canvasH = appClass.getGameCanvasHeight();

        img_mapBg = GLUtil.loadAssetsBitmap(appClass, "map/tilemap.png", null);
        img_curTile = GLUtil.loadAssetsBitmap(appClass, "map/tilecursor.png", null);
        img_card = GLUtil.loadAssetsBitmap(appClass, "main/membercard.png", null);
    }

    @Override
    public void recycleScene() {
        CanvasUtil.recycleBitmap(img_mapBg);
        CanvasUtil.recycleBitmap(img_curTile);
    }

    @Override
    public void drawLoading(Canvas mCanvas, Bitmap loading) {
        mCanvas.drawBitmap(loading, 0, 0, null);
    }

    @Override
    public void draw(Canvas mCanvas) {
        drawMap(mCanvas);
        drawMapCursor(mCanvas);
        drawControlBg(mCanvas);


//        mapLayer.cursor.tileNum = mapLayer.buildiingColumnList.get(mapLayer.cursor.curTile.y)[mapLayer.cursor.curTile.x]-1;
//        if(mapLayer.cursor.tileNum == -1)
//            mapLayer.cursor.tileNum = mapLayer.terrainColumnList.get(mapLayer.cursor.curTile.y)[mapLayer.cursor.curTile.x]-1;
        String curStr = INN.tileName[gameMap.mapLayer.cursor.tileNum] +"["
                +(gameMap.mapLayer.cursor.curTile.x+1) + "," + (gameMap.mapLayer.cursor.curTile.y+1)+"]";
        DrawUtil.drawString(mCanvas, curStr, 20, Color.argb(200,50,50,50), Paint.Align.RIGHT,
                20, 8);

        switch (gameMap.mapLayer.cursor.state ) {
            case Game_Map.MAPTILE_TOWN:
                drawPartyControl(mCanvas);
                break;
            default:
                break;
        }

        gameMap.gameMain.drawStatusTab(mCanvas);
    }

    private void drawMapCursor(Canvas mCanvas) {
        int tileW = gameMap.mapLayer.getTileWidth();
        int tileH = gameMap.mapLayer.getTileHeight();
        int cursorH = img_curTile.getHeight();
        int tempW = 0; // Hex DrawX per Line
        int tempH = gameMap.mapLayer.mTileHeightForMap; // Hex DrawY per Line
        int startX = gameMap.mapLayer.mMapAxis.x;
        int startY = gameMap.mMainScreenY - (tileH / 4) - gameMap.mapLayer.mMapAxis.y;
        int drawY = gameMap.mapLayer.cursor.curTile.y * tempH -10+ startY;
        if (gameMap.mapLayer.cursor.curTile.y % 2 == 0)
            tempW = tileW / 2;
        int drawX = gameMap.mapLayer.cursor.curTile.x * tileW-10 - tempW + startX;
        CanvasUtil.drawClip(img_curTile, mCanvas, (cursorTileAnimCnt/8) * cursorH, 0, cursorH, cursorH, drawX, drawY);
        if (cursorTileAnimCnt == 32)
            cursorTileAnimCnt = 0;
        else
            cursorTileAnimCnt++;
    }

    private void drawMap(Canvas mCanvas) {
        int tileW = gameMap.mapLayer.getTileWidth();
        int tileH = gameMap.mapLayer.getTileHeight();
        int row = gameMap.mapLayer.mMapTileRowNum;
        int column = gameMap.mapLayer.mMapTileColumnNum;
        int mapLeftTileNum = gameMap.mapLayer.LeftTopTileNum.x;
        int mapTopTileNum = gameMap.mapLayer.LeftTopTileNum.y;
        int tempW = tileW / 2; // Hex DrawX per Line
        int tempH = gameMap.mapLayer.mTileHeightForMap; // Hex DrawY per Line
        int startY = gameMap.mMainScreenY - (tileH / 4) - gameMap.mapLayer.mMapAxis.y;

        for (int i = mapTopTileNum; i < column + mapTopTileNum; i++) {
            int startX = gameMap.mapLayer.mMapAxis.x;
            if (i % 2 == 0)
                startX = gameMap.mapLayer.mMapAxis.x - tempW;
            int drawY = i * tempH + startY;

            for (int j = mapLeftTileNum; j < row + mapLeftTileNum; j++) {
                int drawX = j * tileW + startX;
                int mapNum = gameMap.mapLayer.terrainColumnList.get(i)[j] - 1;
                int buildNum = gameMap.mapLayer.buildiingColumnList.get(i)[j] - 1;
                CanvasUtil.drawClip(img_mapBg, mCanvas, (mapNum % 4) * tileW, (mapNum / 4) * tileH, tileW, tileH, drawX, drawY);
                CanvasUtil.drawClip(img_mapBg, mCanvas, (buildNum % 4) * tileW, (buildNum / 4) * tileH, tileW, tileH, drawX, drawY);
            }
        }
    }

    private void drawControlBg(Canvas mCanvas) {
        int drawY = gameMap.mMenuTabBarY + gameMap.img_menuBar.getHeight() - 5;
        int drawH = gameMap.gameMain.appClass.getGameCanvasHeight() - drawY;
        CanvasUtil.drawBox(mCanvas, Color.argb(255, 40, 50, 100), true,
                0, drawY, gameMap.gameMain.appClass.getGameCanvasWidth(), drawH);
    }

    private void drawPartyControl(Canvas mCanvas) {
        int drawY = gameMap.mMenuTabBarY + gameMap.img_menuBar.getHeight() - 5;
        for (int i = 0; i < 5; i++) {
            CanvasUtil.drawBitmap(img_card, mCanvas, 10 + i * (5 + img_card.getWidth()), drawY + 50);
        }

    }
}

