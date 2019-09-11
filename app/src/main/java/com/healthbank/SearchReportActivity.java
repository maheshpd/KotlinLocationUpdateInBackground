package com.healthbank;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.healthbank.adapter.SelectionCheckAdapter;

public class SearchReportActivity extends ActivityCommon {
    RecyclerView mrecyclerview;
    SelectionCheckAdapter adapter;
    LinearLayoutManager mLayoutmanager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_report);
        setmaterialDesign();
        back();
        attachUI();
    }

    public void attachUI(){
        mrecyclerview= findViewById(R.id.recyclerview_1);
        mLayoutmanager=new LinearLayoutManager(SearchReportActivity.this);
        mrecyclerview.setLayoutManager(mLayoutmanager);
        adapter=new SelectionCheckAdapter(GlobalValues.mdataset,SearchReportActivity.this);
        mrecyclerview.setAdapter(adapter);
    }
}
