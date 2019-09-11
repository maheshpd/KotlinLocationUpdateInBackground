package com.healthbank;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.healthbank.adapter.MasterEditAdapter;
import com.healthbank.classes.MasterData;
import com.healthbank.database.DatabaseHelper;

import java.util.ArrayList;

public class MasterActivity extends ActivityCommon {
    String category = "";
    RecyclerView mRecyclerview;
    LinearLayoutManager mLayoutmanager;
    MasterEditAdapter adapter;
    ArrayList<MasterData> mdataset;
    int catid = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);
        setmaterialDesign();
        back();
        Bundle b = getIntent().getExtras();
        if (b != null)
            if (b.containsKey("mastername")) {
                category = b.getString("mastername");
                setTitle(category);
            }
        attachUI();
    }

    private void attachUI() {
        try {
            catid = DatabaseHelper.getInstance(MasterActivity.this).getId(category);
            mdataset = new ArrayList<>();
            mdataset.addAll(DatabaseHelper.getInstance(this).getmstexamination(catid));
            mRecyclerview = findViewById(R.id.recyclerview_1);
            mLayoutmanager = new LinearLayoutManager(MasterActivity.this);
            mRecyclerview.setLayoutManager(mLayoutmanager);
            adapter = new MasterEditAdapter(MasterActivity.this, mdataset);
            mRecyclerview.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_add:
                opendialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void opendialog() {
        LayoutInflater inflater = LayoutInflater.from(MasterActivity.this);
        View dialog_layout = inflater.inflate(R.layout.dialog_add_master, null);
        final AlertDialog.Builder db = new AlertDialog.Builder(MasterActivity.this);
        db.setView(dialog_layout);
        final Dialog dialog = db.show();
        final EditText e1 = dialog_layout.findViewById(R.id.edittext_1);
        Button bt = dialog_layout.findViewById(R.id.button_1);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper.getInstance(MasterActivity.this).savemstexamination(catid,e1.getText().toString());
                MasterData master=new MasterData();
                master.setSubCategoryid(catid);
                master.setName(e1.getText().toString());
                mdataset.add(master);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
    }
}
