package com.example.admin.quanlycafe.MODEL;

public class FoodCategory {
    private int id;
    private String NameCategory;

    public int Id() {
        return id;
    }

    public void Id(int id) {
        this.id = id;
    }

    public String NameCategory() {
        return NameCategory;
    }

    public void NameCategory(String nameCategory) {
        NameCategory = nameCategory;
    }

    public FoodCategory(String nameCategory) {
        NameCategory = nameCategory;
    }

    public FoodCategory(int id, String nameCategory) {

        this.id = id;
        NameCategory = nameCategory;
    }
}
