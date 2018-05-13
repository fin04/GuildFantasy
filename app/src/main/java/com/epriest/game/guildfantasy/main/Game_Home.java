package com.epriest.game.guildfantasy.main;

import android.graphics.Bitmap;
import android.view.MotionEvent;

import com.epriest.game.CanvasGL.graphics.GLUtil;
import com.epriest.game.CanvasGL.util.Game;
import com.epriest.game.CanvasGL.util.GameUtil;
import com.epriest.game.CanvasGL.util.TextUtil;
import com.epriest.game.guildfantasy.main.enty.ButtonEnty;
import com.epriest.game.guildfantasy.main.play.DataManager;
import com.epriest.game.guildfantasy.main.play.GameDialog;
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

    public int canvasW, canvasH;

    public int scrollX, prevScrollX;
    public int mMainScreenY;
    public int mMainScreenHeight;

    public ArrayList<ButtonEnty> menuButtonList;
    public ButtonEnty turnButton;

    public Bitmap bg;
    public Bitmap img_homeBtn;
    public Bitmap img_defCha;

//    public GameDialog turnOffDialog;
//    public GameDialog turnOnDialog;
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

        this.canvasW = gameMain.appClass.getGameCanvasWidth();
        this.canvasH = gameMain.appClass.getGameCanvasHeight();

        bg = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/bg_town.jpg", null);
        img_homeBtn = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/home_btn.png", null);
        img_defCha = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/def_cha.png", null);
//        img_card = GLUtil.loadAssetsBitmap(appClass, "main/membercard.png", null);

        mMainScreenY = 0;
        setMenuIcon(canvasW, canvasH);

        turnButton = new ButtonEnty();
        turnButton.name = INN.MENU_TURNEND;
        turnButton.clipW = 220;
        turnButton.clipH = 150;
        turnButton.clipX = 0;
        turnButton.clipY = 0;
        turnButton.drawX = canvasW - (turnButton.clipW + 5);
        turnButton.drawY = canvasH - (turnButton.clipH * 3);

//        setTurnOffDialog();
//        setTurnOnDialog();
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
    }


    @Override
    public void gStop() {

    }

    @Override
    public void gUpdate() {

    }

//    private void setTurnOffDialog() {
//        try {
//            turnOffDialog = new GameDialog(gameMain.appClass);
//            turnOffDialog.setTwoButtonListener(new GameDialog.onTwoClickListener() {
//                @Override
//                public void onPositiveClick() {
//                    gameMain.showAlertType = GameDialog.ALERT_TYPE_NONE;
//                    gameMain.turnManager.turnCycle(gameMain.userEnty.TURN++);
//                }
//
//                @Override
//                public void onNegativeClick() {
//
//                    gameMain.showAlertType = GameDialog.ALERT_TYPE_NONE;
//                }
//            });
//            turnOffDialog.setTitle("다음 턴");
//            turnOffDialog.setText("현재 턴을 끝내겠습니까?");
//            turnOffDialog.setButtonTitle("턴 종료");
//            turnOffDialog.setButtonTitle("취소");
//        } catch (Exception e) {
//        }
//    }

//    private void setTurnOnDialog() {
//        try {
//            turnOnDialog = new GameDialog(gameMain.appClass);
//            turnOnDialog.setOnButtonListener(new GameDialog.onOneClickListener() {
//                @Override
//                public void onClick() {
//                    gameMain.showAlertType = GameDialog.ALERT_TYPE_NONE;
//                }
//            });
//            turnOnDialog.setTitle(gameMain.userEnty.TURN + "턴 시작");
//            StringBuilder sb = new StringBuilder("Turn : ");
//            sb.append(gameMain.userEnty.TURN);
//            sb.append("\n");
//            sb.append("Clear Quest : ");
//            sb.append("\n");
//            sb.append("Income : ");
//            sb.append(gameMain.userEnty.eventEnty.Gold + "Gold");
//            sb.append("\n");
//            sb.append("New Quest : ");
//            sb.append(gameMain.userEnty.eventEnty.QuestIDList.size());
//            turnOnDialog.setText(sb.toString());
//            turnOnDialog.setButtonTitle("확인");
//        } catch (Exception e) {
//        }
//    }

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
//
//        // 턴 종료 알림
//        if (gameMain.showAlertType == GameDialog.ALERT_TYPE_CURRENT_TURNOFF) {
//            if (turnOffDialog.onTouch())
//                return;
//        } else if (gameMain.showAlertType == GameDialog.ALERT_TYPE_NEXT_TURNON) {
//            if (turnOnDialog.onTouch())
//                return;
//        }
//
//        if (gameMain.alertManager.showAlertType == AlertManager.ALERT_TYPE_NEXT_TURNSTART) {
//            if(gameMain.alertManager.onAlertTouch())
//                return;
//        }

        // scroll
        onScrollBg();
    }

    public boolean onTurnButtonTouch() {
        if (GameUtil.equalsTouch(gameMain.appClass.touch, turnButton.drawX, turnButton.drawY,
                turnButton.clipW, turnButton.clipH)) {
            turnButton.clickState = ButtonEnty.ButtonClickOn;
            if (gameMain.appClass.touch.action == MotionEvent.ACTION_UP) {
                turnButton.clickState = ButtonEnty.ButtonClickOff;
                gameMain.Dialog.setPositiveButtonListener(new GameDialog.onPositiveButtonListener() {
                    @Override
                    public void onClick() {
                        gameMain.turnManager.turnCycle(gameMain.userEnty.TURN++);
                    }
                });
                gameMain.Dialog.setOnNegativeButtonListener(new GameDialog.onNegativeButtonListener() {
                    @Override
                    public void onClick() {

                    }
                });
                gameMain.Dialog.setTitle("다음 턴");
                gameMain.Dialog.setText("현재 턴을 끝내겠습니까?");
                gameMain.Dialog.setPositiveBtnTitle("턴 종료");
                gameMain.Dialog.setNegativeBtnTitle("취소");
                gameMain.Dialog.show();
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
                        gameMain.mainButtonAct(INN.GAME_MEMBER);
                    else if (mBtn.name.equals(INN.MENU_BAR))
                        gameMain.mainButtonAct(INN.GAME_RECRUIT);
                    else if (mBtn.name.equals(INN.MENU_GUILD))
                        gameMain.mainButtonAct(INN.GAME_QUESTLIST);
                    else if (mBtn.name.equals(INN.MENU_SHOP))
                        gameMain.mainButtonAct(INN.GAME_SHOP);
                    else if (mBtn.name.equals(INN.MENU_TEMPLE))
                        gameMain.mainButtonAct(INN.GAME_TEMPLE);
                    else if (mBtn.name.equals(INN.MENU_GATE))
                        gameMain.mainButtonAct(INN.GAME_MOVE);
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

    public void onScrollBg() {
        int bgHalfWidth = bg.getWidth() / 2;
        if (gameMain.appClass.touch.action == MotionEvent.ACTION_DOWN) {
            prevScrollX = scrollX;
        }
        scrollX = prevScrollX - (int) (gameMain.appClass.touch.mLastTouchX - gameMain.appClass.touch.mDownX);
        int maxScrollX = bgHalfWidth - canvasW / 2;
        int minScrollX = -bgHalfWidth + canvasW / 2;
        if (scrollX < minScrollX) {
            scrollX = minScrollX;
        } else if (scrollX > maxScrollX) {
            scrollX = maxScrollX;
        }
    }
}

