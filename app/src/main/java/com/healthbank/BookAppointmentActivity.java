package com.healthbank;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;

import com.healthbank.adapter.BookAppointmentPagerAdapter;

public class BookAppointmentActivity extends ActivityCommon {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);
        setmaterialDesign();
        setTitle("Appointments");
        back();
        ViewPager viewPager = findViewById(R.id.viewpager);
        BookAppointmentPagerAdapter adapter = new BookAppointmentPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}
