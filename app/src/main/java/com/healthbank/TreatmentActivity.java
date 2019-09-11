package com.healthbank;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.healthbank.adapter.TreatmentAdapter;
import com.healthbank.classes.Advicemasterdata;
import com.healthbank.database.DatabaseHelper;

import java.util.ArrayList;

public class TreatmentActivity extends ActivityCommon {
    RecyclerView recyclerview;
    LinearLayoutManager mLayoutmanager;
    TreatmentAdapter adapter;
    ArrayList<Advicemasterdata> mdataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment);
        setmaterialDesign();
        back();
        attachUI();
    }

    private void attachUI() {
        mdataset=new ArrayList<>();
        mdataset= DatabaseHelper.getInstance(TreatmentActivity.this).getadvicemasterdata();
        recyclerview = findViewById(R.id.recyclerview_1);
        mLayoutmanager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(mLayoutmanager);
        recyclerview.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(TreatmentActivity.this, R.drawable.recycler_divider)));
        adapter = new TreatmentAdapter(TreatmentActivity.this,mdataset);
        recyclerview.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu_action_save:
                for (int i=0;i<mdataset.size();i++)
                {
                    if(mdataset.get(i).isIsselected())
                    {
                        GlobalValues.adviceselectedmasterdata.add(mdataset.get(i));
                    }
                }
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
