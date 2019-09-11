package com.healthbank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.healthbank.adapter.PatientDetailsAdapter;
import com.healthbank.classes.Connection;
import com.healthbank.classes.HomeData;
import com.healthbank.database.DatabaseHelper;
import com.healthbank.groupvideocall.openvcall.AGApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.healthbank.classes.Connection.GetMenuByspeciality;

public class PatientDetailsActivity extends ActivityCommon implements View.OnClickListener {
    RecyclerView recyclerView;
    ArrayList<HomeData> mdataset;
    PatientDetailsAdapter adapter;
    GridLayoutManager mlayoutmanager;
    TextView txt1, txt2, txt3, txt4;
    ImageView img1, img2;
    Bundle bundle;
    boolean isvideocall = false;
    Button b1;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);
        setmaterialDesign();
        back();
        setTitle("Patient Details");
        attachUI();
    }

    @Override
    public void onClick(View v) {

    }

    public int calculateNoOfColumns() {
        DisplayMetrics displayMetrics = PatientDetailsActivity.this.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 180);
        return noOfColumns;
    }

    private void attachUI() {
        try {
            pref = PreferenceManager.getDefaultSharedPreferences(PatientDetailsActivity.this);
            bundle = getIntent().getExtras();
            if (bundle != null)
                if (bundle.containsKey("isvideocall"))
                    isvideocall = bundle.getBoolean("isvideocall");
            txt1 = findViewById(R.id.textview_1);
            txt2 = findViewById(R.id.textview_2);
            txt3 = findViewById(R.id.textview_3);
            txt4 = findViewById(R.id.textview_4);
            b1 = findViewById(R.id.button_1);
            if (isvideocall) {
                b1.setVisibility(View.VISIBLE);
                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotogroupvideocall(bundle.getString("channel"));
                    }
                });
            } else {
                b1.setVisibility(View.GONE);
            }

            txt4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String drname = pref.getString("DrName", "");
                    String channel = pref.getString("ProviderId", "");
                    AGApplication.mVideoSettings.mChannelName = channel;
                    String encryption = "AES-128-XTS";
                    AGApplication.mVideoSettings.mEncryptionKey = encryption;
                    Log.e("channel ", channel + " " + encryption + " " + getResources().getStringArray(R.array.encryption_mode_values)[AGApplication.mVideoSettings.mEncryptionModeIndex]);
                    Log.e("data ", AGApplication.mVideoSettings.mChannelName + " " + AGApplication.mVideoSettings.mEncryptionKey + " " + AGApplication.mVideoSettings.mEncryptionModeIndex);
                    String msg = "You have req of telemedicine from  " + drname + " for patient " + GlobalValues.selectedpt.getFirstName();
                    send_message(msg, channel, encryption);
                    gotogroupvideocall(channel);
                   /* Intent i = new Intent(PatientDetailsActivity.this, ChatActivity.class);
                    i.putExtra(ConstantApp.ACTION_KEY_CHANNEL_NAME, channel);
                    i.putExtra(ConstantApp.ACTION_KEY_ENCRYPTION_KEY, encryption);
                    i.putExtra(ConstantApp.ACTION_KEY_ENCRYPTION_MODE, getResources().getStringArray(R.array.encryption_mode_values)[AGApplication.mVideoSettings.mEncryptionModeIndex]);
                    startActivity(i);*/
               /* Intent intent1 = new Intent(PatientlistActivity.this, LiveDoctorsActivity.class);
                startActivity(intent1);*/
                }
            });

            txt1.setTypeface(Fonter.getTypefacesemibold(PatientDetailsActivity.this));
            txt2.setTypeface(Fonter.getTypefaceregular(PatientDetailsActivity.this));
            txt3.setTypeface(Fonter.getTypefaceregular(PatientDetailsActivity.this));
            txt1.setText(GlobalValues.selectedpt.getFirstName() + "|" + GlobalValues.selectedpt.getSrno());
            txt2.setText(GlobalValues.selectedpt.getMobile());
            txt3.setText(GlobalValues.selectedpt.getAge() + " | " + GlobalValues.selectedpt.getGender());
            mdataset = new ArrayList<>();

            //  mdataset.add(new HomeData("Appointment/Medical Visit", R.drawable.icon_medicinebox, R.color.lightgreen));
            //   mdataset.add(new HomeData("Medical History", R.drawable.icon_medicinebox, R.color.ArduinoHeader));
            //mdataset.add(new HomeData("Vitals", R.drawable.icon_vital, R.color.BlueLight));
            // mdataset.add(new HomeData("Rx", R.drawable.icon_medicinebox, R.color.red));

            // mdataset.add(new HomeData("HPI", R.drawable.icon_medicinebox, R.color.yellow));
            //   mdataset.add(new HomeData("Examination", R.drawable.icon_medicinebox, R.color.ArduinoHeader));
            // mdataset.add(new HomeData("Advice", R.drawable.icon_medicinebox, R.color.lightgreen));
            //  mdataset.add(new HomeData("Accounts", R.drawable.icon_pay, R.color.BlueLight));
            //  mdataset.add(new HomeData("Notes", R.drawable.icon_notes, R.color.red));
            mdataset.add(new HomeData("Appointment/Medical Visit", R.drawable.icon_medicinebox1, R.color.lightgreen));
            mdataset.add(new HomeData("Vitals", R.drawable.icon_medicinebox2, R.color.BlueLight));
            mdataset.add(new HomeData("Lab Details", R.drawable.ic_labdetails, R.color.yellow));
            mdataset.add(new HomeData("Radiology", R.drawable.icon_notes, R.color.BlueLight));
            mdataset.add(new HomeData("Rx", R.drawable.icon_medicinebox3, R.color.red));
            mdataset.add(new HomeData("Upload Files", R.drawable.icon_medicinebox3, R.color.red));
            //mdataset.add(new HomeData("Medical history", R.drawable.icon_medicinebox, R.color.red));
            //mdataset.add(new HomeData("History", R.drawable.icon_history, R.color.gridcolor_2));



            /*mdataset.add(new HomeData("HPI", R.drawable.icon_medicinebox4, R.color.yellow));
            mdataset.add(new HomeData("Examination", R.drawable.icon_medicinebox5, R.color.ArduinoHeader));
            mdataset.add(new HomeData("Advice", R.drawable.icon_medicinebox6, R.color.gridcolor_2));
            mdataset.add(new HomeData("Examinationv1", R.drawable.icon_medicinebox7, R.color.gridcolor_2));
            mdataset.add(new HomeData("BloodReport", R.drawable.icon_medicinebox8, R.color.gridcolor_2));
            mdataset.add(new HomeData("Radiology", R.drawable.icon_medicinebox10, R.color.gridcolor_2));
            mdataset.add(new HomeData("UploadReport", R.drawable.icon_medicinebox11, R.color.gridcolor_2));
            mdataset.add(new HomeData("Notes", R.drawable.icon_medicinebox12, R.color.gridcolor_2));*/
            //  mdataset.add(new HomeData("Accounts", R.drawable.icon_pay, R.color.BlueLight));
            recyclerView = findViewById(R.id.recyclerview_1);
            mlayoutmanager = new GridLayoutManager(PatientDetailsActivity.this, 2);
            //   mlayoutmanager = new GridLayoutManager(PatientDetailsActivity.this, calculateNoOfColumns());
            recyclerView.setLayoutManager(mlayoutmanager);
            adapter = new PatientDetailsAdapter(PatientDetailsActivity.this, mdataset);
            recyclerView.setAdapter(adapter);
            img1 = findViewById(R.id.imageview_1);
            img2 = findViewById(R.id.imageview_2);

            img2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PatientDetailsActivity.this, AppointmentActivity.class);
                    intent.putExtra("flag", 1);
                    startActivity(intent);
                }
            });

            String text = URLS.gethistoryurl(Integer.toString(GlobalValues.selectedpt.getPatientid()));
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            try {
                BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);
              /*  BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                img1.setImageBitmap(bitmap);*/
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (InternetUtils.getInstance(PatientDetailsActivity.this).available())
                ConnectionManager.getInstance(PatientDetailsActivity.this).getmenubyspeciality(pref.getString("emr_type", "General Physician"));
            else {
                JSONArray array = DatabaseHelper.getInstance(PatientDetailsActivity.this).getdata("menubyspeciality");
                Log.e("data ", "data menubyspeciality" + array.toString());
                if (array.length() > 0) {
                    JSONObject obj = array.getJSONObject(array.length() - 1);
                    Log.e("data ", "data1 menubyspeciality" + obj.toString());
                    if (obj != null) {
                        String data = obj.getString(DatabaseHelper.JSONDATA);
                        Log.e("data ", "data2 menubyspeciality" + obj.toString());
                        parsemenubyspeciality(data);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResponsed(int statuscode, int accesscode, String data) {
        super.onResponsed(statuscode, accesscode, data);
        if (accesscode == Connection.Edit_patient.ordinal()) {
            txt1.setText(GlobalValues.selectedpt.getFirstName() + "|" + GlobalValues.selectedpt.getSrno());
            txt2.setText(GlobalValues.selectedpt.getMobile());
            txt3.setText(GlobalValues.selectedpt.getAge() + " | " + GlobalValues.selectedpt.getGender());
        } else if (accesscode == GetMenuByspeciality.ordinal()) {
            parsemenubyspeciality(GlobalValues.TEMP_STR);
        }
        if (accesscode == Connection.GET_PRESCRIPTION.ordinal()) {
            Intent intentrx = new Intent(this, RxActivityv1.class);
            intentrx.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
            startActivity(intentrx);
        }

    }

    public void parsemenubyspeciality(String data) {
        try {
            JSONArray array = new JSONArray(data);
            if (array.length() > 0) {
                JSONObject obj = array.optJSONObject(0);
                if (obj != null) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<HomeData>() {
                    }.getType();
                    HomeData homeData = gson.fromJson(obj.toString(), type);
                    if (homeData != null) {
                        mdataset.add(homeData);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    JSONArray arrdata = array.optJSONArray(0);
                    if (arrdata != null) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<HomeData>>() {
                        }.getType();
                        ArrayList<HomeData> homeData = gson.fromJson(arrdata.toString(), type);
                        if (homeData != null) {
                            mdataset.addAll(homeData);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.e("error ", "error " + e.toString());
        }
    }

    private void send_message(String msg, String channel, String encryption) {
        JSONObject obj = new JSONObject();
        JSONObject subobj = new JSONObject();
        try {
            obj.put("condition", "'healthbank_doc' in topics");
            subobj.put("request", msg);
            subobj.put("patientid", GlobalValues.selectedpt.getPatientid());//0 q,1 idareply,2 drreply,3 feedback
            subobj.put("channel", channel);
            subobj.put("encryption", encryption);
            JSONObject obj1 = new JSONObject();
            obj1.put("msgdata", subobj.toString());
            obj.put("data", obj1);
            Log.e("obj ", "obj " + obj.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        genloading("Sending");
        ConnectionManager.getInstance(PatientDetailsActivity.this).sendPushMessage(obj.toString());
    }
}
