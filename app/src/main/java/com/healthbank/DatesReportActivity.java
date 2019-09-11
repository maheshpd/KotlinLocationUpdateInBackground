package com.healthbank;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.healthbank.adapter.OtherReportAdpater;

import java.util.ArrayList;

public class DatesReportActivity extends ActivityCommon {
    RecyclerView recyclerView1;
    LinearLayoutManager mLayoutmanager;
    OtherReportAdpater adapter;
    ArrayList<String> mdatset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dates_report);
        setmaterialDesign();
        back();
        setTitle("Blood Report");
        attachUI();
    }

    public void attachUI(){
        mdatset = new ArrayList<>();
        mdatset.add("15/06/18");
        mdatset.add("13/06/18");
        recyclerView1 = findViewById(R.id.recyclerview_1);
        recyclerView1.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(DatesReportActivity.this, R.drawable.recycler_divider)));

        mLayoutmanager = new LinearLayoutManager(this);
        recyclerView1.setLayoutManager(mLayoutmanager);
        adapter = new OtherReportAdpater(this, mdatset);
        recyclerView1.setAdapter(adapter);
    }
}
