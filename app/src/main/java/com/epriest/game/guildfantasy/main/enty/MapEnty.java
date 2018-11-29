package com.epriest.game.guildfantasy.main.enty;

import android.graphics.Point;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by darka on 2017-06-22.
 */

public class MapEnty {

    public static class CursorTile {
        /**
         * 커서가 가리키는 타일의 Axis
         */
        public Point curTile;
        /**
         * 커서가 카리키는 타일의 지형넘버
         */
        public int tileTerrianNum;
        /**
         * 커서가 가리키는 타일의 오브젝트 넘버
         */
        public int tileObjectNum;
        public int animCnt;
//        public int state;
    }

    public class MapLayer {
        @SerializedName("layers")
        public List<MapData> mapList = new ArrayList();
        public String tileImageName;
        /**
         * 맵 지형의 컬럼 리스트
         */
        public ArrayList<int[]> terrainColumnList;
        /**
         * 맵 오브젝트의 컬럼 리스트
         */
        public ArrayList<int[]> objectColumnList;
        /**
         * 맵의 최상단왼쪽에 위치하는 타일의 Axis
         */
        public Point canvasMap_FirstTile;
        /**
         * 캔버스 내에 그려지는 타일의 총 갯수
         */
        public Point canvasMap_LastTile;
        /**
         * 게임 내에 존재하는 타일의 총 갯수
         */
        public Point gameMap_LastTile;
        /**
         * 맵의 스크롤 이동 좌표
         */
        public Point gameMap_ScrollAxis;


        public CursorTile cursor;
        public boolean isMapTouch;
        public boolean isClick;
        /**
         * 맵에 그려지는 타일의 height값 (tile Height의 3/4)
         */
        public int mTileHeightOnMap;
        @SerializedName("tilewidth")
        private int tilewidth;
        @SerializedName("tileheight")
        private int tileheight;

        public int getTileWidth() {
            return tilewidth;//Integer.parseInt(tilewidth);
        }

        public int getTileHeight() {
            return tileheight;//Integer.parseInt(tilewidth);
        }

        public List<MapData> getLayers() {
            return mapList;
        }

        /**
         * 픽셀 좌표를 타일 좌표로 변환
         *
         * @param pX
         * @param pY
         * @return
         */
        public Point getTileAxis(int pX, int pY) {
            Point tilePoint = new Point();
            //tileAxis Y 계산
            tilePoint.y = pY / (getTileHeight() / 4 * 3);
            if (tilePoint.y < 0)
                tilePoint.y = 0;
            else if (tilePoint.y >= canvasMap_LastTile.y)
                tilePoint.y = canvasMap_LastTile.y - 1;

            //tileAxis X 계산
            int cx = Math.abs(pX);
            if (tilePoint.y % 2 == 1)
                tilePoint.x = (cx + getTileWidth() / 2) / getTileWidth();
            else
                tilePoint.x = cx / getTileWidth();
            if (tilePoint.x < 0)
                tilePoint.x = 0;
            else if (tilePoint.x >= canvasMap_LastTile.x)
                tilePoint.x = canvasMap_LastTile.x - 1;

            return tilePoint;
        }

        /**
         * 타일 좌표가 그려지는 맵위를 벗어나는지 체크
         *
         * @return
         */
        public Point checkTileDrawMapRange(int tileX, int tileY) {
            Point point = new Point();
            int lastX = getLayers().get(0).getWidth() - canvasMap_LastTile.x;
            int lastY = getLayers().get(0).getHeight() - canvasMap_LastTile.y;
            if (tileX > lastX)
                point.x = lastX;
            if (tileY > lastY)
                point.y = lastY;

            return point;
        }

        /**
         * hexa 좌표를 draw 좌표로 전환
         *
         * @return
         */
        public Point getHexaDrawAxis(int hexaX, int hexaY) {
            Point point = new Point();
            int startX = 0;
            if (hexaY % 2 == 0)
                startX = gameMap_ScrollAxis.x % tilewidth;
            else
                startX = gameMap_ScrollAxis.x % tilewidth - (tilewidth / 2);

            point.x = hexaX * tilewidth - startX;
            point.y = hexaY * mTileHeightOnMap - gameMap_ScrollAxis.y % tileheight;
            return point;
        }
    }

    public class MapData {
        @SerializedName("width")
        private String width;

        @SerializedName("height")
        private String height;

        @SerializedName("data")
        private List<Integer> map = new ArrayList<Integer>();

        @SerializedName("x")
        private String x;

        @SerializedName("y")
        private String y;

        public int getWidth() {
            return Integer.parseInt(width);
        }

        public int getHeight() {
            return Integer.parseInt(height);
        }

        public List<Integer> getDataList() {
            return map;
        }

        public int getX() {
            return Integer.parseInt(x);
        }

        public int getY() {
            return Integer.parseInt(y);
        }
    }
}
