package com.example.admin.quanlycafe.INTERFACE;

import com.example.admin.quanlycafe.MODEL.BillInfo;

import java.util.List;

public interface BillInfoInterface {
    public List<BillInfo> GetBillInfo(int idBill);
    public void InsertBillInfo(int idBill, int idFood, int count);
    public void DeleteBillInfo(int idBill, int idFood);
    public void UpdateCountFood(int idBill, int idFood, int newCount);
}
