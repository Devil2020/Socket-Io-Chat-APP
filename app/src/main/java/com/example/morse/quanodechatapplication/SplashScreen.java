package com.example.morse.quanodechatapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.example.morse.quanodechatapplication.Adapter.ViewPagerAdapter;
import com.example.morse.quanodechatapplication.databinding.ActivitySplashScreenBinding;

public class SplashScreen extends AppCompatActivity implements View.OnClickListener ,ViewPager.OnPageChangeListener {
    private ViewPagerAdapter viewPagerAdapter;
    private FragmentManager fragmentManager;
   static private ActivitySplashScreenBinding screenBinding;
   SharedPreferences preferences;
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        preferences =getSharedPreferences("Cache",MODE_PRIVATE);
            boolean b=preferences.getBoolean("isFirst",false);
            if(b==false){
                SharedPreferences.Editor editor=preferences.edit();
                editor.putBoolean("isFirst",true);
                editor.apply();
            }
            else{
                startActivity(new Intent(this,ChatActivity.class));
            }
        screenBinding =DataBindingUtil.setContentView(this,R.layout.activity_splash_screen);
        fragmentManager =getSupportFragmentManager();
        viewPagerAdapter=new ViewPagerAdapter(fragmentManager);
        screenBinding.ViewPager.setAdapter(viewPagerAdapter);
        screenBinding.ViewPager.setCurrentItem(0);
        screenBinding.DotIndecator.setViewPager(screenBinding.ViewPager);
        screenBinding.Next.setOnClickListener(this);
        screenBinding.Previous.setOnClickListener(this);
        screenBinding.ViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        }

    @Override
    protected void onDestroy() {
        super.onDestroy();
   //     EventBus.getDefault().unregister(this);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.Next){
            screenBinding.ViewPager.setCurrentItem(1);
            screenBinding.Next.setVisibility(View.INVISIBLE);
            screenBinding.Previous.setVisibility(View.VISIBLE);
        }
        else{
            screenBinding.ViewPager.setCurrentItem(0);
            screenBinding.Previous.setVisibility(View.INVISIBLE);
            screenBinding.Next.setVisibility(View.VISIBLE);
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onPageScrollStateChanged(int i) {
        if(i==1){
            screenBinding.ViewPager.setCurrentItem(0);
            screenBinding.Next.setVisibility(View.INVISIBLE);
            screenBinding.Previous.setVisibility(View.VISIBLE);
        }
        else{
            screenBinding.ViewPager.setCurrentItem(1);
            screenBinding.Previous.setVisibility(View.INVISIBLE);
            screenBinding.Next.setVisibility(View.VISIBLE);
        }
    }
}
