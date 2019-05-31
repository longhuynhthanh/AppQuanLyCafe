package com.example.admin.quanlycafe.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.admin.quanlycafe.DATA.SqlHelper;
import com.example.admin.quanlycafe.INTERFACE.BillInfoInterface;
import com.example.admin.quanlycafe.MODEL.BillInfo;

import java.util.ArrayList;
import java.util.List;

public class BillInfoDAO implements BillInfoInterface {
    public static final String TABLE = "BillInfo";
    public static final String ID = "_id";
    public static final String IDBILL = "IdBill";
    public static final String IDFOOD = "IdFood";
    public static final String COUNTFOOD = "CountFood";

    private SQLiteDatabase db;
    private Context context;
    private SqlHelper sqlHelper;

    public BillInfoDAO(Context context)
    {
        this.context = context;
        sqlHelper = SqlHelper.Instance(context);
    }
    @Override
    public List<BillInfo> GetBillInfo(int idBill) {
        List<BillInfo> ListBillInfo = new ArrayList<>();
        db = sqlHelper.getReadableDatabase();
        String sql = "SELECT * FROM " + BillInfoDAO.TABLE +
                " WHERE " + BillInfoDAO.IDBILL + " = " + idBill + ";";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst())
        {
            do {
                int id = cursor.getInt(0);
                int idFood = cursor.getInt(2);
                int countFood = cursor.getInt(3);
                BillInfo billInfo = new BillInfo(id, idBill, idFood, countFood);
                ListBillInfo.add(billInfo);
            }while (cursor.moveToNext());
        }

        return ListBillInfo;
    }

    @Override
    public void InsertBillInfo(int idBill, int idFood, int count) {
        db = sqlHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BillInfoDAO.IDFOOD, idFood);
        contentValues.put(BillInfoDAO.IDBILL, idBill);
        contentValues.put(BillInfoDAO.COUNTFOOD, count);
        db.insert(BillInfoDAO.TABLE, null, contentValues);
    }

    @Override
    public void DeleteBillInfo(int idBill, int idFood) {
        db = sqlHelper.getWritableDatabase();
        db.delete(BillInfoDAO.TABLE, BillInfoDAO.IDBILL + " = ? AND " + BillInfoDAO.IDFOOD + " = ? ", new String[]{idBill + "", idFood + ""} );
    }
    @Override
    public void UpdateCountFood(int idBill, int idFood, int newCount) {
        db = sqlHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BillInfoDAO.COUNTFOOD, newCount);
        db.update(BillInfoDAO.TABLE, contentValues, BillInfoDAO.IDBILL + " = ? AND " + BillInfoDAO.IDFOOD + " = ? ", new String[]{idBill + "", idFood + ""});
    }
}
