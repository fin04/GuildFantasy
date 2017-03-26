package com.epriest.game.guildfantasy.main;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.Toast;

import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.CanvasGL.util.Game;
import com.epriest.game.CanvasGL.util.GameUtil;
import com.epriest.game.CanvasGL.util.TextUtil;
import com.epriest.game.guildfantasy.enty.ButtonEnty;
import com.epriest.game.guildfantasy.enty.EquipEnty;
import com.epriest.game.guildfantasy.enty.MemberEnty;
import com.epriest.game.guildfantasy.enty.PlayerEnty;
import com.epriest.game.guildfantasy.enty.StatusEnty;
import com.epriest.game.guildfantasy.util.PPreference;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by darka on 2016-10-31.
 */

public class Game_Home extends Game {

    private Game_Main gameMain;
//    public PlayerEnty playerEnty;
//    public ArrayList<ButtonEnty> menuButtonList;
    public ArrayList<String> prologStrList;
    public boolean isAlert;
    public int maxChar = 18;
    public boolean isNext;

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


    }


    @Override
    public void gStop() {

    }

    @Override
    public void gUpdate() {

    }



    private void startProlog1(){
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
            }else{
                if (strArr[i].charAt(0) == '[') {
                    isProlog = false;
                    break;
                } else {
                    int strCnt = strArr[i].length() / maxChar;
                    if (strCnt == 0 || strArr[i].charAt(0) == '#') {
                        prologStrList.add(strArr[i]);
                    } else {
                        for (int j = 0; j <= strCnt; j++) {
                            int endStrNum = maxChar+maxChar*j;
                            if(strArr[i].length() < endStrNum){
                                prologStrList.add(strArr[i].substring(maxChar*j, strArr[i].length()));
                                break;
                            }
                            prologStrList.add(strArr[i].substring(maxChar*j, endStrNum));
                        }
                    }

                }
            }
        }
    }

    @Override
    public void gOnTouchEvent(MotionEvent event) {
        gameMain.onTouchEvent(event);
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
