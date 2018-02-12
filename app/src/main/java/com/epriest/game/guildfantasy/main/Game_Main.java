package com.epriest.game.guildfantasy.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.epriest.game.CanvasGL.graphics.GLUtil;
import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.CanvasGL.util.GameUtil;
import com.epriest.game.guildfantasy.main.enty.ButtonEnty;
import com.epriest.game.guildfantasy.main.enty.ClipImageEnty;
import com.epriest.game.guildfantasy.main.enty.ImageEnty;
import com.epriest.game.guildfantasy.main.enty.UserEnty;
import com.epriest.game.guildfantasy.main.play.GameDbAdapter;
import com.epriest.game.guildfantasy.main.play.TurnManager;
import com.epriest.game.guildfantasy.util.DrawUtil;
import com.epriest.game.guildfantasy.util.INN;
import com.epriest.game.guildfantasy.util.PPreference;

import java.util.ArrayList;

/**
 * Created by darka on 2017-03-26.
 */

public class Game_Main {

    public ApplicationClass appClass;
    public TurnManager turnManager;
    public GameDbAdapter dbAdapter;
//    public Scene_Main sceneMain;

    public UserEnty userEnty;

    public ImageEnty managerImg;

    public Bitmap img_mainBtn;
    public Bitmap img_statusBar;
    public Bitmap img_classMark;
    public Bitmap img_defBtn;

    public ButtonEnty optionBtn;
    public ButtonEnty menIcon;
    public ButtonEnty feedIcon;
    public ButtonEnty goldIcon;

    public int showAlertType;

    public String selectQuestId;

    public final int statusBarW = 120;
    static public final int statusBarH = 75;

    public int canvasW, canvasH;

    /**
     * partyNumber.cardNumber
     */
    public int selectCardNum;
    public int selectPartyNum;

    public Game_Main(Context context, GameDbAdapter dbAdapter) {
        appClass = (ApplicationClass) context.getApplicationContext();
//        sceneMain = new Scene_Main(this);
        this.dbAdapter = dbAdapter;
        turnManager = new TurnManager(this);
    }

    public void Init() {
//        userEnty = checkPlayerData();
        /*if (userEnty == null) {
            // 프롤로그를 실행하고 플레이어를 작성
//            startProlog1();
            userEnty = DataManager.createUserData(dbAdapter, "홍길동");
        }*/

        canvasW = appClass.getGameCanvasWidth();
        canvasH = appClass.getGameCanvasHeight();

        loadMenuIcon();
        loadManager();
        setStatusIcon();
        setManager();

        selectCardNum = 0;
        selectPartyNum = 0;
    }

    private UserEnty checkPlayerData() {
        return new PPreference(appClass.getBaseContext()).readPlayer("player");
    }

    public void mainButtonAct(int state) {
        appClass.isGameInit = true;
        appClass.isSceneInit = true;
        appClass.gameState = state;
        selectCardNum = 0;
        selectPartyNum = 0;
    }

    public void mainButtonAct(int state, int val) {
        appClass.isGameInit = true;
        appClass.isSceneInit = true;
        appClass.gameState = state;
        switch (state) {
            case INN.GAME_MEMBER:
                selectCardNum = val;
                break;
        }
    }

    public void mainButtonAct(int state, String val) {
        appClass.isGameInit = true;
        appClass.isSceneInit = true;
        appClass.gameState = state;
        switch (state) {
            case INN.GAME_QUEST:
                selectQuestId = val;
                break;
        }
    }

//    public boolean onTouchEvent(MotionEvent event) {
//        return false;
//    }

    public void loadManager() {
        managerImg = new ImageEnty();
        managerImg.bitmap = GLUtil.loadAssetsBitmap(appClass, "main/manager_0.png", null);
    }

    public void setManager() {
        managerImg.animFrame = new ArrayList<>();
        managerImg.animMaxFrame = 100;

        managerImg.animClipList = new ArrayList<>();
        ClipImageEnty clipEnty1 = new ClipImageEnty();
        clipEnty1.animClipX = 758;
        clipEnty1.animClipY = 302;
        clipEnty1.animClipW = 96;
        clipEnty1.animClipH = 28;
        clipEnty1.animStartFrame = 85;
        clipEnty1.animEndFrame = 88;
        clipEnty1.animDrawX = 551;
        clipEnty1.animDrawY = 253;
        managerImg.animClipList.add(clipEnty1);
        ClipImageEnty clipEnty2 = new ClipImageEnty();
        clipEnty2.animClipX = 758;
        clipEnty2.animClipY = 266;
        clipEnty2.animClipW = 96;
        clipEnty2.animClipH = 28;
        clipEnty2.animStartFrame = 89;
        clipEnty2.animEndFrame = 95;
        clipEnty2.animDrawX = 551;
        clipEnty2.animDrawY = 253;
        managerImg.animClipList.add(clipEnty2);
        ClipImageEnty clipEnty3 = new ClipImageEnty();
        clipEnty3.animClipX = 758;
        clipEnty3.animClipY = 302;
        clipEnty3.animClipW = 96;
        clipEnty3.animClipH = 28;
        clipEnty3.animStartFrame = 96;
        clipEnty3.animEndFrame = 100;
        clipEnty3.animDrawX = 551;
        clipEnty3.animDrawY = 253;
        managerImg.animClipList.add(clipEnty3);
    }

    public void loadMenuIcon() {
        img_mainBtn = GLUtil.loadAssetsBitmap(appClass, "main/main_btn.png", null);
        img_statusBar = GLUtil.loadAssetsBitmap(appClass, "main/statusbar.png", null);
        img_classMark = GLUtil.loadAssetsBitmap(appClass, "main/classes_mark.png", null);
        img_defBtn = GLUtil.loadAssetsBitmap(appClass, "main/btn_def.png", null);
    }

    public void setStatusIcon() {
        optionBtn = new ButtonEnty();
        optionBtn.clipW = 102;
        optionBtn.clipH = 53;
        optionBtn.clipX = 121;
        optionBtn.clipY = 0;
        optionBtn.name = "back";
        optionBtn.drawX = canvasW - optionBtn.clipW - 15;
        optionBtn.drawY = (statusBarH - optionBtn.clipH) / 2;

        menIcon = new ButtonEnty();
        menIcon.name = "men";
        menIcon.drawX = (canvasW - 120) / 5 + 260;
        menIcon.drawY = 5;
        menIcon.clipW = 24;
        menIcon.clipH = 34;
        menIcon.clipX = 136;
        menIcon.clipY = 92;

        feedIcon = new ButtonEnty();
        feedIcon.name = "feed";
        feedIcon.drawX = (canvasW - 120) / 5 + 360;
        feedIcon.drawY = 5;
        feedIcon.clipW = 32;
        feedIcon.clipH = 32;
        feedIcon.clipX = 100;
        feedIcon.clipY = 92;

        goldIcon = new ButtonEnty();
        goldIcon.name = "gold";
        goldIcon.drawX = (canvasW - 120) / 5 + 160;
        goldIcon.drawY = 5;
        goldIcon.clipW = 36;
        goldIcon.clipH = 27;
        goldIcon.clipX = 101;
        goldIcon.clipY = 126;
    }

//    public boolean onMainTouch() {
////        if (alertManager.onAlertTouch() || alertManager.onTurnAlertTouch())
////            return true;
//
////        if (appClass.gameState == INN.GAME_HOME) {
////            if (onMeunTouch()) {
////                return true;
////            }
////        }
//
//        if (onStatusTouch()) {
//            return true;
//        }
//        return false;
//    }

    public boolean onStatusTouch() {
        if (GameUtil.equalsTouch(appClass.touch,
                optionBtn.drawX, optionBtn.drawY, optionBtn.clipW, optionBtn.clipH)) {
            if (appClass.touch.action == MotionEvent.ACTION_UP) {
                optionBtn.clickState = ButtonEnty.ButtonClickOff;
                if (optionBtn.name.equals("back")) {
                    switch (appClass.gameState) {
                        case INN.GAME_QUEST:
                            mainButtonAct(INN.GAME_QUESTLIST);
                            break;
                        case INN.GAME_MEMBER_FROM_PARTY:
                            mainButtonAct(INN.GAME_PARTY);
                            break;
                        case INN.GAME_PARTY:
                            mainButtonAct(INN.GAME_QUEST);
                            break;
                        default:
                            mainButtonAct(INN.GAME_HOME);
                            break;
                    }
                } else if (optionBtn.name.equals("menu")) {

                }
                return true;
            } else {
                optionBtn.clickState = ButtonEnty.ButtonClickOn;
            }
        } else {
            optionBtn.clickState = ButtonEnty.ButtonClickOff;
        }
        return false;
    }

    public void recycleScene() {
        DrawUtil.recycleBitmap(img_mainBtn);
        DrawUtil.recycleBitmap(img_statusBar);
        DrawUtil.recycleBitmap(img_classMark);
        DrawUtil.recycleBitmap(managerImg.bitmap);
        DrawUtil.recycleBitmap(img_defBtn);
    }

    public void drawBarGage(Canvas mCanvas, int bgColor, int color, String title,
                            int num, int maxNum, int x, int y, int w, int h) {
        int gageW = (int) (((float) num / (float) maxNum) * w);

        if (bgColor > -1)
            DrawUtil.drawBox(mCanvas, bgColor, true, x, y, w, h);
        DrawUtil.drawBox(mCanvas, color, true, x, y, gageW, h);
        String exp = title + " " + num + "/" + maxNum;
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(15);
        paint.setColor(Color.argb(255, 180, 190, 210));
        DrawUtil.drawString(mCanvas, exp, paint, x + w / 2, y - 5);
    }

    /**
     * draw user status
     */
    public void drawStatusTab(Canvas mCanvas) {
        int barNum = canvasW / statusBarW;
        for (int i = 0; i <= barNum; i++) {
            DrawUtil.drawClip(img_statusBar, mCanvas, 0, 0,
                    statusBarW, statusBarH, statusBarW * i, 0);
        }

        Paint paint = new Paint();
        int fontSize = 30;
        int drawY = (statusBarH - fontSize) / 2;
        int drawX = (canvasW - 120) / 5 + 60;
        paint.setColor(Color.argb(255, 50, 50, 50));
        paint.setTextSize(fontSize);

        // Name Lv
        DrawUtil.drawString(mCanvas, userEnty.Name + "(Lv " + userEnty.LEVEL + ")", paint, 10, drawY);

        // AP
        DrawUtil.drawString(mCanvas, "AP " + userEnty.AP, paint, drawX, drawY);

        // Gold
        DrawUtil.drawClip(img_mainBtn, mCanvas, goldIcon.clipX, goldIcon.clipY,
                goldIcon.clipW, goldIcon.clipH, goldIcon.drawX, drawY);
        DrawUtil.drawString(mCanvas, Integer.toString(userEnty.GOLD), paint,
                goldIcon.drawX + goldIcon.clipW + 5, drawY + 3);

        // Member
        DrawUtil.drawClip(img_mainBtn, mCanvas, menIcon.clipX, menIcon.clipY,
                menIcon.clipW, menIcon.clipH, menIcon.drawX, drawY);
        DrawUtil.drawString(mCanvas, Integer.toString(userEnty.MEMBERLIST.size()), paint,
                menIcon.drawX + menIcon.clipW + 5, drawY + 3);

//        DrawUtil.drawClip(gameHome.img_mainBtn, mCanvas, partyIcon.clipX, partyIcon.clipY,
//                partyIcon.clipW, partyIcon.clipH, partyIcon.drawX, partyIcon.drawY);
//        DrawUtil.drawString(mCanvas, Integer.toString(userEnty.PARTYLIST.size()), paint,
//                partyIcon.drawX + partyIcon.clipW + 5, partyIcon.drawY + 3);

        //Turn
        DrawUtil.drawString(mCanvas, "Turn " + userEnty.TURN, paint, canvasW - 250, drawY);

        //Option Button
        int clipY = optionBtn.clipY;
        if (optionBtn.clickState == ButtonEnty.ButtonClickOn) {
            clipY = 53;
        }
        DrawUtil.drawClip(img_statusBar, mCanvas, optionBtn.clipX, clipY,
                optionBtn.clipW, optionBtn.clipH, optionBtn.drawX, optionBtn.drawY);

        switch (appClass.gameState) {
            case INN.GAME_HOME:
                DrawUtil.drawClip(img_statusBar, mCanvas, 121, 107,
                        82, 23, optionBtn.drawX + (optionBtn.clipW - 82) / 2,
                        optionBtn.drawY + (optionBtn.clipH - 23) / 2);
                break;
            default:
                DrawUtil.drawClip(img_statusBar, mCanvas, 121, 130,
                        82, 23, optionBtn.drawX + (optionBtn.clipW - 82) / 2,
                        optionBtn.drawY + (optionBtn.clipH - 23) / 2);
                break;
        }
    }

    private void drawManager(Canvas mCanvas) {
        int managerBottomY = canvasH - 484;
        DrawUtil.drawClip(managerImg.bitmap, mCanvas, 490, 156,
                228, 484, 50, managerBottomY);

        if (managerImg.animCnt < managerImg.animMaxFrame) {
            managerImg.animCnt++;
        } else {
            managerImg.animCnt = 0;
        }

        for (ClipImageEnty enty : managerImg.animClipList) {
            if (enty.animStartFrame <= managerImg.animCnt && enty.animEndFrame >= managerImg.animCnt) {
                DrawUtil.drawClip(managerImg.bitmap, mCanvas, enty.animClipX, enty.animClipY,
                        enty.animClipW, enty.animClipH, enty.animDrawX - 490 + 50, enty.animDrawY - 156 + managerBottomY);
                break;
            }
        }

    }

    public void setSelectPartyNum(int selectPartyNum) {
        this.selectPartyNum = selectPartyNum;
    }

    public void setSelectCardNum(int selectCardNum) {
        this.selectCardNum = selectCardNum;
    }

    public Integer getSelectPartyNum() {
        return selectPartyNum;
    }

    public Integer getSelectPosition() {
        return selectCardNum;
    }
}
