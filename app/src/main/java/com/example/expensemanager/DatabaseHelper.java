package com.example.expensemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;
// this Java file will
public class DatabaseHelper extends SQLiteOpenHelper {
    //Database Version
    private static final int DATABASE_VERSION=1;
    //Database Name
    private static final String  DATABASE_NAME="ExpenseTable";
    //Table NAme
    private static final String TABLE_NAME="ExpenseData";
    // Table Column that includes ID,name,Amount
    private static final String ID="id";
    private static final String name="name";
    private static final String Amount="Amount";




    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }



    //Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String table_query="CREATE TABLE if not EXISTS "+TABLE_NAME+
                "("+
                ID+" INTEGER PRIMARY KEY,"+
                name+" TEXT ,"+
                Amount+" TEXT)";
        db.execSQL(table_query);
    }
    //It wil Upgrade Table
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop Table if Existed
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    }
    //Crud Operation Starts from here
    // Method for Inserting or adding
    public boolean AddBudget(BudgetModel budgetModel){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(name,budgetModel.getName());
        contentValues.put(Amount,budgetModel.getAmount());
        //Inserting Rows
        long res=db.insert(TABLE_NAME,null,contentValues);
        //if date as inserted incorrectly it will return -1
        if (res == -1) {
            return false;
        } else {
            return true;
        }

        //Will close Database Connection
    }
    // Getting or Retrieving Single Data
    public BudgetModel getBudget(int id){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(TABLE_NAME,new String[]{ID,name,Amount},ID+" = ?",new String[]{String.valueOf(id)},null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        BudgetModel budgetModel=new BudgetModel(cursor.getString(0),cursor.getString(1),cursor.getString(2));
        db.close();
        return budgetModel;
    }
    //Getting All Data
    public List<BudgetModel> getAllBudget(){
        List<BudgetModel> budgetModelList=new ArrayList<>();
        String query="SELECT * from "+TABLE_NAME;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                BudgetModel budgetModel=new BudgetModel(cursor.getString(0),cursor.getString(1),cursor.getString(2));
                budgetModelList.add(budgetModel);
            }
            while (cursor.moveToNext());

        }
        db.close();
        return budgetModelList;
    }
    //Updating Data in Database
    public int updateBudget(BudgetModel budgetModel){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(name,budgetModel.getName());
        contentValues.put(Amount,budgetModel.getAmount());
        return db.update(TABLE_NAME,contentValues,ID+"=?",new String[]{String.valueOf(budgetModel.getId())});

    }
    //Deleteing data in database by ID
    public void deleteBudget(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_NAME,ID+"=?",new String[]{id});
        db.close();
    }
    // For getting total Count of  Rows or Record
    public int getTotalCount(){
        String query="SELECT * from "+TABLE_NAME;
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(query,null);
        return cursor.getCount();
    }
    //It will give Sum of Amount Column which will give total Amount
    public int getTotal()
    {
        int result = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select sum(Amount) from " + TABLE_NAME, null);

        if (cursor.moveToFirst()) result = cursor.getInt(0);
        cursor.close();
        db.close();
        return result;
    }

}