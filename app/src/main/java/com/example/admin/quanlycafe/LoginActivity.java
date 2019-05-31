package com.example.admin.quanlycafe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.admin.quanlycafe.DAO.AccountDAO;
import com.example.admin.quanlycafe.INTERFACE.AccountInterface;
import com.example.admin.quanlycafe.MODEL.Account;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements Animation.AnimationListener {
    private ImageView img;
    private EditText etUser;
    private EditText etPassword;
    private Button btnLogin;
    private Animation animationFadeIn;
    private Animation animationZoomIn;
    private AccountInterface accountDAO;
    private List<Account> accountList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setTitle("Đăng Nhập");
        img = (ImageView)findViewById(R.id.image);
        etUser = (EditText)findViewById(R.id.input_user);
        etPassword = (EditText)findViewById(R.id.input_password);
        btnLogin = (Button)findViewById(R.id.btn_login);
        animationZoomIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoomin);
        animationFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
        animationFadeIn.setAnimationListener(this);
        animationZoomIn.setAnimationListener(this);

        // Thực hiện animation
        etUser.startAnimation(animationFadeIn);
        etPassword.startAnimation(animationFadeIn);
        btnLogin.startAnimation(animationFadeIn);
        img.startAnimation(animationZoomIn);
        // ------------------

        // DAO
        accountDAO = new AccountDAO(this);
        // -------------------
        accountList = accountDAO.GetAllAccount();

        if(accountList.size() == 0)
        {
            accountDAO.InsertAccount(new Account("ThanhLong", "Thanh Long", "123", 1));
            accountList = accountDAO.GetAllAccount();
        }
        Login();
    }

    private void Login()
    {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(Account a : accountList)
                {
                    if (a.UserName().equals(etUser.getText().toString()) && a.PasswordAccount().equals(etPassword.getText().toString()))
                    {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("UserName", a.UserName());
                        bundle.putString("DisplayName", a.DisplayName());
                        bundle.putString("Password", a.PasswordAccount());
                        bundle.putInt("TypeAccount", a.Type());
                        intent.putExtras(bundle);
                        startActivity(intent);
                        LoginActivity.this.finish();
                        break;
                    }
                }
            }
        });
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
