package com.example.admin.quanlycafe.FRAGMENT;

import android.content.DialogInterface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.admin.quanlycafe.ADAPTER.FoodsPagerAdapter;
import com.example.admin.quanlycafe.ADAPTER.CategorySpinnerAdapter;

import com.example.admin.quanlycafe.DAO.FoodCategoryDAO;
import com.example.admin.quanlycafe.INTERFACE.FoodCategoryInterface;
import com.example.admin.quanlycafe.MODEL.Food;
import com.example.admin.quanlycafe.MODEL.FoodCategory;
import com.example.admin.quanlycafe.R;

import java.util.List;

public class FoodsFragment extends Fragment {
    private static FoodsFragment instance;
    public static FoodsFragment Instance()
    {
        if(instance == null)
        {
            instance = new FoodsFragment();
        }
        return instance;
    }
    private void Instance(FoodsFragment instance)
    {
        FoodsFragment.instance = instance;
    }

    private ViewPager viewPager;
    private TabLayout tabLayout;
    public static List<FoodCategory> list;
    private FoodCategoryInterface foodCategoryDAO;
    private FoodsPagerAdapter pagerAdapter;
    private FloatingActionButton fab;
    private int type = 0;
    private static View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.getActivity().setTitle("Danh Sách Món Ăn");
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try{
            view = inflater.inflate(R.layout.fragment_foods, container, false);
            type = getType();

            viewPager = (ViewPager)view.findViewById(R.id.view_pagerfood);
            tabLayout = (TabLayout)view.findViewById(R.id.tab_layoutfood);
            fab = (FloatingActionButton)view.findViewById(R.id.insertCategory);
            UpdateInterface();
            InsertFood();
            DataTransaction();
        }catch (InflateException e)
        {}
        return view;
    }

    public void UpdateInterface()
    {
        foodCategoryDAO = new FoodCategoryDAO(this.getContext());
        list = foodCategoryDAO.GetFoodCategory();
        if(list.size() == 0)
        {
            foodCategoryDAO.InsertFoodCategory(new FoodCategory("Cà Phê"));
            foodCategoryDAO.InsertFoodCategory(new FoodCategory("Trà Sữa"));
            foodCategoryDAO.InsertFoodCategory(new FoodCategory("Nước Ngọt"));
            foodCategoryDAO.InsertFoodCategory(new FoodCategory("Bánh"));
            list = foodCategoryDAO.GetFoodCategory();
        }
        pagerAdapter = new FoodsPagerAdapter(getFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabsFromPagerAdapter(pagerAdapter);//deprecated
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        View root = tabLayout.getChildAt(0);
        if (root instanceof LinearLayout) {
            ((LinearLayout) root).setShowDividers(LinearLayout.SHOW_DIVIDER_END);
            GradientDrawable drawable = new GradientDrawable();
            drawable.setSize(1, 1);
            ((LinearLayout) root).setDividerPadding(15);
            ((LinearLayout) root).setDividerDrawable(drawable);
        }
    }
    private int idCategory = 0;
    private void InsertFood()
    {
        if(type == 1)
        {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog.Builder alerDialog = new AlertDialog.Builder(FoodsFragment.this.getActivity());
                    LayoutInflater inf = getLayoutInflater();
                    final  View view = inf.inflate(R.layout.dialog_food, null);
                    Spinner spn_Category = (Spinner)view.findViewById(R.id.spnCategoryDA);
                    CategorySpinnerAdapter adapter = new CategorySpinnerAdapter(FoodsFragment.this, android.R.layout.simple_spinner_item, list);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn_Category.setAdapter(adapter);
                    spn_Category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            idCategory = list.get(position).Id();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    alerDialog.setView(view);
                    alerDialog.setNegativeButton("Insert", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditText et_NameFood = (EditText)view.findViewById(R.id.etNameFoodDA);
                            EditText et_Price = (EditText)view.findViewById(R.id.etPriceDA);
                            Food food = new Food(et_NameFood.getText().toString(), idCategory, Double.parseDouble(et_Price.getText().toString()));
                            if(idCategory == 1)
                            {
                                CoffeeFragment.Instance().AddFood(food);
                            }
                            else if(idCategory == 2)
                            {
                                MilkTeaFragment.Instance().AddFood(food);
                            }
                            else if(idCategory == 3)
                            {
                                SodaFragment.Instance().AddFood(food);
                            }
                            else if(idCategory == 4)
                            {
                                CakeFragment.Instance().AddFood(food);
                            }
                        }
                    });
                    alerDialog.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    alerDialog.show();
                }
            });
        }
        else if(type == 0)
        {
            fab.hide();
        }

    }

    private int getType()
    {
        int Type = 0;
        Bundle bundle = getArguments();
        if(bundle != null)
        {
            Type = bundle.getInt("Type");
        }
        return Type;
    }

    private void DataTransaction()
    {
        Bundle bundle = new Bundle();
        bundle.putInt("Type", type);
        CoffeeFragment.Instance().setArguments(bundle);
        CakeFragment.Instance().setArguments(bundle);
        MilkTeaFragment.Instance().setArguments(bundle);
        SodaFragment.Instance().setArguments(bundle);
    }

}
