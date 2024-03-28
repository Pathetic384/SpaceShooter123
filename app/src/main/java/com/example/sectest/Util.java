package com.example.sectest;

import android.net.Uri;

public class Util {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "score_db";
    public static final String TABLE_NAME = "scores";


    public static final String KEY_ID = "id";
    public static final String KEY_SCORE = "score";
    public static final String KEY_NAME = "name";
    public static final String AUTHORITY = "com.example.sectest";
    public static final Uri URI = Uri.parse("content://" + AUTHORITY + "/scores");
}
