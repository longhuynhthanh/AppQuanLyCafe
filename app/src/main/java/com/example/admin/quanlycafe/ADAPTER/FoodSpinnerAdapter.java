package com.example.admin.quanlycafe.ADAPTER;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.admin.quanlycafe.MODEL.Food;
import com.example.admin.quanlycafe.R;

import java.util.List;

public class FoodSpinnerAdapter extends ArrayAdapter<Food> {
    private Fragment fragment;
    private List<Food> foodList;
    private LayoutInflater inflater;

    public FoodSpinnerAdapter( Fragment fragment, int resource, List<Food> list) {
        super(fragment.getContext(), resource);
        this.fragment = fragment;
        this.foodList = list;
        inflater = (LayoutInflater)fragment.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return this.foodList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public Food getItem(int position) {
        return this.foodList.get(position);
    }

    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
//        TextView label = (TextView)super.getView(position, convertView, parent);
//        label.setText(this.foodList.get(position).NameFood());
//        return label;
        View view = inflater.inflate(R.layout.row_food_spinner, parent, false);
        TextView label = (TextView)view.findViewById(R.id.spinner_foodName);
        label.setText(this.foodList.get(position).NameFood());
        return view;
    }

    @Override
    public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
        View view = inflater.inflate(R.layout.row_food_spinner, parent, false);
        TextView tvFoodName = (TextView)view.findViewById(R.id.spinner_foodName);
        TextView tvFoodPrice = (TextView)view.findViewById(R.id.spinner_foodPrice);
        tvFoodName.setText(this.foodList.get(position).NameFood());
        tvFoodPrice.setText(this.foodList.get(position).Price() + " Đ");
        return view;
    }
}
