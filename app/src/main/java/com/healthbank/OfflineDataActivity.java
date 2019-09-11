package com.healthbank;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.healthbank.adapter.OfflinedataAdapter;
import com.healthbank.classes.Connection;
import com.healthbank.classes.OfflineData;
import com.healthbank.database.DatabaseHelper;

import java.util.ArrayList;

public class OfflineDataActivity extends ActivityCommon {
    RecyclerView mRecyclerview;
    OfflinedataAdapter adapter;
    ArrayList<OfflineData> mdataset;
    LinearLayoutManager mLayoutmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_data);
        setmaterialDesign();
        back();
        attachUI();
    }

    public void attachUI() {
        mRecyclerview = findViewById(R.id.recyclerview_1);
        mdataset = new ArrayList<>();
        mLayoutmanager=new LinearLayoutManager(this);
        mRecyclerview.setLayoutManager(mLayoutmanager);
        mdataset.add(new OfflineData("Patient", DatabaseHelper.getInstance(this).getofflinepatientcount(), "Patient"));
        mdataset.add(new OfflineData("Appointment", DatabaseHelper.getInstance(this).getofflineappointmentcount(), "Appointment"));
        mdataset.add(new OfflineData("Visit", DatabaseHelper.getInstance(this).getofflinevisitcount(), "Visit"));
        mdataset.add(new OfflineData("Health History", DatabaseHelper.getInstance(this).getofflinetemplatecount(), "Health History"));
        mdataset.add(new OfflineData("Master", DatabaseHelper.getInstance(this).getofflinemastercount(), "Master"));
        mRecyclerview.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(this, R.drawable.recycler_divider)));
        adapter = new OfflinedataAdapter(this, mdataset);
        mRecyclerview.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sync,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu_action_sync:
                genloading("loading");
                ConnectionManager.getInstance(OfflineDataActivity.this).Syncofflinedata();

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResponsed(int statuscode, int accesscode, String data) {
        super.onResponsed(statuscode, accesscode, data);
        if(statuscode==Constants.STATUS_OK)
        if(accesscode==Connection.Offllinedatasaved.ordinal())
        {
         mdataset.clear();
         mdataset.add(new OfflineData("Patient", DatabaseHelper.getInstance(this).getofflinepatientcount(), "Patient"));
         mdataset.add(new OfflineData("Visit", DatabaseHelper.getInstance(this).getofflinevisitcount(), "Visit"));
         mdataset.add(new OfflineData("Health History", DatabaseHelper.getInstance(this).getofflinetemplatecount(), "Health History"));

         adapter.notifyDataSetChanged();
        }
    }
}
