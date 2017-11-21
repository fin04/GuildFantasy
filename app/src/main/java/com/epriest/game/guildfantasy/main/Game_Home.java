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

    public int mMainScreenY;
    public int mMainScreenHeight;


    public Bitmap bg;
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
//        img_card = GLUtil.loadAssetsBitmap(appClass, "main/membercard.png", null);


        mMainScreenY = 0;
        setIcon(gameMain.canvasW, gameMain.canvasH);

    }

    private ArrayList<ButtonEnty> setIcon(int canvasW, int canvasH) {
        ArrayList<ButtonEnty> menuButtonList = new ArrayList<>();
        switch (gameMain.appClass.gameState) {
            default:
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
        gameMain.onTouchMenuIcon();
    }
}

