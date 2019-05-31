package com.example.admin.quanlycafe.ADAPTER;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.example.admin.quanlycafe.FRAGMENT.CakeFragment;
import com.example.admin.quanlycafe.FRAGMENT.CoffeeFragment;
import com.example.admin.quanlycafe.FRAGMENT.MilkTeaFragment;
import com.example.admin.quanlycafe.FRAGMENT.SodaFragment;



public class FoodsPagerAdapter extends FragmentStatePagerAdapter {
    private final int COUNT_PAGE = 4;
    private SparseArray<Fragment> pages = new SparseArray<Fragment>();
    public FoodsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        switch (i){
            case 0:
                fragment = CoffeeFragment.Instance();
                break;
            case 1:
                fragment = MilkTeaFragment.Instance();
                break;
            case 2:
                fragment = SodaFragment.Instance();
                break;
            case 3:
                fragment = CakeFragment.Instance();
                break;
        }
        return fragment;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        pages.put(position, fragment);
        return fragment;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        pages.remove(position);
        super.destroyItem(container, position, object);
    }
    @Override
    public int getCount() {
        return COUNT_PAGE;
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "Cà Phê";
                break;
            case 1:
                title = "Trà Sữa";
                break;
            case 2:
                title = "Nước Ngọt";
                break;
            case 3:
                title = "Bánh";
                break;
        }
        return title;
    }
}
