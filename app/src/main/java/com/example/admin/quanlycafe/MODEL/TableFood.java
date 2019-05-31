package com.example.admin.quanlycafe.MODEL;

public class TableFood {
    private int id;
    private String nameTable;
    private int status;

    public int Id() {
        return id;
    }

    public void Id(int id) {
        this.id = id;
    }

    public String NameTable() {
        return nameTable;
    }

    public void NameTable(String nameTable) {
        this.nameTable = nameTable;
    }

    public int Status() {
        return status;
    }

    public void Status(int status) {
        this.status = status;
    }

    public TableFood(int id, String nameTable, int status) {
        this.id = id;
        this.nameTable = nameTable;
        this.status = status;
    }

    public TableFood(String nameTable, int status) {
        this.nameTable = nameTable;
        this.status = status;
    }

    public TableFood(String nameTable) {
        this.nameTable = nameTable;
    }

    public TableFood() {

    }
}
