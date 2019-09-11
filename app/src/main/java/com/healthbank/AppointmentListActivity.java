package com.healthbank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.healthbank.adapter.fragmentPagerAdapter;

public class AppointmentListActivity extends ActivityCommon {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_list);
        setmaterialDesign();
        setTitle("Appointments");
        attachUI();
    }

    private void attachUI() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(AppointmentListActivity.this);
        String branchname = pref.getString("BranchName", "");
        String dept = pref.getString("DepartmentName", "");
        ViewPager viewPager = findViewById(R.id.viewpager);
        fragmentPagerAdapter adapter = new fragmentPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_addappointment, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_addappointment:
                Intent intent = new Intent(AppointmentListActivity.this, AppointmentActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
