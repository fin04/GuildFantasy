package com.epriest.game.guildfantasy.main;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.view.MotionEvent;
import android.widget.Toast;

import com.epriest.game.CanvasGL.graphics.GLUtil;
import com.epriest.game.CanvasGL.util.ButtonSprite;
import com.epriest.game.CanvasGL.util.Game;
import com.epriest.game.CanvasGL.util.GameUtil;
import com.epriest.game.guildfantasy.main.enty.ButtonEnty;
import com.epriest.game.guildfantasy.main.enty.PartyEnty;
import com.epriest.game.guildfantasy.main.enty.QuestEnty;
import com.epriest.game.guildfantasy.main.play.DataManager;
import com.epriest.game.guildfantasy.util.DialogActivity;
import com.epriest.game.guildfantasy.util.INN;

import java.util.ArrayList;

/**
 * Created by darka on 2018-01-23.
 */

public class Game_Quest extends Game {

    public Game_Main gameMain;

    public int canvasW, canvasH;

    public final int textLimit = 25;
    public final int textLineLimit = 5;

    public Bitmap img_bg;
    public Bitmap img_paper;
    public Bitmap img_questBitmap;
    public Bitmap img_membercard;

    /**
     * 파티 멤버 아이콘
     */
    public ArrayList<ButtonEnty> partyEntyList;

    /**
     * 출발 버튼
     */
    public ButtonEnty nextBtnEnty;

    /**
     * 현재 퀘스트
     */
    public QuestEnty questEnty;

    public enum QuestType{
        delivery("배달"),
        escort("호위"),
        sweep("소탕"),
        chase("퇴치"),
        hunter("사냥"),
        patrol("순찰");

        private String label;

        QuestType(String type){
            this.label = type;
        }

        public String getLabel(){
            return label;
        }
    }

    public Game_Quest(Game_Main gameMain) {
        this.gameMain = gameMain;
    }

    @Override
    public void gStart() {
        this.canvasW = gameMain.appClass.getGameCanvasWidth();
        this.canvasH = gameMain.appClass.getGameCanvasHeight();

        questEnty = DataManager.getUserQuestEnty(gameMain.dbAdapter, gameMain.userEnty.Name, gameMain.selectQuestId);

        img_bg = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/bg_guild.jpg", null);
        img_paper = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/guildpaper.png", null);
        img_membercard = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/membercard.png", null, 2);
        img_questBitmap = GLUtil.loadAssetsBitmap(gameMain.appClass, "quest/" + questEnty.image, null);

        if (questEnty.textArr.isEmpty()) {
            for (int j = 0; j < questEnty.text.length() / textLimit + 1; j++) {
                if ((j + 1) * textLimit < questEnty.text.length())
                    questEnty.textArr.add(questEnty.text.substring(j * textLimit, (j + 1) * textLimit));
                else
                    questEnty.textArr.add(questEnty.text.substring(j * textLimit, questEnty.text.length()));
            }
        }

        nextBtnEnty = new ButtonEnty();
        nextBtnEnty.name = "의뢰수락";
        nextBtnEnty.clipW = 209;
        nextBtnEnty.clipH = 70;
        nextBtnEnty.clipX = 0;
        nextBtnEnty.clipY = 140;
        nextBtnEnty.drawX = canvasW/2 - nextBtnEnty.clipW/2;
        nextBtnEnty.drawY = canvasH - 120;

        partyEntyList = new ArrayList<>();
        for(int i=0; i<4; i++){
            ButtonEnty enty = new ButtonEnty();
            enty.num = i;
            enty.clipW = 106;
            enty.clipH = 140;
            enty.clipX = 0;
            enty.clipY = 0;
            enty.drawX = i*(enty.clipW+10) + (canvasW - 4*(enty.clipW+10))/2;
            enty.drawY = canvasH - enty.clipH - 180;
            partyEntyList.add(enty);
        }
        int partyMemberNum = 0;
        PartyEnty currentParty = DataManager.getPartyData(
                gameMain.dbAdapter, gameMain.userEnty.Name, gameMain.getSelectPartyNum());
        for(int i=0; i<9; i++) {
            if(partyMemberNum == 4) {
                break;
            }
            String name = currentParty.memberPos[i];
            if (!name.equals("0")) {
                String imgPath = DataManager.getMemberData(gameMain.dbAdapter, name).image;
                partyEntyList.get(partyMemberNum).bitmap = GLUtil.loadAssetsBitmap(gameMain.appClass, "member/" + imgPath, null, 2);
                partyEntyList.get(partyMemberNum).name = name;
                partyMemberNum++;
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

        //파티편성으로 진입
        for(ButtonEnty enty : partyEntyList) {
            if (GameUtil.equalsTouch(gameMain.appClass.touch, enty.drawX, enty.drawY,
                    enty.clipW, enty.clipH)) {
                enty.clickState = ButtonEnty.ButtonClickOn;
                if (gameMain.appClass.touch.action == MotionEvent.ACTION_UP) {
                    enty.clickState = ButtonEnty.ButtonClickOff;
                    gameMain.mainButtonAct(INN.GAME_PARTY);
                }
                return;
            }
        }

        //던전으로 출발
        if (GameUtil.equalsTouch(gameMain.appClass.touch, nextBtnEnty.drawX, nextBtnEnty.drawY,
                nextBtnEnty.clipW, nextBtnEnty.clipH)) {
            nextBtnEnty.clickState = ButtonEnty.ButtonClickOn;
            if (gameMain.appClass.touch.action == MotionEvent.ACTION_UP) {
                nextBtnEnty.clickState = ButtonEnty.ButtonClickOff;
//                gameMain.mainButtonAct(INN.GAME_PARTY);
            }
            return;
        }
    }
}
