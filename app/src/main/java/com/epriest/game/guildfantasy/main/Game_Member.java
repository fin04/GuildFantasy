package com.epriest.game.guildfantasy.main;

import android.view.MotionEvent;

import com.epriest.game.CanvasGL.util.Game;
import com.epriest.game.guildfantasy.enty.ButtonEnty;

import java.util.ArrayList;

/**
 * Created by darka on 2017-03-26.
 */

public class Game_Member extends Game {

    public Game_Main gameMain;
    public View_Member viewMember;


    public Game_Member(Game_Main gameMain) {
        this.gameMain = gameMain;
        viewMember = new View_Member(gameMain);
    }

    public int scrollY, prevScrollY;

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
        if (gameMain.onTouchEvent(event))
            return;

        viewMember.onTouchEvent(event);


    }
}
