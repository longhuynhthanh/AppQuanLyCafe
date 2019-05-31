package com.example.admin.quanlycafe.FRAGMENT;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.admin.quanlycafe.ADAPTER.RevenueInfoAdapter;
import com.example.admin.quanlycafe.DAO.RevenueDAO;
import com.example.admin.quanlycafe.MODEL.RevenueInfo;
import com.example.admin.quanlycafe.R;

import java.util.List;

public class RevenueInfoFragment extends Fragment {
    private static RevenueInfoFragment instance;
    public static RevenueInfoFragment Instance()
    {
        if(instance == null)
        {
            instance = new RevenueInfoFragment();
        }
        return instance;
    }

    private RecyclerView rv_Revenue_info;
    private List<RevenueInfo> revenueInfoList;
    private Button totalPrice;
    private RevenueDAO revenueDAO;
    private RevenueInfoAdapter revenueInfoAdapter;
    private int idBill = 0;
    private double total;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.getActivity().setTitle("Món Ăn Trong Bill");
        View view = inflater.inflate(R.layout.fragment_revenue_info, container, false);

        Getdata();

        rv_Revenue_info = (RecyclerView)view.findViewById(R.id.rv_revenue_info);
        rv_Revenue_info.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        rv_Revenue_info.setHasFixedSize(true);
        totalPrice = (Button)view.findViewById(R.id.totalBill_info);
        totalPrice.setText("Tổng Bill: " + total + " VNĐ");
        revenueDAO = new RevenueDAO(this.getContext());
        UpdateInterface();

        return view;
    }

    public void UpdateInterface()
    {
        revenueInfoList = revenueDAO.getByIDBill(idBill);
        revenueInfoAdapter = new RevenueInfoAdapter(revenueInfoList, this);
        rv_Revenue_info.setAdapter(revenueInfoAdapter);
    }
    private void Getdata()
    {
        Bundle bundle = getArguments();
        idBill = bundle.getInt("idBill");
        total = bundle.getDouble("totalPrice");
    }
}
