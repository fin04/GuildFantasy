package com.epriest.game.guildfantasy.main;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.epriest.game.CanvasGL.graphics.CanvasUtil;
import com.epriest.game.CanvasGL.graphics.GLUtil;
import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.CanvasGL.util.Scene;
import com.epriest.game.guildfantasy.enty.ImageEnty;

/**
 * Created by darka on 2017-03-26.
 */

public class Scene_Member extends Scene {

    private Game_Member gameMember;
    private Scene_Main sceneMain;
    private int canvasW, canvasH;

    private Bitmap bg;
    private Bitmap char_01;
//    private Bitmap menu_icon;

    private ImageEnty managerImg;

    public Scene_Member(Game_Member gameMember, Scene_Main sceneMain) {
        this.gameMember = gameMember;
        this.sceneMain = sceneMain;
    }

    @Override
    public void initScene(ApplicationClass appClass) {
        canvasW = appClass.getGameCanvasWidth();
        canvasH = appClass.getGameCanvasHeight();

        bg = GLUtil.loadAssetsBitmap(appClass, "main/member.png", null);
        char_01 = GLUtil.loadAssetsBitmap(appClass, "main/char_01.jpg", null);
    }

    @Override
    public void recycleScene() {
        CanvasUtil.recycleBitmap(bg);
        CanvasUtil.recycleBitmap(char_01);
    }

    @Override
    public void drawLoading(Canvas mCanvas, Bitmap loading) {

    }

    @Override
    public void draw(Canvas mCanvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
//        mCanvas.drawColor(Color.BLACK);

        mCanvas.drawBitmap(bg, 0, 0, null);

        //manager mode
//        drawManager(mCanvas, paint);

        sceneMain.drawMain(mCanvas, paint);
    }
}
