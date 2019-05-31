package com.example.admin.quanlycafe.FRAGMENT;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.admin.quanlycafe.ADAPTER.BillAdapter;
import com.example.admin.quanlycafe.ADAPTER.CategorySpinnerAdapter;
import com.example.admin.quanlycafe.ADAPTER.FoodSpinnerAdapter;
import com.example.admin.quanlycafe.DAO.BillDAO;
import com.example.admin.quanlycafe.DAO.BillInfoDAO;
import com.example.admin.quanlycafe.DAO.FoodCategoryDAO;
import com.example.admin.quanlycafe.DAO.FoodDAO;
import com.example.admin.quanlycafe.DAO.MenuFoodDAO;
import com.example.admin.quanlycafe.INTERFACE.BillInfoInterface;
import com.example.admin.quanlycafe.INTERFACE.BillInterface;
import com.example.admin.quanlycafe.INTERFACE.FoodCategoryInterface;
import com.example.admin.quanlycafe.INTERFACE.FoodInterface;
import com.example.admin.quanlycafe.INTERFACE.TotalPrice;
import com.example.admin.quanlycafe.INTERFACE.UpdateTable;
import com.example.admin.quanlycafe.MODEL.Food;
import com.example.admin.quanlycafe.MODEL.FoodCategory;
import com.example.admin.quanlycafe.MODEL.MenuFood;
import com.example.admin.quanlycafe.MODEL.TableFood;
import com.example.admin.quanlycafe.R;

import java.util.ArrayList;
import java.util.List;

public class BillFragment extends Fragment implements TotalPrice {
    private static BillFragment instance;
    public static BillFragment Instance()
    {
        if (instance == null)
        {
            instance = new BillFragment();
        }
        return instance;
    }
    private void Instance(BillFragment instance)
    {
        BillFragment.instance = instance;
    }

    private TableFood tableFood;
    private RecyclerView rv_Bill;
    private FloatingActionButton fab;
    private List<MenuFood> menuFoodList;
    private MenuFoodDAO menuFoodDAO;
    private List<FoodCategory> foodCategoryList;
    private FoodCategoryInterface foodCategoryDAO;
    private List<Food> foodList;
    private FoodInterface foodDAO;
    private BillInterface billDAO;
    private BillInfoInterface billInfoDAO;
    private BillAdapter billAdapter;
    private CategorySpinnerAdapter categorySpinnerAdapter;
    private FoodSpinnerAdapter foodSpinnerAdapter;
    private Spinner CategorySpinner;
    private Spinner FoodSpinner;
    private ImageView insertFood;
    private List<Integer> idFoodList;
    private Food food;
    private int idCategory = 0;
    private UpdateTable updateTableStatusTable;
    private double totalPrice = 0;
    private Button btnPay;

    public void setUpdateTableStatusTable(UpdateTable updateTableStatusTable)
    {
        this.updateTableStatusTable = updateTableStatusTable;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        tableFood = getTableFood();
        this.getActivity().setTitle("Thông Tin Món Ăn Của " + tableFood.NameTable());
        View view = inflater.inflate(R.layout.fragment_bill, container, false);
        rv_Bill = (RecyclerView)view.findViewById(R.id.rv_bill);
        fab = (FloatingActionButton)view.findViewById(R.id.total);
        CategorySpinner = (Spinner)view.findViewById(R.id.spinner_category);
        FoodSpinner = (Spinner)view.findViewById(R.id.spinner_foods);
        insertFood = (ImageView)view.findViewById(R.id.img_insertFood);
        btnPay = (Button)view.findViewById(R.id.pay);


        // DAO
        foodCategoryDAO = new FoodCategoryDAO(this.getContext());
        foodDAO = new FoodDAO(this.getContext());
        menuFoodDAO = new MenuFoodDAO(this.getContext());
        billDAO = new BillDAO(this.getContext());
        billInfoDAO = new BillInfoDAO(this.getContext());

        UpdateInterface();
        InsertFood();
        showTotalPrice();
        Pay();

        return view;
    }

    @Override
    public void onDestroyView() {
        this.getActivity().setTitle("Danh Sách Bàn Ăn");
        super.onDestroyView();
    }

    public void UpdateInterface()
    {
        // get List
        foodCategoryList = foodCategoryDAO.GetFoodCategory();
        menuFoodList = menuFoodDAO.GetListMenuFoodByTable(tableFood.Id());
        // -------------------------------------

        // Method
        GetListIdFood();
        SetCategorySpinner();
        SetRecyclerView();

        // ------------------------------------------

    }


    private void SetRecyclerView()
    {
        billAdapter = new BillAdapter(menuFoodList, this, this);
        rv_Bill.setAdapter(billAdapter);
        rv_Bill.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        rv_Bill.setHasFixedSize(true);
    }

    public void SetCategorySpinner()
    {
        categorySpinnerAdapter = new CategorySpinnerAdapter(BillFragment.this, android.R.layout.simple_spinner_item, foodCategoryList);
        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CategorySpinner.setAdapter(categorySpinnerAdapter);
        CategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idCategory = foodCategoryList.get(i).Id();
                foodList = foodDAO.GetFoodNotInListIdFood(idCategory, idFoodList);
                SetFoodSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    public void GetListIdFood()
    {
        idFoodList = new ArrayList<>();
        for(MenuFood menuFood : menuFoodList)
        {
            idFoodList.add(menuFood.IdFood());
        }
    }


    private void SetFoodSpinner()
    {
        foodSpinnerAdapter = new FoodSpinnerAdapter(this, R.layout.row_food_spinner, foodList);
        foodSpinnerAdapter.setDropDownViewResource(R.layout.row_food_spinner);
        FoodSpinner.setAdapter(foodSpinnerAdapter);
        FoodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                food = new Food();
                food = foodList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void InsertFood()
    {
        insertFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idBill = billDAO.GetBillByTableID(tableFood.Id());
                if(idBill == -1)
                {
                    billDAO.InsertBill(tableFood.Id());
                    if(tableFood.Status() == 0)
                    {
                        tableFood.Status(1);
                        updateTableStatusTable.UpdateStatusTable(tableFood.Id(), 1);
                    }
                    billInfoDAO.InsertBillInfo(billDAO.GetMaxBillID(), food.Id(), 1);
                }
                else
                {
                    billInfoDAO.InsertBillInfo(idBill, food.Id(), 1);
                }
                totalPrice += food.Price();
                UpdateInterface();
            }
        });
    }

    private void showTotalPrice()
    {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Tổng Tiền của " + tableFood.NameTable() + " : " + totalPrice + " VNĐ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void Pay()
    {
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idBill = billDAO.GetBillByTableID(tableFood.Id());
                if(idBill != -1)
                    updateTableStatusTable.UpdateStatusTable(tableFood.Id(), 0);
                    {
                    billDAO.CheckOut(idBill);
                    BillFragment.this.getActivity().onBackPressed();
                }
            }
        });


    }

    private TableFood getTableFood()
    {
        TableFood tableFood = new TableFood();
        Bundle bundle = getArguments();
        if (bundle != null)
        {
            int idTable = bundle.getInt("idTable");
            String nameTable = bundle.getString("tableName");
            int status = bundle.getInt("status");
            tableFood.Id(idTable);
            tableFood.NameTable(nameTable);
            tableFood.Status(status);
        }
        return tableFood;
    }

    @Override
    public void TotalPrice(double price) {
        totalPrice = price;
    }
}
