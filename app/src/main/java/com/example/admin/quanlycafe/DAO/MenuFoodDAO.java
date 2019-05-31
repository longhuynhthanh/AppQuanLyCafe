package com.example.admin.quanlycafe.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.admin.quanlycafe.DATA.SqlHelper;
import com.example.admin.quanlycafe.MODEL.MenuFood;

import java.util.ArrayList;
import java.util.List;

public class MenuFoodDAO {
    private SQLiteDatabase db;
    private Context context;
    private SqlHelper sqlHelper;

    public MenuFoodDAO(Context context)
    {
        this.context = context;
        sqlHelper = SqlHelper.Instance(context);
    }

    public List<MenuFood> GetListMenuFoodByTable(int idTable)
    {
        List<MenuFood> list = new ArrayList<>();
        db = sqlHelper.getReadableDatabase();
        String sql = "SELECT " + FoodDAO.TABLE + "." + FoodDAO.ID + ", " + BillDAO.TABLE + "." + BillDAO.ID + ", " + FoodDAO.TABLE + "." + FoodDAO.NAMEFOOD + ", " + BillInfoDAO.TABLE + "." + BillInfoDAO.COUNTFOOD + ", " + FoodDAO.TABLE + "." + FoodDAO.PRICE + ", " + FoodDAO.TABLE + "." + FoodDAO.PRICE + "*" + BillInfoDAO.TABLE + "." + BillInfoDAO.COUNTFOOD + "  " +
                "FROM " + BillInfoDAO.TABLE + ", " + BillDAO.TABLE + ", " + FoodDAO.TABLE + " " +
                "WHERE " + BillInfoDAO.TABLE + "." + BillInfoDAO.IDBILL + " = " + BillDAO.TABLE + "." + BillDAO.ID + " AND " + BillInfoDAO.TABLE + "." + BillInfoDAO.IDFOOD + " = " + FoodDAO.TABLE + "." + FoodDAO.ID + " AND " + BillDAO.TABLE + "." + BillDAO.IDTABLE + " = " + idTable + " AND " + BillDAO.STATUSBILL + " = 0";

        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            do {
                int idFood = cursor.getInt(0);
                int idBill = cursor.getInt(1);
                String nameFood = cursor.getString(2);
                int countFood = cursor.getInt(3);
                double price = cursor.getDouble(4);
                double totalPrice = cursor.getDouble(5);
                MenuFood menuFood = new MenuFood(idFood, idBill, nameFood, countFood, price, totalPrice);
                list.add(menuFood);
            }while (cursor.moveToNext());
        }
        return list;
    }
}
