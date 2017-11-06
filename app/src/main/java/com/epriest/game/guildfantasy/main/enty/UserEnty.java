package com.epriest.game.guildfantasy.main.enty;

import java.util.ArrayList;

/**
 * Created by darka on 2017-03-25.
 */

public class UserEnty {
    public String Name;
    public int EXP;
    public int LEVEL;
    public int AP;
    public int GOLD;
    public int TURN;
    public int GEM_RED;
    public int GEM_GREEN;
    public int GEM_BLUE;
    public boolean isStartTurnAlert;
    public EventEnty eventEnty;
    public ArrayList<QuestEnty> QUESTLIST;
    public ArrayList<MemberEnty> MEMBERLIST;
//    public ArrayList<String> PARTY_MEMBERID_LIST;
//    public ArrayList<PartyEnty> PARTYLIST;
}
