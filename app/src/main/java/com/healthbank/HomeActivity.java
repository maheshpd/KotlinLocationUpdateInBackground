package com.healthbank;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.healthbank.adapter.HomeAdapter;
import com.healthbank.classes.HomeData;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    RecyclerView recyclerview;
    GridLayoutManager gridLayoutManager;
    HomeAdapter adapter;
    ArrayList<HomeData> mdatset;
    LinearLayout layout1, layout2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        attachUI();
    }

    private void attachUI() {
        layout1 = findViewById(R.id.layout1);
        layout2 = findViewById(R.id.linearLayout);
        mdatset = new ArrayList<>();
        mdatset.add(new HomeData("Appointment/Medical Visit", R.drawable.icon_appointment));
        mdatset.add(new HomeData("Availability", R.drawable.icon_dochome));
        mdatset.add(new HomeData("Patient Queries", R.drawable.icon_userqueries));
        mdatset.add(new HomeData("Pending Report", R.drawable.icon_report));
        mdatset.add(new HomeData("Settings", R.drawable.icon_report));
        recyclerview = findViewById(R.id.recyclerview_1);
        gridLayoutManager = new GridLayoutManager(HomeActivity.this, 2);
        recyclerview.setLayoutManager(gridLayoutManager);
        adapter = new HomeAdapter(HomeActivity.this, mdatset);
        recyclerview.setAdapter(adapter);
    }

    public void gotonext(int position) {
        switch (position) {
            case 0:
                Intent intent = new Intent(HomeActivity.this, AppointmentListActivity.class);
                startActivity(intent);
                break;
            case 1:
                Intent intentavailiability = new Intent(HomeActivity.this, AvailabilityActivity.class);
                startActivity(intentavailiability);
                break;
            case 4:
                Intent intentsettings = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivity(intentsettings);
                break;
        }
    }
}
