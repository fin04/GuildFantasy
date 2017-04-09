package com.epriest.game.guildfantasy.main;

import android.view.MotionEvent;
import android.widget.Toast;

import com.epriest.game.CanvasGL.util.Game;
import com.epriest.game.CanvasGL.util.GameUtil;
import com.epriest.game.guildfantasy.enty.ButtonEnty;

import java.util.ArrayList;

/**
 * Created by darka on 2017-04-07.
 */

public class Game_Party extends Game {

    public int PartyState;
    public Game_Main gameMain;
    public View_Member viewMember;

    public ArrayList<ButtonEnty> PartyButtonList = new ArrayList<>();

    public boolean isViewMember;

    public Game_Party(Game_Main gameMain) {
        this.gameMain = gameMain;
        viewMember = new View_Member(gameMain);
    }

    @Override
    public void gStart() {
        if(gameMain.QuestEnty == null)
            PartyState = Game_Main.MODE_PARTY_INFO;
        else
            PartyState = Game_Main.MODE_PARTY_SELECT;

        for (int i = 0; i < 4; i++) {
            ButtonEnty btn = new ButtonEnty();
            btn.x = 700;
            btn.y = 80 + (i * 85);
            btn.w = 125;
            btn.h = 75;
            PartyButtonList.add(btn);
        }
    }

    @Override
    public void gStop() {
    }

    @Override
    public void gUpdate() {

    }

    int selectButton;
    @Override
    public void gOnTouchEvent(MotionEvent event) {
        if(isViewMember){
            if(viewMember.onTouchEvent(event)) {
                isViewMember = false;
                PartyButtonList.get(selectButton).iconImgNum = gameMain.playerEnty.MEMBERLIST.get(viewMember.selectMember-1).imageId;
            }

            return;
        }

        if (PartyState == Game_Main.MODE_PARTY_SELECT) {
            for (int i = 0; i< PartyButtonList.size(); i++) {
                ButtonEnty btn  = PartyButtonList.get(i);
                if (GameUtil.equalsTouch(gameMain.appClass.touch, btn.x, btn.y, btn.w, btn.h)) {
                    btn.clickState = ButtonEnty.ButtonClickOn;
                    if (gameMain.appClass.touch.Action == MotionEvent.ACTION_UP) {
                        btn.clickState = ButtonEnty.ButtonClickOff;
                        selectButton = i;
                        isViewMember = true;
                    }
                    return;
                } else {
                    if (btn.clickState == ButtonEnty.ButtonClickOn)
                        btn.clickState = ButtonEnty.ButtonClickOff;
                }
            }
        }

    }
}
