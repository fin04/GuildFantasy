package com.epriest.game.guildfantasy.main;

import android.view.MotionEvent;

import com.epriest.game.CanvasGL.util.Game;
import com.epriest.game.guildfantasy.util.INN;

/**
 * Created by darka on 2017-05-05.
 */

public class Game_Event extends Game {

    public Game_Main gameMain;

    public Game_Event(Game_Main gameMain) {
        this.gameMain = gameMain;
    }

    @Override
    public void gStart() {
        gameMain.userEnty.eventEnty.changeView = true;
        gameMain.userEnty.eventEnty.currentViewNum = 0;
    }

    @Override
    public void gStop() {

    }

    @Override
    public void gUpdate() {

    }

    @Override
    public void gOnTouchEvent(MotionEvent event) {
        if (gameMain.onTouchEvent(event))
            return;
        if (event.getAction() == MotionEvent.ACTION_UP && gameMain.userEnty.eventEnty.changeView == false) {
            if (gameMain.userEnty.eventEnty.currentViewNum < gameMain.userEnty.eventEnty.ImageList.size()-1) {
                gameMain.userEnty.eventEnty.changeView = true;
                gameMain.userEnty.eventEnty.currentViewNum++;
            }else{
                gameMain.mainButtonAct(INN.GAME_HOME, 0);
            }

        }
    }
}
