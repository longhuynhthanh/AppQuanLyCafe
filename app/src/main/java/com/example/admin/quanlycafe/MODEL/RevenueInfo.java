package com.example.admin.quanlycafe.MODEL;

public class RevenueInfo {
    private String nameFood;
    private double price;
    private int count;
    private double totalPrice;

    public RevenueInfo(String nameFood, double price, int count, double totalPrice) {
        this.nameFood = nameFood;
        this.price = price;
        this.count = count;
        this.totalPrice = totalPrice;
    }

    public String getNameFood() {
        return nameFood;
    }

    public void setNameFood(String nameFood) {
        this.nameFood = nameFood;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
