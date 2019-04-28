package com.example.morse.quanodechatapplication.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.morse.quanodechatapplication.View.LoginScreen;
import com.example.morse.quanodechatapplication.View.SplashScreenFragment1;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        if(i==0){
            return new SplashScreenFragment1();
        }
        else{
            return new LoginScreen();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
