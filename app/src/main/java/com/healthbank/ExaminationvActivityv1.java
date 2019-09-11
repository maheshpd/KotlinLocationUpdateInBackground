package com.healthbank;

import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.healthbank.adapter.ExaminationAdapterv1;

import java.util.ArrayList;

public class ExaminationvActivityv1 extends ActivityCommon {
    RecyclerView mrecyclerview;
    LinearLayoutManager mLayoutmanager;
    ExaminationAdapterv1 adapter;
    ArrayList<String> mdataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examinationv_activityv1);
        setmaterialDesign();
        back();
        attachUI();
    }

    private void attachUI(){
        mdataset=new ArrayList<>();
        mdataset.add("Ophthalmology Template");
       // mdataset.add("Gynecologist");
        mrecyclerview= findViewById(R.id.recyclerview_1);
        mLayoutmanager=new LinearLayoutManager(ExaminationvActivityv1.this);
        mrecyclerview.setLayoutManager(mLayoutmanager);
        mrecyclerview.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(ExaminationvActivityv1.this, R.drawable.recycler_divider)));
        adapter=new ExaminationAdapterv1(ExaminationvActivityv1.this,mdataset);
        mrecyclerview.setAdapter(adapter);
    }
}
