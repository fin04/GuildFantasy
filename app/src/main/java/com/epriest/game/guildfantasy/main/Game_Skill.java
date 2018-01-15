package com.epriest.game.guildfantasy.main;

import android.view.MotionEvent;

import com.epriest.game.CanvasGL.util.Game;

/**
 * Created by darka on 2017-03-26.
 */

public class Game_Skill extends Game {

    public Game_Main gameMain;

    public Game_Skill(Game_Main gameMain){
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
        if(gameMain.onStatusTouch())
            return;
    }
}
