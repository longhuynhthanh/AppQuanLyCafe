package com.example.admin.quanlycafe.INTERFACE;

import com.example.admin.quanlycafe.MODEL.Food;

import java.util.List;

public interface FoodInterface {
    public List<Food> GetFood(int idCategory);
    public void InsertFood(Food food);
    public void DeleteFood(int id);
    public void UpdateFood(Food food);
    public List<Food> GetFoodNotInListIdFood(int idCategory, List<Integer> list);
}
