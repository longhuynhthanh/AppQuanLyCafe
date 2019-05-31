package com.example.admin.quanlycafe.FRAGMENT;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.admin.quanlycafe.ADAPTER.UsersAdapter;
import com.example.admin.quanlycafe.DAO.AccountDAO;
import com.example.admin.quanlycafe.INTERFACE.AccountInterface;
import com.example.admin.quanlycafe.MODEL.Account;
import com.example.admin.quanlycafe.R;

import java.util.ArrayList;
import java.util.List;

public class UsersFragment extends Fragment {
    private static UsersFragment instance;

    public static UsersFragment Instance()
    {
        if(instance == null)
        {
            instance = new UsersFragment();
        }
        return instance;
    }
    private void Instance(UsersFragment instance)
    {
        UsersFragment.instance = instance;
    }

    private RecyclerView rvUsers;
    private FloatingActionButton fab;
    private List<Account> accountList;
    private AccountInterface accountDAO;
    private UsersAdapter usersAdapter;
    private EditText etSearch;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.getActivity().setTitle("Tài Khoản Nhân Viên");
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        etSearch = (EditText) view.findViewById(R.id.input_search);
        rvUsers = (RecyclerView)view.findViewById(R.id.rv_users);
        rvUsers.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        rvUsers.setHasFixedSize(true);
        fab = (FloatingActionButton)view.findViewById(R.id.insertUser);
        accountDAO = new AccountDAO(this.getContext());
        UpdateInterface();
        InsertUser();
        Search();
        return view;
    }

    public void UpdateInterface()
    {
        accountList = accountDAO.GetAccount();
        usersAdapter = new UsersAdapter(accountList, this);
        rvUsers.setAdapter(usersAdapter);
    }
    private void InsertUser()
    {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alerDialog = new AlertDialog.Builder(UsersFragment.this.getActivity());
                LayoutInflater inf = getLayoutInflater();
                final View view = inf.inflate(R.layout.dialog_user, null);
                alerDialog.setView(view);
                final EditText etUserName = (EditText)view.findViewById(R.id.etUserNameDA);
                final TextView tvNoti = (TextView)view.findViewById(R.id.tvNotiDA);
                final EditText etDisplayName = (EditText)view.findViewById(R.id.etDisplayNameDA);
                final EditText etPassword = (EditText)view.findViewById(R.id.etPasswordDA);
                etUserName.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        tvNoti.setVisibility(View.VISIBLE);
                        if(s.toString().equals(""))
                        {
                            tvNoti.setText("Đừng Để Trống Ở User Name");
                            tvNoti.setTextColor(Color.RED);
                        }
                        else if(CheckUser(s.toString()))
                        {
                            tvNoti.setText("Bạn Có Thể Sử Dụng User Name Này");
                            tvNoti.setTextColor(Color.BLUE);
                        }
                        else
                        {
                            tvNoti.setText("User Name Bị Trùng");
                            tvNoti.setTextColor(Color.RED);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                alerDialog.setNegativeButton("Insert", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        // xét sự kiện kiểm tra userName
                        Account account = new Account(etUserName.getText().toString(),
                                                    etDisplayName.getText().toString(),
                                                    etPassword.getText().toString(),
                                                    0);
                        accountDAO.InsertAccount(account);
                        usersAdapter.addItem(account);
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
    private List<Account> newList = new ArrayList<>();
    private void Search()
    {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                UpdateInterface();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Tim(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void Tim(String s)
    {
        newList.clear();
        for (Account a : this.accountList)
        {
            if (a.UserName().toUpperCase().contains(s.toUpperCase()))
            {
                newList.add(a);
            }
        }
        usersAdapter.UpdateAll(newList);
    }

    private boolean CheckUser(String s)
    {
        for (Account account : accountList)
        {
            if(account.UserName().equals(s))
            {
                return false;
            }
        }
        return true;
    }
}
