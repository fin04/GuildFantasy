package com.epriest.game.guildfantasy.main;

import android.view.MotionEvent;

import com.epriest.game.CanvasGL.util.Game;

/**
 * Created by darka on 2017-03-26.
 */

public class Game_Town extends Game {

    private Game_Main gameMain;

    public Game_Town(Game_Main gameMain){
        this.gameMain = gameMain;
    }

    @Override
    public void gStart() {

    }

    @Override
    public void gStop() {

    }

    @Override
    public void gUpdate() {

    }

    @Override
    public void gOnTouchEvent(MotionEvent event) {
        gameMain.onTouchEvent(event);
    }
}
