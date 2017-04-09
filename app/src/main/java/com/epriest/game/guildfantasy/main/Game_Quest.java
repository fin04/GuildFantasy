package com.epriest.game.guildfantasy.main;

import android.view.MotionEvent;

import com.epriest.game.CanvasGL.util.Game;
import com.epriest.game.CanvasGL.util.GameUtil;
import com.epriest.game.CanvasGL.util.TextUtil;
import com.epriest.game.guildfantasy.TestData;
import com.epriest.game.guildfantasy.enty.ButtonEnty;
import com.epriest.game.guildfantasy.enty.QuestEnty;

import java.util.ArrayList;

/**
 * Created by darka on 2017-03-26.
 */

public class Game_Quest extends Game {

    public Game_Main gameMain;

    public boolean selectQuestInit;
//    public int selectQuestId = 0;

    public Game_Quest(Game_Main gameMain) {
        this.gameMain = gameMain;
    }

    @Override
    public void gStart() {
        gameMain.playerEnty.QUESTLIST = TestData.testQuestList();
        for (int i = 0; i < gameMain.playerEnty.QUESTLIST.size(); i++) {
            gameMain.playerEnty.QUESTLIST.get(i).text
                    = TextUtil.setMultiLineText(gameMain.playerEnty.QUESTLIST.get(i).text, 24, 300);
            gameMain.playerEnty.QUESTLIST.get(i).tip
                    = TextUtil.setMultiLineText(gameMain.playerEnty.QUESTLIST.get(i).tip, 20, 300);
        }
    }


    @Override
    public void gStop() {

    }

    @Override
    public void gUpdate() {

    }

    @Override
    public void gOnTouchEvent(MotionEvent event) {
        if (gameMain.onTouchEvent(event))
            return;

        for (QuestEnty enty : gameMain.playerEnty.QUESTLIST) {
            if (GameUtil.equalsTouch(gameMain.appClass.touch, enty.btnEnty.x, enty.btnEnty.y, enty.btnEnty.w, enty.btnEnty.h)) {
                enty.btnEnty.clickState = ButtonEnty.ButtonClickOn;
                if (gameMain.appClass.touch.Action == MotionEvent.ACTION_UP) {
                    enty.btnEnty.clickState = ButtonEnty.ButtonClickOff;
//                    selectQuestId = Integer.parseInt(enty.id);
                    gameMain.QuestEnty = enty;
                    gameMain.appClass.gameFlag = Game_Main.GAME_PARTY;
                    gameMain.appClass.isGameInit = true;
                    gameMain.appClass.isSceneInit = true;
                }
                return;
            } else {
                if (enty.btnEnty.clickState == ButtonEnty.ButtonClickOn)
                    enty.btnEnty.clickState = ButtonEnty.ButtonClickOff;
            }
        }

    }
}
