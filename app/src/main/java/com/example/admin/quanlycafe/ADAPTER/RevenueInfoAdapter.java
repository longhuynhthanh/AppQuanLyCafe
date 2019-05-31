package com.example.admin.quanlycafe.ADAPTER;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.quanlycafe.MODEL.RevenueInfo;
import com.example.admin.quanlycafe.R;

import java.util.List;

public class RevenueInfoAdapter extends RecyclerView.Adapter<RevenueInfoAdapter.RevenueInfoViewHolder>  {
    private List<RevenueInfo> revenueInfoList;
    private Fragment fragment;

    public RevenueInfoAdapter(List<RevenueInfo> list, Fragment fragment)
    {
        this.revenueInfoList = list;
        this.fragment = fragment;
    }
    @NonNull
    @Override
    public RevenueInfoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_revenue_info, viewGroup, false);
        return new RevenueInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RevenueInfoViewHolder revenueInfoViewHolder, int i) {
        final RevenueInfo revenueInfo = this.revenueInfoList.get(i);
        revenueInfoViewHolder.nameFood.setText("Tên Món: " + revenueInfo.getNameFood());
        revenueInfoViewHolder.price.setText("Giá: " + revenueInfo.getPrice() + " VNĐ");
        revenueInfoViewHolder.count.setText("Số Lượng: " + revenueInfo.getCount() + " VNĐ/1 Ly");
        revenueInfoViewHolder.totalPrice.setText("Tiền: " + revenueInfo.getTotalPrice() + " VNĐ");
    }

    @Override
    public int getItemCount() {
        return this.revenueInfoList.size();
    }

    public static class RevenueInfoViewHolder extends RecyclerView.ViewHolder {
        private TextView nameFood;
        private TextView price;
        private TextView count;
        private TextView totalPrice;
        public RevenueInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            nameFood = (TextView)itemView.findViewById(R.id.tv_nameFood_revenue);
            price = (TextView)itemView.findViewById(R.id.tv_price_revenue);
            count = (TextView)itemView.findViewById(R.id.tv_countFood_revenue);
            totalPrice = (TextView)itemView.findViewById(R.id.tv_totalPrice_revenue);
        }
    }
}