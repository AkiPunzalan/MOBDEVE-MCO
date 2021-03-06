package com.mobdeve.mco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.mobdeve.mco.Keys.Types;

import java.time.format.DateTimeFormatter;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;

    private static final String DATABASE_NAME = "tasks.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_DAILY = "Daily";
    private static final String TABLE_TODO = "Todo";
    private static final String TABLE_GOAL = "Goal";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESC = "description";
    private static final String COLUMN_COLOR = "color";
    private static final String COLUMN_STATUS = "status";
    private static final String COLUMN_NOTIFON = "notif_on";
    private static final String COLUMN_NOTIF = "next_notif";

    private static final String COLUMN_DAYS = "selected_days";
    private static final String COLUMN_PROGRESS = "progress";
    private static final String COLUMN_CURRENTPROG = "current_progress";
    private static final String COLUMN_MAXREQ = "max_req";

    private static final String CREATE_TABLE_DAILY = "CREATE TABLE " + TABLE_DAILY +
            " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_DESC + " TEXT, " +
            COLUMN_COLOR + " TEXT, " +
            COLUMN_STATUS + " INTEGER, " +
            COLUMN_NOTIFON + " INTEGER, " +
            COLUMN_NOTIF + " TEXT, " +
            COLUMN_DAYS + " TEXT);";

    private static final String CREATE_TABLE_TODO = "CREATE TABLE " + TABLE_TODO +
            " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_DESC + " TEXT, " +
            COLUMN_COLOR + " TEXT, " +
            COLUMN_STATUS + " INTEGER, " +
            COLUMN_NOTIFON + " INTEGER, " +
            COLUMN_NOTIF + " TEXT);";

    private static final String CREATE_TABLE_GOAL = "CREATE TABLE " + TABLE_GOAL +
            " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_DESC + " TEXT, " +
            COLUMN_COLOR + " TEXT, " +
            COLUMN_STATUS + " INTEGER, " +
            COLUMN_NOTIFON + " INTEGER, " +
            COLUMN_NOTIF + " TEXT, " +
            COLUMN_PROGRESS + " INTEGER, " +
            COLUMN_CURRENTPROG + " INTEGER, " +
            COLUMN_MAXREQ + " INTEGER);";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_DAILY);
        db.execSQL(CREATE_TABLE_TODO);
        db.execSQL(CREATE_TABLE_GOAL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DAILY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GOAL);
        onCreate(db);
    }

    public long addDaily(Task.Daily task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, task.getName());

        if(task.getDesc() != null)
            cv.put(COLUMN_DESC, task.getDesc());
        else
            cv.putNull(COLUMN_DESC);

        cv.put(COLUMN_COLOR, task.getColor());
        cv.put(COLUMN_STATUS, task.getStatus());
        cv.put(COLUMN_NOTIFON, task.getNotifOn());

        if(task.getNotif() != null)
            cv.put(COLUMN_NOTIF, task.getNotif().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        else
            cv.putNull(COLUMN_NOTIF);

        cv.put(COLUMN_DAYS, daysToString(task.getDays()));

        long result = db.insert(TABLE_DAILY, null, cv);

        if(result == -1){
            Log.v("database insert", "insert failed - daily");
        }

        db.close();
        return result;
    }

    public long addTodo(Task task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, task.getName());

        if(task.getDesc() != null)
            cv.put(COLUMN_DESC, task.getDesc());
        else
            cv.putNull(COLUMN_DESC);

        cv.put(COLUMN_COLOR, task.getColor());
        cv.put(COLUMN_STATUS, task.getStatus());
        cv.put(COLUMN_NOTIFON, task.getNotifOn());

        if(task.getNotif() != null)
            cv.put(COLUMN_NOTIF, task.getNotif().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        else
            cv.putNull(COLUMN_NOTIF);

        long result = db.insert(TABLE_TODO, null, cv);

        if(result == -1){
            Log.v("database insert", "insert failed - todo");
        }

        db.close();
        return result;
    }

    public long addGoal(Task.Goal task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, task.getName());

        if(task.getDesc() != null)
            cv.put(COLUMN_DESC, task.getDesc());
        else
            cv.putNull(COLUMN_DESC);

        cv.put(COLUMN_COLOR, task.getColor());
        cv.put(COLUMN_STATUS, task.getStatus());
        cv.put(COLUMN_NOTIFON, task.getNotifOn());

        if(task.getNotif() != null)
            cv.put(COLUMN_NOTIF, task.getNotif().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        else
            cv.putNull(COLUMN_NOTIF);

        cv.put(COLUMN_PROGRESS, task.getProgress());

        cv.put(COLUMN_CURRENTPROG, task.getCurrentProg());
        cv.put(COLUMN_MAXREQ, task.getMaxreq());

        long result = db.insert(TABLE_GOAL, null, cv);

        if(result == -1){
            Log.v("database insert", "insert failed - goal");
        }

        db.close();
        return result;
    }

    public Cursor readTable(String table){
        String query = "SELECT * FROM " + table;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public String getStringField(String table, String field, int id){
        String query = "SELECT " + field + " FROM " + table + " WHERE _id = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        String s;

        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            s = cursor.getString(0);
        } else s = null;

        cursor.close();
        db.close();
        return s;
    }

    public void updateStatus(int id, boolean status, String table){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_STATUS, status);

        long result = db.update(table, cv, "_id=?", new String[]{String.valueOf(id)});
    }

    public void updateNotifOn(int id, boolean isOn, String table){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NOTIFON, isOn);

        long result = db.update(table, cv, "_id=?", new String[]{String.valueOf(id)});
    }

    public void updateTime(int id, String time, String table){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NOTIF, time);

        long result = db.update(table, cv, "_id=?", new String[]{String.valueOf(id)});
    }

    //updates task day, desc, time
    public void updateTask(int id, String name, String desc, String table){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_DESC, desc);

        long result = db.update(table, cv, "_id=?", new String[]{String.valueOf(id)});
    }

    public void updateProgress(int id, int currentprog, int progpercent){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_CURRENTPROG, currentprog);
        cv.put(COLUMN_PROGRESS, progpercent);

        long result = db.update("Goal", cv, "_id=?", new String[]{String.valueOf(id)});
    }

    public void resetStatus(){
        Log.v("reset_check", "check");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_STATUS, 0);
        long result = db.update(Types.Daily.name(), cv, null, null);
        db.close();
    }

    public void deleteOneRow(String row_id, String table){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(table, "_id=?", new String[]{row_id});
    }

    private String daysToString(boolean[] days){
        StringBuilder s = new StringBuilder();
        for(int i = 0; i < 7; i++){
            if(days[i])
                s.append(1);
            else s.append(0);
        }
        return s.toString();
    }
}
