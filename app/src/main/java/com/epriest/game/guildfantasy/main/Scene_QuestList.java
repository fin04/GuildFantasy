package com.epriest.game.guildfantasy.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.CanvasGL.util.Scene;
import com.epriest.game.guildfantasy.main.enty.QuestEnty;
import com.epriest.game.guildfantasy.util.DrawUtil;

/**
 * Created by darka on 2017-03-26.
 */

public class Scene_QuestList extends Scene {

    private Context context;
    private Game_QuestList gameQuestList;
//    private Scene_Main sceneMain;


    public Scene_QuestList(Game_QuestList gameQuest, Scene_Main sceneMain) {
        this.gameQuestList = gameQuest;
//        this.sceneMain = sceneMain;
    }

    @Override
    public void initScene(ApplicationClass appClass) {
        this.context = appClass.getBaseContext();

    }

    @Override
    public void recycleScene() {
        DrawUtil.recycleBitmap(gameQuestList.bg);
        DrawUtil.recycleBitmap(gameQuestList.questcard);
        for (Bitmap bm : gameQuestList.questBitmapList) {
            DrawUtil.recycleBitmap(bm);
        }
    }

    @Override
    public void drawLoading(Canvas mCanvas, Bitmap loading) {

    }

    @Override
    public void draw(Canvas mCanvas) {
//        mCanvas.drawColor(Color.BLACK);

//        mCanvas.drawBitmap(bg, 0, 0, null);
        DrawUtil.drawBgBitmap(gameQuestList.bg, mCanvas);
        drawQuestList(mCanvas);

        gameQuestList.gameMain.drawStatusTab(mCanvas);
    }

    private void drawQuestList(Canvas mCanvas) {

        for (int i = 0; i < gameQuestList.gameMain.userEnty.QUESTLIST.size(); i++) {
            QuestEnty enty = gameQuestList.gameMain.userEnty.QUESTLIST.get(i);
            int drawY = enty.btnEnty.drawY;
            DrawUtil.drawClip(gameQuestList.questcard, mCanvas, enty.btnEnty.clipX, enty.btnEnty.clipY,
                    enty.btnEnty.clipW, enty.btnEnty.clipH, enty.btnEnty.drawX, drawY);

            int type = Game_Quest.QuestType.valueOf(enty.type).ordinal();
            String label = Game_Quest.QuestType.valueOf(enty.type).getLabel();


            //quest img (200x200)
            DrawUtil.drawClip(gameQuestList.questBitmapList.get(i), mCanvas, 0, 0, 200, 200,
                    475, drawY + 25);

            //label_eng
            DrawUtil.drawClip(gameQuestList.questcard, mCanvas, 150 + type % 2 * 100, 275 + type / 2 * 25,
                    90, 25, enty.btnEnty.drawX + (enty.btnEnty.clipW - 90) / 2, drawY + 30);

            //label_kor
//            DrawUtil.drawString(mCanvas, label, 20, Color.argb(255,100,40,40),
//                    Paint.Align.CENTER, enty.btnEnty.drawX + enty.btnEnty.clipW/2,enty.btnEnty.drawY+10);

            //title
            DrawUtil.drawString(mCanvas, enty.title, 40, Color.argb(210, 10, 10, 25),
                    Paint.Align.LEFT, enty.btnEnty.drawX + 55, drawY + 45);

            //text
//            for (int j = 0; j < enty.textArr.size(); j++) {
//                if (j < gameQuestList.textLineLimit) {
//                    DrawUtil.drawString(mCanvas, enty.textArr.get(j), 24, Color.argb(210, 10, 10, 25),
//                            Paint.Align.LEFT, enty.btnEnty.drawX + 25, drawY + 120 + j * 27);
//                } else {
//                    break;
//                }
//            }

            //quest difficult stemp
            for (int j = 0; j < enty.difficult; j++) {
                DrawUtil.drawClip(gameQuestList.questcard, mCanvas, 100, 250,
                        50, 50, enty.btnEnty.drawX + j * 35, drawY + 5);
            }

            if (enty.actPartyNum > 0)
                DrawUtil.drawClip(gameQuestList.questcard, mCanvas, 201, 164,
                        51, 49, enty.btnEnty.drawX + 140, drawY + 8);

            //reward
            DrawUtil.drawBox(mCanvas, Color.argb(180,150,170,10), true,
                    enty.btnEnty.drawX + 20, drawY + 150, 60, 30);
            DrawUtil.drawString(mCanvas, "gold", 22, Color.rgb(200, 220, 220),
                            Paint.Align.CENTER, enty.btnEnty.drawX + 50, drawY + 150);
            DrawUtil.drawString(mCanvas, Integer.toString(enty.rewardGold), 24, Color.rgb(20, 20, 20),
                    Paint.Align.CENTER, enty.btnEnty.drawX + 50, drawY + 180);

            DrawUtil.drawBox(mCanvas, Color.argb(180,20,80,180), true,
                    enty.btnEnty.drawX + 90, drawY + 150, 60, 30);
            DrawUtil.drawString(mCanvas, "exp", 22, Color.rgb(200, 220, 220),
                    Paint.Align.CENTER, enty.btnEnty.drawX + 120, drawY + 150);
            DrawUtil.drawString(mCanvas, Integer.toString(enty.rewardExp), 24, Color.rgb(20, 20, 20),
                    Paint.Align.CENTER, enty.btnEnty.drawX + 120, drawY + 180);

            for(int j=0; j<5; j++) {
                DrawUtil.drawBox(mCanvas, Color.argb(180, 180, 20, 10), false,
                        enty.btnEnty.drawX + 170+50*j, drawY + 150, 50, 50);
            }
        }
    }

}
