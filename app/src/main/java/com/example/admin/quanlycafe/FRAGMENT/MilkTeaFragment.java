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

import com.example.admin.quanlycafe.ADAPTER.FoodAdapter;
import com.example.admin.quanlycafe.DAO.FoodDAO;
import com.example.admin.quanlycafe.INTERFACE.FoodInterface;
import com.example.admin.quanlycafe.MODEL.Food;
import com.example.admin.quanlycafe.R;

import java.util.List;

public class MilkTeaFragment extends Fragment {
    private static MilkTeaFragment instance;

    public static MilkTeaFragment Instance()
    {
        if(instance == null)
        {
            instance = new MilkTeaFragment();
        }
        return instance;
    }
    private void Instance(MilkTeaFragment instance)
    {
        MilkTeaFragment.instance = instance;
    }
    private RecyclerView rv_food;
    private List<Food> ListFood;
    private FoodInterface FoodDAO;
    private int idCategory;
    private FoodAdapter foodAdapter;
    private int type;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, container, false);
        type = getType();
        rv_food = (RecyclerView)view.findViewById(R.id.rv_food);
        idCategory = 2;
        FoodDAO = new FoodDAO(this.getContext());
        ListFood = FoodDAO.GetFood(idCategory);
        foodAdapter = new FoodAdapter(ListFood,this, type);
        UpdateInterface();
        return view;
    }
    public void UpdateInterface()
    {
        rv_food.setAdapter(foodAdapter);
        rv_food.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        rv_food.setHasFixedSize(true);
    }
    public void AddFood(Food food)
    {
        FoodDAO.InsertFood(food);
        foodAdapter.AddItem(food);
    }
    public void UpdateFood(Food food)
    {
        FoodDAO.UpdateFood(food);
        foodAdapter.AddItem(food);
    }
    private int getType()
    {
        int Type = -1;
        Bundle bundle = getArguments();
        if(bundle != null)
        {
            Type = bundle.getInt("Type");
        }
        return Type;
    }
}
