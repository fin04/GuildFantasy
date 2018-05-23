package com.epriest.game.guildfantasy.main;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.epriest.game.CanvasGL.graphics.CanvasUtil;
import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.CanvasGL.util.Scene;
import com.epriest.game.guildfantasy.main.enty.UnitEnty;
import com.epriest.game.guildfantasy.util.DrawUtil;

/**
 * Created by darka on 2018-02-01.
 */

public class Scene_Dungeon extends Scene{
    private Game_Dungeon gameDungeon;

    private int cursorTileAnimCnt;

    public Scene_Dungeon(Game_Dungeon gameDungeon, Scene_Main sceneMain) {
        this.gameDungeon = gameDungeon;
    }

    @Override
    public void initScene(ApplicationClass appClass) {

    }

    @Override
    public void recycleScene() {

    }

    @Override
    public void drawLoading(Canvas mCanvas, Bitmap loading) {

    }

    @Override
    public void draw(Canvas mCanvas) {
        DrawUtil.drawBox(mCanvas, Color.BLUE, true, 0, 0, gameDungeon.canvasW, gameDungeon.canvasH);

        //map draw
        drawMap(mCanvas);
        drawMapCursor(mCanvas);

        //member draw
        for(UnitEnty unitEnty : gameDungeon.partyUnitList) {
            drawUnit(mCanvas, unitEnty);
        }

        gameDungeon.gameMain.drawStatusTab(mCanvas);
    }

    private void drawMap(Canvas mCanvas) {
        int tileW = gameDungeon.mapLayer.getTileWidth();
        int tileH = gameDungeon.mapLayer.getTileHeight();
        int row = gameDungeon.mapLayer.mMapTileRowNum;
        int column = gameDungeon.mapLayer.mMapTileColumnNum;
        int mapLeftTileNum = gameDungeon.mapLayer.LeftTopTileNum.x;
        int mapTopTileNum = gameDungeon.mapLayer.LeftTopTileNum.y;
        int tempW = tileW / 2; // Hex DrawX per Line
        int tempH = gameDungeon.mapLayer.mTileHeightForMap; // Hex DrawY per Line
        int startY = gameDungeon.mMainScreenY - (tileH / 4) - gameDungeon.mapLayer.mMapAxis.y;

        for (int i = mapTopTileNum; i < column + mapTopTileNum; i++) {
            int startX = gameDungeon.mapLayer.mMapAxis.x;
            if (i % 2 == 0)
                startX = gameDungeon.mapLayer.mMapAxis.x - tempW;
            int drawY = i * tempH + startY;

            for (int j = mapLeftTileNum; j < row + mapLeftTileNum; j++) {
                int drawX = j * tileW + startX;
                int mapNum = gameDungeon.mapLayer.terrainColumnList.get(i)[j] - 1;
                int buildNum = gameDungeon.mapLayer.buildiingColumnList.get(i)[j] - 1;
                CanvasUtil.drawClip(gameDungeon.img_mapBg, mCanvas, (mapNum % 4) * tileW, (mapNum / 4) * tileH, tileW, tileH, drawX, drawY);
                CanvasUtil.drawClip(gameDungeon.img_mapBg, mCanvas, (buildNum % 4) * tileW, (buildNum / 4) * tileH, tileW, tileH, drawX, drawY);
            }
        }
    }

    private void drawMapCursor(Canvas mCanvas) {
        int tileW = gameDungeon.mapLayer.getTileWidth();
        int tileH = gameDungeon.mapLayer.getTileHeight();
        int cursorH = gameDungeon.img_curTile.getHeight();
        int tempW = 0; // Hex DrawX per Line
        int tempH = gameDungeon.mapLayer.mTileHeightForMap; // Hex DrawY per Line
        int startX = gameDungeon.mapLayer.mMapAxis.x;
        int startY = gameDungeon.mMainScreenY - (tileH / 4) - gameDungeon.mapLayer.mMapAxis.y;
        int drawY = gameDungeon.mapLayer.cursor.curTile.y * tempH -10+ startY;
        if (gameDungeon.mapLayer.cursor.curTile.y % 2 == 0)
            tempW = tileW / 2;
        int drawX = gameDungeon.mapLayer.cursor.curTile.x * tileW-10 - tempW + startX;
        CanvasUtil.drawClip(gameDungeon.img_curTile, mCanvas, (cursorTileAnimCnt/8) * cursorH, 0, cursorH, cursorH, drawX, drawY);
        if (cursorTileAnimCnt == 32)
            cursorTileAnimCnt = 0;
        else
            cursorTileAnimCnt++;
    }

    private void drawUnit(Canvas mCanvas, UnitEnty unitEnty){
        int bitmapW = unitEnty.profile.getWidth();
        int bitmapH = unitEnty.profile.getHeight();

        //unitBg
        DrawUtil.drawBox(mCanvas, Color.argb(255, 60, 60, 60), true,
                unitEnty.curAxisX, unitEnty.curAxisY, unitEnty.unitW, unitEnty.unitH);

        //profile
        DrawUtil.drawClip(unitEnty.profile, mCanvas, (bitmapW - unitEnty.unitprofileW) / 2, 0,
                unitEnty.unitprofileW, unitEnty.uinitprofileH, unitEnty.curAxisX+10, unitEnty.curAxisY);
        //hp
        int hpGuage = (int)((float)unitEnty.memberEnty.status.USE_HP/(float)unitEnty.memberEnty.status.MAX_HP*unitEnty.untibarH);
        DrawUtil.drawBox(mCanvas, Color.GREEN, true, unitEnty.curAxisX, unitEnty.curAxisY+(unitEnty.untibarH-hpGuage),
                unitEnty.unitbarW, hpGuage);
        //name
        DrawUtil.drawString(mCanvas, unitEnty.memberEnty.name, unitEnty.unitfontsize, Color.WHITE, Paint.Align.CENTER,
                unitEnty.curAxisX+unitEnty.unitW/2, unitEnty.curAxisY+unitEnty.unitH-unitEnty.unitnamebarH);
    }
}
