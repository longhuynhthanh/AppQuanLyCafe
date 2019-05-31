package com.example.admin.quanlycafe.FRAGMENT;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.quanlycafe.ADAPTER.RevenueAdapter;
import com.example.admin.quanlycafe.DAO.RevenueDAO;
import com.example.admin.quanlycafe.MODEL.Revenue;
import com.example.admin.quanlycafe.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RevenueFragment extends Fragment {
    private static RevenueFragment instance;

    public static RevenueFragment Instance()
    {
        if (instance == null)
        {
            instance = new RevenueFragment();
        }
        return instance;
    }
    private void Instance(RevenueFragment instance)
    {
        RevenueFragment.instance = instance;
    }

    private RecyclerView rvRevenue;
    private FloatingActionButton fab;
    private List<Revenue> revenueList;
    private RevenueDAO revenueDAO;
    private RevenueAdapter revenueAdapter;
    private EditText DateTime;
    private double totalDay;
    private double totalMonth;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_revenue, container, false);
        this.getActivity().setTitle("Doanh Thu");
        rvRevenue = (RecyclerView)view.findViewById(R.id.rv_revenue);
        rvRevenue.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        rvRevenue.setHasFixedSize(true);
        fab = (FloatingActionButton)view.findViewById(R.id.revenue_totalDay);
        revenueDAO = new RevenueDAO(this.getContext());
        DateTime = (EditText)view.findViewById(R.id.datePicker);

        setDateTimePicker();
        UpdateInterFace();
        showRevenueByDate();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Tổng Tiền của ngày: " + totalDay + " VNĐ\n Tổng tiền của tháng: " + totalMonth + " VNĐ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        return view;
    }

    public void UpdateInterFace()
    {
        totalDay = 0;
        totalMonth = 0;
        revenueList = revenueDAO.GetRevenueByDate(DateTime.getText().toString());
        for (Revenue revenue : revenueList)
        {
            totalDay += revenue.getTotal();
        }
        totalMonth = revenueDAO.totalMonth(DateTime.getText().toString());
        revenueAdapter = new RevenueAdapter(revenueList, this);
        rvRevenue.setAdapter(revenueAdapter);
    }

    private void setDateTimePicker()
    {
        final Calendar myCalendar = Calendar.getInstance();
        updateLabel(myCalendar);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(myCalendar);
            }

        };

        DateTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(RevenueFragment.this.getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private List<Revenue> newList = new ArrayList<>();
    private void showRevenueByDate()
    {
        DateTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                newList = revenueDAO.GetRevenueByDate(charSequence.toString());
                totalDay = 0;
                totalMonth = 0;
                for (Revenue revenue : newList)
                {
                    totalDay += revenue.getTotal();
                }
                revenueAdapter.UpdateAllItem(newList);
                totalMonth = revenueDAO.totalMonth(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void updateLabel(Calendar myCalendar) {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.TAIWAN);
        DateTime.setText(sdf.format(myCalendar.getTime()));
    }
}
