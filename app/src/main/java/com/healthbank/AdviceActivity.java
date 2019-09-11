package com.healthbank;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.healthbank.adapter.AdvicePagerAdapter;

public class AdviceActivity extends ActivityCommon {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);

        setmaterialDesign();
        setTitle("Advice");
        back();

        ViewPager viewPager = findViewById(R.id.viewpager);
        AdvicePagerAdapter adapter = new AdvicePagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        // Give the TabLayout the ViewPager
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}
