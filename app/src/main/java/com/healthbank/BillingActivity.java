package com.healthbank;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.healthbank.classes.BillingPagerAdapter;

public class BillingActivity extends ActivityCommon {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);
        setmaterialDesign();
        setTitle("Billing");
        back();

        ViewPager viewPager = findViewById(R.id.viewpager);
        BillingPagerAdapter adapter = new BillingPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}
