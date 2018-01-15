package com.epriest.game.guildfantasy.main;

import android.view.MotionEvent;

import com.epriest.game.CanvasGL.util.Game;
import com.epriest.game.CanvasGL.util.GameUtil;
import com.epriest.game.guildfantasy.main.enty.ButtonEnty;
import com.epriest.game.guildfantasy.main.enty.QuestEnty;
import com.epriest.game.guildfantasy.util.INN;

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
        for (int i = 0; i < gameMain.userEnty.QUESTLIST.size(); i++) {
            gameMain.userEnty.QUESTLIST.get(i).btnEnty.clipW = 200;
            gameMain.userEnty.QUESTLIST.get(i).btnEnty.clipH = 270;
            gameMain.userEnty.QUESTLIST.get(i).btnEnty.clipX = 0;
            gameMain.userEnty.QUESTLIST.get(i).btnEnty.clipY = 0;
            gameMain.userEnty.QUESTLIST.get(i).btnEnty.drawX = 35 + (i*(gameMain.userEnty.QUESTLIST.get(i).btnEnty.clipW+20));
            gameMain.userEnty.QUESTLIST.get(i).btnEnty.drawY = 115;

            /*for (PartyEnty partyEnty : gameMain.userEnty.PARTYLIST) {
                if (partyEnty.questId.equals(gameMain.userEnty.QUESTLIST.get(i).id)) {
                    gameMain.userEnty.QUESTLIST.get(i).actPartyNum = partyEnty.num;
                }
            }*/
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
        if (gameMain.onStatusTouch())
            return;

        for (QuestEnty enty : gameMain.userEnty.QUESTLIST) {
            if (GameUtil.equalsTouch(gameMain.appClass.touch, enty.btnEnty.drawX, enty.btnEnty.drawY, enty.btnEnty.clipW, enty.btnEnty.clipH)) {
                enty.btnEnty.clickState = ButtonEnty.ButtonClickOn;
                if (gameMain.appClass.touch.action == MotionEvent.ACTION_UP) {
                    enty.btnEnty.clickState = ButtonEnty.ButtonClickOff;
//                    selectQuestId = Integer.parseInt(enty.id);
//                    gameMain.selectQuestEnty = enty;
                    if(enty.actPartyNum > 0){
                        gameMain.mainButtonAct(INN.GAME_MEMBER, INN.MODE_DEFAULT);
                    }else {
                        gameMain.mainButtonAct(INN.GAME_MEMBER, INN.MODE_PARTY_SELECT);

                    }
                }
                return;
            } else {
                enty.btnEnty.clickState = ButtonEnty.ButtonClickOff;
            }
        }

    }
}
