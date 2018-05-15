package com.epriest.game.guildfantasy.main;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.CanvasGL.util.Scene;
import com.epriest.game.guildfantasy.main.enty.UnitEnty;
import com.epriest.game.guildfantasy.util.DrawUtil;

/**
 * Created by darka on 2018-02-01.
 */

public class Scene_Dungeon extends Scene{
    private Game_Dungeon gameDungeon;

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

        //member draw
        for(UnitEnty unitEnty : gameDungeon.partyUnitList) {
            drawUnit(mCanvas, unitEnty);
        }

        gameDungeon.gameMain.drawStatusTab(mCanvas);
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
