package com.epriest.game.guildfantasy.main;

import android.graphics.Bitmap;
import android.view.MotionEvent;

import com.epriest.game.CanvasGL.graphics.GLUtil;
import com.epriest.game.CanvasGL.util.Game;
import com.epriest.game.CanvasGL.util.GameUtil;
import com.epriest.game.guildfantasy.main.enty.MemberEnty;
import com.epriest.game.guildfantasy.main.play.DataManager;

import java.util.ArrayList;

/**
 * Created by darka on 2017-03-26.
 */

public class Game_Member extends Game {

    public Game_Main gameMain;
    public View_Member viewMember;
//    public int viewMode;

    public Bitmap img_memberSheet;
    public Bitmap img_membercard;
    public Bitmap bg;

    public ArrayList<MemberEnty> memberList;
    public ArrayList<Bitmap> img_member;

    public int cardW, cardH;
    public int cardRowNum;
    public int cardTextBoxW, cardTextBoxH;
    public int scrollY, prevScrollY;

    public Game_Member(Game_Main gameMain) {
        this.gameMain = gameMain;
//        this.viewMode = viewMode;
    }


    @Override
    public void gStart() {
        memberList = DataManager.getUserMemberList(gameMain.dbAdapter, gameMain.userEnty.Name);
        img_memberSheet = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/member_sheet.png", null);
        img_membercard = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/membercard.png", null);
        bg = GLUtil.loadAssetsBitmap(gameMain.appClass, "main/member.jpg", null);
        img_member = new ArrayList<>();
        for (int i = 0; i < memberList.size(); i++) {
            img_member.add(GLUtil.loadAssetsBitmap(gameMain.appClass, "member/" + memberList.get(i).image, null));
        }

        cardW = 212;
        cardH = 280;
        cardRowNum = gameMain.canvasW / cardW;

        cardTextBoxW = 140;
        cardTextBoxH = 90;
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

        if (gameMain.appClass.touch.action == MotionEvent.ACTION_UP) {
            for (int i = 0; i < memberList.size(); i++) {
                int cx = 20 + i % cardRowNum * (cardW + 10);
                int cy = 300 + i / cardRowNum * (cardH + 30);
                if (GameUtil.equalsTouch(gameMain.appClass.touch, cx, cy, cardW, cardH)) {
//                    gameMain.userEnty.PARTY_MEMBERID_LIST.set(gameMain.selectCardNum, memberList.get(i).memberId);
//                    gameMain.mainButtonAct(INN.GAME_MEMBER, 0);
                    return;
                }
            }
        }
    }
}
