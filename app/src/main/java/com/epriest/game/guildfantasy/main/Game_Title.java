package com.epriest.game.guildfantasy.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.MotionEvent;

import com.epriest.game.CanvasGL.graphics.GLUtil;
import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.CanvasGL.util.Game;
import com.epriest.game.CanvasGL.util.GameUtil;
import com.epriest.game.guildfantasy.main.enty.ButtonEnty;
import com.epriest.game.guildfantasy.main.enty.ImageEnty;
import com.epriest.game.guildfantasy.main.play.DataManager;
import com.epriest.game.guildfantasy.util.DialogActivity;
import com.epriest.game.guildfantasy.util.INN;

/**
 * Created by User on 2017-10-18.
 */

public class Game_Title extends Game {

    public static final int STARTGAME_NEWPLAYER = 1;
    public static final int STARTGAME_LOADPLAYER = 2;

    public Game_Main gameMain;
    public ApplicationClass appClass;

    public Bitmap intro_bg;

    public ImageEnty titleImg0;
    public ImageEnty titleImg1;
    public ButtonEnty btn_New;
    public ButtonEnty btn_Load;


//    public int flag;

    public Game_Title(Game_Main gameMain) {
        this.gameMain = gameMain;
        this.appClass = gameMain.appClass;
    }

    @Override
    public void gStart() {
        intro_bg = GLUtil.loadAssetsBitmap(appClass, "title.jpg", null);

        titleImg0 = new ImageEnty();
        titleImg1 = new ImageEnty();
        titleImg0.bitmap = GLUtil.loadAssetsBitmap(appClass, "title_1.png", null);
        titleImg1.bitmap = GLUtil.loadAssetsBitmap(appClass, "title_2.png", null);

        int titleY = 250;
        titleImg0.w = titleImg0.bitmap.getWidth();
        titleImg0.h = titleImg0.bitmap.getHeight();
        titleImg0.x = appClass.getGameCanvasWidth()/2 - titleImg0.w/2;
        titleImg0.y = titleY;

        titleImg1.w = titleImg1.bitmap.getWidth();
        titleImg1.h = titleImg1.bitmap.getHeight();
        titleImg1.x = appClass.getGameCanvasWidth()/2 - titleImg1.w/2;
        titleImg1.y = titleY+titleImg0.h+15;

        btn_New = new ButtonEnty();
        btn_Load = new ButtonEnty();
        btn_New.bitmap = GLUtil.loadAssetsBitmap(appClass, "title_new0.png", null);
        btn_New.bitmap_clk = GLUtil.loadAssetsBitmap(appClass, "title_new1.png", null);
        btn_Load.bitmap = GLUtil.loadAssetsBitmap(appClass, "title_load0.png", null);
        btn_Load.bitmap_clk = GLUtil.loadAssetsBitmap(appClass, "title_load1.png", null);

        int btnY = appClass.getGameCanvasHeight()/2 +100;
        btn_New.drawW = btn_New.bitmap.getWidth();
        btn_New.drawH = btn_New.bitmap.getHeight();
        btn_New.drawX = appClass.getGameCanvasWidth()/2 - btn_New.bitmap.getWidth()/2;
        btn_New.drawY = btnY;

        btn_Load.drawW = btn_Load.bitmap.getWidth();
        btn_Load.drawH = btn_Load.bitmap.getHeight();
        btn_Load.drawX = appClass.getGameCanvasWidth()/2 - btn_Load.bitmap.getWidth()/2;
        btn_Load.drawY = btnY+btn_New.bitmap.getHeight()+50;
    }

    @Override
    public void gStop() {

    }

    @Override
    public void gUpdate() {
        if (appClass.newName != null) {
            startGame(STARTGAME_NEWPLAYER);
        }
    }

    public void startGame(int flag){
        if (gameMain.userEnty == null) {
            // 프롤로그를 실행하고 플레이어를 작성
//            startProlog1();
            String name = appClass.newName;
            appClass.newName = null;
            gameMain.userEnty = DataManager.createUserData(gameMain.dbAdapter, name, flag);
        }
        gameMain.mainButtonAct(INN.GAME_HOME, 0, -1);
    }

    @Override
    public void gOnTouchEvent(MotionEvent event) {

        if (GameUtil.equalsTouch(appClass.touch, btn_New.drawX, btn_New.drawY, btn_New.drawW, btn_New.drawH)) {
            btn_New.clickState = ButtonEnty.ButtonClickOn;
            if (gameMain.appClass.touch.action == MotionEvent.ACTION_UP) {
                btn_New.clickState = ButtonEnty.ButtonClickOff;
                Intent intent = new Intent(appClass, DialogActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                appClass.startActivity(intent);

//                startGame(STARTGAME_NEWPLAYER);
            }
            return;
        }

        if (GameUtil.equalsTouch(appClass.touch, btn_Load.drawX, btn_Load.drawY, btn_Load.drawW, btn_Load.drawH)) {
            btn_Load.clickState = ButtonEnty.ButtonClickOn;
            if (gameMain.appClass.touch.action == MotionEvent.ACTION_UP) {
                btn_Load.clickState = ButtonEnty.ButtonClickOff;
                startGame(STARTGAME_LOADPLAYER);
            }
            return;
        }

    }
}
