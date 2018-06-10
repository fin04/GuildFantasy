package com.epriest.game.guildfantasy.main;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import com.epriest.game.CanvasGL.graphics.CanvasUtil;
import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.CanvasGL.util.Scene;
import com.epriest.game.guildfantasy.main.enty.HexaEnty;
import com.epriest.game.guildfantasy.main.enty.MonsterEnty;
import com.epriest.game.guildfantasy.main.enty.UnitEnty;
import com.epriest.game.guildfantasy.util.DrawUtil;

import java.util.ArrayList;

/**
 * Created by darka on 2018-02-01.
 */

public class Scene_Dungeon extends Scene {
    private Game_Dungeon gameDungeon;

    private int cursorTileAnimCnt;
    private int tileW, tileH;
    private int cursorMargin;
    /**
     * 커서 위치 보정값
     */
    private int cursorCorr;

    public Scene_Dungeon(Game_Dungeon gameDungeon, Scene_Main sceneMain) {
        this.gameDungeon = gameDungeon;
    }

    @Override
    public void initScene(ApplicationClass appClass) {
        tileW = gameDungeon.mapLayer.getTileWidth();
        tileH = gameDungeon.mapLayer.getTileHeight();
        cursorMargin = gameDungeon.img_curTile.getHeight();
        cursorCorr = (cursorMargin - tileH) / 2;
    }

    @Override
    public void recycleScene() {

    }

    @Override
    public void drawLoading(Canvas mCanvas, Bitmap loading) {

    }

    @Override
    public void draw(Canvas mCanvas) {
        DrawUtil.drawBox(mCanvas, Color.argb(255, 40, 60, 60), true,
                0, 0, gameDungeon.canvasW, gameDungeon.canvasH);

        //map draw
        drawMap(mCanvas);


        //member draw
        for (UnitEnty unitEnty : gameDungeon.partyUnitList) {
            drawUnit(mCanvas, unitEnty);
        }

        //monster draw
        for (MonsterEnty monEnty : gameDungeon.monsterList) {
            drawMon(mCanvas, monEnty);
        }

        drawUnitZOC(mCanvas);
        drawCursor(mCanvas);

        DrawUtil.drawString(mCanvas, gameDungeon.mapLayer.mMapAxis.x+",,"+gameDungeon.mapLayer.mMapAxis.y,
                20, Color.RED, Paint.Align.LEFT, 10,90);

        gameDungeon.gameMain.drawStatusTab(mCanvas);
    }

    private void drawMap(Canvas mCanvas) {
        for (int i = gameDungeon.mapLayer.LeftTopTileNum.y;
             i < gameDungeon.mapLayer.mMapTileColumnNum + gameDungeon.mapLayer.LeftTopTileNum.y; i++) {

            for (int j = gameDungeon.mapLayer.LeftTopTileNum.x;
                 j < gameDungeon.mapLayer.mMapTileRowNum + gameDungeon.mapLayer.LeftTopTileNum.x; j++) {

                Point hexaAxis = getHexaDrawAxis(j, i);

                int mapNum = gameDungeon.mapLayer.terrainColumnList.get(i)[j] - 1;
                int objNum = gameDungeon.mapLayer.objectColumnList.get(i)[j] - 1;
                CanvasUtil.drawClip(gameDungeon.img_mapBg, mCanvas,
                        (mapNum % 4) * tileW, (mapNum / 4) * tileH,
                        tileW, tileH, hexaAxis.x, hexaAxis.y);
                CanvasUtil.drawClip(gameDungeon.img_mapBg, mCanvas,
                        (objNum % 4) * tileW, (objNum / 4) * tileH,
                        tileW, tileH, hexaAxis.x, hexaAxis.y);
            }
        }
    }

    private void drawUnitZOC(Canvas mCanvas) {
        if (gameDungeon.unitZocList == null)
            return;
        int tileCenter = tileH / 2;
        for (HexaEnty enty : gameDungeon.unitZocList) {
            Point zocAxis = getHexaDrawAxis(enty.x, enty.y);
            CanvasUtil.drawBitmap(gameDungeon.img_zoc, mCanvas, zocAxis.x, zocAxis.y);
            CanvasUtil.drawString(mCanvas, Integer.toString(enty.num), 20, Color.rgb(180, 0, 0),
                    Paint.Align.CENTER, zocAxis.x + tileCenter, zocAxis.y + tileCenter - 10);
        }
    }

    private void drawCursor(Canvas mCanvas) {
        Point curAxis = getHexaDrawAxis(gameDungeon.mapLayer.cursor.curTile.x,
                gameDungeon.mapLayer.cursor.curTile.y);
        CanvasUtil.drawClip(gameDungeon.img_curTile, mCanvas, (cursorTileAnimCnt / 8) * cursorMargin, 0,
                cursorMargin, cursorMargin, curAxis.x - cursorCorr, curAxis.y - cursorCorr);
        if (cursorTileAnimCnt == 32)
            cursorTileAnimCnt = 0;
        else
            cursorTileAnimCnt++;
    }

    private void drawUnit(Canvas mCanvas, UnitEnty unitEnty) {
        Point drawAxis = getHexaDrawAxis(unitEnty.curAxisX, unitEnty.curAxisY);

        //unitBg
        DrawUtil.drawClip(gameDungeon.img_unit, mCanvas, 0, 0,
                gameDungeon.img_unit.getHeight(), gameDungeon.img_unit.getHeight(),
                drawAxis.x += (tileW - unitEnty.unitW) / 2, drawAxis.y += (tileH - unitEnty.unitH) / 2);

        //profile
        DrawUtil.drawClip(unitEnty.chr_img, mCanvas, (unitEnty.chr_img.getWidth() - unitEnty.unitprofileW) / 2, 0,
                unitEnty.unitprofileW, unitEnty.uinitprofileH, drawAxis.x + 10, drawAxis.y);
        //hp
        int hpGuage = (int) ((float) unitEnty.memberEnty.status.USE_HP / (float) unitEnty.memberEnty.status.MAX_HP * unitEnty.untibarH);
        DrawUtil.drawBox(mCanvas, Color.GREEN, true, drawAxis.x + 1, drawAxis.y + (unitEnty.untibarH - hpGuage),
                unitEnty.unitbarW, hpGuage);
        //name
        int nameY = drawAxis.y + unitEnty.unitH - unitEnty.unitnamebarH;
        DrawUtil.drawBox(mCanvas, Color.argb(150, 50, 50, 160), true,
                drawAxis.x, nameY, unitEnty.unitnamebarW, unitEnty.unitnamebarH);
        DrawUtil.drawString(mCanvas, unitEnty.memberEnty.name, unitEnty.unitfontsize, Color.WHITE, Paint.Align.CENTER,
                drawAxis.x + unitEnty.unitW / 2, nameY);
    }

    private void drawMon(Canvas mCanvas, MonsterEnty monEnty) {
        Point monAxis = getHexaDrawAxis(monEnty.startAxisX, monEnty.startAxisy);
        int monMargin = (tileH - monEnty.mon_img.getHeight())/2;
        DrawUtil.drawBitmap(monEnty.mon_img, mCanvas, monAxis.x+monMargin, monAxis.y+monMargin);
    }

    /**
     * hexa 좌표를 draw 좌표로 전환
     *
     * @param hexaX
     * @param hexaY
     * @return
     */
    private Point getHexaDrawAxis(int hexaX, int hexaY) {
        Point point = new Point();
        int startX = gameDungeon.mapLayer.mMapAxis.x;
        if (hexaY % 2 == 0)
            startX = gameDungeon.mapLayer.mMapAxis.x - (tileW / 2);
        int startY = gameDungeon.mMainScreenTop - gameDungeon.mapLayer.mMapAxis.y ;//- (tileH / 4);
        point.x = hexaX * tileW + startX;
        point.y = hexaY * gameDungeon.mapLayer.mTileHeightForMap + gameDungeon.mMainScreenTop + startY;
        return point;
    }
}
