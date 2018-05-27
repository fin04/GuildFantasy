package com.epriest.game.guildfantasy.main.enty;

import java.util.ArrayList;

public class QuestEnty {

	public String id;
	public int actPartyNum;
	public int actCount;
	public String type;
	public String title;
	public String text;
	public String time;
	public ArrayList<String> textArr = new ArrayList<>();
    public String tip;
	public String dungeon;
	public String image;
	public int rewardGold;
	public int rewardExp;
	public int difficult;
	public int needMember;
	public ButtonEnty btnEnty = new ButtonEnty();
}
