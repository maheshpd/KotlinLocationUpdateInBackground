package com.healthbank;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;

import com.healthbank.adapter.ExaminationPagerAdapter;

public class Examination extends ActivityCommon {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examination);
        setmaterialDesign();
        setTitle("HPI");
        back();

        ViewPager viewPager = findViewById(R.id.viewpager);
        ExaminationPagerAdapter adapter = new ExaminationPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}
