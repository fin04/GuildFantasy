package com.epriest.game.guildfantasy.main.play;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.epriest.game.guildfantasy.main.enty.EventEnty;
import com.epriest.game.guildfantasy.main.enty.MemberEnty;
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
        return dbAdapter.getCursor(GameDbAdapter.RACE_TABLE, GameDbAdapter.KEY_RACENAME , raceName);
    }

    public static PlayerEnty setStartGamePlayerData(Context context, GameDbAdapter dbAdapter) {
        PlayerEnty enty = new PlayerEnty();
        enty.Name = "홍길동";
        enty.LEVEL = 1;
        enty.GOLD = 100;
        enty.TURN = 1;
        enty.eventEnty = getEventDataList(dbAdapter, enty.TURN);
        enty.MEMBERLIST = getMemberDataList(dbAdapter, enty.eventEnty.MemberIDList);
        enty.QUESTLIST = getQuestDataList(dbAdapter, enty.eventEnty.QuestIDList);
        enty.PARTYLIST = new ArrayList<>();
        return enty;
    }

    public static PlayerEnty setChangeEvent(GameDbAdapter dbAdapter, PlayerEnty playerEnty) {
        playerEnty.eventEnty = getEventDataList(dbAdapter, playerEnty.TURN);
        playerEnty.MEMBERLIST.addAll(getMemberDataList(dbAdapter, playerEnty.eventEnty.MemberIDList));
        playerEnty.QUESTLIST = getQuestDataList(dbAdapter, playerEnty.eventEnty.QuestIDList);
        return playerEnty;
    }

    public static EventEnty getEventDataList(GameDbAdapter dbAdapter, int turn) {
        EventEnty eventEnty = new EventEnty();
        Cursor eventCursor = getEventTurnCursor(dbAdapter, Integer.toString(turn));
        eventEnty.MemberIDList = new ArrayList<>();
        eventEnty.QuestIDList = new ArrayList<>();
        String gold = "";
        eventEnty.ItemList = new ArrayList<>();
        eventEnty.ImageList = new ArrayList<>();
        eventEnty.TextList = new ArrayList<>();

        while(!eventCursor.isAfterLast()){
            String questId = eventCursor.getString(eventCursor.getColumnIndex(GameDbAdapter.KEY_EVENTQUEST));
            if(questId.equals("") || questId == null){}else{
                eventEnty.QuestIDList.add(questId);
            }
            String memberId = eventCursor.getString(eventCursor.getColumnIndex(GameDbAdapter.KEY_EVENTMEMBER));
            if(memberId.equals("") || memberId == null){}else{
                eventEnty.MemberIDList.add(memberId);
            }
            gold = eventCursor.getString(eventCursor.getColumnIndex(GameDbAdapter.KEY_EVENTGOLD));
            if(gold.equals("") || gold == null){
                gold = "0";
            }
            eventEnty.Gold += Integer.parseInt(gold);

            String itemId = eventCursor.getString(eventCursor.getColumnIndex(GameDbAdapter.KEY_EVENTITEM));
            if(itemId.equals("") || itemId == null){}else{
                eventEnty.ItemList.add(itemId);
            }
            String ImageName = eventCursor.getString(eventCursor.getColumnIndex(GameDbAdapter.KEY_EVENTIMAGE));
            if(ImageName.equals("") || ImageName == null){}else{
                eventEnty.ImageList.add(ImageName);
            }
            String text = eventCursor.getString(eventCursor.getColumnIndex(GameDbAdapter.KEY_EVENTTEXT));
            if(text.equals("") || text == null){}else{
                eventEnty.TextList.add(text);
            }
            eventCursor.moveToNext();
        }

        return eventEnty;
    }

    public static ArrayList<MemberEnty> getMemberDataList(GameDbAdapter dbAdapter, ArrayList<String> eventMember) {
        ArrayList<MemberEnty> entyList = new ArrayList<>();
        for(String memberID : eventMember){
            MemberEnty memEenty = new MemberEnty();
            Cursor memberCursor = getMemberCursor(dbAdapter, memberID);
            memEenty.name = memberCursor.getString(memberCursor.getColumnIndex(GameDbAdapter.KEY_NAME));
            memEenty.engname = memberCursor.getString(memberCursor.getColumnIndex(GameDbAdapter.KEY_ENGNAME));
            memEenty.sex = memberCursor.getString(memberCursor.getColumnIndex(GameDbAdapter.KEY_SEX));
            memEenty.age = memberCursor.getString(memberCursor.getColumnIndex(GameDbAdapter.KEY_AGE));
            memEenty.race  = memberCursor.getString(memberCursor.getColumnIndex(GameDbAdapter.KEY_RACE));
            memEenty._class  = memberCursor.getString(memberCursor.getColumnIndex(GameDbAdapter.KEY_CLASS));
            memEenty.mercy  = memberCursor.getString(memberCursor.getColumnIndex(GameDbAdapter.KEY_MERCY));
            memEenty.image  = memberCursor.getString(memberCursor.getColumnIndex(GameDbAdapter.KEY_IMAGENAME));
            memEenty.iconid  = memberCursor.getInt(memberCursor.getColumnIndex(GameDbAdapter.KEY_ICONID));
            memEenty.profile  = memberCursor.getString(memberCursor.getColumnIndex(GameDbAdapter.KEY_PROFILE));
            memEenty.dialog1  = memberCursor.getString(memberCursor.getColumnIndex(GameDbAdapter.KEY_DIALOG1));

            memEenty.status = new StatusEnty();
            Cursor classCursor = getClassCursor(dbAdapter, memEenty._class);
            memEenty.status.STR = classCursor.getInt(classCursor.getColumnIndex(GameDbAdapter.KEY_CLASSSTR));
            memEenty.status.DEX = classCursor.getInt(classCursor.getColumnIndex(GameDbAdapter.KEY_CLASSDEX));
            memEenty.status.INT = classCursor.getInt(classCursor.getColumnIndex(GameDbAdapter.KEY_CLASSINT));
            memEenty.status.VIT = classCursor.getInt(classCursor.getColumnIndex(GameDbAdapter.KEY_CLASSVIT));

            Cursor raceCursor = getRaceCursor(dbAdapter, memEenty.race);
            memEenty.status.HP = raceCursor.getInt(raceCursor.getColumnIndex(GameDbAdapter.KEY_RACEHP));
            memEenty.status.MP = raceCursor.getInt(raceCursor.getColumnIndex(GameDbAdapter.KEY_RACEMP));
            entyList.add(memEenty);
        }
        return entyList;
    }

    public static ArrayList<QuestEnty> getQuestDataList(GameDbAdapter dbAdapter, ArrayList<String> eventQuest) {
        ArrayList<QuestEnty> entyList = new ArrayList<>();
        for(String questId : eventQuest){
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
}
