package com.example.admin.quanlycafe.INTERFACE;

import com.example.admin.quanlycafe.MODEL.Bill;

import java.util.Date;
import java.util.List;

public interface BillInterface {
    public int GetBillByTableID(int idTable);
    public void InsertBill(int idTable);
    public void DeleteBill(int id);
    public void UpdateStatusBill(int id);
    public int GetMaxBillID();
    public void CheckOut(int idBill);
}
