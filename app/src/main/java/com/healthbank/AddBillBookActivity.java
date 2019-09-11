package com.healthbank;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.healthbank.adapter.BillBookAdapter;
import com.healthbank.classes.BillBookData;

import java.util.ArrayList;

public class AddBillBookActivity extends ActivityCommon {
    ArrayList<BillBookData> mdataset;
    RecyclerView recyclerview;
    LinearLayoutManager mLayoutmanager;
    BillBookAdapter adapter;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill_book);
        setmaterialDesign();
        back();
        setTitle("Bill Book");
        attachUI();
    }

    private void attachUI() {
        mdataset = new ArrayList<>();
        mdataset.add(new BillBookData());
        mdataset.add(new BillBookData());
        recyclerview = findViewById(R.id.recyclerview_1);
        mLayoutmanager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(mLayoutmanager);
        recyclerview.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(AddBillBookActivity.this, R.drawable.recycler_divider)));
        adapter = new BillBookAdapter(AddBillBookActivity.this, mdataset);
        recyclerview.setAdapter(adapter);
        txt = findViewById(R.id.textview_1);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opendialog();
            }
        });
    }

    public void opendialog() {
        LayoutInflater inflater = LayoutInflater.from(AddBillBookActivity.this);
        View dialog_layout = inflater.inflate(R.layout.dialog_add_billbook, null);
        final AlertDialog.Builder db = new AlertDialog.Builder(AddBillBookActivity.this);
        db.setView(dialog_layout);
        final Dialog dialog = db.show();
        final EditText e1 = dialog_layout.findViewById(R.id.edittext_1);
        final EditText e2 = dialog_layout.findViewById(R.id.edittext_2);
        TextView txt = dialog_layout.findViewById(R.id.textview_1);

        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BillBookData data = new BillBookData();
                data.setDrname(e1.getText().toString().trim());
                data.setAccountno(e2.getText().toString().trim());
                mdataset.add(data);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
    }
}
