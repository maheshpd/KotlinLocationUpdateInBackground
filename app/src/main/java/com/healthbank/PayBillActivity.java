package com.healthbank;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.healthbank.adapter.BillingAdapter;
import com.healthbank.classes.Advicemasterdata;
import com.healthbank.classes.Connection;

import java.util.ArrayList;

public class PayBillActivity extends ActivityCommon {
    TextView txt1, txt2, txt3, txt4, txt5, txt6;
    EditText e1, e2, e3, e4;
    RecyclerView recyclerview;
    LinearLayoutManager mlayoutmanager;
    BillingAdapter adapter;
    ArrayList<Advicemasterdata> data;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_bill);
        setmaterialDesign();
        back();
        attachUI();
    }

    private void attachUI() {
        txt1 = findViewById(R.id.textview_1);
        txt2 = findViewById(R.id.textview_2);
        txt3 = findViewById(R.id.textview_3);
        txt4 = findViewById(R.id.textview_4);
        txt5 = findViewById(R.id.textview_5);
        txt6 = findViewById(R.id.textview_6);

        b = findViewById(R.id.button_1);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Constants.BROADCAST_WIZARD);
                intent.putExtra(Constants.BROADCAST_RESPONSE_CODE, Constants.STATUS_OK);
                intent.putExtra(Constants.BROADCAST_URL_ACCESS, Connection.billpaid.ordinal());
                LocalBroadcastManager.getInstance(PayBillActivity.this).sendBroadcast(intent);
                Toast.makeText(getApplicationContext(), "Thank you", Toast.LENGTH_LONG).show();
                finish();
            }
        });
        txt1.setText(" Dr. Kirti");
        txt2.setText(" Dr. Arnav");
        data = new ArrayList<>();
        data.addAll(GlobalValues.adviceselectedmasterdata);
        recyclerview = findViewById(R.id.recyclerview_1);
        recyclerview.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(PayBillActivity.this, R.drawable.recycler_divider)));
        LinearLayoutManager mLayoutmanager = new LinearLayoutManager(PayBillActivity.this);
        recyclerview.setLayoutManager(mLayoutmanager);
        adapter = new BillingAdapter(PayBillActivity.this, data);
        recyclerview.setAdapter(adapter);
        txt3.setText(PayBillActivity.this.getResources().getString(R.string.char_rupees) + getTotalcost());
        txt4.setText(PayBillActivity.this.getResources().getString(R.string.char_rupees) + 0);
    }

    public Double getTotalcost() {
        Double totalprice = 0.0;
        for (int i = 0; i < data.size(); i++) {
            if (Long.parseLong(data.get(i).getAmount()) > 0) {
                Double price = Double.parseDouble(data.get(i).getAmount()) * 1;
                totalprice = totalprice + price;
            }
        }
        return totalprice;
    }

    @Override
    protected void onResume() {
        super.onResume();
        try{
            data.clear();
            data.addAll(GlobalValues.adviceselectedmasterdata);
            adapter.notifyDataSetChanged();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
