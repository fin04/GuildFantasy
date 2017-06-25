package com.epriest.game.guildfantasy.main.play;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.io.InputStream;

import jxl.Sheet;
import jxl.Workbook;

/**
 * Created by darka on 2017-04-28.
 */

public class GameDbAdapter {
    public static final String KEY_ROWID = "_id";
    public static final String KEY_MEMBERID = "memberid";
    public static final String KEY_NAME = "name";
    public static final String KEY_ENGNAME = "engname";
    public static final String KEY_SEX = "sex";
    public static final String KEY_AGE = "age";
    public static final String KEY_RACE = "race";
    public static final String KEY_CLASS = "class";
    public static final String KEY_MERCY = "mercy";
    public static final String KEY_IMAGENAME = "imagename";
    public static final String KEY_ICONID = "iconid";
    public static final String KEY_PROFILE = "profile";
    public static final String KEY_DIALOG1 = "dialog1";

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


    private static String[] MemberColumns = new String[]{KEY_MEMBERID, KEY_NAME, KEY_ENGNAME, KEY_SEX,
            KEY_AGE, KEY_RACE, KEY_CLASS, KEY_MERCY, KEY_IMAGENAME, KEY_ICONID, KEY_PROFILE, KEY_DIALOG1};
    private static String[] ClassColumns = new String[]{KEY_CLASSID, KEY_CLASSNAME, KEY_CLASSSTR, KEY_CLASSDEX,
            KEY_CLASSINT, KEY_CLASSVIT, KEY_CLASSMAINARMS, KEY_CLASSSUBARMS, KEY_CLASSHEAD, KEY_CLASSBODY};
    private static String[] RaceColumns = new String[]{KEY_RACEID, KEY_RACENAME, KEY_RACEFIRE, KEY_RACEWATER,
            KEY_RACEWIND, KEY_RACEEARTH, KEY_RACELIGHTNING, KEY_RACEHP, KEY_RACEMP};
    private static String[] QuestColumns = new String[]{KEY_QUESTID, KEY_QUESTTITLE, KEY_QUESTTYPE, KEY_QUESTMAP,
            KEY_QUESTMONSTER1, KEY_QUESTMONSTER2, KEY_QUESTDIFFICULT, KEY_QUESTMONSTERLV, KEY_QUESTNEEDMEMBER,
            KEY_QUESTREWARD_GOLD, KEY_QUESTREWARD_EXP, KEY_QUESTIMAGENAME, KEY_QUESTTEXT};
    private static String[] EventColumns = new String[]{KEY_EVENTID, KEY_EVENTTURN, KEY_EVENTQUEST, KEY_EVENTMEMBER,
            KEY_EVENTGOLD, KEY_EVENTITEM, KEY_EVENTIMAGE, KEY_EVENTTEXT};


    private static final String TAG = "GameDbAdapter";

    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    /**
     * Database creation sql statement
     */
    private static final String DATABASE_NAME = "data.db";
    public static final String MEMBER_TABLE = "member";
    public static final String CLASS_TABLE = "class";
    public static final String RACE_TABLE = "race";
    public static final String QUEST_TABLE = "quest";
    public static final String EVENT_TABLE = "event";
    private static final int DATABASE_VERSION = 5;
    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper {
        private Context context;

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            createMemberTable(db, MEMBER_TABLE, MemberColumns);
            createMemberTable(db, CLASS_TABLE, ClassColumns);
            createMemberTable(db, RACE_TABLE, RaceColumns);
            createMemberTable(db, QUEST_TABLE, QuestColumns);
            createMemberTable(db, EVENT_TABLE, EventColumns);
        }

        private void createMemberTable(SQLiteDatabase db, String tableName, String[] columns) {
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
            db.execSQL(TableStr);

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

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion
//                    + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + MEMBER_TABLE);
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
    public long createDATA(String TableName, String[] columnData) {
        String[] columns = null;
        columns = getColumns(TableName);
        /*if(TableName.equals(MEMBER_TABLE))
            columns = MemberColumns;
        else if(TableName.equals(CLASS_TABLE))
            columns = ClassColumns;
        else if(TableName.equals(RACE_TABLE))
            columns = RaceColumns;
        else if(TableName.equals(QUEST_TABLE))
            columns = QuestColumns;*/

        ContentValues initialValues = new ContentValues();
        for (int i = 0; i < columns.length; i++) {
            initialValues.put(columns[i], columnData[i]);
        }

        return mDb.insert(TableName, null, initialValues);
    }

    public boolean deleteROW(String TableName, long rowId) {
        Log.i("Delete called", "value__" + rowId);
        return mDb.delete(TableName, KEY_ROWID + "=" + rowId, null) > 0;
    }

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
//        Cursor mCursor = mDb.query(MEMBER_TABLE, null, KEY_MEMBERID+ " = '" + memberID+"'",
//                null, null, null, null, null);
        Cursor mCursor = mDb.rawQuery("SELECT * FROM " + tableName + " WHERE " + selectKey + " ='" + selectName + "'", null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        return mCursor;
    }

    private String[] getColumns(String TableName) {
        if (TableName.equals(MEMBER_TABLE))
            return MemberColumns;
        else if (TableName.equals(CLASS_TABLE))
            return ClassColumns;
        else if (TableName.equals(RACE_TABLE))
            return RaceColumns;
        else if (TableName.equals(QUEST_TABLE))
            return QuestColumns;
        else if (TableName.equals(EVENT_TABLE))
            return EventColumns;
        return null;
    }

    public int getColumnsLength(String TableName) {
        return getColumns(TableName).length;
    }

    public boolean updateROW(String TableName, String[] columnData, long rowId) {
        String[] columns = getColumns(TableName);

        ContentValues args = new ContentValues();
        for (int i = 0; i < columns.length; i++) {
            args.put(columns[i], columnData[i]);
        }
        return mDb.update(TableName, args, KEY_ROWID + "=" + rowId, null) > 0;
    }


}
