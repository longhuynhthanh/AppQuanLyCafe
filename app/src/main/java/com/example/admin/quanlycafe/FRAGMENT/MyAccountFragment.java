package com.example.admin.quanlycafe.FRAGMENT;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.quanlycafe.DAO.AccountDAO;
import com.example.admin.quanlycafe.INTERFACE.AccountInterface;
import com.example.admin.quanlycafe.MODEL.Account;
import com.example.admin.quanlycafe.R;

public class MyAccountFragment extends Fragment {
    private static MyAccountFragment instance;
    private Account account;
    private EditText etUserName;
    private EditText etDisplayName;
    private EditText etPassword;
    private EditText etType;
    private Button btnUpdate;
    private int type = 0;
    private AccountInterface accountDAO;
    public static MyAccountFragment Instance()
    {
        if(instance == null)
        {
            instance = new MyAccountFragment();
        }
        return instance;
    }
    private void Instance(MyAccountFragment instance)
    {
        MyAccountFragment.instance = instance;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.getActivity().setTitle("Thông Tin Tài Khoản");
        View view = inflater.inflate(R.layout.fragment_my_account, container, false);
        account = getAccount();

        accountDAO = new AccountDAO(this.getContext());

        etUserName = (EditText)view.findViewById(R.id.userAccount);
        etDisplayName = (EditText)view.findViewById(R.id.displayNameAccount);
        etPassword = (EditText)view.findViewById(R.id.passwordAccount);
        etType = (EditText)view.findViewById(R.id.TypeAccount);
        btnUpdate = (Button)view.findViewById(R.id.btn_updateAccount);

        etUserName.setText(account.UserName());
        etDisplayName.setText(account.DisplayName());
        etPassword.setText(account.PasswordAccount());
        if(account.Type() == 0)
        {
            etType.setText("Nhân Viên");
        }
        else
        {
            etType.setText("Quản Lý");
            type = 1;
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accountDAO.UpdateAccount(new Account(etUserName.getText().toString(),
                        etDisplayName.getText().toString(),
                        etPassword.getText().toString(), type));
                Toast.makeText(MyAccountFragment.this.getContext(), "UpdateTable Thành Công", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private Account getAccount()
    {
        Account a = new Account();
        Bundle bundle = getArguments();
        if(bundle != null)
        {
            a.UserName(bundle.getString("UserName"));
            a.DisplayName(bundle.getString("DisplayName"));
            a.PasswordAccount(bundle.getString("Password"));
            a.Type(bundle.getInt("TypeAccount"));
        }
        return a;
    }
}
