package com.epriest.game.guildfantasy.main;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.CanvasGL.util.Scene;
import com.epriest.game.guildfantasy.main.enty.ButtonEnty;
import com.epriest.game.guildfantasy.util.DrawUtil;

/**
 * Created by darka on 2016-11-03.
 */

public class Scene_Title extends Scene {

    private Game_Title game_title;

    public Scene_Title(Game_Title game_title) {
        this.game_title = game_title;
    }

    @Override
    public void initScene(ApplicationClass appClass) {
    }

    @Override
    public void recycleScene() {
        DrawUtil.recycleBitmap(game_title.intro_bg);
        DrawUtil.recycleBitmap(game_title.titleImg0.bitmap);
        DrawUtil.recycleBitmap(game_title.titleImg1.bitmap);
        DrawUtil.recycleBitmap(game_title.btn_New.bitmap);
        DrawUtil.recycleBitmap(game_title.btn_New.bitmap_clk);
        DrawUtil.recycleBitmap(game_title.btn_Load.bitmap);
        DrawUtil.recycleBitmap(game_title.btn_Load.bitmap_clk);
        DrawUtil.recycleBitmap(game_title.btnName);
    }

    @Override
    public void drawLoading(Canvas mCanvas, Bitmap loading) {

    }

    String str = "터치하고 게임 시작하기";
    int cnt = 0;
    @Override
    public void draw(Canvas mCanvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        mCanvas.drawColor(Color.MAGENTA);
        DrawUtil.drawBgBitmap(game_title.intro_bg, mCanvas);

        drawTitle(mCanvas);
        drawButton(mCanvas);

        if(cnt < 30){
            DrawUtil.drawString(mCanvas, str, 30, Color.GREEN,
                    Paint.Align.CENTER, game_title.appClass.getGameCanvasWidth()/2,
                    game_title.appClass.getGameCanvasHeight()-100);
        }else{
            if(cnt == 50){
                cnt = 0;
            }
        }
        cnt++;
    }

    private void drawTitle(Canvas mCanvas){
        DrawUtil.drawBitmap(game_title.titleImg0.bitmap, mCanvas,
                game_title.titleImg0.x, game_title.titleImg0.y);
        DrawUtil.drawBitmap(game_title.titleImg1.bitmap, mCanvas,
                game_title.titleImg1.x, game_title.titleImg1.y);
    }

    private void drawButton(Canvas mCanvas){
        if(game_title.btn_New.clickState == ButtonEnty.ButtonClickOff)
            DrawUtil.drawBitmap(game_title.btn_New.bitmap, mCanvas,
                    game_title.btn_New.drawX, game_title.btn_New.drawY);
        else if(game_title.btn_New.clickState == ButtonEnty.ButtonClickOn)
            DrawUtil.drawBitmap(game_title.btn_New.bitmap_clk, mCanvas,
                    game_title.btn_New.drawX, game_title.btn_New.drawY);

        if(game_title.btn_Load.clickState == ButtonEnty.ButtonClickOff)
            DrawUtil.drawBitmap(game_title.btn_Load.bitmap, mCanvas,
                    game_title.btn_Load.drawX, game_title.btn_Load.drawY);
        else if(game_title.btn_Load.clickState == ButtonEnty.ButtonClickOn)
            DrawUtil.drawBitmap(game_title.btn_Load.bitmap_clk, mCanvas,
                    game_title.btn_Load.drawX, game_title.btn_Load.drawY);

        for(ButtonEnty enty : game_title.btn_NameList){
            if(enty.clickState == ButtonEnty.ButtonClickOff)
                DrawUtil.drawClip(game_title.btnName, mCanvas, enty.clipX, enty.clipY,
                        enty.clipW, enty.clipH, enty.drawX, enty.drawY);
            else if(enty.clickState == ButtonEnty.ButtonClickOn)
                DrawUtil.drawClip(game_title.btnName, mCanvas, enty.clipX, enty.clipY+enty.clipH,
                        enty.clipW, enty.clipH, enty.drawX, enty.drawY);

            DrawUtil.drawString(mCanvas, enty.name, 45,
                    Color.argb(255,180,180,220), Paint.Align.LEFT,
                    enty.drawX+10, enty.drawY+5);
        }
    }
}
