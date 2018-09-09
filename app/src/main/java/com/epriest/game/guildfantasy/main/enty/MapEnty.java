package com.epriest.game.guildfantasy.main.enty;

import android.graphics.Point;
import android.util.Log;

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

        /**
         * 캔버스 내에 그려지는 타일의 총 갯수
         */
        public int mapCanvasTileTotalX, mapCanvasTileTotalY;

        /**
         * 맵의 스크롤 이동 좌표
         */
        public Point mMapScrollAxis;
        /**
         * 맵의 최상단왼쪽에 위치하는 타일의 Axis
         */
        public Point LeftTopTileAxis;

        public CursorTile cursor;

        public boolean isMapTouch;
        public boolean isClick;

        /**
         * 맵에 그려지는 타일의 height값 (tile Height의 3/4)
         */
        public int mTileHeightOnMap;

        /**
         * 픽셀 좌표를 타일 좌표로 변환
         * @param pX
         * @param pY
         * @return
         */
        public Point getTileAxis(int pX, int pY, int mapMarginLeft, int mapMarginTop) {
            int x = pX - mapMarginLeft;
            int y = pY - mapMarginTop;

            Point tilePoint = new Point();
            //tileAxis Y 계산
            tilePoint.y = y / (getTileHeight() / 4 * 3);
            if (tilePoint.y < 0)
                tilePoint.y = 0;
            else if(tilePoint.y >= mapCanvasTileTotalY)
                tilePoint.y = getLayers().get(0).getHeight()-mapCanvasTileTotalY-1;

            //tileAxis X 계산
            int cx = Math.abs(x);
            if (tilePoint.y % 2 == 0)
                tilePoint.x = (cx + getTileWidth() / 2) / getTileWidth();
            else
                tilePoint.x = cx / getTileWidth();
            if(tilePoint.x < 0)
                tilePoint.x = 0;
            else if(tilePoint.x >= mapCanvasTileTotalX)
                tilePoint.x = getLayers().get(0).getWidth()-mapCanvasTileTotalX-1;
            Log.d("","px:"+tilePoint.x+",py:"+tilePoint.y);

            return tilePoint;
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
