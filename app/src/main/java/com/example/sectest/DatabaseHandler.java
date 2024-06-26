package com.example.sectest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    DatabaseHandler(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACT_TABLE = "CREATE TABLE " + Util.TABLE_NAME + "("
                + Util.KEY_ID + " INTEGER PRIMARY KEY,"
                + Util.KEY_SCORE + " TEXT,"
                + Util.KEY_NAME + " TEXT,"
                + Util.KEY_LOCATION + " TEXT,"
                + Util.KEY_TIME + " TEXT" + ")";
        db.execSQL(CREATE_CONTACT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE " + Util.TABLE_NAME +  " IF EXISTS";
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void addScore(String score, String name, String location, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util.KEY_SCORE, score);
        values.put(Util.KEY_NAME, name);
        values.put(Util.KEY_LOCATION, location);
        values.put(Util.KEY_TIME, time);
        db.insert(Util.TABLE_NAME, null, values);
        db.close();
    }

    public Cursor getScores() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectAll = "SELECT * FROM " + Util.TABLE_NAME + " ORDER BY " + Util.KEY_SCORE + " DESC LIMIT 3";
        Cursor cursor = db.rawQuery(selectAll, null);
        return cursor;

    }
}
