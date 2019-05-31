package com.example.admin.quanlycafe.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.widget.Toast;

import com.example.admin.quanlycafe.DATA.SqlHelper;
import com.example.admin.quanlycafe.INTERFACE.BillInterface;
import com.example.admin.quanlycafe.MODEL.Bill;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BillDAO implements BillInterface {
    public static final String TABLE = "Bill";
    public static final String ID = "_id";
    public static final String IDTABLE = "IdTable";
    public static final String DATECHECKIN = "DateCheckIn";
    public static final String DATECHECKOUT = "DateCheckOut";
    public static final String STATUSBILL = "StatusBill";

    private SQLiteDatabase db;
    private Context context;
    private SqlHelper sqlHelper;

    public BillDAO(Context context)
    {
        this.context = context;
        sqlHelper = SqlHelper.Instance(context);
    }

    @Override
    public int GetBillByTableID(int idTable) {
        List<Integer> list = new ArrayList<>();
        db = sqlHelper.getReadableDatabase();
        String sql = "SELECT * FROM " + BillDAO.TABLE + " " +
                "WHERE " + BillDAO.IDTABLE + " = " + idTable +" AND " + BillDAO.STATUSBILL + " = 0";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst())
        {
            do {
                int id = cursor.getInt(0);
                list.add(id);
            }while(cursor.moveToNext());
        }
        if(list.size() > 0)
        {
            return list.get(0);
        }
        return -1;
    }

    @Override
    public int GetMaxBillID()
    {
        int max = 1;
        db = sqlHelper.getReadableDatabase();
        String sql = "SELECT MAX(" + BillDAO.ID + ") FROM " + BillDAO.TABLE + ";";
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            max = cursor.getInt(0);
        }
        return max;
    }

    @Override
    public void CheckOut(int idBill) {
        db = sqlHelper.getWritableDatabase();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BillDAO.STATUSBILL, 1);
        contentValues.put(BillDAO.DATECHECKOUT, dateFormat.format(date));
        db.update(BillDAO.TABLE, contentValues, BillDAO.ID + " = ? ", new String[]{idBill + ""});
    }

    @Override
    public void InsertBill(int idTable) {
        db = sqlHelper.getWritableDatabase();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BillDAO.IDTABLE, idTable);
        contentValues.put(BillDAO.DATECHECKIN, dateFormat.format(date));
        db.insert(BillDAO.TABLE, null, contentValues);
    }

    @Override
    public void DeleteBill(int id) {

    }

    @Override
    public void UpdateStatusBill(int id) {
        db = sqlHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BillDAO.STATUSBILL, 1);
        db.update(BillDAO.TABLE, contentValues, BillDAO.ID + " = ? ", new String[]{id + ""});
    }
}
