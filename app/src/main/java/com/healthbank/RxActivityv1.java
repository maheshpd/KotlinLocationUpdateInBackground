package com.healthbank;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;

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
