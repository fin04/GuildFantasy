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
        private List<MapData> mapList = new ArrayList<MapData>();

        public int getTileWidth() {
            return tilewidth;//Integer.parseInt(tilewidth);
        }

        public int getTileHeight() {
            return tileheight;//Integer.parseInt(tilewidth);
        }

        public List<MapData> getLayers() {
            return mapList;
        }

        public ArrayList<int[]> terrainColumnList;
        public ArrayList<int[]> buildiingColumnList;

        public int mMapTileRowNum;
        public int mMapTileColumnNum;

        public Point mMapAxis;
        public Point LeftTopTileNum;

        public CursorTile cursor;

        public boolean isMapTouch;
        public boolean isClick;

        public int mTileHeightForMap;

        public Point getTileNum(Point point, int x, int y) {
            if (x < 0)
                x *= -1;
            point.y = y / (getTileHeight() / 4 * 3);

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
        private List<Integer> layer = new ArrayList<Integer>();

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
            return layer;
        }

        public int getX() {
            return Integer.parseInt(x);
        }

        public int getY() {
            return Integer.parseInt(y);
        }
    }

    public static class CursorTile{
        public Point point;
        public Point curTile;
        public int tileNum;
        public int animCnt;
        public int state;
    }
}
