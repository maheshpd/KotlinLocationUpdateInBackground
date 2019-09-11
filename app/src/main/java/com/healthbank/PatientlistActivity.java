package com.healthbank;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.healthbank.adapter.PatientAdapter;
import com.healthbank.classes.Connection;
import com.healthbank.classes.Patient;
import com.healthbank.database.DatabaseHelper;
import com.healthbank.groupvideocall.openvcall.AGApplication;
import com.healthbank.groupvideocall.openvcall.ui.BaseActivity;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class PatientlistActivity extends BaseActivity {
    RecyclerView mrecyclerview;
    PatientAdapter adapter;
    LinearLayoutManager mLayoutmanager;
    ArrayList<Patient> mdataset;
    EditText e1;
    int ptid = 0;
    boolean isnotified = false;
    Bundle bundle;
    String channel = "";
    String deptid,userid,locid;

    public static boolean hasPermissions(Context context, String... permissions) {
        try {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
                for (String permission : permissions) {
                    if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patientlist);
        Log.e("callled ", "called ");
        AGApplication.the().getmAgoraAPI().channelJoin(Constants.channel);

        attachUI();
        //  checkpermission();
    }

    @Override
    protected void initUIandEvent() {

    }

    @Override
    protected void deInitUIandEvent() {

    }

    public void checkpermission() {
        try {
            int PERMISSION_ALL = 1;
            String[] PERMISSIONS = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA};
            if (!hasPermissions(this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte[] readFile(String file) {
        ByteArrayOutputStream bos = null;
        try {
            File f = new File(file);
            FileInputStream fis = new FileInputStream(f);
            byte[] buffer = new byte[1024];
            bos = new ByteArrayOutputStream();
            for (int len; (len = fis.read(buffer)) != -1; ) {
                bos.write(buffer, 0, len);
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e2) {
            System.err.println(e2.getMessage());
        }
        return bos != null ? bos.toByteArray() : null;
    }

    public void attachUI() {
        try {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(PatientlistActivity.this);
             deptid = preferences.getString("DepartmentId", "");
             userid =  preferences.getString("ProviderId", "");
             locid =  preferences.getString("CustomerId", "");

            bundle = getIntent().getExtras();
            if (bundle != null) {
                if (bundle.containsKey("isnotified")) {
                    isnotified = bundle.getBoolean("isnotified");
                    if (bundle.containsKey("patientid"))
                        ptid = bundle.getInt("patientid");

                    if (bundle.containsKey("channel"))
                        channel = bundle.getString("channel");
                }
            }
     /*   long fileSizeInBytes = DatabaseHelper.getInstance(this).getsize();
        long fileSizeInKB = fileSizeInBytes / 1024;
        long fileSizeInMB = fileSizeInKB / 1024;
        Log.e("dbsize ", "fileSizeInBytes " + fileSizeInBytes + fileSizeInKB + " fileSizeInKB " + fileSizeInKB + " fileSizeInMB " + fileSizeInMB);
        try {
            File f = new File("/storage/emulated/0/HMIS1/im2.png");
            //  Log.e("dbsize","filesize "+((f.length()/ (1024 * 1024) )));
            FileInputStream fis = new FileInputStream(f);
            byte[] bytes = readFile("/storage/emulated/0/HMIS1/im2.png");// IOUtils.toByteArray(fis);
            String base64Value = Base64.encodeToString(bytes, Base64.DEFAULT);
          *//*  DataInputStream in = new DataInputStream(fis);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            String myData="";
            while ((strLine = br.readLine()) != null) {
                myData = myData + strLine;
            }
            in.close();*//*
            ContentValues c = new ContentValues();
            c.put(DatabaseHelper.BINARY_DATA, bytes);
            Log.e("dbsize", "sizebefore" + " " + DatabaseHelper.getInstance(PatientlistActivity.this).getsize());
            //   long index = DatabaseHelper.getInstance(PatientlistActivity.this).savefiledata(c);
            //Log.e("dbsize", "index" + " " + index);
            long fileSizeInBytes1 = DatabaseHelper.getInstance(this).getsize();
            long fileSizeInKB1 = fileSizeInBytes1 / 1024;
            long fileSizeInMB1 = fileSizeInKB1 / 1024;
            Log.e("dbsize", "sizeafter" + " " + fileSizeInBytes1 + " fileSizeInKB1 " + fileSizeInKB1 + " fileSizeInMB1 " + fileSizeInMB1);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
            setmaterialDesign();
            e1 = findViewById(R.id.edittext_1);

            mdataset = new ArrayList<>();

            JSONArray array = DatabaseHelper.getInstance(PatientlistActivity.this).getptdata();
            if (array.length() > 0) {
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<Patient>>() {
                }.getType();
                mdataset = gson.fromJson(array.toString(), type);
            }



            if (mdataset.size() == 0)
                if (InternetUtils.getInstance(PatientlistActivity.this).available()) {

                    ConnectionManager.getInstance(this).searchpatient("", "1", "25000", userid, deptid, locid);
                } else {
                    Toast.makeText(getApplicationContext(), "No Network", Toast.LENGTH_LONG).show();
                }

            mrecyclerview = findViewById(R.id.recyclerview_1);
            mLayoutmanager = new LinearLayoutManager(this);
            mrecyclerview.setLayoutManager(mLayoutmanager);
            mrecyclerview.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(this, R.drawable.recycler_divider)));
            adapter = new PatientAdapter(PatientlistActivity.this, mdataset);
            mrecyclerview.setAdapter(adapter);
            if (mdataset.size() > 0) {
                if (isnotified) {
                    isnotified = false;
                    gotopatientdetailsbynotification(ptid);
                }
            }

            e1.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    mdataset.clear();
                    ArrayList<Patient> ptdata = DatabaseHelper.getInstance(PatientlistActivity.this).getfilteredaptdata(editable.toString());
                    if (ptdata != null) {
                        mdataset.addAll(ptdata);
                    }
             /*   if (array.length() > 0) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<ArrayList<Patient>>() {
                    }.getType();
                    ArrayList<Patient> ptdata = gson.fromJson(array.toString(), type);

               //     Log.e("mdataset","mdataset "+mdataset.size());
                }*/
                    adapter.notifyDataSetChanged();
             /*   if (InternetUtils.getInstance(PatientlistActivity.this).available()) {
                    ConnectionManager.getInstance(PatientlistActivity.this).searchpatient(editable.toString(), "1", "1000");
                } else {
                    Toast.makeText(getApplicationContext(), "No Network", Toast.LENGTH_LONG).show();
                }*/
                }
            });
            if (bundle != null)
                if (bundle.containsKey("isdirectvideocall")) {
                    Intent intent = new Intent(this, IncomingcallActivity.class);
                    startActivity(intent);
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onResponsed(int statuscode, int accesscode, String data) {
        super.onResponsed(statuscode, accesscode, data);
        if (statuscode == Constants.STATUS_OK) {
            if (accesscode == Connection.SearchPatient.ordinal()) {
                try {
                    mdataset.clear();
                    JSONArray array = new JSONArray(GlobalValues.TEMP_STR);
                    Log.e("received ","received "+GlobalValues.TEMP_STR);
                    if (array.length() > 0) {
                        JSONArray array1 = array.getJSONArray(0);
                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<Patient>>() {
                        }.getType();
                        mdataset = gson.fromJson(array1.toString(), type);
                        adapter.notifyDataSetChanged();
                        adapter = new PatientAdapter(PatientlistActivity.this, mdataset);
                        mrecyclerview.setAdapter(adapter);

                        for (int i = 0; i < mdataset.size(); i++) {
                            mdataset.get(i).setPatientid(mdataset.get(i).getPatientid());
                            mdataset.get(i).setId((int) DatabaseHelper.getInstance(PatientlistActivity.this).savepatient(mdataset.get(i).getcontentvalues()));
                        }
                    }

                    if (mdataset.size() > 0) {
                        if (isnotified) {
                            isnotified = false;
                            gotopatientdetailsbynotification(ptid);
                        }
                    }
                    Log.e("mdataset size ","mdataset size "+mdataset.size());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (accesscode == Connection.New_Patient_Added.ordinal()) {
                try {
                    mdataset.add(0, GlobalValues.selectedpt);
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (accesscode == Connection.Offllinedatasaved.ordinal()) {
                try {
                    mdataset.clear();
                    JSONArray array = DatabaseHelper.getInstance(PatientlistActivity.this).getptdata();
                    if (array.length() > 0) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<Patient>>() {
                        }.getType();
                        ArrayList<Patient> ptdata = gson.fromJson(array.toString(), type);
                        mdataset.addAll(ptdata);
                    }
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.scanqrcode, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            switch (item.getItemId()) {
                case R.id.menu_action_appointmentview:
                    Intent intentapt = new Intent(PatientlistActivity.this, AppointmentListActivity.class);
                    startActivity(intentapt);
                    break;
                case R.id.menu_action_calanderview:
                    Intent intentcal = new Intent(PatientlistActivity.this, BarcodescanActivity.class);
                    startActivity(intentcal);
                    break;
                case R.id.menu_action_scan:
                    Intent intent = new Intent(PatientlistActivity.this, BarcodescanActivity.class);
                    startActivity(intent);
                    break;
                case R.id.menu_action_addpt:
                    Intent intentpt = new Intent(PatientlistActivity.this, AppointmentActivity.class);
                    startActivity(intentpt);
                    break;
                case R.id.menu_action_setting:
                    Intent intentsetting = new Intent(PatientlistActivity.this, OfflineDataActivity.class);
                    startActivity(intentsetting);
                case R.id.menu_action_refresh:
                    if (InternetUtils.getInstance(PatientlistActivity.this).available()) {
                        genloading("loading");
                        ConnectionManager.getInstance(this).searchpatient("", "1", "25000", userid, deptid, locid);
                    } else {
                        Toast.makeText(getApplicationContext(), "No Network", Toast.LENGTH_LONG).show();
                    }
                    break;
                case R.id.menu_action_waitingroom:
                    gotogroupvideocall(channel);
               /* Intent intent1 = new Intent(PatientlistActivity.this, LiveDoctorsActivity.class);
                startActivity(intent1);*/
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onOptionsItemSelected(item);
    }


    public void gotopatientdetailsbynotification(int ptid) {
        try {
            int position = -1;
            for (int i = 0; i < mdataset.size(); i++) {
                if (ptid == mdataset.get(i).getPatientid()) {
                    position = i;
                    break;
                }
            }
            Log.e("position ", "position " + position);
            if (position == -1) {
                Toast.makeText(getApplicationContext(), "Patient data not available", Toast.LENGTH_LONG).show();
                gotogroupvideocall(channel);
            } else {
                GlobalValues.selectedpt = mdataset.get(position);
                Log.e("dataset ", "position " + GlobalValues.selectedpt.getFirstName() + " " + GlobalValues.selectedpt.getPatientid());
                gotoptdetails(bundle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
