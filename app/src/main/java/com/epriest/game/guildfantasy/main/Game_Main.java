package com.epriest.game.guildfantasy.main;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.guildfantasy.main.enty.UserEnty;
import com.epriest.game.guildfantasy.main.play.DataManager;
import com.epriest.game.guildfantasy.main.play.GameDbAdapter;
import com.epriest.game.guildfantasy.main.play.TurnManager;
import com.epriest.game.guildfantasy.util.PPreference;

/**
 * Created by darka on 2017-03-26.
 */

public class Game_Main {

    public ApplicationClass appClass;
    public TurnManager turnManager;
    public GameDbAdapter dbAdapter;
//    public Scene_Main sceneMain;

    public UserEnty userEnty;
//    public EventEnty eventEnty;
//    public QuestEnty selectQuestEnty;
//    public ArrayList<PartyEnty> inTownPartyList;
//    private ButtonEnty backBtn;
//    private ButtonEnty memberIcon;
//    private ButtonEnty partyIcon;
//    private ButtonEnty goldIcon;



//    public Bitmap img_mainBtn;
//    private Bitmap img_homeBtn;
//    public Bitmap img_menuBar;
//    private Bitmap alertBox;

//    public int mMainScreenY;
//    public int mMainScreenHeight;
//    public int mMenuTabBarY;

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
        if (userEnty == null) {
            // 프롤로그를 실행하고 플레이어를 작성
//            startProlog1();
            userEnty = DataManager.createUserData(dbAdapter, "홍길동");
        }

        canvasW = appClass.getGameCanvasWidth();
        canvasH = appClass.getGameCanvasHeight();

//        mMainScreenY = 0;
//        mMenuTabBarY = mMainScreenY;

//        inTownPartyList = new ArrayList<>();

//        img_homeBtn = GLUtil.loadAssetsBitmap(appClass, "main/home_btn.png", null);
//        img_mainBtn = GLUtil.loadAssetsBitmap(appClass, "main/main_btn.png", null);
//        img_menuBar = GLUtil.loadAssetsBitmap(appClass, "main/statusbar_tile.png", null);
//        alertBox = GLUtil.loadAssetsBitmap(appClass, "main/alertbox.png", null);
//        menuButtonList = setMenuIcon(canvasW, canvasH);
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
        /*if (appClass.gameState == INN.GAME_HOME && userEnty.isStartTurnAlert) {
            if (GameUtil.equalsTouch(appClass.touch, alertBtn.drawX, alertBtn.drawY, alertBtn.clipW, alertBtn.clipH)) {
                if (appClass.touch.action == MotionEvent.ACTION_UP) {
                    alertBtn.clickState = ButtonEnty.ButtonClickOff;
                    userEnty.isStartTurnAlert = false;
//                    userEnty = DataManager.setChangeEvent(dbAdapter, userEnty);

                    return true;
                } else {
                    alertBtn.clickState = ButtonEnty.ButtonClickOn;
                }
            } else
                alertBtn.clickState = ButtonEnty.ButtonClickOn;
            return false;
        }

        int turnBtnNum = menuButtonList.size() - 1;
        if (GameUtil.equalsTouch(appClass.touch, menuButtonList.get(turnBtnNum).drawX, menuButtonList.get(turnBtnNum).drawY,
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
            return true;
        } else {
            menuButtonList.get(turnBtnNum).clickState = ButtonEnty.ButtonClickOff;
        }

        if (GameUtil.equalsTouch(appClass.touch, backBtn.drawX, backBtn.drawY, backBtn.clipW, backBtn.clipH)) {
            if (appClass.touch.action == MotionEvent.ACTION_UP) {
                backBtn.clickState = ButtonEnty.ButtonClickOff;
                switch (appClass.gameState) {
                    case INN.GAME_PARTY:
                        mainButtonAct(INN.GAME_QUEST_SELECT, 0);
                        break;
                    default:
                        // back
                        mainButtonAct(INN.GAME_HOME, 0);
                        break;
                }
            } else {
                backBtn.clickState = ButtonEnty.ButtonClickOn;
            }
            return true;
        } else {
            backBtn.clickState = ButtonEnty.ButtonClickOff;
        }


        if (appClass.gameState != INN.GAME_HOME)
            return false;

        for (ButtonEnty mBtn : menuButtonList) {
            if (GameUtil.equalsTouch(appClass.touch, mBtn.drawX, mBtn.drawY, mBtn.clipW, mBtn.clipH)) {
                mBtn.clickState = ButtonEnty.ButtonClickOn;
                if (appClass.touch.action == MotionEvent.ACTION_UP) {
                    mBtn.clickState = ButtonEnty.ButtonClickOff;

                    if (mBtn.name.equals("Home") && appClass.gameState != INN.GAME_HOME)
                        mainButtonAct(INN.GAME_HOME, 0);
                    else if (mBtn.name.equals("Party") && appClass.gameState != INN.GAME_PARTY)
                        mainButtonAct(INN.GAME_PARTY, 0);
                    else if (mBtn.name.equals("Member") && appClass.gameState != INN.GAME_MEMBER)
                        mainButtonAct(INN.GAME_MEMBER, 0);
                    else if (mBtn.name.equals("Town") && appClass.gameState != INN.GAME_TOWN)
                        mainButtonAct(INN.GAME_TOWN, 0);
                    else if (mBtn.name.equals("Quest") && appClass.gameState != INN.GAME_QUEST_SELECT) {
                        if (userEnty.QUESTLIST.size() > 0)
                            mainButtonAct(INN.GAME_QUEST_SELECT, 0);
                    } else if (mBtn.name.equals("Setting") && appClass.gameState != INN.GAME_OPTION)
                        mainButtonAct(INN.GAME_OPTION, 0);
                }
                return true;
            } else {
                mBtn.clickState = ButtonEnty.ButtonClickOff;
            }
        }*/
        return false;
    }

    /*private ArrayList<ButtonEnty> setMenuIcon(int canvasW, int canvasH) {
        ArrayList<ButtonEnty> menuButtonList = new ArrayList<>();
        switch (appClass.gameState) {
            default:
                for (int i = 0; i < INN.menuIconName.length - 2; i++) {
                    ButtonEnty mBtn = new ButtonEnty();
                    mBtn.num = i;
                    mBtn.name = INN.menuIconName[i];
                    mBtn.iconImgNum = INN.menuIconNum[i];
//                    mBtn.clipW = 96;
//                    mBtn.clipH = 84;
//                    mBtn.clipX = 0;
//                    mBtn.clipY = 0;
                    mBtn.clipW = 104;
                    mBtn.clipH = 40;
                    mBtn.clipX = 97;
                    mBtn.clipY = 0;
//                    mBtn.drawX = canvasW - 110 - (4 - i) * (mBtn.clipW + 10);
//                    mBtn.drawY = canvasH - mBtn.clipH - 10;

                    mBtn.drawX = 20 + (mBtn.clipW + 5) * i;
                    mBtn.drawY = mMenuTabBarY + (img_menuBar.getHeight() - mBtn.clipH) / 3;
                    menuButtonList.add(mBtn);
                }

                //play button
                ButtonEnty mBtn = new ButtonEnty();
//                mBtn.num = menuIconName.length - 2;
                mBtn.name = INN.menuIconName[INN.menuIconName.length - 2];
//                mBtn.iconImgNum = menuIconNum[mBtn.num];
                mBtn.clipW = 116;
                mBtn.clipH = 116;
                mBtn.clipX = 0;
                mBtn.clipY = 172;
                mBtn.drawX = canvasW - (mBtn.clipW + 5);
                mBtn.drawY = canvasH - (mBtn.clipH + 5);
                menuButtonList.add(mBtn);

                backBtn = new ButtonEnty();
                backBtn.clipW = 104;
                backBtn.clipH = 40;
                backBtn.clipX = 97;
                backBtn.clipY = 0;
                backBtn.name = "Back";
                backBtn.drawX = canvasW - backBtn.clipW - 15;
                backBtn.drawY = mMenuTabBarY + (img_menuBar.getHeight() - backBtn.clipH) / 3;


                memberIcon = new ButtonEnty();
                memberIcon.name = "Member";
                memberIcon.drawX = (appClass.getGameCanvasWidth() - 120) / 5;
                memberIcon.drawY = 5;
                memberIcon.clipW = 24;
                memberIcon.clipH = 34;
                memberIcon.clipX = 136;
                memberIcon.clipY = 92;

                partyIcon = new ButtonEnty();
                partyIcon.name = "Party";
                partyIcon.drawX = (appClass.getGameCanvasWidth() - 120) / 5 + 80;
                partyIcon.drawY = 5;
                partyIcon.clipW = 32;
                partyIcon.clipH = 32;
                partyIcon.clipX = 100;
                partyIcon.clipY = 92;

                goldIcon = new ButtonEnty();
                goldIcon.name = "Gold";
                goldIcon.drawX = (appClass.getGameCanvasWidth() - 120) / 5 + 160;
                goldIcon.drawY = 5;
                goldIcon.clipW = 36;
                goldIcon.clipH = 27;
                goldIcon.clipX = 101;
                goldIcon.clipY = 126;

                alertBtn = new ButtonEnty();
                alertBtn.name = "Alert";
                alertBtn.drawX = (appClass.getGameCanvasWidth() - alertBox.getWidth()) / 2 + alertBox.getWidth() - 100;
                alertBtn.drawY = (appClass.getGameCanvasHeight() - alertBox.getHeight()) / 2 + alertBox.getHeight() - 100;
                alertBtn.clipW = 90;
                alertBtn.clipH = 90;
                alertBtn.clipX = 231;
                alertBtn.clipY = 173;
                break;

            case INN.GAME_PARTY:
                ButtonEnty mBtn1 = new ButtonEnty();
                mBtn1.num = 0;
                mBtn1.name = "start";
                mBtn1.clipW = 117;
                mBtn1.clipH = 117;
                mBtn1.clipX = 1;
                mBtn1.clipY = 172;
                mBtn1.drawX = canvasW - mBtn1.clipW - 20;
                mBtn1.drawY = canvasH - mBtn1.clipH - 10;
                menuButtonList.add(mBtn1);

                ButtonEnty mBtn2 = new ButtonEnty();
                mBtn2.num = 1;
                mBtn2.name = "back";
                mBtn2.clipW = 90;
                mBtn2.clipH = 90;
                mBtn2.clipX = 230;
                mBtn2.clipY = 172;
                mBtn2.drawX = canvasW - mBtn1.clipW - 20;
                mBtn2.drawY = 90;
                menuButtonList.add(mBtn2);
                break;
        }

        return menuButtonList;
    }*/

    public void drawMain(Canvas mCanvas, boolean viewMenuButton) {
        // top
//        drawBG(mCanvas);

        //menu button
//        if (viewMenuButton) {
//            drawMenuButton(mCanvas);
//        }

        // turn alert dialog
//        if (appClass.gameState == INN.GAME_HOME && userEnty.isStartTurnAlert)
//            drawTurnStartAlert(mCanvas);
    }

    /*private void drawBG(Canvas mCanvas) {
        CanvasUtil.drawBox(mCanvas, Color.argb(255, 50,50,50),true,
                0, 0,
                appClass.getGameCanvasWidth(), mMainScreenY);

        int barNum = appClass.getGameCanvasWidth() / img_menuBar.getWidth();
        for (int i = 0; i <= barNum; i++) {
            CanvasUtil.drawBitmap(img_menuBar, mCanvas, img_menuBar.getWidth() * i, mMenuTabBarY);
        }
    }*/

    /*public void drawMenuButton(Canvas mCanvas) {
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
            CanvasUtil.drawClip(img_mainBtn, mCanvas, clipX, clipY,
                    mBtn.clipW, mBtn.clipH, mBtn.drawX, mBtn.drawY);
            CanvasUtil.drawClip(img_mainBtn, mCanvas, 200, mBtn.iconImgNum * 18,
                    64, 18, mBtn.drawX + (mBtn.clipW - 64) / 2, mBtn.drawY + 15);
//            CanvasUtil.drawClip(menu_icon, mCanvas, null, (iconNum%5)*mBtn.w, (iconNum/5)*mBtn.h,
//                    mBtn.w, mBtn.h, mBtn.x+(btnArea-mBtn.w)/2, mBtn.y);

        }

        int clipY = backBtn.clipY;
        if (backBtn.clickState == ButtonEnty.ButtonClickOn) {
            clipY = 41;
        }
        CanvasUtil.drawClip(img_mainBtn, mCanvas, backBtn.clipX, clipY,
                backBtn.clipW, backBtn.clipH, backBtn.drawX, backBtn.drawY);

        switch (appClass.gameState) {
            case INN.GAME_HOME:
                CanvasUtil.drawClip(img_mainBtn, mCanvas, 270, 0,
                        backBtn.clipW, 18, backBtn.drawX, backBtn.drawY + 12);
                break;
            default:
                CanvasUtil.drawClip(img_mainBtn, mCanvas, 270, 18,
                        backBtn.clipW, 18, backBtn.drawX, backBtn.drawY + 12);
                break;
        }
    }*/

    /*private void drawStatusTab(Canvas mCanvas){
        Paint paint = new Paint();
        int drawY = 6;
        int drawX = (appClass.getGameCanvasWidth() - 120) / 5;
        paint.setColor(Color.argb(255, 50, 50, 50));
        paint.setTextSize(20);
        CanvasUtil.drawString(mCanvas, userEnty.Name + "(Lv " + userEnty.LEVEL + ")", paint, 10, drawY);
//        CanvasUtil.drawClip(img_mainBtn, mCanvas, mBtn.clipX, clipY,
//                mBtn.clipW, mBtn.clipH, drawX * 1, drawY);
        CanvasUtil.drawClip(img_mainBtn, mCanvas, memberIcon.clipX, memberIcon.clipY,
                memberIcon.clipW, memberIcon.clipH, memberIcon.drawX, memberIcon.drawY);
        CanvasUtil.drawString(mCanvas, Integer.toString(userEnty.MEMBERLIST.size()), paint,
                memberIcon.drawX + memberIcon.clipW + 5, memberIcon.drawY + 3);
        CanvasUtil.drawClip(img_mainBtn, mCanvas, partyIcon.clipX, partyIcon.clipY,
                partyIcon.clipW, partyIcon.clipH, partyIcon.drawX, partyIcon.drawY);
        CanvasUtil.drawString(mCanvas, Integer.toString(userEnty.PARTYLIST.size()), paint,
                partyIcon.drawX + partyIcon.clipW + 5, partyIcon.drawY + 3);
        CanvasUtil.drawClip(img_mainBtn, mCanvas, goldIcon.clipX, goldIcon.clipY,
                goldIcon.clipW, goldIcon.clipH, goldIcon.drawX, goldIcon.drawY);
        CanvasUtil.drawString(mCanvas, Integer.toString(userEnty.GOLD), paint,
                goldIcon.drawX + goldIcon.clipW + 5, goldIcon.drawY + 3);
        CanvasUtil.drawString(mCanvas, "AP " + userEnty.AP, paint, drawX * 4, drawY);
        CanvasUtil.drawString(mCanvas, "Turn " + userEnty.TURN, paint, appClass.getGameCanvasWidth() - 180, drawY);
    }*/

    /*private void drawTurnStartAlert(Canvas mCanvas) {
        CanvasUtil.drawBitmap(alertBox, mCanvas, (appClass.getGameCanvasWidth() - alertBox.getWidth()) / 2
                , (appClass.getGameCanvasHeight() - alertBox.getHeight()) / 2);
        int alertBtnClipX = alertBtn.clipX;
        if (alertBtn.clickState == ButtonEnty.ButtonClickOn) {
            alertBtnClipX += alertBtn.clipW;
        }
        drawClip(img_mainBtn, mCanvas,
                alertBtnClipX, alertBtn.clipY,
                alertBtn.clipW, alertBtn.clipH,
                alertBtn.drawX, alertBtn.drawY);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
//        paint.setAntiAlias(true);
        paint.setTextSize(20);
        int strX = (appClass.getGameCanvasWidth() - 400) / 2 + 30;
        int strY = (appClass.getGameCanvasHeight() - 300) / 2;
        CanvasUtil.drawString(mCanvas, userEnty.TURN + " 턴", paint, strX, strY);
        CanvasUtil.drawString(mCanvas, "완료된 퀘스트 수 - ", paint, strX, strY + 30);
        CanvasUtil.drawString(mCanvas, "길드 수입 - " + userEnty.eventEnty.Gold + "Gold", paint, strX, strY + 80);
//        CanvasUtil.drawString(mCanvas, "충전된 AP - " + turnManager.turnEnty.AP + "point", paint, strX, strY + 130);
        CanvasUtil.drawString(mCanvas, "새로운 퀘스트 수 - " + userEnty.eventEnty.QuestIDList.size(), paint, strX, strY + 180);
    }*/

    /*public void drawMenuBar(Canvas mCanvas) {
        Paint paint = new Paint();
//        paint.setAntiAlias(true);
//        CanvasUtil.drawBox(mCanvas, Color.argb(255, 40, 50, 80), true,
//                0, mMenuTabBarY, appClass.getGameCanvasWidth(), appClass.getGameCanvasHeight() - mMenuTabBarY);


        //메뉴바
        int barNum = appClass.getGameCanvasWidth() / img_menuBar.getWidth();
        for (int i = 0; i <= barNum; i++) {
            CanvasUtil.drawBitmap(img_menuBar, mCanvas, img_menuBar.getWidth() * i, mMenuTabBarY);
        }

        int drawY = 6;
        int drawX = (appClass.getGameCanvasWidth() - 120) / 5;
        paint.setColor(Color.argb(255, 50, 50, 50));
        paint.setTextSize(20);
        CanvasUtil.drawString(mCanvas, userEnty.Name + "(Lv " + userEnty.LEVEL + ")", paint, 10, drawY);
//        CanvasUtil.drawClip(img_mainBtn, mCanvas, mBtn.clipX, clipY,
//                mBtn.clipW, mBtn.clipH, drawX * 1, drawY);
        CanvasUtil.drawClip(img_mainBtn, mCanvas, memberIcon.clipX, memberIcon.clipY,
                memberIcon.clipW, memberIcon.clipH, memberIcon.drawX, memberIcon.drawY);
        CanvasUtil.drawString(mCanvas, Integer.toString(userEnty.MEMBERLIST.size()), paint,
                memberIcon.drawX + memberIcon.clipW + 5, memberIcon.drawY + 3);
        CanvasUtil.drawClip(img_mainBtn, mCanvas, partyIcon.clipX, partyIcon.clipY,
                partyIcon.clipW, partyIcon.clipH, partyIcon.drawX, partyIcon.drawY);
        CanvasUtil.drawString(mCanvas, Integer.toString(userEnty.PARTYLIST.size()), paint,
                partyIcon.drawX + partyIcon.clipW + 5, partyIcon.drawY + 3);
        CanvasUtil.drawClip(img_mainBtn, mCanvas, goldIcon.clipX, goldIcon.clipY,
                goldIcon.clipW, goldIcon.clipH, goldIcon.drawX, goldIcon.drawY);
        CanvasUtil.drawString(mCanvas, Integer.toString(userEnty.GOLD), paint,
                goldIcon.drawX + goldIcon.clipW + 5, goldIcon.drawY + 3);
        CanvasUtil.drawString(mCanvas, "AP " + userEnty.AP, paint, drawX * 4, drawY);
        CanvasUtil.drawString(mCanvas, "Turn " + userEnty.TURN, paint, appClass.getGameCanvasWidth() - 180, drawY);

    }*/
/*
    private void drawPartyMenuButton(Canvas mCanvas, Paint paint) {
        for(ButtonEnty mBtn : gameMain.menuButtonList){
            int clipY = mBtn.clipY;
            if(mBtn.clickState == ButtonEnty.ButtonClickOn){
                clipY += mBtn.clipH;
            }
            CanvasUtil.drawClip(menu_icon, mCanvas, null, mBtn.clipX, clipY,
                    mBtn.clipW, mBtn.clipH, mBtn.drawX, mBtn.drawY);
//            CanvasUtil.drawClip(menu_icon, mCanvas, null, (iconNum%5)*mBtn.w, (iconNum/5)*mBtn.h,
//                    mBtn.w, mBtn.h, mBtn.x+(btnArea-mBtn.w)/2, mBtn.y);

        }
    }*/

    /*public void drawHomeCard(Canvas mCanvas) {
        for (ButtonEnty mBtn : menuButtonList) {
            if (mBtn.clickState == ButtonEnty.ButtonClickOn) {

            }
            CanvasUtil.drawBitmap(img_mainBtn, mCanvas, mBtn.drawX, mBtn.drawY);
            CanvasUtil.drawClip(img_mainBtn, mCanvas, mBtn.clipW + 1, mBtn.num * 18,
                    64, 18, mBtn.drawX + (mBtn.clipW - 64) / 2, mBtn.drawY + 56);
//            CanvasUtil.drawClip(menu_icon, mCanvas, null, (iconNum%5)*mBtn.w, (iconNum/5)*mBtn.h,
//                    mBtn.w, mBtn.h, mBtn.x+(btnArea-mBtn.w)/2, mBtn.y);

        }
    }*/
}
