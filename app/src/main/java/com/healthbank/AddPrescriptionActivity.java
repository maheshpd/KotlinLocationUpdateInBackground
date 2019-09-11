package com.healthbank;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;

import com.healthbank.adapter.PrescriptionAdapter;
import com.healthbank.classes.Drug;

import java.util.ArrayList;

public class AddPrescriptionActivity extends ActivityCommon {
    LinearLayout layout1, layout2;
    ArrayList<String> data;
    ArrayAdapter<String> adapter;
    Button b1;
    RecyclerView recyclerView;
    LinearLayoutManager mLayoutmanager;
    PrescriptionAdapter adapter1;
    ArrayList<Drug> mdataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prescription);
        setmaterialDesign();
        back();
        setTitle("Add Drug");
        try {
            layout1 = findViewById(R.id.layout1);
            layout2 = findViewById(R.id.layout2);
            b1 = findViewById(R.id.button_1);

            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LayoutInflater inflater = LayoutInflater.from(AddPrescriptionActivity.this);
                    View dialog_layout = inflater.inflate(R.layout.dialog_add_prescription, null);
                    Button bt = dialog_layout.findViewById(R.id.button_1);
                    bt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });
                    AlertDialog.Builder db = new AlertDialog.Builder(AddPrescriptionActivity.this);
                    db.setView(dialog_layout);
                    AlertDialog dialog = db.show();
                }
            });

            data = new ArrayList<>();
            data.add("avvvvv");
            data.add("bvvvvv");
            data.add("cvvvvv");
            data.add("dvvvvv");
            data.add("evvvvv");
            data.add("fvvvvv");
            data.add("gvvvvv");

            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
            addautotxt();
            addautotxtdiagnostic();

            recyclerView = findViewById(R.id.recyclerview_1);
            mLayoutmanager = new LinearLayoutManager(AddPrescriptionActivity.this);
            recyclerView.setLayoutManager(mLayoutmanager);
            recyclerView.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(AddPrescriptionActivity.this, R.drawable.recycler_divider)));

            mdataset = new ArrayList<>();
            mdataset.add(new Drug());
            mdataset.add(new Drug());
            mdataset.add(new Drug());
            mdataset.add(new Drug());
            mdataset.add(new Drug());
            adapter1 = new PrescriptionAdapter(AddPrescriptionActivity.this, mdataset);
            recyclerView.setAdapter(adapter1);
        } catch (Exception e) {
            //Log.e("error ", "error " + e.toString());
        }
    }

    private void addautotxt(){
        final AutoCompleteTextView autotxt = new AutoCompleteTextView(this);
        layout1.addView(autotxt);
        autotxt.setThreshold(1);
        autotxt.setAdapter(adapter);
        autotxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                addautotxt();
            }
        });
    }

    private void addautotxtdiagnostic(){
        final AutoCompleteTextView autotxt = new AutoCompleteTextView(this);
        layout2.addView(autotxt);
        autotxt.setThreshold(1);
        autotxt.setAdapter(adapter);
        autotxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                addautotxtdiagnostic();
            }
        });
    }
}
