package com.epriest.game.guildfantasy.main;

import android.view.MotionEvent;

import com.epriest.game.CanvasGL.util.Game;

/**
 * Created by darka on 2018-02-01.
 */

public class Game_Dungeon extends Game{
    public Game_Main gameMain;

    public int canvasW, canvasH;

    public Game_Dungeon(Game_Main gameMain) {
        this.gameMain = gameMain;
    }

    @Override
    public void gStart() {
        this.canvasW = gameMain.appClass.getGameCanvasWidth();
        this.canvasH = gameMain.appClass.getGameCanvasHeight();
    }

    @Override
    public void gStop() {

    }

    @Override
    public void gUpdate() {

    }

    @Override
    public void gOnTouchEvent(MotionEvent event) {
        if (gameMain.onStatusTouch())
            return;
    }
}
