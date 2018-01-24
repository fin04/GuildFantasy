package com.epriest.game.guildfantasy.main;

import android.graphics.Bitmap;
import android.view.MotionEvent;

import com.epriest.game.CanvasGL.graphics.GLUtil;
import com.epriest.game.CanvasGL.util.Game;
import com.epriest.game.guildfantasy.main.enty.QuestEnty;
import com.epriest.game.guildfantasy.main.play.DataManager;

import java.util.ArrayList;

/**
 * Created by darka on 2018-01-23.
 */

public class Game_Quest extends Game {

    public Game_Main gameMain;

    public int canvasW, canvasH;

    public final int textLimit = 25;
    public final int textLineLimit = 5;

    public Bitmap bg;
    public Bitmap paper;
    public Bitmap questBitmap;

    public QuestEnty questEnty;

    public Game_Quest(Game_Main gameMain) {
        this.gameMain = gameMain;
    }

    @Override
    public void gStart() {
        this.canvasW = gameMain.appClass.getGameCanvasWidth();
        this.canvasH = gameMain.appClass.getGameCanvasHeight();

        questEnty = DataManager.getUserQuestEnty(gameMain.dbAdapter, gameMain.userEnty.Name, gameMain.selectQuestId);

        bg = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/quest.jpg", null);
        paper = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/guildpaper.png", null);
        questBitmap = GLUtil.loadAssetsBitmap(gameMain.appClass, "quest/" + questEnty.image, null);

        if (questEnty.textArr.isEmpty()) {
            for (int j = 0; j < questEnty.text.length() / textLimit + 1; j++) {
                if ((j + 1) * textLimit < questEnty.text.length())
                    questEnty.textArr.add(questEnty.text.substring(j * textLimit, (j + 1) * textLimit));
                else
                    questEnty.textArr.add(questEnty.text.substring(j * textLimit, questEnty.text.length()));
            }
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
    }
}
