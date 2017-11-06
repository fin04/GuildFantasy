package com.epriest.game.guildfantasy.main;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.epriest.game.CanvasGL.graphics.CanvasUtil;
import com.epriest.game.CanvasGL.graphics.GLUtil;
import com.epriest.game.guildfantasy.main.enty.ButtonEnty;
import com.epriest.game.guildfantasy.main.enty.MemberEnty;
import com.epriest.game.guildfantasy.main.play.DataManager;
import com.epriest.game.guildfantasy.util.INN;

import java.util.ArrayList;

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
    private Bitmap card;

    public int charW = 80;
    public int charH = 97;
    private int charRow = 10;
    private int charMax = 30;
    private int scrollY, prevScrollY;

    private int left = 110;
    private int top = 100;

//    private int cardH = 200;
//    private int cardW = 165;
    private int cardRow = 4;

    public int mMainScreenY;

    public int selectMember;

    public ArrayList<ButtonEnty> classButtonList;
    public ArrayList<MemberEnty> memberList;
    private Game_Main gameMain;

    public View_Member(Game_Main gameMain) {
        this.gameMain = gameMain;
    }

    public boolean onTouchEvent(MotionEvent event) {
        /*if(event.getAction() == MotionEvent.ACTION_UP) {
            if(prevScrollY == scrollY){
                selectMember = (((int)gameMain.appClass.touch.axisX-left) / card.getWidth())
                        + (((int)gameMain.appClass.touch.axisY+scrollY-top)/card.getHeight())*cardRow + 1;
                return true;
            }else
                prevScrollY = scrollY;
        }
        scrollY = prevScrollY + (int)(gameMain.appClass.touch.oriY - gameMain.appClass.touch.axisY);
        int maxScrollY = (gameMain.userEnty.MEMBERLIST.size()/4+1) * 200 - gameMain.appClass.getGameCanvasHeight() + 100;
        if(scrollY < 0)
            scrollY = 0;
        else if(scrollY > maxScrollY)
            scrollY = maxScrollY;*/

        return false;
    }

    public void initScene() {
        canvasW = gameMain.canvasW;
        canvasH = gameMain.canvasH;

        char_01 = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/char_01.jpg", null);
        classesMark = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/classes_mark.png", null);
        card = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/membercard.png", null);

        classButtonList = setClassesButton();

    }
    public void recycleScene() {
        recycleBitmap(char_01);
        recycleBitmap(classesMark);
        recycleBitmap(card);
    }

    private ArrayList<ButtonEnty> setClassesButton() {
        ArrayList<ButtonEnty> menuButtonList = new ArrayList<>();
        for (int i = 0; i < INN.classNameArr.length; i++) {
            ButtonEnty mBtn = new ButtonEnty();
            mBtn.num = i;
            mBtn.name = INN.classNameArr[i];
            mBtn.iconImgNum = i;
            mBtn.clipW = classesMark.getWidth()/4;
            mBtn.clipH = classesMark.getHeight()/4;
            mBtn.drawX = 10;
            mBtn.drawY = (canvasH - mMainScreenY - 20) / (INN.classNameArr.length) * i
                    + mMainScreenY + 10;
            menuButtonList.add(mBtn);
        }
        return menuButtonList;
    }

    public void draw(Canvas mCanvas) {

//        drawClassesButton(mCanvas);
        drawMemberList(mCanvas);

    }

    int aa=1;
    int a = 1;
    private void drawMemberList(Canvas mCanvas) {
        Paint paint = new Paint();
        paint.setTextSize(17);

        aa+=6;
        int cW = card.getWidth()-aa;

        if(cW == 0)
            cW = 1;
        if(Math.abs(cW) > card.getWidth()){
            aa = 0;
            a = 0;
        }

        int cH = 0;

        if(cW <= 0) {
            if(a == 0)
                a=aa;
            cH = (a - (aa - a))/7;
        }else{
            cH = aa/7;
        }

        memberList = DataManager.getGuildMemberList(gameMain.dbAdapter, gameMain.userEnty.Name);
        for(int i=0; i< memberList.size() ; i++) {
            MemberEnty enty = memberList.get(i);
            int sheetLeft = (card.getWidth()+5)*(i%cardRow);
            int sheetTop = (card.getHeight()+5)*(i/cardRow) - scrollY;

            Matrix matrix = new Matrix();
            /*float[] src = new float[] { 0, 0, // 左上
                    card.getWidth(), 0,// 右上
                    card.getWidth(), card.getHeight(),// 右下
                    0, card.getHeight() };// 左下
            float[] dst = new float[] { 0, 0,
                    cW, cH,
                    cW, card.getHeight() - cH,
                    0,card.getHeight() };
            matrix.setPolyToPoly(src, 0, dst, 0, src.length/2);*/

            Bitmap mCanvasBitmap = Bitmap.createBitmap(card.getWidth(), card.getHeight(), Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(mCanvasBitmap);
            canvas.drawBitmap(card, 0,0, null);
            drawClip(char_01, canvas, (enty.iconid%10)*charW,(enty.iconid/10)*charH,charW, charH,((165-charW)/2), 20);
            paint.setColor(Color.argb(255, 0,0,220));
            paint.setTextAlign(Paint.Align.CENTER);
            drawString(canvas, enty.name, paint, (165/2), charH+20);
            Bitmap bm = Bitmap.createBitmap(mCanvasBitmap, 0, 0, card.getWidth(),
                    card.getHeight(), matrix, true);


            CanvasUtil.drawBitmap(bm, mCanvas, left +sheetLeft, top +sheetTop);
            /*drawClip(char_01, mCanvas, (enty.imageId%10)*charW,(enty.imageId/10)*charH,charW, charH,left+sheetLeft+((165-charW)/2), top +sheetTop+20);
            paint.setColor(Color.argb(255, 0,0,220));
            paint.setTextAlign(Paint.Align.CENTER);
            drawString(mCanvas, enty.name, paint, left+sheetLeft+(165/2), top +sheetTop);*/
        }
        paint.setTextAlign(Paint.Align.LEFT);
    }

    public void drawClassesButton(Canvas mCanvas) {
        int btnArea = (canvasH - mMainScreenY)/ classButtonList.size();
        for(ButtonEnty mBtn : classButtonList){
            int iconNum = mBtn.iconImgNum;
//            if(mBtn.clickState == ButtonEnty.ButtonClickOn){
//                iconNum+=5;
//            }
            drawClip(classesMark, mCanvas, (iconNum%4)*mBtn.clipW, (iconNum/4)*mBtn.clipH,
                    mBtn.clipW, mBtn.clipH, mBtn.drawX+(btnArea-mBtn.clipW)/2, mBtn.drawY);
        }
    }


}
