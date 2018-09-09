package com.epriest.game.guildfantasy.main;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;

import com.epriest.game.CanvasGL.graphics.GLUtil;
import com.epriest.game.CanvasGL.util.Game;
import com.epriest.game.CanvasGL.util.GameUtil;
import com.epriest.game.CanvasGL.util.TouchData;
import com.epriest.game.guildfantasy.main.enty.ButtonEnty;
import com.epriest.game.guildfantasy.main.enty.DungeonEnty;
import com.epriest.game.guildfantasy.main.enty.HexaEnty;
import com.epriest.game.guildfantasy.main.enty.MapEnty;
import com.epriest.game.guildfantasy.main.enty.MemberEnty;
import com.epriest.game.guildfantasy.main.enty.MonsterEnty;
import com.epriest.game.guildfantasy.main.enty.PartyEnty;
import com.epriest.game.guildfantasy.main.enty.QuestEnty;
import com.epriest.game.guildfantasy.main.enty.UnitEnty;
import com.epriest.game.guildfantasy.main.play.DataManager;
import com.epriest.game.guildfantasy.main.play.GameDbAdapter;
import com.epriest.game.guildfantasy.util.INN;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by darka on 2018-02-01.
 */

public class Game_Stage extends Game {
    public final static int MAPTILE_VILLAGE = 12;
    public final static int MAPTILE_TOWN = 13;
    public final static int MAPTILE_CASTLE = 14;
    public final static int MAPTILE_FORT = 15;
    public final static int MAPTILE_MINE = 16;
    public final static int MAPTILE_RUIN = 17;
    public final static int MAPTILE_NEST = 18;
    public final String[] unitCommandArr = {
            "move", "magic", "item", "wait", "retreat"
    };
    public final String[] MapTileNameArr = {
            "평지", "언덕", "산", "모래", "숲", "개울", "늪", "바다", "눈", "설원", "빙벽",
            "", "chest", "trap", "poison", "rest", "gate", "mon2", "mon1", "boss"};
    public final String[] MapTileAttrArr = {INN.TILEATTR_EARTH, INN.TILEATTR_EARTH, INN.TILEATTR_FIRE, INN.TILEATTR_FIRE, INN.TILEATTR_EARTH,
            INN.TILEATTR_WATER, INN.TILEATTR_WATER, INN.TILEATTR_WATER, INN.TILEATTR_ICE, INN.TILEATTR_ICE, INN.TILEATTR_ICE};
    public Game_Main gameMain;
    /**
     * unit card width, height
     */
    public int cardW, cardH;

    public int commandBtnW = 83;
    public int commandBtnH = 80;

    public int canvasW, canvasH;

    /**
     * map의 배열된 타일 갯수
     */
    public int mapTileTotalX, mapTileTotalY;

    /**
     * 실제 그려지는 map의 width, height
     */
    public int mapCanvasW, mapCanvasH;

    /**
     * 맵이 그려지는 Top, Left 위치
     */
    public int mapMarginTop, mapMarginLeft;

    public MapEnty.MapLayer mapLayer;
    public ArrayList<ButtonEnty> commandBtnList;
    public Bitmap img_mapBg;
    public Bitmap img_curTile;
    public Bitmap img_unit;
    public Bitmap img_zoc;
    public Bitmap img_unitCard;
    public Bitmap img_map_object;
    public Bitmap img_chr_unit;
    /**
     * 파티의 대표 멤버 enty
     */
    public UnitEnty unitEnty;
    public ArrayList<HexaEnty> unitZocList;
    /**
     * 파티 유닛
     */
    public ArrayList<UnitEnty> partyUnitList;
    /**
     * 몬스터
     */
    public ArrayList<MonsterEnty> monsterList;
    public UnitEnty selectUnitEnty;
    public MonsterEnty selectMonsterEnty;
    /**
     * 타일에서 터치를 인식하는 영역 - 드래그로 영역밖으로 나가면 터치오프
     */
    private int touchableTileArea;
    private QuestEnty questEnty;
    private DungeonEnty dungeonEnty;

    public Game_Stage(Game_Main gameMain) {
        this.gameMain = gameMain;
    }

    @Override
    public void gStart() {
        this.canvasW = gameMain.appClass.getGameCanvasWidth();
        this.canvasH = gameMain.appClass.getGameCanvasHeight();

        questEnty = DataManager.getUserQuestEnty(gameMain.dbAdapter, gameMain.userEnty.Name, gameMain.selectQuestId);
        dungeonEnty = DataManager.getDungeonEnty(gameMain.dbAdapter, questEnty.dungeon);

        // 맵 설정
        img_mapBg = GLUtil.loadAssetsBitmap(gameMain.appClass, "map/tilemap01.png", null);
        img_curTile = GLUtil.loadAssetsBitmap(gameMain.appClass, "map/tilecursor.png", null);
        img_unit = GLUtil.loadAssetsBitmap(gameMain.appClass, "map/unit.png", null);
        img_zoc = GLUtil.loadAssetsBitmap(gameMain.appClass, "map/areahexa.png", null);
        img_unitCard = GLUtil.loadAssetsBitmap(gameMain.appClass, "map/unitcard.png", null);
        img_map_object = GLUtil.loadAssetsBitmap(gameMain.appClass, "map/map_object0.png", null);
        img_chr_unit = GLUtil.loadAssetsBitmap(gameMain.appClass, "map/chr_unit.png", null);

        cardW = img_unitCard.getWidth();
        cardH = img_unitCard.getHeight();

        setMapLayer();
        setMapInit();

        // 파티 리더유닛 설정
        setUnitEnty(3);

        // 파티 설정
        setPartyData();

        // 몬스터 설정
        setMonsterData();

        // 명령버튼 설정
        setCommandBtn();
    }

    @Override
    public void gStop() {

    }

    @Override
    public void gUpdate() {

    }

    private void setMapLayer() {
        String jsonStr = GameUtil.getAssetString(gameMain.appClass.getBaseContext(), "map/stage00.json");
        Gson gson = new Gson();
        mapLayer = gson.fromJson(jsonStr, MapEnty.MapLayer.class);
        mapLayer.terrainColumnList = new ArrayList<>();
        mapLayer.objectColumnList = new ArrayList<>();
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
                if (mRowArray_b[j] == Game_Stage.MAPTILE_TOWN) {
                    mapLayer.cursor.curTile.x = j;
                    mapLayer.cursor.curTile.y = i;
                }
                if (j == rowNum - 1) {
                    mapLayer.terrainColumnList.add(mRowArray_t);
                    mapLayer.objectColumnList.add(mRowArray_b);
                }
            }
        }

        //맵에 실제로 그려지는 Tile의 Height값 계산
        mapLayer.mTileHeightOnMap = mapLayer.getTileHeight() / 4 * 3;
    }

    private void setMapInit() {
        int tileW = mapLayer.getTileWidth();
        int tileH = mapLayer.getTileHeight();
        touchableTileArea = tileH / 3;
        mapLayer.mapCanvasTileTotalX = mapLayer.getLayers().get(0).getWidth();
        if (mapLayer.mapCanvasTileTotalX * tileW > canvasW + tileW) {
            mapLayer.mapCanvasTileTotalX = gameMain.appClass.getGameCanvasWidth() / tileW + 2;
        }

        // map이 그려질 영역
        int mapH = canvasH - cardH - commandBtnH;
        mapLayer.mapCanvasTileTotalY = mapH / (tileH * 3 / 4);
        if (mapLayer.mapCanvasTileTotalY > mapLayer.getLayers().get(0).getHeight())
            mapLayer.mapCanvasTileTotalY = mapLayer.getLayers().get(0).getHeight();

        //그려지는 맵의 크기
        mapCanvasW = mapLayer.mapCanvasTileTotalX * tileW;
        mapCanvasH = mapLayer.mapCanvasTileTotalY * mapLayer.mTileHeightOnMap;

        //맵의 총 크기
        mapTileTotalX = mapLayer.mapList.get(0).getWidth();//mapLayer.terrainColumnList.get(0).length * tileW;
        mapTileTotalY = mapLayer.mapList.get(0).getHeight();//mapLayer.terrainColumnList.size() * mapLayer.mTileHeightOnMap;

        //맵이 그려지는 top margin 계산
        mapMarginTop = Game_Main.statusBarH;
        int mDrawMapAxisH = mapTileTotalY * mapLayer.mTileHeightOnMap;
        if (mDrawMapAxisH < mapH) {
            mapMarginTop = Game_Main.statusBarH + (mapH - mDrawMapAxisH) / 2;
        }

        //맵이 그려지는 left margin 계산
        mapMarginLeft = 0;
        int mDrawMapAxisW = mapTileTotalX * mapLayer.getTileWidth();
        if (mDrawMapAxisW < canvasW) {
            mapMarginLeft = (canvasW - mDrawMapAxisW) / 2;
        }

        mapLayer.LeftTopTileAxis = new Point();
        mapLayer.mMapScrollAxis = new Point();
        //게임 처음 맵이 보여지는 위치 설정 (임시로 0)
        mapLayer.mMapScrollAxis.x = 0;
        mapLayer.mMapScrollAxis.y = 0;
        mapLayer.LeftTopTileAxis = mapLayer.getTileAxis(mapLayer.mMapScrollAxis.x, mapLayer.mMapScrollAxis.y,
                mapMarginLeft, mapMarginTop);
    }

    private void setUnitEnty(int memberNum) {
        unitEnty = new UnitEnty();
        PartyEnty currentParty = DataManager.getPartyData(
                gameMain.dbAdapter, gameMain.userEnty.Name, gameMain.getSelectPartyNum());

        //파티멤버에서 memberNum의 맴버 id를 선택
        int cnt = 0;
        for (int i = 0; i < currentParty.memberPos.length; i++) {
            String id = currentParty.memberPos[i];
            if (!id.equals("0")) {
                if (cnt == memberNum) {
                    unitEnty.id = id;
                    break;
                }
                cnt++;
            }
        }

        String imgPath = DataManager.getMemberData(gameMain.dbAdapter, unitEnty.id).image;
        unitEnty.chr_img = GLUtil.loadAssetsBitmap(
                gameMain.appClass, "member/" + imgPath, null, 3);

        unitEnty.num = memberNum;


        // unit초기 위치는 gate가 있는 스타트 위치.
        unitEnty.startAxis = getUnitTileAxis(mapTileTotalX, mapTileTotalY, INN.TILETYPE_GATE);
        mapLayer.LeftTopTileAxis.x = unitEnty.startAxis.x-mapLayer.mapCanvasTileTotalX/2;
        mapLayer.LeftTopTileAxis.y = unitEnty.startAxis.y-mapLayer.mapCanvasTileTotalY/2;
        if(mapLayer.LeftTopTileAxis.x < 0)
            mapLayer.LeftTopTileAxis.x = 0;
        else if(mapLayer.LeftTopTileAxis.x+mapLayer.mapCanvasTileTotalX > mapTileTotalX)
            mapLayer.LeftTopTileAxis.x = mapTileTotalX - mapLayer.mapCanvasTileTotalX;
        if(mapLayer.LeftTopTileAxis.y < 0)
            mapLayer.LeftTopTileAxis.y = 0;
        else if(mapLayer.LeftTopTileAxis.y+mapLayer.mapCanvasTileTotalY > mapTileTotalY)
            mapLayer.LeftTopTileAxis.y = mapTileTotalY - mapLayer.mapCanvasTileTotalY;





//        int canvasTopTileNum = mapLayer.LeftTopTileAxis.y;
//        if (mapLayer.LeftTopTileAxis.y + mapLayer.mapCanvasTileTotalY >= mapTileTotalY)
//            canvasTopTileNum = mapTileTotalY - mapLayer.mapCanvasTileTotalY;
//
//        int canvasLeftTileNum = mapLayer.LeftTopTileAxis.x;
//        if (mapLayer.mapCanvasTileTotalX + mapLayer.LeftTopTileAxis.x >= mapTileTotalX)
//            canvasLeftTileNum = mapTileTotalX - mapLayer.mapCanvasTileTotalX;
//
//        for (int i = canvasTopTileNum; i < mapLayer.mapCanvasTileTotalY; i++) {
//
//            for (int j = canvasLeftTileNum; j < mapLayer.mapCanvasTileTotalX; j++) {
//                int objNum = mapLayer.objectColumnList.get(i)[j] - 1;
//                if (objNum == INN.TILETYPE_GATE) {
//                    unitEnty.startAxis.x = j;
//                    unitEnty.startAxis.y = i;
//                    break;
//                }
//            }
//        }
        unitEnty.curAxis.x = unitEnty.startAxis.x;
        unitEnty.curAxis.y = unitEnty.startAxis.y;
        unitEnty.memberEnty = new MemberEnty();
        unitEnty.memberEnty = DataManager.getMemberData(gameMain.dbAdapter, unitEnty.id);
        unitEnty.memberEnty.status.USE_HP = unitEnty.memberEnty.status.MAX_HP;
        unitEnty.memberEnty.status.USE_MP = unitEnty.memberEnty.status.MAX_MP;
        unitEnty.memberEnty.status.USE_AP = unitEnty.memberEnty.status.MAX_AP;

    }

    private Point getUnitTileAxis(int TotalX, int TotalY, int type) {
        Point point = new Point();
        for (int i = 0; i < TotalY; i++) {
            for (int j = 0; j < TotalX; j++) {
                int objNum = mapLayer.objectColumnList.get(i)[j] - 1;
                if (objNum == type) {
                    point.x = j;
                    point.y = i;
                    break;
                }
            }
        }
        return point;
    }

    private void setPartyData() {
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
                        gameMain.appClass, "member/" + imgPath, null, 3);
                enty.id = id;
                enty.num = memberCnt;
                enty.pos = i;
                memberCnt++;

                enty.startAxis.x = enty.pos % 3 + 1;
                enty.startAxis.y = mapLayer.mapCanvasTileTotalY - enty.pos / 3 - 3;

                enty.curAxis.x = enty.startAxis.x;
                enty.curAxis.y = enty.startAxis.y;
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
        int num = 0;
        for (int i = 0; i < mapLayer.objectColumnList.size(); i++) {
            int[] arr = mapLayer.objectColumnList.get(i);
            for (int j = 0; j < arr.length; j++) {
                if (arr[j] == 18 || arr[j] == 19 || arr[j] == 20) {
                    String name = null;
                    if (arr[j] == 18) {
                        name = dungeonEnty.mon2;
                    } else if (arr[j] == 19) {
                        name = dungeonEnty.mon1;
                    } else if (arr[j] == 20) {
                        name = dungeonEnty.monboss;
                    }
                    Cursor c = gameMain.dbAdapter.getCursor(GameDbAdapter.MONSTER_TABLE, GameDbAdapter.KEY_MONSTER_ENGNAME, name);
                    String monsterId = c.getString(c.getColumnIndex(GameDbAdapter.KEY_MONSTER_ID));
                    MonsterEnty enty = DataManager.getMonsterEnty(gameMain.dbAdapter, monsterId);
                    enty.mon_img = GLUtil.loadAssetsBitmap(
                            gameMain.appClass, "mon/" + enty.image, null, 1);
                    enty.num = num;
                    enty.startAxisX = j;
                    enty.startAxisy = i;
                    monsterList.add(enty);
                    num++;
                }
            }
        }
    }

    private void setCommandBtn() {
        commandBtnList = new ArrayList<>();
        int btnX = (canvasW - commandBtnW * unitCommandArr.length) / 2;
        for (int i = 0; i < unitCommandArr.length; i++) {
            ButtonEnty enty = new ButtonEnty();
            enty.name = unitCommandArr[i];
            enty.num = i;
            enty.drawX = i * commandBtnW + btnX;
            enty.drawY = mapMarginTop + mapCanvasH;
            enty.drawW = commandBtnW;
            enty.drawH = commandBtnH - 10;
            enty.clipX = 210;
            enty.clipY = 0;
            enty.clipW = commandBtnW;
            enty.clipH = commandBtnH - 10;
            commandBtnList.add(enty);
        }
    }

    /**
     * tile좌표로 terrain 타일 넘버를 get
     *
     * @param tileX
     * @param tileY
     * @return
     */
    public int getTerrainTileNumber(int tileX, int tileY) {
        return mapLayer.terrainColumnList.get(tileY)[tileX] - 1;
    }

    /**
     * tile좌표로 object 타일 넘버를 get
     *
     * @param tileX
     * @param tileY
     * @return
     */
    public int getObjectTileNumber(int tileX, int tileY) {
        return mapLayer.objectColumnList.get(tileY)[tileX] - 1;
    }

    /**
     * 이동할 수 있는 타일여부
     *
     * @param terrainNum
     * @return
     */
    public boolean isMoveableTile(int terrainNum) {
        if (terrainNum == INN.TILETYPE_MOUNTINE || terrainNum == INN.TILETYPE_SEA || terrainNum == INN.TILETYPE_ICEBERG)
            return false;
        else
            return true;
    }

    /**
     * ZOC를 체크
     *
     * @param hexaX
     * @param hexaY
     * @return
     */
    private ArrayList<HexaEnty> checkZOC(int hexaX, int hexaY) {
        ArrayList<HexaEnty> hexaList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            HexaEnty enty = new HexaEnty();
            enty.num = i;
            switch (i) {
                case 0:
                    enty.x = hexaX - 1;
                    enty.y = hexaY;
                    break;
                case 1:
                    if (hexaY % 2 == 0) {
                        enty.x = hexaX - 1;
                    } else {
                        enty.x = hexaX;
                    }
                    enty.y = hexaY + 1;
                    break;
                case 2:
                    if (hexaY % 2 == 0) {
                        enty.x = hexaX;
                    } else {
                        enty.x = hexaX + 1;
                    }
                    enty.y = hexaY + 1;
                    break;
                case 3:
                    enty.x = hexaX + 1;
                    enty.y = hexaY;
                    break;
                case 4:
                    if (hexaY % 2 == 0) {
                        enty.x = hexaX;
                    } else {
                        enty.x = hexaX + 1;
                    }
                    enty.y = hexaY - 1;
                    break;
                case 5:
                    if (hexaY % 2 == 0) {
                        enty.x = hexaX - 1;
                    } else {
                        enty.x = hexaX;
                    }
                    enty.y = hexaY - 1;
                    break;
            }
            if (enty.x >= mapLayer.mapList.get(0).getWidth() || enty.y >= mapLayer.mapList.get(0).getHeight()) {
                enty = null;
            } else {
                enty.terrain = getTerrainTileNumber(enty.x, enty.y);
                enty.object = getObjectTileNumber(enty.x, enty.y);
            }
            hexaList.add(enty);
        }
        return hexaList;
    }

    @Override
    public void gOnTouchEvent(MotionEvent event) {
        TouchData.Touch touch = gameMain.appClass.touch;
        if (gameMain.onStatusTouch())
            return;

        boolean isTouchMapArea = false;
        if (GameUtil.equalsTouch(touch, mapMarginLeft, mapMarginTop, mapCanvasW, mapCanvasH))
            isTouchMapArea = true;
        switch (touch.action) {
            case MotionEvent.ACTION_DOWN:
                if (GameUtil.equalsTouch(touch, mapMarginLeft, mapMarginTop, mapCanvasW, mapCanvasH))
                    mapLayer.isClick = true;
                break;
            case MotionEvent.ACTION_MOVE:
                // Click (유격은 타일의 1/3)
                if (mapLayer.isClick) {
                    if (Math.abs(touch.mDownX - touch.mLastTouchX) > touchableTileArea ||
                            Math.abs(touch.mDownY - touch.mLastTouchY) > touchableTileArea) {
                        mapLayer.isClick = false;
                    } else {
                        return;
                    }
                }

                //Map Scroll
                if (isTouchMapArea) {
                    // 스크롤 영역을 맵 크기내로 제한.
                    int mapCanvasW = canvasW - mapMarginLeft * 2;
                    if (this.mapCanvasW > mapCanvasW) {
                        if (touch.mPosX < mapMarginLeft) {
                            mapLayer.mMapScrollAxis.x = mapMarginLeft;
                        } else if (touch.mPosX >= this.mapCanvasW - mapCanvasW) {
                            mapLayer.mMapScrollAxis.x = this.mapCanvasW - mapCanvasW;
                        } else {
                            mapLayer.mMapScrollAxis.x = (int) (touch.mPosX);
                        }
                    }

//                    mapLayer.mMapScrollAxis.x = (int) (touch.mPosX);
//                    int mapWidth = mapTileTotalX*mapLayer.getTileWidth();
//                    if(mapLayer.mMapScrollAxis.x < mapMarginLeft){
//                        mapLayer.mMapScrollAxis.x = 0;
//                    }else if(mapLayer.mMapScrollAxis.x > mapWidth-mapCanvasW ){
//                        if(mapWidth-mapCanvasW < 0){
//                            mapLayer.mMapScrollAxis.x = 0;
//                        }else{
//                            mapLayer.mMapScrollAxis.x = mapWidth-mapCanvasW;
//                        }
//
//                    }

                    int mapCanvasH = canvasH - mapMarginTop;

                    mapLayer.mMapScrollAxis.y = (int) (touch.mPosY);
                    int mapHeight = mapTileTotalY * mapLayer.getTileHeight();
                    if (mapLayer.mMapScrollAxis.y < mapMarginTop) {
                        mapLayer.mMapScrollAxis.y = 0;
                    } else if (mapLayer.mMapScrollAxis.y > mapHeight - this.mapCanvasH) {
                        if (mapHeight - this.mapCanvasH < 0) {
                            mapLayer.mMapScrollAxis.y = 0;
                        } else {
                            mapLayer.mMapScrollAxis.y = mapHeight - this.mapCanvasH;
                        }

                    }

                    mapLayer.LeftTopTileAxis = mapLayer.getTileAxis(mapLayer.mMapScrollAxis.x, mapLayer.mMapScrollAxis.y,
                            mapMarginLeft, mapMarginTop);
                    Log.d("", "=====x:" + mapLayer.LeftTopTileAxis.x + "==y:" + mapLayer.LeftTopTileAxis.y);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (isTouchMapArea && mapLayer.isClick) {
                    touchMapTile();
                } else {
                    //유닛선택시 나타나는 커멘드 클릭
                    if (selectUnitEnty != null) {

                    }
                }
                mapLayer.isClick = false;


                break;
        }
    }

    private void touchMapTile() {
        //커서가 위치할 타일의 axis 계산
        mapLayer.cursor.curTile = mapLayer.getTileAxis((int) gameMain.appClass.touch.mLastTouchX - mapLayer.mMapScrollAxis.x,
                (int) gameMain.appClass.touch.mLastTouchY + mapLayer.mMapScrollAxis.y, mapMarginLeft, mapMarginTop);

        // 커서가 위치할 타일의 타입(넘버) 체크
        mapLayer.cursor.tileObjectNum = getObjectTileNumber(mapLayer.cursor.curTile.x, mapLayer.cursor.curTile.y);
//                    mapLayer.objectColumnList.get(mapLayer.cursor.curTile.y)[mapLayer.cursor.curTile.x] - 1;
//                    if (mapLayer.cursor.tileNum == -1)
        mapLayer.cursor.tileTerrianNum = getTerrainTileNumber(mapLayer.cursor.curTile.x, mapLayer.cursor.curTile.y);
//                    mapLayer.terrainColumnList.get(mapLayer.cursor.curTile.y)[mapLayer.cursor.curTile.x] - 1;

        switch (mapLayer.cursor.tileObjectNum) {
            case MAPTILE_TOWN:
                //temp party list에서 파티리스트를 불러온다
                break;
            default:
                break;
        }

        // 커서가 유닛을 선택할 경우 zoc 체크
        unitZocList = null;
        selectUnitEnty = null;
        selectMonsterEnty = null;

        if (mapLayer.cursor.curTile.x == unitEnty.curAxis.x &&
                mapLayer.cursor.curTile.y == unitEnty.curAxis.y) {
            unitZocList = checkZOC(unitEnty.curAxis.x, unitEnty.curAxis.y);
            selectUnitEnty = unitEnty;
            return;
        }

        //커서가 몬스터를 선택할 경우 zoc 체크
        for (MonsterEnty monEnty : monsterList) {
            if (mapLayer.cursor.curTile.x == monEnty.curAxisX &&
                    mapLayer.cursor.curTile.y == monEnty.curAxisY) {
                unitZocList = checkZOC(monEnty.curAxisX, monEnty.curAxisY);
                selectMonsterEnty = monEnty;
                break;
            }
        }
    }
}
