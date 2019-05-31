package com.example.admin.quanlycafe.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.admin.quanlycafe.DATA.SqlHelper;
import com.example.admin.quanlycafe.MODEL.Revenue;
import com.example.admin.quanlycafe.MODEL.RevenueInfo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RevenueDAO {
    private SQLiteDatabase db;
    private Context context;
    private SqlHelper sqlHelper;

    public RevenueDAO(Context context)
    {
        this.context = context;
        sqlHelper = SqlHelper.Instance(context);
    }

    public List<Revenue> GetRevenueByDate(String date)
    {
        List<Revenue> list = new ArrayList<>();
        db = sqlHelper.getReadableDatabase();
        String sql = "SELECT " + TableFoodDAO.TABLE + "." + TableFoodDAO.ID + ", b." + BillDAO.ID + ", "
                + TableFoodDAO.TABLE + "." + TableFoodDAO.NAMETABLE + ", b." + BillDAO.DATECHECKIN + ", b." + BillDAO.DATECHECKOUT +
                ", (SELECT SUM(" + FoodDAO.TABLE + "." + FoodDAO.PRICE + "*" + BillInfoDAO.TABLE + "." + BillInfoDAO.COUNTFOOD + ")"
                + " FROM " + FoodDAO.TABLE + ", " + BillDAO.TABLE + ", " + BillInfoDAO.TABLE
                + " WHERE " + FoodDAO.TABLE + "." + FoodDAO.ID + " = " + BillInfoDAO.TABLE + "." + BillInfoDAO.IDFOOD + " AND " + BillDAO.TABLE + "." + BillDAO.ID + " = " + BillInfoDAO.TABLE + "." + BillInfoDAO.IDBILL + " AND " + BillDAO.TABLE + "." + BillDAO.STATUSBILL + " = 1 AND " + BillDAO.TABLE + "." + BillDAO.ID + " = b." + BillDAO.ID +
"                    )" +
                " FROM " + TableFoodDAO.TABLE + ", " + BillDAO.TABLE + " AS b" +
                " WHERE " + TableFoodDAO.TABLE + "." + TableFoodDAO.ID + " = b." + BillDAO.IDTABLE + " AND  strftime('%d', (b." + BillDAO.DATECHECKOUT + ")) = strftime('%d', ('" + date + "'));";
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            do {
                int idTable = cursor.getInt(0);
                int idBill = cursor.getInt(1);
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String tableName = cursor.getString(2);
                Date checkIn = new Date();
                Date checkOut = new Date();
                try {
                    checkIn = dateFormat.parse(cursor.getString(3));
                    checkOut = dateFormat.parse(cursor.getString(4));
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }
                double total = cursor.getDouble(5);
                Revenue revenue = new Revenue(idTable, idBill,tableName, checkIn, checkOut, total);
                list.add(revenue);
            }while(cursor.moveToNext());
        }
        return list;
    }

    public double totalMonth(String date)
    {
        double total = 0;
        db = sqlHelper.getReadableDatabase();
        String sql = "SELECT SUM(f." + FoodDAO.PRICE + "*bi." + BillInfoDAO.COUNTFOOD + ") " +
                " FROM " + BillInfoDAO.TABLE + " AS bi, " + BillDAO.TABLE + " AS b, " + FoodDAO.TABLE + " AS f" +
                " WHERE bi." + BillInfoDAO.IDFOOD + " = f." + FoodDAO.ID + " AND bi." + BillInfoDAO.IDBILL + " = b." + BillDAO.ID + " AND b." + BillDAO.STATUSBILL + " = 1 AND strftime('%m', b." + BillDAO.DATECHECKOUT + ") = strftime('%m', '" + date + "')";
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            total = cursor.getDouble(0);
        }
        return total;
    }

    public List<RevenueInfo> getByIDBill(int idBill)
    {
        db = sqlHelper.getReadableDatabase();
        List<RevenueInfo> list = new ArrayList<>();
        String sql = "SELECT f." + FoodDAO.NAMEFOOD + ", f." + FoodDAO.PRICE + ", bi." + BillInfoDAO.COUNTFOOD + ", f." + FoodDAO.PRICE + "*bi." + BillInfoDAO.COUNTFOOD +
                " FROM " + FoodDAO.TABLE + " AS f, " + BillDAO.TABLE + " AS b, " + BillInfoDAO.TABLE + " AS bi" +
                " WHERE f." + FoodDAO.ID + " = bi." + BillInfoDAO.IDFOOD + " AND b." + BillDAO.ID + " = bi." + BillInfoDAO.IDBILL + " AND b." + BillDAO.ID + " = " + idBill + " AND b." + BillDAO.STATUSBILL + " = 1;";
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            do {
                String nameFood = cursor.getString(0);
                int count = cursor.getInt(1);
                double price = cursor.getDouble(2);
                double totalPrice = cursor.getDouble(3);
                RevenueInfo revenueInfo = new RevenueInfo(nameFood, price, count, totalPrice);
                list.add(revenueInfo);
            }while (cursor.moveToNext());
        }
        return list;
    }
}
