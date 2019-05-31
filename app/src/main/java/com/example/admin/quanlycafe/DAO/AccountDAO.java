package com.example.admin.quanlycafe.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.admin.quanlycafe.DATA.SqlHelper;
import com.example.admin.quanlycafe.INTERFACE.AccountInterface;
import com.example.admin.quanlycafe.MODEL.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountDAO implements AccountInterface {
    public static final String TABLE = "Account";
    public static final String USER_NAME = "UserName";
    public static final String DISPLAY_NAME = "DisplayName";
    public static final String PASSWORD = "Password";
    public static final String TYPE_ACCOUNT = "TypeAccount";

    private SQLiteDatabase db;
    private Context context;
    private SqlHelper sqlHelper;

    public AccountDAO(Context context)
    {
        this.context = context;
        sqlHelper = SqlHelper.Instance(context);
    }

    @Override
    public List<Account> GetAccount() {
        List<Account> list = new ArrayList<>();
        db = sqlHelper.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE +
                " WHERE " + AccountDAO.TYPE_ACCOUNT + " = 0"
                + ";";
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            do {
                String userName = cursor.getString(0);
                String displayName = cursor.getString(1);
                String passWord = cursor.getString(2);
                int type = cursor.getInt(3);
                Account account = new Account(userName, displayName, passWord, type);
                list.add(account);
            }while(cursor.moveToNext());
        }
        return list;
    }

    @Override
    public List<Account> GetAllAccount() {
        List<Account> list = new ArrayList<>();
        db = sqlHelper.getReadableDatabase();
        String sql = "SELECT * FROM " + AccountDAO.TABLE
                + ";";
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst())
        {
            do {
                String userName = cursor.getString(0);
                String displayName = cursor.getString(1);
                String passWord = cursor.getString(2);
                int type = cursor.getInt(3);
                Account account = new Account(userName, displayName, passWord, type);
                list.add(account);
            }while(cursor.moveToNext());
        }
        return list;
    }

    @Override
    public void InsertAccount(Account account) {
        db = sqlHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(AccountDAO.USER_NAME, account.UserName());
        contentValues.put(AccountDAO.DISPLAY_NAME, account.DisplayName());
        contentValues.put(AccountDAO.PASSWORD, account.PasswordAccount());
        contentValues.put(AccountDAO.TYPE_ACCOUNT, account.Type());
        db.insert(AccountDAO.TABLE, null, contentValues);
    }

    @Override
    public void DeleteAccount(String s) {
        db = sqlHelper.getWritableDatabase();
        db.delete(AccountDAO.TABLE, AccountDAO.USER_NAME + " = ? ", new String[]{s});
    }

    @Override
    public void UpdateAccount(Account account) {
        db = sqlHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(AccountDAO.DISPLAY_NAME, account.DisplayName());
        contentValues.put(AccountDAO.PASSWORD, account.PasswordAccount());
        contentValues.put(AccountDAO.TYPE_ACCOUNT, account.Type());
        db.update(AccountDAO.TABLE, contentValues, AccountDAO.USER_NAME + " = ? ", new String[]{account.UserName()});
    }
}
