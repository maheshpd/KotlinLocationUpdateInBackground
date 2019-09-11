package com.healthbank;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.healthbank.classes.EyeData;
import com.healthbank.classes.EyespecialityData;
import com.healthbank.database.DatabaseHelper;

import org.json.JSONObject;
public class UpdateeyecheckupdataActivity extends ActivityCommon {
    long id = 0;
    EditText edt1, edt2, edt3, edt4, edt5, edt6, edt7, edt8, edt9, edt10, edt11, edt12, edt13, edt14, edt15, edt16, edt17, edt18, edt19, edt20, edt21, edt22, edt23, edt24, edt25, edt26, edt27, edt28, edt29, edt30, edt31, edt32, edt33, edt34, edt35, edt36, edt37, edt38, edt39, edt40, edt41, edt42, edt43, edt44, edt45, edt46, edt47, edt48, edt49, edt50, edt51, edt52, edt53, edt54, edt55, edt56, edt57, edt58, edtedit;
    EyeData eyedata;
    Button bt1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_updateeyecheckupdata);
        attachUI();
    }

    private void attachUI() {
        try {
            id = getIntent().getExtras().getLong("id");
            String data = DatabaseHelper.getInstance(UpdateeyecheckupdataActivity.this).geteyespecialitydatabyid(id);
            JSONObject obj1 = new JSONObject(data);
            JSONObject obj = obj1.getJSONObject("root").getJSONObject("subroot");
            obj.put("id", obj1.getLong("id"));
            eyedata = new EyeData(obj);
            bt1 = findViewById(R.id.button_1);
            bt1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        JSONObject mainobjobj = new JSONObject();
                        JSONObject rootobj = new JSONObject();
                        JSONObject subrootobj = new JSONObject();

                        JSONObject odobj = new JSONObject();
                        JSONObject UCVAobj = new JSONObject();
                        UCVAobj.put("D", ((EditText) (findViewById(R.id.editText_1))).getText().toString().trim());
                        UCVAobj.put("N", ((EditText) (findViewById(R.id.editText_2))).getText().toString().trim());
                        UCVAobj.put("PH", ((EditText) (findViewById(R.id.editText_3))).getText().toString().trim());
                        UCVAobj.put("VN", ((EditText) (findViewById(R.id.editText_4))).getText().toString().trim());
                        UCVAobj.put("Add", ((EditText) (findViewById(R.id.editText_5))).getText().toString().trim());
                        UCVAobj.put("NV", ((EditText) (findViewById(R.id.editText_6))).getText().toString().trim());
                        odobj.put("UCVA", UCVAobj);

                        JSONObject PGPobj = new JSONObject();
                        PGPobj.put("Sph", ((EditText) (findViewById(R.id.editText_13))).getText().toString().trim());
                        PGPobj.put("Cyl", ((EditText) (findViewById(R.id.editText_14))).getText().toString().trim());
                        PGPobj.put("Axis", ((EditText) (findViewById(R.id.editText_15))).getText().toString().trim());
                        PGPobj.put("VN", ((EditText) (findViewById(R.id.editText_16))).getText().toString().trim());
                        PGPobj.put("Add", ((EditText) (findViewById(R.id.editText_17))).getText().toString().trim());
                        PGPobj.put("NV", ((EditText) (findViewById(R.id.editText_18))).getText().toString().trim());
                        odobj.put("PGP", PGPobj);

                        JSONObject MRobj = new JSONObject();
                        MRobj.put("Sph", ((EditText) (findViewById(R.id.editText_25))).getText().toString().trim());
                        MRobj.put("Cyl", ((EditText) (findViewById(R.id.editText_26))).getText().toString().trim());
                        MRobj.put("Axis", ((EditText) (findViewById(R.id.editText_27))).getText().toString().trim());
                        MRobj.put("VN", ((EditText) (findViewById(R.id.editText_28))).getText().toString().trim());
                        MRobj.put("Add", ((EditText) (findViewById(R.id.editText_29))).getText().toString().trim());
                        MRobj.put("NV", ((EditText) (findViewById(R.id.editText_30))).getText().toString().trim());
                        odobj.put("MR", MRobj);

                        JSONObject CRobj = new JSONObject();
                        CRobj.put("Sph", ((EditText) (findViewById(R.id.editText_37))).getText().toString().trim());
                        CRobj.put("Cyl", ((EditText) (findViewById(R.id.editText_38))).getText().toString().trim());
                        CRobj.put("Axis", ((EditText) (findViewById(R.id.editText_39))).getText().toString().trim());
                        CRobj.put("VN", ((EditText) (findViewById(R.id.editText_40))).getText().toString().trim());
                        CRobj.put("Add", ((EditText) (findViewById(R.id.editText_41))).getText().toString().trim());
                        CRobj.put("NV", ((EditText) (findViewById(R.id.editText_42))).getText().toString().trim());

                        odobj.put("CR", CRobj);
                        odobj.put("PUPIL", ((EditText) (findViewById(R.id.editText_49))).getText().toString().trim());
                        odobj.put("IOP", ((EditText) (findViewById(R.id.editText_51))).getText().toString().trim());
                        odobj.put("EXTEXM", ((EditText) (findViewById(R.id.editText_53))).getText().toString().trim());
                        odobj.put("ANTSEG", ((EditText) (findViewById(R.id.editText_55))).getText().toString().trim());
                        odobj.put("FUNDUS", ((EditText) (findViewById(R.id.editText_57))).getText().toString().trim());
                        subrootobj.put("OD", odobj);

                        JSONObject OSobj = new JSONObject();
                        JSONObject OSUCVAobj = new JSONObject();
                        OSUCVAobj.put("D", ((EditText) (findViewById(R.id.editText_7))).getText().toString().trim());
                        OSUCVAobj.put("N", ((EditText) (findViewById(R.id.editText_8))).getText().toString().trim());
                        OSUCVAobj.put("PH", ((EditText) (findViewById(R.id.editText_9))).getText().toString().trim());
                        OSUCVAobj.put("VN", ((EditText) (findViewById(R.id.editText_10))).getText().toString().trim());
                        OSUCVAobj.put("Add", ((EditText) (findViewById(R.id.editText_11))).getText().toString().trim());
                        OSUCVAobj.put("NV", ((EditText) (findViewById(R.id.editText_12))).getText().toString().trim());
                        OSobj.put("UCVA", OSUCVAobj);

                        JSONObject OSPGPobj = new JSONObject();
                        OSPGPobj.put("Sph", ((EditText) (findViewById(R.id.editText_19))).getText().toString().trim());
                        OSPGPobj.put("Cyl", ((EditText) (findViewById(R.id.editText_20))).getText().toString().trim());
                        OSPGPobj.put("Axis", ((EditText) (findViewById(R.id.editText_21))).getText().toString().trim());
                        OSPGPobj.put("VN", ((EditText) (findViewById(R.id.editText_22))).getText().toString().trim());
                        OSPGPobj.put("Add", ((EditText) (findViewById(R.id.editText_23))).getText().toString().trim());
                        OSPGPobj.put("NV", ((EditText) (findViewById(R.id.editText_24))).getText().toString().trim());
                        OSobj.put("PGP", OSPGPobj);

                        JSONObject OSMRobj = new JSONObject();
                        OSMRobj.put("Sph", ((EditText) (findViewById(R.id.editText_31))).getText().toString().trim());
                        OSMRobj.put("Cyl", ((EditText) (findViewById(R.id.editText_32))).getText().toString().trim());
                        OSMRobj.put("Axis", ((EditText) (findViewById(R.id.editText_33))).getText().toString().trim());
                        OSMRobj.put("VN", ((EditText) (findViewById(R.id.editText_34))).getText().toString().trim());
                        OSMRobj.put("Add", ((EditText) (findViewById(R.id.editText_35))).getText().toString().trim());
                        OSMRobj.put("NV", ((EditText) (findViewById(R.id.editText_36))).getText().toString().trim());
                        OSobj.put("MR", OSMRobj);

                        JSONObject OSCRobj = new JSONObject();
                        OSCRobj.put("Sph", ((EditText) (findViewById(R.id.editText_43))).getText().toString().trim());
                        OSCRobj.put("Cyl", ((EditText) (findViewById(R.id.editText_44))).getText().toString().trim());
                        OSCRobj.put("Axis", ((EditText) (findViewById(R.id.editText_45))).getText().toString().trim());
                        OSCRobj.put("VN", ((EditText) (findViewById(R.id.editText_46))).getText().toString().trim());
                        OSCRobj.put("Add", ((EditText) (findViewById(R.id.editText_47))).getText().toString().trim());
                        OSCRobj.put("NV", ((EditText) (findViewById(R.id.editText_48))).getText().toString().trim());

                        OSobj.put("CR", OSCRobj);
                        OSobj.put("PUPIL", ((EditText) (findViewById(R.id.editText_50))).getText().toString().trim());
                        OSobj.put("IOP", ((EditText) (findViewById(R.id.editText_52))).getText().toString().trim());
                        OSobj.put("EXTEXM", ((EditText) (findViewById(R.id.editText_54))).getText().toString().trim());
                        OSobj.put("ANTSEG", ((EditText) (findViewById(R.id.editText_56))).getText().toString().trim());
                        OSobj.put("FUNDUS", ((EditText) (findViewById(R.id.editText_58))).getText().toString().trim());
                        subrootobj.put("OS", OSobj);
                        rootobj.put("subroot", subrootobj);
                        mainobjobj.put("root", rootobj);
                        // Log.e("root ", "root " + mainobjobj.toString());

                        ContentValues c = new ContentValues();
                        c.put(DatabaseHelper.JSONDATA, mainobjobj.toString());
                        c.put(DatabaseHelper.DoctorId, GlobalValues.docid);
                        c.put(DatabaseHelper.Patientid, GlobalValues.selectedpt.getPatientid());
                        c.put(DatabaseHelper.SAVEDTIME, DateUtils.getSqliteTime());
                        DatabaseHelper.getInstance(UpdateeyecheckupdataActivity.this).updateeyespecialitydata(c, id);
                        Intent intent = new Intent("eyedataupdated");
                        LocalBroadcastManager.getInstance(UpdateeyecheckupdataActivity.this).sendBroadcast(intent);
                        finish();
                        Toast.makeText(getApplicationContext(), "data updated", Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            edt1 = findViewById(R.id.editText_1);
            edt2 = findViewById(R.id.editText_2);
            edt3 = findViewById(R.id.editText_3);
            edt4 = findViewById(R.id.editText_4);
            edt5 = findViewById(R.id.editText_5);
            edt6 = findViewById(R.id.editText_6);
            edt7 = findViewById(R.id.editText_7);
            edt8 = findViewById(R.id.editText_8);
            edt9 = findViewById(R.id.editText_9);
            edt10 = findViewById(R.id.editText_10);
            edt11 = findViewById(R.id.editText_11);
            edt12 = findViewById(R.id.editText_12);
            edt13 = findViewById(R.id.editText_13);
            edt14 = findViewById(R.id.editText_14);
            edt15 = findViewById(R.id.editText_15);
            edt16 = findViewById(R.id.editText_16);
            edt17 = findViewById(R.id.editText_17);
            edt18 = findViewById(R.id.editText_18);
            edt19 = findViewById(R.id.editText_19);
            edt20 = findViewById(R.id.editText_20);

            edt21 = findViewById(R.id.editText_21);
            edt22 = findViewById(R.id.editText_22);
            edt23 = findViewById(R.id.editText_23);
            edt24 = findViewById(R.id.editText_24);
            edt25 = findViewById(R.id.editText_25);
            edt26 = findViewById(R.id.editText_26);
            edt27 = findViewById(R.id.editText_27);
            edt28 = findViewById(R.id.editText_28);
            edt29 = findViewById(R.id.editText_29);
            edt30 = findViewById(R.id.editText_30);

            edt31 = findViewById(R.id.editText_31);
            edt32 = findViewById(R.id.editText_32);
            edt33 = findViewById(R.id.editText_33);
            edt34 = findViewById(R.id.editText_34);
            edt35 = findViewById(R.id.editText_35);
            edt36 = findViewById(R.id.editText_36);
            edt37 = findViewById(R.id.editText_37);
            edt38 = findViewById(R.id.editText_38);
            edt39 = findViewById(R.id.editText_39);
            edt40 = findViewById(R.id.editText_40);

            edt41 = findViewById(R.id.editText_41);
            edt42 = findViewById(R.id.editText_42);
            edt43 = findViewById(R.id.editText_43);
            edt44 = findViewById(R.id.editText_44);
            edt45 = findViewById(R.id.editText_45);
            edt46 = findViewById(R.id.editText_46);
            edt47 = findViewById(R.id.editText_47);
            edt48 = findViewById(R.id.editText_48);
            edt49 = findViewById(R.id.editText_49);
            edt50 = findViewById(R.id.editText_50);

            edt51 = findViewById(R.id.editText_51);
            edt52 = findViewById(R.id.editText_52);
            edt53 = findViewById(R.id.editText_53);
            edt54 = findViewById(R.id.editText_54);
            edt55 = findViewById(R.id.editText_55);
            edt56 = findViewById(R.id.editText_56);
            edt57 = findViewById(R.id.editText_57);
            edt58 = findViewById(R.id.editText_58);

            EyespecialityData od = eyedata.getOD();
            EyespecialityData os = eyedata.getOS();

            edt1.setText(od.getUcvaData().getD());
            edt2.setText(od.getUcvaData().getN());
            edt3.setText(od.getUcvaData().getPH());
            edt4.setText(od.getUcvaData().getVN());
            edt5.setText(od.getUcvaData().getAdd());
            edt6.setText(od.getUcvaData().getNV());

            edt7.setText(os.getUcvaData().getD());
            edt8.setText(os.getUcvaData().getN());
            edt9.setText(os.getUcvaData().getPH());
            edt10.setText(os.getUcvaData().getVN());
            edt11.setText(os.getUcvaData().getAdd());
            edt12.setText(os.getUcvaData().getNV());

            edt13.setText(od.getPGP().getSph());
            edt14.setText(od.getPGP().getCyl());
            edt15.setText(od.getPGP().getAxis());
            edt16.setText(od.getPGP().getVN());
            edt17.setText(od.getPGP().getAdd());
            edt18.setText(od.getPGP().getNV());

            edt19.setText(os.getPGP().getSph());
            edt20.setText(os.getPGP().getCyl());
            edt21.setText(os.getPGP().getAxis());
            edt22.setText(os.getPGP().getVN());
            edt23.setText(os.getPGP().getAdd());
            edt24.setText(os.getPGP().getNV());

            edt25.setText(od.getMR().getSph());
            edt26.setText(od.getMR().getCyl());
            edt27.setText(od.getMR().getAxis());
            edt28.setText(od.getMR().getVN());
            edt29.setText(od.getMR().getAdd());
            edt30.setText(od.getMR().getNV());

            edt31.setText(os.getMR().getSph());
            edt32.setText(os.getMR().getCyl());
            edt33.setText(os.getMR().getAxis());
            edt34.setText(os.getMR().getVN());
            edt35.setText(os.getMR().getAdd());
            edt36.setText(os.getMR().getNV());

            edt37.setText(od.getCR().getSph());
            edt38.setText(od.getCR().getCyl());
            edt39.setText(od.getCR().getAxis());
            edt40.setText(od.getCR().getVN());
            edt41.setText(od.getCR().getAdd());
            edt42.setText(od.getCR().getNV());

            edt43.setText(os.getCR().getSph());
            edt44.setText(os.getCR().getCyl());
            edt45.setText(os.getCR().getAxis());
            edt46.setText(os.getCR().getVN());
            edt47.setText(os.getCR().getAdd());
            edt48.setText(os.getCR().getNV());

            edt49.setText(od.getPUPIL());
            edt51.setText(od.getIOP());
            edt53.setText(od.getEXTEXM());
            edt55.setText(od.getANTSEG());
            edt57.setText(od.getFUNDUS());

            edt50.setText(os.getPUPIL());
            edt52.setText(os.getIOP());
            edt53.setText(os.getEXTEXM());
            edt56.setText(os.getANTSEG());
            edt58.setText(os.getFUNDUS());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
