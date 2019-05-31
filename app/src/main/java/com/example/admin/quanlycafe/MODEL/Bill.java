package com.example.admin.quanlycafe.MODEL;

import java.util.Date;

public class Bill {
    private int id;
    private int idTable;
    private Date DateCheckin;
    private Date DateCheckout;
    private int status;

    public int Id() {
        return id;
    }

    public void Id(int id) {
        this.id = id;
    }

    public int IdTable() {
        return idTable;
    }

    public void IdTable(int idTable) {
        this.idTable = idTable;
    }

    public Date DateCheckin() {
        return DateCheckin;
    }

    public void DateCheckin(Date dateCheckin) {
        DateCheckin = dateCheckin;
    }

    public Date DateCheckout() {
        return DateCheckout;
    }

    public void DateCheckout(Date dateCheckout) {
        DateCheckout = dateCheckout;
    }

    public int Status() {
        return status;
    }

    public void Status(int status) {
        this.status = status;
    }

    public Bill(int id, int idTable, Date dateCheckin, Date dateCheckout, int status) {
        this.id = id;
        this.idTable = idTable;
        DateCheckin = dateCheckin;
        DateCheckout = dateCheckout;
        this.status = status;

    }
}
