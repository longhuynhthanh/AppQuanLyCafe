package com.example.admin.quanlycafe.MODEL;

public class MenuFood {
    private int idFood;
    private int idBill;
    private String nameFood;
    private int countFood;
    private double priceFood;
    private double totalPrice;

    public int IdFood() {
        return idFood;
    }

    public void IdFood(int idFood) {
        this.idFood = idFood;
    }

    public int IdBill() {
        return idBill;
    }

    public void IdBill(int idBill) {
        this.idBill = idBill;
    }

    public String NameFood() {

        return nameFood;

    }

    public void NameFood(String nameFood) {
        this.nameFood = nameFood;
    }

    public int getCountFood() {
        return countFood;
    }

    public void CountFood(int countFood) {
        this.countFood = countFood;
    }

    public double PriceFood() {
        return priceFood;
    }

    public void PriceFood(double priceFood) {
        this.priceFood = priceFood;
    }

    public double TotalPrice() {
        return totalPrice;
    }

    public void TotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public MenuFood(int idFood, int idBill ,String nameFood, int countFood, double priceFood, double totalPrice) {
        this.idFood = idFood;
        this.idBill = idBill;
        this.nameFood = nameFood;
        this.countFood = countFood;
        this.priceFood = priceFood;
        this.totalPrice = totalPrice;
    }
}
