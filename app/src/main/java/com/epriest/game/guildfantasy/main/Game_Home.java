package com.epriest.game.guildfantasy.main;

import android.graphics.Bitmap;
import android.view.MotionEvent;
import android.widget.Toast;

import com.epriest.game.CanvasGL.graphics.GLUtil;
import com.epriest.game.CanvasGL.util.Game;
import com.epriest.game.CanvasGL.util.GameUtil;
import com.epriest.game.CanvasGL.util.TextUtil;
import com.epriest.game.guildfantasy.main.enty.ButtonEnty;
import com.epriest.game.guildfantasy.main.play.AlertManager;
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

    public int mMainScreenY;
    public int mMainScreenHeight;

    public ArrayList<ButtonEnty> menuButtonList;
    public ButtonEnty turnButton;

    public Bitmap bg;
    public Bitmap img_homeBtn;
//    public Bitmap img_char_01;
//    private Bitmap img_card;

    public Game_Home(Game_Main gameMain) {
        this.gameMain = gameMain;
    }

    @Override
    public void gStart() {
//        userEnty = checkPlayerData();
//        if(userEnty == null){
//            // 프롤로그를 실행하고 플레이어를 작성
////            startProlog1();
//            userEnty = setTestPlayerData();
//        }
//        menuButtonList = new ArrayList();

        bg = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/town.jpg", null);
        img_homeBtn = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/home_btn.png", null);
//        img_card = GLUtil.loadAssetsBitmap(appClass, "main/membercard.png", null);


        mMainScreenY = 0;
        setMenuIcon(gameMain.canvasW, gameMain.canvasH);

        turnButton = new ButtonEnty();
        turnButton.name = INN.MENU_TURNEND;
        turnButton.clipW = 220;
        turnButton.clipH = 150;
        turnButton.clipX = 0;
        turnButton.clipY = 0;
        turnButton.drawX = gameMain.canvasW - (turnButton.clipW + 5);
        turnButton.drawY = gameMain.canvasH - (turnButton.clipH * 3);
    }

    private void setMenuIcon(int canvasW, int canvasH) {
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



        /*ArrayList<ButtonEnty> menuButtonList = new ArrayList<>();
        switch (gameMain.appClass.gameState) {
            default:
*//*                //play button
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
                menuButtonList.add(mBtn);*//*



                break;

            case INN.GAME_MEMBER:
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
        }*/
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
        // 홈메뉴 버튼
        if (onMeunButtonTouch())
            return;

        // 스테이터스 버튼
        if (gameMain.onStatusTouch())
            return;

        // 턴 버튼
        if (onTurnButtonTouch())
            return;

        // 턴 시작 알림
        if (gameMain.alertManager.showAlertType == AlertManager.ALERT_TYPE_NEXT_TURNSTART) {
            if(gameMain.alertManager.onAlertTouch())
                return;
        }
    }

    public boolean onTurnButtonTouch() {
        if (GameUtil.equalsTouch(gameMain.appClass.touch, turnButton.drawX, turnButton.drawY,
                turnButton.clipW, turnButton.clipH)) {
            turnButton.clickState = ButtonEnty.ButtonClickOn;
            if (gameMain.appClass.touch.action == MotionEvent.ACTION_UP) {
                turnButton.clickState = ButtonEnty.ButtonClickOff;
                gameMain.alertManager.showAlertType = AlertManager.ALERT_TYPE_CURRENT_TURNEND;
                gameMain.alertManager.onAlertTouch();
                return true;
            }
        } else {
            turnButton.clickState = ButtonEnty.ButtonClickOff;
        }
        return false;
    }

    public boolean onMeunButtonTouch() {
        for (ButtonEnty mBtn : menuButtonList) {
            if (GameUtil.equalsTouch(gameMain.appClass.touch, mBtn.drawX, mBtn.drawY, mBtn.clipW, mBtn.clipH)) {
                mBtn.clickState = ButtonEnty.ButtonClickOn;
                if (gameMain.appClass.touch.action == MotionEvent.ACTION_UP) {
                    mBtn.clickState = ButtonEnty.ButtonClickOff;

                    if (mBtn.name.equals(INN.MENU_INN))
                        gameMain.mainButtonAct(INN.GAME_MEMBER, 0);
                    else if (mBtn.name.equals(INN.MENU_BAR))
                        gameMain.mainButtonAct(INN.GAME_RECRUIT, 0);
                    else if (mBtn.name.equals(INN.MENU_GUILD))
                        gameMain.mainButtonAct(INN.GAME_PARTY, 0);
                    else if (mBtn.name.equals(INN.MENU_SHOP))
                        gameMain.mainButtonAct(INN.GAME_ITEM, 0);
                    else if (mBtn.name.equals(INN.MENU_TEMPLE))
                        gameMain.mainButtonAct(INN.GAME_SKILL, 0);
                    else if (mBtn.name.equals(INN.MENU_GATE))
                        gameMain.mainButtonAct(INN.GAME_MOVE, 0);
//                    else if (mBtn.name.equals("Menu"))
//                        gameMain.mainButtonAct(INN.GAME_OPTION, 0);
                    return true;
                }
            } else {
                mBtn.clickState = ButtonEnty.ButtonClickOff;
            }
        }
        return false;
    }
}

