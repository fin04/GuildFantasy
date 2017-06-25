package com.epriest.game.guildfantasy.main;

import android.view.MotionEvent;

import com.epriest.game.CanvasGL.util.Game;
import com.epriest.game.CanvasGL.util.GameUtil;
import com.epriest.game.CanvasGL.util.TextUtil;
import com.epriest.game.guildfantasy.main.enty.ButtonEnty;
import com.epriest.game.guildfantasy.main.enty.PartyEnty;

import java.util.ArrayList;

/**
 * Created by darka on 2017-04-07.
 */

public class Game_Party extends Game {

    public int partyState;
    public Game_Main gameMain;
    public View_Member viewMember;

    public ArrayList<ButtonEnty> PartyButtonList = new ArrayList<>();
    public PartyEnty tempParty = new PartyEnty();
    public ButtonEnty backBtn;
    public ButtonEnty startBtn;
    public ButtonEnty supplyBtn;

    public boolean isViewMember;
    public int selectButtonNUm;

    public Game_Party(Game_Main gameMain, int partyState) {
        this.gameMain = gameMain;
        this.partyState = partyState;
        viewMember = new View_Member(gameMain);
    }

    @Override
    public void gStart() {
        if(partyState == Game_Main.MODE_PARTY_INFO){
            tempParty = gameMain.playerEnty.PARTYLIST.get(gameMain.selectQuestEnty.actPartyNum-1);
        }
        gameMain.selectQuestEnty.textArr = TextUtil.setMultiLineText(gameMain.selectQuestEnty.text, 24, 300);
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

        if(isViewMember){
            if(viewMember.onTouchEvent(event)) {
                isViewMember = false;
                tempParty.playerIdList.add(gameMain.playerEnty.MEMBERLIST.get(viewMember.selectMember-1).id);

//                PartyButtonList.get(selectButton).iconImgNum = gameMain.playerEnty.MEMBERLIST.get(viewMember.selectMember-1).imageId;
//                PartyButtonList.get(selectButton).num = viewMember.selectMember-1;
            }

            return;
        }

        if (GameUtil.equalsTouch(gameMain.appClass.touch, startBtn.drawX, startBtn.drawY, startBtn.clipW, startBtn.clipH)) {
            startBtn.clickState = ButtonEnty.ButtonClickOn;
            if (gameMain.appClass.touch.action == MotionEvent.ACTION_UP) {
                startBtn.clickState = ButtonEnty.ButtonClickOff;
                tempParty.questId = gameMain.selectQuestEnty.id;
                tempParty.num = gameMain.playerEnty.PARTYLIST.size()+1;
                gameMain.playerEnty.PARTYLIST.add(tempParty);

                gameMain.mainButtonAct(Game_Main.GAME_QUEST_SELECT, 0);
            }
            return;
        }

        if (GameUtil.equalsTouch(gameMain.appClass.touch, supplyBtn.drawX, supplyBtn.drawY, supplyBtn.clipW, supplyBtn.clipH)) {
            supplyBtn.clickState = ButtonEnty.ButtonClickOn;
            if (gameMain.appClass.touch.action == MotionEvent.ACTION_UP) {
                supplyBtn.clickState = ButtonEnty.ButtonClickOff;

            }
            return;
        }

        if (GameUtil.equalsTouch(gameMain.appClass.touch, backBtn.drawX, backBtn.drawY, backBtn.clipW, backBtn.clipH)) {
            backBtn.clickState = ButtonEnty.ButtonClickOn;
            if (gameMain.appClass.touch.action == MotionEvent.ACTION_UP) {
                backBtn.clickState = ButtonEnty.ButtonClickOff;
                gameMain.mainButtonAct(Game_Main.GAME_QUEST_SELECT, 0);
            }
            return;
        }

        if (partyState == Game_Main.MODE_PARTY_SELECT) {
            for (int i = 0; i< PartyButtonList.size(); i++) {
                ButtonEnty btn  = PartyButtonList.get(i);
                if (GameUtil.equalsTouch(gameMain.appClass.touch, btn.drawX, btn.drawY, btn.clipW, btn.clipH)) {
                    btn.clickState = ButtonEnty.ButtonClickOn;
                    if (gameMain.appClass.touch.action == MotionEvent.ACTION_UP) {
                        btn.clickState = ButtonEnty.ButtonClickOff;
                        selectButtonNUm = i;
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
