package com.epriest.game.guildfantasy.util;

/**
 * Created by darka on 2017-06-29.
 */

public interface INN {
    public final static int GAME_INTRO = 0;
    public final static int GAME_HOME = 1;
    public final static int GAME_MEMBER = 2;
    public final static int GAME_RECRUIT = 3;
    public final static int GAME_TEMPLE = 4;
    public final static int GAME_SHOP = 5;
    public final static int GAME_QUESTLIST = 6;
    public final static int GAME_MOVE = 7;
    public final static int GAME_EVENT = 8;
    public final static int GAME_PARTY = 9;
    public final static int GAME_OPTION = 10;
    public final static int GAME_MEMBER_FROM_PARTY = 11;
    public final static int GAME_QUEST = 12;
    public final static int GAME_STAGE = 13;
    public final static int GAME_BATTLE = 14;
    public final static int GAME_MEMBER_FROM_DUNGEON = 15;

//    public final static int MODE_DEFAULT = 0;
//    public final static int MODE_PARTY_SELECT = 1;
//    public final static int MODE_MEMBER_PARTY = 2;
//    public final static int MODE_MEMBER_SELECT = 3;

    public final static String CLASS_KNIGHT = "Knight";
    public final static String CLASS_WARRIOR = "Warrior";
    public final static String CLASS_CLERIC = "Cleric";
    public final static String CLASS_MAGE = "Mage";
    public final static String CLASS_RANGER = "Ranger";
    public final static String CLASS_ROGUE = "Rogue";
    public final static String[] classNameArr = {"All", CLASS_KNIGHT, CLASS_WARRIOR, CLASS_CLERIC, CLASS_MAGE, CLASS_RANGER, CLASS_ROGUE};

    public final static String MENU_OPTION = "option";
    public final static String MENU_TURNEND = "turn";
    public final static String MENU_BACK = "back";

    public final static String MENU_INN = "Inn";
    public final static String MENU_BAR = "Bar";
    public final static String MENU_GUILD = "Guild";
    public final static String MENU_SHOP = "Shop";
    public final static String MENU_TEMPLE = "Temple";
    public final static String MENU_GATE = "Gate";
    public final static String[] menuIconName = {MENU_INN, MENU_BAR, MENU_GUILD, MENU_SHOP, MENU_TEMPLE, MENU_GATE};
    public final static int[] menuIconNum = {4, 1, 0, 3, 2, 5};

    public final static String[] tileName = {
            "Field", "Hill", "Mountine", "Sand", "Forest",
            "Fountain", "Swamp","Sea","Icefield", "Icehill",
            "Iceberg","", "Village","Town","Castle",
            "Fort","Mine","Ruin","Nest",""};

    public final static int USER_START_EXP = 0;
    public final static int USER_START_LV = 1;
    public final static int USER_START_AP = 2;
    public final static int USER_START_GOLD = 100;
    public final static int USER_START_TURN = 1;
    public final static int USER_START_GEM = 1;

    public final static int CREATE_PLAYER_LIMITED = 3;

    public final static boolean setTempImg = false;

    public final static int TILETYPE_FIELD = 0;
    public final static int TILETYPE_HILL = 1;
    public final static int TILETYPE_MOUNTINE = 2;
    public final static int TILETYPE_SAND = 3;
    public final static int TILETYPE_FOREST = 4;
    public final static int TILETYPE_FOUNTAIN = 5;
    public final static int TILETYPE_SWAMP = 6;
    public final static int TILETYPE_SEA = 7;
    public final static int TILETYPE_ICEFIELD = 8;
    public final static int TILETYPE_ICEHILL = 9;
    public final static int TILETYPE_ICEBERG = 10;
    public final static int TILETYPE_CHEST = 12;
    public final static int TILETYPE_TRAP = 13;
    public final static int TILETYPE_POISON = 14;
    public final static int TILETYPE_REST = 15;
    public final static int TILETYPE_GATE = 16;
    public final static int TILETYPE_MON2 = 17;
    public final static int TILETYPE_MON1 = 18;
    public final static int TILETYPE_BOSS = 19;

    public final static String TILEATTR_EARTH = "대지";
    public final static String TILEATTR_WATER = "물";
    public final static String TILEATTR_WIND = "바람";
    public final static String TILEATTR_FIRE = "불";
    public final static String TILEATTR_TREE = "숲";
    public final static String TILEATTR_ICE = "얼음";

}
