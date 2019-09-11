package com.healthbank;

import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;

import com.healthbank.adapter.PrescriptionAdapter;
import com.healthbank.classes.Drug;

import java.util.ArrayList;

public class AddLabsActivity extends ActivityCommon {
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
        setContentView(R.layout.activity_add_labs);
        setmaterialDesign();
        back();
        try {
            layout1 = findViewById(R.id.layout1);
            layout2 = findViewById(R.id.layout2);
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
            mLayoutmanager = new LinearLayoutManager(AddLabsActivity.this);
            recyclerView.setLayoutManager(mLayoutmanager);
            recyclerView.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(AddLabsActivity.this, R.drawable.recycler_divider)));

            mdataset = new ArrayList<>();
            mdataset.add(new Drug());
            mdataset.add(new Drug());
            mdataset.add(new Drug());
            mdataset.add(new Drug());
            mdataset.add(new Drug());

            adapter1 = new PrescriptionAdapter(AddLabsActivity.this, mdataset);
            recyclerView.setAdapter(adapter1);
        } catch (Exception e) {
            Log.e("error ", "error " + e.toString());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_save:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addautotxt() {
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

    private void addautotxtdiagnostic() {
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
