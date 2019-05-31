package com.example.admin.quanlycafe.ADAPTER;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.quanlycafe.FRAGMENT.RevenueInfoFragment;
import com.example.admin.quanlycafe.INTERFACE.ItemClickListener;
import com.example.admin.quanlycafe.MODEL.Revenue;
import com.example.admin.quanlycafe.R;

import java.util.List;

public class RevenueAdapter extends RecyclerView.Adapter<RevenueAdapter.RevenueViewHolder> {
    private List<Revenue> revenueList;
    private Fragment fragment;

    public RevenueAdapter(List<Revenue> revenues, Fragment fragment)
    {
        this.revenueList = revenues;
        this.fragment = fragment;
    }

    public void UpdateAllItem(List<Revenue> list)
    {
        this.revenueList.clear();
        this.revenueList.addAll(list);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RevenueViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_revenue, viewGroup, false);
        return new RevenueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RevenueViewHolder revenueViewHolder, int i) {
        final Revenue revenue = this.revenueList.get(i);
        revenueViewHolder.idBill.setText("ID Bill: " + revenue.getIdBill());
        revenueViewHolder.tableName.setText("Bàn: " + revenue.getTableName());
        revenueViewHolder.checkIn.setText("Thời Gian Vào: " + revenue.getCheckIn().toString());
        revenueViewHolder.checkOut.setText("Thanh Toán: " + revenue.getCheckOut().toString());
        revenueViewHolder.totalBill.setText("Tổng Bill: " + revenue.getTotal() + " VNĐ");
        revenueViewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void OnClick(View view, int position, boolean isLongClick) {
                if(isLongClick)
                {}
                else {
                    Bundle bundle = new Bundle();
                    bundle.putInt("idBill", revenue.getIdBill());
                    bundle.putDouble("totalPrice", revenue.getTotal());
                    RevenueInfoFragment.Instance().setArguments(bundle);
                    ReplaceFragment(RevenueInfoFragment.Instance());
                }
            }
        });
    }

    private void ReplaceFragment(Fragment fragment)
    {
        FragmentTransaction ft = this.fragment.getFragmentManager().beginTransaction();
        ft.hide(this.fragment);
        ft.replace(R.id.container_body, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public int getItemCount() {
        return this.revenueList.size();
    }

    public static class RevenueViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView idBill;
        private TextView tableName;
        private TextView checkIn;
        private TextView checkOut;
        private TextView totalBill;
        private ItemClickListener itemClickListener;
        public void setItemClickListener(ItemClickListener itemClickListener)
        {
            this.itemClickListener = itemClickListener;
        }
        public RevenueViewHolder(@NonNull View itemView) {
            super(itemView);
            idBill = (TextView)itemView.findViewById(R.id.revenue_idBill);
            tableName = (TextView)itemView.findViewById(R.id.revenue_tableName);
            checkIn = (TextView)itemView.findViewById(R.id.revenue_checkIn);
            checkOut = (TextView)itemView.findViewById(R.id.revenue_checkOut);
            totalBill = (TextView)itemView.findViewById(R.id.revenue_totalBill);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            this.itemClickListener.OnClick(view, getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View view) {
            this.itemClickListener.OnClick(view, getAdapterPosition(), true);
            return true;
        }
    }
}
