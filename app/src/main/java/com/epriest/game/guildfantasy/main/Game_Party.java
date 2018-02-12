package com.epriest.game.guildfantasy.main;

import android.graphics.Bitmap;
import android.view.MotionEvent;

import com.epriest.game.CanvasGL.graphics.GLUtil;
import com.epriest.game.CanvasGL.util.Game;
import com.epriest.game.CanvasGL.util.GameUtil;
import com.epriest.game.guildfantasy.main.enty.ButtonEnty;
import com.epriest.game.guildfantasy.main.enty.MemberEnty;
import com.epriest.game.guildfantasy.main.enty.PartyEnty;
import com.epriest.game.guildfantasy.main.play.AlertManager;
import com.epriest.game.guildfantasy.main.play.DataManager;
import com.epriest.game.guildfantasy.main.play.GameDialog;
import com.epriest.game.guildfantasy.util.INN;

import java.util.ArrayList;

/**
 * Created by darka on 2017-04-07.
 */

public class Game_Party extends Game {

    final public static int PartyMemberMaximum = 4;

    public Game_Main gameMain;
    public GameDialog limitedPartyMemberDialog;

    /**
     * 파티 선택 버튼
     */
    public ArrayList<ButtonEnty> PartyNumButtonList = new ArrayList<>();
    /**
     * 파티원 카드 버튼
     */
    public ArrayList<ButtonEnty> PartyCardList = new ArrayList<>();
    /**
     * 파티정보
     */
    public PartyEnty currentParty = new PartyEnty();


    public Bitmap img_membercard;
    public Bitmap img_title_bg;

    /**
     * 현재 파티의 파티원 수
     */
    public int partyMemberCount;

    public Game_Party(Game_Main gameMain) {
        this.gameMain = gameMain;
    }

    @Override
    public void gStart() {
        img_membercard = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/membercard.png", null);
        img_title_bg = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/bg_party.jpg", null);

        //userName으로 파티정보를 불러옴
        currentParty = DataManager.getPartyData(gameMain.dbAdapter, gameMain.userEnty.Name, gameMain.getSelectPartyNum());

        limitedPartyMemberDialog = new GameDialog(gameMain.appClass);
        limitedPartyMemberDialog.setOnButtonListener(new GameDialog.onClickListener() {
            @Override
            public void onPositiveClick() {
                gameMain.showAlertType = GameDialog.ALERT_TYPE_NONE;
            }

            @Override
            public void onCancelClick() {
                gameMain.showAlertType = GameDialog.ALERT_TYPE_NONE;
            }
        });
        limitedPartyMemberDialog.setText("더 이상 파티원을 추가할 수 없습니다.");

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
            mCard.clipW = 212;
            mCard.clipH = 280;
            mCard.clipX = 0;
            mCard.clipY = 0;
            mCard.drawX = 34 + (i % 3) * (mCard.clipW + 8);
            mCard.drawY = cardY + (i / 3) * (mCard.clipH + 10);
            mCard.name = currentParty.memberPos[i];
            if(!mCard.name.equals("0")) {
                partyMemberCount++;
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
        if (gameMain.onStatusTouch())
            return;

        if (gameMain.showAlertType == GameDialog.ALERT_TYPE_LIMITEDPARTYMEMBER) {
            if (limitedPartyMemberDialog.onTouch())
                return;
        }

        //파티선택
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

        //파티멤버선택
        for (int i = 0; i < PartyCardList.size(); i++) {
            ButtonEnty btn = PartyCardList.get(i);
            if (GameUtil.equalsTouch(gameMain.appClass.touch, btn.drawX, btn.drawY, btn.clipW, btn.clipH)) {
                btn.clickState = ButtonEnty.ButtonClickOn;
                if (gameMain.appClass.touch.action == MotionEvent.ACTION_UP) {
                    btn.clickState = ButtonEnty.ButtonClickOff;
                    //파티원이 제한수가 넘고, 빈 편성카드를 클릭했을때 (경고창 "더이상 추가할 수 없습니다")
                    if(currentParty.memberPos[i].equals("0") && partyMemberCount >= PartyMemberMaximum) {
                        gameMain.showAlertType = GameDialog.ALERT_TYPE_LIMITEDPARTYMEMBER;
                        return;
                    }
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
