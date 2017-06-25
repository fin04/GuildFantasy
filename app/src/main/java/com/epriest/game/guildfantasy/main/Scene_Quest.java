package com.epriest.game.guildfantasy.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.epriest.game.CanvasGL.graphics.CanvasUtil;
import com.epriest.game.CanvasGL.graphics.GLUtil;
import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.CanvasGL.util.Scene;
import com.epriest.game.guildfantasy.main.enty.ImageEnty;
import com.epriest.game.guildfantasy.main.enty.QuestEnty;

import java.util.ArrayList;

/**
 * Created by darka on 2017-03-26.
 */

public class Scene_Quest extends Scene {

    private Context context;
    private Game_Quest gameQuest;
//    private Scene_Main sceneMain;
    private int canvasW, canvasH;

    private Bitmap bg;
    private Bitmap questcard;
    private ArrayList<Bitmap> questBitmap = new ArrayList<>();

    private ImageEnty managerImg;

    public Scene_Quest(Game_Quest gameQuest, Scene_Main sceneMain) {
        this.gameQuest = gameQuest;
//        this.sceneMain = sceneMain;
    }

    @Override
    public void initScene(ApplicationClass appClass) {
        this.context = appClass.getBaseContext();
        canvasW = appClass.getGameCanvasWidth();
        canvasH = appClass.getGameCanvasHeight();

        bg = GLUtil.loadAssetsBitmap(context, "main/quest.jpg", null);
        questcard = GLUtil.loadAssetsBitmap(context, "main/questcard.png", null);

        for (QuestEnty enty : gameQuest.gameMain.playerEnty.QUESTLIST) {
            questBitmap.add(GLUtil.loadAssetsBitmap(context, "quest/" + enty.image, null));
        }
    }

    @Override
    public void recycleScene() {
        CanvasUtil.recycleBitmap(bg);
        CanvasUtil.recycleBitmap(questcard);
        for (Bitmap bm : questBitmap) {
            CanvasUtil.recycleBitmap(bm);
        }
    }

    @Override
    public void drawLoading(Canvas mCanvas, Bitmap loading) {

    }

    @Override
    public void draw(Canvas mCanvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
//        mCanvas.drawColor(Color.BLACK);

//        mCanvas.drawBitmap(bg, 0, 0, null);
        CanvasUtil.drawBgBitmap(bg, mCanvas);
        drawQuestList(mCanvas);

        //manager mode
//        drawManager(mCanvas, paint);

        gameQuest.gameMain.drawMain(mCanvas, false);
    }

    private void drawQuestList(Canvas mCanvas) {

        for(int i=0; i<gameQuest.gameMain.playerEnty.QUESTLIST.size(); i++){
            QuestEnty enty = gameQuest.gameMain.playerEnty.QUESTLIST.get(i);
            CanvasUtil.drawClip(questcard, mCanvas, enty.btnEnty.clipX, enty.btnEnty.clipY,
                    enty.btnEnty.clipW, enty.btnEnty.clipH, enty.btnEnty.drawX, enty.btnEnty.drawY);

            int type = 0;
            String label = "";
            if(enty.type.equals("delivery")) {
                type = 0;
                label = "배달";
            }else if(enty.type.equals("escort")) {
                type = 1;
                label = "호위";
            }else if(enty.type.equals("sweep")) {
                type = 2;
                label = "소탕";
            }else if(enty.type.equals("chase")) {
                type = 3;
                label = "퇴치";
            }else if(enty.type.equals("hunter")) {
                type = 4;
                label = "사냥";
            }else if(enty.type.equals("patrol")) {
                type = 5;
                label = "순찰";
            }
            CanvasUtil.drawClip(questcard, mCanvas, type%2*89, 275+type/2*25,
                    90, 25, enty.btnEnty.drawX + (enty.btnEnty.clipW-90)/2, enty.btnEnty.drawY+30);
            CanvasUtil.drawString(mCanvas, label, 20, Color.argb(255,100,40,40), Paint.Align.CENTER,
                    enty.btnEnty.drawX + enty.btnEnty.clipW/2,enty.btnEnty.drawY+10);

            CanvasUtil.drawBitmap(questBitmap.get(i), mCanvas,
                    enty.btnEnty.drawX + (enty.btnEnty.clipW-questBitmap.get(i).getWidth())/2, enty.btnEnty.drawY+60);


            for(int j=0; j<enty.difficult; j++) {
                CanvasUtil.drawClip(questcard, mCanvas, 201, 118,
                        37, 43, enty.btnEnty.drawX + j%5*34 + 10, enty.btnEnty.drawY + j/5*40 + 180);
            }

            if(enty.actPartyNum > 0)
                CanvasUtil.drawClip(questcard, mCanvas, 201, 164,
                    51, 49, enty.btnEnty.drawX+140, enty.btnEnty.drawY+8);
        }
    }
}
