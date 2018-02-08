package com.epriest.game.guildfantasy.main;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.epriest.game.CanvasGL.graphics.GLUtil;
import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.CanvasGL.util.Scene;
import com.epriest.game.CanvasGL.util.TextUtil;
import com.epriest.game.guildfantasy.util.DrawUtil;

import java.util.ArrayList;

/**
 * Created by darka on 2017-05-05.
 */

public class Scene_Event extends Scene {

    private Game_Event gameEvent;
    private Scene_Main sceneMain;
    public ArrayList<String> textList = new ArrayList<>();

    private Bitmap eventBitmap;

    public Scene_Event(Game_Event gameEvent, Scene_Main sceneMain) {
        this.gameEvent = gameEvent;
        this.sceneMain = sceneMain;
    }

    @Override
    public void initScene(ApplicationClass appClass) {
//        loadEventImage(gameEvent.currentViewNum);
    }

    private void loadEventImage(int num) {
        int size = gameEvent.gameMain.userEnty.eventEnty.ImageList.size();
        if(num < size) {
            String imagePath = gameEvent.gameMain.userEnty.eventEnty.ImageList.get(num);
            DrawUtil.recycleBitmap(eventBitmap);
            eventBitmap = GLUtil.loadAssetsBitmap(gameEvent.gameMain.appClass.getBaseContext(), "" +
                    "event/" + imagePath, null);
        }
        size = gameEvent.gameMain.userEnty.eventEnty.TextList.size();
        if(num < size){
            textList = TextUtil.setMultiLineText(gameEvent.gameMain.userEnty.eventEnty.TextList.get(num), 25, 600);
        }
        gameEvent.gameMain.userEnty.eventEnty.changeView = false;
    }

    @Override
    public void recycleScene() {
        DrawUtil.recycleBitmap(eventBitmap);
    }

    @Override
    public void drawLoading(Canvas mCanvas, Bitmap loading) {

    }

    @Override
    public void draw(Canvas mCanvas) {
        if(gameEvent.gameMain.userEnty.eventEnty.changeView)
            loadEventImage(gameEvent.gameMain.userEnty.eventEnty.currentViewNum);
        DrawUtil.drawBgBitmap(eventBitmap, mCanvas);

        DrawUtil.drawBox(mCanvas, Color.argb(150, 50, 50,50), true, 50, 50,
                gameEvent.gameMain.canvasW-100, gameEvent.gameMain.canvasH-100);

        int textsize = 25;
        int textHeight = textList.size()*25;
        int paperX = 60;
        int paperY = (gameEvent.gameMain.canvasH - textHeight)/2;
        Paint paint = new Paint();
        paint.setTextSize(25);
        paint.setAntiAlias(true);
        paint.setColor(Color.argb(255,220,220,220));
        paint.setTextAlign(Paint.Align.LEFT);
        for(int i =0; i< textList.size(); i++){
            DrawUtil.drawString(mCanvas, textList.get(i), paint,
                    paperX+15, (int) (paperY + i*(paint.getTextSize()+10)));
        }
    }
}
