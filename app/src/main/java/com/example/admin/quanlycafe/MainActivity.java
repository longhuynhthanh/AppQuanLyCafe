package com.example.admin.quanlycafe;

import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.quanlycafe.FRAGMENT.FoodsFragment;
import com.example.admin.quanlycafe.FRAGMENT.LocationFragment;
import com.example.admin.quanlycafe.FRAGMENT.MyAccountFragment;
import com.example.admin.quanlycafe.FRAGMENT.RevenueFragment;
import com.example.admin.quanlycafe.FRAGMENT.TableFragment;
import com.example.admin.quanlycafe.FRAGMENT.UsersFragment;
import com.example.admin.quanlycafe.MODEL.Account;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Account a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Lấy Thông Tin Của Accoutn Đăng Nhập Vào
        a = getAccount();
        // --------------


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        // Navigation
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        ImageView nav_imgHeader = (ImageView)headerView.findViewById(R.id.nav_imgAccount);
        nav_imgHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("UserName", a.UserName());
                bundle.putString("DisplayName", a.DisplayName());
                bundle.putString("Password", a.PasswordAccount());
                bundle.putInt("TypeAccount", a.Type());
                MyAccountFragment.Instance().setArguments(bundle);
                initFragment(MyAccountFragment.Instance());
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        TextView nav_tvDisplayName = (TextView)headerView.findViewById(R.id.nav_tvUserName);
        nav_tvDisplayName.setText(a.DisplayName());
        TextView nav_typeAccount = (TextView)headerView.findViewById(R.id.nav_tvType);

        if (a.Type() == 0)
        {
            nav_typeAccount.setText("Chức Vụ: Nhân Viên");
            Menu menuNav = navigationView.getMenu();
            MenuItem item1 = menuNav.findItem(R.id.nav_revenue);
            item1.setEnabled(false);
            item1.setVisible(false);

            MenuItem item2 = menuNav.findItem(R.id.nav_users);
            item2.setVisible(false);
            item2.setEnabled(false);
        }
        else if(a.Type() == 1)
        {
            nav_typeAccount.setText("Chức Vụ: Quản Lý");
        }
        navigationView.setNavigationItemSelectedListener(this);
        // End Navigation ---------------------------------------------



        // Show Table
        initFragment(TableFragment.Instance());
        setTitle("Danh Sách Bàn Ăn");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_location) {
            initFragment(LocationFragment.Instance());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Class classFragment = null;

        if (id == R.id.nav_food) {
            Bundle bundle = new Bundle();
            bundle.putInt("Type", a.Type());
            FoodsFragment.Instance().setArguments(bundle);
            initFragment(FoodsFragment.Instance());
        } else if (id == R.id.nav_table) {Bundle bundle = new Bundle();
            bundle.putInt("Type", a.Type());
            FoodsFragment.Instance().setArguments(bundle);
            initFragment(TableFragment.Instance());
        } else if (id == R.id.nav_revenue) {
            initFragment(RevenueFragment.Instance());
        } else if (id == R.id.nav_users) {
            initFragment(UsersFragment.Instance());
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if(id == R.id.logout)
        {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.container_body, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    private Account getAccount()
    {
        Account a = new Account();
        Intent intent = getIntent();
        if(intent != null)
        {
            Bundle bundle = intent.getExtras();
            if(bundle != null)
            {
                a.UserName(bundle.getString("UserName"));
                a.DisplayName(bundle.getString("DisplayName"));
                a.PasswordAccount(bundle.getString("Password"));
                a.Type(bundle.getInt("TypeAccount"));
            }
        }
        return a;
    }
}
