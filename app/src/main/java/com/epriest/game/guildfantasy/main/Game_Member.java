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

    public Bitmap img_membercard;
    public Bitmap img_bg;

    /**
     * 파티추가버튼 리스트
     */
    public ArrayList<ButtonEnty> PartyAddButtonList = new ArrayList<>();
    /**
     * 멤버 리스트
     */
    public ArrayList<MemberEnty> memberList;
    /**
     * 멤버 이미지 리스트
     */
    public ArrayList<Bitmap> img_member;

    public final int cardW = 212;
    public final int cardH = 280;

    public final int cardTextBoxW = 140;
    public final int cardTextBoxH = 90;

    public final int cardLeftX = 34;
    public final int cardTopY = Game_Main.statusBarH + 200;

    public final int addBtnW = 47;
    public final int addBtnH = 47;

    public final int exceptBtnW = 85;
    public final int exceptBtnH = 85;

    public int cardRowNum;
    public int scrollY, prevScrollY;
    public int selectMember = -1;


    public Game_Member(Game_Main gameMain) {
        this.gameMain = gameMain;
    }


    @Override
    public void gStart() {
        //멤버리스트 불러오기
        memberList = DataManager.getUserMemberList(gameMain.dbAdapter, gameMain.userEnty.Name);
        //멤버 이미지 불러오기
        img_member = new ArrayList<>();
        for (int i = 0; i < memberList.size(); i++) {
            img_member.add(GLUtil.loadAssetsBitmap(gameMain.appClass, "member/" + memberList.get(i).image, null, 2));
        }

        //멤버 카드 행 숫자
        cardRowNum = gameMain.canvasW / cardW;
        //파티원 편성용
        if (gameMain.appClass.gameState == INN.GAME_MEMBER_FROM_PARTY) {
            memberList.add(0, null);
            img_member.add(0, null);
            setAddMemberCardList();
        }

        img_membercard = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/membercard.png", null);
        img_bg = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/bg_inn.jpg", null);

//        setPartyButton();
    }

    private void setAddMemberCardList() {

        for (int i = 0; i < memberList.size(); i++) {
            ButtonEnty mBtn = new ButtonEnty();
            mBtn.num = i;
            if(i == 0){
                mBtn.clipW = exceptBtnW;
                mBtn.clipH = exceptBtnH;
                mBtn.clipX = 0;
                mBtn.clipY = 282;
                mBtn.drawX = cardLeftX + (cardW-exceptBtnW)/2;
                mBtn.drawY = cardTopY + (cardH-exceptBtnH)/2;
            }else {
                mBtn.clipW = addBtnW;
                mBtn.clipH = addBtnH;
                mBtn.clipX = 213;
                mBtn.clipY = 0;
                mBtn.drawX = cardLeftX + (i % cardRowNum) * (cardW + 8) + cardW - mBtn.clipW;
                mBtn.drawY = cardTopY + (i / cardRowNum) * (cardH + 30) + cardH - mBtn.clipH;
            }
            PartyAddButtonList.add(mBtn);
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
            for (int i = 0; i < PartyAddButtonList.size(); i++) {
                ButtonEnty btn = PartyAddButtonList.get(i);
                if (GameUtil.equalsTouch(gameMain.appClass.touch, btn.drawX, btn.drawY + scrollY, btn.clipW, btn.clipH)) {
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
                                    //memberID가 "0"이면 파티에서 제거, 그보다 크면 파티원 추가
                                    String memberID = "0";
                                    if (selectMember > 0) {
                                        memberID = memberList.get(selectMember).memberId;
                                    }
                                    DataManager.updateUserPartyMember(gameMain.dbAdapter, memberID,
                                            gameMain.userEnty.Name, gameMain.getSelectPartyNum(),
                                            gameMain.getSelectPosition());
                                    gameMain.mainButtonAct(INN.GAME_PARTY);
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
                selectMember = (((int) gameMain.appClass.touch.mLastTouchX - cardLeftX) / cardW)
                        + (((int) gameMain.appClass.touch.mLastTouchY + scrollY - cardTopY) / cardH) * cardRowNum;
                if (gameMain.userEnty.MEMBERLIST.size() < selectMember) {
                    selectMember = -1;
                }
//                gameMain.userEnty.MEMBERLIST.get(selectMember).party_number = gameMain.selectCardNum;
//                String partyID = gameMain.userEnty.Name+"_"+gameMain.getSelectPartyNum();

                if (selectMember > 0) {
                    String memberID = memberList.get(selectMember).memberId;
                    DataManager.updateUserPartyMember(gameMain.dbAdapter, memberID,
                            gameMain.userEnty.Name, gameMain.getSelectPartyNum(), gameMain.getSelectPosition());
                }
                return;
            } else {
            }
        }
        scrollY = prevScrollY - (int) (gameMain.appClass.touch.mLastTouchY - gameMain.appClass.touch.mDownY);
        int maxScrollY = (gameMain.userEnty.MEMBERLIST.size() / cardRowNum) * (cardH + 30) - (gameMain.canvasH - cardTopY);
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
