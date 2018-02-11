package com.epriest.game.guildfantasy.main;

import android.graphics.Bitmap;
import android.view.MotionEvent;

import com.epriest.game.CanvasGL.graphics.GLUtil;
import com.epriest.game.CanvasGL.util.Game;
import com.epriest.game.CanvasGL.util.GameUtil;
import com.epriest.game.guildfantasy.main.enty.ButtonEnty;
import com.epriest.game.guildfantasy.main.enty.MemberEnty;
import com.epriest.game.guildfantasy.main.enty.PartyEnty;
import com.epriest.game.guildfantasy.main.play.DataManager;
import com.epriest.game.guildfantasy.util.INN;

import java.util.ArrayList;

/**
 * Created by darka on 2017-04-07.
 */

public class Game_Party extends Game {

    //    public int partyState;
    public Game_Main gameMain;
//    public View_Member viewMember;

    public ArrayList<ButtonEnty> PartyNumButtonList = new ArrayList<>();
    public ArrayList<ButtonEnty> PartyCardList = new ArrayList<>();
//    public ArrayList<Bitmap> CardImgList = new ArrayList<>();
    public PartyEnty currentParty = new PartyEnty();

    public ButtonEnty backBtn;
    public ButtonEnty okBtn;
//    public ButtonEnty supplyBtn;

    /**
     * partyNumber.cardNumber
     */
//    public String selectCardNum;

    //    public Bitmap img_mainBtn;
//    public Bitmap img_statusBar;
    public Bitmap img_membercard;
    public Bitmap img_memberFrame;
    public Bitmap img_title_bg;
//    public Bitmap img_questPaper;

//    public int mMenuTabBarY;

    public Game_Party(Game_Main gameMain) {
        this.gameMain = gameMain;
//        this.partyState = partyState;
//        viewMember = new View_Member(gameMain);
    }

    @Override
    public void gStart() {
//        if(partyState == INN.MODE_PARTY_INFO){
//            tempParty = gameMain.userEnty.PARTYLIST.get(gameMain.selectQuestEnty.actPartyNum-1);
//        }
//        gameMain.selectQuestEnty.textArr = TextUtil.setMultiLineText(gameMain.selectQuestEnty.text, 24, 300);
//        if (gameMain.userEnty.PARTYLIST.size() == 0)
//            gameMain.userEnty.PARTYLIST.add(currentParty);
//        else
//            currentParty = gameMain.userEnty.PARTYLIST.get(selectPartyNum);

//        img_statusBar = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/statusbar.png", null);
//        img_mainBtn = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/main_btn.png", null);
        img_membercard = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/membercard.png", null);
        img_title_bg = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/bg_party.jpg", null);
//        img_memberFrame = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/member_frame.png", null);
//        img_questPaper = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/guildpaper.png", null);

//        gameMain.setCardListFromSelectParty(0, 0);
        currentParty = DataManager.getPartyData(gameMain.dbAdapter, gameMain.userEnty.Name, gameMain.getSelectPartyNum());

        backBtn = new ButtonEnty();
        backBtn.clipW = 102;
        backBtn.clipH = 53;
        backBtn.clipX = 121;
        backBtn.clipY = 0;
        backBtn.name = "back";
        backBtn.drawX = gameMain.canvasW - backBtn.clipW - 15;
        backBtn.drawY = (gameMain.statusBarH - backBtn.clipH) / 2;

        okBtn = new ButtonEnty();
        okBtn.clipW = 102;
        okBtn.clipH = 53;
        okBtn.clipX = 121;
        okBtn.clipY = 0;
        okBtn.name = "ok";
        okBtn.drawX = gameMain.canvasW - okBtn.clipW * 2 - 20;
        okBtn.drawY = (gameMain.statusBarH - okBtn.clipH) / 2;

        // party card 위치
        setPartyCardList();

        int partyBtnH = 115;
        int bottomMenuY = gameMain.canvasH - partyBtnH;
        // party button 위치
        for (int i = 0; i < 5; i++) {
            ButtonEnty mBtn = new ButtonEnty();
            mBtn.num = i;
            mBtn.name = "party" + (i + 1);
            mBtn.clipW = 115;
            mBtn.clipH = partyBtnH;
            mBtn.clipX = 0;
            mBtn.clipY = 172;
            mBtn.drawX = 40 + (mBtn.clipW + 10) * i;
            mBtn.drawY = bottomMenuY - 5;
            PartyNumButtonList.add(mBtn);
        }
    }

    /**
     * Card Setting
     */
    private void setPartyCardList(){
        int cardY = 300;
        for (int i = 0; i < 9; i++) {
            ButtonEnty mCard = new ButtonEnty();
            mCard.num = i;
//            mCard.name = "card" + (i + 1);
            mCard.clipW = 212;
            mCard.clipH = 280;
            mCard.clipX = 0;
            mCard.clipY = 0;
            mCard.drawX = 34 + (i % 3) * (mCard.clipW + 8);
            mCard.drawY = cardY + (i / 3) * (mCard.clipH + 10);
            mCard.name = currentParty.memberPos[i];
            if(!mCard.name.equals("0")) {
                String imgPath = DataManager.getMemberData(gameMain.dbAdapter, mCard.name).image;
                mCard.bitmap = GLUtil.loadAssetsBitmap(gameMain.appClass, "member/" + imgPath, null, 2);
            }
            PartyCardList.add(mCard);
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

        if (GameUtil.equalsTouch(gameMain.appClass.touch, okBtn.drawX, okBtn.drawY,
                okBtn.clipW, okBtn.clipH)) {
            okBtn.clickState = ButtonEnty.ButtonClickOn;
            if (gameMain.appClass.touch.action == MotionEvent.ACTION_UP) {
                okBtn.clickState = ButtonEnty.ButtonClickOff;
                //파티설정 저장
            }
            return;
        }

        if (GameUtil.equalsTouch(gameMain.appClass.touch, backBtn.drawX, backBtn.drawY,
                backBtn.clipW, backBtn.clipH)) {
            backBtn.clickState = ButtonEnty.ButtonClickOn;
            if (gameMain.appClass.touch.action == MotionEvent.ACTION_UP) {
                backBtn.clickState = ButtonEnty.ButtonClickOff;
                gameMain.mainButtonAct(INN.GAME_QUEST);
            }
            return;
        }

        for (int i = 0; i < PartyNumButtonList.size(); i++) {
            ButtonEnty btn = PartyNumButtonList.get(i);
            if (GameUtil.equalsTouch(gameMain.appClass.touch, btn.drawX, btn.drawY, btn.clipW, btn.clipH)) {
                btn.clickState = ButtonEnty.ButtonClickOn;
                if (gameMain.appClass.touch.action == MotionEvent.ACTION_UP) {
                    btn.clickState = ButtonEnty.ButtonClickOff;
                    gameMain.setSelectPartyNum(i);
//                    currentParty = gameMain.userEnty.PARTYLIST.get(selectPartyNum);
                    currentParty = DataManager.getPartyData(gameMain.dbAdapter,
                            gameMain.userEnty.Name, gameMain.getSelectPartyNum());
                    setPartyCardList();
                }
                return;
            } else {
                if (btn.clickState == ButtonEnty.ButtonClickOn)
                    btn.clickState = ButtonEnty.ButtonClickOff;
            }
        }

        for (int i = 0; i < PartyCardList.size(); i++) {
            ButtonEnty btn = PartyCardList.get(i);
            if (GameUtil.equalsTouch(gameMain.appClass.touch, btn.drawX, btn.drawY, btn.clipW, btn.clipH)) {
                btn.clickState = ButtonEnty.ButtonClickOn;
                if (gameMain.appClass.touch.action == MotionEvent.ACTION_UP) {
                    btn.clickState = ButtonEnty.ButtonClickOff;
                    gameMain.setSelectCardNum(i);
                    gameMain.mainButtonAct(INN.GAME_MEMBER_FROM_PARTY, i);
                }
                return;
            } else {
                if (btn.clickState == ButtonEnty.ButtonClickOn)
                    btn.clickState = ButtonEnty.ButtonClickOff;
            }
        }

    }


}
