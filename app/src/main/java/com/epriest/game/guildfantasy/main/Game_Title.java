package com.epriest.game.guildfantasy.main;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.epriest.game.CanvasGL.graphics.GLUtil;
import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.CanvasGL.util.Game;
import com.epriest.game.CanvasGL.util.GameUtil;
import com.epriest.game.guildfantasy.main.enty.ButtonEnty;
import com.epriest.game.guildfantasy.main.enty.ImageEnty;
import com.epriest.game.guildfantasy.main.enty.MemberEnty;
import com.epriest.game.guildfantasy.main.play.DataManager;
import com.epriest.game.guildfantasy.main.play.GameDbAdapter;
import com.epriest.game.guildfantasy.util.DialogActivity;
import com.epriest.game.guildfantasy.util.INN;

import java.util.ArrayList;

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

    public Bitmap btn_Name;
    public Bitmap btn_Name_clk;
    public ArrayList<ButtonEnty> btn_NameList;

    public ArrayList<String> UserList;


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
        titleImg0.x = gameMain.canvasW / 2 - titleImg0.w / 2;
        titleImg0.y = titleY;

        titleImg1.w = titleImg1.bitmap.getWidth();
        titleImg1.h = titleImg1.bitmap.getHeight();
        titleImg1.x = gameMain.canvasW / 2 - titleImg1.w / 2;
        titleImg1.y = titleY + titleImg0.h + 15;

        btn_New = new ButtonEnty();
        btn_Load = new ButtonEnty();

        btn_New.bitmap = GLUtil.loadAssetsBitmap(appClass, "title_new0.png", null);
        btn_New.bitmap_clk = GLUtil.loadAssetsBitmap(appClass, "title_new1.png", null);
        btn_Load.bitmap = GLUtil.loadAssetsBitmap(appClass, "title_load0.png", null);
        btn_Load.bitmap_clk = GLUtil.loadAssetsBitmap(appClass, "title_load1.png", null);
        btn_Name = GLUtil.loadAssetsBitmap(appClass, "main/btnname_0.png", null);
        btn_Name_clk = GLUtil.loadAssetsBitmap(appClass, "main/btnname_1.png", null);

        int btnY = gameMain.canvasH / 2 + 100;
        btn_New.drawW = btn_New.bitmap.getWidth();
        btn_New.drawH = btn_New.bitmap.getHeight();
        btn_New.drawX = gameMain.canvasW / 2 - btn_New.bitmap.getWidth() / 2;
        btn_New.drawY = btnY;

        btn_Load.drawW = btn_Load.bitmap.getWidth();
        btn_Load.drawH = btn_Load.bitmap.getHeight();
        btn_Load.drawX = gameMain.canvasW / 2 - btn_Load.bitmap.getWidth() / 2;
        btn_Load.drawY = btnY + btn_New.bitmap.getHeight() + 50;

        UserList = new ArrayList<>();
        btn_NameList = new ArrayList<>();
        Cursor c = DataManager.getUserMainCursor(gameMain.dbAdapter, null);
        if (c == null || c.getCount() == 0) {

        }else{
            int num = 0;
            c.moveToFirst();
            do {
                UserList.add(c.getString(c.getColumnIndex(GameDbAdapter.KEY_USERNAME)));
                ButtonEnty enty = new ButtonEnty();
                enty.num = num;
                enty.name = UserList.get(num);
                enty.drawW = btn_Name.getWidth();
                enty.drawH = btn_Name.getHeight();
                enty.drawX = gameMain.canvasW / 2 - enty.drawW/2;
                enty.drawY = btn_Load.drawY + btn_Load.drawH + 50 + enty.num*(enty.drawH+10);
                btn_NameList.add(enty);
                num++;
            } while (c.moveToNext());
        }
    }

    @Override
    public void gStop() {

    }

    @Override
    public void gUpdate() {
        if (appClass.newName != null) {
            String name = appClass.newName;
            appClass.newName = null;
            startGame(STARTGAME_NEWPLAYER, name);
        }
    }

    public void startGame(int flag, String name) {
        if (gameMain.userEnty == null) {
            // 프롤로그를 실행하고 플레이어를 작성
//            startProlog1();
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
                Cursor userCursor = DataManager.getUserMainCursor(gameMain.dbAdapter, null);
                if (userCursor.getCount() < INN.CREATE_PLAYER_LIMITED) {
                    Intent intent = new Intent(appClass, DialogActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    appClass.startActivity(intent);
                } else {
                    Toast.makeText(appClass, "생성 가능한 유저 수가 넘었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
            return;
        }

        if (GameUtil.equalsTouch(appClass.touch, btn_Load.drawX, btn_Load.drawY, btn_Load.drawW, btn_Load.drawH)) {
            btn_Load.clickState = ButtonEnty.ButtonClickOn;
            if (gameMain.appClass.touch.action == MotionEvent.ACTION_UP) {
                btn_Load.clickState = ButtonEnty.ButtonClickOff;
                Cursor userCursor = DataManager.getUserMainCursor(gameMain.dbAdapter, null);
                if (userCursor.getCount() == 0) {
                    Toast.makeText(appClass, "불러올 유저가 없습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    startGame(STARTGAME_LOADPLAYER, "aaa");
                }
            }
            return;
        }

        for(ButtonEnty enty : btn_NameList){
            if (GameUtil.equalsTouch(appClass.touch, enty.drawX, enty.drawY, enty.drawW, enty.drawH)){
                enty.clickState = ButtonEnty.ButtonClickOn;
                if (gameMain.appClass.touch.action == MotionEvent.ACTION_UP) {
                    enty.clickState = ButtonEnty.ButtonClickOff;
                        startGame(STARTGAME_LOADPLAYER, enty.name);
                }
                return;
            }
        }


    }
}
