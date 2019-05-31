package com.example.admin.quanlycafe.MODEL;

public class Account {
    private String UserName;
    private String DisplayName;
    private String PasswordAccount;
    private int Type;

    public Account() {
    }

    public String UserName() {
        return UserName;
    }

    public void UserName(String userName) {
        UserName = userName;
    }

    public String DisplayName() {
        return DisplayName;
    }

    public void DisplayName(String displayName) {
        DisplayName = displayName;
    }

    public String PasswordAccount() {
        return PasswordAccount;
    }

    public void PasswordAccount(String passwordAccount) {
        PasswordAccount = passwordAccount;
    }

    public int Type() {
        return Type;
    }

    public void Type(int type) {
        Type = type;
    }

    public Account(String userName, String displayName, String passwordAccount, int type) {
        UserName = userName;
        DisplayName = displayName;
        PasswordAccount = passwordAccount;
        Type = type;
    }
}
