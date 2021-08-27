package com.mobdeve.mco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.time.LocalDateTime;
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
    private static final String COLUMN_NOTIF = "next_notif";

    private static final String COLUMN_DAYS = "selected_days";
    private static final String COLUMN_PROGRESS = "progress";

    private static final String CREATE_TABLE_DAILY = "CREATE TABLE " + TABLE_DAILY +
            " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_DESC + " TEXT, " +
            COLUMN_COLOR + " TEXT, " +
            COLUMN_STATUS + " INTEGER, " +
            COLUMN_NOTIF + " TEXT, " +
            COLUMN_DAYS + " TEXT);";

    private static final String CREATE_TABLE_TODO = "CREATE TABLE " + TABLE_TODO +
            " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_DESC + " TEXT, " +
            COLUMN_COLOR + " TEXT, " +
            COLUMN_STATUS + " INTEGER, " +
            COLUMN_NOTIF + " TEXT);";

    private static final String CREATE_TABLE_GOAL = "CREATE TABLE " + TABLE_GOAL +
            " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_DESC + " TEXT, " +
            COLUMN_COLOR + " TEXT, " +
            COLUMN_STATUS + " INTEGER, " +
            COLUMN_NOTIF + " TEXT, " +
            COLUMN_PROGRESS + " INTEGER);";


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

    public void addDaily(String name, String desc, String color, int status, @Nullable LocalDateTime notif, boolean[] days){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name);

        if(desc != null)
            cv.put(COLUMN_DESC, desc);
        else
            cv.putNull(COLUMN_DESC);

        cv.put(COLUMN_COLOR, color);
        cv.put(COLUMN_STATUS, status);

        if(notif != null)
            cv.put(COLUMN_NOTIF, notif.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        else
            cv.putNull(COLUMN_NOTIF);

        cv.put(COLUMN_DAYS, daysToString(days));

        long result = db.insert(TABLE_DAILY, null, cv);

        if(result == -1){
            Log.v("database insert", "insert failed - daily");
        }
    }

    public void addTodo(String name, String desc, String color, int status, @Nullable LocalDateTime notif){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name);

        if(desc != null)
            cv.put(COLUMN_DESC, desc);
        else
            cv.putNull(COLUMN_DESC);

        cv.put(COLUMN_COLOR, color);
        cv.put(COLUMN_STATUS, status);

        if(notif != null)
            cv.put(COLUMN_NOTIF, notif.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        else
            cv.putNull(COLUMN_NOTIF);

        long result = db.insert(TABLE_TODO, null, cv);

        if(result == -1){
            Log.v("database insert", "insert failed - todo");
        }
    }

    public void addGoal(String name, String desc, String color, int status, @Nullable LocalDateTime notif, int progress){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name);

        if(desc != null)
            cv.put(COLUMN_DESC, desc);
        else
            cv.putNull(COLUMN_DESC);

        cv.put(COLUMN_COLOR, color);
        cv.put(COLUMN_STATUS, status);

        if(notif != null)
            cv.put(COLUMN_NOTIF, notif.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        else
            cv.putNull(COLUMN_NOTIF);

        cv.put(COLUMN_PROGRESS, progress);

        long result = db.insert(TABLE_GOAL, null, cv);

        if(result == -1){
            Log.v("database insert", "insert failed - goal");
        }
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

    public void updateOneDaily(String row_id, String name, String desc, String color, int status, @Nullable LocalDateTime notif, boolean[] days){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name);

        if(desc != null)
            cv.put(COLUMN_DESC, desc);
        else
            cv.putNull(COLUMN_DESC);

        cv.put(COLUMN_COLOR, color);
        cv.put(COLUMN_STATUS, status);

        if(notif != null)
            cv.put(COLUMN_NOTIF, notif.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        else
            cv.putNull(COLUMN_NOTIF);

        cv.put(COLUMN_DAYS, daysToString(days));

        long result = db.update(TABLE_DAILY, cv, "_id=?", new String[]{row_id});
    }

    public void updateOneTodo(String row_id, String name, String desc, String color, int status, @Nullable LocalDateTime notif){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name);

        if(desc != null)
            cv.put(COLUMN_DESC, desc);
        else
            cv.putNull(COLUMN_DESC);

        cv.put(COLUMN_COLOR, color);
        cv.put(COLUMN_STATUS, status);

        if(notif != null)
            cv.put(COLUMN_NOTIF, notif.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        else
            cv.putNull(COLUMN_NOTIF);

        long result = db.update(TABLE_TODO, cv, "_id=?", new String[]{row_id});
    }

    public void updateOneGoal(String row_id, String name, String desc, String color, int status, @Nullable LocalDateTime notif, int progress){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name);

        if(desc != null)
            cv.put(COLUMN_DESC, desc);
        else
            cv.putNull(COLUMN_DESC);

        cv.put(COLUMN_COLOR, color);
        cv.put(COLUMN_STATUS, status);

        if(notif != null)
            cv.put(COLUMN_NOTIF, notif.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        else
            cv.putNull(COLUMN_NOTIF);

        cv.put(COLUMN_PROGRESS, progress);

        long result = db.update(TABLE_GOAL, cv, "_id=?", new String[]{row_id});
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
