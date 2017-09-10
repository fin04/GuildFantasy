package com.epriest.game.guildfantasy.main;

import android.graphics.Bitmap;
import android.view.MotionEvent;
import android.widget.Toast;

import com.epriest.game.CanvasGL.graphics.GLUtil;
import com.epriest.game.CanvasGL.util.Game;
import com.epriest.game.CanvasGL.util.GameUtil;
import com.epriest.game.CanvasGL.util.TextUtil;
import com.epriest.game.guildfantasy.main.enty.ButtonEnty;
import com.epriest.game.guildfantasy.util.INN;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by darka on 2016-10-31.
 */

public class Game_Home extends Game {

    public Game_Main gameMain;
    public ArrayList<String> prologStrList;
    public boolean isAlert;
    public int maxChar = 18;
    public boolean isNext;

    public ArrayList<ButtonEnty> menuButtonList;

    public int mMainScreenY;
    public int mMainScreenHeight;
    public int mMenuTabBarY;

    public ButtonEnty alertBtn;

    public ButtonEnty optionBtn;
    public ButtonEnty menIcon;
    public ButtonEnty feedIcon;
    public ButtonEnty goldIcon;

    public Bitmap bg;
    public Bitmap img_char_01;
//    private Bitmap img_card;

    public Bitmap img_mainBtn;
    public Bitmap img_homeBtn;
    public Bitmap img_menuBar;
    public Bitmap alertBox;

    public Game_Home(Game_Main gameMain) {
        this.gameMain = gameMain;
    }

    @Override
    public void gStart() {
//        playerEnty = checkPlayerData();
//        if(playerEnty == null){
//            // 프롤로그를 실행하고 플레이어를 작성
////            startProlog1();
//            playerEnty = setTestPlayerData();
//        }
//        menuButtonList = new ArrayList();

        bg = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/town.jpg", null);
        img_char_01 = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/char_01.jpg", null);
//        img_card = GLUtil.loadAssetsBitmap(appClass, "main/membercard.png", null);

        img_homeBtn = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/home_btn.png", null);
        img_mainBtn = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/main_btn.png", null);
        img_menuBar = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/statusbar.png", null);
        alertBox = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/alertbox.png", null);

        mMainScreenY = 0;
        mMenuTabBarY = gameMain.canvasH-300-gameMain.statusBarH;
        menuButtonList = setMenuIcon(gameMain.canvasW, gameMain.canvasH);

    }

    private ArrayList<ButtonEnty> setMenuIcon(int canvasW, int canvasH) {
        ArrayList<ButtonEnty> menuButtonList = new ArrayList<>();
        switch (gameMain.appClass.gameState) {
            default:
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

                    mBtn.drawX = 20 + ((mBtn.clipW + 5) * (i%3));
                    mBtn.drawY = mMenuTabBarY + gameMain.statusBarH + mBtn.clipH*(i / 3);
                    menuButtonList.add(mBtn);
                }

/*                //play button
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
                menuButtonList.add(mBtn);*/

                optionBtn = new ButtonEnty();
                optionBtn.clipW = 103;
                optionBtn.clipH = 52;
                optionBtn.clipX = 121;
                optionBtn.clipY = 0;
                optionBtn.name = "Back";
                optionBtn.drawX = canvasW - optionBtn.clipW - 15;
                optionBtn.drawY = mMenuTabBarY + (gameMain.statusBarH - optionBtn.clipH) / 2;


                menIcon = new ButtonEnty();
                menIcon.name = "Men";
                menIcon.drawX = (gameMain.appClass.getGameCanvasWidth() - 120) / 5;
                menIcon.drawY = 5;
                menIcon.clipW = 24;
                menIcon.clipH = 34;
                menIcon.clipX = 136;
                menIcon.clipY = 92;

                feedIcon = new ButtonEnty();
                feedIcon.name = "Feed";
                feedIcon.drawX = (gameMain.appClass.getGameCanvasWidth() - 120) / 5 + 80;
                feedIcon.drawY = 5;
                feedIcon.clipW = 32;
                feedIcon.clipH = 32;
                feedIcon.clipX = 100;
                feedIcon.clipY = 92;

                goldIcon = new ButtonEnty();
                goldIcon.name = "Gold";
                goldIcon.drawX = (gameMain.appClass.getGameCanvasWidth() - 120) / 5 + 160;
                goldIcon.drawY = 5;
                goldIcon.clipW = 36;
                goldIcon.clipH = 27;
                goldIcon.clipX = 101;
                goldIcon.clipY = 126;

                alertBtn = new ButtonEnty();
                alertBtn.name = "Alert";
                alertBtn.drawX = (gameMain.appClass.getGameCanvasWidth() - alertBox.getWidth()) / 2 + alertBox.getWidth() - 100;
                alertBtn.drawY = (gameMain.appClass.getGameCanvasHeight() - alertBox.getHeight()) / 2 + alertBox.getHeight() - 100;
                alertBtn.clipW = 90;
                alertBtn.clipH = 90;
                alertBtn.clipX = 231;
                alertBtn.clipY = 173;
                break;

            case INN.GAME_INN:
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
    }


    @Override
    public void gStop() {

    }

    @Override
    public void gUpdate() {

    }

    private void startProlog1() {
        String str = null;
        try {
            str = TextUtil.getStringToAsset(gameMain.appClass, "main/intro.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] strArr = str.split("\n");
        prologStrList = new ArrayList<String>();
        boolean isProlog = false;
        for (int i = 0; i < strArr.length; i++) {
            if (!isProlog) {
                if (strArr[i].charAt(0) == '[' && strArr[i].contains("prolog")) {
                    isProlog = true;
                    continue;
                }
            } else {
                if (strArr[i].charAt(0) == '[') {
                    isProlog = false;
                    break;
                } else {
                    int strCnt = strArr[i].length() / maxChar;
                    if (strCnt == 0 || strArr[i].charAt(0) == '#') {
                        prologStrList.add(strArr[i]);
                    } else {
                        for (int j = 0; j <= strCnt; j++) {
                            int endStrNum = maxChar + maxChar * j;
                            if (strArr[i].length() < endStrNum) {
                                prologStrList.add(strArr[i].substring(maxChar * j, strArr[i].length()));
                                break;
                            }
                            prologStrList.add(strArr[i].substring(maxChar * j, endStrNum));
                        }
                    }

                }
            }
        }
    }

    @Override
    public void gOnTouchEvent(MotionEvent event) {
        if (gameMain.playerEnty.isStartTurnAlert) {
            if (GameUtil.equalsTouch(gameMain.appClass.touch, alertBtn.drawX, alertBtn.drawY, alertBtn.clipW, alertBtn.clipH)) {
                if (gameMain.appClass.touch.action == MotionEvent.ACTION_UP) {
                    alertBtn.clickState = ButtonEnty.ButtonClickOff;
                    gameMain.playerEnty.isStartTurnAlert = false;
//                    playerEnty = DataManager.setChangeEvent(dbAdapter, playerEnty);

                    return;
                } else {
                    alertBtn.clickState = ButtonEnty.ButtonClickOn;
                }
            } else
                alertBtn.clickState = ButtonEnty.ButtonClickOn;
            return;
        }

        int turnBtnNum = menuButtonList.size() - 1;
        if (GameUtil.equalsTouch(gameMain.appClass.touch, menuButtonList.get(turnBtnNum).drawX, menuButtonList.get(turnBtnNum).drawY,
                menuButtonList.get(turnBtnNum).clipW, menuButtonList.get(turnBtnNum).clipH)) {
            if (gameMain.appClass.touch.action == MotionEvent.ACTION_UP) {
                menuButtonList.get(turnBtnNum).clickState = ButtonEnty.ButtonClickOff;
                switch (gameMain.appClass.gameState) {
                    case INN.GAME_HOME:
                        gameMain.turnManager.turnCycle(1);
                        break;
                }
            } else {
                menuButtonList.get(turnBtnNum).clickState = ButtonEnty.ButtonClickOn;
            }
            return;
        } else {
            menuButtonList.get(turnBtnNum).clickState = ButtonEnty.ButtonClickOff;
        }

        if (GameUtil.equalsTouch(gameMain.appClass.touch, optionBtn.drawX, optionBtn.drawY, optionBtn.clipW, optionBtn.clipH)) {
            if (gameMain.appClass.touch.action == MotionEvent.ACTION_UP) {
                optionBtn.clickState = ButtonEnty.ButtonClickOff;
                Toast.makeText(gameMain.appClass, "menu", Toast.LENGTH_SHORT).show();
            } else {
                optionBtn.clickState = ButtonEnty.ButtonClickOn;
            }
            return;
        } else {
            optionBtn.clickState = ButtonEnty.ButtonClickOff;
        }


        if (gameMain.appClass.gameState != INN.GAME_HOME)
            return;

        for (ButtonEnty mBtn : menuButtonList) {
            if (GameUtil.equalsTouch(gameMain.appClass.touch, mBtn.drawX, mBtn.drawY, mBtn.clipW, mBtn.clipH)) {
                mBtn.clickState = ButtonEnty.ButtonClickOn;
                if (gameMain.appClass.touch.action == MotionEvent.ACTION_UP) {
                    mBtn.clickState = ButtonEnty.ButtonClickOff;

                    if (mBtn.name.equals(INN.menuIconName[0]))
                        gameMain.mainButtonAct(INN.GAME_INN, 0);
                    else if (mBtn.name.equals(INN.menuIconName[1]))
                        gameMain.mainButtonAct(INN.GAME_BAR, INN.MODE_MEMBER_RECRUIT);
                    else if (mBtn.name.equals(INN.menuIconName[2]))
                        gameMain.mainButtonAct(INN.GAME_TEMPLE, 0);
                    else if (mBtn.name.equals(INN.menuIconName[3]))
                        gameMain.mainButtonAct(INN.GAME_SHOP, 0);
                    else if (mBtn.name.equals(INN.menuIconName[4]))
                        gameMain.mainButtonAct(INN.GAME_GUILD, 0);
                    else if (mBtn.name.equals(INN.menuIconName[5]))
                        gameMain.mainButtonAct(INN.GAME_GATE, 0);
                    else if (mBtn.name.equals("Menu"))
                        gameMain.mainButtonAct(INN.GAME_OPTION, 0);
                }
                return;
            } else {
                mBtn.clickState = ButtonEnty.ButtonClickOff;
            }
        }

        /*for(ButtonEnty mBtn : menuButtonList){
            if(GameUtil.equalsTouch(appClass.touch, mBtn.x, mBtn.y, mBtn.w, mBtn.h)){
                mBtn.clickState = ButtonEnty.ButtonClickOn;
                if(appClass.touch.Action == MotionEvent.ACTION_UP) {
                    mBtn.clickState = ButtonEnty.ButtonClickOff;
                    Toast.makeText(appClass.getBaseContext(), mBtn.name, Toast.LENGTH_SHORT).show();
                }
            }else{
                mBtn.clickState = ButtonEnty.ButtonClickOff;
            }
        }*/

    }
}
