package com.epriest.game.guildfantasy.main;

import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;

import com.epriest.game.CanvasGL.util.Game;
import com.epriest.game.CanvasGL.util.GameUtil;
import com.epriest.game.CanvasGL.util.TextUtil;
import com.epriest.game.guildfantasy.main.enty.MapEnty;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by darka on 2016-10-31.
 */

public class Game_Home extends Game {

    public Game_Main gameMain;
    //    public PlayerEnty playerEnty;
//    public ArrayList<ButtonEnty> menuButtonList;
    public ArrayList<String> prologStrList;
    public boolean isAlert;
    public int maxChar = 18;
    public boolean isNext;
    public int mapDrawW, mapDrawH;
    public int mapTotalW, mapTotalH;

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


        int tileW = gameMain.mapLayer.getTileWidth();
        int tileH = gameMain.mapLayer.getTileHeight();

        gameMain.mapLayer.mMapTileRowNum = gameMain.mapLayer.getLayers().get(0).getWidth();
        if (gameMain.mapLayer.mMapTileRowNum * tileW > gameMain.appClass.getGameCanvasWidth()) {
            gameMain.mapLayer.mMapTileRowNum = gameMain.appClass.getGameCanvasWidth() / tileW + 2;
        }

        gameMain.mapLayer.mMapTileColumnNum = 9;

        //그려지는 맵의 크기
        mapDrawW = gameMain.mapLayer.mMapTileRowNum * tileW;
        mapDrawH = gameMain.mapLayer.mMapTileColumnNum * tileH;

        //맵의 총 크기
        mapTotalW = gameMain.mapLayer.terrainColumnList.get(0).length*tileW;
        mapTotalH = gameMain.mapLayer.terrainColumnList.size()*tileH;
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
        if (gameMain.onTouchEvent(event))
            return;
        boolean hasTouchMap = false;
        if (GameUtil.equalsTouch(gameMain.appClass.touch, 0, Game_Main.statusBarH, mapDrawW, mapDrawH)) {
            hasTouchMap = true;
        }
        switch (gameMain.appClass.touch.action) {
            case MotionEvent.ACTION_DOWN:
                if (hasTouchMap) {
                    gameMain.mapLayer.isMapMove = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (hasTouchMap && gameMain.mapLayer.isMapMove) {
                    gameMain.mapLayer.mMapAxis.x = (int) (gameMain.appClass.touch.mPosX);
                    if (gameMain.mapLayer.mMapAxis.x > 0)
                        gameMain.mapLayer.mMapAxis.x = 0;
                    else if(gameMain.mapLayer.mMapAxis.x < (mapTotalW - mapDrawW)*-1)
                        gameMain.mapLayer.mMapAxis.x = (mapTotalW - mapDrawW)*-1;

                    gameMain.mapLayer.mMapAxis.y = (int) (gameMain.appClass.touch.mPosY);
                    if (gameMain.mapLayer.mMapAxis.y < 0)
                        gameMain.mapLayer.mMapAxis.y = 0;
                    else if(gameMain.mapLayer.mMapAxis.y > mapTotalH - mapDrawH)
                        gameMain.mapLayer.mMapAxis.y = mapTotalH - mapDrawH;

                    gameMain.mapLayer.getTileNum(gameMain.mapLayer.LeftTopTileNum,
                            gameMain.mapLayer.mMapAxis.x, gameMain.mapLayer.mMapAxis.y);


                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (gameMain.mapLayer.isMapMove) {
                    gameMain.mapLayer.isMapMove = false;
                }
                break;
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
