package com.healthbank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.healthbank.classes.Connection;
import com.healthbank.classes.Template;
import com.healthbank.database.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SplashActivity extends ActivityCommon {
    SharedPreferences pref;
    int ptid = -1;
    boolean isnotified = false;
    Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        registerreceiver();
        FirebaseMessaging.getInstance().subscribeToTopic("healthbank_doc");

        b = getIntent().getExtras();
        if (b != null) {
            if (b.containsKey("isnotified")) {
                isnotified = b.getBoolean("isnotified");
                if (b.containsKey("patientid"))
                    ptid = b.getInt("patientid");
            }
        }

      /*  try {
            JSONObject obj = new JSONObject();
            obj.put("Drugtype", "Tablet");
            obj.put("Generic", "Floss");
            obj.put("DrugName", "Test");
            obj.put("Manufacture", "Shofu");
            obj.put("Weight", "1");
            obj.put("Unit", "%");
            obj.put("PracticeId", "103");
            obj.put("DepartmentId", "0");
            JSONObject dataobject = new JSONObject();
            JSONObject root = new JSONObject();
            JSONObject subroot = new JSONObject();
            subroot.put("subroot", obj);
            root.put("root", subroot);
            dataobject.put("data", root.toString());
            dataobject.put("connectionid", Constants.projectid);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        pref = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
        if (InternetUtils.getInstance(SplashActivity.this).available()) {
            if (!pref.getBoolean("ismasterupdated", false)) {
                genloading("loading");
                ArrayList<String> data = new ArrayList<>();
                data.add("Drug");
                data.add("Diagnosis");
                data.add("title");
                data.add("purpose");
                data.add("Unit");
                data.add("Doses");
                data.add("Duration");
                data.add("Instruction");
                data.add("Body Part");
                data.add("Life Style");
                data.add("Lab");
                data.add("Packages");
                data.add("Test");
                data.add("treatment procedures");
                data.add("empname");
                data.add("area");
                data.add("state");
                data.add("city");
                data.add("country");
                data.add("billing grp");
                data.add("contact grp");
                data.add("refer by");
                data.add("refer to");
                data.add("occupation");
                genloading("loading");
                ConnectionManager.getInstance(SplashActivity.this).getMaster(data);

                ConnectionManager.getInstance(SplashActivity.this).getRemaningMasterData();
            } else {
                gotonext();
            }
        } else {
            gotonext();
        }
    }
/*
    @Override
    protected void initUIandEvent() {

    }

    @Override
    protected void deInitUIandEvent() {

    }*/

    public void gotonext() {
        if (pref.getBoolean("islogin", false)) {
            GlobalValues.docid = Integer.parseInt(pref.getString("ProviderId", "0"));
            GlobalValues.branchid = Integer.parseInt(pref.getString("CustomerId", "0"));
            GlobalValues.DrName = pref.getString("DrName", "");
            if (InternetUtils.getInstance(SplashActivity.this).available()) {
                try {
                    JSONObject obj = new JSONObject();
                    obj.put("PracticeId", GlobalValues.branchid);
                    obj.put("Grouptype", "");
                    obj.put("Speciality", "");
                    obj.put("TempName", "");
                    obj.put("pagenumber", "1");
                    obj.put("pagesize", "100");
                    obj.put("connectionid", Constants.projectid);
                    ConnectionManager.getInstance(SplashActivity.this).getQuestionTemplates(obj.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                gotopatientlist();
            }
        } else {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onResponsed(int statuscode, int accesscode, String data) {
        super.onResponsed(statuscode, accesscode, data);
        if (statuscode == Constants.STATUS_OK) {
            if (accesscode == Connection.GET_MASTERDATA.ordinal()) {
                SharedPreferences.Editor edit = pref.edit();
                edit.putBoolean("ismasterupdated", true);
                edit.commit();
                gotonext();
                finish();
            } else if (accesscode == Connection.getQuestionTemplates.ordinal()) {
                try {
                    JSONArray array = new JSONArray(GlobalValues.TEMP_STR);
                    parsetemplatenames(array);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (accesscode == Connection.GetTemplateQuestionAnswerfreshdata.ordinal()) {
                gotopatientlist();
            }
        }
    }

    public void gotopatientlist() {
        Intent intent = new Intent(SplashActivity.this, PatientlistActivity.class);
       // Intent intent=new Intent(SplashActivity.this,AppointmentListActivity.class);
        if(b!=null)
        intent.putExtras(b);
        startActivity(intent);
        this.finish();
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
                DatabaseHelper.getInstance(SplashActivity.this).savetemplatenamedata(mdataset.get(i).getContentValues(), Integer.toString(mdataset.get(i).getTemplateId()));
            }
            ConnectionManager.getInstance(SplashActivity.this).GetTemplateQuestionAnswerFreshdata(mdataset);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
