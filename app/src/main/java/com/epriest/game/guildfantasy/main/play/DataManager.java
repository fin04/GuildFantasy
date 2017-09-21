package com.epriest.game.guildfantasy.main.play;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.epriest.game.guildfantasy.main.enty.EventEnty;
import com.epriest.game.guildfantasy.main.enty.MemberEnty;
import com.epriest.game.guildfantasy.main.enty.PartyEnty;
import com.epriest.game.guildfantasy.main.enty.PlayerEnty;
import com.epriest.game.guildfantasy.main.enty.QuestEnty;
import com.epriest.game.guildfantasy.main.enty.StatusEnty;

import java.util.ArrayList;

/**
 * Created by darka on 2017-04-30.
 */

public class DataManager {

    public static Cursor getEventTurnCursor(GameDbAdapter dbAdapter, String turn) {
        return dbAdapter.getCursor(GameDbAdapter.EVENT_TABLE, GameDbAdapter.KEY_EVENTTURN, turn);
    }

    /*public static Cursor getEventQuestCursor(GameDbAdapter dbAdapter, String turn) {
        return dbAdapter.getCursor(GameDbAdapter.EVENT_TABLE, GameDbAdapter.KEY_EVENTTURN, turn);
    }*/

    public static Cursor getQuestCursor(GameDbAdapter dbAdapter, String questID) {
        return dbAdapter.getCursor(GameDbAdapter.QUEST_TABLE, GameDbAdapter.KEY_QUESTID, questID);
    }

    public static Cursor getMemberCursor(GameDbAdapter dbAdapter, String memberID) {
        return dbAdapter.getCursor(GameDbAdapter.MEMBER_TABLE, GameDbAdapter.KEY_MEMBERID, memberID);
    }

    public static Cursor getClassCursor(GameDbAdapter dbAdapter, String className) {
        return dbAdapter.getCursor(GameDbAdapter.CLASS_TABLE, GameDbAdapter.KEY_CLASSNAME, className);
    }

    public static Cursor getRaceCursor(GameDbAdapter dbAdapter, String raceName) {
        return dbAdapter.getCursor(GameDbAdapter.RACE_TABLE, GameDbAdapter.KEY_RACENAME, raceName);
    }

    /**
     * 처음 시작할때 호출, PlayerEnty를 생성
     * @param context
     * @param dbAdapter
     * @return
     */
    public static PlayerEnty setStartGamePlayerData(Context context, GameDbAdapter dbAdapter) {
        PlayerEnty playerEnty = new PlayerEnty();
        playerEnty.Name = "홍길동";
        playerEnty.LEVEL = 1;
        playerEnty.GOLD = 100;
        playerEnty.TURN = 1;
        playerEnty.eventEnty = getEventDataList(dbAdapter, playerEnty.TURN);
        playerEnty.MEMBERLIST = getMemberDataList(dbAdapter, playerEnty.eventEnty.MemberIDList);
        playerEnty.QUESTLIST = getQuestDataList(dbAdapter, playerEnty.eventEnty.QuestIDList);
        playerEnty.PARTYMemberIdLIST = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            playerEnty.PARTYMemberIdLIST.add(null);
        }
        return playerEnty;
    }

    /**
     * 이벤트가 바뀔때 호출
     * @param dbAdapter
     * @param playerEnty
     * @return
     */
    public static PlayerEnty setChangeEvent(GameDbAdapter dbAdapter, PlayerEnty playerEnty) {
        playerEnty.eventEnty = getEventDataList(dbAdapter, playerEnty.TURN);
        playerEnty.MEMBERLIST.addAll(getMemberDataList(dbAdapter, playerEnty.eventEnty.MemberIDList));
        playerEnty.QUESTLIST = getQuestDataList(dbAdapter, playerEnty.eventEnty.QuestIDList);
        return playerEnty;
    }

    /**
     * DB에서 turn에 해당되는 data를 Get
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
                //Member Id에 고유값을 추가하기 위해...
                eventEnty.MemberIDList.add(memberId+"-"+System.currentTimeMillis());
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

        return eventEnty;
    }

    /**
     * ID로 member의 data를 get
     * @param dbAdapter
     * @param id
     * @return
     */
    public static MemberEnty getMemberDataFromID(GameDbAdapter dbAdapter, String id){
        String _id = id.split("-")[0];
        Cursor memberCursor = getMemberCursor(dbAdapter, _id);
        MemberEnty memberEnty = new MemberEnty();
        memberEnty.memebrId = id;
        memberEnty.name = memberCursor.getString(memberCursor.getColumnIndex(GameDbAdapter.KEY_NAME));
        memberEnty.engname = memberCursor.getString(memberCursor.getColumnIndex(GameDbAdapter.KEY_ENGNAME));
        memberEnty.sex = memberCursor.getString(memberCursor.getColumnIndex(GameDbAdapter.KEY_SEX));
        memberEnty.age = memberCursor.getString(memberCursor.getColumnIndex(GameDbAdapter.KEY_AGE));
        memberEnty.race = memberCursor.getString(memberCursor.getColumnIndex(GameDbAdapter.KEY_RACE));
        memberEnty._class = memberCursor.getString(memberCursor.getColumnIndex(GameDbAdapter.KEY_CLASS));
        memberEnty.mercy = memberCursor.getString(memberCursor.getColumnIndex(GameDbAdapter.KEY_MERCY));
        memberEnty.image = memberCursor.getString(memberCursor.getColumnIndex(GameDbAdapter.KEY_IMAGENAME));
        memberEnty.iconid = memberCursor.getInt(memberCursor.getColumnIndex(GameDbAdapter.KEY_ICONID));
        memberEnty.profile = memberCursor.getString(memberCursor.getColumnIndex(GameDbAdapter.KEY_PROFILE));
        memberEnty.dialog1 = memberCursor.getString(memberCursor.getColumnIndex(GameDbAdapter.KEY_DIALOG1));

        memberEnty.status = new StatusEnty();
        Cursor classCursor = getClassCursor(dbAdapter, memberEnty._class);
        memberEnty.status.STR = classCursor.getInt(classCursor.getColumnIndex(GameDbAdapter.KEY_CLASSSTR));
        memberEnty.status.DEX = classCursor.getInt(classCursor.getColumnIndex(GameDbAdapter.KEY_CLASSDEX));
        memberEnty.status.INT = classCursor.getInt(classCursor.getColumnIndex(GameDbAdapter.KEY_CLASSINT));
        memberEnty.status.VIT = classCursor.getInt(classCursor.getColumnIndex(GameDbAdapter.KEY_CLASSVIT));

        Cursor raceCursor = getRaceCursor(dbAdapter, memberEnty.race);
        memberEnty.status.HP = raceCursor.getInt(raceCursor.getColumnIndex(GameDbAdapter.KEY_RACEHP));
        memberEnty.status.MP = raceCursor.getInt(raceCursor.getColumnIndex(GameDbAdapter.KEY_RACEMP));
        return memberEnty;
    }

    public static ArrayList<MemberEnty> getMemberDataList(GameDbAdapter dbAdapter, ArrayList<String> eventMember) {
        ArrayList<MemberEnty> entyList = new ArrayList<>();
        for (String id : eventMember) {
            entyList.add(DataManager.getMemberDataFromID(dbAdapter, id));
        }
        return entyList;
    }

    /**
     * Quest Id로 Quest정보를 get
     * @param dbAdapter
     * @param eventQuestID
     * @return
     */
    public static ArrayList<QuestEnty> getQuestDataList(GameDbAdapter dbAdapter, ArrayList<String> eventQuestID) {
        ArrayList<QuestEnty> entyList = new ArrayList<>();
        for (String questId : eventQuestID) {
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
            entyList.add(enty);
        }
        return entyList;
    }

    public static MemberEnty getMemberEntyFromId(ArrayList<MemberEnty> MEMBERLIST, String id) {
        for(MemberEnty enty : MEMBERLIST){
            if(enty.memebrId.equals(id))
                return enty;
        }
        return null;
    }
}
