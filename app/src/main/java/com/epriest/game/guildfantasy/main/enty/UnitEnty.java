package com.epriest.game.guildfantasy.main.enty;

import android.graphics.Bitmap;

public class UnitEnty {
    final public int unitW = 80;
    final public int unitH = 100;
    final public int unitprofileW = 70;
    final public int uinitprofileH = 80;
    final public int unitbarW = 10;
    final public int untibarH = 80;
    final public int unitnamebarW = 80;
    final public int unitnamebarH = 20;
    final public int unitfontsize = 15;

    public MemberEnty memberEnty;
    /**
     * formation position
     */
    public int pos;
    public int num;
    public String id;

    public int startAxisX;
    public int startAxisy;
    public int curAxisX;
    public int curAxisY;
    public int nextAxisX;
    public int nextAxisY;

    public Bitmap profile;
}
