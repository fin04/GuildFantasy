package com.epriest.game.guildfantasy.main;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.epriest.game.CanvasGL.graphics.CanvasUtil;
import com.epriest.game.CanvasGL.graphics.GLUtil;
import com.epriest.game.guildfantasy.enty.ButtonEnty;

import java.util.ArrayList;

/**
 * Created by darka on 2017-03-26.
 */

public class Scene_Main {

    private final int charRow = 10;
    private final int charNum = 28;
    private final int charW = 80;
    private final int charH = 96;

    private final int insigniaRow = 10;
    private final int insigniaNum = 20;
    private final int insigniaW = 80;
    private final int insigniaH = 96;

    private final int btnW = 96;
    private final int btnH = 84;

    public final static int statusBarH = 45;

    private int canvasW, canvasH;
    private Bitmap menu_icon;
    private Bitmap status_bar;
    private Game_Main gameMain;

    public Scene_Main(){

    }

    public void initScene(Game_Main gameMain) {
        this.gameMain = gameMain;
        this.canvasW = gameMain.appClass.getGameCanvasWidth();
        this.canvasH = gameMain.appClass.getGameCanvasHeight();
        menu_icon = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/main_btn.png", null);
        status_bar = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/statusBar.png", null);

        gameMain.menuButtonList = setMenuIcon(canvasW, canvasH);
        gameMain.appClass.isSceneInit = false;
    }

    private ArrayList<ButtonEnty> setMenuIcon(int canvasW, int canvasH) {
        ArrayList<ButtonEnty> menuButtonList = new ArrayList<>();
        for(int i=0; i< gameMain.menuIconName.length-2; i++) {
            ButtonEnty mBtn = new ButtonEnty();
            mBtn.num = i;
            mBtn.name = gameMain.menuIconName[i];
            mBtn.iconImgNum = gameMain.menuIconNum[i];
            mBtn.w = btnW;
            mBtn.h = btnH;
            mBtn.x = canvasW - 110 - (4-i)*(mBtn.w+10);
            mBtn.y = canvasH - mBtn.h - 10;
            menuButtonList.add(mBtn);
        }
        return  menuButtonList;
    }

    public void drawMain(Canvas mCanvas, Paint paint, boolean viewMenuButton){
        //menu button
        if(viewMenuButton)
            drawMenuButton(mCanvas, paint);

        // status bar
        drawStatusBar(mCanvas, paint);
    }

    public void drawStatusBar(Canvas mCanvas, Paint paint) {
        CanvasUtil.drawBitmap(status_bar, mCanvas, null, 0, 0);
        paint.setColor(Color.argb(255, 50, 50, 50));
        paint.setTextSize(25);
        CanvasUtil.drawString(mCanvas, gameMain.playerEnty.Name, paint, 30, 3);
        CanvasUtil.drawString(mCanvas, "Level "+ gameMain.playerEnty.LEVEL, paint, 150, 3);
        CanvasUtil.drawString(mCanvas, "quest "+ gameMain.playerEnty.QUESTLIST.size(), paint, 300, 3);
        CanvasUtil.drawString(mCanvas, "party "+ gameMain.playerEnty.PARTYLIST.size(), paint, 450, 3);
        CanvasUtil.drawString(mCanvas, "member "+ gameMain.playerEnty.MEMBERLIST.size(), paint, 600, 3);
    }

    public void drawMenuButton(Canvas mCanvas, Paint paint) {
//        int btnArea = (canvasH - statusBarH)/ gameMain.menuButtonList.size();
        for(ButtonEnty mBtn : gameMain.menuButtonList){
            int iconNum = mBtn.iconImgNum-1;
            if(mBtn.clickState == ButtonEnty.ButtonClickOn){
                iconNum+=4;
            }
            CanvasUtil.drawClip(menu_icon, mCanvas, null, (iconNum%4)*mBtn.w, (iconNum/4)*mBtn.h,
                    mBtn.w, mBtn.h, mBtn.x, mBtn.y);
//            CanvasUtil.drawClip(menu_icon, mCanvas, null, (iconNum%5)*mBtn.w, (iconNum/5)*mBtn.h,
//                    mBtn.w, mBtn.h, mBtn.x+(btnArea-mBtn.w)/2, mBtn.y);

        }
    }

}
