package com.example.admin.quanlycafe.MODEL;

import java.util.Date;

public class Revenue {
    private int idTable;
    private int idBill;
    private String TableName;
    private Date checkIn;
    private Date checkOut;

    public int getIdTable() {
        return idTable;
    }

    public void setIdTable(int idTable) {
        this.idTable = idTable;
    }

    public int getIdBill() {
        return idBill;
    }

    public void setIdBill(int idBill) {
        this.idBill = idBill;
    }

    public String getTableName() {
        return TableName;
    }

    public void setTableName(String tableName) {
        TableName = tableName;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    private double total;

    public Revenue(int idTable, int idBill, String tableName, Date checkIn, Date checkOut, double total) {
        this.idTable = idTable;
        this.idBill = idBill;
        TableName = tableName;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.total = total;
    }
}
