package com.epriest.game.guildfantasy.main.enty;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by darka on 2017-03-25.
 */

public class ImageEnty {
    public static final int ButtonClickOff = 0;
    public static final int ButtonClickOn = 1;
    public int clickState;

    public String id;
    public String name;
    public Bitmap bitmap;
    public boolean  isAnim;

    public int x;
    public int y;
    public int w;
    public int h;

    public int animCnt;
    public int animMaxFrame;
    public ArrayList<Integer> animFrame;
    public ArrayList<ClipImageEnty> animClipList;


}
