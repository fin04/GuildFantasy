package com.epriest.game.guildfantasy.main.play;

import com.epriest.game.guildfantasy.main.Game_Main;
import com.epriest.game.guildfantasy.main.enty.QuestEnty;
import com.epriest.game.guildfantasy.util.INN;

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
        game_main.userEnty.TURN += turn;
//        turnEnty = new TurnEnty();

        // 실행중인 퀘스트 진행
        if (game_main.userEnty.QUESTLIST.size() > 0)
            QuestLife();

        // 사용중인 멤버의 진행
        if (game_main.userEnty.MEMBERLIST.size() > 0)
            MemberLife();

        //db에서 turn data 가져옴
        game_main.userEnty = DataManager.setChangeEvent(game_main.dbAdapter, game_main.userEnty);
        game_main.userEnty.GOLD += game_main.userEnty.eventEnty.Gold;

//        PlayEvent(game_main.userEnty.TURN);
//        turnEnty.AP = getAP(game_main.userEnty.LEVEL, game_main.userEnty.TURN);
//        turnEnty.GOLD = getGold(game_main.userEnty.MEMBERLIST.size(), game_main.userEnty.TURN);

        AddQuest();
        game_main.userEnty.isStartTurnAlert = true;
        game_main.userEnty.eventEnty.changeView = true;
        if(game_main.userEnty.eventEnty.ImageList.size() > 0)
            game_main.mainButtonAct(INN.GAME_EVENT, 0);
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
