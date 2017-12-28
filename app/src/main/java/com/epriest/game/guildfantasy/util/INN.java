package com.epriest.game.guildfantasy.util;

/**
 * Created by darka on 2017-06-29.
 */

public interface INN {
    public final static int GAME_INTRO = 0;
    public final static int GAME_HOME = 1;
    public final static int GAME_MEMBER = 2;
    public final static int GAME_RECRUIT = 3;
    public final static int GAME_SKILL = 4;
    public final static int GAME_ITEM = 5;
    public final static int GAME_QUEST = 6;
    public final static int GAME_MOVE = 7;
    public final static int GAME_EVENT = 8;
    public final static int GAME_PARTY = 9;
    public final static int GAME_OPTION = 10;

    public final static int MODE_DEFAULT = 0;
    public final static int MODE_PARTY_SELECT = 1;
    public final static int MODE_MEMBER_PARTY = 2;
    public final static int MODE_MEMBER_SELECT = 3;

    public final static int MAPTILE_VILLAGE = 12;
    public final static int MAPTILE_TOWN = 13;
    public final static int MAPTILE_CASTLE = 14;
    public final static int MAPTILE_FORT = 15;
    public final static int MAPTILE_MINE = 16;
    public final static int MAPTILE_RUIN = 17;
    public final static int MAPTILE_NEST = 18;

    public final static String CLASS_KNIGHT = "Knight";
    public final static String CLASS_WARRIOR = "Warrior";
    public final static String CLASS_CLERIC = "Cleric";
    public final static String CLASS_MAGE = "Mage";
    public final static String CLASS_RANGER = "Ranger";
    public final static String CLASS_ROGUE = "Rogue";
    public final static String[] classNameArr = {"All", CLASS_KNIGHT, CLASS_WARRIOR, CLASS_CLERIC, CLASS_MAGE, CLASS_RANGER, CLASS_ROGUE};

    public final static String MENU_INN = "Inn";
    public final static String MENU_BAR = "Bar";
    public final static String MENU_GUILD = "Guild";
    public final static String MENU_SHOP = "Shop";
    public final static String MENU_TEMPLE = "Temple";
    public final static String MENU_GATE = "Gate";
    public final static String[] menuIconName = {MENU_INN, MENU_BAR, MENU_GUILD, MENU_SHOP, MENU_TEMPLE, MENU_GATE};
    public final static int[] menuIconNum = {4, 1, 0, 3, 2, 5};

    //    public final static int statusBarH = 45;
    public final static String[] tileName = {
            "Field", "Hill", "Mountine", "Sand", "Forest",
            "River", "Swamp","Sea","Icefield", "Icehill",
            "Glacier","", "Village","Town","Castle",
            "Fort","Mine","Ruin","Nest",""};

    public final static int USER_START_EXP = 0;
    public final static int USER_START_LV = 1;
    public final static int USER_START_AP = 2;
    public final static int USER_START_GOLD = 100;
    public final static int USER_START_TURN = 1;
    public final static int USER_START_GEM = 1;

    public final static int CREATE_PLAYER_LIMITED = 3;

    public final static boolean setTempImg = false;
}
