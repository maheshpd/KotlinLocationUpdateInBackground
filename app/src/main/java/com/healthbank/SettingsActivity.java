package com.healthbank;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.healthbank.adapter.MasterDataAdapter;

import java.util.ArrayList;

public class SettingsActivity extends ActivityCommon {
    RecyclerView recyclerview;
    MasterDataAdapter adapter;
    ArrayList<String> mdataset;
    LinearLayoutManager mLayoutmanager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setmaterialDesign();
        back();
        setTitle("Settings");
        attachUI();
    }

    private void attachUI() {
        recyclerview = findViewById(R.id.recyclerview_1);
        mLayoutmanager = new LinearLayoutManager(SettingsActivity.this);
        recyclerview.setLayoutManager(mLayoutmanager);
        mdataset = new ArrayList<>();
        mdataset.add("Bill Book");
        mdataset.add("Daignosis");
        mdataset.add("Treatement Procedure");
        mdataset.add("Lab");
        mdataset.add("Drug");
        mdataset.add("Unit");
        mdataset.add("Body Part");
        mdataset.add("Dosage");
        adapter = new MasterDataAdapter(SettingsActivity.this, mdataset);
        recyclerview.setAdapter(adapter);
        recyclerview.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(SettingsActivity.this, R.drawable.recycler_divider)));
        /*txt1 = (TextView) findViewById(R.id.textview_1);
        txt2 = (TextView) findViewById(R.id.textview_2);
        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, AddBillBookActivity.class);
                startActivity(intent);
            }
        });

        txt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, AddProcedureActivity.class);
                startActivity(intent);
            }
        });*/
    }
}
