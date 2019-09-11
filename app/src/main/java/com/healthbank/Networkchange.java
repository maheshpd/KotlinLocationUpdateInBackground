package com.healthbank;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONArray;

public class Networkchange extends BroadcastReceiver {
    Context mcontext;
    JSONArray array;
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            mcontext = context;
            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                NetworkInfo networkInfo = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
                if (networkInfo != null && networkInfo.getDetailedState() == NetworkInfo.DetailedState.CONNECTED) {
                    ConnectionManager.getInstance(context).Syncofflinedata();
/*
                    JSONArray patientarray=DatabaseHelper.getInstance(mcontext).getofflinepatient();
                    for (int i=0; i<patientarray.length();i++)
                    {
                        patientarray.getJSONObject(i).put("name",patientarray.getJSONObject(i).getString(DatabaseHelper.FirstName));
                        ConnectionManager.getInstance(context).SavePatient(patientarray.getJSONObject(i).toString(),1);
                    }

                    JSONArray visitarray=DatabaseHelper.getInstance(mcontext).getofflinevivisits();
                    Log.e("visitdata ","visitdata "+visitarray.toString());
                    for (int i=0; i<visitarray.length();i++)
                    {
                        ConnectionManager.getInstance(context).AddVisit(visitarray.getJSONObject(i).toString(),1);
                    }

                    JSONArray array=DatabaseHelper.getInstance(context).getnotsync();
                    for (int i=0;i<array.length();i++)
                    {
                        try {
                            JSONObject arrayobj=array.getJSONObject(0);
                            JSONObject obj = new JSONObject(array.getJSONObject(0).getString("jsondata"));
                            if (obj.has("QuestionData")) {
                                JSONArray array1 = obj.getJSONArray("QuestionData");
                                JSONObject mainobj = new JSONObject();
                                mainobj.put("deptid",obj.getString("deptid"));
                                mainobj.put("locid", obj.getString("locid"));
                                mainobj.put("VisitNo", arrayobj.getString(DatabaseHelper.VISITID));
                                mainobj.put("VisitDate", obj.getString("VisitDate"));
                                mainobj.put("DoctorId", obj.getString("DoctorId"));
                                mainobj.put("searchid", arrayobj.getString(DatabaseHelper.Patientid));
                                mainobj.put("Group", obj.getString("Group"));
                                mainobj.put("TemplateId", obj.getString("TemplateId"));
                                mainobj.put("TempName", obj.getString("TempName"));
                                mainobj.put("AddmitionNo", obj.getString("AddmitionNo"));
                                mainobj.put("data", array1.toString());
                                Log.e("refreshdata","refreshdata "+mainobj.toString());
                                ConnectionManager.getInstance(context).saveQuestionTemplates(mainobj.toString(),1);
                            }
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }*/

                } else if (networkInfo != null && networkInfo.getDetailedState() == NetworkInfo.DetailedState.DISCONNECTED) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
