package com.epriest.game.guildfantasy.main;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.MotionEvent;

import com.epriest.game.CanvasGL.graphics.GLUtil;
import com.epriest.game.CanvasGL.util.Game;
import com.epriest.game.CanvasGL.util.GameUtil;
import com.epriest.game.guildfantasy.main.enty.DungeonEnty;
import com.epriest.game.guildfantasy.main.enty.HexaEnty;
import com.epriest.game.guildfantasy.main.enty.MapEnty;
import com.epriest.game.guildfantasy.main.enty.MemberEnty;
import com.epriest.game.guildfantasy.main.enty.MonsterEnty;
import com.epriest.game.guildfantasy.main.enty.PartyEnty;
import com.epriest.game.guildfantasy.main.enty.QuestEnty;
import com.epriest.game.guildfantasy.main.enty.StatusEnty;
import com.epriest.game.guildfantasy.main.enty.UnitEnty;
import com.epriest.game.guildfantasy.main.play.DataManager;
import com.epriest.game.guildfantasy.main.play.GameDbAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by darka on 2018-02-01.
 */

public class Game_Dungeon extends Game {
    public Game_Main gameMain;

    public int canvasW, canvasH;
    public int mapDrawW, mapDrawH;
    public int mapTotalW, mapTotalH;
    private int tileTouchArea;

    /**
     * 맵이 그려지는 Top 위치
     */
    public int mMainScreenTop;

    public MapEnty.MapLayer mapLayer;

    public Bitmap img_mapBg;
    public Bitmap img_curTile;
    public Bitmap img_unit;
    public Bitmap img_zoc;

    private QuestEnty questEnty;
    private DungeonEnty dungeonEnty;
    public ArrayList<HexaEnty> unitZocList;

    /**
     * 파티 유닛
     */
    public ArrayList<UnitEnty> partyUnitList;

    /**
     * 몬스터
     */
    public ArrayList<MonsterEnty> monsterList;

    public Game_Dungeon(Game_Main gameMain) {
        this.gameMain = gameMain;
    }

    @Override
    public void gStart() {
        this.canvasW = gameMain.appClass.getGameCanvasWidth();
        this.canvasH = gameMain.appClass.getGameCanvasHeight();

        mMainScreenTop = Game_Main.statusBarH;

        questEnty = DataManager.getUserQuestEnty(gameMain.dbAdapter, gameMain.userEnty.Name, gameMain.selectQuestId);
        dungeonEnty = DataManager.getDungeonEnty(gameMain.dbAdapter, questEnty.dungeon);

        // 맵 설정
        img_mapBg = GLUtil.loadAssetsBitmap(gameMain.appClass, "map/tilemap1.png", null);
        img_curTile = GLUtil.loadAssetsBitmap(gameMain.appClass, "map/tilecursor.png", null);
        img_unit = GLUtil.loadAssetsBitmap(gameMain.appClass, "map/unit.png", null);
        img_zoc = GLUtil.loadAssetsBitmap(gameMain.appClass, "map/areahexa.png", null);

        setMapLayer();
        setMapInit();

        // 파티 유닛 설정
        setUnitData();

        // 몬스터 설정
        setMonsterData();
    }

    @Override
    public void gStop() {

    }

    @Override
    public void gUpdate() {

    }

    private void setMapLayer() {
        String jsonStr = GameUtil.getAssetString(gameMain.appClass.getBaseContext(), "map/stage01.json");
        Gson gson = new Gson();
        mapLayer = gson.fromJson(jsonStr, MapEnty.MapLayer.class);
        mapLayer.terrainColumnList = new ArrayList<>();
        mapLayer.buildiingColumnList = new ArrayList<>();
        mapLayer.cursor = new MapEnty.CursorTile();
//        mapLayer.cursor.point = new Point();
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
                if (mRowArray_b[j] == Game_Map.MAPTILE_TOWN) {
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

    private void setMapInit() {
        int tileW = mapLayer.getTileWidth();
        int tileH = mapLayer.getTileHeight();
        tileTouchArea = tileW / 3;
        mapLayer.mMapTileRowNum = mapLayer.getLayers().get(0).getWidth();
        if (mapLayer.mMapTileRowNum * tileW > canvasW + tileW) {
            mapLayer.mMapTileRowNum = gameMain.appClass.getGameCanvasWidth() / tileW + 2;
        }

//        mapLayer.mMapTileColumnNum = 11;
        mapLayer.mMapTileColumnNum = canvasH / (tileH * 3 / 4);
        if (mapLayer.mMapTileColumnNum > mapLayer.getLayers().get(0).getHeight())
            mapLayer.mMapTileColumnNum = mapLayer.getLayers().get(0).getHeight();

        //그려지는 맵의 크기
        mapDrawW = mapLayer.mMapTileRowNum * tileW;
        mapDrawH = mapLayer.mMapTileColumnNum * mapLayer.mTileHeightForMap;

        //맵의 총 크기
        mapTotalW = mapLayer.terrainColumnList.get(0).length * tileW;
        mapTotalH = mapLayer.terrainColumnList.size() * mapLayer.mTileHeightForMap;
    }

    private void setUnitData(){
        partyUnitList = new ArrayList<>();

        PartyEnty currentParty = DataManager.getPartyData(
                gameMain.dbAdapter, gameMain.userEnty.Name, gameMain.getSelectPartyNum());
        int memberCnt = 0;
        for (int i = 0; i < currentParty.memberPos.length; i++) {
            if (memberCnt == 4) {
                break;
            }
            String id = currentParty.memberPos[i];
            if (!id.equals("0")) {
                UnitEnty enty = new UnitEnty();
                String imgPath = DataManager.getMemberData(gameMain.dbAdapter, id).image;
                enty.chr_img = GLUtil.loadAssetsBitmap(
                        gameMain.appClass, "member/" + imgPath, null, 6);
                enty.id = id;
                enty.num = memberCnt;
                enty.pos = i;
                memberCnt++;

                enty.startAxisX = enty.pos % 3 + 1;
                enty.startAxisy = mapLayer.mMapTileColumnNum - enty.pos / 3 - 3;

                enty.curAxisX = enty.startAxisX;
                enty.curAxisY = enty.startAxisy;
                enty.memberEnty = new MemberEnty();
                enty.memberEnty = DataManager.getMemberData(gameMain.dbAdapter, id);
                enty.memberEnty.status.USE_HP = enty.memberEnty.status.MAX_HP;
                enty.memberEnty.status.USE_MP = enty.memberEnty.status.MAX_MP;
                enty.memberEnty.status.USE_AP = enty.memberEnty.status.MAX_AP;
                partyUnitList.add(enty);
            }
        }
    }

    private void setMonsterData() {
        monsterList = new ArrayList<>();
        int num = 6;
//        String monsterId = questEnty.monster1;
        Cursor c = gameMain.dbAdapter.getCursor(GameDbAdapter.MONSTER_TABLE, GameDbAdapter.KEY_MONSTERENGNAME, dungeonEnty.mon1);
        String monsterId = c.getString(c.getColumnIndex(GameDbAdapter.KEY_MONSTERID));

        for (int i = 0; i < num; i++) {
            MonsterEnty enty = DataManager.getMonsterEnty(gameMain.dbAdapter, monsterId);
            enty.num = i;

            monsterList.add(enty);
        }
    }

    /**
     * ZOC를 체크
     * @param hexaX
     * @param hexaY
     * @return
     */
    private ArrayList<HexaEnty> checkZOC(int hexaX, int hexaY){
        ArrayList<HexaEnty> hexaList = new ArrayList<>();
        HexaEnty enty = new HexaEnty();
        enty.num = 0;
        enty.x = hexaX-1;
        enty.y = hexaY;
        hexaList.add(enty);

        enty = new HexaEnty();
        enty.num = 1;
        if(hexaY%2 == 0){
            enty.x = hexaX-1;
        }else{
            enty.x = hexaX;
        }
        enty.y = hexaY+1;
        hexaList.add(enty);

        enty = new HexaEnty();
        enty.num = 2;
        if(hexaY%2 == 0){
            enty.x = hexaX;
        }else{
            enty.x = hexaX+1;
        }

        enty.y = hexaY+1;
        hexaList.add(enty);

        enty = new HexaEnty();
        enty.num = 3;
        enty.x = hexaX+1;
        enty.y = hexaY;
        hexaList.add(enty);

        enty = new HexaEnty();
        enty.num = 4;
        if(hexaY%2 == 0){
            enty.x = hexaX;
        }else{
            enty.x = hexaX+1;
        }
        enty.y = hexaY-1;
        hexaList.add(enty);

        enty = new HexaEnty();
        enty.num = 5;
        if(hexaY%2 == 0){
            enty.x = hexaX-1;
        }else{
            enty.x = hexaX;
        }
        enty.y = hexaY-1;
        hexaList.add(enty);

        return hexaList;
    }

    @Override
    public void gOnTouchEvent(MotionEvent event) {
        if (gameMain.onStatusTouch())
            return;

        boolean hasTouchMap = false;
        if (GameUtil.equalsTouch(gameMain.appClass.touch, 0, mMainScreenTop, mapDrawW, mapDrawH)) {
            hasTouchMap = true;
        }
        switch (gameMain.appClass.touch.action) {
            case MotionEvent.ACTION_DOWN:
                mapLayer.isClick = true;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mapLayer.isClick) {
                    if (Math.abs(gameMain.appClass.touch.mDownX - gameMain.appClass.touch.mLastTouchX) > tileTouchArea) {
                        mapLayer.isClick = false;
                    } else {
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
                    //커서가 위치할 타일의 axis 계산
                    int x = (int) gameMain.appClass.touch.mLastTouchX - mapLayer.mMapAxis.x;
                    int y = (int) gameMain.appClass.touch.mLastTouchY +
                            mapLayer.mMapAxis.y - mMainScreenTop;
                    mapLayer.getTileNum(mapLayer.cursor.curTile, x, y);

                    // 커서가 위치할 타일의 타입(넘버) 체크
                    mapLayer.cursor.tileNum = mapLayer.buildiingColumnList.get(
                            mapLayer.cursor.curTile.y)[mapLayer.cursor.curTile.x] - 1;
                    if (mapLayer.cursor.tileNum == -1)
                        mapLayer.cursor.tileNum = mapLayer.terrainColumnList.get(
                                mapLayer.cursor.curTile.y)[mapLayer.cursor.curTile.x] - 1;

                    switch (mapLayer.cursor.tileNum) {
                        case Game_Map.MAPTILE_TOWN:
                            //temp party list에서 파티리스트를 불러온다
                            break;
                        default:
                            break;
                    }

                    // 유닛이 선택될 경우 zoc 체크
                    unitZocList = null;
                    for(UnitEnty unitEnty : partyUnitList){
                        if(mapLayer.cursor.curTile.x == unitEnty.curAxisX &&
                                mapLayer.cursor.curTile.y == unitEnty.curAxisY){
                            unitZocList = checkZOC(unitEnty.curAxisX, unitEnty.curAxisY);
                            break;
                        }
                    }

                }
                mapLayer.isClick = false;
                break;
        }
    }
}
