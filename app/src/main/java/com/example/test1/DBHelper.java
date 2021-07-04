package com.example.test1;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

public class
DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "commute.db";
    private static final int DB_VER = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String cmembertbl = "CREATE TABLE IF NOT EXISTS cmembertbl ("+
                "userid   TEXT NOT NULL PRIMARY KEY,"+
                "password   TEXT NOT NULL,"+
                "name   TEXT NOT NULL,"+
                "company   TEXT NOT NULL,"+
                "department   TEXT NOT NULL,"+
                "position   TEXT NOT NULL,"+
                "usertel   TEXT NOT NULL,"+
                "email   TEXT NOT NULL)";

        String cworktbl = "CREATE TABLE IF NOT EXISTS cworktbl ("+
                "wDate   TEXT NOT NULL PRIMARY KEY,"+
                "wMID   TEXT NOT NULL,"+
                "wGoTime   TEXT,"+
                "wOffTime   TEXT,"+
                "wGoHo   INTEGAR,"+
                "wGoMi   INTEGAR,"+
                "wOffHo   INTEGAR,"+
                "wOffMi   INTEGAR,"+
                "wDutyHours   TEXT,"+
                "wExtraHours   TEXT,"+
                "wChange INTEGER NOT NULL,"+ //수정 여부(수정=1, 미수정=0)
                "wChangeDate   TEXT,"+ //수정날짜
                "wChangeCause   TEXT,"+ //수정이유
                "wNewGoTime   TEXT,"+ //수정출근시간
                "wNewOffTime   TEXT,"+ //수정퇴근시간
                "FOREIGN KEY(wMID) REFERENCES cmembertbl(mID))";

        String cpaytbl = "CREATE TABLE IF NOT EXISTS cpaytbl ("+
                "pDate   TEXT NOT NULL PRIMARY KEY,"+
                "pMID   TEXT NOT NULL,"+
                "pMonthTime   TEXT ,"+
                "pMonthExtraHours   TEXT,"+
                "pAntiPay   INTEGER ,"+
                "pExtraPay   INTEGER,"+
                "pMonthPay   INTEGER ,"+
                "FOREIGN KEY(pMID) REFERENCES cmembertbl(mID))";

        String cscheduletbl = "CREATE TABLE IF NOT EXISTS cscheduletbl ("+
                "sNum   INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+
                "sMID   TEXT NOT NULL,"+
                "sStartDate   TEXT NOT NULL,"+
                "sEndDate   TEXT NOT NULL,"+
                "sStartTime   TEXT NOT NULL,"+
                "sEndTime   TEXT NOT NULL,"+
                "sText   TEXT NOT NULL,"+
                "FOREIGN KEY(sMID) REFERENCES cmembertbl(mID))";

        db.execSQL(cmembertbl);
        db.execSQL(cworktbl);
        db.execSQL(cpaytbl);
        db.execSQL(cscheduletbl);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists users");
        onCreate(db);
    }

    public boolean insertData(String userid, String password, String name, String company, String department, String position, String usertel, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userid", userid);
        contentValues.put("password",password);
        contentValues.put("name", name);
        contentValues.put("company", company);
        contentValues.put("department", department);
        contentValues.put("position", position);
        contentValues.put("usertel", usertel);
        contentValues.put("email",email);
        long result = db.insert("cmembertbl", null, contentValues);

        if(result== -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public boolean checkuserid(String userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from cmembertbl where userid = ?", new String[] {userid});
        if(cursor.getCount()>0) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean checkuseridPassword(String userid, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from cmembertbl where userid = ? and password = ?", new String[] {userid,password});

        if(cursor.getCount()>0) {
            return true;
        }
        else {
            return
                    false;
        }
    }
}