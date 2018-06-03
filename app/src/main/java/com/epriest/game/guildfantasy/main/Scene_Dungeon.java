package com.epriest.game.guildfantasy.main;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.epriest.game.CanvasGL.graphics.CanvasUtil;
import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.CanvasGL.util.Scene;
import com.epriest.game.guildfantasy.main.enty.HexaEnty;
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
    private int cursorDiameter;
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
        cursorDiameter = gameDungeon.img_curTile.getHeight();
        cursorCorr = (cursorDiameter - tileH) / 2;
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

        drawUnitZOC(mCanvas);
        drawCursor(mCanvas);

        gameDungeon.gameMain.drawStatusTab(mCanvas);
    }

    private void drawMap(Canvas mCanvas) {
        for (int i = gameDungeon.mapLayer.LeftTopTileNum.y;
             i < gameDungeon.mapLayer.mMapTileColumnNum + gameDungeon.mapLayer.LeftTopTileNum.y; i++) {

            for (int j = gameDungeon.mapLayer.LeftTopTileNum.x;
                 j < gameDungeon.mapLayer.mMapTileRowNum + gameDungeon.mapLayer.LeftTopTileNum.x; j++) {

                int[] hexaAxis = getHexaDrawAxis(j, i);

                int mapNum = gameDungeon.mapLayer.terrainColumnList.get(i)[j] - 1;
                int buildNum = gameDungeon.mapLayer.buildiingColumnList.get(i)[j] - 1;
                CanvasUtil.drawClip(gameDungeon.img_mapBg, mCanvas,
                        (mapNum % 4) * tileW, (mapNum / 4) * tileH,
                        tileW, tileH, hexaAxis[0], hexaAxis[1]);
                CanvasUtil.drawClip(gameDungeon.img_mapBg, mCanvas,
                        (buildNum % 4) * tileW, (buildNum / 4) * tileH,
                        tileW, tileH, hexaAxis[0], hexaAxis[1]);
            }
        }
    }

    private void drawUnitZOC(Canvas mCanvas) {
        if(gameDungeon.unitZocList == null)
            return;
        int tileCenter = tileH/2;
        for(HexaEnty enty : gameDungeon.unitZocList){
            int[] axis = getHexaDrawAxis(enty.x, enty.y);
            CanvasUtil.drawBitmap(gameDungeon.img_zoc, mCanvas, axis[0], axis[1]);
            CanvasUtil.drawString(mCanvas, Integer.toString(enty.num), 20, Color.rgb(180,0,0),
                    Paint.Align.CENTER, axis[0]+tileCenter, axis[1]+tileCenter-10);
        }
    }

    private void drawCursor(Canvas mCanvas) {
        int[] curAxis = getHexaDrawAxis(gameDungeon.mapLayer.cursor.curTile.x,
                gameDungeon.mapLayer.cursor.curTile.y);
        CanvasUtil.drawClip(gameDungeon.img_curTile, mCanvas, (cursorTileAnimCnt / 8) * cursorDiameter, 0,
                cursorDiameter, cursorDiameter, curAxis[0]-cursorCorr, curAxis[1]-cursorCorr);
        if (cursorTileAnimCnt == 32)
            cursorTileAnimCnt = 0;
        else
            cursorTileAnimCnt++;
    }

    private void drawUnit(Canvas mCanvas, UnitEnty unitEnty) {
        int[] drawAxis = getHexaDrawAxis(unitEnty.curAxisX, unitEnty.curAxisY);

        //unitBg
        DrawUtil.drawClip(gameDungeon.img_unit, mCanvas, 0,0,
                gameDungeon.img_unit.getHeight(), gameDungeon.img_unit.getHeight(),
                drawAxis[0] += (tileW - unitEnty.unitW) / 2, drawAxis[1] += (tileH - unitEnty.unitH) / 2);

        //profile
        DrawUtil.drawClip(unitEnty.chr_img, mCanvas, (unitEnty.chr_img.getWidth() - unitEnty.unitprofileW) / 2, 0,
                unitEnty.unitprofileW, unitEnty.uinitprofileH, drawAxis[0] + 10, drawAxis[1]);
        //hp
        int hpGuage = (int) ((float) unitEnty.memberEnty.status.USE_HP / (float) unitEnty.memberEnty.status.MAX_HP * unitEnty.untibarH);
        DrawUtil.drawBox(mCanvas, Color.GREEN, true, drawAxis[0] + 1, drawAxis[1] + (unitEnty.untibarH - hpGuage),
                unitEnty.unitbarW, hpGuage);
        //name
        int nameY = drawAxis[1] + unitEnty.unitH - unitEnty.unitnamebarH;
        DrawUtil.drawBox(mCanvas, Color.argb(150, 50, 50, 160), true,
                drawAxis[0], nameY, unitEnty.unitnamebarW, unitEnty.unitnamebarH);
        DrawUtil.drawString(mCanvas, unitEnty.memberEnty.name, unitEnty.unitfontsize, Color.WHITE, Paint.Align.CENTER,
                drawAxis[0] + unitEnty.unitW / 2, nameY);
    }

    /**
     * hexa 좌표를 draw 좌표로 전환
     *
     * @param hexaX
     * @param hexaY
     * @return
     */
    private int[] getHexaDrawAxis(int hexaX, int hexaY) {
        int[] drawAxis = new int[2];
        int startX = gameDungeon.mapLayer.mMapAxis.x;
        if (hexaY % 2 == 0)
            startX = gameDungeon.mapLayer.mMapAxis.x - (tileW / 2);
        drawAxis[0] = hexaX * tileW + startX;
        drawAxis[1] = hexaY * gameDungeon.mapLayer.mTileHeightForMap + gameDungeon.mMainScreenTop;
        return drawAxis;
    }
}
