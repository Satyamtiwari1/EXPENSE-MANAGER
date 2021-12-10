package com.example.expensemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
public class DatabaseUser extends SQLiteOpenHelper {

    //Database Version
    private static final int DATABASE_VERSION=1;
    //Database Name
    private static final String  DATABASE_NAME="ExpenseUser";
    private static final String TABLE_NAME = "Usertable";
    private static final String COL1 = "ID";
    private static final String COL2 = "Username";
    private static final String COL3 = "Password";
    private static final String COL4 = "Name";

    public DatabaseUser(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 +" TEXT, "+
                COL3 +" TEXT, "+
                COL4 +" TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE if EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String Username, String Password, String Name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, Username);
        contentValues.put(COL3, Password);
        contentValues.put(COL4, Name);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }




  //Verify that username & Password match the database


    public boolean VerifyLogin(String Username, String Password){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME +
                " WHERE " + COL2 + " = '" + Username + "'"+
                " AND " + COL3 + " = '" + Password + "'";
        Cursor data = db.rawQuery(query, null);
        if(data.getCount()>0)
        {
            return true;
        }else
            return false;
    }

}