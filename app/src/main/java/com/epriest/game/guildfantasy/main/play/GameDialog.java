package com.epriest.game.guildfantasy.main.play;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;

import com.epriest.game.CanvasGL.graphics.CanvasUtil;
import com.epriest.game.CanvasGL.graphics.GLUtil;
import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.CanvasGL.util.GameUtil;
import com.epriest.game.CanvasGL.util.TouchData;
import com.epriest.game.guildfantasy.main.enty.ButtonEnty;

import static com.epriest.game.CanvasGL.graphics.CanvasUtil.drawClip;

/**
 * Created by darka on 2018-01-16.
 */

public class GameDialog {
    public final static int ALERT_TYPE_NONE = 0;
    public final static int ALERT_TYPE_CURRENT_TURNEND = 1;
    public final static int ALERT_TYPE_NEXT_TURNSTART = 2;
    public final static int ALERT_TYPE_EMPTYGOLD = 3;
    public final static int ALERT_TYPE_GETNEWMEMBER = 4;
    public final static int ALERT_TYPE_MAXMEMBER = 5;
    public final static int ALERT_TYPE_GEMNOTENOUGH = 6;
    public final static int ALERT_TYPE_VIEWMEMBER = 7;

    private onClickListener mListener;

    private ApplicationClass appClass;
    private String title;
    private String text;
    private Bitmap bitmap;
    private int canvasW, canvasH;
    private ButtonEnty alertBtn;
    private ButtonEnty cancelBtn;
    public Bitmap img_alertBox;

    public interface onClickListener {
        void onPositiveClick();

        void onCancelClick();
    }

    public GameDialog(ApplicationClass appClass) {
        this.appClass = appClass;
        init();
    }

    private void init() {
        canvasW = appClass.getGameCanvasWidth();
        canvasH = appClass.getGameCanvasHeight();
        img_alertBox = GLUtil.loadAssetsBitmap(appClass, "main/alertbox.png", null);
        alertBtn = new ButtonEnty();
        alertBtn.name = "alert";
        alertBtn.clipW = 90;
        alertBtn.clipH = 90;
        alertBtn.clipX = 231;
        alertBtn.clipY = 173;
        alertBtn.drawX = canvasW / 2 - alertBtn.clipW - 10;//(canvasW - img_alertBox.getWidth()) / 2 + img_alertBox.getWidth() - 100;
        alertBtn.drawY = (canvasH - img_alertBox.getHeight()) / 2 + img_alertBox.getHeight() - alertBtn.clipH - 30;


        cancelBtn = new ButtonEnty();
        cancelBtn.name = "cancel";
        cancelBtn.clipW = 90;
        cancelBtn.clipH = 90;
        cancelBtn.clipX = 231;
        cancelBtn.clipY = 173;
        cancelBtn.drawX = canvasW / 2 + 10;//(canvasW - img_alertBox.getWidth()) / 2 + img_alertBox.getWidth() - alertBtn.clipW -10;
        cancelBtn.drawY = (canvasH - img_alertBox.getHeight()) / 2 + img_alertBox.getHeight() - cancelBtn.clipH - 30;
    }

    public void dissmiss(){
        CanvasUtil.recycleBitmap(img_alertBox);
    }

    public boolean onTouch() {
        if (GameUtil.equalsTouch(appClass.touch,
                alertBtn.drawX, alertBtn.drawY, alertBtn.clipW, alertBtn.clipH)) {
            if (appClass.touch.action == MotionEvent.ACTION_UP) {
                alertBtn.clickState = ButtonEnty.ButtonClickOff;
                mListener.onPositiveClick();
                return true;
            } else {
                alertBtn.clickState = ButtonEnty.ButtonClickOn;
            }
        } else if (GameUtil.equalsTouch(appClass.touch,
                cancelBtn.drawX, cancelBtn.drawY, cancelBtn.clipW, cancelBtn.clipH)) {
            if (appClass.touch.action == MotionEvent.ACTION_UP) {
                cancelBtn.clickState = ButtonEnty.ButtonClickOff;
                mListener.onCancelClick();
                return true;
            } else {
                cancelBtn.clickState = ButtonEnty.ButtonClickOn;
            }
        } else
            cancelBtn.clickState = ButtonEnty.ButtonClickOn;
        return false;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setOnButtonListener(onClickListener listener) {
        mListener = listener;
    }

    public void draw(Canvas mCanvas, Bitmap img_mainBtn) {
        if(img_alertBox == null)
            return;
        int alertY = (canvasH - img_alertBox.getHeight()) / 2;
        // alert bg
        CanvasUtil.drawBitmap(img_alertBox, mCanvas, (canvasW - img_alertBox.getWidth()) / 2
                , alertY);

        // alert button
        int alertBtnClipX = alertBtn.clipX;
        if (alertBtn.clickState == ButtonEnty.ButtonClickOn) {
            alertBtnClipX += alertBtn.clipW;
        }
        drawClip(img_mainBtn, mCanvas,
                alertBtnClipX, alertBtn.clipY,
                alertBtn.clipW, alertBtn.clipH, alertBtn.drawX, alertBtn.drawY);

        // cancel button
        int cancelBtnClipX = cancelBtn.clipX;
        if (cancelBtn.clickState == ButtonEnty.ButtonClickOn) {
            cancelBtnClipX += cancelBtn.clipW;
        }
        drawClip(img_mainBtn, mCanvas,
                cancelBtnClipX, cancelBtn.clipY,
                cancelBtn.clipW, cancelBtn.clipH, cancelBtn.drawX, cancelBtn.drawY);

        // title
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
        if (title != null) {
            paint.setColor(Color.BLACK);
            paint.setTextSize(30);
            CanvasUtil.drawString(mCanvas, title, paint, canvasW / 2, alertY + 35);
        }

        // text
        if (text != null) {
            paint.setTextAlign(Paint.Align.LEFT);
            paint.setColor(Color.DKGRAY);
            paint.setTextSize(30);
            alertY = img_alertBox.getHeight() / 2 + alertY;
            CanvasUtil.drawString(mCanvas, text, paint, canvasW / 2 - 150, alertY);
        }
    }
}
