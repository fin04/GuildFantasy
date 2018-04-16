package com.epriest.game.guildfantasy.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;

import com.epriest.game.guildfantasy.main.Game_Main;

/**
 * Created by darka on 2018-02-04.
 */

public class DrawUtil {

    static public boolean recycleBitmap(Bitmap bitmap) {
        if (bitmap == null)
            return false;
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
        paint.setTypeface(Typeface.create(Typeface.SERIF, Typeface.BOLD));
        paint.setColor(color);
        paint.setTextSize(size);
        paint.setTextAlign(align);
        paint.setAntiAlias(true);
        mCanvas.drawText(text, picX, picY + size, paint);
    }

    static public void drawString(Canvas mCanvas, String text, int size, int color, Paint.Align align,
                                  int picX, int picY) {
        Paint paint = new Paint();
        paint.setTypeface(Typeface.create(Typeface.SERIF, Typeface.NORMAL));
        paint.setColor(color);
        paint.setTextSize(size);
        paint.setTextAlign(align);
        paint.setAntiAlias(true);
        mCanvas.drawText(text, picX, picY + size, paint);
    }

    static public void drawString(Canvas mCanvas, String text, Paint paint, int picX, int picY) {
        paint.setTypeface(Typeface.create(Typeface.SERIF, Typeface.NORMAL));
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
        if (bitmap == null)
            return;
        mCanvas.drawBitmap(bitmap, picX, picY, null);
    }

    static public void drawBgBitmap(Bitmap bitmap, Canvas mCanvas) {
        if (bitmap == null)
            return;
        mCanvas.drawBitmap(bitmap, (mCanvas.getWidth() - bitmap.getWidth()) / 2, (mCanvas.getHeight() - bitmap.getHeight()) / 2, null);
    }

    static public void drawScrollBgBitmap(Bitmap bitmap, Canvas mCanvas, int scrollX) {
        if (bitmap == null)
            return;
        mCanvas.drawBitmap(bitmap, (mCanvas.getWidth() - bitmap.getWidth()) / 2 - scrollX, Game_Main.statusBarH - 3, null);
    }

    static public void drawClip(Bitmap bitmap, Canvas mCanvas,
                                int clipX, int clipY, int clipW, int clipH, int drawX, int drawY) {
        if (bitmap == null)
            return;
        if (bitmap.isRecycled())
            return;
//        Paint mPaint = new Paint();
//        mPaint.setAntiAlias(true);
        Rect src = new Rect(clipX, clipY, clipX + clipW, clipY + clipH);
        Rect dst = new Rect(drawX, drawY, drawX + clipW, drawY + clipH);
        mCanvas.drawBitmap(bitmap, src, dst, null);
    }

    static public void drawClipResize(Bitmap bitmap, Canvas mCanvas, int clipX, int clipY, int clipW, int clipH,
                                      int picX, int picY, int picW, int picH) {
        if (bitmap == null)
            return;
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

    /**
     * @param mBitmap
     * @param contrast   : 0 to 10
     * @param brightness : -255 to 255
     * @return
     */
    static public Bitmap enhanceImage(Bitmap mBitmap, float contrast, float brightness) {
        ColorMatrix cm = new ColorMatrix(new float[]
                {
                        contrast, 0, 0, 0, brightness,
                        0, contrast, 0, 0, brightness,
                        0, 0, contrast, 0, brightness,
                        0, 0, 0, 1, 0
                });
        Bitmap mEnhancedBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), mBitmap
                .getConfig());
        Canvas canvas = new Canvas(mEnhancedBitmap);
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        canvas.drawBitmap(mBitmap, 0, 0, paint);
        return mEnhancedBitmap;
    }

    static public Bitmap blur(Context context, Bitmap sentBitmap, int radius) {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

            final RenderScript rs = RenderScript.create(context);
            final Allocation input = Allocation.createFromBitmap(rs, sentBitmap, Allocation.MipmapControl.MIPMAP_NONE,
                    Allocation.USAGE_SCRIPT);
            final Allocation output = Allocation.createTyped(rs, input.getType());
            final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            script.setRadius(radius); //0.0f ~ 25.0f
            script.setInput(input);
            script.forEach(output);
            output.copyTo(bitmap);
            return bitmap;
        }
        return sentBitmap;
    }

    static public Bitmap fastblur(Bitmap sentBitmap, float scale, int radius) {

        int width = Math.round(sentBitmap.getWidth() * scale);
        int height = Math.round(sentBitmap.getHeight() * scale);
        sentBitmap = Bitmap.createScaledBitmap(sentBitmap, width, height, false);

        Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

        if (radius < 1) {
            return (null);
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];
        Log.e("pix", w + " " + h + " " + pix.length);
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int r[] = new int[wh];
        int g[] = new int[wh];
        int b[] = new int[wh];
        int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
        int vmin[] = new int[Math.max(w, h)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int dv[] = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = (i / divsum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum, goutsum, boutsum;
        int rinsum, ginsum, binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;

            for (x = 0; x < w; x++) {

                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[(stackpointer) % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = ( 0xff000000 & pix[yi] ) | ( dv[rsum] << 16 ) | ( dv[gsum] << 8 ) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }

        Log.e("pix", w + " " + h + " " + pix.length);
        bitmap.setPixels(pix, 0, w, 0, 0, w, h);

        return (bitmap);
    }

}
