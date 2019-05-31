package com.example.admin.quanlycafe.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.admin.quanlycafe.DATA.SqlHelper;
import com.example.admin.quanlycafe.INTERFACE.FoodCategoryInterface;
import com.example.admin.quanlycafe.MODEL.FoodCategory;

import java.util.ArrayList;
import java.util.List;

public class FoodCategoryDAO implements FoodCategoryInterface {
    public static final String TABLE = "FoodCategory";
    public static final String ID = "_id";
    public static final String NAMECATEGORY = "NameCategory";

    private SqlHelper sqlHelper;
    private Context context;
    private SQLiteDatabase db;

    public FoodCategoryDAO(Context context)
    {
        this.context = context;
        sqlHelper = SqlHelper.Instance(context);
    }

    @Override
    public List<FoodCategory> GetFoodCategory() {
        List<FoodCategory> list = new ArrayList<>();
        db = sqlHelper.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE + ";";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst())
        {
            do{
                int idCategory = cursor.getInt(0);
                String nameCategory = cursor.getString(1);
                FoodCategory foodCategory = new FoodCategory(idCategory,nameCategory);
                list.add(foodCategory);
            }while (cursor.moveToNext());
        }
        return list;
    }

    @Override
    public void InsertFoodCategory(FoodCategory foodCategory) {
        db = sqlHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAMECATEGORY, foodCategory.NameCategory());
        db.insert(TABLE, null, contentValues);
    }

    @Override
    public void DeleteFoodCategory(int id) {

    }

    @Override
    public void UpdateFoodCategory(FoodCategory foodCategory) {

    }
}
