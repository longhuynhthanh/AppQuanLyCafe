package com.example.admin.quanlycafe.ADAPTER;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.admin.quanlycafe.DAO.FoodDAO;
import com.example.admin.quanlycafe.FRAGMENT.CakeFragment;
import com.example.admin.quanlycafe.FRAGMENT.CoffeeFragment;
import com.example.admin.quanlycafe.FRAGMENT.FoodsFragment;
import com.example.admin.quanlycafe.FRAGMENT.MilkTeaFragment;
import com.example.admin.quanlycafe.FRAGMENT.SodaFragment;
import com.example.admin.quanlycafe.INTERFACE.FoodInterface;
import com.example.admin.quanlycafe.INTERFACE.ItemClickListener;
import com.example.admin.quanlycafe.MODEL.Food;
import com.example.admin.quanlycafe.R;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    private List<Food> ListFood;
    private Fragment fragment;
    private int type;

    public FoodAdapter(List<Food> list, Fragment fragment, int type)
    {
        this.ListFood = list;
        this.fragment = fragment;
        this.type = type;
    }
    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_food, viewGroup, false);
        return new FoodViewHolder(view);
    }
    public void AddItem(Food food)
    {
        this.ListFood.add(food);
        notifyItemInserted(this.ListFood.size() - 1);
    }
    public void UpdateItem(Food food)
    {
        int index = 0;
        for (Food f : this.ListFood)
        {
            if (f.Id() == food.Id())
            {
                if(f.IdCategory() != food.IdCategory())
                {
                    notifyItemRemoved(index);
                    this.ListFood.remove(index);
                }
                else
                {
                    this.ListFood.set(index, food);
                    notifyItemChanged(index);
                }

                if(food.IdCategory() == 1)
                {
                    CoffeeFragment.Instance().UpdateFood(food);
                }
                else if(food.IdCategory() == 2)
                {
                    MilkTeaFragment.Instance().UpdateFood(food);
                }
                else if(f.IdCategory() == 3)
                {
                    SodaFragment.Instance().UpdateFood(food);
                }
                else if(food.IdCategory() == 4)
                {
                    CakeFragment.Instance().UpdateFood(food);
                }
                break;
            }
            index++;
        }
    }

    int idCategory = 0;
    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder foodViewHolder, int i) {
        final Food food = this.ListFood.get(i);
        foodViewHolder.tvNameFood.setText(food.NameFood());
        foodViewHolder.tvPrice.setText(food.Price() + " ₫");
        if(this.type == 1)
        {
            foodViewHolder.setItemClickListener(new ItemClickListener() {
                @Override
                public void OnClick(View view, int position, boolean isLongClick) {
                    if (isLongClick)
                    {}
                    else
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getActivity());
                        view = fragment.getActivity().getLayoutInflater().inflate(R.layout.dialog_food, null);
                        TextView NameDialog = (TextView)view.findViewById(R.id.NameDialog);
                        NameDialog.setText("CẬP NHẬT MÓN ĂN");
                        final TextView tvID = (TextView)view.findViewById(R.id.tvIdDialog);
                        tvID.setText(food.Id() + "");
                        final EditText tvNameFood = (EditText)view.findViewById(R.id.etNameFoodDA);
                        tvNameFood.setText(food.NameFood());
                        final EditText tvPrice = (EditText)view.findViewById(R.id.etPriceDA);
                        tvPrice.setText(food.Price() + "");
                        Spinner spn_Category = (Spinner)view.findViewById(R.id.spnCategoryDA);
                        CategorySpinnerAdapter adapter = new CategorySpinnerAdapter(fragment, android.R.layout.simple_spinner_item, FoodsFragment.list);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spn_Category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                idCategory = FoodsFragment.list.get(position).Id();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        spn_Category.setAdapter(adapter);

                        builder.setView(view);
                        builder.setNegativeButton("Update", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Food updateFood = new Food(Integer.parseInt(tvID.getText().toString()),
                                        tvNameFood.getText().toString(),
                                        idCategory,
                                        Double.parseDouble(tvPrice.getText().toString()));
                                UpdateItem(updateFood);

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
        else
        {
            foodViewHolder.setItemClickListener(new ItemClickListener() {
                @Override
                public void OnClick(View view, int position, boolean isLongClick) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return this.ListFood.size();
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        private TextView tvNameFood;
        private TextView tvPrice;
        private ItemClickListener itemClickListener;

        public void setItemClickListener(ItemClickListener itemClickListener)
        {
            this.itemClickListener = itemClickListener;
        }

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameFood = (TextView)itemView.findViewById(R.id.tv_nameFood);
            tvPrice = (TextView)itemView.findViewById(R.id.tv_price);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
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
