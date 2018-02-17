package com.epriest.game.guildfantasy.main;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.CanvasGL.util.Scene;

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
        gameDungeon.gameMain.drawStatusTab(mCanvas);
    }
}
