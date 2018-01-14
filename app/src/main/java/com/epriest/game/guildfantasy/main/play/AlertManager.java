package com.epriest.game.guildfantasy.main.play;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.epriest.game.CanvasGL.graphics.CanvasUtil;
import com.epriest.game.CanvasGL.graphics.GLUtil;
import com.epriest.game.CanvasGL.util.GameUtil;
import com.epriest.game.guildfantasy.main.Game_Main;
import com.epriest.game.guildfantasy.main.enty.ButtonEnty;
import com.epriest.game.guildfantasy.main.enty.MemberEnty;
import com.epriest.game.guildfantasy.util.INN;

import static com.epriest.game.CanvasGL.graphics.CanvasUtil.drawClip;

/**
 * Created by darka on 2017-12-29.
 */

public class AlertManager {
    public final static int ALERT_TYPE_NONE = 0;
    public final static int ALERT_TYPE_TURNSTART = 1;
    public final static int ALERT_TYPE_EMPTYGOLD = 2;
    public final static int ALERT_TYPE_GETNEWMEMBER = 3;
    public final static int ALERT_TYPE_MAXMEMBER = 4;
    public final static int ALERT_TYPE_GEMNOTENOUGH = 5;
    public final static int ALERT_TYPE_VIEWMEMBER = 6;

    private Game_Main game_main;

    public int showAlertType;
    public ButtonEnty alertBtn;
    public Bitmap img_alertBox;

    public int canvasW, canvasH;

    public AlertManager(Game_Main game_main) {
        this.game_main = game_main;
    }

    public void init(){
        canvasW = game_main.canvasW;
        canvasH = game_main.canvasH;
        img_alertBox = GLUtil.loadAssetsBitmap(game_main.appClass, "main/alertbox.png", null);
        alertBtn = new ButtonEnty();
        alertBtn.name = "alert";
        alertBtn.drawX = (canvasW - img_alertBox.getWidth()) / 2 + img_alertBox.getWidth() - 100;
        alertBtn.drawY = (canvasH - img_alertBox.getHeight()) / 2 + img_alertBox.getHeight() - 100;
        alertBtn.clipW = 90;
        alertBtn.clipH = 90;
        alertBtn.clipX = 231;
        alertBtn.clipY = 173;
    }

    public void recycleScene() {
        CanvasUtil.recycleBitmap(img_alertBox);
    }

    public boolean onAlertTouch() {
        if (showAlertType == AlertManager.ALERT_TYPE_NONE)
            return false;
        if (GameUtil.equalsTouch(game_main.appClass.touch,
                alertBtn.drawX, alertBtn.drawY, alertBtn.clipW, alertBtn.clipH)) {
            if (game_main.appClass.touch.action == MotionEvent.ACTION_UP) {
                alertBtn.clickState = ButtonEnty.ButtonClickOff;
                switch (showAlertType) {
                    case AlertManager.ALERT_TYPE_TURNSTART:
                        game_main.userEnty.GOLD += game_main.userEnty.eventEnty.Gold;
                        break;
                }
                showAlertType = AlertManager.ALERT_TYPE_NONE;
                return true;
            } else {
                alertBtn.clickState = ButtonEnty.ButtonClickOn;
            }
        } else
            alertBtn.clickState = ButtonEnty.ButtonClickOn;
        return false;
    }

//    public boolean onTurnAlertTouch() {
//        if (showAlertType == AlertManager.ALERT_TYPE_NONE)
//            return false;
//        int turnBtnNum = game_main.menuButtonList.size() - 1;
//        if (GameUtil.equalsTouch(game_main.appClass.touch,
//                game_main.menuButtonList.get(turnBtnNum).drawX, game_main.menuButtonList.get(turnBtnNum).drawY,
//                game_main.menuButtonList.get(turnBtnNum).clipW, game_main.menuButtonList.get(turnBtnNum).clipH)) {
//            if (game_main.appClass.touch.action == MotionEvent.ACTION_UP) {
//                game_main.menuButtonList.get(turnBtnNum).clickState = ButtonEnty.ButtonClickOff;
//                switch (game_main.appClass.gameState) {
//                    case INN.GAME_HOME:
//                        game_main.turnManager.turnCycle(game_main.userEnty.TURN++);
//                        break;
//                }
//                showAlertType = AlertManager.ALERT_TYPE_NONE;
//                return true;
//            } else {
//                game_main.menuButtonList.get(turnBtnNum).clickState = ButtonEnty.ButtonClickOn;
//            }
//        } else {
//            game_main.menuButtonList.get(turnBtnNum).clickState = ButtonEnty.ButtonClickOff;
//        }
//        return false;
//    }


    public void drawAlert(Canvas mCanvas, String title, String text) {
        int alertY = (canvasH - img_alertBox.getHeight()) / 2;
        // alert bg
        CanvasUtil.drawBitmap(img_alertBox, mCanvas, (canvasW - img_alertBox.getWidth()) / 2
                , alertY);

        // alert button
        int alertBtnClipX = alertBtn.clipX;
        if (alertBtn.clickState == ButtonEnty.ButtonClickOn) {
            alertBtnClipX += alertBtn.clipW;
        }
        drawClip(game_main.img_mainBtn, mCanvas,
                alertBtnClipX, alertBtn.clipY,
                alertBtn.clipW, alertBtn.clipH, alertBtn.drawX, alertBtn.drawY);

        // title
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(Color.BLACK);
        paint.setTextSize(30);
        CanvasUtil.drawString(mCanvas, title, paint, canvasW / 2, alertY + 20);

        // text
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setColor(Color.DKGRAY);
        paint.setTextSize(25);
        CanvasUtil.drawString(mCanvas, text, paint, canvasW / 2 - 150, alertY + 70);
    }

    public void drawTurnStartAlert(Canvas mCanvas) {
        StringBuilder sb = new StringBuilder("Turn : ");
        sb.append(game_main.userEnty.TURN);
        sb.append("\n");
        sb.append("Clear Quest : ");
        sb.append("\n");
        sb.append("Income : ");
        sb.append(game_main.userEnty.eventEnty.Gold + "Gold");
        sb.append("\n");
        sb.append("New Quest : ");
        sb.append(game_main.userEnty.eventEnty.QuestIDList.size());

        drawAlert(mCanvas, game_main.userEnty.TURN + " Turn", sb.toString());
    }

    public void drawMemberAlert(Canvas mCanvas, Bitmap profileImg, MemberEnty enty) {
        int alertY = (canvasH - img_alertBox.getHeight()) / 2;
        int alertX = (canvasW - img_alertBox.getWidth()) / 2;
        int halfCanvasW = canvasW / 2;

        // alert bg
        CanvasUtil.drawBitmap(img_alertBox, mCanvas, alertX, alertY);

        //profile image
        CanvasUtil.drawBitmap(profileImg, mCanvas, alertX + 15, alertY + 105);
        if (INN.setTempImg)
            CanvasUtil.drawBox(mCanvas, Color.DKGRAY, true, alertX + 15, alertY + 105, 400, 512);

        // class mark
        int classId = 0;
        if (enty.memberclass.equals("knight"))
            classId = 1;
        else if (enty.memberclass.equals("warrior"))
            classId = 2;
        else if (enty.memberclass.equals("priest"))
            classId = 3;
        else if (enty.memberclass.equals("mage"))
            classId = 4;
        else if (enty.memberclass.equals("hunter"))
            classId = 5;
        else if (enty.memberclass.equals("rogue"))
            classId = 6;
        int clipX = classId % 4 * 64;
        int clipY = classId / 4 * 64;
        CanvasUtil.drawClip(game_main.img_classMark, mCanvas, clipX, clipY,
                64, 64, alertX + 10, alertY + 105);

        // alert button
        int alertBtnClipX = alertBtn.clipX;
        if (alertBtn.clickState == ButtonEnty.ButtonClickOn) {
            alertBtnClipX += alertBtn.clipW;
        }
        drawClip(game_main.img_mainBtn, mCanvas,
                alertBtnClipX, alertBtn.clipY,
                alertBtn.clipW, alertBtn.clipH, alertBtn.drawX, alertBtn.drawY);

        // title
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(Color.WHITE);
        paint.setTextSize(35);
        CanvasUtil.drawString(mCanvas, enty.name, paint, halfCanvasW, alertY + 30);

        // exp
        game_main.drawBarGage(mCanvas, -1, Color.argb(255, 0, 200, 50),
                "", enty.status.EXP, enty.status.MAX_EXP,
                alertX + 5, alertY + 100, img_alertBox.getWidth() - 10, 5);

        // status_basic
        StringBuilder sb = new StringBuilder(enty.race + "(" + enty.sex + ")\n");
        sb.append("age  : " + enty.age);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setColor(Color.argb(255, 80, 80, 80));
        paint.setTextSize(25);
        CanvasUtil.drawString(mCanvas, sb.toString(), paint,
                halfCanvasW, alertY + 110);

        // status_detail
        game_main.drawBarGage(mCanvas, -1, Color.argb(255, 250, 90, 60),
                "HP", enty.status.MAX_HP - enty.status.USE_HP, enty.status.MAX_HP,
                halfCanvasW, alertY + 180, 200, 12);
        game_main.drawBarGage(mCanvas, -1, Color.argb(255, 60, 90, 250),
                "MP", enty.status.MAX_MP - enty.status.USE_MP, enty.status.MAX_MP,
                halfCanvasW, alertY + 200, 200, 12);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setColor(Color.BLACK);
        paint.setTextSize(25);
        CanvasUtil.drawString(mCanvas, "STR : " + enty.status.STR, paint,
                halfCanvasW, alertY + 230);
        CanvasUtil.drawString(mCanvas, "DEX : " + enty.status.DEX, paint,
                halfCanvasW + 100, alertY + 230);
        CanvasUtil.drawString(mCanvas, "INT : " + enty.status.INT, paint,
                halfCanvasW, alertY + 255);
        CanvasUtil.drawString(mCanvas, "VIT : " + enty.status.VIT, paint,
                halfCanvasW + 100, alertY + 255);

        // profile text
        sb = new StringBuilder();
        int startNum = 0;
        for (int i = 0; i < enty.profile.length(); i++) {
            String tempStr = enty.profile.substring(startNum, i);
            if (tempStr.length() > 20) {
                sb.append(tempStr + "\n");
                startNum = i;
            } else if (i == tempStr.length() - 1) {
                sb.append(tempStr);
            }
        }
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setColor(Color.BLACK);
        paint.setTextSize(25);
        CanvasUtil.drawString(mCanvas, sb.toString(), paint,
                halfCanvasW - 200, alertY + 300);

    }
}
