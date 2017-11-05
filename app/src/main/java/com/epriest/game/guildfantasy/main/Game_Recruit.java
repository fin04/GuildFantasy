package com.epriest.game.guildfantasy.main;

import android.graphics.Bitmap;
import android.view.MotionEvent;

import com.epriest.game.CanvasGL.graphics.GLUtil;
import com.epriest.game.CanvasGL.util.Game;

/**
 * Created by darka on 2017-11-06.
 */

public class Game_Recruit extends Game {

    public Bitmap img_recruitBtn;

    public Game_Main gameMain;

    public Game_Recruit(Game_Main gameMain) {
        this.gameMain = gameMain;
    }

    @Override
    public void gStart() {
        img_recruitBtn = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/recruit_btn.png", null);
    }

    @Override
    public void gStop() {

    }

    @Override
    public void gUpdate() {

    }

    @Override
    public void gOnTouchEvent(MotionEvent event) {

    }
}
