package com.healthbank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.healthbank.classes.Connection;
import com.healthbank.classes.Doctor;
import com.healthbank.classes.Template;
import com.healthbank.database.DatabaseHelper;
import com.healthbank.groupvideocall.openvcall.AGApplication;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class LoginActivity extends ActivityCommon {
    EditText e1, e2;
    Button bt1;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        registerreceiver();
        attchUI();
    }

    private void attchUI() {
        pref = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        e1 = findViewById(R.id.edittext_1);
        e2 = findViewById(R.id.edittext_2);
        bt1 = findViewById(R.id.button_1);
        bt1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (InternetUtils.getInstance(LoginActivity.this).available()) {
                        if (e1.getText().length() > 0 && e2.getText().length() > 0) {
                            JSONObject dataobject = new JSONObject();
                            JSONObject obj = new JSONObject();
                            obj.put("EmailId", e1.getText().toString().trim());
                            obj.put("Password", e2.getText().toString().trim());

                            JSONObject root = new JSONObject();
                            JSONObject subroot = new JSONObject();
                            subroot.put("subroot", obj);
                            root.put("root", subroot);
                            dataobject.put("data", root.toString());
                            dataobject.put("connectionid", Constants.projectid);
                            genloading("loading");
                            ConnectionManager.getInstance(LoginActivity.this).login(dataobject.toString());
                        } else {
                            Toast.makeText(getApplicationContext(), "Please enter valid details", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "No Network", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void gotonext() {
        /*if (pref.getBoolean("islogin", false)) {
            GlobalValues.docid = Integer.parseInt(pref.getString("ProviderId", "0"));
            GlobalValues.branchid = Integer.parseInt(pref.getString("CustomerId", "0"));
            Intent intent = new Intent(LoginActivity.this, PatientlistActivity.class);
            startActivity(intent);
            this.finish();
        }*/
        if (pref.getBoolean("islogin", false)) {
            GlobalValues.docid = Integer.parseInt(pref.getString("ProviderId", "0"));
            GlobalValues.branchid = Integer.parseInt(pref.getString("CustomerId", "0"));
            GlobalValues.DrName = pref.getString("DrName", "");

         /*   GlobalValues.docid = Integer.parseInt(pref.getString("ProviderId", "0"));
            GlobalValues.branchid = Integer.parseInt(pref.getString("CustomerId", "0"));
            GlobalValues.DrName = pref.getString("DrName", "");*/
            AGApplication.the().getmAgoraAPI().channelJoin(Constants.channel);
           /* if (InternetUtils.getInstance(SplashActivity.this).available()) {
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
            }*/

            if (InternetUtils.getInstance(LoginActivity.this).available()) {
                try {
                    JSONObject obj = new JSONObject();
                    obj.put("PracticeId", GlobalValues.branchid);
                    obj.put("Grouptype", "");
                    obj.put("Speciality", "");
                    obj.put("TempName", "");
                    obj.put("pagenumber", "1");
                    obj.put("pagesize", "100");
                    obj.put("connectionid", Constants.projectid);
                    ConnectionManager.getInstance(LoginActivity.this).getQuestionTemplates(obj.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Intent intent = new Intent(LoginActivity.this, PatientlistActivity.class);
                startActivity(intent);
                this.finish();
            }
        } else {
          /*  Intent intent = new Intent(LoginActivity.this, PatientlistActivity.class);
            startActivity(intent);
            finish()*/
        }
    }

    @Override
    protected void onResponsed(int statuscode, int accesscode, String data) {
        super.onResponsed(statuscode, accesscode, data);
        Log.e("received ", "received");
        if (statuscode == Constants.STATUS_OK) {
            if (accesscode == Connection.LOGIN.ordinal()) {
                try {
                    JSONObject object = new JSONObject(GlobalValues.TEMP_STR).getJSONObject("root").optJSONObject("subroot");
                    if (object != null) {
                        Doctor d = new Doctor(object);
                        GlobalValues.doctordata.add(d);
                        Intent intent = new Intent(this, AssignBranchActivity.class);
                        startActivity(intent);
                        this.finish();
                    } else {
                        JSONArray array = new JSONObject(GlobalValues.TEMP_STR).getJSONObject("root").optJSONArray("subroot");
                        if (array != null) {
                            for (int i = 0; i < array.length(); i++) {
                                Doctor d = new Doctor(array.getJSONObject(i));
                                GlobalValues.doctordata.add(d);
                            }
                            Intent intent = new Intent(this, AssignBranchActivity.class);
                            startActivity(intent);
                            this.finish();
                        }else {
                            Toast.makeText(getApplicationContext(),"Please check login details",Toast.LENGTH_LONG).show();
                        }
                    }

                  /*  if (object == null) {
                        JSONArray  array = new JSONObject(GlobalValues.TEMP_STR).getJSONObject("root").optJSONArray("subroot");
                        if(array!=null) {
                            if (array.length() > 0) {
                                object = array.optJSONObject(0);
                                Log.e("obj ", "obj " + object.toString());
                                Doctor d = new Doctor(object);
                                if (d.getLoc().size() > 0) {
                                    // Log.e("detsize ", "dept size " + d.getLoc().get(0).getMaindept().size());
                                }
                                GlobalValues.d = d;
                                if (d.getSoftware_type().equalsIgnoreCase("Hospital EMR")) {
                                    Intent intent = new Intent(this, AssignBranchActivity.class);
                                    startActivity(intent);
                                    this.finish();
                                } else {
                                    if (d.getLoc().size() >= 1) {
                                        loc Loc = d.getLoc().get(0);
                                        Department dept = new Department();
                                        if (d.getLoc().get(0).getMaindept().size() > 1)
                                            dept = d.getLoc().get(0).getMaindept().get(0);
                                        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                                        SharedPreferences.Editor edit = pref.edit();
                                        edit.putString("CustomerId", Loc.getCustomerId());
                                        edit.putString("emr_type",d.getEmr_type());
                                        edit.putString("EmrSpeciality",d.getEmrSpeciality());
                                        edit.putString("ProviderId", d.getProviderId());
                                        edit.putString("BranchName", Loc.getName());
                                        edit.putString("parentBranchId", Loc.getParentBranchId());
                                        edit.putString("DepartmentId", dept.getId());
                                        edit.putString("DepartmentName", dept.getName());
                                        edit.putString("DepartmentParentId", dept.getParentId());
                                        edit.putString("DrName", d.getName());
                                        edit.putBoolean("islogin", true);
                                        edit.commit();
                                        GlobalValues.docid = Integer.parseInt(d.getProviderId());
                                        GlobalValues.branchid = Integer.parseInt(Loc.getCustomerId());
                                        GlobalValues.DrName = d.getName();
                                        GlobalValues.selectedoctor=d;
                                        AGApplication.the().getmAgoraAPI().channelJoin(Constants.channel);
                                        if (InternetUtils.getInstance(LoginActivity.this).available()) {
                                            try {
                                                JSONObject obj = new JSONObject();
                                                obj.put("PracticeId", GlobalValues.branchid);
                                                obj.put("Grouptype", "");
                                                obj.put("Speciality", "");
                                                obj.put("TempName", "");
                                                obj.put("pagenumber", "1");
                                                obj.put("pagesize", "100");
                                                obj.put("connectionid", Constants.projectid);
                                                ConnectionManager.getInstance(LoginActivity.this).getQuestionTemplates(obj.toString());
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            Intent intent = new Intent(LoginActivity.this, PatientlistActivity.class);
                                            startActivity(intent);
                                            this.finish();
                                        }

                                    }
                                }
                            }
                        }else {
                            Toast.makeText(getApplicationContext(), "please check login details", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Log.e("obj ","obj "+object.toString());
                        Doctor d = new Doctor(object);
                        if (d.getLoc().size() > 0) {
                            // Log.e("detsize ", "dept size " + d.getLoc().get(0).getMaindept().size());
                        }
                        GlobalValues.d = d;
                        if (d.getSoftware_type().equalsIgnoreCase("Hospital EMR")) {
                            Intent intent = new Intent(this, AssignBranchActivity.class);
                            startActivity(intent);
                            this.finish();
                        } else {
                            if (d.getLoc().size() >= 1) {
                                loc Loc = d.getLoc().get(0);
                                Department dept = new Department();
                                if (d.getLoc().get(0).getMaindept().size() > 1)
                                    dept = d.getLoc().get(0).getMaindept().get(0);

                                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                                SharedPreferences.Editor edit = pref.edit();
                                edit.putString("CustomerId", Loc.getCustomerId());
                                edit.putString("ProviderId", d.getProviderId());
                                edit.putString("BranchName", Loc.getName());
                                edit.putString("parentBranchId", Loc.getParentBranchId());
                                edit.putString("DepartmentId", dept.getId());
                                edit.putString("DepartmentName", dept.getName());
                                edit.putString("DepartmentParentId", dept.getParentId());
                                edit.putString("DrName", d.getName());
                                edit.putBoolean("islogin", true);
                                edit.commit();
                                GlobalValues.docid = Integer.parseInt(d.getProviderId());
                                GlobalValues.branchid = Integer.parseInt(Loc.getCustomerId());
                                GlobalValues.DrName = d.getName();
                                AGApplication.the().getmAgoraAPI().channelJoin(Constants.channel);
                                if (InternetUtils.getInstance(LoginActivity.this).available()) {
                                    try {
                                        JSONObject obj = new JSONObject();
                                        obj.put("PracticeId", GlobalValues.branchid);
                                        obj.put("Grouptype", "");
                                        obj.put("Speciality", "");
                                        obj.put("TempName", "");
                                        obj.put("pagenumber", "1");
                                        obj.put("pagesize", "100");
                                        obj.put("connectionid", Constants.projectid);
                                        ConnectionManager.getInstance(LoginActivity.this).getQuestionTemplates(obj.toString());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    Intent intent = new Intent(LoginActivity.this, PatientlistActivity.class);
                                    startActivity(intent);
                                    this.finish();
                                }

                            }
                        }
                    }*/
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (accesscode == Connection.GET_MASTERDATA.ordinal()) {
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
                Log.e("fresh data received ", "fresh data received ");
                Intent intent = new Intent(LoginActivity.this, PatientlistActivity.class);
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
                DatabaseHelper.getInstance(LoginActivity.this).savetemplatenamedata(mdataset.get(i).getContentValues(), Integer.toString(mdataset.get(i).getTemplateId()));
            }
            ConnectionManager.getInstance(LoginActivity.this).GetTemplateQuestionAnswerFreshdata(mdataset);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

