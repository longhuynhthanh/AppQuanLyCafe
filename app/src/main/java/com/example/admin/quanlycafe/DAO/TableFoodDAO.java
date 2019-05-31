package com.example.admin.quanlycafe.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.admin.quanlycafe.DATA.SqlHelper;
import com.example.admin.quanlycafe.INTERFACE.TableFoodInterface;
import com.example.admin.quanlycafe.MODEL.TableFood;

import java.util.ArrayList;
import java.util.List;

public class TableFoodDAO implements TableFoodInterface {
    public static final String TABLE = "TableFood";
    public static final String ID = "_id";
    public static final String NAMETABLE = "NameTable";
    public static final String STATUSTABLE = "StatusTable";

    private SqlHelper sqlHelper;
    private Context context;
    private SQLiteDatabase db;

    public TableFoodDAO(Context context)
    {
        this.context = context;
        sqlHelper = SqlHelper.Instance(context);
    }

    @Override
    public List<TableFood> GetTableFood() {
        List<TableFood> ListTableFood = new ArrayList<>();
        db = sqlHelper.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE + ";";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst())
        {
            do{
                int idTable = cursor.getInt(0);
                String nameTable = cursor.getString(1);
                int status = cursor.getInt(2);
                TableFood tableFood = new TableFood(idTable,nameTable, status);
                ListTableFood.add(tableFood);
            }while(cursor.moveToNext());
        }
        return ListTableFood;
    }

    @Override
    public void InsertTableFood(TableFood tableFood) {
        db = sqlHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TableFoodDAO.NAMETABLE, tableFood.NameTable());
        db.insert(TableFoodDAO.TABLE, null, contentValues);
    }

    @Override
    public void DeleteTableFood(int id) {

    }

    @Override
    public void UpdateTableFood(int idTable, int status) {

        db = sqlHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TableFoodDAO.STATUSTABLE, status);
        db.update(TableFoodDAO.TABLE, contentValues, TableFoodDAO.ID + " = ? ", new String[]{idTable + ""});

    }
}
