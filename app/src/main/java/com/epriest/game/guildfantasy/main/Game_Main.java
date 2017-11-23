package com.epriest.game.guildfantasy.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.widget.Toast;

import com.epriest.game.CanvasGL.graphics.CanvasUtil;
import com.epriest.game.CanvasGL.graphics.GLUtil;
import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.CanvasGL.util.GameUtil;
import com.epriest.game.guildfantasy.main.enty.ButtonEnty;
import com.epriest.game.guildfantasy.main.enty.ClipImageEnty;
import com.epriest.game.guildfantasy.main.enty.ImageEnty;
import com.epriest.game.guildfantasy.main.enty.UserEnty;
import com.epriest.game.guildfantasy.main.play.DataManager;
import com.epriest.game.guildfantasy.main.play.GameDbAdapter;
import com.epriest.game.guildfantasy.main.play.TurnManager;
import com.epriest.game.guildfantasy.util.INN;
import com.epriest.game.guildfantasy.util.PPreference;

import java.util.ArrayList;

import static com.epriest.game.CanvasGL.graphics.CanvasUtil.drawClip;

/**
 * Created by darka on 2017-03-26.
 */

public class Game_Main {

    public ApplicationClass appClass;
    public TurnManager turnManager;
    public GameDbAdapter dbAdapter;
//    public Scene_Main sceneMain;

    public UserEnty userEnty;

    public int showAlertType;
    public ButtonEnty alertBtn;
    public ArrayList<ButtonEnty> menuButtonList;

    public ImageEnty managerImg;

    public Bitmap img_mainBtn;
    public Bitmap img_homeBtn;
    public Bitmap img_menuBar;
    public Bitmap alertBox;

    public ButtonEnty optionBtn;
    public ButtonEnty menIcon;
    public ButtonEnty feedIcon;
    public ButtonEnty goldIcon;

    //    public int mMainScreenY;
//    public int mMainScreenHeight;
    public int mMenuTabBarY;

    public final int statusBarW = 120;
    public final int statusBarH = 75;

    public int canvasW, canvasH;

    public int selectCardNum;

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
        setMenuIcon();
        setManager();
    }

    private UserEnty checkPlayerData() {
        return new PPreference(appClass.getBaseContext()).readPlayer("player");
    }

    public void mainButtonAct(int state, int mode) {
        appClass.isGameInit = true;
        appClass.isSceneInit = true;
        appClass.gameState = state;
        appClass.stateMode = mode;
        selectCardNum = -1;

        new PPreference(appClass.getBaseContext()).writePlayer("player", userEnty);
    }

    public void mainButtonAct(int state, int mode, int num) {
        appClass.isGameInit = true;
        appClass.isSceneInit = true;
        appClass.gameState = state;
        appClass.stateMode = mode;
        selectCardNum = num;

        new PPreference(appClass.getBaseContext()).writePlayer("player", userEnty);
    }

    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

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
        img_homeBtn = GLUtil.loadAssetsBitmap(appClass, "main/home_btn.png", null);
        img_mainBtn = GLUtil.loadAssetsBitmap(appClass, "main/main_btn.png", null);
        img_menuBar = GLUtil.loadAssetsBitmap(appClass, "main/statusbar.png", null);
        alertBox = GLUtil.loadAssetsBitmap(appClass, "main/alertbox.png", null);
    }

    public void setMenuIcon() {
        menuButtonList = new ArrayList<>();
        mMenuTabBarY = canvasH - 300 - statusBarH;
        for (int i = 0; i < INN.menuIconName.length; i++) {
            ButtonEnty mBtn = new ButtonEnty();
            mBtn.num = i;
            mBtn.name = INN.menuIconName[i];
            mBtn.iconImgNum = INN.menuIconNum[i];
//                    mBtn.clipW = 96;
//                    mBtn.clipH = 84;
//                    mBtn.clipX = 0;
//                    mBtn.clipY = 0;
            mBtn.clipW = 220;
            mBtn.clipH = 150;
            mBtn.clipX = 0;
            mBtn.clipY = 0;
//                    mBtn.drawX = canvasW - 110 - (4 - i) * (mBtn.clipW + 10);
//                    mBtn.drawY = canvasH - mBtn.clipH - 10;

            mBtn.drawX = 20 + ((mBtn.clipW + 5) * (i % 3));
            mBtn.drawY = mMenuTabBarY + statusBarH + mBtn.clipH * (i / 3);
            menuButtonList.add(mBtn);
        }

        alertBtn = new ButtonEnty();
        alertBtn.name = "Alert";
        alertBtn.drawX = (appClass.getGameCanvasWidth() -
                alertBox.getWidth()) / 2 + alertBox.getWidth() - 100;
        alertBtn.drawY = (appClass.getGameCanvasHeight() -
                alertBox.getHeight()) / 2 + alertBox.getHeight() - 100;
        alertBtn.clipW = 90;
        alertBtn.clipH = 90;
        alertBtn.clipX = 231;
        alertBtn.clipY = 173;

        optionBtn = new ButtonEnty();
        optionBtn.clipW = 103;
        optionBtn.clipH = 52;
        optionBtn.clipX = 121;
        optionBtn.clipY = 0;
        optionBtn.name = "Back";
        optionBtn.drawX = canvasW - optionBtn.clipW - 15;
        optionBtn.drawY = mMenuTabBarY + (statusBarH - optionBtn.clipH) / 2;

        menIcon = new ButtonEnty();
        menIcon.name = "Men";
        menIcon.drawX = (canvasW - 120) / 5 + 260;
        menIcon.drawY = 5;
        menIcon.clipW = 24;
        menIcon.clipH = 34;
        menIcon.clipX = 136;
        menIcon.clipY = 92;

        feedIcon = new ButtonEnty();
        feedIcon.name = "Feed";
        feedIcon.drawX = (canvasW - 120) / 5 + 360;
        feedIcon.drawY = 5;
        feedIcon.clipW = 32;
        feedIcon.clipH = 32;
        feedIcon.clipX = 100;
        feedIcon.clipY = 92;

        goldIcon = new ButtonEnty();
        goldIcon.name = "Gold";
        goldIcon.drawX = (canvasW - 120) / 5 + 160;
        goldIcon.drawY = 5;
        goldIcon.clipW = 36;
        goldIcon.clipH = 27;
        goldIcon.clipX = 101;
        goldIcon.clipY = 126;
    }

    public void onTouchMenuIcon() {
        if (showAlertType > INN.ALERT_TYPE_NONE) {
            if (GameUtil.equalsTouch(appClass.touch,
                    alertBtn.drawX, alertBtn.drawY, alertBtn.clipW, alertBtn.clipH)) {
                if (appClass.touch.action == MotionEvent.ACTION_UP) {
                    alertBtn.clickState = ButtonEnty.ButtonClickOff;
//                    userEnty.isStartTurnAlert = false;
                    showAlertType = INN.ALERT_TYPE_NONE;
                    switch (showAlertType) {
                        case INN.ALERT_TYPE_TURNSTART:
                            userEnty.GOLD += userEnty.eventEnty.Gold;
                            break;
                    }


                    return;
                } else {
                    alertBtn.clickState = ButtonEnty.ButtonClickOn;
                }
            } else
                alertBtn.clickState = ButtonEnty.ButtonClickOn;
            return;
        }

        int turnBtnNum = menuButtonList.size() - 1;
        if (GameUtil.equalsTouch(appClass.touch,
                menuButtonList.get(turnBtnNum).drawX, menuButtonList.get(turnBtnNum).drawY,
                menuButtonList.get(turnBtnNum).clipW, menuButtonList.get(turnBtnNum).clipH)) {
            if (appClass.touch.action == MotionEvent.ACTION_UP) {
                menuButtonList.get(turnBtnNum).clickState = ButtonEnty.ButtonClickOff;
                switch (appClass.gameState) {
                    case INN.GAME_HOME:
                        turnManager.turnCycle(1);
                        break;
                }
            } else {
                menuButtonList.get(turnBtnNum).clickState = ButtonEnty.ButtonClickOn;
            }
            return;
        } else {
            menuButtonList.get(turnBtnNum).clickState = ButtonEnty.ButtonClickOff;
        }
        if (appClass.gameState != INN.GAME_HOME)
            return;

        for (ButtonEnty mBtn : menuButtonList) {
            if (GameUtil.equalsTouch(appClass.touch, mBtn.drawX, mBtn.drawY, mBtn.clipW, mBtn.clipH)) {
                mBtn.clickState = ButtonEnty.ButtonClickOn;
                if (appClass.touch.action == MotionEvent.ACTION_UP) {
                    mBtn.clickState = ButtonEnty.ButtonClickOff;

                    if (mBtn.name.equals(INN.menuIconName[0]))
                        mainButtonAct(INN.GAME_MEMBER, 0);
                    else if (mBtn.name.equals(INN.menuIconName[1]))
                        mainButtonAct(INN.GAME_RECRUIT, INN.MODE_MEMBER_RECRUIT);
                    else if (mBtn.name.equals(INN.menuIconName[2]))
                        mainButtonAct(INN.GAME_SKILL, 0);
                    else if (mBtn.name.equals(INN.menuIconName[3]))
                        mainButtonAct(INN.GAME_ITEM, 0);
                    else if (mBtn.name.equals(INN.menuIconName[4]))
                        mainButtonAct(INN.GAME_QUEST, 0);
                    else if (mBtn.name.equals(INN.menuIconName[5]))
                        mainButtonAct(INN.GAME_MOVE, 0);
                    else if (mBtn.name.equals("Menu"))
                        mainButtonAct(INN.GAME_OPTION, 0);
                }
                return;
            } else {
                mBtn.clickState = ButtonEnty.ButtonClickOff;
            }
        }

        if (GameUtil.equalsTouch(appClass.touch,
                optionBtn.drawX, optionBtn.drawY, optionBtn.clipW, optionBtn.clipH)) {
            if (appClass.touch.action == MotionEvent.ACTION_UP) {
                optionBtn.clickState = ButtonEnty.ButtonClickOff;
                Toast.makeText(appClass, "menu", Toast.LENGTH_SHORT).show();
            } else {
                optionBtn.clickState = ButtonEnty.ButtonClickOn;
            }
            return;
        } else {
            optionBtn.clickState = ButtonEnty.ButtonClickOff;
        }
    }

    public void recycleScene() {
        CanvasUtil.recycleBitmap(img_mainBtn);
        CanvasUtil.recycleBitmap(img_homeBtn);
        CanvasUtil.recycleBitmap(img_menuBar);
        CanvasUtil.recycleBitmap(alertBox);
        CanvasUtil.recycleBitmap(managerImg.bitmap);
    }

    public void drawMain(Canvas mCanvas, boolean viewMenuButton) {

    }

    public void drawMenu(Canvas mCanvas) {
        drawMenuButton(mCanvas);
        if (appClass.gameState == INN.GAME_HOME && showAlertType>INN.ALERT_TYPE_NONE)
            drawTurnStartAlert(mCanvas);

        drawStatusTab(mCanvas);

        //manager mode
//        drawManager(mCanvas);
    }

    private void drawMenuButton(Canvas mCanvas) {
//        int btnArea = (canvasH - statusBarH)/ gameMain.menuButtonList.size();
        for (ButtonEnty mBtn : menuButtonList) {
            int clipX = mBtn.clipX;
            int clipY = mBtn.clipY;
            if (mBtn.clickState == ButtonEnty.ButtonClickOn) {
                if (mBtn.num == menuButtonList.size() - 1)
                    clipX += mBtn.clipW;
                else
                    clipY += mBtn.clipH;
            }
            //Button Image
            CanvasUtil.drawClip(img_homeBtn, mCanvas, clipX, clipY,
                    mBtn.clipW, mBtn.clipH, mBtn.drawX, mBtn.drawY);

            int btnNameX = mBtn.drawX + (mBtn.clipW - 180) / 2;
            int btnNameY = mBtn.drawY + (mBtn.clipH - 50) / 2;
            //Button Title
            CanvasUtil.drawClip(img_homeBtn, mCanvas, 220, mBtn.iconImgNum * 50,
                    180, 50, btnNameX, btnNameY);
            btnNameX = mBtn.drawX + (mBtn.clipW - 130);
            btnNameY = mBtn.drawY;
            //Button Subtitle
            CanvasUtil.drawClip(img_homeBtn, mCanvas, 400, mBtn.iconImgNum * 50,
                    130, 50, btnNameX, btnNameY);
            //Button Subtitle HanGul
//            CanvasUtil.drawClip(gameHome.img_homeBtn, mCanvas, 531, mBtn.iconImgNum*50,
//                    130, 50, btnNameX, btnNameY);
//            CanvasUtil.drawClip(menu_icon, mCanvas, null, (iconNum%5)*mBtn.w, (iconNum/5)*mBtn.h,
//                    mBtn.w, mBtn.h, mBtn.x+(btnArea-mBtn.w)/2, mBtn.y);
        }

        int clipY = optionBtn.clipY;
        if (optionBtn.clickState == ButtonEnty.ButtonClickOn) {
            clipY = 54;
        }
        CanvasUtil.drawClip(img_menuBar, mCanvas, optionBtn.clipX, clipY,
                optionBtn.clipW, optionBtn.clipH, optionBtn.drawX, optionBtn.drawY);

        CanvasUtil.drawClip(img_menuBar, mCanvas, 121, 107,
                82, 23, optionBtn.drawX + (optionBtn.clipW - 82) / 2,
                optionBtn.drawY + (optionBtn.clipH - 23) / 2);
//
//        switch (gameHome.gameMain.appClass.gameState) {
//            case INN.GAME_HOME:
//                CanvasUtil.drawClip(gameHome.img_mainBtn, mCanvas, 270, 0,
//                        gameHome.optionBtn.clipW, 18, gameHome.optionBtn.drawX, gameHome.optionBtn.drawY + 12);
//                break;
//            default:
//                CanvasUtil.drawClip(gameHome.img_mainBtn, mCanvas, 270, 18,
//                        gameHome.optionBtn.clipW, 18, gameHome.optionBtn.drawX, gameHome.optionBtn.drawY + 12);
//                break;
//        }
    }

    public void drawAlert(Canvas mCanvas, String title, String text) {
        int alertY = (canvasH - alertBox.getHeight()) / 2;
        // alert bg
        CanvasUtil.drawBitmap(alertBox, mCanvas, (canvasW - alertBox.getWidth()) / 2
                , alertY);

        // alert button
        int alertBtnClipX = alertBtn.clipX;
        if (alertBtn.clickState == ButtonEnty.ButtonClickOn) {
            alertBtnClipX += alertBtn.clipW;
        }
        drawClip(img_mainBtn, mCanvas,
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

    private void drawTurnStartAlert(Canvas mCanvas) {
        StringBuilder sb = new StringBuilder("Turn : ");
        sb.append(userEnty.TURN);
        sb.append("\n");
        sb.append("Clear Quest : ");
        sb.append("\n");
        sb.append("Income : ");
        sb.append(userEnty.eventEnty.Gold + "Gold");
        sb.append("\n");
        sb.append("New Quest : ");
        sb.append(userEnty.eventEnty.QuestIDList.size());

        drawAlert(mCanvas, userEnty.TURN + " Turn", sb.toString());
    }

    /**
     * draw user status
     */
    private void drawStatusTab(Canvas mCanvas) {
        int canvasWidth = appClass.getGameCanvasWidth();
        Paint paint = new Paint();
        int drawY = mMenuTabBarY + statusBarH / 2 - 10;
        int drawX = (canvasWidth - 120) / 5 + 60;
        paint.setColor(Color.argb(255, 50, 50, 50));
        paint.setTextSize(20);

        // Name Lv
        CanvasUtil.drawString(mCanvas, userEnty.Name + "(Lv " + userEnty.LEVEL + ")", paint, 10, drawY);

        // AP
        CanvasUtil.drawString(mCanvas, "AP " + userEnty.AP, paint, drawX, drawY);

        // Gold
        CanvasUtil.drawClip(img_mainBtn, mCanvas, goldIcon.clipX, goldIcon.clipY,
                goldIcon.clipW, goldIcon.clipH, goldIcon.drawX, drawY);
        CanvasUtil.drawString(mCanvas, Integer.toString(userEnty.GOLD), paint,
                goldIcon.drawX + goldIcon.clipW + 5, drawY + 3);

        // Member
        CanvasUtil.drawClip(img_mainBtn, mCanvas, menIcon.clipX, menIcon.clipY,
                menIcon.clipW, menIcon.clipH, menIcon.drawX, drawY);
        CanvasUtil.drawString(mCanvas, Integer.toString(userEnty.MEMBERLIST.size()), paint,
                menIcon.drawX + menIcon.clipW + 5, drawY + 3);

//        CanvasUtil.drawClip(gameHome.img_mainBtn, mCanvas, partyIcon.clipX, partyIcon.clipY,
//                partyIcon.clipW, partyIcon.clipH, partyIcon.drawX, partyIcon.drawY);
//        CanvasUtil.drawString(mCanvas, Integer.toString(userEnty.PARTYLIST.size()), paint,
//                partyIcon.drawX + partyIcon.clipW + 5, partyIcon.drawY + 3);

        //Turn
        CanvasUtil.drawString(mCanvas, "Turn " + userEnty.TURN, paint, canvasWidth - 180, drawY);
    }

    private void drawManager(Canvas mCanvas) {
        int managerBottomY = mMenuTabBarY - 484;
        CanvasUtil.drawClip(managerImg.bitmap, mCanvas, 490, 156,
                228, 484, 50, managerBottomY);

        if (managerImg.animCnt < managerImg.animMaxFrame) {
            managerImg.animCnt++;
        } else {
            managerImg.animCnt = 0;
        }

        for (ClipImageEnty enty : managerImg.animClipList) {
            if (enty.animStartFrame <= managerImg.animCnt && enty.animEndFrame >= managerImg.animCnt) {
                CanvasUtil.drawClip(managerImg.bitmap, mCanvas, enty.animClipX, enty.animClipY,
                        enty.animClipW, enty.animClipH, enty.animDrawX - 490 + 50, enty.animDrawY - 156 + managerBottomY);
                break;
            }
        }

    }
}
