package com.epriest.game.guildfantasy.main;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.epriest.game.CanvasGL.graphics.GLUtil;
import com.epriest.game.CanvasGL.util.Game;
import com.epriest.game.CanvasGL.util.GameUtil;
import com.epriest.game.guildfantasy.main.enty.ButtonEnty;
import com.epriest.game.guildfantasy.main.enty.MemberEnty;
import com.epriest.game.guildfantasy.main.enty.PartyEnty;
import com.epriest.game.guildfantasy.main.play.DataManager;
import com.epriest.game.guildfantasy.util.DrawUtil;
import com.epriest.game.guildfantasy.util.INN;
import com.epriest.game.guildfantasy.util.LoadImageTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by darka on 2017-03-26.
 */

public class Game_Member extends Game {

    public Game_Main gameMain;

    public Bitmap img_cardBg;
    public Bitmap img_bg;

    /**
     * 멤버카드 버튼 리스트
     */
    public ArrayList<ButtonEnty> cardImageList;
    /**
     * 멤버 추가 버튼 리스트
     */
    public ArrayList<ButtonEnty> addButtonList;
    /**
     * 멤버 이미지 리스트
     */
//    public ArrayList<Bitmap> memberBitmapList;

    /**
     * 파티 멤버
     */
    PartyEnty currentParty;

    public final int cardW = 212;
    public final int cardH = 280;
    private final int addBtnW = 47;
    private final int addBtnH = 47;
    private final int exceptBtnW = 85;
    private final int exceptBtnH = 85;

    public final int chrImgW = 720;
    public final int chrImgH = 1024;

    public final int cardTextBoxW = 140;
    public final int cardTextBoxH = 90;

    public final int cardLeftX = 34;
    public final int cardTopY = Game_Main.statusBarH + 200;

    public int maxScrollY;
    /**
     * 화면 내의 카드 열의 최대갯수
     */
    public int maxnum_ScreenCardColum;
    /**
     * 화면 내의 카드 행의 최대갯수
     */
    public int maxnum_ScreenCardRow;
    public int maxnum_ScreenCard;
    public int maxnum_ScrollCardRow;
    /**
     * 화면내에 그려지는 카드 행의 시작 넘버
     */
    public int curDrawCardRow;
    public int prevDrawCardRow;
    public int scrollY, prevScrollY;
    public int selectMemberNum = -1;


    public Game_Member(Game_Main gameMain) {
        this.gameMain = gameMain;
    }


    @Override
    public void gStart() {
        //멤버리스트 불러오기
        ArrayList<MemberEnty> memberList = gameMain.userEnty.MEMBERLIST;
//        memberList = DataManager.getUserMemberList(gameMain.dbAdapter, gameMain.userEnty.Name);
        //파티 불러오기
        currentParty = DataManager.getPartyData(gameMain.dbAdapter, gameMain.userEnty.Name, gameMain.getSelectPartyNum());
        //멤버 리스트 생성
        for (int i = 0; i < memberList.size(); i++) {
//            img_member.add(GLUtil.loadAssetsBitmap(gameMain.appClass, "member/" + memberList.get(i).image, null, 2));
            memberList.get(i).partyNum = getPartyNumFromSelectMemberId(memberList.get(i).memberId);
            memberList.get(i).partyPos = getPositionFromSelectMemberId(memberList.get(i).memberId);
        }

        //멤버 카드 행 숫자
        maxnum_ScreenCardColum = gameMain.canvasW / cardW;
        maxnum_ScreenCardRow = gameMain.canvasH / cardH + 1;
        maxnum_ScreenCard = maxnum_ScreenCardColum * maxnum_ScreenCardRow;
        prevDrawCardRow = 0;
        curDrawCardRow = 0;
        maxnum_ScrollCardRow = memberList.size() /maxnum_ScreenCardColum +1;
        maxScrollY = maxnum_ScrollCardRow * (cardH + 30) - (gameMain.canvasH - cardTopY);


//        memberBitmapList = new ArrayList<>();
//        for (int i = 0; i < imgLoadCnt; i++) {
//            try {
//                Bitmap bitmap = new LoadImageTask(gameMain.appClass, null, 2)
//                        .execute("member/" + memberList.get(i).image).get();
//                memberBitmapList.add(bitmap);
//            } catch (Exception e) {
//            }
//        }

        //파티원 편성용
//        if (gameMain.appClass.gameState == INN.GAME_MEMBER_FROM_PARTY) {
//            memberList.add(0, null);
//            memberBitmapList.add(0, null);
//            memberBitmapList.remove(memberBitmapList.size()-1);
//        }
        setMemberCardList(memberList);
        img_cardBg = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/membercard.png", null);
        img_bg = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/bg_inn.jpg", null);

//        setPartyButton();
    }

    private void setMemberCardList(ArrayList<MemberEnty> memberList) {
        //멤버 이미지 로드
        int imgLoadCnt = maxnum_ScreenCardRow * maxnum_ScreenCardColum;
        if (memberList.size() < imgLoadCnt) {
            imgLoadCnt = memberList.size();
        }
        cardImageList = new ArrayList<>();
        addButtonList = new ArrayList<>();
        for (int i = 0; i < memberList.size(); i++) {
            ButtonEnty cardBtn = new ButtonEnty();
            cardBtn.num = i;
            if (i < imgLoadCnt) {
                try {
                    if (i == 0 && gameMain.appClass.gameState == INN.GAME_MEMBER_FROM_PARTY) {
                        cardBtn.bitmap = null;
                    } else {
                        cardBtn.bitmap = new LoadImageTask(gameMain.appClass, null, 2)
                                .execute("member/" + memberList.get(i).image).get();
                    }
                } catch (ExecutionException e) {
                } catch (InterruptedException e) {
                }
            }
            cardBtn.clipX = 0;
            cardBtn.clipY = 0;
            cardBtn.clipW = cardW;
            cardBtn.clipH = cardH;
            cardBtn.drawX = cardLeftX + i % maxnum_ScreenCardColum * (cardW + 10);
            cardBtn.drawY = cardTopY + i / maxnum_ScreenCardColum * (cardH + 30);
            cardImageList.add(cardBtn);

            if (gameMain.appClass.gameState != INN.GAME_MEMBER_FROM_PARTY)
                continue;

            ButtonEnty mBtn = new ButtonEnty();
            if (i == 0) {
                mBtn.clipW = exceptBtnW;
                mBtn.clipH = exceptBtnH;
                mBtn.clipX = 0;
                mBtn.clipY = 282;
                mBtn.drawX = cardBtn.drawX + (cardW - exceptBtnW) / 2;
                mBtn.drawY = cardBtn.drawY + (cardH - exceptBtnH) / 2;
            } else {
                mBtn.clipW = addBtnW;
                mBtn.clipH = addBtnH;
                mBtn.clipX = 213;
                mBtn.clipY = 0;
                mBtn.drawX = cardLeftX + (i % maxnum_ScreenCardColum) * (cardW + 8) + cardW - mBtn.clipW;
                mBtn.drawY = cardTopY + (i / maxnum_ScreenCardColum) * (cardH + 30) + cardH - mBtn.clipH - 5;
            }
            addButtonList.add(mBtn);
        }
    }

    @Override
    public void gStop() {

    }

    @Override
    public void gUpdate() {

    }

    /**
     * 선택한 멤버를 파티의 포지션에 등록후 파티메뉴로 전환
     *
     * @param memberID   : 선택 멤버
     * @param position   : 파티 포지션
     * @param changeMain : 파티메뉴 전환
     */
    private void setSelectMemberToParty(String memberID, int position, boolean changeMain) {
        DataManager.updateUserPartyMember(gameMain.dbAdapter, memberID,
                gameMain.userEnty.Name, gameMain.getSelectPartyNum(),
                position);

        if (changeMain)
            gameMain.mainButtonAct(INN.GAME_PARTY);
    }

    /**
     * //선택된 멤버가 이미 파티에 등록되어있을 경우 파티위치 반환
     *
     * @param selectMemberID
     * @return
     */
    private int getPositionFromSelectMemberId(String selectMemberID) {
        for (int j = 0; j < currentParty.memberPos.length; j++) {
            if (selectMemberID.equals(currentParty.memberPos[j])) {
                return j;
            }
        }
        return -1;
    }

    /**
     * 선택된 멤버가 이미 파티에 등록되어있을 경우 파티넘버 반환
     *
     * @param selectMemberID
     * @return
     */
    private int getPartyNumFromSelectMemberId(String selectMemberID) {
        for (int j = 0; j < currentParty.memberPos.length; j++) {
            if (selectMemberID.equals(currentParty.memberPos[j])) {
                return currentParty.party_num;
            }
        }
        return -1;
    }

    public String getMemberDB_ID(String memberId) {
        String db_id = memberId.split("-")[0];
        return db_id;
    }

    @Override
    public void gOnTouchEvent(MotionEvent event) {
        if (gameMain.onStatusTouch())
            return;

        //party 편성용 버튼
        if (gameMain.appClass.gameState == INN.GAME_MEMBER_FROM_PARTY) {
            for (int i = 0; i < cardImageList.size(); i++) {
                ButtonEnty btn = cardImageList.get(i);
                if (GameUtil.equalsTouch(gameMain.appClass.touch, btn.drawX, btn.drawY - scrollY, btn.clipW, btn.clipH)) {
                    switch (gameMain.appClass.touch.action) {
                        case MotionEvent.ACTION_DOWN:
                            btn.clickState = ButtonEnty.ButtonClickOn;
                            prevScrollY = scrollY;
                            break;
                        case MotionEvent.ACTION_UP:
                            btn.clickState = ButtonEnty.ButtonClickOff;
                            if (prevScrollY == scrollY) {

                                switch (btn.num) {
                                    case 0:
                                        // 편성해체카드 클릭시 편성해제
                                        setSelectMemberToParty(Game_Party.BlankID, gameMain.getSelectPosition(), true);
                                        break;
                                    default:
                                        String selectMemberID = gameMain.userEnty.MEMBERLIST.get(btn.num).memberId;
                                        String selectMemberDB_ID = getMemberDB_ID(selectMemberID);
                                        String positionMemberID = currentParty.memberPos[gameMain.getSelectPosition()];
                                        String positionMemberDBID = null;
                                        if (!positionMemberID.equals(Game_Party.BlankID)) {
                                            positionMemberDBID = getMemberDB_ID(positionMemberID);
                                        }

                                        // 중복체크
                                        if (selectMemberID.equals(positionMemberID) || selectMemberDB_ID.equals(positionMemberDBID)) {
                                            //선택한 멤버가 수정하려는 파티 포지션의 멤버와 동일할때 -> 그대로 삽입
                                            //선택한 멤버의 타입이 수정하려는 파티 포지션의 멤버의 타입과 동일할때 -> 그대로 삽입
                                        } else {
                                            for (int j = 0; j < currentParty.memberPos.length; j++) {
                                                if (currentParty.memberPos[j].equals(Game_Party.BlankID))
                                                    continue;

                                                String selectPosMemberID = currentParty.memberPos[j];
                                                if (selectMemberID.equals(selectPosMemberID)) {
                                                    //선택한 멤버가 파티의 다른 포지션에 이미 있을때
                                                    // -> 선택한 멤버의 포지션으로 수정하려는 파티 포지션의 멤버를 이동
                                                    setSelectMemberToParty(positionMemberID, j, false);
                                                    break;
                                                } else if (selectMemberDB_ID.equals(getMemberDB_ID(selectPosMemberID))) {
                                                    //선택한 멤버의 타입이 다른 포지션에 이미 있을때
                                                    // -> 동일한 타입의 카드가 중복되는 것을 방지하고 알림을 띄움
                                                    Toast.makeText(gameMain.appClass, "중복된 카드입니다.", Toast.LENGTH_SHORT).show();
                                                    return;
                                                }
                                            }
                                        }

//                                            if (isOverlapDbMemberId(memberList.get(btn.num))) {
////                                        MemberEnty selectPosEnty = DataManager.getMemberData(gameMain.dbAdapter, currentParty.memberPos[gameMain.getSelectPosition()]);
////                                        if (memberList.get(btn.num).db_memberID.equals(selectPosEnty.db_memberID)) {
//                                                //toast
//                                                Toast.makeText(gameMain.appClass, "중복된 카드입니다.", Toast.LENGTH_SHORT).show();
//                                                return;
//                                            }
//
//                                            int selectMemberPartyPos = getPositionFromSelectMemberId(selectEnty.memberId);
//                                            if (selectMemberPartyPos > -1) {
//
//                                                if (positionMemberID.equals(Game_Party.BlankID)) {
//                                                    // 선택한 멤버의 원래 포지션에서 멤버를 삭제
//                                                    setSelectMemberToParty(Game_Party.BlankID, selectMemberPartyPos, false);
//                                                }else{
//                                                    //선택된 포지션에 memberId가 있을때 선택한 멤버의 원래 포지션에 삽입
//                                                    setSelectMemberToParty(positionMemberID, selectMemberPartyPos, false);
//                                                }
//                                        }
                                        //선택한 멤버를 수정하려는 파티 포지션에 삽입
                                        setSelectMemberToParty(selectMemberID, gameMain.getSelectPosition(), true);
                                        break;
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
                selectMemberNum = (((int) gameMain.appClass.touch.mLastTouchX - cardLeftX) / cardW)
                        + (((int) gameMain.appClass.touch.mLastTouchY + scrollY - cardTopY) / cardH) * maxnum_ScreenCardColum;
                if (gameMain.userEnty.MEMBERLIST.size() < selectMemberNum) {
                    selectMemberNum = -1;
                }
//                gameMain.userEnty.MEMBERLIST.get(selectMember).party_number = gameMain.selectCardNum;
//                String partyID = gameMain.userEnty.Name+"_"+gameMain.getSelectPartyNum();

                if (selectMemberNum > 0) {
                    String memberID = gameMain.userEnty.MEMBERLIST.get(selectMemberNum).memberId;
                    DataManager.updateUserPartyMember(gameMain.dbAdapter, memberID,
                            gameMain.userEnty.Name, gameMain.getSelectPartyNum(), gameMain.getSelectPosition());
                }
                return;
            } else {
            }
        }
        scrollY = prevScrollY - (int) (gameMain.appClass.touch.mLastTouchY - gameMain.appClass.touch.mDownY);

        if (maxScrollY < 0) {
            maxScrollY = 0;
            return;
        }
        if (scrollY < 0) {
            scrollY = 0;
            curDrawCardRow = 0;
        } else if (scrollY > maxScrollY) {
            scrollY = maxScrollY;
            curDrawCardRow = maxScrollY / cardH;
        } else {
            curDrawCardRow = scrollY / cardH;
            if (0 > curDrawCardRow) {
                return;
            } else if (prevDrawCardRow < curDrawCardRow && curDrawCardRow+maxnum_ScreenCardRow <= maxnum_ScrollCardRow) {
                int loadCardCnt = (curDrawCardRow - prevDrawCardRow) * maxnum_ScreenCardColum;
                int loadCardNum = (prevDrawCardRow + maxnum_ScreenCardRow) * maxnum_ScreenCardColum;
                for (int i = loadCardNum; i < loadCardNum + loadCardCnt; i++) {
                    if (cardImageList.size() <= i)
                        break;
                    String imgPath = "member/" + gameMain.userEnty.MEMBERLIST.get(i).image;
                    try {
                        cardImageList.get(i).bitmap = new LoadImageTask(gameMain.appClass,
                                null, 2).execute(imgPath).get();
                    } catch (ExecutionException e) {
                    } catch (InterruptedException e) {
                    } finally {
                        int num = i - maxnum_ScreenCard;
                        DrawUtil.recycleBitmap(cardImageList.get(num).bitmap);
                    }
                }
                prevDrawCardRow = curDrawCardRow;
            } else if (prevDrawCardRow > curDrawCardRow) {
                int loadCardCnt = (prevDrawCardRow - curDrawCardRow) * maxnum_ScreenCardColum;
                int loadCardNum = curDrawCardRow * maxnum_ScreenCardColum;
                for (int i = loadCardNum; i < loadCardNum + loadCardCnt; i++) {
                    if (cardImageList.size() <= i)
                        break;
                    String imgPath = "member/" + gameMain.userEnty.MEMBERLIST.get(i).image;
                    try {
                        cardImageList.get(i).bitmap = new LoadImageTask(gameMain.appClass,
                                null, 2).execute(imgPath).get();
                    } catch (ExecutionException e) {
                    } catch (InterruptedException e) {
                    } finally {
                        int num = i + maxnum_ScreenCard;
                        if (num >= cardImageList.size())
                            continue;
                        DrawUtil.recycleBitmap(cardImageList.get(num).bitmap);
                    }
                }
                prevDrawCardRow = curDrawCardRow;
            }

        }

//        if(event.getAction() == MotionEvent.ACTION_UP) {
//            if(prevScrollY == scrollY){
//                selectMember = (((int)gameMain.appClass.touch.mLastTouchX-cardX) / cardW)
//                        + (((int)gameMain.appClass.touch.mLastTouchY+scrollY-cardY)/cardH)*maxnum_ScreenCardColum + 1;
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
                int cx = cardX + i % maxnum_ScreenCardColum * (cardW + 10);
                int cy = cardY + i / maxnum_ScreenCardColum * (cardH + 30);
                if (GameUtil.equalsTouch(gameMain.appClass.touch, cx, cy, cardW, cardH)) {
//                    gameMain.userEnty.PARTY_MEMBERID_LIST.set(gameMain.selectCardNum, memberList.get(i).memberId);
//                    gameMain.mainButtonAct(INN.GAME_MEMBER, 0);
                    return;
                }
            }
        }*/
    }
}
