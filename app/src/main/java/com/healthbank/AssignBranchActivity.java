package com.healthbank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.healthbank.classes.Connection;
import com.healthbank.classes.Department;
import com.healthbank.classes.Doctor;
import com.healthbank.classes.Template;
import com.healthbank.classes.loc;
import com.healthbank.database.DatabaseHelper;
import com.healthbank.groupvideocall.openvcall.AGApplication;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AssignBranchActivity extends ActivityCommon {
    Spinner sp1, sp2, sp3;
    TextView txt1, txt2, txt3;
    Doctor doc;
    Button bt;
    LinearLayout layout1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_branch);
        setmaterialDesign();
        back();
        attachUI();
        Log.e("in AssignBranchActivity", "in AssignBranchActivity ");
    }

    private void attachUI() {
        Bundle b = getIntent().getExtras();
       /* if (b.containsKey("data"))
            doc = (Doctor) b.getSerializable("data");*/
        doc = GlobalValues.d;
        sp1 = findViewById(R.id.spinner_1);
        sp2 = findViewById(R.id.spinner_2);
        sp3 = findViewById(R.id.spinner_3);
        txt1 = findViewById(R.id.textview_1);
        txt2 = findViewById(R.id.textview_2);
        if (GlobalValues.doctordata.size() > 0) {
            doc = GlobalValues.doctordata.get(0);
            GlobalValues.d = GlobalValues.doctordata.get(0);
        }

        Log.e("size ", "size " + GlobalValues.doctordata.size() + " " + doc.getLoc());
        if (GlobalValues.doctordata.size() == 1) {
            GlobalValues.d = GlobalValues.doctordata.get(0);
            doc = GlobalValues.doctordata.get(0);
            if (doc.getLoc().size() == 1) {
                layout1 = findViewById(R.id.layout_1);
                layout1.setVisibility(View.GONE);
                ArrayAdapter<Department> spinnerArrayAdapter = new ArrayAdapter<Department>
                        (AssignBranchActivity.this, android.R.layout.simple_spinner_item,
                                doc.getLoc().get(0).getMaindept()); //selected item will look like a spinner set from XML
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp2.setAdapter(spinnerArrayAdapter);
                if (doc.getLoc().get(0).getMaindept().size() == 0) {
                    loc Loc = doc.getLoc().get(0);
                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(AssignBranchActivity.this);
                    SharedPreferences.Editor edit = pref.edit();
                    edit.putString("CustomerId", Loc.getCustomerId());
                    edit.putString("ProviderId", doc.getProviderId());
                    edit.putString("emr_type", doc.getEmr_type());
                    edit.putString("EmrSpeciality", doc.getEmrSpeciality());
                    edit.putString("BranchName", Loc.getName());
                    edit.putString("parentBranchId", Loc.getParentBranchId());
                    edit.putString("DepartmentId", "0");
                    edit.putString("DepartmentName", "");
                    edit.putString("DrName", doc.getName());
                    edit.putString("DepartmentParentId", "0");
                    edit.putBoolean("islogin", true);
                    edit.commit();
                    GlobalValues.docid = Integer.parseInt(doc.getProviderId());
                    GlobalValues.branchid = Integer.parseInt(Loc.getCustomerId());
                    GlobalValues.DrName = doc.getName();
                    GlobalValues.selectedoctor = doc;
                    AGApplication.the().getmAgoraAPI().channelJoin(Constants.channel);
                    gotonext();
                }
            }
        }

        txt1.setTypeface(Fonter.getTypefacesemibold(AssignBranchActivity.this));
        txt2.setTypeface(Fonter.getTypefacesemibold(AssignBranchActivity.this));

        ArrayAdapter<loc> spinnerArrayAdapter1 = new ArrayAdapter<loc>
                (this, android.R.layout.simple_spinner_item,
                        doc.getLoc()); //selected item will look like a spinner set from XML
        spinnerArrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(spinnerArrayAdapter1);

        ArrayAdapter<Doctor> spinnerArrayAdapterdoc = new ArrayAdapter<Doctor>
                (this, android.R.layout.simple_spinner_item,
                        GlobalValues.doctordata); //selected item will look like a spinner set from XML
        spinnerArrayAdapterdoc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp3.setAdapter(spinnerArrayAdapterdoc);
        sp3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                doc = GlobalValues.doctordata.get(position);
                GlobalValues.d = GlobalValues.doctordata.get(position);
                ArrayAdapter<loc> spinnerArrayAdapter = new ArrayAdapter<loc>
                        (AssignBranchActivity.this, android.R.layout.simple_spinner_item,
                                doc.getLoc()); //selected item will look like a spinner set from XML
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp1.setAdapter(spinnerArrayAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayAdapter<Department> spinnerArrayAdapter = new ArrayAdapter<Department>
                        (AssignBranchActivity.this, android.R.layout.simple_spinner_item,
                                doc.getLoc().get(position).getMaindept()); //selected item will look like a spinner set from XML
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp2.setAdapter(spinnerArrayAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        bt = findViewById(R.id.button_1);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loc Loc = doc.getLoc().get(sp1.getSelectedItemPosition());
                Department dept = doc.getLoc().get(sp1.getSelectedItemPosition()).getMaindept().get(sp2.getSelectedItemPosition());
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(AssignBranchActivity.this);
                SharedPreferences.Editor edit = pref.edit();
                edit.putString("CustomerId", Loc.getCustomerId());
                edit.putString("ProviderId", doc.getProviderId());
                edit.putString("BranchName", Loc.getName());
                edit.putString("parentBranchId", Loc.getParentBranchId());
                edit.putString("DepartmentId", dept.getId());
                edit.putString("DepartmentName", dept.getName());
                edit.putString("DepartmentParentId", dept.getParentId());
                edit.putBoolean("islogin", true);
                edit.commit();

                GlobalValues.docid = Integer.parseInt(doc.getProviderId());
                GlobalValues.branchid = Integer.parseInt(Loc.getCustomerId());
                GlobalValues.selectedoctor = doc;
                GlobalValues.DrName = doc.getName();
                edit.putString("DrName", doc.getName());
                AGApplication.the().getmAgoraAPI().channelJoin(Constants.channel);
                gotonext();
              /*  Intent intent = new Intent(AssignBranchActivity.this, PatientlistActivity.class);
                startActivity(intent);
                finish();*/
            }
        });
    }

    public void gotonext() {
        if (InternetUtils.getInstance(AssignBranchActivity.this).available()) {
            try {
                genloading("loading...");
                JSONObject obj = new JSONObject();
                obj.put("PracticeId", GlobalValues.branchid);
                obj.put("Grouptype", "");
                obj.put("Speciality", "");
                obj.put("TempName", "");
                obj.put("pagenumber", "1");
                obj.put("pagesize", "100");
                obj.put("connectionid", Constants.projectid);
                genloading("loading");
                ConnectionManager.getInstance(AssignBranchActivity.this).getQuestionTemplates(obj.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(AssignBranchActivity.this, "No Network", Toast.LENGTH_LONG).show();
           /* Intent intent = new Intent(AssignBranchActivity.this, PatientlistActivity.class);
            startActivity(intent);
            finish();*/
        }
    }

    @Override
    protected void onResponsed(int statuscode, int accesscode, String data) {
        super.onResponsed(statuscode, accesscode, data);
        if (statuscode == Constants.STATUS_OK) {
            if (accesscode == Connection.getQuestionTemplates.ordinal()) {
                try {
                    JSONArray array = new JSONArray(GlobalValues.TEMP_STR);
                    parsetemplatenames(array);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (accesscode == Connection.GetTemplateQuestionAnswerfreshdata.ordinal()) {
                Intent intent = new Intent(AssignBranchActivity.this, PatientlistActivity.class);
                startActivity(intent);
                this.finish();
            }
        }
    }

    public void parsetemplatenames(JSONArray array) {
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Template>>() {
            }.getType();
            ArrayList<Template> mdataset = gson.fromJson(array.toString(), type);
            if (mdataset == null)
                mdataset = new ArrayList<>();

            for (int i = 0; i < mdataset.size(); i++) {
                DatabaseHelper.getInstance(AssignBranchActivity.this).savetemplatenamedata(mdataset.get(i).getContentValues(), Integer.toString(mdataset.get(i).getTemplateId()));
            }
            ConnectionManager.getInstance(AssignBranchActivity.this).GetTemplateQuestionAnswerFreshdata(mdataset);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
