package com.epriest.game.guildfantasy.main;

import android.graphics.Bitmap;
import android.view.MotionEvent;
import android.widget.Toast;

import com.epriest.game.CanvasGL.graphics.GLUtil;
import com.epriest.game.CanvasGL.util.ButtonSprite;
import com.epriest.game.CanvasGL.util.Game;
import com.epriest.game.CanvasGL.util.GameUtil;
import com.epriest.game.CanvasGL.util.TouchData;
import com.epriest.game.guildfantasy.main.enty.ButtonEnty;

/**
 * Created by darka on 2017-11-06.
 */

public class Game_Recruit extends Game {

    public Bitmap img_recruitBtn;
    public Bitmap bg;

    public Game_Main gameMain;

    public ButtonEnty summonBtn;
    public ButtonEnty bondageBtn;
    public ButtonEnty covenantBtn;


    public Game_Recruit(Game_Main gameMain) {
        this.gameMain = gameMain;
    }

    @Override
    public void gStart() {
        bg = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/member.jpg", null);
        img_recruitBtn = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/recruit_btn.png", null);
        setButton();
    }

    private void setButton() {
        int btnW = 250;
        int btnH = 40;
        int canvasW = gameMain.canvasW;
        int canvasH = gameMain.canvasH;
        int btnX = canvasW / 2 - btnW / 2;

        summonBtn = new ButtonEnty();
        summonBtn.drawX = btnX;
        summonBtn.drawY = 400;
        summonBtn.clipW = btnW;
        summonBtn.clipH = btnH;
        summonBtn.name = "summon";

        bondageBtn = new ButtonEnty();
        bondageBtn.drawX = btnX;
        bondageBtn.drawY = 500;
        bondageBtn.clipW = btnW;
        bondageBtn.clipH = btnH;
        bondageBtn.name = "bondage";

        covenantBtn = new ButtonEnty();
        covenantBtn.drawX = btnX;
        covenantBtn.drawY = 600;
        covenantBtn.clipW = btnW;
        covenantBtn.clipH = btnH;
        covenantBtn.name = "covenant";
    }

    @Override
    public void gStop() {

    }

    @Override
    public void gUpdate() {

    }

    @Override
    public void gOnTouchEvent(MotionEvent event) {
        gameMain.onTouchMenuIcon();
        TouchData.Touch touch = gameMain.appClass.touch;
        if (GameUtil.equalsTouch(touch,
                summonBtn.drawX, summonBtn.drawY, summonBtn.clipW, summonBtn.clipH)) {
            if (touch.action == MotionEvent.ACTION_UP) {
                summonBtn.clickState = ButtonEnty.ButtonClickOff;

                return;
            } else {
                summonBtn.clickState = ButtonEnty.ButtonClickOn;
                return;
            }
        } else {
            summonBtn.clickState = ButtonEnty.ButtonClickOff;
        }

        if (GameUtil.equalsTouch(touch,
                bondageBtn.drawX, bondageBtn.drawY, bondageBtn.clipW, bondageBtn.clipH)) {
            if (touch.action == MotionEvent.ACTION_UP) {
                bondageBtn.clickState = ButtonEnty.ButtonClickOff;
                return;
            } else {
                bondageBtn.clickState = ButtonEnty.ButtonClickOn;
                return;
            }
        } else {
            bondageBtn.clickState = ButtonEnty.ButtonClickOff;
        }

        if (GameUtil.equalsTouch(touch,
                covenantBtn.drawX, covenantBtn.drawY, covenantBtn.clipW, covenantBtn.clipH)) {
            if (touch.action == MotionEvent.ACTION_UP) {
                covenantBtn.clickState = ButtonEnty.ButtonClickOff;
                if (gameMain.userEnty.GOLD < 10) {
                    Toast.makeText(gameMain.appClass, "Gold가 없습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    gameMain.userEnty.GOLD -= 10;
//                   새로운 캐릭터를 띄운다
                }

                return;
            } else {
                covenantBtn.clickState = ButtonEnty.ButtonClickOn;
                return;
            }
        } else {
            covenantBtn.clickState = ButtonEnty.ButtonClickOff;
        }
    }
}


