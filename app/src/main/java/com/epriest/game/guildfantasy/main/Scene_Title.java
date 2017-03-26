package com.epriest.game.guildfantasy.main;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.epriest.game.CanvasGL.graphics.CanvasUtil;
import com.epriest.game.CanvasGL.graphics.GLUtil;
import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.CanvasGL.util.Scene;

/**
 * Created by darka on 2016-11-03.
 */

public class Scene_Title extends Scene {
    private ApplicationClass appClass;
    private Bitmap intro_bg;

    private int flag;


    public Scene_Title() {
    }

    @Override
    public void initScene(ApplicationClass appClass) {
        this.appClass = appClass;
        intro_bg = GLUtil.loadAssetsBitmap(appClass, "title.png", null);
    }

    @Override
    public void recycleScene() {
        CanvasUtil.recycleBitmap(intro_bg);
    }

    @Override
    public void drawLoading(Canvas mCanvas, Bitmap loading) {

    }

    String str = "터치하고 게임 시작하기";
    int cnt = 0;
    @Override
    public void draw(Canvas mCanvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        mCanvas.drawColor(Color.MAGENTA);
		mCanvas.drawBitmap(intro_bg, 0, 0, null);
        if(cnt < 30){
            CanvasUtil.drawString(mCanvas, str, 30, paint, Color.GREEN,
                    Paint.Align.CENTER, appClass.getGameCanvasWidth()/2, appClass.getGameCanvasHeight()-100);
        }else{
            if(cnt == 50){
                cnt = 0;
            }
        }
        cnt++;
    }
}
