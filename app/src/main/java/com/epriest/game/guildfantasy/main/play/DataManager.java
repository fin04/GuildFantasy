package com.epriest.game.guildfantasy.main.play;

import android.content.ContentValues;
import android.database.Cursor;

import com.epriest.game.guildfantasy.main.Game_Main;
import com.epriest.game.guildfantasy.main.Game_Title;
import com.epriest.game.guildfantasy.main.enty.EquipEnty;
import com.epriest.game.guildfantasy.main.enty.EventEnty;
import com.epriest.game.guildfantasy.main.enty.MemberEnty;
import com.epriest.game.guildfantasy.main.enty.PartyEnty;
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
     * @return cursor
     */
    public static Cursor getUserMainCursor(GameDbAdapter dbAdapter, String playerName) {
        return dbAdapter.getCursor(GameDbAdapter.PLAYER_MAIN_TABLE, GameDbAdapter.KEY_USERNAME, playerName);
    }

    /**
     * memberID로 user 소속의 멤버 cursor를 찾음
     * @param memberID - null이면 모든 멤버를 찾음
     * @return cursor
     */
    public static Cursor getUserMemberCursor(GameDbAdapter dbAdapter, String memberID) {
        return dbAdapter.getCursor(GameDbAdapter.PLAYER_MEMBER_TABLE, GameDbAdapter.KEY_MEMBERID, memberID);
    }

    /**
     * questID user의 퀘스트 cursor를 찾음
     * @param questID - null이면 모든 퀘스트를 찾음
     * @return cursor
     */
    public static Cursor getUserQuestCursor(GameDbAdapter dbAdapter, String questID) {
        return dbAdapter.getCursor(GameDbAdapter.PLAYER_QUEST_TABLE, GameDbAdapter.KEY_QUESTID, questID);
    }

    /**
     * partyID user의 파티 cursor를 찾음
     * @param partyID - null이면 모든 퀘스트를 찾음
     * @return cursor
     */
    public static Cursor getPartyCursorFromId(GameDbAdapter dbAdapter, String partyID) {
        return dbAdapter.getCursor(GameDbAdapter.PLAYER_PARTY_TABLE, GameDbAdapter.KEY_PARTYID, partyID);
    }

    /**
     * username으로 party cursor를 찾음
     * @param username
     * @return cursor
     */
    public static Cursor getPartyCursorFromUserName(GameDbAdapter dbAdapter, String username) {
        return dbAdapter.getCursor(GameDbAdapter.QUEST_TABLE, GameDbAdapter.KEY_USERNAME, username);
    }


    /**
     * DB에서 해당 turn의 이벤트 cursor를 찾음
     * @return cursor
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
     * @return cursor
     */
    public static Cursor getDBQuestCursor(GameDbAdapter dbAdapter, String questID) {
        return dbAdapter.getCursor(GameDbAdapter.QUEST_TABLE, GameDbAdapter.KEY_QUESTID, questID);
    }

    /**
     * memberID로 DB에서 멤버 cursor를 찾음
     * @param memberID
     * @return cursor
     */
    public static Cursor getDBMemberCursor(GameDbAdapter dbAdapter, String memberID) {
        return dbAdapter.getCursor(GameDbAdapter.DB_MEMBER_TABLE, GameDbAdapter.KEY_MEMBERID, memberID);
    }

    /**
     * grade로 DB에서 멤버 cursor를 찾음
     * @param grade
     * @return cursor
     */
    public static Cursor getGradeDBMemberCursor(GameDbAdapter dbAdapter, String grade){
        return dbAdapter.getCursor(GameDbAdapter.DB_MEMBER_TABLE, GameDbAdapter.KEY_MEMBERGRADE, grade);
    }

    /**
     * DB에서 해당 직업의 cursor를 찾음
     * @param className
     * @return cursor
     */
    public static Cursor getClassCursor(GameDbAdapter dbAdapter, String className) {
        return dbAdapter.getCursor(GameDbAdapter.CLASS_TABLE, GameDbAdapter.KEY_CLASSNAME, className);
    }

    /**
     * DB에서 해당 종족의 cursor를 찾음
     * @param raceName
     * @return cursor
     */
    public static Cursor getRaceCursor(GameDbAdapter dbAdapter, String raceName) {
        return dbAdapter.getCursor(GameDbAdapter.RACE_TABLE, GameDbAdapter.KEY_RACENAME, raceName);
    }

    /**
     * 유저 길드의 전체 멤버 수를 반환
     * @param userName
     * @return count
     */
    public static int getUserMemberSize(GameDbAdapter dbAdapter, String userName) {
        return dbAdapter.getRowCount(GameDbAdapter.PLAYER_MEMBER_TABLE, GameDbAdapter.KEY_USERNAME, userName);
    }


    /**
     * 새로운 user data를 생성함.
     * @param playerName
     * @param flag
     * @return userEnty
     */
    public static UserEnty createUserData(GameDbAdapter dbAdapter,
                                          String playerName, int flag) {
        UserEnty userEnty= new UserEnty();
        switch(flag){
            case Game_Title.STARTGAME_NEWPLAYER:
                userEnty.Name = playerName;
                userEnty.EXP = INN.USER_START_EXP;
                userEnty.LEVEL = INN.USER_START_LV;
                userEnty.AP = INN.USER_START_AP;
                userEnty.GOLD = INN.USER_START_GOLD;
                userEnty.TURN = INN.USER_START_TURN;
                userEnty.GEM_RED = INN.USER_START_GEM;
                userEnty.GEM_GREEN = INN.USER_START_GEM;
                userEnty.GEM_BLUE = INN.USER_START_GEM;
                insertUserInfo(dbAdapter, userEnty);

                //player party table 생성
                for(int i=0; i< 5; i++){
                    PartyEnty enty = new PartyEnty();
                    enty.partyId = userEnty.Name+"_"+i;
                    enty.num = i;
                    insertUserParty(dbAdapter, enty, userEnty.Name);
                }

                // 0turn에 해당하는 이벤트, 멤버, 퀘스트 가져옴
                userEnty = setChangeEvent(dbAdapter, userEnty);
                break;
            case Game_Title.STARTGAME_LOADPLAYER:
                userEnty = getUserInfo(dbAdapter, playerName);
                userEnty.MEMBERLIST = getUserMemberList(dbAdapter, playerName);
                userEnty.QUESTLIST = getUserQuestList(dbAdapter, playerName);
                break;
        }

        return userEnty;
    }

    /**
     * UserDB에서 user 정보를 UserEnty로 불러옴
     * @param userName
     * @return userEnty
     */
    public static UserEnty getUserInfo(GameDbAdapter dbAdapter, String userName) {
        UserEnty userEnty = new UserEnty();
        userEnty.eventEnty = new EventEnty();
        Cursor cursor = getUserMainCursor(dbAdapter, userName);
        userEnty.Name = cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_USERNAME));
        userEnty.EXP = Integer.parseInt(cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_USEREXP)));
        userEnty.LEVEL = Integer.parseInt(cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_USERLEVEL)));
        userEnty.AP = Integer.parseInt(cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_USERAP)));
        userEnty.TURN = Integer.parseInt(cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_USERTURN)));
        userEnty.GOLD = Integer.parseInt(cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_USERGOLD)));
        userEnty.GEM_RED = Integer.parseInt(cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_USERGEM_RED)));
        userEnty.GEM_GREEN = Integer.parseInt(cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_USERGEM_GREEN)));
        userEnty.GEM_BLUE = Integer.parseInt(cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_USERGEM_BLUE)));
        cursor.close();

        return userEnty;
    }

    /**
     * userDB에서 member enty를 불러옴
     * @param memberID
     * @return enty
     */
    public static MemberEnty getUserMemberEnty(GameDbAdapter dbAdapter, String memberID) {
        MemberEnty enty = new MemberEnty();
        Cursor cursor = getUserMemberCursor(dbAdapter, memberID);
        enty.status = new StatusEnty();
        enty.equip = new EquipEnty();
        enty.memberId = memberID;
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
        enty.status.MAX_HP = Integer.parseInt(cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_MEMBERHP)));
        enty.status.MAX_MP = Integer.parseInt(cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_MEMBERMP)));
        enty.status.MAX_AP = Integer.parseInt(cursor.getString(cursor.getColumnIndex(GameDbAdapter.KEY_MEMBERAP)));
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
     * @return entyList
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

    public static ArrayList<PartyEnty> getUserPartyList(GameDbAdapter dbAdapter, String username){
        ArrayList<PartyEnty> entyList = new ArrayList<>();
        Cursor partyCursor = dbAdapter.getCursor(GameDbAdapter.PLAYER_PARTY_TABLE, GameDbAdapter.KEY_USERNAME, username);
        if(partyCursor!=null) {
            partyCursor.moveToFirst();
            do {
                PartyEnty enty = new PartyEnty();
                enty.partyName = partyCursor.getString(partyCursor.getColumnIndex(GameDbAdapter.KEY_PARTYTITLE));
                enty.partyId = partyCursor.getString(partyCursor.getColumnIndex(GameDbAdapter.KEY_PARTYID));
                enty.birthTime = partyCursor.getString(partyCursor.getColumnIndex(GameDbAdapter.KEY_PARTYBIRTH));
                enty.questTime = partyCursor.getString(partyCursor.getColumnIndex(GameDbAdapter.KEY_QUESTTIME));
                enty.party_exp = partyCursor.getString(partyCursor.getColumnIndex(GameDbAdapter.KEY_PARTY_EXP));
                enty.party_gold = partyCursor.getString(partyCursor.getColumnIndex(GameDbAdapter.KEY_PARTY_GOLD));
                enty.questId = partyCursor.getString(partyCursor.getColumnIndex(GameDbAdapter.KEY_QUESTID));
                for(int i=0;i<enty.memberPos.length;i++){
                    enty.memberPos[i] = partyCursor.getString(partyCursor.getColumnIndex("party_pos"+(i+1)));
                }
                entyList.add(enty);
            } while (partyCursor.moveToNext()) ;
            partyCursor.close();
        }
        return entyList;
    }

    /**
     * DB에서 Quest list를 가져옴
     * @param username
     * @return entyList
     */
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
     * @param turn
     * @return eventEnty
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
                eventEnty.MemberIDList.add(memberId);
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
     * @param memberID
     * @return memberEnty
     */
    public static MemberEnty getMemberData(GameDbAdapter dbAdapter, String memberID){
        String id = memberID.split("-")[0];
        MemberEnty memEnty = new MemberEnty();
        Cursor memberCursor = getDBMemberCursor(dbAdapter, id);
        memEnty.db_memberID = id;
        memEnty.memberId = id + "-" + System.currentTimeMillis();
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
        memEnty.status.MAX_HP = raceCursor.getInt(raceCursor.getColumnIndex(GameDbAdapter.KEY_RACEHP));
        memEnty.status.MAX_MP = raceCursor.getInt(raceCursor.getColumnIndex(GameDbAdapter.KEY_RACEMP));

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
     * @param memberIdList
     * @return entyList
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
     * @return entyList
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

    /**
     * db에서 quest data를 불러옴
     * @param questID
     * @return enty
     */
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

    public static PartyEnty getPartyData(GameDbAdapter dbAdapter, String username, int partyNum) {
        String partyID = username+"_"+partyNum;
        Cursor partyCursor = getPartyCursorFromId(dbAdapter, partyID);
        PartyEnty enty = new PartyEnty();
        enty.partyName = partyCursor.getString(partyCursor.getColumnIndex(GameDbAdapter.KEY_PARTYTITLE));
        enty.partyId = partyID;
        enty.birthTime = partyCursor.getString(partyCursor.getColumnIndex(GameDbAdapter.KEY_PARTYBIRTH));
        enty.questTime = partyCursor.getString(partyCursor.getColumnIndex(GameDbAdapter.KEY_QUESTTIME));
        enty.party_exp = partyCursor.getString(partyCursor.getColumnIndex(GameDbAdapter.KEY_PARTY_EXP));
        enty.party_gold = partyCursor.getString(partyCursor.getColumnIndex(GameDbAdapter.KEY_PARTY_GOLD));
        enty.questId = partyCursor.getString(partyCursor.getColumnIndex(GameDbAdapter.KEY_QUESTID));
        for(int i=0;i<enty.memberPos.length;i++){
            enty.memberPos[i] = partyCursor.getString(partyCursor.getColumnIndex("party_pos"+(i+1)));
        }

        partyCursor.close();
        return enty;
    }

    /**
     * quest table에서 quest 리스트를 불러옴
     * @param eventQuest
     * @return entyList
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
     * @return userEnty
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
        userEnty.GOLD += userEnty.eventEnty.Gold;

        return userEnty;
    }

    /**
     * user table에 user 정보를 삽입함
     * @param userEnty
     */
    public static void insertUserInfo(GameDbAdapter dbAdapter, UserEnty userEnty) {
        String TableName = GameDbAdapter.PLAYER_MAIN_TABLE;
        String UserID = "0"; //추후 google, facebook과 연동 가능
        String[] columns = {UserID, userEnty.Name, Integer.toString(userEnty.EXP),
                Integer.toString(userEnty.LEVEL), Integer.toString(userEnty.AP),
                Integer.toString(userEnty.TURN), Integer.toString(userEnty.GOLD),
                Integer.toString(userEnty.GEM_RED), Integer.toString(userEnty.GEM_GREEN),
                Integer.toString(userEnty.GEM_BLUE)};
        dbAdapter.insertDATA(TableName, columns);
    }

    /**
     * userDB에에 member를 삽입
     * @param userName
     * @param memEnty
     * @return row_id
     */
    public static long insertUserMember(GameDbAdapter dbAdapter, MemberEnty memEnty, String userName){
        String TableName = GameDbAdapter.PLAYER_MEMBER_TABLE;
        String[] columns = {memEnty.memberId, userName, memEnty.name, memEnty.engname, memEnty.sex,
                memEnty.age, memEnty.race, memEnty.memberclass, memEnty.mercy, memEnty.image,
                Integer.toString(memEnty.iconid), memEnty.profile, memEnty.dialog1,
                Integer.toString(memEnty.status.LEVEL), Integer.toString(memEnty.status.EXP),
                Integer.toString(memEnty.status.MAX_HP), Integer.toString(memEnty.status.MAX_MP),
                Integer.toString(memEnty.status.MAX_AP), Integer.toString(memEnty.status.STR),
                Integer.toString(memEnty.status.DEX), Integer.toString(memEnty.status.INT),
                Integer.toString(memEnty.status.VIT), Integer.toString(memEnty.status.RENOWN),
                Integer.toString(memEnty.equip.food), memEnty.equip.equipID_head, memEnty.equip.equipID_body,
                memEnty.equip.equipID_etc, memEnty.equip.equipID_main_arms, memEnty.equip.equipID_sub_arms,
                memEnty.questID};
        long row_id = dbAdapter.insertDATA(TableName, columns);
        return row_id;
    }

    /**
     * userDB에 quest를 삽입
     * @param userName
     * @param questEnty
     * @return
     */
    public static long insertUserQuest(GameDbAdapter dbAdapter, QuestEnty questEnty, String userName){
        String TableName = GameDbAdapter.PLAYER_QUEST_TABLE;
        String[] columns = {questEnty.id, userName, questEnty.title, questEnty.type, questEnty.map,
                questEnty.monster1, questEnty.monster2, Integer.toString(questEnty.difficult),
                Integer.toString(questEnty.monsterLV), Integer.toString(questEnty.needMember),
                Integer.toString(questEnty.rewardGold), Integer.toString(questEnty.rewardExp),
                questEnty.image, questEnty.text};

        long row_id = dbAdapter.insertDATA(TableName, columns);
        return row_id;
    }

    public static long insertUserParty(GameDbAdapter dbAdapter, PartyEnty partyEnty, String userName){
        String TableName = GameDbAdapter.PLAYER_PARTY_TABLE;
        String[] columns = {
                partyEnty.partyId, partyEnty.partyName, Integer.toString(partyEnty.num), userName, partyEnty.birthTime,
                partyEnty.questId, partyEnty.questTime, partyEnty.party_exp, partyEnty.party_gold, partyEnty.memberPos[0],
                partyEnty.memberPos[1], partyEnty.memberPos[2], partyEnty.memberPos[3], partyEnty.memberPos[4],
                partyEnty.memberPos[5],partyEnty.memberPos[6],partyEnty.memberPos[7],partyEnty.memberPos[8]};

        long row_id = dbAdapter.insertDATA(TableName, columns);
        return row_id;
    }

    /**
     * UserDB의 user정보를 갱신
     * @param userEnty
     */
    public static void updateUserInfo(GameDbAdapter dbAdapter, UserEnty userEnty){
        String TableName = GameDbAdapter.PLAYER_MAIN_TABLE;
        ContentValues values = new ContentValues();
        values.put(GameDbAdapter.KEY_USERTURN, userEnty.TURN);
        values.put(GameDbAdapter.KEY_USEREXP, userEnty.EXP);
        values.put(GameDbAdapter.KEY_USERLEVEL, userEnty.LEVEL);
        values.put(GameDbAdapter.KEY_USERAP, userEnty.AP);
        values.put(GameDbAdapter.KEY_USERGOLD, userEnty.GOLD);
        values.put(GameDbAdapter.KEY_USERGEM_RED, userEnty.GEM_RED);
        values.put(GameDbAdapter.KEY_USERGEM_GREEN, userEnty.GEM_GREEN);
        values.put(GameDbAdapter.KEY_USERGEM_BLUE, userEnty.GEM_BLUE);
        dbAdapter.updateData(TableName, values, GameDbAdapter.KEY_USERNAME, userEnty.Name);
    }

    /**
     * user 길드의 member정보 갱신
     * @param memEnty
     */
    public static void updateUserMember(GameDbAdapter dbAdapter, String memberId, MemberEnty memEnty){
        String TableName = GameDbAdapter.PLAYER_MEMBER_TABLE;
        ContentValues values = new ContentValues();
        values.put(GameDbAdapter.KEY_MEMBEREXP, memEnty.status.EXP);
        values.put(GameDbAdapter.KEY_MEMBERHP, memEnty.status.MAX_HP);
        values.put(GameDbAdapter.KEY_MEMBERMP, memEnty.status.MAX_MP);
        values.put(GameDbAdapter.KEY_MEMBERAP, memEnty.status.MAX_AP);
        values.put(GameDbAdapter.KEY_MEMBERSTR, memEnty.status.STR);
        values.put(GameDbAdapter.KEY_MEMBERDEX, memEnty.status.DEX);
        values.put(GameDbAdapter.KEY_MEMBERINT, memEnty.status.INT);
        values.put(GameDbAdapter.KEY_MEMBERVIT, memEnty.status.VIT);
        values.put(GameDbAdapter.KEY_MEMBERRENOWN, memEnty.status.RENOWN);
        values.put(GameDbAdapter.KEY_MEMBERFOOD, memEnty.equip.food);
        values.put(GameDbAdapter.KEY_MEMBERITEM1, memEnty.equip.equipID_head);
        values.put(GameDbAdapter.KEY_MEMBERITEM2, memEnty.equip.equipID_body);
        values.put(GameDbAdapter.KEY_MEMBERITEM3, memEnty.equip.equipID_etc);
        values.put(GameDbAdapter.KEY_MEMBERARM1, memEnty.equip.equipID_main_arms);
        values.put(GameDbAdapter.KEY_MEMBERARM2, memEnty.equip.equipID_sub_arms);
        dbAdapter.updateData(TableName, values, GameDbAdapter.KEY_MEMBERID, memberId);
    }

    /**
     * partyNum party 정보를 갱신
     * @param dbAdapter
     * @param partyNum
     * @param partyEnty
     */
    public static void updateUserParty(GameDbAdapter dbAdapter, String partyNum, PartyEnty partyEnty){
        String TableName = GameDbAdapter.PLAYER_PARTY_TABLE;
        ContentValues values = new ContentValues();
//        values.put(GameDbAdapter.KEY_PARTYID, partyEnty.partyId);
        values.put(GameDbAdapter.KEY_PARTYTITLE, partyEnty.partyName);
//        values.put(GameDbAdapter.KEY_USERNAME, userName);
        values.put(GameDbAdapter.KEY_QUESTID, partyEnty.questId);
        values.put(GameDbAdapter.KEY_QUESTTIME, partyEnty.questTime);
        values.put(GameDbAdapter.KEY_PARTYBIRTH, partyEnty.birthTime);
        values.put(GameDbAdapter.KEY_PARTY_EXP, partyEnty.party_exp);
        values.put(GameDbAdapter.KEY_PARTY_GOLD, partyEnty.party_gold);
        for(int i=0;i<partyEnty.memberPos.length;i++){
            values.put("party_pos"+(i+1), partyEnty.memberPos[i]);
        }

        dbAdapter.updateData(TableName, values, GameDbAdapter.KEY_PARTYNUM, partyNum);
    }

    public static void updateUserPartyMember(GameDbAdapter dbAdapter, String memberID, String userName, int partyNum, int position){
        String TableName = GameDbAdapter.PLAYER_PARTY_TABLE;
        PartyEnty partyEnty = getPartyData(dbAdapter, userName , partyNum);

        ContentValues values = new ContentValues();
//        values.put(GameDbAdapter.KEY_PARTYID, partyEnty.partyId);
        values.put(GameDbAdapter.KEY_PARTYTITLE, partyEnty.partyName);
//        values.put(GameDbAdapter.KEY_USERNAME, userName);
        values.put(GameDbAdapter.KEY_QUESTID, partyEnty.questId);
        values.put(GameDbAdapter.KEY_QUESTTIME, partyEnty.questTime);
        values.put(GameDbAdapter.KEY_PARTYBIRTH, partyEnty.birthTime);
        values.put(GameDbAdapter.KEY_PARTY_EXP, partyEnty.party_exp);
        values.put(GameDbAdapter.KEY_PARTY_GOLD, partyEnty.party_gold);
        for(int i=0;i<partyEnty.memberPos.length;i++){
            if(i == position){
                values.put("party_pos"+(i+1), memberID);
            }else {
                values.put("party_pos" + (i + 1), partyEnty.memberPos[i]);
            }
        }

        dbAdapter.updateData(TableName, values, GameDbAdapter.KEY_PARTYNUM, Integer.toString(partyNum));
    }

    /**
     * 지정된 user data를 모두 삭제함
     * @param playerName
     * @return true
     */
    public static boolean deleteUserData(GameDbAdapter dbAdapter, String playerName) {
        boolean delUser = dbAdapter.deleteROW(GameDbAdapter.PLAYER_MAIN_TABLE, -1, playerName, null);
        boolean delMember = dbAdapter.deleteROW(GameDbAdapter.PLAYER_MEMBER_TABLE, -1, playerName, null);
        boolean delQuest = dbAdapter.deleteROW(GameDbAdapter.PLAYER_QUEST_TABLE, -1, playerName, null);
        if(!delUser || !delMember || !delQuest)
            return false;
        else
            return true;
    }

    /**
     * 해당테이블의 user의 컬럼을 모두 삭제한다.
     * @param playerName
     * @return true;
     */
    public static boolean deleteUserColumn(GameDbAdapter dbAdapter, String table, String playerName){
        return dbAdapter.deleteROW(table, -1, playerName, null);
    }

    /**
     * memberID의 멤버를 삭제한다
     * @param memberId
     * @return true
     */
    public static boolean deleteUserMemberData(GameDbAdapter dbAdapter, String memberId){
        return dbAdapter.deleteROW(GameDbAdapter.PLAYER_MEMBER_TABLE, -1, null, memberId);
    }

    /**
     * questID의 quest를 삭제한다
     * @param questId
     * @return true
     */
    public static boolean deleteUserQuest(GameDbAdapter dbAdapter, String questId){
        return dbAdapter.deleteROW(GameDbAdapter.PLAYER_QUEST_TABLE, -1, null, questId);
    }

    /**
     * partyId의 party를 삭제한다
     * @param partyId
     * @return true
     */
    public static boolean deleteUserParty(GameDbAdapter dbAdapter, String partyId){
        return dbAdapter.deleteROW(GameDbAdapter.PLAYER_PARTY_TABLE, -1, null, partyId);
    }
}
