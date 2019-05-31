package com.example.admin.quanlycafe.INTERFACE;

import com.example.admin.quanlycafe.MODEL.TableFood;

import java.util.List;

public interface TableFoodInterface {
    public List<TableFood> GetTableFood();
    public void InsertTableFood(TableFood tableFood);
    public void DeleteTableFood(int id);
    public void UpdateTableFood(int idTable, int status);
}
