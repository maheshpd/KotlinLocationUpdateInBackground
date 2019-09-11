package com.healthbank;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.healthbank.adapter.VitalAdapter;
import com.healthbank.classes.VitalHeader;
import com.healthbank.database.DatabaseHelper;

import org.json.JSONArray;

import java.util.ArrayList;

public class VitalsActivity extends ActivityCommon {
    VitalAdapter adapter;
    LinearLayout layout;
    ArrayList<VitalHeader> vitalsdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vitals);
        setmaterialDesign();
        back();
        setTitle("Vitals");

        vitalsdata = new ArrayList<>();
        JSONArray array = DatabaseHelper.getInstance(context).getlabdata(GlobalValues.selectedpt.getPatientid(), DatabaseHelper.vitals);
        for (int i = 0; i < array.length(); i++) {
            try {
                vitalsdata.add(new VitalHeader(array.getJSONObject(i)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        TableMainLayout v = new TableMainLayout(VitalsActivity.this,vitalsdata);
        layout = findViewById(R.id.layout1);
        layout.addView(v);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filter, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onResume() {
        super.onResume();
        try {
            layout = findViewById(R.id.layout1);
            layout.removeAllViews();
            vitalsdata = new ArrayList<>();
            JSONArray array = DatabaseHelper.getInstance(context).getlabdata(GlobalValues.selectedpt.getPatientid(), DatabaseHelper.vitals);
            for (int i = 0; i < array.length(); i++) {
                try {
                    vitalsdata.add(new VitalHeader(array.getJSONObject(i)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            TableMainLayout v = new TableMainLayout(VitalsActivity.this,vitalsdata);
            layout.addView(v);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_add:
                Intent intent = new Intent(VitalsActivity.this, AddVitalsActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_action_filter:
                Intent intentfilter = new Intent(VitalsActivity.this, SearchReportActivity.class);
                startActivity(intentfilter);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
