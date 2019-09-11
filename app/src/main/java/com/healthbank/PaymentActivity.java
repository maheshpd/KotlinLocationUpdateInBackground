package com.healthbank;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.healthbank.adapter.SelectedTreatmentAdapter;
import com.healthbank.classes.Connection;
import com.healthbank.classes.ProcedureData;

import java.util.ArrayList;

public class PaymentActivity extends ActivityCommon {
    TextView txt1, txt2;
    RecyclerView recyclerview;
    LinearLayoutManager mlayoutmanager;
    SelectedTreatmentAdapter adapter;
    ArrayList<ProcedureData> data;
    boolean isfirst = true;
    Double totalcount = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        setmaterialDesign();
        back();
        setTitle("Fees");
        attachUI();
        GlobalValues.treatmentdata.add(new ProcedureData());
        GlobalValues.treatmentdata.add(new ProcedureData());
        GlobalValues.treatmentdata.add(new ProcedureData());
        GlobalValues.treatmentdata.add(new ProcedureData());
        data = new ArrayList<>();
        for (int i = 0; i < GlobalValues.treatmentdata.size(); i++) {
            if (GlobalValues.treatmentdata.get(i).isIsselected())
                data.add(GlobalValues.treatmentdata.get(i));
        }

        recyclerview = findViewById(R.id.recyclerview_1);
        recyclerview.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(PaymentActivity.this, R.drawable.recycler_divider)));
        LinearLayoutManager mLayoutmanager = new LinearLayoutManager(PaymentActivity.this);
        recyclerview.setLayoutManager(mLayoutmanager);
        isfirst = false;
    }

    @Override
    protected void onResume() {
        try {
            if (!isfirst) {
                data.clear();
                for (int i = 0; i < GlobalValues.treatmentdata.size(); i++) {
                    if (GlobalValues.treatmentdata.get(i).isIsselected())
                        data.add(GlobalValues.treatmentdata.get(i));
                }
                adapter.notifyDataSetChanged();
            }
            updatetotalcount();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onResume();
    }

    private void attachUI() {
        txt1 = findViewById(R.id.textview_1);
        txt2 = findViewById(R.id.textview_2);
        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaymentActivity.this, TreatmentActivity.class);
                startActivity(intent);
            }
        });
    }

    public void updatetotalcount() {
        totalcount = 0.0;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getItemcount() > 0) {
                Double price = Double.parseDouble(data.get(i).getCost()) * data.get(i).getItemcount();
                totalcount = totalcount + price;
            }
        }

        if (totalcount > 0)
            txt2.setText("Total : " + PaymentActivity.this.getResources().getString(R.string.char_rupees) + totalcount);
        else
            txt2.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action_pay, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_pay:
                if (totalcount > 0) {
                    Intent intent = new Intent(PaymentActivity.this, PayBillActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "please add treatment", Toast.LENGTH_LONG).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        GlobalValues.treatmentdata.clear();
        super.onDestroy();
    }

    @Override
    protected void onResponsed(int statuscode, int accesscode, String data) {
        super.onResponsed(statuscode, accesscode, data);
        if (statuscode == Constants.STATUS_OK) {
            if (accesscode == Connection.billpaid.ordinal()) {
                finish();
            }
        }
    }
}
