package com.epriest.game.guildfantasy.main;

import android.view.MotionEvent;

import com.epriest.game.CanvasGL.graphics.GLUtil;
import com.epriest.game.CanvasGL.util.Game;
import com.epriest.game.guildfantasy.main.enty.MemberEnty;
import com.epriest.game.guildfantasy.main.enty.PartyEnty;
import com.epriest.game.guildfantasy.main.enty.UnitEnty;
import com.epriest.game.guildfantasy.main.play.DataManager;

import java.util.ArrayList;

/**
 * Created by darka on 2018-02-01.
 */

public class Game_Dungeon extends Game {
    public Game_Main gameMain;

    public int canvasW, canvasH;

    /**
     * 파티 멤버 아이콘
     */
    public ArrayList<UnitEnty> partyUnitList;

    public Game_Dungeon(Game_Main gameMain) {
        this.gameMain = gameMain;
    }

    @Override
    public void gStart() {
        this.canvasW = gameMain.appClass.getGameCanvasWidth();
        this.canvasH = gameMain.appClass.getGameCanvasHeight();

        partyUnitList = new ArrayList<>();
        PartyEnty currentParty = DataManager.getPartyData(
                gameMain.dbAdapter, gameMain.userEnty.Name, gameMain.getSelectPartyNum());
        int memberCnt = 0;
        for (int i = 0; i < currentParty.memberPos.length; i++) {
            if (memberCnt == 4) {
                break;
            }
            String id = currentParty.memberPos[i];
            if (!id.equals("0")) {
                UnitEnty enty = new UnitEnty();
                String imgPath = DataManager.getMemberData(gameMain.dbAdapter, id).image;
                enty.profile = GLUtil.loadAssetsBitmap(
                        gameMain.appClass, "member/" + imgPath, null, 5);
                enty.id = id;
                enty.num = memberCnt;
                enty.pos = i;
                memberCnt++;
                enty.startAxisX = enty.pos%3*enty.unitW;
                enty.startAxisy = enty.pos/3*enty.unitH+Game_Main.statusBarH;
                enty.curAxisX = enty.startAxisX;
                enty.curAxisY = enty.startAxisy;
                enty.memberEnty = new MemberEnty();
                enty.memberEnty = DataManager.getMemberData(gameMain.dbAdapter, id);
                partyUnitList.add(enty);
            }
        }
    }

    @Override
    public void gStop() {

    }

    @Override
    public void gUpdate() {

    }

    private void memberMove(){

    }

    @Override
    public void gOnTouchEvent(MotionEvent event) {
        if (gameMain.onStatusTouch())
            return;
    }
}
