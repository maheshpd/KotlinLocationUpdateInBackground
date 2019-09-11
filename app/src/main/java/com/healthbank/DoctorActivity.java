package com.healthbank;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Spinner;

import com.healthbank.adapter.DoctorAdapter;
import com.healthbank.classes.Connection;
import com.healthbank.classes.Department;
import com.healthbank.classes.Doctor;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DoctorActivity extends ActivityCommon {
    RecyclerView recyclerView;
    LinearLayoutManager mLayoutmanager;
    ArrayList<Doctor> mdataset;
    DoctorAdapter adapter;
    Department d;
    EditText e1;
    Spinner sp1;
    ArrayList<Department> departmentdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        setmaterialDesign();
        back();
        setTitle("Doctors");
        attachUI();
    }

    private void attachUI() {
        mdataset = new ArrayList<>();
        sp1 = findViewById(R.id.spinner_1);
        departmentdata = new ArrayList<>();
        try {
            mdataset = new ArrayList<>();
            genloading("loading");
            ConnectionManager.getInstance(DoctorActivity.this).getdepartment("all");
            recyclerView = findViewById(R.id.recyclerview_1);
            mLayoutmanager = new LinearLayoutManager(DoctorActivity.this);
            recyclerView.setLayoutManager(mLayoutmanager);
            recyclerView.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(DoctorActivity.this, R.drawable.recycler_divider)));
            adapter = new DoctorAdapter(DoctorActivity.this, mdataset);
            recyclerView.setAdapter(adapter);

          /*  ArrayAdapter<Department> departmetadapter = new ArrayAdapter<Department>(this, R.layout.list_item_spinner, departmentdata);
            sp1.setAdapter(departmetadapter);

            sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    adapter.departmentfilter(departmentdata.get(i).getName());
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });*/

            e1 = findViewById(R.id.edittext_1);
            e1.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    adapter.filter(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            /*if (GlobalValues.d != null) {
                for (int i = 0; i < departmentdata.size(); i++) {
                    if (departmentdata.get(i).getId() == GlobalValues.d.getId()) {
                        sp1.setSelection(i);
                        break;
                    }
                }
            }*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResponsed(int statuscode, int accesscode, String data) {
        super.onResponsed(statuscode, accesscode, data);
        Log.e("onresp", "onresp " + statuscode + " " + accesscode + " " + Connection.DEPARTMENT.ordinal());
        if (statuscode == Constants.STATUS_OK) {
            if (accesscode == Connection.BookAppointment.ordinal()) {
                finish();
            } else if (accesscode == Connection.DEPARTMENT.ordinal()) {
                Log.e("data ", "data ");

                try {
                    JSONObject obj = new JSONObject(GlobalValues.TEMP_STR).getJSONObject("root").getJSONObject("subroot");
                    if (obj.has("doctor")) {
                        JSONArray array = obj.optJSONArray("doctor");
                        for (int i = 0; i < array.length(); i++) {
                            Doctor d = new Doctor(array.getJSONObject(i));
                            mdataset.add(d);
                        }

                        adapter = new DoctorAdapter(DoctorActivity.this, mdataset);
                        recyclerView.setAdapter(adapter);

                        Log.e("data ", "data " + mdataset.size());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        GlobalValues.d = null;
        super.onDestroy();
    }
}
