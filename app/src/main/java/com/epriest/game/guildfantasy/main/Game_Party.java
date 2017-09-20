package com.epriest.game.guildfantasy.main;

import android.graphics.Bitmap;
import android.view.MotionEvent;

import com.epriest.game.CanvasGL.graphics.GLUtil;
import com.epriest.game.CanvasGL.util.Game;
import com.epriest.game.CanvasGL.util.GameUtil;
import com.epriest.game.guildfantasy.main.enty.ButtonEnty;
import com.epriest.game.guildfantasy.main.enty.PartyEnty;
import com.epriest.game.guildfantasy.util.INN;

import java.util.ArrayList;

/**
 * Created by darka on 2017-04-07.
 */

public class Game_Party extends Game {

    public int partyState;
    public Game_Main gameMain;
//    public View_Member viewMember;

    public ArrayList<ButtonEnty> PartyNumButtonList = new ArrayList<>();
    public ArrayList<ButtonEnty> CardButtonList = new ArrayList<>();
    public ArrayList<Bitmap> img_member;
//    public PartyEnty currentParty = new PartyEnty();
    public ButtonEnty backBtn;
//    public ButtonEnty startBtn;
//    public ButtonEnty supplyBtn;

//    public boolean isViewMember;
    public int selectPartyNum;
    public int selectCardNum;

    public Bitmap img_mainBtn;
    public Bitmap img_menuBar;
    public Bitmap img_membercard;
    public Bitmap img_memberFrame;
    public Bitmap img_title_bg;
    public Bitmap img_questPaper;

    public int mMenuTabBarY;

    public Game_Party(Game_Main gameMain, int partyState) {
        this.gameMain = gameMain;
        this.partyState = partyState;
//        viewMember = new View_Member(gameMain);
    }

    @Override
    public void gStart() {
//        if(partyState == INN.MODE_PARTY_INFO){
//            tempParty = gameMain.playerEnty.PARTYLIST.get(gameMain.selectQuestEnty.actPartyNum-1);
//        }
//        gameMain.selectQuestEnty.textArr = TextUtil.setMultiLineText(gameMain.selectQuestEnty.text, 24, 300);
//        if (gameMain.playerEnty.PARTYLIST.size() == 0)
//            gameMain.playerEnty.PARTYLIST.add(currentParty);
//        else
//            currentParty = gameMain.playerEnty.PARTYLIST.get(selectPartyNum);

        img_menuBar = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/statusbar.png", null);
        img_mainBtn = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/main_btn.png", null);
        img_membercard = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/membercard.png", null);
        img_title_bg = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/party_cr.jpg", null);
//        img_memberFrame = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/member_frame.png", null);
        img_questPaper = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/guildpaper.png", null);

        img_member= new ArrayList<>();
        for(int i=0; i< gameMain.playerEnty.PARTY_MEMBERLIST.size(); i++){
            if(gameMain.playerEnty.PARTY_MEMBERLIST.get(i).charId == null)
                img_member.add(null);
            else
                img_member.add(GLUtil.loadAssetsBitmap(gameMain.appClass, "member/"+gameMain.playerEnty.PARTY_MEMBERLIST.get(i).image, null));
        }

        mMenuTabBarY = gameMain.canvasH - gameMain.statusBarH;

        int cardY = img_title_bg.getHeight() + 300 + 30;
        for (int i = 0; i < 4; i++) {
            ButtonEnty mBtn = new ButtonEnty();
            mBtn.num = i;
            mBtn.name = "card" + (i + 1);
            mBtn.clipW = 162;
            mBtn.clipH = 253;
            mBtn.clipX = 0;
            mBtn.clipY = 0;
            mBtn.drawX = 20 + i * (162 + 10);
            mBtn.drawY = cardY;
            CardButtonList.add(mBtn);
        }

        for (int i = 0; i < 4; i++) {
            ButtonEnty mBtn = new ButtonEnty();
            mBtn.num = i;
            mBtn.name = "party" + (i + 1);
            mBtn.clipW = 103;
            mBtn.clipH = 52;
            mBtn.clipX = 121;
            mBtn.clipY = 0;
            mBtn.drawX = 35 + (mBtn.clipW + 10) * i;
            mBtn.drawY = mMenuTabBarY + 1;
            PartyNumButtonList.add(mBtn);
        }

        backBtn = new ButtonEnty();
        backBtn.clipW = 103;
        backBtn.clipH = 52;
        backBtn.clipX = 121;
        backBtn.clipY = 0;
        backBtn.name = "Back";
        backBtn.drawX = gameMain.canvasW - backBtn.clipW - 15;
        backBtn.drawY = mMenuTabBarY + (gameMain.statusBarH - backBtn.clipH) / 2;
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

        /*if (isViewMember) {
            if (viewMember.onTouchEvent(event)) {
                isViewMember = false;
                currentParty.playerIdList.add(gameMain.playerEnty.MEMBERLIST.get(viewMember.selectMember - 1).id);

//                PartyButtonList.get(selectButton).iconImgNum = gameMain.playerEnty.MEMBERLIST.get(viewMember.selectMember-1).imageId;
//                PartyButtonList.get(selectButton).num = viewMember.selectMember-1;
            }

            return;
        }*/

        if (GameUtil.equalsTouch(gameMain.appClass.touch, backBtn.drawX, backBtn.drawY, backBtn.clipW, backBtn.clipH)) {
            backBtn.clickState = ButtonEnty.ButtonClickOn;
            if (gameMain.appClass.touch.action == MotionEvent.ACTION_UP) {
                backBtn.clickState = ButtonEnty.ButtonClickOff;
                gameMain.mainButtonAct(INN.GAME_HOME, 0, -1);
            }
            return;
        }

        for (int i = 0; i < PartyNumButtonList.size(); i++) {
            ButtonEnty btn = PartyNumButtonList.get(i);
            if (GameUtil.equalsTouch(gameMain.appClass.touch, btn.drawX, btn.drawY, btn.clipW, btn.clipH)) {
                btn.clickState = ButtonEnty.ButtonClickOn;
                if (gameMain.appClass.touch.action == MotionEvent.ACTION_UP) {
                    btn.clickState = ButtonEnty.ButtonClickOff;
                    selectPartyNum = i;
//                    currentParty = gameMain.playerEnty.PARTYLIST.get(selectPartyNum);
                }
                return;
            } else {
                if (btn.clickState == ButtonEnty.ButtonClickOn)
                    btn.clickState = ButtonEnty.ButtonClickOff;
            }
        }

        for (int i = 0; i < CardButtonList.size(); i++) {
            ButtonEnty btn = CardButtonList.get(i);
            if (GameUtil.equalsTouch(gameMain.appClass.touch, btn.drawX, btn.drawY, btn.clipW, btn.clipH)) {
                btn.clickState = ButtonEnty.ButtonClickOn;
                if (gameMain.appClass.touch.action == MotionEvent.ACTION_UP) {
                    btn.clickState = ButtonEnty.ButtonClickOff;
                    selectCardNum = i;
                    gameMain.mainButtonAct(INN.GAME_BAR, INN.MODE_MEMBER_SELECT, selectPartyNum*4+selectCardNum);
                }
                return;
            } else {
                if (btn.clickState == ButtonEnty.ButtonClickOn)
                    btn.clickState = ButtonEnty.ButtonClickOff;
            }
        }

    }
}
