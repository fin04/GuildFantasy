package com.epriest.game.guildfantasy.main.play;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.epriest.game.guildfantasy.util.StringUtils;

import java.io.InputStream;

import jxl.Sheet;
import jxl.Workbook;

/**
 * Created by darka on 2017-04-28.
 */

public class GameDbAdapter {
    public static final String KEY_ROWID = "_id";
    public static final String KEY_MEMBERID = "member_id";
    public static final String KEY_MEMBERNAME = "member_name";
    public static final String KEY_MEMBERENGNAME = "member_engname";
    public static final String KEY_MEMBERSEX = "member_sex";
    public static final String KEY_MEMBERAGE = "member_age";
    public static final String KEY_MEMBERRACE = "member_race";
    public static final String KEY_MEMBERCLASS = "member_class";
    public static final String KEY_MEMBERMERCY = "member_mercy";
    public static final String KEY_MEMBERIMAGENAME = "member_imagename";
    public static final String KEY_MEMBERICONID = "member_iconid";
    public static final String KEY_MEMBERGRADE = "member_grade";
    public static final String KEY_MEMBERRELOAD = "member_reload";
    public static final String KEY_MEMBERPROFILE = "member_profile";
    public static final String KEY_MEMBERDIALOG1 = "member_dialog1";

    public static final String KEY_CLASSID = "class_id";
    public static final String KEY_CLASSNAME = "class_name";
    public static final String KEY_CLASSSTR = "class_str";
    public static final String KEY_CLASSDEX = "class_dex";
    public static final String KEY_CLASSINT = "class_int";
    public static final String KEY_CLASSVIT = "class_vit";
    public static final String KEY_CLASSMAINARMS = "class_mainArms";
    public static final String KEY_CLASSSUBARMS = "class_subArms";
    public static final String KEY_CLASSHEAD = "class_head";
    public static final String KEY_CLASSBODY = "class_body";


    public static final String KEY_RACEID = "race_id";
    public static final String KEY_RACENAME = "race_name";
    public static final String KEY_RACEFIRE = "race_fire";
    public static final String KEY_RACEWATER = "race_water";
    public static final String KEY_RACEWIND = "race_wind";
    public static final String KEY_RACEEARTH = "race_earth";
    public static final String KEY_RACELIGHTNING = "race_lightning";
    public static final String KEY_RACEHP = "race_hp";
    public static final String KEY_RACEMP = "race_mp";

    public static final String KEY_QUESTID = "quest_id";
    public static final String KEY_QUESTTITLE = "quest_title";
    public static final String KEY_QUESTTYPE = "quest_type";
    public static final String KEY_QUESTMAP = "quest_map";
    public static final String KEY_QUESTTIME = "quest_time";
    public static final String KEY_QUESTMONSTER1 = "quest_monster1";
    public static final String KEY_QUESTMONSTER2 = "quest_monster2";
    public static final String KEY_QUESTDIFFICULT = "quest_difficult";
    public static final String KEY_QUESTMONSTERLV = "quest_monsterlv";
    public static final String KEY_QUESTNEEDMEMBER = "quest_needmember";
    public static final String KEY_QUESTREWARD_GOLD = "quest_reward_gold";
    public static final String KEY_QUESTREWARD_EXP = "quest_reward_exp";

    public static final String KEY_QUESTIMAGENAME = "quest_image";
    public static final String KEY_QUESTTEXT = "quest_text";

    public static final String KEY_EVENTID = "event_id";
    public static final String KEY_EVENTTURN = "event_turn";
    public static final String KEY_EVENTQUEST = "event_quest";
    public static final String KEY_EVENTMEMBER = "event_member";
    public static final String KEY_EVENTGOLD = "event_gold";
    public static final String KEY_EVENTITEM = "event_item";
    public static final String KEY_EVENTIMAGE = "event_image";
    public static final String KEY_EVENTTEXT = "event_text";

    public static final String KEY_PLAYERID = "player_id";
    public static final String KEY_USERNAME = "player_name";
    public static final String KEY_USEREXP = "player_exp";
    public static final String KEY_USERLEVEL = "player_lv";
    public static final String KEY_USERAP = "player_ap";
    public static final String KEY_USERTURN = "player_turn";
    public static final String KEY_USERGOLD = "player_gold";
    public static final String KEY_USERGEM_RED = "player_gem_red";
    public static final String KEY_USERGEM_GREEN = "player_gem_green";
    public static final String KEY_USERGEM_BLUE = "player_gem_blue";

    public static final String KEY_MEMBERLEVEL = "member_lv";
    public static final String KEY_MEMBEREXP = "member_exp";
    public static final String KEY_MEMBERHP = "member_hp";
    public static final String KEY_MEMBERMP = "member_mp";
    public static final String KEY_MEMBERAP = "member_ap";
    public static final String KEY_MEMBERSTR = "member_str";
    public static final String KEY_MEMBERDEX = "member_dex";
    public static final String KEY_MEMBERINT = "member_int";
    public static final String KEY_MEMBERVIT = "member_vit";
    public static final String KEY_MEMBERRENOWN = "member_renown";
    public static final String KEY_MEMBERFOOD = "member_food";
    public static final String KEY_MEMBERITEM1 = "member_item1";
    public static final String KEY_MEMBERITEM2 = "member_item2";
    public static final String KEY_MEMBERITEM3 = "member_item3";
    public static final String KEY_MEMBERARM1 = "member_arm1";
    public static final String KEY_MEMBERARM2 = "member_arm2";

    public static final String KEY_PARTYID = "party_id";
    public static final String KEY_PARTYNUM = "party_num";
    public static final String KEY_PARTYTITLE = "party_title";
    public static final String KEY_PARTYBIRTH = "party_birth";
    public static final String KEY_PARTYPOS_1 = "party_pos1";
    public static final String KEY_PARTYPOS_2 = "party_pos2";
    public static final String KEY_PARTYPOS_3 = "party_pos3";
    public static final String KEY_PARTYPOS_4 = "party_pos4";
    public static final String KEY_PARTYPOS_5 = "party_pos5";
    public static final String KEY_PARTYPOS_6 = "party_pos6";
    public static final String KEY_PARTYPOS_7 = "party_pos7";
    public static final String KEY_PARTYPOS_8 = "party_pos8";
    public static final String KEY_PARTYPOS_9 = "party_pos9";
    public static final String KEY_PARTY_GOLD = "party_gold";
    public static final String KEY_PARTY_EXP = "party_exp";

    private static String[] MemberColumns = new String[]{
            KEY_MEMBERID, KEY_MEMBERNAME, KEY_MEMBERENGNAME, KEY_MEMBERSEX,
            KEY_MEMBERAGE, KEY_MEMBERRACE, KEY_MEMBERCLASS, KEY_MEMBERMERCY, KEY_MEMBERIMAGENAME, KEY_MEMBERICONID,
            KEY_MEMBERGRADE, KEY_MEMBERRELOAD, KEY_MEMBERPROFILE, KEY_MEMBERDIALOG1
    };

    private static String[] ClassColumns = new String[]{
            KEY_CLASSID, KEY_CLASSNAME, KEY_CLASSSTR, KEY_CLASSDEX,
            KEY_CLASSINT, KEY_CLASSVIT, KEY_CLASSMAINARMS, KEY_CLASSSUBARMS, KEY_CLASSHEAD, KEY_CLASSBODY
    };

    private static String[] RaceColumns = new String[]{
            KEY_RACEID, KEY_RACENAME, KEY_RACEFIRE, KEY_RACEWATER,
            KEY_RACEWIND, KEY_RACEEARTH, KEY_RACELIGHTNING, KEY_RACEHP, KEY_RACEMP
    };

    private static String[] QuestColumns = new String[]{
            KEY_QUESTID, KEY_QUESTTITLE, KEY_QUESTTYPE, KEY_QUESTMAP,
            KEY_QUESTMONSTER1, KEY_QUESTMONSTER2, KEY_QUESTDIFFICULT, KEY_QUESTMONSTERLV, KEY_QUESTNEEDMEMBER,
            KEY_QUESTREWARD_GOLD, KEY_QUESTREWARD_EXP, KEY_QUESTIMAGENAME, KEY_QUESTTEXT
    };

    private static String[] EventColumns = new String[]{
            KEY_EVENTID, KEY_EVENTTURN, KEY_EVENTQUEST, KEY_EVENTMEMBER,
            KEY_EVENTGOLD, KEY_EVENTITEM, KEY_EVENTIMAGE, KEY_EVENTTEXT
    };

    private static String[] PlayerMainColumns = new String[]{
            KEY_PLAYERID, KEY_USERNAME, KEY_USEREXP, KEY_USERLEVEL,
            KEY_USERAP, KEY_USERTURN, KEY_USERGOLD, KEY_USERGEM_RED,
            KEY_USERGEM_GREEN, KEY_USERGEM_BLUE};

    private static String[] PlayerMemberColumns = new String[]{
            KEY_MEMBERID, KEY_USERNAME, KEY_MEMBERNAME, KEY_MEMBERENGNAME, KEY_MEMBERSEX,
            KEY_MEMBERAGE, KEY_MEMBERRACE, KEY_MEMBERCLASS, KEY_MEMBERMERCY,
            KEY_MEMBERIMAGENAME, KEY_MEMBERICONID, KEY_MEMBERGRADE, KEY_MEMBERRELOAD,
            KEY_MEMBERPROFILE, KEY_MEMBERDIALOG1,
            KEY_MEMBERLEVEL, KEY_MEMBEREXP, KEY_MEMBERHP, KEY_MEMBERMP, KEY_MEMBERAP,
            KEY_MEMBERSTR, KEY_MEMBERDEX, KEY_MEMBERINT, KEY_MEMBERVIT, KEY_MEMBERRENOWN,
            KEY_MEMBERFOOD, KEY_MEMBERITEM1, KEY_MEMBERITEM2, KEY_MEMBERITEM3, KEY_MEMBERARM1,
            KEY_MEMBERARM2, KEY_QUESTID
    };

    private static String[] PlayerQuestColumns = new String[]{
            KEY_QUESTID, KEY_USERNAME, KEY_QUESTTITLE, KEY_QUESTTYPE, KEY_QUESTMAP,
            KEY_QUESTMONSTER1, KEY_QUESTMONSTER2, KEY_QUESTDIFFICULT, KEY_QUESTMONSTERLV, KEY_QUESTNEEDMEMBER,
            KEY_QUESTREWARD_GOLD, KEY_QUESTREWARD_EXP, KEY_QUESTIMAGENAME, KEY_QUESTTEXT
    };

    private static String[] PlayerPartyColumns = new String[]{
            KEY_PARTYID, KEY_PARTYTITLE, KEY_PARTYNUM, KEY_USERNAME, KEY_PARTYBIRTH,
            KEY_QUESTID, KEY_QUESTTIME, KEY_PARTY_EXP, KEY_PARTY_GOLD, KEY_PARTYPOS_1,
            KEY_PARTYPOS_2, KEY_PARTYPOS_3, KEY_PARTYPOS_4, KEY_PARTYPOS_5, KEY_PARTYPOS_6,
            KEY_PARTYPOS_7, KEY_PARTYPOS_8, KEY_PARTYPOS_9
    };

    private static final String TAG = "GameDbAdapter";

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    /**
     * Database creation sql statement
     */
    private static final String DATABASE_NAME = "data.db";
    public static final String DB_MEMBER_TABLE = "member";
    public static final String CLASS_TABLE = "class";
    public static final String RACE_TABLE = "race";
    public static final String QUEST_TABLE = "quest";
    public static final String EVENT_TABLE = "event";
    public static final String PLAYER_MAIN_TABLE = "player_main";
    public static final String PLAYER_MEMBER_TABLE = "player_member";
    public static final String PLAYER_QUEST_TABLE = "player_quest";
    public static final String PLAYER_PARTY_TABLE = "player_party";

    private static final int DATABASE_VERSION = 1;
    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper {
        private Context context;

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            createMemberTable(db, DB_MEMBER_TABLE, MemberColumns);
            createMemberTable(db, CLASS_TABLE, ClassColumns);
            createMemberTable(db, RACE_TABLE, RaceColumns);
            createMemberTable(db, QUEST_TABLE, QuestColumns);
            createMemberTable(db, EVENT_TABLE, EventColumns);

            createPlayerTable(db, PLAYER_MAIN_TABLE, PlayerMainColumns);
            createPlayerTable(db, PLAYER_MEMBER_TABLE, PlayerMemberColumns);
            createPlayerTable(db, PLAYER_QUEST_TABLE, PlayerQuestColumns);
            createPlayerTable(db, PLAYER_PARTY_TABLE, PlayerPartyColumns);
        }

        private String createTable(String tableName, String[] columns) {
            //create table
            String TableStr = "create table " + tableName + "(_id integer primary key autoincrement, ";
            for (int i = 0; i < columns.length; i++) {
                TableStr += columns[i] + " text not null";
                if (i < columns.length - 1) {
                    TableStr += ",";
                } else {
                    TableStr += ");";
                }
            }
            return TableStr;
        }

        private void createMemberTable(SQLiteDatabase db, String tableName, String[] columns) {
            db.execSQL(createTable(tableName, columns));
            //insert data
            Workbook workbook = null;
//            Sheet sheet = null;
            Log.w("", "----db create");
            try {
                InputStream is = context.getResources().getAssets().open("member.xls");
                workbook = Workbook.getWorkbook(is);

                if (workbook != null) {
                    int sheetNum = 0;
                    if (tableName.equals(CLASS_TABLE))
                        sheetNum = 1;
                    else if (tableName.equals(RACE_TABLE))
                        sheetNum = 2;
                    else if (tableName.equals(QUEST_TABLE))
                        sheetNum = 3;
                    else if (tableName.equals(EVENT_TABLE))
                        sheetNum = 4;
                    Sheet sheet = workbook.getSheet(sheetNum);
                    if (sheet != null) {

                        int nMaxColumn = 2;
                        int nRowStartIndex = 1;
                        int nRowEndIndex = sheet.getColumn(nMaxColumn - 1).length - 1;
                        int nColumnStartIndex = 0;
                        int nColumnEndIndex = sheet.getRow(2).length;

                        for (int nRow = nRowStartIndex; nRow <= nRowEndIndex; nRow++) {
                            String[] columnData = new String[columns.length];
                            for (int i = 0; i < columns.length; i++) {
                                columnData[i] = sheet.getCell(nColumnStartIndex + i, nRow).getContents();
                            }
                            ContentValues initialValues = new ContentValues();
                            for (int i = 0; i < columns.length; i++) {
                                initialValues.put(columns[i], columnData[i]);
                            }
                            db.insert(tableName, null, initialValues);
                        }
                    } else {
                        System.out.println("Sheet is null!!");
                    }
                } else {
                    System.out.println("WorkBook is null!!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                Log.w("", "----db success");
                if (workbook != null) {
                    workbook.close();
                }
            }
        }

        private void createPlayerTable(SQLiteDatabase db, String tableName, String[] columns) {
            db.execSQL(createTable(tableName, columns));
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion
//                    + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + DB_MEMBER_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + CLASS_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + RACE_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + QUEST_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + EVENT_TABLE);
            onCreate(db);
        }
    }

    public GameDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    public GameDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    //member
    public Cursor fetchAllDATA(String TableName, String[] columns) {
        return mDb.query(TableName, columns, null, null, null, null, null);
    }

    public Cursor fetchDATA(String TableName, String[] columns, long rowId) throws SQLException {
        Cursor mCursor = mDb.query(true, TableName, columns, KEY_ROWID
                + "=" + rowId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor getCursor(String tableName, String selectKey, String selectName) throws SQLException {
//        Cursor mCursor = mDb.query(DB_MEMBER_TABLE, null, KEY_MEMBERID+ " = '" + memberID+"'",
//                null, null, null, null, null);
        Cursor mCursor;
        if (selectName == null) {
            mCursor = mDb.rawQuery("SELECT * FROM " + tableName, null);
        } else {
            mCursor = mDb.rawQuery("SELECT * FROM " + tableName + " WHERE " + selectKey + " ='" + selectName + "'", null);
        }
        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        return mCursor;
    }

    public Cursor getCursor(String tableName, String selectKey1, String selectName1, String selectKey2, String selectName2) throws SQLException {
        Cursor mCursor = mDb.rawQuery("SELECT * FROM " + tableName + " WHERE " + selectKey1 + " ='" + selectName1 + "' AND "+ selectKey2 + " ='" + selectName2 + "'", null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        return mCursor;
    }

    public String[] getColumns(String TableName) {
        if (TableName.equals(DB_MEMBER_TABLE))
            return MemberColumns;
        else if (TableName.equals(CLASS_TABLE))
            return ClassColumns;
        else if (TableName.equals(RACE_TABLE))
            return RaceColumns;
        else if (TableName.equals(QUEST_TABLE))
            return QuestColumns;
        else if (TableName.equals(EVENT_TABLE))
            return EventColumns;
        else if (TableName.equals(PLAYER_MAIN_TABLE))
            return PlayerMainColumns;
        else if (TableName.equals(PLAYER_MEMBER_TABLE))
            return PlayerMemberColumns;
        else if (TableName.equals(PLAYER_QUEST_TABLE))
            return PlayerQuestColumns;
        else if (TableName.equals(PLAYER_PARTY_TABLE))
            return PlayerPartyColumns;
        return null;
    }

    public int getColumnsLength(String TableName) {
        return getColumns(TableName).length;
    }

    public int getRowCount(String TableName, String selectKey, String selectName) {
        Cursor c = getCursor(TableName, selectKey, selectName);
        return c.getCount();
    }

    public boolean updateData(String TableName, String[] columnData, long rowId) {
        String[] columns = getColumns(TableName);

        ContentValues args = new ContentValues();
        for (int i = 0; i < columns.length; i++) {
            args.put(columns[i], columnData[i]);
        }
        return mDb.update(TableName, args, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public void updateData(String TableName, long rowId, String data, int columnNum) {
        String[] columns = getColumns(TableName);
        columns[columnNum] = data;
        updateData(TableName, columns, rowId);
    }

    public long updateData(String TableName, ContentValues values, String columnName,
                           String columnData) {
        String whereClause = columnName + "= '" + columnData + "'";
        return mDb.update(TableName, values, whereClause, null);
    }

    /**
     * @param TableName
     * @param rowId     -1 이면 모든 ROW 제거
     * @return
     */
    public boolean deleteUserROW(String TableName, long rowId, String username) {
//        Log.i("Delete called", "value__" + rowId);
        String whereClause = null;
        if (rowId != -1) {
            whereClause = KEY_ROWID + "=" + rowId;
        } else if (username != null) {
            whereClause = KEY_USERNAME + "= '" + username + "'";
        }
        int count = mDb.delete(TableName, whereClause, null);
        return count > 0;
    }

    public boolean deleteROW(String TableName, long rowId, String username, String row_key, String row_id) {
        String whereClause = KEY_USERNAME + "=? AND " + row_key + "=?";
        String[] whereArgs = new String[]{username, row_id};
        int count = mDb.delete(TableName, whereClause, whereArgs);
        return count > 0;
    }

    public long insertDATA(String TableName, String[] columnData) {
        String[] columns = null;
        columns = getColumns(TableName);

        ContentValues initialValues = new ContentValues();
        for (int i = 0; i < columns.length; i++) {
            initialValues.put(columns[i], StringUtils.nullToEmpty(columnData[i]));
        }

        long row_id = -1;
        try {
            mDb.beginTransaction();
            row_id = mDb.insert(TableName, null, initialValues);
            mDb.setTransactionSuccessful();
        } catch (SQLException e) {
            row_id = -1;
        } finally {
            mDb.endTransaction();
        }

        return row_id;
    }

    /*public String getLastRowID(String TableName){
        String query = "SELECT "+KEY_ROWID+" from "+TableName+" order by "+KEY_ROWID+" DESC limit 1";
        Cursor c = mDb.rawQuery(query);
        if (c != null && c.moveToFirst()) {
            lastId = c.getLong(0); //The 0 is the column index, we only have 1 column, so the index is 0
        }
    }*/

}
