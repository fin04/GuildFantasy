package com.epriest.game.guildfantasy.main.play;

import android.database.Cursor;

import com.epriest.game.guildfantasy.main.Game_Title;
import com.epriest.game.guildfantasy.main.enty.EquipEnty;
import com.epriest.game.guildfantasy.main.enty.EventEnty;
import com.epriest.game.guildfantasy.main.enty.MemberEnty;
import com.epriest.game.guildfantasy.main.enty.UserEnty;
import com.epriest.game.guildfantasy.main.enty.QuestEnty;
import com.epriest.game.guildfantasy.main.enty.StatusEnty;
import com.epriest.game.guildfantasy.util.INN;

import java.util.ArrayList;

/**
 * Created by darka on 2017-04-30.
 */

public class DataManager {

    /**
     * User의 cursor를 찾음
     * @param playerName - 플레이어 이름, null이면 모든 플레이어를 찾음
     * @return
     */
    public static Cursor getUserMainCursor(GameDbAdapter dbAdapter, String playerName) {
        return dbAdapter.getCursor(GameDbAdapter.PLAYER_MAIN_TABLE, GameDbAdapter.KEY_USERNAME, playerName);
    }

    /**
     * memberID로 user 소속의 멤버 cursor를 찾음
     * @param memberID - null이면 모든 멤버를 찾음
     * @return
     */
    public static Cursor getUserMemberCursor(GameDbAdapter dbAdapter, String memberID) {
        return dbAdapter.getCursor(GameDbAdapter.PLAYER_MEMBER_TABLE, GameDbAdapter.KEY_MEMBERID, memberID);
    }

    /**
     * questID user의 퀘스트 cursor를 찾음
     * @param questID - null이면 모든 퀘스트를 찾음
     * @return
     */
    public static Cursor getUserQuestCursor(GameDbAdapter dbAdapter, String questID) {
        return dbAdapter.getCursor(GameDbAdapter.PLAYER_QUEST_TABLE, GameDbAdapter.KEY_QUESTID, questID);
    }

    /**
     * DB에서 해당 turn의 이벤트 cursor를 찾음
     * @return
     */
    public static Cursor getEventCursorOfTurn(GameDbAdapter dbAdapter, String turn) {
        return dbAdapter.getCursor(GameDbAdapter.EVENT_TABLE, GameDbAdapter.KEY_EVENTTURN, turn);
    }

    /*public static Cursor getEventQuestCursor(GameDbAdapter dbAdapter, String turn) {
        return dbAdapter.getCursor(GameDbAdapter.EVENT_TABLE, GameDbAdapter.KEY_EVENTTURN, turn);
    }*/

    /**
     * questID로 DB에서 퀘스트 cursor를 찾음
     * @param questID
     * @return
     */
    public static Cursor getDBQuestCursor(GameDbAdapter dbAdapter, String questID) {
        return dbAdapter.getCursor(GameDbAdapter.QUEST_TABLE, GameDbAdapter.KEY_QUESTID, questID);
    }

    /**
     * memberID로 DB에서 멤버 cursor를 찾음
     * @param memberID
     * @return
     */
    public static Cursor getDBMemberCursor(GameDbAdapter dbAdapter, String memberID) {
        return dbAdapter.getCursor(GameDbAdapter.DB_MEMBER_TABLE, GameDbAdapter.KEY_MEMBERID, memberID);
    }

    /**
     * grade로 DB에서 멤버 cursor를 찾음
     * @param grade
     * @return
     */
    public static Cursor getGradeDBMemberCursor(GameDbAdapter dbAdapter, String grade){
        return dbAdapter.getCursor(GameDbAdapter.DB_MEMBER_TABLE, GameDbAdapter.KEY_MEMBERGRADE, grade);
    }

    /**
     * DB에서 해당 직업의 cursor를 찾음
     * @param className
     * @return
     */
    public static Cursor getClassCursor(GameDbAdapter dbAdapter, String className) {
        return dbAdapter.getCursor(GameDbAdapter.CLASS_TABLE, GameDbAdapter.KEY_CLASSNAME, className);
    }

    /**
     * DB에서 해당 종족의 cursor를 찾음
     * @param raceName
     * @return
     */
    public static Cursor getRaceCursor(GameDbAdapter dbAdapter, String raceName) {
        return dbAdapter.getCursor(GameDbAdapter.RACE_TABLE, GameDbAdapter.KEY_RACENAME, raceName);
    }

    /**
     * 유저 길드의 전체 멤버 수를 반환
     * @param userName
     * @return
     */
    public static int getUserMemberSize(GameDbAdapter dbAdapter, String userName) {
        return dbAdapter.getRowCount(GameDbAdapter.PLAYER_MEMBER_TABLE, GameDbAdapter.KEY_USERNAME, userName);
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
                enty.EXP = INN.USER_START_EXP;
                enty.LEVEL = INN.USER_START_LV;
                enty.AP = INN.USER_START_AP;
                enty.GOLD = INN.USER_START_GOLD;
                enty.TURN = INN.USER_START_TURN;
                enty.GEM_RED = INN.USER_START_GEM;
                enty.GEM_GREEN = INN.USER_START_GEM;
                enty.GEM_BLUE = INN.USER_START_GEM;
                insertUserInfo(dbAdapter, enty);

                // 0turn에 해당하는 이벤트, 멤버, 퀘스트 가져옴
                enty = setChangeEvent(dbAdapter, enty);
                break;
            case Game_Title.STARTGAME_LOADPLAYER:
                enty = getUserInfo(dbAdapter, playerName);
                enty.MEMBERLIST = getUserMemberList(dbAdapter, playerName);
                enty.QUESTLIST = getUserQuestList(dbAdapter, playerName);
                break;
        }


//        enty.PARTY_MEMBERID_LIST = new ArrayList<>();
//        for (int i = 0; i < 16; i++) {
//            enty.PARTY_MEMBERID_LIST.add(null);
//        }

        return enty;
    }

    /**
     * 지정된 user data를 모두 삭제함
     * @return
     */
    public static boolean deleteUserData(GameDbAdapter dbAdapter, String playerName) {
        boolean delUser = dbAdapter.deleteROW(GameDbAdapter.PLAYER_MAIN_TABLE, -1, playerName);
        boolean delMember = dbAdapter.deleteROW(GameDbAdapter.PLAYER_MEMBER_TABLE, -1, playerName);
        boolean delQuest = dbAdapter.deleteROW(GameDbAdapter.PLAYER_QUEST_TABLE, -1, playerName);
        if(!delUser || !delMember || !delQuest)
            return false;
        else
            return true;
    }

    /**
     * user table에 user 정보를 삽입함
     */
    public static void insertUserInfo(GameDbAdapter dbAdapter, UserEnty enty) {

        String TableName = GameDbAdapter.PLAYER_MAIN_TABLE;
        String UserID = "0"; //추후 google, facebook과 연동 가능
        String[] columns = {UserID, enty.Name, Integer.toString(enty.EXP), Integer.toString(enty.LEVEL),
                Integer.toString(enty.AP), Integer.toString(enty.TURN), Integer.toString(enty.GOLD),
                Integer.toString(enty.GEM_RED), Integer.toString(enty.GEM_GREEN), Integer.toString(enty.GEM_BLUE)};
        dbAdapter.insertDATA(TableName, columns);
    }

    /**
     * userDB에에 member를 삽입
     * @return
     */
    public static long insertUserMember(GameDbAdapter dbAdapter, MemberEnty enty, String userName){
        String TableName = GameDbAdapter.PLAYER_MEMBER_TABLE;
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
     * userDB에 quest를 삽입
     * @return
     */
    public static long insertUserQuest(GameDbAdapter dbAdapter, QuestEnty enty, String userName){
        String TableName = GameDbAdapter.PLAYER_QUEST_TABLE;
        String[] columns = {enty.id, userName, enty.title, enty.type, enty.map,
                enty.monster1, enty.monster2, Integer.toString(enty.difficult), Integer.toString(enty.monsterLV),
                Integer.toString(enty.needMember), Integer.toString(enty.rewardGold), Integer.toString(enty.rewardExp),
                enty.image, enty.text};

        long row_id = dbAdapter.insertDATA(TableName, columns);
        return row_id;
    }


    /**
     * UserDB에서 user 정보를 UserEnty로 불러옴
     * @return
     */
    public static UserEnty getUserInfo(GameDbAdapter dbAdapter, String userName) {
        UserEnty enty = new UserEnty();
        Cursor cursor = getUserMainCursor(dbAdapter, userName);
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

        //길드의 멤버 List를 userEnty에 삽입
//        enty.MEMBERLIST= getUserMemberList(dbAdapter, userName);
//        enty.eventEnty = new EventEnty();
//        enty.eventEnty.MemberIDList = new ArrayList<>();
//        for(MemberEnty mEnty : mList){
//            enty.eventEnty.MemberIDList.add(mEnty.memberId);
//        }
        return enty;
    }

    /**
     * userDB에서 member enty를 불러옴
     * @return
     */
    public static MemberEnty getUserMemberEnty(GameDbAdapter dbAdapter, String memberID) {
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

    /**
     * user 길드의 모든 멤버의 리스트를 불러옴
     * @param username
     * @return
     */
    public static ArrayList<MemberEnty> getUserMemberList(GameDbAdapter dbAdapter, String username) {
        ArrayList<MemberEnty> entyList = new ArrayList<>();
//        int userMemberSize = DataManager.getGuildMemberSize(dbAdapter, username);
        Cursor cursor = dbAdapter.getCursor(GameDbAdapter.PLAYER_MEMBER_TABLE, GameDbAdapter.KEY_USERNAME, username);
        if(cursor!=null) {
            cursor.moveToFirst();
            do {
                MemberEnty memEnty = getUserMemberEnty(dbAdapter, cursor.getString(
                        cursor.getColumnIndex(GameDbAdapter.KEY_MEMBERID)));
                entyList.add(memEnty);
            } while (cursor.moveToNext()) ;
            cursor.close();
        }
        return entyList;
    }

    public static ArrayList<QuestEnty> getUserQuestList(GameDbAdapter dbAdapter, String username) {
        ArrayList<QuestEnty> entyList = new ArrayList<>();
        Cursor questCursor = dbAdapter.getCursor(GameDbAdapter.PLAYER_QUEST_TABLE, GameDbAdapter.KEY_USERNAME, username);
        if(questCursor!=null) {
            questCursor.moveToFirst();
            do {
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
                entyList.add(enty);
            } while (questCursor.moveToNext()) ;
            questCursor.close();
        }
        return entyList;
    }

    /**
     * db에서 turn에 해당하는 event를 불러옴
     * @return
     */
    public static EventEnty getEventData(GameDbAdapter dbAdapter, int turn) {
        EventEnty eventEnty = new EventEnty();
        Cursor eventCursor = getEventCursorOfTurn(dbAdapter, Integer.toString(turn));
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
     * DB의 member table에서 멤버 정보를 불러옴
     * @return
     */
    public static MemberEnty getMemberData(GameDbAdapter dbAdapter, String memberID){
        String id = memberID.split("-")[0];
        MemberEnty memEnty = new MemberEnty();
        Cursor memberCursor = getDBMemberCursor(dbAdapter, id);
        memEnty.db_memberID = id;
        memEnty.memberId = memberID;
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
     * memberID List의 모든 멤버를 DB에서 찾아 MemberEnty List로 변환
     * @return
     */
    public static ArrayList<MemberEnty> getMemberDataList(GameDbAdapter dbAdapter, ArrayList<String> memberIdList) {
        ArrayList<MemberEnty> entyList = new ArrayList<>();
        for (String memberID : memberIdList) {
            entyList.add(getMemberData(dbAdapter, memberID));
        }
        return entyList;
    }

    /**
     * grade에 해당하는 모든 멤버를 DB에서 찾아 List로 변환
     * @param grade
     * @return
     */
    public static ArrayList<MemberEnty> getGradeMemberDataList(GameDbAdapter dbAdapter, String grade){
        ArrayList<MemberEnty> entyList = new ArrayList<>();
        Cursor cursor = getGradeDBMemberCursor(dbAdapter, grade);
        do {
            MemberEnty memEnty = getMemberData(dbAdapter, cursor.getString(
                    cursor.getColumnIndex(GameDbAdapter.KEY_MEMBERID)));
            entyList.add(memEnty);
        } while (cursor.moveToNext()) ;
        cursor.close();
        return entyList;
    }

    public static QuestEnty getQuestData(GameDbAdapter dbAdapter, String questID) {
        Cursor questCursor = getDBQuestCursor(dbAdapter, questID);
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
        return enty;
    }

    /**
     * quest table에서 quest 리스트를 불러옴
     * @return
     */
    public static ArrayList<QuestEnty> getQuestDataList(GameDbAdapter dbAdapter, ArrayList<String> eventQuest) {
        ArrayList<QuestEnty> entyList = new ArrayList<>();
        for (String questId : eventQuest) {
            entyList.add(getQuestData(dbAdapter, questId));
        }
        return entyList;
    }

    /**
     * 현재 턴의 Event의 member와 quest List를 불러옴
     * @param userEnty
     * @return
     */
    public static UserEnty setChangeEvent(GameDbAdapter dbAdapter, UserEnty userEnty) {
        userEnty.eventEnty = getEventData(dbAdapter, userEnty.TURN);

        userEnty.MEMBERLIST = getMemberDataList(dbAdapter, userEnty.eventEnty.MemberIDList);
        for(MemberEnty enty : userEnty.MEMBERLIST) {
            insertUserMember(dbAdapter, enty, userEnty.Name);
        }
        userEnty.QUESTLIST = getQuestDataList(dbAdapter, userEnty.eventEnty.QuestIDList);
        for(QuestEnty enty : userEnty.QUESTLIST){
            insertUserQuest(dbAdapter, enty, userEnty.Name);
        }
        return userEnty;
    }

    /**
     * user의 table을 모두 삭제한다.
     */
    public static boolean deleteUserTable(GameDbAdapter dbAdapter, String table, String playerName){
        return dbAdapter.deleteROW(table, -1, playerName);
    }









}
