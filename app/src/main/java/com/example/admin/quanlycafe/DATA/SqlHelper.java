package com.example.admin.quanlycafe.DATA;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.admin.quanlycafe.DAO.AccountDAO;
import com.example.admin.quanlycafe.DAO.BillDAO;
import com.example.admin.quanlycafe.DAO.BillInfoDAO;
import com.example.admin.quanlycafe.DAO.FoodCategoryDAO;
import com.example.admin.quanlycafe.DAO.FoodDAO;
import com.example.admin.quanlycafe.DAO.TableFoodDAO;

public class SqlHelper extends SQLiteOpenHelper {
    private static SqlHelper instance;
    private static final String DATABASE = "quanlycafe";

    public static SqlHelper Instance(Context context)
    {
        if(instance == null)
        {
            instance = new SqlHelper(context.getApplicationContext());
        }
        return instance;
    }
    private SqlHelper( Context context) {

        super(context, DATABASE, null, 11);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Table Account
        String query = "CREATE TABLE IF NOT EXISTS " + AccountDAO.TABLE +
                "(" +
                    AccountDAO.USER_NAME +  " text primary key," +
                    AccountDAO.DISPLAY_NAME + " text not null default 'No Name'," +
                    AccountDAO.PASSWORD + " text not null default '123'," +
                    AccountDAO.TYPE_ACCOUNT + " integer not null default 0" +
                ");";
        db.execSQL(query);

        // Create Table FoodCategory
        String query2 = "CREATE TABLE IF NOT EXISTS " + FoodCategoryDAO.TABLE +
                "(" +
                    FoodCategoryDAO.ID + " integer primary key autoincrement," +
                    FoodCategoryDAO.NAMECATEGORY + " text not null default 'No Name'" +
                ");";
        db.execSQL(query2);

        // Create Table Food
        String query3 = "create table if not EXISTS " + FoodDAO.TABLE +
                "(" +
                        FoodDAO.ID + " integer primary key autoincrement," +
                        FoodDAO.NAMEFOOD + " text not null default 'No Name'," +
                        FoodDAO.IDCATEGORY + " int not null," +
                        FoodDAO.PRICE + " real not null," +
                        "foreign key(" + FoodDAO.IDCATEGORY + ") references " + FoodCategoryDAO.TABLE + "(" + FoodCategoryDAO.ID + ")" +
                ");";
        db.execSQL(query3);

        // Create Table TableFood
        String query4 = "CREATE TABLE IF NOT EXISTS " + TableFoodDAO.TABLE +
                "(" +
                        TableFoodDAO.ID + " integer primary key autoincrement," +
                        TableFoodDAO.NAMETABLE + " text not null default 'No Name'," +
                        TableFoodDAO.STATUSTABLE + " integer not null default 0" +
                ");";
        db.execSQL(query4);

        // Create Table Bill
        String query5 = "create table if not EXISTS " + BillDAO.TABLE +
                "(" +
                        BillDAO.ID + " integer primary key autoincrement," +
                        BillDAO.IDTABLE + " integer not null," +
                        BillDAO.DATECHECKIN + " datetime," +
                        BillDAO.DATECHECKOUT + " datetime," +
                        BillDAO.STATUSBILL + " integer not null default 0," +
                        "foreign key(" + BillDAO.IDTABLE + ") references " + TableFoodDAO.TABLE + "(" + TableFoodDAO.ID + ")" +
                ");";
        db.execSQL(query5);

        // Create Table BillInfo
        String query6 = "create table if NOT EXISTS " + BillInfoDAO.TABLE +
                "(" +
                        BillInfoDAO.ID + " integer primary key autoincrement," +
                        BillInfoDAO.IDBILL + " integer not null," +
                        BillInfoDAO.IDFOOD + " int Not null," +
                        BillInfoDAO.COUNTFOOD + " integer Not null default 0," +
                        "foreign key(" + BillInfoDAO.IDBILL + ") references " + BillDAO.TABLE + "(" + BillDAO.ID + ")," +
                        "foreign key(" + BillInfoDAO.IDFOOD + ") references " + FoodDAO.TABLE + "(" + FoodDAO.ID + ")" +
                "); ";
        db.execSQL(query6);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + BillDAO.TABLE);
        db.execSQL("drop table if exists " + AccountDAO.TABLE);
        db.execSQL("drop table if exists " + BillInfoDAO.TABLE);
        db.execSQL("drop table if exists " + TableFoodDAO.TABLE);
        db.execSQL("drop table if exists " + FoodDAO.TABLE);
        db.execSQL("drop table if exists " + FoodCategoryDAO.TABLE);
        onCreate(db);
    }
}
