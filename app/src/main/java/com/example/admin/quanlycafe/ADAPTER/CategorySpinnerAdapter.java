package com.example.admin.quanlycafe.ADAPTER;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.admin.quanlycafe.MODEL.FoodCategory;

import java.util.List;

public class CategorySpinnerAdapter extends ArrayAdapter<FoodCategory> {
    private Fragment fragment;
    private List<FoodCategory> list;
    public CategorySpinnerAdapter(Fragment fragment, int resource, List<FoodCategory> list) {
        super(fragment.getContext(), resource, list);
        this.fragment = fragment;
        this.list = list;
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public FoodCategory getItem(int position) {
        return this.list.get(position);
    }


    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        TextView label = (TextView)super.getView(position, convertView, parent);
        label.setText(this.list.get(position).NameCategory());
        label.setTextSize(22);
        return label;
    }

    @Override
    public View getDropDownView(int position,  View convertView, ViewGroup parent) {
        TextView label = (TextView)super.getDropDownView(position, convertView, parent);
        label.setText(this.list.get(position).NameCategory());
        label.setTextSize(22);
        return label;
    }
}
