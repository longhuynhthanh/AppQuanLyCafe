package com.example.admin.quanlycafe.INTERFACE;

import com.example.admin.quanlycafe.MODEL.FoodCategory;

import java.util.List;

public interface FoodCategoryInterface {
    public List<FoodCategory> GetFoodCategory();
    public void InsertFoodCategory(FoodCategory foodCategory);
    public void DeleteFoodCategory(int id);
    public void UpdateFoodCategory(FoodCategory foodCategory);
}
