package com.epriest.game.guildfantasy.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.epriest.game.CanvasGL.graphics.CanvasUtil;
import com.epriest.game.CanvasGL.graphics.GLUtil;
import com.epriest.game.guildfantasy.enty.ButtonEnty;
import com.epriest.game.guildfantasy.enty.PlayerEnty;

import java.util.ArrayList;

/**
 * Created by darka on 2017-03-26.
 */

public class Scene_Main {

    private String[] menuIcon = {"Home" , "Party", "Member", "Town", "Quest", "Setting"};
    private int[] menuIconNum = {1 , 2, 3, 4, 5, 20};

    private final int charRow = 10;
    private final int charNum = 28;
    private final int charW = 80;
    private final int charH = 96;

    private final int insigniaRow = 10;
    private final int insigniaNum = 20;
    private final int insigniaW = 80;
    private final int insigniaH = 96;

    private final int iconRow = 5;
    private final int iconNum = 20;
    private final int iconW = 120;
    private final int iconH = 120;

    private final int statusBarH = 32;

    private int canvasW, canvasH;
    private Bitmap menu_icon;

    private Game_Main gameMain;

    public Scene_Main(){

    }

    public void initScene(Game_Main gameMain) {
        this.gameMain = gameMain;
        this.canvasW = gameMain.appClass.getGameCanvasWidth();
        this.canvasH = gameMain.appClass.getGameCanvasHeight();
        menu_icon = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/main_icon.png", null);

        gameMain.menuButtonList = setMenuIcon(canvasW, canvasH);
        gameMain.appClass.isSceneInit = false;
    }


    public ArrayList<ButtonEnty> setMenuIcon(int canvasW, int canvasH) {
        ArrayList<ButtonEnty> menuButtonList = new ArrayList<>();
        for(int i=0; i< menuIcon.length-1; i++) {
            ButtonEnty mBtn = new ButtonEnty();
            mBtn.num = i;
            mBtn.name = menuIcon[i];
            mBtn.iconImgNum = menuIconNum[i];
            mBtn.w = iconW;
            mBtn.h = iconH;
            mBtn.x = canvasW - iconW;
            mBtn.y = (canvasH - statusBarH)/(menuIcon.length-2)*i +statusBarH;;
            menuButtonList.add(mBtn);
        }
        return  menuButtonList;

        //setting button
        /*ButtonEnty mBtn = new ButtonEnty();
        mBtn.num = menuIcon.length;
        mBtn.name = menuIcon[menuIcon.length-1];
        mBtn.iconImgNum = menuIconNum[menuIcon.length-1];
        mBtn.w = iconW;
        mBtn.h = iconH;
        mBtn.x = gameMain.menuButtonList.get(gameMain.menuButtonList.size()-1).x;
        mBtn.y = gameMain.menuButtonList.get(gameMain.menuButtonList.size()-1).y - iconH - 20 ;
        gameMain.menuButtonList.add(mBtn);*/
    }

    public void drawMain(Canvas mCanvas, Paint paint){
        //menu button
        drawMenuButton(mCanvas, paint);

        // status bar
        drawStatusBar(mCanvas, paint);
    }

    public void drawStatusBar(Canvas mCanvas, Paint paint) {
        CanvasUtil.drawBox(mCanvas, paint, Color.argb(220, 145, 50, 70), true, 0, 0, canvasW, statusBarH);
        paint.setColor(Color.argb(255, 200, 230, 230));
        paint.setTextSize(25);
        CanvasUtil.drawString(mCanvas, gameMain.playerEnty.Name, paint, 30, 3);
        CanvasUtil.drawString(mCanvas, "Level "+ gameMain.playerEnty.LEVEL, paint, 150, 3);
        CanvasUtil.drawString(mCanvas, "quest "+ gameMain.playerEnty.QUESTLIST.size(), paint, 300, 3);
        CanvasUtil.drawString(mCanvas, "party "+ gameMain.playerEnty.PARTYLIST.size(), paint, 450, 3);
        CanvasUtil.drawString(mCanvas, "member "+ gameMain.playerEnty.MEMBERLIST.size(), paint, 600, 3);
        CanvasUtil.drawString(mCanvas, "Turn "+ gameMain.playerEnty.TURN, paint, 800, 3);
    }

    public void drawMenuButton(Canvas mCanvas, Paint paint) {
        int btnArea = (canvasH-statusBarH)/ gameMain.menuButtonList.size();
        for(ButtonEnty mBtn : gameMain.menuButtonList){
            int iconNum = mBtn.iconImgNum-1;
            if(mBtn.clickState == ButtonEnty.ButtonClickOn){
                iconNum+=5;
            }
            CanvasUtil.drawClip(menu_icon, mCanvas, paint, (iconNum%5)*mBtn.w, (iconNum/5)*mBtn.h,
                    mBtn.w, mBtn.h, mBtn.x+(btnArea-mBtn.w)/2, mBtn.y);
            paint.setTextSize(20);
            CanvasUtil.drawString(mCanvas, mBtn.name, paint, mBtn.x+(btnArea-mBtn.w)/2, mBtn.y);
        }
    }

}
