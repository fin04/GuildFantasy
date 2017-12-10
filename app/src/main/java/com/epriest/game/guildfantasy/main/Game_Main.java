package com.epriest.game.guildfantasy.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.epriest.game.CanvasGL.graphics.CanvasUtil;
import com.epriest.game.CanvasGL.graphics.GLUtil;
import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.CanvasGL.util.GameUtil;
import com.epriest.game.guildfantasy.main.enty.ButtonEnty;
import com.epriest.game.guildfantasy.main.enty.ClipImageEnty;
import com.epriest.game.guildfantasy.main.enty.ImageEnty;
import com.epriest.game.guildfantasy.main.enty.MemberEnty;
import com.epriest.game.guildfantasy.main.enty.UserEnty;
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
    public Bitmap img_statusBar;
    public Bitmap img_alertBox;
    public Bitmap img_classMark;

    public ButtonEnty optionBtn;
    public ButtonEnty menIcon;
    public ButtonEnty feedIcon;
    public ButtonEnty goldIcon;

    //    public int mMainScreenY;
//    public int mMainScreenHeight;
//    public int mMenuTabBarY;

    public final int statusBarW = 120;
    public final int statusBarH = 75;

    public int canvasW, canvasH;

    /**
     * partyNumber.cardNumber
     */
    public String selectCardNum;

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

        setCardListFromSelectParty(0, 0);
    }

    private UserEnty checkPlayerData() {
        return new PPreference(appClass.getBaseContext()).readPlayer("player");
    }

    public void mainButtonAct(int state, int mode) {
        appClass.isGameInit = true;
        appClass.isSceneInit = true;
        appClass.gameState = state;
        appClass.stateMode = mode;
        setCardListFromSelectParty(0, 0);

//        new PPreference(appClass.getBaseContext()).writePlayer("player", userEnty);
    }

    public void mainButtonAct(int state, int mode, String cardNum) {
        appClass.isGameInit = true;
        appClass.isSceneInit = true;
        appClass.gameState = state;
        appClass.stateMode = mode;
        selectCardNum = cardNum;

//        new PPreference(appClass.getBaseContext()).writePlayer("player", userEnty);
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
        img_homeBtn = GLUtil.loadAssetsBitmap(appClass, "main/home_btn.png", null);
        img_mainBtn = GLUtil.loadAssetsBitmap(appClass, "main/main_btn.png", null);
        img_statusBar = GLUtil.loadAssetsBitmap(appClass, "main/statusbar.png", null);
        img_alertBox = GLUtil.loadAssetsBitmap(appClass, "main/alertbox.png", null);
        img_classMark = GLUtil.loadAssetsBitmap(appClass, "main/classes_mark.png", null);
    }

    public void setMenuIcon() {
        menuButtonList = new ArrayList<>();

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
            mBtn.drawY = canvasH - (mBtn.clipH * 2) + mBtn.clipH * (i / 3);
            menuButtonList.add(mBtn);
        }

        alertBtn = new ButtonEnty();
        alertBtn.name = "alert";
        alertBtn.drawX = (appClass.getGameCanvasWidth() -
                img_alertBox.getWidth()) / 2 + img_alertBox.getWidth() - 100;
        alertBtn.drawY = (appClass.getGameCanvasHeight() -
                img_alertBox.getHeight()) / 2 + img_alertBox.getHeight() - 100;
        alertBtn.clipW = 90;
        alertBtn.clipH = 90;
        alertBtn.clipX = 231;
        alertBtn.clipY = 173;

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

    public boolean onTouchMenu() {
        if (onAlertTouch() || onTurnAlertTouch())
            return true;

        if (appClass.gameState == INN.GAME_HOME) {
            if (onMeunTouch()) {
                return true;
            }
        }

        if (onStatusTouch()) {
            return true;
        }
        return false;
    }

    public boolean onTouchEvent() {
        if (onStatusTouch())
            return true;

        if (onAlertTouch()) {
            return true;
        }
        return false;
    }

    public boolean onAlertTouch() {
        if (showAlertType == INN.ALERT_TYPE_NONE)
            return false;
        if (GameUtil.equalsTouch(appClass.touch,
                alertBtn.drawX, alertBtn.drawY, alertBtn.clipW, alertBtn.clipH)) {
            if (appClass.touch.action == MotionEvent.ACTION_UP) {
                alertBtn.clickState = ButtonEnty.ButtonClickOff;
                switch (showAlertType) {
                    case INN.ALERT_TYPE_TURNSTART:
                        userEnty.GOLD += userEnty.eventEnty.Gold;
                        break;
                }
                showAlertType = INN.ALERT_TYPE_NONE;
                return true;
            } else {
                alertBtn.clickState = ButtonEnty.ButtonClickOn;
            }
        } else
            alertBtn.clickState = ButtonEnty.ButtonClickOn;
        return false;
    }

    public boolean onTurnAlertTouch() {
        if (showAlertType == INN.ALERT_TYPE_NONE)
            return false;
        int turnBtnNum = 5;
        if (GameUtil.equalsTouch(appClass.touch,
                menuButtonList.get(turnBtnNum).drawX, menuButtonList.get(turnBtnNum).drawY,
                menuButtonList.get(turnBtnNum).clipW, menuButtonList.get(turnBtnNum).clipH)) {
            if (appClass.touch.action == MotionEvent.ACTION_UP) {
                menuButtonList.get(turnBtnNum).clickState = ButtonEnty.ButtonClickOff;
                switch (appClass.gameState) {
                    case INN.GAME_HOME:
                        turnManager.turnCycle(userEnty.TURN++);
                        break;
                }
                showAlertType = INN.ALERT_TYPE_NONE;
                return true;
            } else {
                menuButtonList.get(turnBtnNum).clickState = ButtonEnty.ButtonClickOn;
            }
        } else {
            menuButtonList.get(turnBtnNum).clickState = ButtonEnty.ButtonClickOff;
        }
        return false;
    }

    public boolean onMeunTouch() {
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
                        mainButtonAct(INN.GAME_PARTY, 0);
                    else if (mBtn.name.equals(INN.menuIconName[3]))
                        mainButtonAct(INN.GAME_ITEM, 0);
                    else if (mBtn.name.equals(INN.menuIconName[4]))
                        mainButtonAct(INN.GAME_SKILL, 0);
                    else if (mBtn.name.equals(INN.menuIconName[5]))
                        mainButtonAct(INN.GAME_MOVE, 0);
                    else if (mBtn.name.equals("menu"))
                        mainButtonAct(INN.GAME_OPTION, 0);
                    return true;
                }
            } else {
                mBtn.clickState = ButtonEnty.ButtonClickOff;
            }
        }
        return false;
    }

    public boolean onStatusTouch() {
        if (GameUtil.equalsTouch(appClass.touch,
                optionBtn.drawX, optionBtn.drawY, optionBtn.clipW, optionBtn.clipH)) {
            if (appClass.touch.action == MotionEvent.ACTION_UP) {
                optionBtn.clickState = ButtonEnty.ButtonClickOff;
                if (optionBtn.name.equals("back")) {
                    mainButtonAct(INN.GAME_HOME, 0);
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
        CanvasUtil.recycleBitmap(img_mainBtn);
        CanvasUtil.recycleBitmap(img_homeBtn);
        CanvasUtil.recycleBitmap(img_statusBar);
        CanvasUtil.recycleBitmap(img_alertBox);
        CanvasUtil.recycleBitmap(img_classMark);
        CanvasUtil.recycleBitmap(managerImg.bitmap);
    }

    public void drawMain(Canvas mCanvas, boolean viewMenuButton) {

    }

    public void drawMenu(Canvas mCanvas) {
        drawMenuButton(mCanvas);

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
    }

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

    public void drawTurnStartAlert(Canvas mCanvas) {
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
        CanvasUtil.drawClip(img_classMark, mCanvas, clipX, clipY,
                64, 64, alertX + 10, alertY + 105);

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
        paint.setColor(Color.WHITE);
        paint.setTextSize(35);
        CanvasUtil.drawString(mCanvas, enty.name, paint, halfCanvasW, alertY + 30);

        // exp
        drawBarGage(mCanvas, -1, Color.argb(255, 0, 200, 50),
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
        drawBarGage(mCanvas, -1, Color.argb(255, 250, 90, 60),
                "HP", enty.status.MAX_HP - enty.status.USE_HP, enty.status.MAX_HP,
                halfCanvasW, alertY + 180, 200, 12);
        drawBarGage(mCanvas, -1, Color.argb(255, 60, 90, 250),
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

    public void drawBarGage(Canvas mCanvas, int bgColor, int color, String title,
                            int num, int maxNum, int x, int y, int w, int h) {
        int gageW = (int) (((float) num / (float) maxNum) * w);

        if (bgColor > -1)
            CanvasUtil.drawBox(mCanvas, bgColor, true, x, y, w, h);
        CanvasUtil.drawBox(mCanvas, color, true, x, y, gageW, h);
        String exp = title + " " + num + "/" + maxNum;
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(15);
        paint.setColor(Color.argb(255, 180, 190, 210));
        CanvasUtil.drawString(mCanvas, exp, paint, x + w / 2, y - 5);
    }

    /**
     * draw user status
     */
    public void drawStatusTab(Canvas mCanvas) {
        int barNum = canvasW / statusBarW;
        for (int i = 0; i <= barNum; i++) {
            CanvasUtil.drawClip(img_statusBar, mCanvas, 0, 0,
                    statusBarW, statusBarH, statusBarW * i, 0);
        }

        Paint paint = new Paint();
        int fontSize = 30;
        int drawY = (statusBarH - fontSize) / 2;
        int drawX = (canvasW - 120) / 5 + 60;
        paint.setColor(Color.argb(255, 50, 50, 50));
        paint.setTextSize(fontSize);

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
        CanvasUtil.drawString(mCanvas, "Turn " + userEnty.TURN, paint, canvasW - 250, drawY);

        //Option Button
        int clipY = optionBtn.clipY;
        if (optionBtn.clickState == ButtonEnty.ButtonClickOn) {
            clipY = 53;
        }
        CanvasUtil.drawClip(img_statusBar, mCanvas, optionBtn.clipX, clipY,
                optionBtn.clipW, optionBtn.clipH, optionBtn.drawX, optionBtn.drawY);

        switch (appClass.gameState) {
            case INN.GAME_HOME:
                CanvasUtil.drawClip(img_statusBar, mCanvas, 121, 107,
                        82, 23, optionBtn.drawX + (optionBtn.clipW - 82) / 2,
                        optionBtn.drawY + (optionBtn.clipH - 23) / 2);
                break;
            default:
                CanvasUtil.drawClip(img_statusBar, mCanvas, 121, 130,
                        82, 23, optionBtn.drawX + (optionBtn.clipW - 82) / 2,
                        optionBtn.drawY + (optionBtn.clipH - 23) / 2);
                break;
        }
    }

    private void drawManager(Canvas mCanvas) {
        int managerBottomY = menuButtonList.get(3).drawY - 484;
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

    public void setCardListFromSelectParty(int selectParty, int selectCardPos) {
        this.selectCardNum = selectParty + "-" + selectCardPos;
    }

    public void setSelectPartyNum(int selectPartyNum) {
        String str[] = this.selectCardNum.split("-");
        this.selectCardNum = selectPartyNum + "-" + str[1];
    }

    public void setSelectCardNum(int selectCardNum) {
        String str[] = this.selectCardNum.split("-");
        this.selectCardNum = str[0] + "-" + selectCardNum;
    }

    public Integer getSelectPartyNum() {
        String str[] = selectCardNum.split("-");
        return Integer.parseInt(str[0]);
    }

    public Integer getSelectPosition() {
        String str[] = selectCardNum.split("-");
        return Integer.parseInt(str[1]);
    }
}
