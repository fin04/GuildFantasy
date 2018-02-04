package com.epriest.game.guildfantasy.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.epriest.game.CanvasGL.graphics.CanvasUtil;
import com.epriest.game.guildfantasy.main.Game_Main;

/**
 * Created by darka on 2018-02-04.
 */

public class DrawUtil{
    static public void drawBgBitmap(Bitmap bitmap, Canvas mCanvas, int scrollX) {
        mCanvas.drawBitmap(bitmap, (mCanvas.getWidth()-bitmap.getWidth())/2-scrollX, Game_Main.statusBarH-3, null);
    }
}
