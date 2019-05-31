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

import com.example.admin.quanlycafe.FRAGMENT.BillFragment;
import com.example.admin.quanlycafe.FRAGMENT.TableFragment;
import com.example.admin.quanlycafe.INTERFACE.ItemClickListener;
import com.example.admin.quanlycafe.MODEL.TableFood;
import com.example.admin.quanlycafe.R;

import java.util.List;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.TableItemViewHolder> {
    private List<TableFood> ListTableFood;
    private Fragment fragment;

    public TableAdapter(List<TableFood> ListTableFood, Fragment fragment)
    {
        this.ListTableFood = ListTableFood;
        this.fragment = fragment;
    }
    @NonNull
    @Override
    public TableItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_table, viewGroup, false);
        return new TableItemViewHolder(view);
    }
    public void addItem(TableFood tableFood)
    {
        tableFood.Id(this.ListTableFood.size() + 1);
        this.ListTableFood.add(tableFood);
        notifyItemInserted(this.ListTableFood.size() - 1);
    }
    public void UpdateTable(int idTable, int status)
    {
        int index = 0;
        for(TableFood tableFood : this.ListTableFood)
        {
            if(tableFood.Id() == idTable)
            {
                TableFood newTable = new TableFood(tableFood.Id(),tableFood.NameTable(), status);
                this.ListTableFood.set(index, newTable);

                notifyItemChanged(index);
                break;
            }
            index++;
        }
    }
    private void ReplaceFragment(Fragment fragment)
    {
        FragmentTransaction ft = this.fragment.getFragmentManager().beginTransaction();
        ft.hide(this.fragment);
        ft.add(R.id.container_body, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }
    @Override
    public void onBindViewHolder(@NonNull TableItemViewHolder tableItemViewHolder, int i) {
        final TableFood tableFood = this.ListTableFood.get(i);
        tableItemViewHolder.tvNameTable.setText("Tên Bàn: " + tableFood.NameTable());
        String status = "";
        if (tableFood.Status() == 1)
        {
            status = "Đang Có Người";
        }
        else
        {
            status = "Không Có Người";
        }
        tableItemViewHolder.tvStatus.setText("Trạng Thái: " + status);

        tableItemViewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void OnClick(View view, int position, boolean isLongClick) {
                if(isLongClick)
                { }
                else {
                    Bundle bundle = new Bundle();
                    bundle.putInt("idTable", tableFood.Id());
                    bundle.putCharSequence("tableName", tableFood.NameTable());
                    bundle.putInt("status", tableFood.Status());
                    BillFragment.Instance().setArguments(bundle);
                    BillFragment.Instance().setUpdateTableStatusTable(TableFragment.Instance().getUpdateTableStatusTable());
                    ReplaceFragment(BillFragment.Instance());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.ListTableFood.size();
    }

    public static class TableItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        private TextView tvNameTable;
        private TextView tvStatus;

        private ItemClickListener itemClickListener;
        public void setItemClickListener(ItemClickListener itemClickListener)
        {
            this.itemClickListener = itemClickListener;
        }

        public TableItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameTable = (TextView)itemView.findViewById(R.id.tv_nameTable);
            tvStatus = (TextView)itemView.findViewById(R.id.tv_status);
            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.OnClick(v, getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View v) {
            this.itemClickListener.OnClick(v, getAdapterPosition(), true);
            return true;
        }
    }
}
