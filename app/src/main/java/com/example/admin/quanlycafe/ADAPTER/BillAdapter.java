package com.example.admin.quanlycafe.ADAPTER;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.admin.quanlycafe.DAO.BillInfoDAO;
import com.example.admin.quanlycafe.FRAGMENT.BillFragment;
import com.example.admin.quanlycafe.INTERFACE.BillInfoInterface;
import com.example.admin.quanlycafe.INTERFACE.ItemClickListener;
import com.example.admin.quanlycafe.INTERFACE.TotalPrice;
import com.example.admin.quanlycafe.MODEL.MenuFood;
import com.example.admin.quanlycafe.R;

import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillViewHolder> {
    private List<MenuFood> menuFoodList;
    private Fragment fragment;
    private double total = 0;
    private TotalPrice totalPrice;

    public BillAdapter(List<MenuFood> list, Fragment fragment, TotalPrice TotalPrice)
    {
        this.menuFoodList = list;
        this.fragment = fragment;
        for (MenuFood menuFood : menuFoodList)
        {
            total += menuFood.TotalPrice();
        }
        TotalPrice.TotalPrice(total);
        this.totalPrice = TotalPrice;
    }
    @NonNull
    @Override
    public BillViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_bill, viewGroup, false);
        return new BillViewHolder(view);
    }

    public void addItem(MenuFood menuFood)
    {
        menuFoodList.add(menuFood);
        notifyItemInserted(menuFoodList.size() - 1);
    }

    public void removeItem(int idBill, int idFood)
    {
      for (int i = 0; i < this.menuFoodList.size(); i++)
      {
          if(this.menuFoodList.get(i).IdFood() == idFood && this.menuFoodList.get(i).IdBill() == idBill)
          {
              total -= this.menuFoodList.get(i).TotalPrice();
              totalPrice.TotalPrice(total);
              this.menuFoodList.remove(i);
              notifyItemRemoved(i);
              break;
          }
      }
    }
    public void updateCountItem(int idBill, int idFood, int count)
    {
        int index = 0;
        for(MenuFood menuFood : this.menuFoodList)
        {
            if(menuFood.IdBill() == idBill && menuFood.IdFood() == idFood)
            {
                total -= menuFood.TotalPrice();
                menuFood.CountFood(count);
                menuFood.TotalPrice(count*menuFood.PriceFood());
                this.menuFoodList.set(index, menuFood);
                total += this.menuFoodList.get(index).TotalPrice();
                totalPrice.TotalPrice(total);
                notifyItemChanged(index);
                break;
            }
            index++;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BillViewHolder billViewHolder, int i) {
        final MenuFood menuFood = this.menuFoodList.get(i);
        billViewHolder.tvFoodName.setText("Tên Món Ăn: " + menuFood.NameFood());
        billViewHolder.tvFoodPrice.setText("Giá: " + menuFood.PriceFood() + " VNĐ/1 Ly");
        billViewHolder.tvCount.setText("Số Lượng: " + menuFood.getCountFood() + "");
        billViewHolder.tvTotalPrice.setText("Tiền: " + menuFood.TotalPrice() + " VNĐ");
        billViewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void OnClick(View view, int position, boolean isLongClick) {
                if(isLongClick)
                {}
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getActivity());
                    view = fragment.getActivity().getLayoutInflater().inflate(R.layout.dialog_bill, null);
                    final EditText etnameFood = (EditText)view.findViewById(R.id.etfoodNameDAB);
                    etnameFood.setText(menuFood.NameFood());
                    final EditText etfoodPrice = (EditText)view.findViewById(R.id.etfoodPriceDAB);
                    etfoodPrice.setText(menuFood.PriceFood() + "");
                    final EditText etfoodCount = (EditText)view.findViewById(R.id.etfoodCountDAB);
                    etfoodCount.setText(menuFood.getCountFood() + "");

                    builder.setView(view);
                    builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            BillInfoInterface billInfoDAO = new BillInfoDAO(fragment.getContext());
                            billInfoDAO.DeleteBillInfo(menuFood.IdBill(), menuFood.IdFood());
                            removeItem(menuFood.IdBill(), menuFood.IdFood());
                        }
                    });
                    builder.setNegativeButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            BillInfoInterface billInfoDAO = new BillInfoDAO(fragment.getContext());
                            billInfoDAO.UpdateCountFood(menuFood.IdBill(), menuFood.IdFood(),
                                    Integer.parseInt(etfoodCount.getText().toString()));
                            updateCountItem(menuFood.IdBill(), menuFood.IdFood(), Integer.parseInt(etfoodCount.getText().toString()));
                        }
                    });
                    builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.menuFoodList.size();
    }

    public static class BillViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
        private TextView tvFoodName;
        private TextView tvFoodPrice;
        private TextView tvCount;
        private TextView tvTotalPrice;
        private ItemClickListener itemClickListener;
        public void setItemClickListener(ItemClickListener itemClickListener)
        {
            this.itemClickListener = itemClickListener;
        }
        public BillViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFoodName = (TextView)itemView.findViewById(R.id.tv_nameFoodB);
            tvFoodPrice = (TextView)itemView.findViewById(R.id.tv_priceB);
            tvCount = (TextView)itemView.findViewById(R.id.tv_countFoodB);
            tvTotalPrice = (TextView)itemView.findViewById(R.id.tv_totalPrice);
            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);
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
