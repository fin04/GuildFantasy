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
import com.epriest.game.guildfantasy.main.enty.MemberEnty;
import com.epriest.game.guildfantasy.main.play.DataManager;
import com.epriest.game.guildfantasy.util.INN;

import java.util.ArrayList;

/**
 * Created by darka on 2017-11-06.
 */

public class Game_Recruit extends Game {

    public Bitmap img_value;
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
        img_value = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/banner_value.jpg", null);
        summonBtn = new ButtonEnty();
        summonBtn.bitmap = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/banner_summon.png", null);
        bondageBtn = new ButtonEnty();
        bondageBtn.bitmap = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/banner_bondage.png", null);
        covenantBtn = new ButtonEnty();
        covenantBtn.bitmap = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/banner_covenant.png", null);
        setButton();
    }

    private void setButton() {
        int btnW = summonBtn.bitmap.getWidth();
        int btnH = summonBtn.bitmap.getHeight();
        int canvasW = gameMain.canvasW;
        int canvasH = gameMain.canvasH;
        int btnX = canvasW / 2 - btnW / 2;

        summonBtn.drawX = btnX;
        summonBtn.drawY = 100;
        summonBtn.clipW = btnW;
        summonBtn.clipH = btnH;
        summonBtn.name = "summon";

        bondageBtn.drawX = btnX;
        bondageBtn.drawY = btnH+130;
        bondageBtn.clipW = btnW;
        bondageBtn.clipH = btnH;
        bondageBtn.name = "bondage";

        covenantBtn.drawX = btnX;
        covenantBtn.drawY = btnH*2+160;
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

    private void activeNewCard(String type) {
        if (type.equals("covenant")) {
            if (gameMain.userEnty.GOLD < 10) {
                gameMain.showAlertType = INN.ALERT_TYPE_EMPTYGOLD;
//                Toast.makeText(gameMain.appClass, "Gold가 없습니다.", Toast.LENGTH_SHORT).show();
            } else {
                gameMain.userEnty.GOLD -= 10;
                ArrayList<MemberEnty> entyList = DataManager.getGradeMemberDataList(gameMain.dbAdapter, "1");
                int num = (int)(Math.random()*entyList.size());
                MemberEnty enty = entyList.get(num);
                Toast.makeText(gameMain.appClass, enty.name, Toast.LENGTH_SHORT).show();
                gameMain.showAlertType = INN.ALERT_TYPE_GETNEWMEMBER;
            }
        }
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
                activeNewCard(covenantBtn.name);

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


