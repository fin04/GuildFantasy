package com.epriest.game.guildfantasy.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import com.epriest.game.guildfantasy.main.Game_Main;

/**
 * Created by darka on 2018-02-04.
 */

public class DrawUtil {

    static public boolean recycleBitmap(Bitmap bitmap) {
        try {
            bitmap.recycle();
            bitmap = null;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    static public void drawBoldString(Canvas mCanvas, String text, int size, int color, Paint.Align align,
                                      int picX, int picY) {
        Paint paint = new Paint();
//        paint.setStyle(Paint.Style.FILL);
        paint.setTypeface(Typeface.create(Typeface.MONOSPACE, Typeface.BOLD));
        paint.setColor(color);
        paint.setTextSize(size);
        paint.setTextAlign(align);
        paint.setAntiAlias(true);
        mCanvas.drawText(text, picX, picY + size, paint);
    }

    static public void drawString(Canvas mCanvas, String text, int size, int color, Paint.Align align,
                                  int picX, int picY) {
        Paint paint = new Paint();
        paint.setTypeface(Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL));
        paint.setColor(color);
        paint.setTextSize(size);
        paint.setTextAlign(align);
        paint.setAntiAlias(true);
        mCanvas.drawText(text, picX, picY + size, paint);
    }

    static public void drawString(Canvas mCanvas, String text, Paint paint, int picX, int picY) {
        paint.setTypeface(Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL));
        String[] strArr = text.split("\n");
        float textSize = paint.getTextSize();
        for (int i = 0; i < strArr.length; i++) {
            mCanvas.drawText(strArr[i], picX, (i + 1) * textSize + (textSize / 4 * i) + picY, paint);
        }
    }

    static public void drawBox(Canvas mCanvas, int color, boolean isFill, int left, int top, int boxW, int boxH) {
        Paint paint = new Paint();
        paint.setColor(color);
        if (isFill)
            paint.setStyle(Paint.Style.FILL);
        else
            paint.setStyle(Paint.Style.STROKE);
        mCanvas.drawRect(left, top, boxW + left, boxH + top, paint);

    }

    static public void drawBitmap(Bitmap bitmap, Canvas mCanvas, int picX, int picY) {
        mCanvas.drawBitmap(bitmap, picX, picY, null);
    }

    static public void drawBgBitmap(Bitmap bitmap, Canvas mCanvas) {
        mCanvas.drawBitmap(bitmap, (mCanvas.getWidth()-bitmap.getWidth())/2, (mCanvas.getHeight()-bitmap.getHeight())/2, null);
    }

    static public void drawScrollBgBitmap(Bitmap bitmap, Canvas mCanvas, int scrollX) {
        mCanvas.drawBitmap(bitmap, (mCanvas.getWidth() - bitmap.getWidth()) / 2 - scrollX, Game_Main.statusBarH - 3, null);
    }

    static public void drawClip(Bitmap bitmap, Canvas mCanvas,
                                int clipX, int clipY, int clipW, int clipH, int drawX, int drawY) {
//        Paint mPaint = new Paint();
//        mPaint.setAntiAlias(true);
        Rect src = new Rect(clipX, clipY, clipX + clipW, clipY + clipH);
        Rect dst = new Rect(drawX, drawY, drawX + clipW, drawY + clipH);
        mCanvas.drawBitmap(bitmap, src, dst, null);
    }

    static public void drawClipResize(Bitmap bitmap, Canvas mCanvas, int clipX, int clipY, int clipW, int clipH,
                                      int picX, int picY, int picW, int picH) {
        Rect src = new Rect(clipX, clipY, clipX + clipW, clipY + clipH);
        Rect dst = new Rect(picX, picY, picX + picW, picY + picH);
        mCanvas.drawBitmap(bitmap, src, dst, null);
    }

    static public Bitmap rotateBitmap(Bitmap bitmap, int angle) {
        Matrix matrix = new Matrix();
        matrix.setRotate(angle,
                (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);

        return Bitmap.createBitmap(bitmap,
                0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
}
