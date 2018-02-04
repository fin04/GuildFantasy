package com.epriest.game.guildfantasy.main;

import android.graphics.Bitmap;
import android.view.MotionEvent;

import com.epriest.game.CanvasGL.graphics.GLUtil;
import com.epriest.game.CanvasGL.util.Game;
import com.epriest.game.CanvasGL.util.GameUtil;
import com.epriest.game.guildfantasy.main.enty.ButtonEnty;
import com.epriest.game.guildfantasy.main.enty.MemberEnty;
import com.epriest.game.guildfantasy.main.play.AlertManager;
import com.epriest.game.guildfantasy.main.play.DataManager;
import com.epriest.game.guildfantasy.util.INN;

import java.util.ArrayList;

/**
 * Created by darka on 2017-03-26.
 */

public class Game_Member extends Game {

    public Game_Main gameMain;

    public Bitmap img_memberSheet;
    public Bitmap img_membercard;
    public Bitmap img_bg;

    public ArrayList<ButtonEnty> PartyButtonList = new ArrayList<>();
    public ArrayList<ButtonEnty> addMemberButtonList = new ArrayList<>();
    public ArrayList<MemberEnty> memberList;
    public ArrayList<Bitmap> img_member;

        public int cardW, cardH;
    public int cardRowNum;
    public int cardTextBoxW, cardTextBoxH;
    public int scrollY, prevScrollY;

    public int selectMember = -1;

    public int cardX, cardY;

    public Game_Member(Game_Main gameMain) {
        this.gameMain = gameMain;
    }


    @Override
    public void gStart() {
        memberList = DataManager.getUserMemberList(gameMain.dbAdapter, gameMain.userEnty.Name);
        img_memberSheet = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/member_sheet.png", null);
        img_membercard = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/membercard.png", null);
        img_bg = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/bg_inn.jpg", null);
        img_member = new ArrayList<>();
        for (int i = 0; i < memberList.size(); i++) {
            img_member.add(GLUtil.loadAssetsBitmap(gameMain.appClass, "member/" + memberList.get(i).image, null, 2));
        }

        cardW = 212;
        cardH = 280;
        cardRowNum = gameMain.canvasW / cardW;

        cardX = 34;
        cardY = gameMain.statusBarH + 200;

        if (gameMain.appClass.gameState == INN.GAME_MEMBER_FROM_PARTY) {
            setAddMemberCardList();
        }

        setPartyButton();
    }

    private void setAddMemberCardList() {
        int btnW = 47;
        int btnH = 47;

        cardTextBoxW = 140;
        cardTextBoxH = 90;

//        int cardY = 300;
        for (int i = 0; i < 9; i++) {
            ButtonEnty mBtn = new ButtonEnty();
            mBtn.num = i;
//            mCard.name = "card" + (i + 1);
            mBtn.clipW = btnW;
            mBtn.clipH = btnH;
            mBtn.clipX = 213;
            mBtn.clipY = 0;
            mBtn.drawX = 34 + (i % cardRowNum) * (cardW + 8) + cardW - mBtn.clipW;
            mBtn.drawY = cardY + (i / cardRowNum) * (cardH + 10) + cardH - mBtn.clipH;
            mBtn.name = memberList.get(i).name;
            if (!mBtn.name.equals("0")) {
                String imgPath = memberList.get(i).image;
                mBtn.bitmap = GLUtil.loadAssetsBitmap(gameMain.appClass, "member/" + imgPath, null, 2);
            }
            addMemberButtonList.add(mBtn);
        }
    }

    private void setPartyButton() {
        int memberBtnH = 84;
        int bottomMenuY = gameMain.canvasH - memberBtnH;
        for (int i = 0; i < 5; i++) {
            ButtonEnty mParty = new ButtonEnty();
            mParty.num = i;
            mParty.name = "party" + (i + 1);
            mParty.clipW = 95;
            mParty.clipH = memberBtnH;
            mParty.clipX = 0;
            mParty.clipY = 0;
            mParty.drawX = 30 + (mParty.clipW + 10) * i;
            mParty.drawY = bottomMenuY + 1;
            PartyButtonList.add(mParty);
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

        //party 편성용 버튼
        if (gameMain.appClass.gameState == INN.GAME_MEMBER_FROM_PARTY) {
            for (int i = 0; i < addMemberButtonList.size(); i++) {
                ButtonEnty btn = addMemberButtonList.get(i);
                if (GameUtil.equalsTouch(gameMain.appClass.touch, btn.drawX, btn.drawY, btn.clipW, btn.clipH+scrollY)) {
                    switch (gameMain.appClass.touch.action) {
                        case MotionEvent.ACTION_DOWN:
                            btn.clickState = ButtonEnty.ButtonClickOn;
                            prevScrollY = scrollY;
                            break;
                        case MotionEvent.ACTION_UP:
                            btn.clickState = ButtonEnty.ButtonClickOff;
                            if (prevScrollY == scrollY) {
                                selectMember = btn.num;
                                if (selectMember != -1) {
                                    String memberID = memberList.get(selectMember).memberId;
                                    DataManager.updateUserPartyMember(gameMain.dbAdapter, memberID,
                                            gameMain.userEnty.Name, gameMain.getSelectPartyNum(),
                                            gameMain.getSelectPosition());
                                    gameMain.mainButtonAct(INN.GAME_PARTY, 0);
                                }
                            }
                            break;
                    }
                    return;
                }
            }
        }

        //멤버 상세
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            prevScrollY = scrollY;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (prevScrollY == scrollY) {
                selectMember = (((int) gameMain.appClass.touch.mLastTouchX - cardX) / cardW)
                        + (((int) gameMain.appClass.touch.mLastTouchY + scrollY - cardY) / cardH) * cardRowNum;
                if(gameMain.userEnty.MEMBERLIST.size() < selectMember) {
                    selectMember = -1;
                }
//                gameMain.userEnty.MEMBERLIST.get(selectMember).party_number = gameMain.selectCardNum;
//                String partyID = gameMain.userEnty.Name+"_"+gameMain.getSelectPartyNum();

                if (selectMember != -1) {
                    String memberID = memberList.get(selectMember).memberId;
                    DataManager.updateUserPartyMember(gameMain.dbAdapter, memberID,
                            gameMain.userEnty.Name, gameMain.getSelectPartyNum(), gameMain.getSelectPosition());
                }
                return;
            } else {
            }
        }
        scrollY = prevScrollY - (int) (gameMain.appClass.touch.mLastTouchY - gameMain.appClass.touch.mDownY);
        int maxScrollY = (gameMain.userEnty.MEMBERLIST.size() / cardRowNum) * (cardH + 30) - (gameMain.canvasH - cardY);
        if (maxScrollY < 0)
            maxScrollY = 0;
        if (scrollY < 0)
            scrollY = 0;
        else if (scrollY > maxScrollY)
            scrollY = maxScrollY;

//        if(event.getAction() == MotionEvent.ACTION_UP) {
//            if(prevScrollY == scrollY){
//                selectMember = (((int)gameMain.appClass.touch.mLastTouchX-cardX) / cardW)
//                        + (((int)gameMain.appClass.touch.mLastTouchY+scrollY-cardY)/cardH)*cardRowNum + 1;
//                gameMain.userEnty.MEMBERLIST.get(selectMember).party_number = gameMain.selectCardNum;
//                gameMain.mainButtonAct(INN.GAME_PARTY, 0);
//                return;
//            }else
//                prevScrollY = scrollY;
//        }
//        scrollY = prevScrollY + (int)(gameMain.appClass.touch.mLastTouchY - gameMain.appClass.touch.mDownY);
//        int maxScrollY = (gameMain.userEnty.MEMBERLIST.size()/4+1) * 200 - gameMain.appClass.getGameCanvasHeight() + 100;
//        if(scrollY < 0)
//            scrollY = 0;
//        else if(scrollY > maxScrollY)
//            scrollY = maxScrollY;

        /*if (gameMain.appClass.touch.action == MotionEvent.ACTION_UP) {
            for (int i = 0; i < memberList.size(); i++) {
                int cx = cardX + i % cardRowNum * (cardW + 10);
                int cy = cardY + i / cardRowNum * (cardH + 30);
                if (GameUtil.equalsTouch(gameMain.appClass.touch, cx, cy, cardW, cardH)) {
//                    gameMain.userEnty.PARTY_MEMBERID_LIST.set(gameMain.selectCardNum, memberList.get(i).memberId);
//                    gameMain.mainButtonAct(INN.GAME_MEMBER, 0);
                    return;
                }
            }
        }*/
    }
}
