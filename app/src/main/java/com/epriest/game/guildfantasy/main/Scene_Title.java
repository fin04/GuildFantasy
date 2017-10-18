package com.epriest.game.guildfantasy.main;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.epriest.game.CanvasGL.graphics.CanvasUtil;
import com.epriest.game.CanvasGL.graphics.GLUtil;
import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.CanvasGL.util.Scene;
import com.epriest.game.guildfantasy.main.enty.ButtonEnty;
import com.epriest.game.guildfantasy.main.enty.ImageEnty;

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
        CanvasUtil.recycleBitmap(game_title.intro_bg);
        CanvasUtil.recycleBitmap(game_title.titleImg0.bitmap);
        CanvasUtil.recycleBitmap(game_title.titleImg1.bitmap);
        CanvasUtil.recycleBitmap(game_title.btn_New.bitmap);
        CanvasUtil.recycleBitmap(game_title.btn_New.bitmap_clk);
        CanvasUtil.recycleBitmap(game_title.btn_Load.bitmap);
        CanvasUtil.recycleBitmap(game_title.btn_Load.bitmap_clk);
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
        CanvasUtil.drawBgBitmap(game_title.intro_bg, mCanvas);

        drawTitle(mCanvas);
        drawButton(mCanvas);

        if(cnt < 30){
            CanvasUtil.drawString(mCanvas, str, 30, Color.GREEN,
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
        CanvasUtil.drawBitmap(game_title.titleImg0.bitmap, mCanvas,
                game_title.titleImg0.x, game_title.titleImg0.y);
        CanvasUtil.drawBitmap(game_title.titleImg1.bitmap, mCanvas,
                game_title.titleImg1.x, game_title.titleImg1.y);
    }

    private void drawButton(Canvas mCanvas){
        if(game_title.btn_New.clickState == ButtonEnty.ButtonClickOff)
            CanvasUtil.drawBitmap(game_title.btn_New.bitmap, mCanvas,
                    game_title.btn_New.drawX, game_title.btn_New.drawY);
        else if(game_title.btn_New.clickState == ButtonEnty.ButtonClickOn)
            CanvasUtil.drawBitmap(game_title.btn_New.bitmap_clk, mCanvas,
                    game_title.btn_New.drawX, game_title.btn_New.drawY);

        if(game_title.btn_Load.clickState == ButtonEnty.ButtonClickOff)
            CanvasUtil.drawBitmap(game_title.btn_Load.bitmap, mCanvas,
                    game_title.btn_Load.drawX, game_title.btn_Load.drawY);
        else if(game_title.btn_Load.clickState == ButtonEnty.ButtonClickOn)
            CanvasUtil.drawBitmap(game_title.btn_Load.bitmap_clk, mCanvas,
                    game_title.btn_Load.drawX, game_title.btn_Load.drawY);
    }
}
