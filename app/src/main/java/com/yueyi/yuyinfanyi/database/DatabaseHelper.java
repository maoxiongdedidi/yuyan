package com.yueyi.yuyinfanyi.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public final String TABLE_TEMP = "fy_cache";
    public DatabaseHelper(Context context) {
        super(context, "yuyinfanyi.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table if not exists " + TABLE_TEMP + "(num integer primary key autoincrement, jsonData text null,cid text null,  cacheTime text null, type text null);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
