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

import java.util.ArrayList;

/**
 * Created by darka on 2018-01-16.
 */

public class GameDialog {
    public final static int ALERT_TYPE_NONE = 0;
    public final static int ALERT_TYPE_CURRENT_TURNOFF = 1;
    public final static int ALERT_TYPE_NEXT_TURNON = 2;
    public final static int ALERT_TYPE_EMPTYGOLD = 3;
    public final static int ALERT_TYPE_GETNEWMEMBER = 4;
    public final static int ALERT_TYPE_MAXMEMBER = 5;
    public final static int ALERT_TYPE_GEMNOTENOUGH = 6;
    public final static int ALERT_TYPE_VIEWMEMBER = 7;
    public final static int ALERT_TYPE_LIMITEDPARTYMEMBER = 8;
    public final static int ALERT_TYPE_RECRUIT_SUMMON = 11;
    public final static int ALERT_TYPE_RECRUIT_BONDAGE = 12;
    public final static int ALERT_TYPE_RECRUIT_COVENANT = 13;

    private onTwoClickListener mTwoClickListener;
    private onOneClickListener mOneClickListener;

    private ApplicationClass appClass;
    private String title;
    private String text;
    private ArrayList<String> buttonTitleList;
    private Bitmap bitmap;
    private int canvasW, canvasH;
    private ButtonEnty alertBtn;
    private ButtonEnty cancelBtn;
    public Bitmap img_alertBox;

    public interface onTwoClickListener {
        void onPositiveClick();

        void onNegativeClick();
    }

    public interface onOneClickListener {
        void onClick();
    }

    public GameDialog(ApplicationClass appClass) {
        this.appClass = appClass;
        init();
    }

    private void init() {
        canvasW = appClass.getGameCanvasWidth();
        canvasH = appClass.getGameCanvasHeight();
        img_alertBox = GLUtil.loadAssetsBitmap(appClass, "main/alertbox.png", null);

        buttonTitleList = new ArrayList<>();

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

    public void dissmiss() {
        DrawUtil.recycleBitmap(img_alertBox);
        DrawUtil.recycleBitmap(bitmap);
    }

    public boolean onTouch() {
        if (GameUtil.equalsTouch(appClass.touch,
                alertBtn.drawX, alertBtn.drawY, alertBtn.clipW, alertBtn.clipH)) {
            if (appClass.touch.action == MotionEvent.ACTION_UP) {
                alertBtn.clickState = ButtonEnty.ButtonClickOff;
                if (mOneClickListener != null)
                    mOneClickListener.onClick();
                else
                    mTwoClickListener.onPositiveClick();
                return true;
            } else {
                alertBtn.clickState = ButtonEnty.ButtonClickOn;
            }
        } else if (GameUtil.equalsTouch(appClass.touch,
                cancelBtn.drawX, cancelBtn.drawY, cancelBtn.clipW, cancelBtn.clipH)) {
            if (appClass.touch.action == MotionEvent.ACTION_UP) {
                cancelBtn.clickState = ButtonEnty.ButtonClickOff;
                mTwoClickListener.onNegativeClick();
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

    public void setButtonTitle(String title) {
        buttonTitleList.add(title);
    }

    public void setOnButtonListener(onOneClickListener listener) {
        mOneClickListener = listener;
    }

    public void setTwoButtonListener(onTwoClickListener listener) {
        mTwoClickListener = listener;
    }

    public void draw(Canvas mCanvas, Bitmap img_mainBtn) {
        int canvasHalfW = canvasW/2;
        DrawUtil.drawBox(mCanvas, Color.argb(180, 0, 0, 0), true,
                0, 0, appClass.getGameCanvasWidth(), appClass.getGameCanvasHeight());
        if (img_alertBox == null)
            return;
        int alertY = (canvasH - img_alertBox.getHeight()) / 2;
        // alert bg
        DrawUtil.drawBitmap(img_alertBox, mCanvas, (canvasW - img_alertBox.getWidth()) / 2
                , alertY);

        // draw button
        if (mOneClickListener != null) {
            drawOneButton(mCanvas, img_mainBtn);
        } else if (mTwoClickListener != null) {
            drawTwoButton(mCanvas, img_mainBtn);
        }

        // title
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
        if (title != null) {
            paint.setColor(Color.BLACK);
            paint.setTextSize(30);
            DrawUtil.drawString(mCanvas, title, paint, canvasHalfW, alertY + 35);
        }

        // text
        if (text != null) {
            paint.setTextAlign(Paint.Align.LEFT);
            paint.setColor(Color.DKGRAY);
            paint.setTextSize(30);
            alertY = img_alertBox.getHeight() / 2 + alertY;
            DrawUtil.drawString(mCanvas, text, paint, canvasHalfW - 150, alertY);
        }

        //image
        if(bitmap != null){
            DrawUtil.drawBitmap(bitmap, mCanvas, canvasHalfW + 15, alertY + 105);
        }
    }

    private void drawOneButton(Canvas mCanvas, Bitmap img_mainBtn) {
        // alert button
        int alertBtnClipX = alertBtn.clipX;
        if (alertBtn.clickState == ButtonEnty.ButtonClickOn) {
            alertBtnClipX += alertBtn.clipW;
        }

        DrawUtil.drawClip(img_mainBtn, mCanvas,
                alertBtnClipX, alertBtn.clipY,
                alertBtn.clipW, alertBtn.clipH, alertBtn.drawX, alertBtn.drawY);

        if (!buttonTitleList.isEmpty()) {
            DrawUtil.drawBoldString(mCanvas, buttonTitleList.get(0), 20,
                    Color.argb(255, 255, 255, 255),
                    Paint.Align.CENTER, alertBtn.drawX + alertBtn.drawW / 2, alertBtn.drawY);
        }
    }

    private void drawTwoButton(Canvas mCanvas, Bitmap img_mainBtn) {
        // alert button
        int alertBtnClipX = alertBtn.clipX;
        if (alertBtn.clickState == ButtonEnty.ButtonClickOn) {
            alertBtnClipX += alertBtn.clipW;
        }
        DrawUtil.drawClip(img_mainBtn, mCanvas,
                alertBtnClipX, alertBtn.clipY,
                alertBtn.clipW, alertBtn.clipH, alertBtn.drawX, alertBtn.drawY);

        int fontSize = 25;
        if (!buttonTitleList.isEmpty()) {
            DrawUtil.drawBoldString(mCanvas, buttonTitleList.get(0), fontSize,
                    Color.argb(255, 255, 255, 255), Paint.Align.CENTER,
                    alertBtn.drawX + alertBtn.clipW / 2, alertBtn.drawY + alertBtn.clipH / 3);
        }

        // cancel button
        int cancelBtnClipX = cancelBtn.clipX;
        if (cancelBtn.clickState == ButtonEnty.ButtonClickOn) {
            cancelBtnClipX += cancelBtn.clipW;
        }
        DrawUtil.drawClip(img_mainBtn, mCanvas,
                cancelBtnClipX, cancelBtn.clipY,
                cancelBtn.clipW, cancelBtn.clipH, cancelBtn.drawX, cancelBtn.drawY);

        if (buttonTitleList.size() > 1) {
            DrawUtil.drawBoldString(mCanvas, buttonTitleList.get(1), fontSize,
                    Color.argb(255, 255, 255, 255), Paint.Align.CENTER,
                    cancelBtn.drawX + cancelBtn.clipW / 2, cancelBtn.drawY + cancelBtn.clipH / 3);
        }
    }
}
