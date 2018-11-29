package com.epriest.game.guildfantasy.main.enty;

import android.graphics.Bitmap;
import android.graphics.Point;

public class UnitEnty {
    final public int unitW = 70;
    final public int unitH = 70;
    final public int unitprofileW = 60;
    final public int uinitprofileH = 70;
    final public int unitbarW = 10;
    final public int untibarH = 60;
    final public int unitnamebarW = 70;
    final public int unitnamebarH = 15;
    final public int unitfontsize = 15;

    public MemberEnty memberEnty;
    /**
     * formation position
     */
    public int pos;
    public int num;
    public String id;

    /**
     * 유닛의 첫 시작 타일 위치
     */
    public Point startTileAxis = new Point();
//    public int startAxisX;
//    public int startAxisY;
    /**
     * 유닛의 현재 타일 위치
     */
    public Point curTileAxis = new Point();
//    public int curAxisX;
//    public int curAxisY;
    /**
     * 유닛의 다음 타일 위치
     */
    public int nextAxisX;
    public int nextAxisY;

    public Bitmap chr_img;
}
