package com.healthbank;

import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.healthbank.adapter.BloodReportAdpater;

import java.util.ArrayList;

public class BloodReportActivity extends ActivityCommon {
    RecyclerView recyclerView1;
    LinearLayoutManager mLayoutmanager;
    BloodReportAdpater adapter;
    ArrayList<String> mdatset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_report);
        setmaterialDesign();
        back();
        setTitle("Blood Report");
        attachUI();
    }

    public void attachUI() {
        mdatset = new ArrayList<>();
        mdatset.add("RBC");
        mdatset.add("WBC");
        mdatset.add("RBC");
        mdatset.add("WBC");
        recyclerView1 = findViewById(R.id.recyclerview_1);
        recyclerView1.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(BloodReportActivity.this, R.drawable.recycler_divider)));
        mLayoutmanager = new LinearLayoutManager(this);
        recyclerView1.setLayoutManager(mLayoutmanager);
        adapter = new BloodReportAdpater(this, mdatset);
        recyclerView1.setAdapter(adapter);
    }
}
