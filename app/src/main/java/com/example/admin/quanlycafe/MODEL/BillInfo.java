package com.example.admin.quanlycafe.MODEL;

public class BillInfo {
    private int id;
    private int idBill;
    private int idFood;
    private int CountFood;

    public int Id() {
        return id;
    }

    public void Id(int id) {
        this.id = id;
    }

    public int IdBill() {
        return idBill;
    }

    public void IdBill(int idBill) {
        this.idBill = idBill;
    }

    public int IdFood() {
        return idFood;
    }

    public void IdFood(int idFood) {
        this.idFood = idFood;
    }

    public int CountFood() {
        return CountFood;
    }

    public void CountFood(int countFood) {
        CountFood = countFood;
    }

    public BillInfo(int id, int idBill, int idFood, int countFood) {
        this.id = id;
        this.idBill = idBill;
        this.idFood = idFood;
        CountFood = countFood;
    }
}
