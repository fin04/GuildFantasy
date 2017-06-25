package com.epriest.game.guildfantasy.main.play;

import com.epriest.game.guildfantasy.main.enty.QuestEnty;
import com.epriest.game.guildfantasy.main.Game_Main;

import java.util.ArrayList;

/**
 * Created by darka on 2017-04-23.
 */

public class TurnManager {

    public class TurnEnty{
        public int AP;
        public int GOLD;
        public ArrayList<QuestEnty> questEnty = new ArrayList<>();
    }

    private Game_Main game_main;

    private int guildTax = 30;

//    public TurnEnty turnEnty;

    public TurnManager(Game_Main game_main) {
        this.game_main = game_main;
    }

    public void turnCycle(int turn) {
        game_main.playerEnty.TURN += turn;
//        turnEnty = new TurnEnty();

        // 실행중인 퀘스트 진행
        if (game_main.playerEnty.QUESTLIST.size() > 0)
            QuestLife();

        // 사용중인 멤버의 진행
        if (game_main.playerEnty.MEMBERLIST.size() > 0)
            MemberLife();

        //db에서 turn data 가져옴
        game_main.playerEnty = DataManager.setChangeEvent(game_main.dbAdapter, game_main.playerEnty);
        game_main.playerEnty.GOLD += game_main.playerEnty.eventEnty.Gold;

//        PlayEvent(game_main.playerEnty.TURN);
//        turnEnty.AP = getAP(game_main.playerEnty.LEVEL, game_main.playerEnty.TURN);
//        turnEnty.GOLD = getGold(game_main.playerEnty.MEMBERLIST.size(), game_main.playerEnty.TURN);

        AddQuest();
        game_main.playerEnty.isStartTurnAlert = true;
        game_main.playerEnty.eventEnty.changeView = true;
        if(game_main.playerEnty.eventEnty.ImageList.size() > 0)
            game_main.mainButtonAct(game_main.GAME_EVENT, 0);
    }

    private void QuestLife(){

    }

    private void MemberLife(){

    }

    private void PlayEvent(int turn){

    }

    private int getAP(int level, int turn){
        if(turn == 1)
            return 30;
        int ap = (int) ((level/5) * 1.5f + 10);
        return ap;
    }

    private int getGold(int member, int turn){
        if(turn == 1)
            return 300;
        int gold = member * guildTax;
        return gold;
    }

    private void AddQuest(){

    }
}
