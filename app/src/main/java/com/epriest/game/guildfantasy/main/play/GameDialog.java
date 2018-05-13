package com.epriest.game.guildfantasy.main.play;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.epriest.game.CanvasGL.graphics.GLUtil;
import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.CanvasGL.util.GameUtil;
import com.epriest.game.guildfantasy.main.enty.ButtonEnty;
import com.epriest.game.guildfantasy.util.DrawUtil;

/**
 * Created by darka on 2018-01-16.
 */

public class GameDialog {
//    public final static int ALERT_TYPE_NONE = 0;
//    public final static int ALERT_TYPE_CURRENT_TURNOFF = 1;
//    public final static int ALERT_TYPE_NEXT_TURNON = 2;
//    public final static int ALERT_TYPE_EMPTYGOLD = 3;
//    public final static int ALERT_TYPE_GETNEWMEMBER = 4;
//    public final static int ALERT_TYPE_MAXMEMBER = 5;
//    public final static int ALERT_TYPE_GEMNOTENOUGH = 6;
//    public final static int ALERT_TYPE_VIEWMEMBER = 7;
//    public final static int ALERT_TYPE_LIMITEDPARTYMEMBER = 8;
//    public final static int ALERT_TYPE_RECRUIT_SUMMON = 11;
//    public final static int ALERT_TYPE_RECRUIT_BONDAGE = 12;
//    public final static int ALERT_TYPE_RECRUIT_COVENANT = 13;

    private onNegativeButtonListener mNegativeButtonListener;
    private onPositiveButtonListener mPositiveButtonListener;

    public boolean isDialogShow = false;

    private ApplicationClass appClass;
    private String title;
    private String text;
    private Bitmap bitmap;
    private int canvasW, canvasH;
    private ButtonEnty positiveBtn;
    private ButtonEnty negativeBtn;
    public Bitmap img_alertBox;

    /**
     * alert box의 height
     */
    private int img_alertBoxH = 430;
    /**
     * onebutton에서 쓰이는 drawX
     */
    private int singleBtnX;

    public interface onPositiveButtonListener {
        void onClick();
    }

    public interface onNegativeButtonListener {
        void onClick();
    }

    public GameDialog(ApplicationClass appClass) {
        this.appClass = appClass;
        init();
    }

    private void init() {
        canvasW = appClass.getGameCanvasWidth();
        canvasH = appClass.getGameCanvasHeight();
        img_alertBox = GLUtil.loadAssetsBitmap(appClass, "main/alert1.png", null);

        positiveBtn = new ButtonEnty();
        positiveBtn.clipW = 130;
        positiveBtn.clipH = 70;
        positiveBtn.clipX = 0;
        positiveBtn.clipY = 431;
        positiveBtn.drawX = canvasW / 2 - positiveBtn.clipW - 10;//(canvasW - img_alertBox.getWidth()) / 2 + img_alertBox.getWidth() - 100;
        positiveBtn.drawY = (canvasH - img_alertBox.getHeight()) / 2 + img_alertBoxH - positiveBtn.clipH - 30;

        singleBtnX = (canvasW - positiveBtn.clipW) / 2;

        negativeBtn = new ButtonEnty();
        negativeBtn.clipW = 130;
        negativeBtn.clipH = 70;
        negativeBtn.clipX = 0;
        negativeBtn.clipY = 431;
        negativeBtn.drawX = canvasW / 2 + 10;//(canvasW - img_alertBox.getWidth()) / 2 + img_alertBox.getWidth() - positiveBtn.clipW -10;
        negativeBtn.drawY = (canvasH - img_alertBox.getHeight()) / 2 + img_alertBoxH - negativeBtn.clipH - 30;
    }

    public void dissmiss() {
        DrawUtil.recycleBitmap(img_alertBox);
        DrawUtil.recycleBitmap(bitmap);
    }

    public boolean onTouch() {
        if (mPositiveButtonListener != null && mNegativeButtonListener == null) {
            if (GameUtil.equalsTouch(appClass.touch,
                    singleBtnX, positiveBtn.drawY, positiveBtn.clipW, positiveBtn.clipH)) {
                if (appClass.touch.action == MotionEvent.ACTION_UP) {
                    positiveBtn.clickState = ButtonEnty.ButtonClickOff;
                    mPositiveButtonListener.onClick();
                    close();
                    return true;
                } else {
                    positiveBtn.clickState = ButtonEnty.ButtonClickOn;
                }
                return true;
            }
        } else if (mPositiveButtonListener == null && mNegativeButtonListener != null) {
            if (GameUtil.equalsTouch(appClass.touch,
                    singleBtnX, positiveBtn.drawY, positiveBtn.clipW, positiveBtn.clipH)) {
                if (appClass.touch.action == MotionEvent.ACTION_UP) {
                    negativeBtn.clickState = ButtonEnty.ButtonClickOff;
                    mNegativeButtonListener.onClick();
                    close();
                    return true;
                } else {
                    negativeBtn.clickState = ButtonEnty.ButtonClickOn;
                }
                return true;
            }
        } else if ((mPositiveButtonListener != null && mNegativeButtonListener != null)) {
            if (GameUtil.equalsTouch(appClass.touch,
                    positiveBtn.drawX, positiveBtn.drawY, positiveBtn.clipW, positiveBtn.clipH)) {
                if (appClass.touch.action == MotionEvent.ACTION_UP) {
                    positiveBtn.clickState = ButtonEnty.ButtonClickOff;
                    mPositiveButtonListener.onClick();
                    close();
                    return true;
                } else {
                    positiveBtn.clickState = ButtonEnty.ButtonClickOn;
                }
            } else if (GameUtil.equalsTouch(appClass.touch,
                    negativeBtn.drawX, negativeBtn.drawY, negativeBtn.clipW, negativeBtn.clipH)) {
                if (appClass.touch.action == MotionEvent.ACTION_UP) {
                    negativeBtn.clickState = ButtonEnty.ButtonClickOff;
                    mNegativeButtonListener.onClick();
                    close();
                    return true;
                } else {
                    negativeBtn.clickState = ButtonEnty.ButtonClickOn;
                }
            } else {
                positiveBtn.clickState = ButtonEnty.ButtonClickOff;
                negativeBtn.clickState = ButtonEnty.ButtonClickOff;
            }
        }
        return false;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setImage(Bitmap bitmap) {
        if(bitmap != null)
            this.bitmap = bitmap;
    }

    public void setPositiveBtnTitle(String title) {
        this.positiveBtn.name = title;
    }

    public void setNegativeBtnTitle(String title) {
        this.negativeBtn.name = title;
    }

    public void setOnNegativeButtonListener(onNegativeButtonListener listener) {
        mNegativeButtonListener = listener;
    }

    public void setPositiveButtonListener(onPositiveButtonListener listener) {
        mPositiveButtonListener = listener;
    }

    public void show() {
        isDialogShow = true;
    }

    public boolean getShow() {
        return isDialogShow;
    }

    public void close(){
        DrawUtil.recycleBitmap(bitmap);
        title = null;
        text = null;
        mNegativeButtonListener = null;
        mPositiveButtonListener = null;

        isDialogShow = false;
    }

    public void draw(Canvas mCanvas) {
        int canvasHalfW = canvasW / 2;
        DrawUtil.drawBox(mCanvas, Color.argb(160, 0, 0, 0), true,
                0, 0, appClass.getGameCanvasWidth(), appClass.getGameCanvasHeight());
        if (img_alertBox == null)
            return;
        int alertY = (canvasH - img_alertBox.getHeight()) / 2;
        // alert bg
        DrawUtil.drawClip(img_alertBox, mCanvas, 0, 0, img_alertBox.getWidth(), img_alertBoxH
                , (canvasW - img_alertBox.getWidth()) / 2, alertY);

        if (mPositiveButtonListener != null && mNegativeButtonListener == null) {
            drawSingleButton(mCanvas, positiveBtn);
        } else if (mPositiveButtonListener == null && mNegativeButtonListener != null) {
            drawSingleButton(mCanvas, negativeBtn);
        } else if ((mPositiveButtonListener != null && mNegativeButtonListener != null)) {
            drawTwinButton(mCanvas, positiveBtn, negativeBtn);
        }

        // title
        if (title != null) {
            DrawUtil.drawBoldString(mCanvas, title, 30, Color.WHITE, Paint.Align.CENTER,
                    canvasHalfW, alertY + 35);
        }

        // text
        if (text != null) {
            DrawUtil.drawString(mCanvas, text, 25, Color.WHITE, Paint.Align.LEFT,
                    canvasHalfW - 150, alertY + img_alertBoxH / 2);
        }

        //image
        if (bitmap == null || bitmap.isRecycled()) {}else{
            DrawUtil.drawBitmap(bitmap, mCanvas, canvasHalfW + 15, alertY + 105);
        }
    }

    private void drawSingleButton(Canvas mCanvas, ButtonEnty mBtn) {
        int mBtnClipX = mBtn.clipX;
        if (mBtn.clickState == ButtonEnty.ButtonClickOn) {
            mBtnClipX += mBtn.clipW;
        }

        DrawUtil.drawClip(img_alertBox, mCanvas,
                mBtnClipX, mBtn.clipY,
                mBtn.clipW, mBtn.clipH, singleBtnX, mBtn.drawY);

        DrawUtil.drawBoldString(mCanvas, mBtn.name, 25,
                Color.argb(255, 255, 255, 255), Paint.Align.CENTER,
                singleBtnX + mBtn.clipW / 2, mBtn.drawY + mBtn.clipH / 3);
    }

    private void drawTwinButton(Canvas mCanvas, ButtonEnty mBtn1, ButtonEnty mBtn2) {
        // positive button
        int positiveBtnClipX = mBtn1.clipX;
        if (positiveBtn.clickState == ButtonEnty.ButtonClickOn) {
            positiveBtnClipX += mBtn1.clipW;
        }

        DrawUtil.drawClip(img_alertBox, mCanvas,
                positiveBtnClipX, mBtn1.clipY,
                mBtn1.clipW, mBtn1.clipH, mBtn1.drawX, mBtn1.drawY);

        DrawUtil.drawBoldString(mCanvas, mBtn1.name, 25,
                Color.argb(255, 255, 255, 255), Paint.Align.CENTER,
                mBtn1.drawX + mBtn1.clipW / 2, mBtn1.drawY + mBtn1.clipH / 3);


        // negative button
        int negativeBtnClipX = mBtn2.clipX;
        if (negativeBtn.clickState == ButtonEnty.ButtonClickOn) {
            negativeBtnClipX += mBtn2.clipW;
        }
        DrawUtil.drawClip(img_alertBox, mCanvas,
                negativeBtnClipX, mBtn2.clipY,
                mBtn2.clipW, mBtn2.clipH, mBtn2.drawX, mBtn2.drawY);


        DrawUtil.drawBoldString(mCanvas, mBtn2.name, 25,
                Color.argb(255, 255, 255, 255), Paint.Align.CENTER,
                mBtn2.drawX + mBtn2.clipW / 2, mBtn2.drawY + mBtn2.clipH / 3);

    }
}
