package com.example.admin.quanlycafe.INTERFACE;

import com.example.admin.quanlycafe.MODEL.Account;

import java.util.List;

public interface AccountInterface {
    public List<Account> GetAccount();
    public List<Account> GetAllAccount();
    public void InsertAccount(Account account);
    public void DeleteAccount(String s);
    public void UpdateAccount(Account account);
}
