package com.epriest.game.guildfantasy.main.enty;

import java.util.ArrayList;

public class QuestEnty {

	public String id;
	public int actPartyNum;
	public int actCount;
	public String type;
	public String title;
	public String text;
	public ArrayList<String> textArr = new ArrayList<>();
    public String tip;
	public String map;
	public String image;
	public int rewardGold;
	public int rewardExp;
	public int difficult;
	public int needMember;
	public String monster1;
	public String monster2;
	public int monsterLV;
	public ButtonEnty btnEnty = new ButtonEnty();
}
