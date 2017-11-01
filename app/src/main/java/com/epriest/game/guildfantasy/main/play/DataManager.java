package com.epriest.game.guildfantasy.main.play;

import android.database.Cursor;

import com.epriest.game.guildfantasy.main.Game_Title;
import com.epriest.game.guildfantasy.main.enty.EquipEnty;
import com.epriest.game.guildfantasy.main.enty.EventEnty;
import com.epriest.game.guildfantasy.main.enty.MemberEnty;
import com.epriest.game.guildfantasy.main.enty.UserEnty;
import com.epriest.game.guildfantasy.main.enty.QuestEnty;
import com.epriest.game.guildfantasy.main.enty.StatusEnty;

import java.util.ArrayList;

/**
 * Created by darka on 2017-04-30.
 */

public class DataManager {

    public static Cursor getUserMainCursor(GameDbAdapter dbAdapter, String playerName) {
        return dbAdapter.getCursor(GameDbAdapter.USERMAIN_TABLE, GameDbAdapter.KEY_USERNAME, playerName);
    }

    public static Cursor getUserMemberCursor(GameDbAdapter dbAdapter, String memberID) {
        return dbAdapter.getCursor(GameDbAdapter.USERMEMBER_TABLE, GameDbAdapter.KEY_MEMBERID, memberID);
    }

    public static Cursor getEventTurnCursor(GameDbAdapter dbAdapter, String turn) {
        return dbAdapter.getCursor(GameDbAdapter.EVENT_TABLE, GameDbAdapter.KEY_EVENTTURN, turn);
    }

    /*public static Cursor getEventQuestCursor(GameDbAdapter dbAdapter, String turn) {
        return dbAdapter.getCursor(GameDbAdapter.EVENT_TABLE, GameDbAdapter.KEY_EVENTTURN, turn);
    }*/

    public static Cursor getQuestCursor(GameDbAdapter dbAdapter, String questID) {
        return dbAdapter.getCursor(GameDbAdapter.QUEST_TABLE, GameDbAdapter.KEY_QUESTID, questID);
    }

    public static Cursor getDBMemberCursor(GameDbAdapter dbAdapter, String memberID) {
        return dbAdapter.getCursor(GameDbAdapter.DB_MEMBER_TABLE, GameDbAdapter.KEY_MEMBERID, memberID);
    }

    public static Cursor getClassCursor(GameDbAdapter dbAdapter, String className) {
        return dbAdapter.getCursor(GameDbAdapter.CLASS_TABLE, GameDbAdapter.KEY_CLASSNAME, className);
    }

    public static Cursor getRaceCursor(GameDbAdapter dbAdapter, String raceName) {
        return dbAdapter.getCursor(GameDbAdapter.RACE_TABLE, GameDbAdapter.KEY_RACENAME, raceName);
    }

    public static int getUserMemberSize(GameDbAdapter dbAdapter, String userName) {
        return dbAdapter.getRowCount(GameDbAdapter.USERMEMBER_TABLE, GameDbAdapter.KEY_USERNAME, userName);
    }

    /**
     * user table에 user 정보를 삽입함
     * @param dbAdapter
     * @param playerName
     * @param Exp
     * @param Level
     * @param Ap
     * @param Turn
     * @param Gold
     * @param GemRed
     * @param GemGreen
     * @param GemBlue
     */
    public static void insertUserData(GameDbAdapter dbAdapter,
                                      String playerName, String Exp, String Level,
                                      String Ap, String Turn, String Gold,
                                      String GemRed, String GemGreen, String GemBlue) {
        String TableName = GameDbAdapter.USERMAIN_TABLE;
        String UserID = ""; //추후 google, facebook과 연동 가능
        String[] columns = {UserID, playerName, Exp, Level, Ap, Turn, Gold, GemRed, GemGreen, GemBlue};
        dbAdapter.insertDATA(TableName, columns);
    }

    /**
     * user table에서 user 정보를 불러옴
     * @return
     */
    public static UserEnty getUserFromData(GameDbAdapter dbAdapter, String playerName) {
        UserEnty enty = new UserEnty();
        Cursor cursor = getUserMainCursor(dbAdapter, playerName);
        enty.Name = cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_USERNAME));
        enty.EXP = Integer.parseInt(cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_USEREXP)));
        enty.LEVEL = Integer.parseInt(cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_USERLEVEL)));
        enty.AP = Integer.parseInt(cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_USERAP)));
        enty.TURN = Integer.parseInt(cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_USERTURN)));
        enty.GOLD = Integer.parseInt(cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_USERGOLD)));
        enty.GEM_RED = Integer.parseInt(cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_USERGEM_RED)));
        enty.GEM_GREEN = Integer.parseInt(cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_USERGEM_GREEN)));
        enty.GEM_BLUE = Integer.parseInt(cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_USERGEM_BLUE)));
        cursor.close();

        ArrayList<MemberEnty> mList = getAllUserMemberList(dbAdapter, playerName);
        enty.eventEnty = new EventEnty();
        enty.eventEnty.MemberIDList = new ArrayList<>();
        for(MemberEnty mEnty : mList){
            enty.eventEnty.MemberIDList.add(mEnty.memberId);
        }
        return enty;
    }

    /**
     * 새로운 user data를 생성함.
     * @return
     */
    public static UserEnty createUserData(GameDbAdapter dbAdapter,
                                          String playerName, int flag) {

        UserEnty enty = new UserEnty();

        switch(flag){
            case Game_Title.STARTGAME_NEWPLAYER:
                enty.Name = playerName;
                enty.EXP = 0;
                enty.LEVEL = 1;
                enty.AP = 2;
                enty.GOLD = 100;
                enty.TURN = 1;
                enty.GEM_RED = 1;
                enty.GEM_GREEN = 1;
                enty.GEM_BLUE = 1;
                insertUserData(dbAdapter, enty.Name, Integer.toString(enty.EXP), Integer.toString(enty.LEVEL),
                        Integer.toString(enty.AP), Integer.toString(enty.TURN), Integer.toString(enty.GOLD),
                        Integer.toString(enty.GEM_RED), Integer.toString(enty.GEM_GREEN), Integer.toString(enty.GEM_BLUE));
                //해당 유저의 모든 멤버데이터 삭제 (리셋)
                dbAdapter.deleteROW(GameDbAdapter.USERMEMBER_TABLE, -1, enty.Name);

                //이벤트는 턴 시작 처음에 실행되기때문에 여기에만 적용
                enty.eventEnty = getEventDataList(dbAdapter, 1);
                break;
            case Game_Title.STARTGAME_LOADPLAYER:
                enty = getUserFromData(dbAdapter, playerName);
                break;
        }

        /*int playerNameCount = dbAdapter.getRowCount(
                GameDbAdapter.USERMAIN_TABLE, GameDbAdapter.KEY_USERNAME, playerName);
        if (playerNameCount == 0) {
            enty.Name = playerName;
            enty.EXP = 0;
            enty.LEVEL = 1;
            enty.AP = 2;
            enty.GOLD = 100;
            enty.TURN = 1;
            enty.GEM_RED = 1;
            enty.GEM_GREEN = 1;
            enty.GEM_BLUE = 1;

            //DB insert
            insertUserData(dbAdapter, enty.Name, Integer.toString(enty.EXP), Integer.toString(enty.LEVEL),
                    Integer.toString(enty.AP), Integer.toString(enty.TURN), Integer.toString(enty.GOLD),
                    Integer.toString(enty.GEM_RED), Integer.toString(enty.GEM_GREEN), Integer.toString(enty.GEM_BLUE));
        } else {
            enty = getUserFromData(dbAdapter, playerName);
        }

        if(flag == Game_Title.STARTGAME_NEWPLAYER) {
            //해당 유저의 모든 멤버데이터 삭제 (리셋)
            dbAdapter.deleteROW(GameDbAdapter.USERMEMBER_TABLE, -1, enty.Name);
        }*/

        ArrayList<MemberEnty> entyList = addMemberListFromMemberDB(dbAdapter, enty.eventEnty.MemberIDList);
        for(MemberEnty memEnty : entyList) {
            insertUserMemberToData(dbAdapter, memEnty, enty.Name);
        }

//        enty.MEMBERLIST = getMemberDataList(dbAdapter, enty.eventEnty.MemberIDList);

        enty.QUESTLIST = getQuestDataList(dbAdapter, enty.eventEnty.QuestIDList);
        enty.PARTY_MEMBERID_LIST = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            enty.PARTY_MEMBERID_LIST.add(null);
        }

        return enty;
    }

    /**
     * db에 user의 crew를 삽입
     * @return
     */
    public static long insertUserMemberToData(GameDbAdapter dbAdapter, MemberEnty enty, String userName){
        String TableName = GameDbAdapter.USERMEMBER_TABLE;
        String[] columns = {enty.memberId, userName, enty.name, enty.engname, enty.sex, enty.age, enty.race, enty.memberclass, enty.mercy, enty.image,
                Integer.toString(enty.iconid), enty.profile, enty.dialog1, Integer.toString(enty.status.LEVEL), Integer.toString(enty.status.EXP),
                Integer.toString(enty.status.HP), Integer.toString(enty.status.MP), Integer.toString(enty.status.AP),
                Integer.toString(enty.status.STR), Integer.toString(enty.status.DEX), Integer.toString(enty.status.INT), Integer.toString(enty.status.VIT),
                Integer.toString(enty.status.RENOWN), Integer.toString(enty.equip.food), enty.equip.equipID_head, enty.equip.equipID_body,
                enty.equip.equipID_etc, enty.equip.equipID_main_arms, enty.equip.equipID_sub_arms, enty.questID};
        long row_id = dbAdapter.insertDATA(TableName, columns);
        return row_id;
    }

    /**
     * db에서 user의 crew를 불러옴
     * @return
     */
    public static MemberEnty getUserMemberFromData(GameDbAdapter dbAdapter, String memberID) {
        MemberEnty enty = new MemberEnty();
        Cursor cursor = getUserMemberCursor(dbAdapter, memberID);
        enty.status = new StatusEnty();
        enty.equip = new EquipEnty();
        enty.name = cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_MEMBERNAME));
        enty.engname = cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_MEMBERENGNAME));
        enty.sex = cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_MEMBERSEX));
        enty.age = cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_MEMBERAGE));
        enty.race = cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_MEMBERRACE));
        enty.memberclass = cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_MEMBERCLASS));
        enty.profile = cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_MEMBERPROFILE));
        enty.dialog1 = cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_MEMBERDIALOG1));
        enty.image = cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_MEMBERIMAGENAME));
        enty.status.EXP = Integer.parseInt(cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_MEMBEREXP)));
        enty.status.LEVEL = Integer.parseInt(cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_MEMBERLEVEL)));
        enty.status.HP = Integer.parseInt(cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_MEMBERHP)));
        enty.status.MP = Integer.parseInt(cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_MEMBERMP)));
        enty.status.AP = Integer.parseInt(cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_MEMBERAP)));
        enty.status.STR = Integer.parseInt(cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_MEMBERSTR)));
        enty.status.DEX = Integer.parseInt(cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_MEMBERDEX)));
        enty.status.INT = Integer.parseInt(cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_MEMBERINT)));
        enty.status.VIT = Integer.parseInt(cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_MEMBERVIT)));
        enty.status.RENOWN = Integer.parseInt(cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_MEMBERRENOWN)));
        enty.equip.food = Integer.parseInt(cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_MEMBERFOOD)));
        enty.equip.equipID_head = cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_MEMBERITEM1));
        enty.equip.equipID_body = cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_MEMBERITEM2));
        enty.equip.equipID_etc = cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_MEMBERITEM3));
        enty.equip.equipID_main_arms = cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_MEMBERARM1));
        enty.equip.equipID_sub_arms = cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_MEMBERARM2));
        cursor.close();
        return enty;
    }

    public static UserEnty setChangeEvent(GameDbAdapter dbAdapter, UserEnty userEnty) {
        userEnty.eventEnty = getEventDataList(dbAdapter, userEnty.TURN);
//        for(String memberID : userEnty.eventEnty.MemberIDList){
//            insertUserMemberToData(dbAdapter, getMemberEntyFromDBMember(dbAdapter, memberID), userEnty.Name);
//        }
//        userEnty.MEMBERLIST.addAll(getMemberDataList(dbAdapter, userEnty.eventEnty.MemberIDList));
        ArrayList<MemberEnty> entyList = addMemberListFromMemberDB(dbAdapter, userEnty.eventEnty.MemberIDList);
        for(MemberEnty enty : entyList) {
            insertUserMemberToData(dbAdapter, enty, userEnty.Name);
        }
        userEnty.QUESTLIST = getQuestDataList(dbAdapter, userEnty.eventEnty.QuestIDList);
        return userEnty;
    }

    /**
     * db에서 turn에 해당하는 event를 불러옴
     * @param dbAdapter
     * @param turn
     * @return
     */
    public static EventEnty getEventDataList(GameDbAdapter dbAdapter, int turn) {
        EventEnty eventEnty = new EventEnty();
        Cursor eventCursor = getEventTurnCursor(dbAdapter, Integer.toString(turn));
        eventEnty.MemberIDList = new ArrayList<>();
        eventEnty.QuestIDList = new ArrayList<>();
        String gold = "";
        eventEnty.ItemList = new ArrayList<>();
        eventEnty.ImageList = new ArrayList<>();
        eventEnty.TextList = new ArrayList<>();

        while (!eventCursor.isAfterLast()) {
            String questId = eventCursor.getString(eventCursor.getColumnIndex(GameDbAdapter.KEY_EVENTQUEST));
            if (questId.equals("") || questId == null) {
            } else {
                eventEnty.QuestIDList.add(questId);
            }
            String memberId = eventCursor.getString(eventCursor.getColumnIndex(GameDbAdapter.KEY_EVENTMEMBER));
            if (memberId.equals("") || memberId == null) {
            } else {
                eventEnty.MemberIDList.add(memberId + "-" + System.currentTimeMillis());
            }
            gold = eventCursor.getString(eventCursor.getColumnIndex(GameDbAdapter.KEY_EVENTGOLD));
            if (gold.equals("") || gold == null) {
                gold = "0";
            }
            eventEnty.Gold += Integer.parseInt(gold);

            String itemId = eventCursor.getString(eventCursor.getColumnIndex(GameDbAdapter.KEY_EVENTITEM));
            if (itemId.equals("") || itemId == null) {
            } else {
                eventEnty.ItemList.add(itemId);
            }
            String ImageName = eventCursor.getString(eventCursor.getColumnIndex(GameDbAdapter.KEY_EVENTIMAGE));
            if (ImageName.equals("") || ImageName == null) {
            } else {
                eventEnty.ImageList.add(ImageName);
            }
            String text = eventCursor.getString(eventCursor.getColumnIndex(GameDbAdapter.KEY_EVENTTEXT));
            if (text.equals("") || text == null) {
            } else {
                eventEnty.TextList.add(text);
            }
            eventCursor.moveToNext();
        }
        eventCursor.close();
        return eventEnty;
    }

    /**
     * db의 member table에서 member정보를 불러옴
     * @return
     */
    public static MemberEnty getMemberEntyFromDBMember(GameDbAdapter dbAdapter, String memberID){
        String id = memberID.split("-")[0];
        MemberEnty memEnty = new MemberEnty();
        Cursor memberCursor = getDBMemberCursor(dbAdapter, id);
        memEnty.db_memberID = id;
        memEnty.memberId = memberID.split("-")[1];
        memEnty.name = memberCursor.getString(memberCursor.getColumnIndex(GameDbAdapter.KEY_MEMBERNAME));
        memEnty.engname = memberCursor.getString(memberCursor.getColumnIndex(GameDbAdapter.KEY_MEMBERENGNAME));
        memEnty.sex = memberCursor.getString(memberCursor.getColumnIndex(GameDbAdapter.KEY_MEMBERSEX));
        memEnty.age = memberCursor.getString(memberCursor.getColumnIndex(GameDbAdapter.KEY_MEMBERAGE));
        memEnty.race = memberCursor.getString(memberCursor.getColumnIndex(GameDbAdapter.KEY_MEMBERRACE));
        memEnty.memberclass = memberCursor.getString(memberCursor.getColumnIndex(GameDbAdapter.KEY_MEMBERCLASS));
        memEnty.mercy = memberCursor.getString(memberCursor.getColumnIndex(GameDbAdapter.KEY_MEMBERMERCY));
        memEnty.image = memberCursor.getString(memberCursor.getColumnIndex(GameDbAdapter.KEY_MEMBERIMAGENAME));
        memEnty.iconid = memberCursor.getInt(memberCursor.getColumnIndex(GameDbAdapter.KEY_MEMBERICONID));
        memEnty.profile = memberCursor.getString(memberCursor.getColumnIndex(GameDbAdapter.KEY_MEMBERPROFILE));
        memEnty.dialog1 = memberCursor.getString(memberCursor.getColumnIndex(GameDbAdapter.KEY_MEMBERDIALOG1));
        memEnty.questID = "";

        memEnty.status = new StatusEnty();
        Cursor classCursor = getClassCursor(dbAdapter, memEnty.memberclass);
        memEnty.status.STR = classCursor.getInt(classCursor.getColumnIndex(GameDbAdapter.KEY_CLASSSTR));
        memEnty.status.DEX = classCursor.getInt(classCursor.getColumnIndex(GameDbAdapter.KEY_CLASSDEX));
        memEnty.status.INT = classCursor.getInt(classCursor.getColumnIndex(GameDbAdapter.KEY_CLASSINT));
        memEnty.status.VIT = classCursor.getInt(classCursor.getColumnIndex(GameDbAdapter.KEY_CLASSVIT));

        Cursor raceCursor = getRaceCursor(dbAdapter, memEnty.race);
        memEnty.status.HP = raceCursor.getInt(raceCursor.getColumnIndex(GameDbAdapter.KEY_RACEHP));
        memEnty.status.MP = raceCursor.getInt(raceCursor.getColumnIndex(GameDbAdapter.KEY_RACEMP));

        memEnty.equip = new EquipEnty();
        memEnty.equip.gold = 0;
        memEnty.equip.food = 0;
        memEnty.equip.equipID_head = "";
        memEnty.equip.equipID_body = "";
        memEnty.equip.equipID_etc = "";
        memEnty.equip.equipID_main_arms = "";
        memEnty.equip.equipID_sub_arms = "";

        classCursor.close();
        raceCursor.close();
        memberCursor.close();
        return memEnty;
    }

    /**
     * db의 member table에서 member 리스트를 불러옴
     * @return
     */
    public static ArrayList<MemberEnty> addMemberListFromMemberDB(GameDbAdapter dbAdapter, ArrayList<String> eventMemberId) {
        ArrayList<MemberEnty> entyList = new ArrayList<>();
        for (String memberID : eventMemberId) {
            entyList.add(getMemberEntyFromDBMember(dbAdapter, memberID));
        }
        return entyList;
    }

    /**
     * user의 모든 member 리스트를 불러옴
     * @param dbAdapter
     * @param username
     * @return
     */
    public static ArrayList<MemberEnty> getAllUserMemberList(GameDbAdapter dbAdapter, String username) {
        ArrayList<MemberEnty> entyList = new ArrayList<>();
//        int userMemberSize = DataManager.getUserMemberSize(dbAdapter, username);
        Cursor cursor = dbAdapter.getCursor(GameDbAdapter.USERMEMBER_TABLE, GameDbAdapter.KEY_USERNAME, username);
        if(cursor!=null) {
            cursor.moveToFirst();
            do {
                MemberEnty memEnty = new MemberEnty();
                memEnty.memberId = cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_MEMBERID));
                memEnty.name = cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_MEMBERNAME));
                memEnty.engname = cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_MEMBERENGNAME));
                memEnty.sex = cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_MEMBERSEX));
                memEnty.age = cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_MEMBERAGE));
                memEnty.race = cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_MEMBERRACE));
                memEnty.memberclass = cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_MEMBERCLASS));
                memEnty.mercy = cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_MEMBERMERCY));
                memEnty.image = cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_MEMBERIMAGENAME));
                memEnty.iconid = cursor.getInt(cursor.getColumnIndex(GameDbAdapter.KEY_MEMBERICONID));
                memEnty.profile = cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_MEMBERPROFILE));
                memEnty.dialog1 = cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_MEMBERDIALOG1));

                memEnty.status = new StatusEnty();
                Cursor classCursor = getClassCursor(dbAdapter, memEnty.memberclass);
                memEnty.status.STR = classCursor.getInt(classCursor.getColumnIndex(GameDbAdapter.KEY_CLASSSTR));
                memEnty.status.DEX = classCursor.getInt(classCursor.getColumnIndex(GameDbAdapter.KEY_CLASSDEX));
                memEnty.status.INT = classCursor.getInt(classCursor.getColumnIndex(GameDbAdapter.KEY_CLASSINT));
                memEnty.status.VIT = classCursor.getInt(classCursor.getColumnIndex(GameDbAdapter.KEY_CLASSVIT));
                classCursor.close();;

                Cursor raceCursor = getRaceCursor(dbAdapter, memEnty.race);
                memEnty.status.HP = raceCursor.getInt(raceCursor.getColumnIndex(GameDbAdapter.KEY_RACEHP));
                memEnty.status.MP = raceCursor.getInt(raceCursor.getColumnIndex(GameDbAdapter.KEY_RACEMP));
                raceCursor.close();
                entyList.add(memEnty);
            } while (cursor.moveToNext()) ;
            cursor.close();
        }
        return entyList;
    }

    /**
     * quest table에서 quest 리스트를 불러옴
     * @param dbAdapter
     * @param eventQuest
     * @return
     */
    public static ArrayList<QuestEnty> getQuestDataList(GameDbAdapter dbAdapter, ArrayList<String> eventQuest) {
        ArrayList<QuestEnty> entyList = new ArrayList<>();
        for (String questId : eventQuest) {
            Cursor questCursor = getQuestCursor(dbAdapter, questId);
            QuestEnty enty = new QuestEnty();
            enty.id = questCursor.getString(questCursor.getColumnIndex(GameDbAdapter.KEY_QUESTID));
            enty.title = questCursor.getString(questCursor.getColumnIndex(GameDbAdapter.KEY_QUESTTITLE));
            enty.type = questCursor.getString(questCursor.getColumnIndex(GameDbAdapter.KEY_QUESTTYPE));
            enty.difficult = questCursor.getInt(questCursor.getColumnIndex(GameDbAdapter.KEY_QUESTDIFFICULT));
            enty.needMember = questCursor.getInt(questCursor.getColumnIndex(GameDbAdapter.KEY_QUESTNEEDMEMBER));
            enty.image = questCursor.getString(questCursor.getColumnIndex(GameDbAdapter.KEY_QUESTIMAGENAME));
            enty.text = questCursor.getString(questCursor.getColumnIndex(GameDbAdapter.KEY_QUESTTEXT));
            enty.rewardGold = questCursor.getInt(questCursor.getColumnIndex(GameDbAdapter.KEY_QUESTREWARD_GOLD));
            enty.rewardExp = questCursor.getInt(questCursor.getColumnIndex(GameDbAdapter.KEY_QUESTREWARD_EXP));
            enty.monster1 = questCursor.getString(questCursor.getColumnIndex(GameDbAdapter.KEY_QUESTMONSTER1));
            enty.monster2 = questCursor.getString(questCursor.getColumnIndex(GameDbAdapter.KEY_QUESTMONSTER2));
            enty.monsterLV = questCursor.getInt(questCursor.getColumnIndex(GameDbAdapter.KEY_QUESTMONSTERLV));
            questCursor.close();
            entyList.add(enty);
        }
        return entyList;
    }
}
