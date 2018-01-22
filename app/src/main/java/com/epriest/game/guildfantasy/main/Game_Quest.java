package com.epriest.game.guildfantasy.main;

import android.graphics.Bitmap;
import android.view.MotionEvent;

import com.epriest.game.CanvasGL.graphics.GLUtil;
import com.epriest.game.CanvasGL.util.Game;
import com.epriest.game.CanvasGL.util.GameUtil;
import com.epriest.game.guildfantasy.main.enty.ButtonEnty;
import com.epriest.game.guildfantasy.main.enty.QuestEnty;
import com.epriest.game.guildfantasy.util.INN;

import java.util.ArrayList;

/**
 * Created by darka on 2017-03-26.
 */

public class Game_Quest extends Game {

    public Game_Main gameMain;

    public boolean selectQuestInit;
//    public int selectQuestId = 0;

    public int canvasW, canvasH;

    public Bitmap bg;
    public Bitmap questcard;
    public ArrayList<Bitmap> questBitmap = new ArrayList<>();

    public Game_Quest(Game_Main gameMain) {
        this.gameMain = gameMain;
    }

    @Override
    public void gStart() {
        this.canvasW = gameMain.appClass.getGameCanvasWidth();
        this.canvasH = gameMain.appClass.getGameCanvasHeight();

        bg = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/quest.jpg", null);
        questcard = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/questcard.png", null);

//            for (QuestEnty enty : questList) {
//                questBitmap.add(GLUtil.loadAssetsBitmap(gameMain.appClass, "quest/" + enty.image, null));
//            }

        for (int i = 0; i < gameMain.userEnty.QUESTLIST.size(); i++) {
            QuestEnty enty = gameMain.userEnty.QUESTLIST.get(i);
            questBitmap.add(GLUtil.loadAssetsBitmap(gameMain.appClass,
                    "quest/" + enty.image, null, 2));

            if(enty.textArr.isEmpty()) {
                int textLimit = 22;
                for (int j = 0; j < enty.text.length() / textLimit + 1; j++) {
                    if ((j + 1) * textLimit < enty.text.length())
                        enty.textArr.add(enty.text.substring(j * textLimit, (j + 1) * textLimit));
                    else
                        enty.textArr.add(enty.text.substring(j * textLimit, enty.text.length()));
                }
            }
            enty.btnEnty.clipW = 660;
            enty.btnEnty.clipH = 250;
            enty.btnEnty.clipX = 0;
            enty.btnEnty.clipY = 0;
            enty.btnEnty.drawX = 30;
            enty.btnEnty.drawY = gameMain.statusBarH + 15 + (i * (enty.btnEnty.clipH + 15));

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
                    if (enty.actPartyNum > 0) {
//                        gameMain.mainButtonAct(INN.GAME_MEMBER, INN.MODE_DEFAULT);
                    } else {
                        gameMain.mainButtonAct(INN.GAME_PARTY_FROM_QUEST, 0);
                    }
                }
                return;
            } else {
                enty.btnEnty.clickState = ButtonEnty.ButtonClickOff;
            }
        }

    }
}
