package com.example.admin.quanlycafe.MODEL;

public class Food {
    private int id;
    private String NameFood;
    private int idCategory;
    private double Price;

    public Food() {
    }

    public int Id() {
        return id;
    }

    public void Id(int id) {
        this.id = id;
    }

    public String NameFood() {
        return NameFood;
    }

    public void NameFood(String nameFood) {
        NameFood = nameFood;
    }

    public int IdCategory() {
        return idCategory;
    }

    public void IdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public double Price() {
        return Price;
    }

    public void Price(double price) {
        Price = price;
    }

    public Food(int id, String nameFood, int idCategory, double price) {
        this.id = id;
        NameFood = nameFood;
        this.idCategory = idCategory;
        Price = price;
    }

    public Food(String nameFood, int idCategory, double price) {
        NameFood = nameFood;
        this.idCategory = idCategory;
        Price = price;
    }
}
