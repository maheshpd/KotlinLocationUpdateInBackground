package com.healthbank;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.healthbank.adapter.PartialPaidBillAdapter;
import com.healthbank.classes.BillingClass;
import com.healthbank.classes.BillingDetails;
import com.healthbank.classes.Connection;
import com.healthbank.database.DatabaseHelper;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class PartialPaymentActivity extends ActivityCommon {
    BillingClass billing;
    TextView txt1, txt2, txt3, txt4, txt5, txt6, txt7, txt8, txt9, txt10, txt11, txt12;
    RecyclerView recyclerview;
    LinearLayoutManager mlayoutmanager;
    PartialPaidBillAdapter adapter;
    ArrayList<BillingDetails> data;
    Button b1;
    Spinner sp2, sp3, sp4;
    Double tax = 0.0;
    int discounttype = 0;
    int taxtype = 0;
    Double price = 0.0;
    Double Paybaleamount = 0.0;
    Double discount = 0.0;
    Double Dueamount = 0.0;
    Double paidamount = 0.0;
    EditText e1, e2, e3, e4,e7, e8, e9, e10;//e6, e7,
    String rupee = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partial_payment);
        setmaterialDesign();
        back();

        Bundle b = getIntent().getExtras();
        if (b != null) {
            billing = (BillingClass) b.getSerializable("data");
        }

        if (billing.getBillno().length() > 0)
            setTitle(billing.getBillno());

        rupee = getResources().getString(R.string.char_rupees);
        txt1 = findViewById(R.id.textview_1);
        txt2 = findViewById(R.id.textview_2);
        txt3 = findViewById(R.id.textview_3);
        txt4 = findViewById(R.id.textview_4);
        txt5 = findViewById(R.id.textview_5);
        txt6 = findViewById(R.id.textview_6);
        txt7 = findViewById(R.id.textview_7);
        txt8 = findViewById(R.id.textview_8);
        txt9 = findViewById(R.id.textview_9);
        txt10 = findViewById(R.id.textview_10);
        txt11 = findViewById(R.id.textview_11);
        txt12 = findViewById(R.id.textview_12);
        txt1.setText(billing.getReferfrom() + " Dr. kirti");
        txt2.setText(billing.getBillbookid() + " Dr. Arnav");
        txt3.setText(rupee + billing.getTotalamt());
        txt4.setText(rupee + 0);
        txt5.setText(billing.getReferfrom());
        txt6.setText(billing.getBillbookid());
        txt8.setText(getResources().getString(R.string.char_rupees) + billing.getDueamt());
      //  txt9.setText(billing.getReferfrom());
        txt10.setText(rupee + billing.getDiscount());
        txt11.setText(rupee + billing.getTaxamt());
        txt12.setText(rupee + billing.getPaidamt());
        e1 = findViewById(R.id.edittext_1);
        e2 = findViewById(R.id.edittext_2);
        e3 = findViewById(R.id.edittext_3);
        e4 = findViewById(R.id.edittext_4);
        e7 = findViewById(R.id.edittext_7);
        e8 = findViewById(R.id.edittext_8);
        e9 = findViewById(R.id.edittext_9);
        e10 = findViewById(R.id.edittext_10);
        b1 = findViewById(R.id.button_1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues c = new ContentValues();
                c.put(DatabaseHelper.PAIDAMT, Double.parseDouble(billing.getPaidamt())+paidamount);
                c.put(DatabaseHelper.DUEAMT, Dueamount);
                c.put(DatabaseHelper.MODDATE, DateUtils.getSqliteTime());
                c.put(DatabaseHelper.DESCRIPTION, e7.getText().toString().trim());
                c.put(DatabaseHelper.REMARK, e4.getText().toString().trim());
                c.put(DatabaseHelper.PREFERREDDATE, DateUtils.getSqliteTime());
                Toast.makeText(PartialPaymentActivity.this, "Thank you", Toast.LENGTH_LONG).show();
                DatabaseHelper.getInstance(PartialPaymentActivity.this).updatereceiptid(c, billing.getId());

                Intent intent = new Intent("fragmentlabadded");
                intent.putExtra(Constants.BROADCAST_URL_ACCESS, Connection.appointmentadded.ordinal());
                LocalBroadcastManager.getInstance(PartialPaymentActivity.this).sendBroadcast(intent);
                finish();
            }
        });
        Paybaleamount = Double.parseDouble(billing.getDueamt());

        e1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                paidamount = 0.0;
                if (e2.getText().toString().length() > 0) {
                    paidamount = paidamount + Double.parseDouble(e2.getText().toString().trim());
                }
                if (e1.getText().toString().length() > 0) {
                    paidamount = paidamount + Double.parseDouble(e1.getText().toString().trim());
                }
                txt5.setText("" + paidamount);
                Dueamount = (Paybaleamount - paidamount);

                if (Dueamount >= 0) {
                    txt6.setText(rupee + Dueamount);
                    txt9.setText("Due After Payment");
                } else {
                    txt6.setText(rupee + Double.toString(Dueamount).replace("-", ""));
                    txt9.setText("Advance After Payment");
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        e2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                paidamount = 0.0;
                if (e2.getText().toString().length() > 0) {
                    paidamount = Double.parseDouble(e2.getText().toString().trim());
                }

                if (e1.getText().toString().length() > 0) {
                    paidamount = paidamount + Double.parseDouble(e1.getText().toString().trim());
                }
                txt5.setText("" + paidamount);
                Dueamount = (Paybaleamount - paidamount);
                if (Dueamount >= 0) {
                    txt6.setText(getResources().getString(R.string.char_rupees) + Dueamount);
                    txt9.setText("Due After Payment");
                } else {
                    txt6.setText(getResources().getString(R.string.char_rupees) + Double.toString(Dueamount).replace("-", ""));
                    txt9.setText("Advance After Payment");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        recyclerview = findViewById(R.id.recyclerview_1);
        mlayoutmanager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(mlayoutmanager);
        JSONArray array = DatabaseHelper.getInstance(this).getbillingdetails(billing.getId());
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<BillingDetails>>() {
        }.getType();
        ArrayList<BillingDetails> mdataset = gson.fromJson(array.toString(), type);
        adapter = new PartialPaidBillAdapter(mdataset, this);
        recyclerview.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void calculatepayableamount() {
        discount = 0.0;
        Paybaleamount = price;
     /*   if (e5.getText().toString().trim().length() > 0 && price > 0) {
            switch (discounttype) {
                case 0:
                    discount = Double.parseDouble(e5.getText().toString().trim());
                    Paybaleamount = Paybaleamount - discount;
                    break;
                case 1:
                    discount = (Double.parseDouble(e5.getText().toString().trim()) * Paybaleamount) / 100;
                    Paybaleamount = Paybaleamount - discount;
                    break;
            }
        }*/

        tax = 0.0;
      /*  if (e6.getText().toString().trim().length() > 0 && price > 0) {
            switch (taxtype) {
                case 0:
                    tax = Double.parseDouble(e6.getText().toString().trim());
                    Paybaleamount = Paybaleamount + tax;
                    break;
                case 1:
                    tax = (Double.parseDouble(e6.getText().toString().trim()) * Paybaleamount) / 100;
                    Paybaleamount = Paybaleamount + tax;
                    break;
            }
        }*/

        txt8.setText(getResources().getString(R.string.char_rupees) + Dueamount);
        if (Paybaleamount > 0 && e2.getText().toString().trim().length() > 0) {
            Dueamount = (Paybaleamount - Double.parseDouble(e2.getText().toString().trim()));
            if (Dueamount >= 0) {
                txt6.setText(getResources().getString(R.string.char_rupees) + Dueamount);
                txt9.setText("Due After Payment");
            } else {
                txt6.setText(getResources().getString(R.string.char_rupees) + Double.toString(Dueamount).replace("-", ""));
                txt9.setText("Advance After Payment");
            }
        }

        paidamount = 0.0;
        if (e2.getText().toString().length() > 0) {
            paidamount = Double.parseDouble(e2.getText().toString().trim());
        }

        if (e1.getText().toString().length() > 0) {
            paidamount = paidamount + Double.parseDouble(e1.getText().toString().trim());
        }
    }

}
