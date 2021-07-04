package com.example.test1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String calTable = "cscheduletbl";
        db.execSQL("create table if not exists " + calTable + " ("
                + " sNum integer PRIMARY KEY autoincrement, "
                + "sMID   TEXT NOT NULL,"
                + "sStartDate   TEXT NOT NULL,"
                + "sEndDate   TEXT NOT NULL,"
                + "sStartTime   TEXT NOT NULL,"
                + "sEndTime   TEXT NOT NULL,"
                + "sText   TEXT NOT NULL);");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

}
