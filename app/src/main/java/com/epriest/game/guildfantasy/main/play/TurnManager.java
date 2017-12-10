package com.epriest.game.guildfantasy.main.play;

import com.epriest.game.guildfantasy.main.Game_Main;
import com.epriest.game.guildfantasy.main.enty.MemberEnty;
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

    private int guildTax = 5;

//    public TurnEnty turnEnty;

    public TurnManager(Game_Main game_main) {
        this.game_main = game_main;
    }

    public void turnCycle(int turn) {
//        turnEnty = new TurnEnty();

        // 완료된 퀘스트는 보상처리, 미완료된 퀘스트는 실패 알림을 띄운다.
        // 모든 퀘스트를 제거한다.
        if (game_main.userEnty.QUESTLIST.size() > 0) {
            QuestLife();
            for(QuestEnty enty : game_main.userEnty.QUESTLIST){
                DataManager.deleteUserQuest(game_main.dbAdapter, enty.id);
            }

        }

        // 사용중인 멤버의 진행
        if (game_main.userEnty.MEMBERLIST.size() > 0)
            MemberLife();

        //db에서 turn에 해당되는 이벤트와 멤버, 퀘스트를 가져온다
        game_main.userEnty = DataManager.setChangeEvent(game_main.dbAdapter, game_main.userEnty);

        DataManager.updateUserInfo(game_main.dbAdapter, game_main.userEnty);

//        PlayEvent(game_main.userEnty.TURN);
//        turnEnty.AP = getAP(game_main.userEnty.LEVEL, game_main.userEnty.TURN);
//        turnEnty.GOLD = getGold(game_main.userEnty.MEMBERLIST.size(), game_main.userEnty.TURN);

        AddQuest();
        game_main.showAlertType = INN.ALERT_TYPE_TURNSTART;
//        game_main.userEnty.isStartTurnAlert = true;
        game_main.userEnty.eventEnty.changeView = true;
        if(game_main.userEnty.eventEnty.ImageList.size() > 0)
            game_main.mainButtonAct(INN.GAME_EVENT, 0);
    }

    private void QuestLife(){

    }

    private void MemberLife(){
        for(MemberEnty enty : game_main.userEnty.MEMBERLIST) {
            //exp가 full이면 level up, max_exp를 갱신.
            if(enty.status.EXP >= enty.status.MAX_EXP){
                enty.status.LEVEL++;
                enty.status.EXP = enty.status.MAX_EXP - enty.status.EXP;
                enty.status.MAX_EXP = mathMaxExp(enty.status.LEVEL);
                DataManager.updateUserMember(game_main.dbAdapter, enty.memberId, enty);
            }
        }
    }

    private int mathMaxExp(int level){
        return (int)(Math.pow((double)level, 2));
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
