package com.epriest.game.guildfantasy.main;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.widget.Toast;

import com.epriest.game.CanvasGL.graphics.GLUtil;
import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.CanvasGL.util.GameUtil;
import com.epriest.game.guildfantasy.enty.ButtonEnty;
import com.epriest.game.guildfantasy.enty.MemberEnty;

import java.util.ArrayList;

import static com.epriest.game.CanvasGL.graphics.CanvasUtil.drawBox;
import static com.epriest.game.CanvasGL.graphics.CanvasUtil.drawClip;
import static com.epriest.game.CanvasGL.graphics.CanvasUtil.drawString;
import static com.epriest.game.CanvasGL.graphics.CanvasUtil.recycleBitmap;

/**
 * Created by darka on 2017-04-09.
 */

public class View_Member {

    public static final int MODE_VIEWMEMBER_STATE_OFF = 0;
    public static final int MODE_VIEWMEMBER_STATE_INIT = 1;
    public static final int MODE_VIEWMEMBER_STATE_ON = 2;

    private int canvasW, canvasH;

    public Bitmap char_01;
    private Bitmap classesMark;

    public int charW = 80;
    public int charH = 97;
    private int charRow = 10;
    private int charMax = 30;
    private int scrollY, prevScrollY;

    private int left = 110;
    private int top = 100;

    private int cardH = 200;
    private int cardW = 165;
    private int cardRow = 4;

    public int selectMember;

    public ArrayList<ButtonEnty> classButtonList;
    public String[] classNameArr = {"All", "Knight", "Sellsword", "Cleric", "Mage", "Ranger", "Rogue"};

    private Game_Main gameMain;

    public View_Member(Game_Main gameMain) {
        this.gameMain = gameMain;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_UP) {
            if(prevScrollY == scrollY){
                selectMember = (((int)gameMain.appClass.touch.axisX-left) / cardW)
                        + (((int)gameMain.appClass.touch.axisY+scrollY-top)/cardH)*cardRow + 1;
                return true;
            }else
                prevScrollY = scrollY;
        }
        scrollY = prevScrollY + (int)(gameMain.appClass.touch.oriY - gameMain.appClass.touch.axisY);
        int maxScrollY = (gameMain.playerEnty.MEMBERLIST.size()/4+1) * 200 - gameMain.appClass.getGameCanvasHeight() + 100;
        if(scrollY < 0)
            scrollY = 0;
        else if(scrollY > maxScrollY)
            scrollY = maxScrollY;

        return false;
    }

    public void initScene() {
        canvasW = gameMain.appClass.getGameCanvasWidth();
        canvasH = gameMain.appClass.getGameCanvasHeight();

        char_01 = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/char_01.jpg", null);
        classesMark = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/classes_mark.png", null);

        classButtonList = setClassesButton();

    }
    public void recycleScene() {
        recycleBitmap(char_01);
        recycleBitmap(classesMark);
    }

    private ArrayList<ButtonEnty> setClassesButton() {
        ArrayList<ButtonEnty> menuButtonList = new ArrayList<>();
        for (int i = 0; i < classNameArr.length; i++) {
            ButtonEnty mBtn = new ButtonEnty();
            mBtn.num = i;
            mBtn.name = classNameArr[i];
            mBtn.iconImgNum = i;
            mBtn.w = classesMark.getWidth()/4;
            mBtn.h = classesMark.getHeight()/4;
            mBtn.x = 10;
            mBtn.y = (canvasH - Scene_Main.statusBarH - 20) / (classNameArr.length) * i
                    + Scene_Main.statusBarH + 10;
            menuButtonList.add(mBtn);
        }
        return menuButtonList;
    }

    public void draw(Canvas mCanvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
//        mCanvas.drawColor(Color.BLACK);

        drawClassesButton(mCanvas, paint);
        drawMemberList(mCanvas, paint);

    }

    private void drawMemberList(Canvas mCanvas, Paint paint) {
        paint.setTextSize(17);
        for(int i=0; i< gameMain.playerEnty.MEMBERLIST.size() ; i++) {
            MemberEnty enty = gameMain.playerEnty.MEMBERLIST.get(i);
            int sheetLeft = cardW*(i%cardRow);
            int sheetTop = cardH*(i/cardRow) - scrollY;
            drawBox(mCanvas, paint, Color.argb(200, 230, 230, 255), true, left +sheetLeft, top +sheetTop, 150, 180);
            drawClip(char_01, mCanvas, null, (enty.imageId%10)*charW,(enty.imageId/10)*charH,charW, charH,left+sheetLeft+((165-charW)/2), top +sheetTop+20);
            paint.setColor(Color.argb(255, 0,0,220));
            paint.setTextAlign(Paint.Align.CENTER);
            drawString(mCanvas, enty.name, paint, left+sheetLeft+(165/2), top +sheetTop);
        }
        paint.setTextAlign(Paint.Align.LEFT);
    }

    public void drawClassesButton(Canvas mCanvas, Paint paint) {
        int btnArea = (canvasH - Scene_Main.statusBarH)/ classButtonList.size();
        for(ButtonEnty mBtn : classButtonList){
            int iconNum = mBtn.iconImgNum;
//            if(mBtn.clickState == ButtonEnty.ButtonClickOn){
//                iconNum+=5;
//            }
            drawClip(classesMark, mCanvas, paint, (iconNum%4)*mBtn.w, (iconNum/4)*mBtn.h,
                    mBtn.w, mBtn.h, mBtn.x+(btnArea-mBtn.w)/2, mBtn.y);
        }
    }


}
