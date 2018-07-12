package com.epriest.game.guildfantasy.main.enty;

import android.graphics.Point;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by darka on 2017-06-22.
 */

public class MapEnty {

    public class MapLayer {
        @SerializedName("tilewidth")
        private int tilewidth;

        @SerializedName("tileheight")
        private int tileheight;

        @SerializedName("layers")
        public List<MapData> mapList = new ArrayList();

        public int getTileWidth() {
            return tilewidth;//Integer.parseInt(tilewidth);
        }

        public int getTileHeight() {
            return tileheight;//Integer.parseInt(tilewidth);
        }

        public List<MapData> getLayers() {
            return mapList;
        }

        public String tileImageName;

        /**
         * 맵 지형의 컬럼 리스트
         */
        public ArrayList<int[]> terrainColumnList;

        /**
         * 맵 오브젝트의 컬럼 리스트
         */
        public ArrayList<int[]> objectColumnList;

        public int mMapTileRowNum;
        public int mMapTileColumnNum;

        public Point mMapAxis;
        public Point LeftTopTileNum;

        public CursorTile cursor;

        public boolean isMapTouch;
        public boolean isClick;

        /**
         * 맵에 그려지는 타일의 height값 (tile Height의 3/4)
         */
        public int mTileHeightOnMap;

        public Point getTileNum(Point point, int x, int y) {
            if (x < 0)
                x *= -1;
            int selectTileMarginTop = getTileHeight() / 8;
            point.y = (y - selectTileMarginTop) / (getTileHeight() / 4 * 3);
            if (point.y < 0)
                point.y = 0;

            if (point.y % 2 == 0)
                point.x = (x + getTileWidth() / 2) / getTileWidth();
            else
                point.x = x / getTileWidth();

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

    public static class CursorTile {
//        public Point point;
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
}
