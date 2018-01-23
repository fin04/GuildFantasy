package com.epriest.game.guildfantasy.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.CanvasGL.util.Scene;

/**
 * Created by darka on 2018-01-23.
 */

public class Scene_Battle extends Scene{

    private Context context;
    private Game_Battle gameBattle;

    public Scene_Battle(Game_Battle gameBattle, Scene_Main sceneMain) {
        this.gameBattle = gameBattle;
//        this.sceneMain = sceneMain;
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

    }
}
