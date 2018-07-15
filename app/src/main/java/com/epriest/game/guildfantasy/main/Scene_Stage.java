package com.epriest.game.guildfantasy.main;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import com.epriest.game.CanvasGL.graphics.CanvasUtil;
import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.CanvasGL.util.Scene;
import com.epriest.game.guildfantasy.main.enty.ButtonEnty;
import com.epriest.game.guildfantasy.main.enty.HexaEnty;
import com.epriest.game.guildfantasy.main.enty.MonsterEnty;
import com.epriest.game.guildfantasy.main.enty.UnitEnty;
import com.epriest.game.guildfantasy.util.DrawUtil;
import com.epriest.game.guildfantasy.util.INN;

/**
 * Created by darka on 2018-02-01.
 */

public class Scene_Stage extends Scene {
    private Game_Stage gameDungeon;

    private int cursorTileAnimCnt;
    private int tileW, tileH;
    private int cursorMargin;
    /**
     * 커서 위치 보정값
     */
    private int cursorCorr;

    public Scene_Stage(Game_Stage gameDungeon, Scene_Main sceneMain) {
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
        drawUnit(mCanvas, gameDungeon.unitEnty);
//        for (UnitEnty unitEnty : gameDungeon.partyUnitList) {
//            drawUnit(mCanvas, unitEnty);
//        }

        //monster draw
        for (MonsterEnty monEnty : gameDungeon.monsterList) {
            drawMon(mCanvas, monEnty);
        }

        drawUnitZOC(mCanvas);
        drawCursor(mCanvas);
        drawPartyCard(mCanvas);

        DrawUtil.drawString(mCanvas, gameDungeon.mapLayer.mMapAxis.x + ",," + gameDungeon.mapLayer.mMapAxis.y,
                20, Color.RED, Paint.Align.LEFT, 10, 90);

        DrawUtil.drawBox(mCanvas, Color.argb(200, 0, 180, 30), true,
                0, gameDungeon.canvasH - gameDungeon.commandBtnH, gameDungeon.canvasW, gameDungeon.commandBtnH);

        drawUnitCommand(mCanvas);

        drawStageTab(mCanvas);
    }

    private void drawStageTab(Canvas mCanvas) {
        int statusBarW = gameDungeon.gameMain.statusBarW;
        int statusBarH = gameDungeon.gameMain.statusBarH;
        int barNum = gameDungeon.canvasW / statusBarW;
        for (int i = 0; i <= barNum; i++) {
            DrawUtil.drawClip(gameDungeon.gameMain.img_statusBar, mCanvas, 0, 0,
                    statusBarW, statusBarH, statusBarW * i, 0);
        }

        int fontSize = 30;
        int drawY = (statusBarH - fontSize) / 2;
        int drawX = (gameDungeon.canvasW - 120) / 5 + 60;
        Paint paint = new Paint();
        paint.setTextSize(fontSize);


        // tile Attr
        String curTileAttr = gameDungeon.MapTileAttrArr[gameDungeon.mapLayer.cursor.tileTerrianNum];
        if (curTileAttr.equals(INN.TILEATTR_EARTH)) {
            paint.setColor(Color.argb(255, 100, 200, 0));
        } else if (curTileAttr.equals(INN.TILEATTR_FIRE)) {
            paint.setColor(Color.argb(255, 200, 50, 0));
        } else if (curTileAttr.equals(INN.TILEATTR_WATER)) {
            paint.setColor(Color.argb(255, 0, 50, 200));
        } else if (curTileAttr.equals(INN.TILEATTR_WIND)) {
            paint.setColor(Color.argb(255, 50, 150, 200));
        } else if (curTileAttr.equals(INN.TILEATTR_ICE)) {
            paint.setColor(Color.argb(255, 100, 0, 200));
        }
        DrawUtil.drawBoldString(mCanvas, curTileAttr, fontSize, paint.getColor(), paint.getTextAlign(), 20, drawY);

        // tile Name
        String curTileName = gameDungeon.MapTileNameArr[gameDungeon.mapLayer.cursor.tileTerrianNum];
        paint.setColor(Color.argb(255, 50, 50, 50));
        DrawUtil.drawString(mCanvas, curTileName, paint, 100, drawY);

        // tile Object
        int i = gameDungeon.mapLayer.cursor.tileObjectNum;
        if (i > -1) {
            String curTileObj = gameDungeon.MapTileNameArr[i];
            DrawUtil.drawString(mCanvas, curTileObj, paint, 250, drawY);
        }

        // cursor tile axis
        String curAxis = gameDungeon.mapLayer.cursor.curTile.x + "," + gameDungeon.mapLayer.cursor.curTile.y;
        DrawUtil.drawString(mCanvas, curAxis, paint, 400, drawY);


        //Turn
//        DrawUtil.drawString(mCanvas, "Turn " + userEnty.TURN, paint, canvasW - 250, drawY);

        ButtonEnty menuBtn = gameDungeon.gameMain.optionBtn;
        //Option Button
        int clipY = menuBtn.clipY;
        if (menuBtn.clickState == ButtonEnty.ButtonClickOn) {
            clipY = 53;
        }
        DrawUtil.drawClip(gameDungeon.gameMain.img_statusBar, mCanvas, menuBtn.clipX, clipY,
                menuBtn.clipW, menuBtn.clipH, menuBtn.drawX, menuBtn.drawY);


        DrawUtil.drawClip(gameDungeon.gameMain.img_statusBar, mCanvas, 121, 107,
                82, 23, menuBtn.drawX + (menuBtn.clipW - 82) / 2,
                menuBtn.drawY + (menuBtn.clipH - 23) / 2);


    }

    private void drawUnitCommand(Canvas mCanvas){
        CanvasUtil.drawBox(mCanvas, Color.argb(255,80,80,80), true,
                0, gameDungeon.commandBtnList.get(0).drawY-5, gameDungeon.canvasW, gameDungeon.commandBtnH+10);
        for(ButtonEnty enty : gameDungeon.commandBtnList){
            CanvasUtil.drawClip(gameDungeon.gameMain.img_defBtn, mCanvas, enty.clipX, enty.clipY,
                    enty.clipW, enty.clipH, enty.drawX, enty.drawY);
            if(gameDungeon.unitZocList != null) {
                CanvasUtil.drawString(mCanvas, enty.name, 25, Color.argb(255, 200, 230, 250),
                        Paint.Align.CENTER, enty.drawX + enty.clipW / 2, enty.drawY + (enty.clipH - 25) / 2);
            }
        }
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
            if (enty == null)
                continue;
            if(!gameDungeon.isMoveableTile(enty.terrain))
                continue;
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
        int monMargin = (tileH - monEnty.mon_img.getHeight()) / 2;
        DrawUtil.drawBitmap(monEnty.mon_img, mCanvas, monAxis.x + monMargin, monAxis.y + monMargin);
    }

    private void drawPartyCard(Canvas mCanvas) {
        if (gameDungeon.selectUnitEnty == null && gameDungeon.selectMonsterEnty == null)
            return;

        if (gameDungeon.selectUnitEnty == null) {
            //monster
        } else {
            //unit
            for (UnitEnty enty : gameDungeon.partyUnitList) {
                drawPartyCard(mCanvas, enty);
            }
        }

    }

    private void drawPartyCard(Canvas mCanvas, UnitEnty enty) {
        int infoCardW = gameDungeon.canvasW / 4;
        int infoX = enty.num * infoCardW + 10;
        int infoY = gameDungeon.mapMarginTop + gameDungeon.mapDrawH + gameDungeon.commandBtnH;
        DrawUtil.drawBitmap(gameDungeon.img_unitCard, mCanvas, infoX, infoY);
        DrawUtil.drawClip(enty.chr_img, mCanvas, 0, 0, 150, 130, infoX, infoY + 10);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setColor(Color.BLACK);
        paint.setTextSize(20);
        infoX += 20;
        infoY += 140;
        DrawUtil.drawString(mCanvas, enty.memberEnty.name, paint, infoX, infoY);

        paint.setColor(Color.DKGRAY);
        paint.setTextSize(18);
        infoY += 22;
        DrawUtil.drawString(mCanvas, enty.memberEnty.memberclass, paint, infoX, infoY);
        DrawUtil.drawString(mCanvas, Integer.toString(enty.memberEnty.status.LEVEL), paint, infoX + 80, infoY);

        paint.setColor(Color.RED);
        paint.setTextSize(17);
        infoY += 20;
        String _hp = "HP." + enty.memberEnty.status.USE_HP +
                "/" + enty.memberEnty.status.MAX_HP;
        String _mp = "MP." + enty.memberEnty.status.USE_MP +
                "/" + enty.memberEnty.status.MAX_MP;
        DrawUtil.drawString(mCanvas, _hp, paint, infoX, infoY);
        DrawUtil.drawString(mCanvas, _mp, paint, infoX + 70, infoY);
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

        point.x = hexaX * tileW + startX + gameDungeon.mapMarginLeft;
        point.y = hexaY * gameDungeon.mapLayer.mTileHeightOnMap + gameDungeon.mapMarginTop;
        return point;
    }
}
