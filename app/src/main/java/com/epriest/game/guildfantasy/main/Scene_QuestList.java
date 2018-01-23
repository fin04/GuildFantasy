package com.epriest.game.guildfantasy.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.epriest.game.CanvasGL.graphics.CanvasUtil;
import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.CanvasGL.util.Scene;
import com.epriest.game.guildfantasy.main.enty.QuestEnty;

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
        CanvasUtil.recycleBitmap(gameQuestList.bg);
        CanvasUtil.recycleBitmap(gameQuestList.questcard);
        for (Bitmap bm : gameQuestList.questBitmapList) {
            CanvasUtil.recycleBitmap(bm);
        }
    }

    @Override
    public void drawLoading(Canvas mCanvas, Bitmap loading) {

    }

    @Override
    public void draw(Canvas mCanvas) {
//        mCanvas.drawColor(Color.BLACK);

//        mCanvas.drawBitmap(bg, 0, 0, null);
        CanvasUtil.drawBgBitmap(gameQuestList.bg, mCanvas);
        drawQuestList(mCanvas);

        gameQuestList.gameMain.drawStatusTab(mCanvas);
    }

    private void drawQuestList(Canvas mCanvas) {

        for (int i = 0; i < gameQuestList.gameMain.userEnty.QUESTLIST.size(); i++) {
            QuestEnty enty = gameQuestList.gameMain.userEnty.QUESTLIST.get(i);
            int drawY = enty.btnEnty.drawY;
            CanvasUtil.drawClip(gameQuestList.questcard, mCanvas, enty.btnEnty.clipX, enty.btnEnty.clipY,
                    enty.btnEnty.clipW, enty.btnEnty.clipH, enty.btnEnty.drawX, drawY);

            int type = 0;
            String label = "";
            if (enty.type.equals("delivery")) {
                type = 0;
                label = "배달";
            } else if (enty.type.equals("escort")) {
                type = 1;
                label = "호위";
            } else if (enty.type.equals("sweep")) {
                type = 2;
                label = "소탕";
            } else if (enty.type.equals("chase")) {
                type = 3;
                label = "퇴치";
            } else if (enty.type.equals("hunter")) {
                type = 4;
                label = "사냥";
            } else if (enty.type.equals("patrol")) {
                type = 5;
                label = "순찰";
            }

            //quest img (200x200)
            CanvasUtil.drawClip(gameQuestList.questBitmapList.get(i), mCanvas, 0, 0, 200, 200,
                    475, drawY + 25);

            //label_eng
            CanvasUtil.drawClip(gameQuestList.questcard, mCanvas, 150 + type % 2 * 100, 275 + type / 2 * 25,
                    90, 25, enty.btnEnty.drawX + (enty.btnEnty.clipW - 90) / 2, drawY + 30);

            //label_kor
//            CanvasUtil.drawString(mCanvas, label, 20, Color.argb(255,100,40,40),
//                    Paint.Align.CENTER, enty.btnEnty.drawX + enty.btnEnty.clipW/2,enty.btnEnty.drawY+10);

            //title
            CanvasUtil.drawString(mCanvas, enty.title, 40, Color.argb(210, 10, 10, 25),
                    Paint.Align.LEFT, enty.btnEnty.drawX + 55, drawY + 45);

            //text
            for (int j = 0; j < enty.textArr.size(); j++) {
                if (j < gameQuestList.textLineLimit) {
                    CanvasUtil.drawString(mCanvas, enty.textArr.get(j), 24, Color.argb(210, 10, 10, 25),
                            Paint.Align.LEFT, enty.btnEnty.drawX + 25, drawY + 120 + j * 27);
                } else {
                    break;
                }
            }

            //quest difficult stemp
            for (int j = 0; j < enty.difficult; j++) {
                CanvasUtil.drawClip(gameQuestList.questcard, mCanvas, 100, 250,
                        50, 50, enty.btnEnty.drawX + j * 35, drawY + 5);
            }

            if (enty.actPartyNum > 0)
                CanvasUtil.drawClip(gameQuestList.questcard, mCanvas, 201, 164,
                        51, 49, enty.btnEnty.drawX + 140, drawY + 8);
        }
    }

}
