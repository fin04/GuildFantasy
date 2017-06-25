package com.epriest.game.guildfantasy.main;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.epriest.game.CanvasGL.graphics.CanvasUtil;
import com.epriest.game.CanvasGL.graphics.GLUtil;
import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.CanvasGL.util.Scene;
import com.epriest.game.CanvasGL.util.TextUtil;

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
        int size = gameEvent.gameMain.playerEnty.eventEnty.ImageList.size();
        if(size >= num) {
            String imagePath = gameEvent.gameMain.playerEnty.eventEnty.ImageList.get(num);
            CanvasUtil.recycleBitmap(eventBitmap);
            eventBitmap = GLUtil.loadAssetsBitmap(gameEvent.gameMain.appClass.getBaseContext(), "" +
                    "event/" + imagePath, null);
        }
        size = gameEvent.gameMain.playerEnty.eventEnty.TextList.size();
        if(size >= num){
            textList = TextUtil.setMultiLineText(gameEvent.gameMain.playerEnty.eventEnty.TextList.get(num), 25, 600);
        }
        gameEvent.gameMain.playerEnty.eventEnty.changeView = false;
    }

    @Override
    public void recycleScene() {
        CanvasUtil.recycleBitmap(eventBitmap);
    }

    @Override
    public void drawLoading(Canvas mCanvas, Bitmap loading) {

    }

    @Override
    public void draw(Canvas mCanvas) {
        if(gameEvent.gameMain.playerEnty.eventEnty.changeView)
            loadEventImage(gameEvent.gameMain.playerEnty.eventEnty.currentViewNum);
        CanvasUtil.drawBgBitmap(eventBitmap, mCanvas);
        int paperX = 30;
        int paperY = 50;
        Paint paint = new Paint();
        paint.setTextSize(25);
        paint.setAntiAlias(true);
        paint.setColor(Color.argb(255,90,90,90));
        paint.setTextAlign(Paint.Align.LEFT);
        for(int i =0; i< textList.size(); i++){
            CanvasUtil.drawString(mCanvas, textList.get(i), paint,
                    paperX+15, (int) (paperY + i*(paint.getTextSize()+10)));
        }
    }
}
