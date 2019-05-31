package com.example.admin.quanlycafe.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.admin.quanlycafe.DATA.SqlHelper;
import com.example.admin.quanlycafe.INTERFACE.FoodInterface;
import com.example.admin.quanlycafe.MODEL.Food;

import java.util.ArrayList;
import java.util.List;

public class FoodDAO implements FoodInterface {
    public static final String TABLE = "Food";
    public static final String ID = "_id";
    public static final String NAMEFOOD = "NameFood";
    public static final String IDCATEGORY = "IdCategory";
    public static final String PRICE = "Price";

    private SQLiteDatabase db;
    private Context context;
    private SqlHelper sqlHelper;

    public FoodDAO(Context context)
    {
        this.context = context;
        sqlHelper = SqlHelper.Instance(context);
    }

    @Override
    public List<Food> GetFood(int idCategory) {
        List<Food> ListFood = new ArrayList<>();
        db = sqlHelper.getReadableDatabase();
        String sql =    "SELECT " + FoodDAO.ID + ", " + FoodDAO.NAMEFOOD + ", " + FoodDAO.PRICE + " FROM  " + FoodDAO.TABLE +
                        " WHERE " + FoodDAO.IDCATEGORY + " = " + idCategory + ";";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst())
        {
            do{
                int id = cursor.getInt(0);
                String nameFood = cursor.getString(1);
                double price = cursor.getDouble(2);
                Food food = new Food(id, nameFood, idCategory, price);
                ListFood.add(food);
            }while(cursor.moveToNext());
        }
        return ListFood;
    }

    @Override
    public void InsertFood(Food food) {
        db = sqlHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FoodDAO.NAMEFOOD, food.NameFood());
        contentValues.put(FoodDAO.IDCATEGORY, food.IdCategory());
        contentValues.put(FoodDAO.PRICE, food.Price());
        db.insert(TABLE, null, contentValues);
    }

    @Override
    public void DeleteFood(int id) {

    }

    @Override
    public void UpdateFood(Food food) {
        db = sqlHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FoodDAO.NAMEFOOD, food.NameFood());
        contentValues.put(FoodDAO.IDCATEGORY, food.IdCategory());
        contentValues.put(FoodDAO.PRICE, food.Price());
        db.update(FoodDAO.TABLE, contentValues, FoodDAO.ID + " = ?", new String[]{food.Id() + ""});
    }

    @Override
    public List<Food> GetFoodNotInListIdFood(int idCategory, List<Integer> list) {
        List<Food> ListFood = new ArrayList<>();
        db = sqlHelper.getReadableDatabase();

        String sql = "";
        if(list.size() > 0)
        {
            String s = "";
            for(int i : list)
            {
                if(list.size() > 0)
                {
                    s += ("," + i);
                }
            }
            StringBuffer stringBuffer = new StringBuffer(s);
            stringBuffer.deleteCharAt(0);
            sql = "SELECT " + FoodDAO.ID + ", " + FoodDAO.NAMEFOOD + ", " + FoodDAO.PRICE + " FROM  " + FoodDAO.TABLE +
                    " WHERE " + FoodDAO.IDCATEGORY + " = " + idCategory + " AND " + FoodDAO.ID + " NOT IN (" + stringBuffer + ");";
        }
        else
        {
            sql = "SELECT " + FoodDAO.ID + ", " + FoodDAO.NAMEFOOD + ", " + FoodDAO.PRICE + " FROM  " + FoodDAO.TABLE +
                    " WHERE " + FoodDAO.IDCATEGORY + " = " + idCategory + ";";
        }
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst())
        {
            do{
                int id = cursor.getInt(0);
                String nameFood = cursor.getString(1);
                double price = cursor.getDouble(2);
                Food food = new Food(id, nameFood, idCategory, price);
                ListFood.add(food);
            }while(cursor.moveToNext());
        }
        return ListFood;
    }
}
