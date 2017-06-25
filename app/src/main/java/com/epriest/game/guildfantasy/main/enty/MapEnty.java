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
        private String tilewidth;

        @SerializedName("tileheight")
        private String tileheight;

        @SerializedName("layers")
        private List<MapData> mapList = new ArrayList<MapData>();

        public int getTileWidth() {
            return Integer.parseInt(tilewidth);
        }

        public int getTileHeight() {
            return Integer.parseInt(tilewidth);
        }

        public List<MapData> getLayers() {
            return mapList;
        }

        public ArrayList<int[]> terrainColumnList;
        public ArrayList<int[]> buildiingColumnList;

        public int mMapTileRowNum;
        public int mMapTileColumnNum;

        public Point mMapAxis;
        public Point curClickTile;
        public Point LeftTopTileNum;

        public boolean isMapMove;

        public Point getTileNum(Point point, int x, int y) {
            if (x < 0)
                x *= -1;
            point.x = x / getTileWidth();
            point.y = y / getTileHeight();
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
}
