package com.epriest.game.guildfantasy.main;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.CanvasGL.util.Scene;

/**
 * Created by darka on 2017-08-07.
 */

public class Scene_Guild extends Scene {

    private Game_Guild gameGuild;

    public Scene_Guild(Game_Guild gameGuild, Scene_Main sceneMain) {
        this.gameGuild = gameGuild;
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
