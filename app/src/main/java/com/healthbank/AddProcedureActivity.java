package com.healthbank;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.healthbank.adapter.ProcedureAdapter;
import com.healthbank.classes.Advicemasterdata;
import com.healthbank.database.DatabaseHelper;

import java.util.ArrayList;

public class AddProcedureActivity extends ActivityCommon {
    ArrayList<Advicemasterdata> mdataset;
    RecyclerView recyclerview;
    LinearLayoutManager mLayoutmanager;
    ProcedureAdapter adapter;
    TextView txt;
    EditText e1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_procedure);
        setmaterialDesign();
        back();
        attachUI();
    }

    private void attachUI() {
        mdataset = new ArrayList<>();
        mdataset = DatabaseHelper.getInstance(AddProcedureActivity.this).getadvicemasterdata();
        recyclerview = findViewById(R.id.recyclerview_1);
        mLayoutmanager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(mLayoutmanager);
        recyclerview.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(AddProcedureActivity.this, R.drawable.recycler_divider)));
        adapter = new ProcedureAdapter(AddProcedureActivity.this, mdataset);
        recyclerview.setAdapter(adapter);
        txt = findViewById(R.id.textview_1);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opendialog();
            }
        });
        e1= findViewById(R.id.edittext_1);
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
    }

    public void opendialog() {
        LayoutInflater inflater = LayoutInflater.from(AddProcedureActivity.this);
        View dialog_layout = inflater.inflate(R.layout.dialog_add_procedure, null);
        final AlertDialog.Builder db = new AlertDialog.Builder(AddProcedureActivity.this);
        db.setView(dialog_layout);
        final Dialog dialog = db.show();
        final EditText e1 = dialog_layout.findViewById(R.id.edittext_1);
        final EditText e2 = dialog_layout.findViewById(R.id.edittext_2);
        final Spinner sp= dialog_layout.findViewById(R.id.spinner_1);
        TextView txt2 = dialog_layout.findViewById(R.id.textview_2);
        TextView txt1 = dialog_layout.findViewById(R.id.textview_1);
        txt1.setText("Amount ( in " + getResources().getString(R.string.char_rupees) + " )");
        txt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues cvalues = new ContentValues();
                cvalues.put(DatabaseHelper.Title, e1.getText().toString());
                cvalues.put(DatabaseHelper.Description, "");
                cvalues.put(DatabaseHelper.type,sp.getSelectedItem().toString());
                cvalues.put(DatabaseHelper.DOCTORID, GlobalValues.docid);
                cvalues.put(DatabaseHelper.CODE, "0");
                cvalues.put(DatabaseHelper.PRACTICEID, GlobalValues.practiceid);
                cvalues.put(DatabaseHelper.SAVEDTIME, DateUtils.getSqliteTime());
                cvalues.put(DatabaseHelper.AMOUNT, e2.getText().toString());
                long id = DatabaseHelper.getInstance(AddProcedureActivity.this).saveadvicemaster(Constants.ADVICE, cvalues);
                if (id != -1) {
                    Advicemasterdata masterData = new Advicemasterdata();
                    masterData.setTitle(e1.getText().toString());
                    masterData.setDescription("");
                    masterData.setType(sp.getSelectedItem().toString());
                    masterData.setDoctorId(GlobalValues.docid);
                    masterData.setCode("0");
                    masterData.setPracticeId(GlobalValues.practiceid);
                    masterData.setAmount(e2.getText().toString());
                    mdataset.add(masterData);
                    adapter = new ProcedureAdapter(AddProcedureActivity.this, mdataset);
                    adapter.notifyDataSetChanged();
                }
                dialog.dismiss();
            }
        });
    }
}
