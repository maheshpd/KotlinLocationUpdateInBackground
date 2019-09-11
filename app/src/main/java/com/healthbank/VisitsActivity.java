package com.healthbank;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.healthbank.adapter.VisitsAdapter;
import com.healthbank.classes.Connection;
import com.healthbank.classes.Visits;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class VisitsActivity extends ActivityCommon {
    RecyclerView recyclerview;
    LinearLayoutManager mLayoutmanager;
    VisitsAdapter adapter;
    ArrayList<Visits> mdataset;
    TextView txt1;
    int flag = 0;
    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visits);
        setmaterialDesign();
        back();
        setTitle("Visits");
        attachUI();
    }

    private void attachUI() {
        pref= PreferenceManager.getDefaultSharedPreferences(VisitsActivity.this);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            flag = b.getInt("flag");
        }

        recyclerview = findViewById(R.id.recyclerview_1);
        mLayoutmanager = new LinearLayoutManager(VisitsActivity.this);

        recyclerview.setLayoutManager(mLayoutmanager);
        recyclerview.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(VisitsActivity.this, R.drawable.recycler_divider)));

        mdataset = new ArrayList<>();
        adapter = new VisitsAdapter(VisitsActivity.this, mdataset, flag);
        recyclerview.setAdapter(adapter);
        if (InternetUtils.getInstance(VisitsActivity.this).available()) {
            genloading("loading");
            ConnectionManager.getInstance(VisitsActivity.this).getvisits(Integer.toString(GlobalValues.selectedpt.getPatientid()));
        }
        txt1 = findViewById(R.id.textview_1);
        txt1.setTypeface(Fonter.getTypefacesemibold(this));
        txt1.setVisibility(View.GONE);
       /* txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String visitid = "";
                for (int i = 0; i < mdataset.size(); i++) {
                    if (mdataset.get(i).isIsselected())
                        visitid = visitid + "," + mdataset.get(i).getVisitid();
                }
                if (visitid.length() > 0)
                    getprescriptiondata(visitid, flag);
                else
                    Toast.makeText(getApplicationContext(), "Please select visit", Toast.LENGTH_LONG).show();
            }
        });*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        dismissLoading();
    }

    @Override
    protected void onResponsed(int statuscode, int accesscode, String data) {
        super.onResponsed(statuscode, accesscode, data);
        if (statuscode == Constants.STATUS_OK) {
            if (accesscode == Connection.GET_VISITS.ordinal()) {
                try {
                    mdataset.clear();
                    mdataset.add(new Visits(0));
                    JSONArray array = new JSONArray(GlobalValues.TEMP_STR);
                    if (array.length() > 0) {
                        JSONArray array1 = array.getJSONArray(0);
                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<Visits>>() {
                        }.getType();
                        ArrayList<Visits> dataset = gson.fromJson(array1.toString(), type);
                        mdataset.addAll(dataset);
                        adapter.notifyDataSetChanged();
                        adapter = new VisitsAdapter(VisitsActivity.this, mdataset, flag);
                        recyclerview.setAdapter(adapter);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (accesscode == Connection.GET_PRESCRIPTION.ordinal()) {
                dismissLoading();
            }
        }
    }
}
