package com.healthbank;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.healthbank.adapter.RxPagerAdapter;

public class RxActivityv1 extends ActivityCommon {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_activityv1);
        setmaterialDesign();
        setTitle("RX");
        back();

        ViewPager viewPager = findViewById(R.id.viewpager);
        RxPagerAdapter adapter = new RxPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}
