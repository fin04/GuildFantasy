package com.epriest.game.guildfantasy.main;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.MotionEvent;

import com.epriest.game.CanvasGL.graphics.GLUtil;
import com.epriest.game.CanvasGL.util.Game;
import com.epriest.game.CanvasGL.util.GameUtil;
import com.epriest.game.guildfantasy.main.enty.MapEnty;
import com.epriest.game.guildfantasy.util.INN;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by darka on 2017-07-28.
 */

public class Game_Map extends Game {
    public Game_Main gameMain;

    public int mapDrawW, mapDrawH;
    public int mapTotalW, mapTotalH;
    private int tileTouchArea;

    private int mMapScreenTop;

    public MapEnty.MapLayer mapLayer;

    public int mMainScreenY;
//    public int mMainScreenHeight;
    public int mMenuTabBarY;

    public Bitmap img_menuBar;

    public Game_Map(Game_Main gameMain) {
        this.gameMain = gameMain;
    }

    @Override
    public void gStart() {
        img_menuBar = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/statusbar_tile.png", null);

        setMapLayer();
        int tileW = mapLayer.getTileWidth();
        int tileH = mapLayer.getTileHeight();
        tileTouchArea = tileW/3;
        mMapScreenTop = 0;
        mapLayer.mMapTileRowNum = mapLayer.getLayers().get(0).getWidth();
        if (mapLayer.mMapTileRowNum * tileW > gameMain.appClass.getGameCanvasWidth()) {
            mapLayer.mMapTileRowNum = gameMain.appClass.getGameCanvasWidth() / tileW + 2;
        }

        mapLayer.mMapTileColumnNum = 11;

        //그려지는 맵의 크기
        mapDrawW = mapLayer.mMapTileRowNum * tileW;
        mapDrawH = mapLayer.mMapTileColumnNum * mapLayer.mTileHeightForMap;

        //맵의 총 크기
        mapTotalW = mapLayer.terrainColumnList.get(0).length * tileW;
        mapTotalH = mapLayer.terrainColumnList.size() * mapLayer.mTileHeightForMap;
    }

    private void setMapLayer(){
        String jsonStr = GameUtil.getAssetString(gameMain.appClass.getBaseContext(), "map/stage02.json");
        Gson gson = new Gson();
        mapLayer = gson.fromJson(jsonStr, MapEnty.MapLayer.class);
        mapLayer.terrainColumnList = new ArrayList<>();
        mapLayer.buildiingColumnList = new ArrayList<>();
        mapLayer.cursor = new MapEnty.CursorTile();
        mapLayer.cursor.point = new Point();
        mapLayer.cursor.curTile = new Point();
        int rowNum = mapLayer.getLayers().get(0).getWidth();
        int columnNum = mapLayer.getLayers().get(0).getHeight();
        for (int i = 0; i < columnNum; i++) {
            int[] mRowArray_t = new int[rowNum];
            int[] mRowArray_b = new int[rowNum];
            for (int j = 0; j < rowNum; j++) {
                int index = i * rowNum + j;
                mRowArray_t[j] = mapLayer.getLayers().get(0).getDataList().get(index);
                mRowArray_b[j] = mapLayer.getLayers().get(1).getDataList().get(index);
                if(mRowArray_b[j] == INN.MAPTILE_TOWN){
                    mapLayer.cursor.curTile.x = j;
                    mapLayer.cursor.curTile.y = i;
                }
                if (j == rowNum - 1) {
                    mapLayer.terrainColumnList.add(mRowArray_t);
                    mapLayer.buildiingColumnList.add(mRowArray_b);
                }
            }
        }
        mapLayer.LeftTopTileNum = new Point();
        mapLayer.mMapAxis = new Point();
        //게임 처음 맵에서 보여지는 위치 설정 (임시로 0)
        mapLayer.mMapAxis.x = 0;
        mapLayer.mMapAxis.y = 0;
        mapLayer.getTileNum(mapLayer.LeftTopTileNum, mapLayer.mMapAxis.x, mapLayer.mMapAxis.y);

        mapLayer.mTileHeightForMap = mapLayer.getTileHeight() / 4 * 3;
    }

    @Override
    public void gStop() {

    }

    @Override
    public void gUpdate() {
        switch(mapLayer.cursor.state){
            case INN.MAPTILE_TOWN:
                break;
            default:
                break;
        }
    }

    @Override
    public void gOnTouchEvent(MotionEvent event) {
        if (gameMain.onStatusTouch())
            return;
        boolean hasTouchMap = false;
        if (GameUtil.equalsTouch(gameMain.appClass.touch, 0, mMapScreenTop, mapDrawW, mapDrawH)) {
            hasTouchMap = true;
        }
        switch (gameMain.appClass.touch.action) {
            case MotionEvent.ACTION_DOWN:
                mapLayer.isClick = true;
                break;
            case MotionEvent.ACTION_MOVE:
                if(mapLayer.isClick){
                    if(Math.abs(gameMain.appClass.touch.mDownX-gameMain.appClass.touch.mLastTouchX) > tileTouchArea ){
                        mapLayer.isClick = false;
                    }else{
                        return;
                    }
                }

                if (hasTouchMap) {
                    mapLayer.mMapAxis.x = (int) (gameMain.appClass.touch.mPosX);
                    if (mapLayer.mMapAxis.x > 0)
                        mapLayer.mMapAxis.x = 0;
                    else if (mapLayer.mMapAxis.x < (mapTotalW - mapDrawW) * -1)
                        mapLayer.mMapAxis.x = (mapTotalW - mapDrawW) * -1;

                    mapLayer.mMapAxis.y = (int) (gameMain.appClass.touch.mPosY);
                    if (mapLayer.mMapAxis.y < 0)
                        mapLayer.mMapAxis.y = 0;
                    else if (mapLayer.mMapAxis.y > mapTotalH - mapDrawH)
                        mapLayer.mMapAxis.y = mapTotalH - mapDrawH;

                    mapLayer.getTileNum(mapLayer.LeftTopTileNum,
                            mapLayer.mMapAxis.x, mapLayer.mMapAxis.y);

                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (hasTouchMap && mapLayer.isClick) {
                    mapLayer.cursor.point.x = (int)gameMain.appClass.touch.mLastTouchX - mapLayer.mMapAxis.x;
                    mapLayer.cursor.point.y = (int)gameMain.appClass.touch.mLastTouchY +
                            mapLayer.mMapAxis.y - mMapScreenTop;
                    mapLayer.getTileNum(mapLayer.cursor.curTile,
                            mapLayer.cursor.point.x, mapLayer.cursor.point.y);

                    mapLayer.cursor.tileNum = mapLayer.buildiingColumnList.get(
                            mapLayer.cursor.curTile.y)[mapLayer.cursor.curTile.x]-1;
                    if(mapLayer.cursor.tileNum == -1)
                        mapLayer.cursor.tileNum = mapLayer.terrainColumnList.get(
                                mapLayer.cursor.curTile.y)[mapLayer.cursor.curTile.x]-1;

//                            gameMain.mapLayer.cursor.tileNum = gameMain.mapLayer.buildiingColumnList.get(
//                                    gameMain.mapLayer.cursor.curTile.y)[gameMain.mapLayer.cursor.curTile.x] - 1;
//                    if(gameMain.mapLayer.cursor.tileNum > 12 )
                    mapLayer.cursor.state = mapLayer.cursor.tileNum;
//                    else
//                        gameMain.mapLayer.cursor.state = -1;
                    switch(mapLayer.cursor.state) {
                        case INN.MAPTILE_TOWN:
                            //temp party list에서 파티리스트를 불러온다
                            break;
                        default:
                            break;
                    }

                }
                mapLayer.isClick = false;
                break;
        }
    }
}
