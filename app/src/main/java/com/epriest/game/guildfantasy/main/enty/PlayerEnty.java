package com.epriest.game.guildfantasy.main.enty;

import java.util.ArrayList;

/**
 * Created by darka on 2017-03-25.
 */

public class PlayerEnty {
    public String Name;
    public int LEVEL;
    public int AP;
    public int GOLD;
    public int TURN;
    public boolean isStartTurnAlert;
    public EventEnty eventEnty;
    public ArrayList<QuestEnty> QUESTLIST;
    public ArrayList<MemberEnty> MEMBERLIST;
    public ArrayList<String> PARTYMemberIdLIST;
//    public ArrayList<PartyEnty> PARTYLIST;
}
