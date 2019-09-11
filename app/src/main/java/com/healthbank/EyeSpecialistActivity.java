package com.healthbank;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;

import com.healthbank.adapter.EyeSpecialistAdapter;

public class EyeSpecialistActivity extends ActivityCommon {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eye_specialist);
        setmaterialDesign();
        setTitle("Ophthalmology Template");
        back();

        ViewPager viewPager = findViewById(R.id.viewpager);
        EyeSpecialistAdapter adapter = new EyeSpecialistAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}
