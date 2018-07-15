package com.epriest.game.guildfantasy.main;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.MotionEvent;

import com.epriest.game.CanvasGL.graphics.GLUtil;
import com.epriest.game.CanvasGL.util.Game;
import com.epriest.game.CanvasGL.util.GameUtil;
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

    public Game_Main gameMain;

    public final String[] unitCommandArr = {
            "move", "magic", "item", "wait", "retreat"
    };
    public final String[] MapTileNameArr = {
            "평지", "언덕", "산", "모래", "숲", "개울", "늪", "바다", "눈", "설원", "빙벽",
            "", "chest", "trap", "poison", "rest", "gate", "mon2", "mon1", "boss"};
    public final String[] MapTileAttrArr = {INN.TILEATTR_EARTH, INN.TILEATTR_EARTH, INN.TILEATTR_FIRE, INN.TILEATTR_FIRE, INN.TILEATTR_EARTH,
            INN.TILEATTR_WATER, INN.TILEATTR_WATER, INN.TILEATTR_WATER, INN.TILEATTR_ICE, INN.TILEATTR_ICE, INN.TILEATTR_ICE};

    /**
     * unit card width, height
     */
    public int cardW, cardH;

    public int commandBtnW = 83;
    public int commandBtnH = 80;

    public int canvasW, canvasH;

    /**
     * map의 width, height
     */
    public int mapTotalW, mapTotalH;

    /**
     * 실제 그려지는 map의 width, height
     */
    public int mapDrawW, mapDrawH;

    /**
     * 맵이 그려지는 Top, Left 위치
     */
    public int mapMarginTop, mapMarginLeft;

    /**
     * 타일에서 터치를 인식하는 영역 - 드래그로 영역밖으로 나가면 터치오프
     */
    private int touchableTileArea;

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


    public UnitEnty selectUnitEnty;
    public MonsterEnty selectMonsterEnty;

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
        mapLayer.LeftTopTileNum = new Point();
        mapLayer.mMapAxis = new Point();
        //게임 처음 맵에서 보여지는 위치 설정 (임시로 0)
        mapLayer.mMapAxis.x = 0;
        mapLayer.mMapAxis.y = 0;
        mapLayer.getTileNum(mapLayer.LeftTopTileNum, mapLayer.mMapAxis.x, mapLayer.mMapAxis.y);

        //맵에 실제로 그려지는 Tile의 Height값 계산
        mapLayer.mTileHeightOnMap = mapLayer.getTileHeight() / 4 * 3;
    }

    private void setMapInit() {
        int tileW = mapLayer.getTileWidth();
        int tileH = mapLayer.getTileHeight();
        touchableTileArea = tileH / 2;
        mapLayer.mMapTileRowNum = mapLayer.getLayers().get(0).getWidth();
        if (mapLayer.mMapTileRowNum * tileW > canvasW + tileW) {
            mapLayer.mMapTileRowNum = gameMain.appClass.getGameCanvasWidth() / tileW + 2;
        }

        // map이 그려질 영역
        int mapH = canvasH - cardH - commandBtnH;
        mapLayer.mMapTileColumnNum = mapH / (tileH * 3 / 4);
        if (mapLayer.mMapTileColumnNum > mapLayer.getLayers().get(0).getHeight())
            mapLayer.mMapTileColumnNum = mapLayer.getLayers().get(0).getHeight();

        //그려지는 맵의 크기
        mapDrawW = mapLayer.mMapTileRowNum * tileW;
        mapDrawH = mapLayer.mMapTileColumnNum * mapLayer.mTileHeightOnMap;

        //맵의 총 크기
        mapTotalW = mapLayer.mapList.get(0).getWidth();//mapLayer.terrainColumnList.get(0).length * tileW;
        mapTotalH = mapLayer.mapList.get(0).getHeight();//mapLayer.terrainColumnList.size() * mapLayer.mTileHeightOnMap;

        //맵이 그려지는 top margin 계산
        mapMarginTop = Game_Main.statusBarH;
        int mDrawMapAxisH = mapTotalH * mapLayer.mTileHeightOnMap;
        if (mDrawMapAxisH < mapH) {
            mapMarginTop = Game_Main.statusBarH + (mapH - mDrawMapAxisH) / 2;
        }

        //맵이 그려지는 left margin 계산
        mapMarginLeft = 0;
        int mDrawMapAxisW = mapTotalW * mapLayer.getTileWidth();
        if (mDrawMapAxisW < canvasW) {
            mapMarginLeft = (canvasW - mDrawMapAxisW) / 2;
        }
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
        for (int i = mapLayer.LeftTopTileNum.y;
             i < mapLayer.mMapTileColumnNum + mapLayer.LeftTopTileNum.y; i++) {

            for (int j = mapLayer.LeftTopTileNum.x;
                 j < mapLayer.mMapTileRowNum + mapLayer.LeftTopTileNum.x; j++) {
                int objNum = mapLayer.objectColumnList.get(i)[j] - 1;
                if (objNum == INN.TILETYPE_GATE) {
                    unitEnty.startAxisX = j;
                    unitEnty.startAxisY = i;
                    break;
                }
            }
        }
        unitEnty.curAxisX = unitEnty.startAxisX;
        unitEnty.curAxisY = unitEnty.startAxisY;
        unitEnty.memberEnty = new MemberEnty();
        unitEnty.memberEnty = DataManager.getMemberData(gameMain.dbAdapter, unitEnty.id);
        unitEnty.memberEnty.status.USE_HP = unitEnty.memberEnty.status.MAX_HP;
        unitEnty.memberEnty.status.USE_MP = unitEnty.memberEnty.status.MAX_MP;
        unitEnty.memberEnty.status.USE_AP = unitEnty.memberEnty.status.MAX_AP;

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

                enty.startAxisX = enty.pos % 3 + 1;
                enty.startAxisY = mapLayer.mMapTileColumnNum - enty.pos / 3 - 3;

                enty.curAxisX = enty.startAxisX;
                enty.curAxisY = enty.startAxisY;
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
        int btnX = (canvasW - commandBtnW*unitCommandArr.length)/2;
        for (int i = 0; i < unitCommandArr.length; i++) {
            ButtonEnty enty = new ButtonEnty();
            enty.name = unitCommandArr[i];
            enty.num = i;
            enty.drawX = i * commandBtnW + btnX;
            enty.drawY = mapMarginTop + mapDrawH;
            enty.drawW = commandBtnW;
            enty.drawH = commandBtnH-10;
            enty.clipX = 210;
            enty.clipY = 0;
            enty.clipW = commandBtnW;
            enty.clipH = commandBtnH-10;
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
        if (gameMain.onStatusTouch())
            return;

        boolean hasTouchMap = false;
        if (GameUtil.equalsTouch(gameMain.appClass.touch, mapMarginLeft, mapMarginTop, mapDrawW, mapDrawH)) {
            hasTouchMap = true;
        }
        switch (gameMain.appClass.touch.action) {
            case MotionEvent.ACTION_DOWN:
                mapLayer.isClick = true;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mapLayer.isClick) {
                    if (Math.abs(gameMain.appClass.touch.mDownX - gameMain.appClass.touch.mLastTouchX) > touchableTileArea) {
                        mapLayer.isClick = false;
                    } else {
                        return;
                    }
                }

                if (hasTouchMap) {
                    mapLayer.mMapAxis.x = (int) (gameMain.appClass.touch.mPosX);
                    if (mapLayer.mMapAxis.x > 0) {
                        gameMain.appClass.touch.mPosX = 0;
                    } else if (mapLayer.mMapAxis.x < (mapTotalW - mapDrawW) * -1) {
                        gameMain.appClass.touch.mPosX = (mapTotalW - mapDrawW) * -1;
                        mapLayer.mMapAxis.x = (mapTotalW - mapDrawW) * -1;
                    }

                    mapLayer.mMapAxis.y = (int) (gameMain.appClass.touch.mPosY);
                    if (mapLayer.mMapAxis.y < 0) {
                        gameMain.appClass.touch.mPosY = 0;
                    } else if (mapLayer.mMapAxis.y > mapTotalH - mapDrawH) {
                        gameMain.appClass.touch.mPosY = mapTotalH - mapDrawH;
                    }
                    mapLayer.mMapAxis.x = (int) gameMain.appClass.touch.mPosX;
                    mapLayer.mMapAxis.y = (int) gameMain.appClass.touch.mPosY;

                    mapLayer.getTileNum(mapLayer.LeftTopTileNum,
                            mapLayer.mMapAxis.x, mapLayer.mMapAxis.y);

                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (hasTouchMap && mapLayer.isClick) {
                    //커서가 위치할 타일의 axis 계산
                    int x = (int) gameMain.appClass.touch.mLastTouchX - mapLayer.mMapAxis.x - mapMarginLeft;
                    int y = (int) gameMain.appClass.touch.mLastTouchY +
                            mapLayer.mMapAxis.y - mapMarginTop;
                    mapLayer.getTileNum(mapLayer.cursor.curTile, x, y);

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

                    if (mapLayer.cursor.curTile.x == unitEnty.curAxisX &&
                            mapLayer.cursor.curTile.y == unitEnty.curAxisY) {
                        unitZocList = checkZOC(unitEnty.curAxisX, unitEnty.curAxisY);
                        selectUnitEnty = unitEnty;
                        break;
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
                mapLayer.isClick = false;
                break;
        }
    }
}
