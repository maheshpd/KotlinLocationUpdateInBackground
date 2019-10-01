package com.healthbank;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.healthbank.adapter.MasterAdapter;
import com.healthbank.classes.Connection;
import com.healthbank.classes.Master;
import com.healthbank.classes.Patient;
import com.healthbank.database.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AppointmentActivity extends ActivityCommon {
    LinearLayout layoutregister;
    EditText e1, e2, e3, efname, epin, emname, elname, eageyear, eagemonth, emobile, eemail, eaddress, earea, eaadharno, epanno,
            eidmark1, eidmark2, ecity, estate, ecountry, ephno, ealternatemobile, ealternateemail, ebirthdate,
            edoreg, empname, empno, eoccupation, eremark, eweddingdate;
    Button bt;
    SharedPreferences pref;
    SharedPreferences.Editor edit;
    Patient profile;
    Calendar selectedcal;
    Spinner sp1, sp2, spbloodgroup, spmaritialstatus, spsex, splanguage, spreligion;
    ArrayList<Master> titlemaster;
    ArrayList<Master> statemaster;
    ArrayList<Master> occupationmster;
    ArrayList<Master> empnamemaster;
    ArrayList<Master> areamaster;
    ArrayList<Master> citymaster;
    ArrayList<Master> countrymaster;
    ArrayList<Master> purposemaster;
    ArrayAdapter<Master> titleadapter;
    ArrayAdapter<Master> purposeadapter;
    int flag = 0;
    private int hour;
    private int minute;
    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
            // TODO Auto-generated method stub
            hour = hourOfDay;
            minute = minutes;
            updateTime(hour, minute);
        }
    };

    //dd/mm/yyyy
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointmentv1);
        setmaterialDesign();
        back();
        setTitle("Patient Registration");
        statemaster = new ArrayList<>();
        citymaster = new ArrayList<>();
        countrymaster = new ArrayList<>();
        occupationmster = new ArrayList<>();
        empnamemaster = new ArrayList<>();
        areamaster = new ArrayList<>();
        attachUI();
    }

    private void attachUI() {
        Bundle b = getIntent().getExtras();
        if (b != null) {
            if (b.containsKey("flag"))
                flag = b.getInt("flag");
        }

        titlemaster = new ArrayList<>();
        purposemaster = new ArrayList<>();

        selectedcal = Calendar.getInstance();
        pref = PreferenceManager.getDefaultSharedPreferences(AppointmentActivity.this);
        edit = pref.edit();
        layoutregister = findViewById(R.id.layout_register);
        e1 = findViewById(R.id.edittext_1);
        e2 = findViewById(R.id.edittext_2);
        e3 = findViewById(R.id.edittext_3);
        epin = findViewById(R.id.edittext_pincode);
        sp1 = findViewById(R.id.spinner_1);
        sp2 = findViewById(R.id.spinner_2);

        spbloodgroup = findViewById(R.id.spinner_bloodgroup);
        spmaritialstatus = findViewById(R.id.spinner_maritalstatus);
        spsex = findViewById(R.id.spinner_sex);
        splanguage = findViewById(R.id.spinner_language);
        spreligion = findViewById(R.id.spinner_religion);
        efname = findViewById(R.id.edittext_fname);
        emname = findViewById(R.id.edittext_mname);
        elname = findViewById(R.id.edittext_lname);
        emobile = findViewById(R.id.edittext_mobile);
        eemail = findViewById(R.id.edittext_email);
        eaddress = findViewById(R.id.edittext_address);
        earea = findViewById(R.id.edittext_area);
        eageyear = findViewById(R.id.edittext_ageyear);
        eoccupation = findViewById(R.id.edittext_iccupation);
        empname = findViewById(R.id.edittext_employeename);
        eagemonth = findViewById(R.id.edittext_agemonth);
        eaadharno = findViewById(R.id.edittext_aadharno);
        epanno = findViewById(R.id.edittext_panno);
        eidmark1 = findViewById(R.id.edittext_idmark1);
        eidmark2 = findViewById(R.id.edittext_idmark2);
        estate = findViewById(R.id.edittext_state);
        ecountry = findViewById(R.id.edittext_country);
        ecity = findViewById(R.id.edittext_city);
        JSONArray array = DatabaseHelper.getInstance(AppointmentActivity.this).getMaster("state");
        for (int i = 0; i < array.length(); i++) {
            try {
                Log.e("data ", "data " + array.getJSONObject(i).toString());
                JSONObject obj = new JSONObject(array.getJSONObject(i).getString("Name"));
                Master master = new Master();
                master.setName(obj.getString("Name"));
                master.setCategoryid(obj.getString("stateId"));
                statemaster.add(master);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        JSONArray arraycity = DatabaseHelper.getInstance(AppointmentActivity.this).getMaster("city");
        // Log.e("citydata ", "data " + arraycity.toString());
        for (int i = 0; i < arraycity.length(); i++) {
            try {
                Log.e("citydata ", "data " + arraycity.getJSONObject(i).toString());
                JSONObject obj = new JSONObject(arraycity.getJSONObject(i).getString("Name"));
                Master master = new Master();
                master.setName(obj.getString("Name"));
                master.setCategoryid(obj.getString("CityID"));
                citymaster.add(master);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        JSONArray arrayarea = DatabaseHelper.getInstance(AppointmentActivity.this).getMaster("area");
        // Log.e("citydata ", "data " + arraycity.toString());
        for (int i = 0; i < arrayarea.length(); i++) {
            try {
                Log.e("citydata ", "data " + arrayarea.getJSONObject(i).toString());
                JSONObject obj = new JSONObject(arrayarea.getJSONObject(i).getString("Name"));
                Master master = new Master();
                master.setName(obj.getString("Name"));
                master.setCategoryid(obj.getString("id"));
                areamaster.add(master);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        JSONArray arraycountry = DatabaseHelper.getInstance(AppointmentActivity.this).getMaster("country");
        for (int i = 0; i < arraycountry.length(); i++) {
            try {
                Log.e("data ", "data " + arraycountry.getJSONObject(i).toString());
                JSONObject obj = new JSONObject(arraycountry.getJSONObject(i).getString("Name"));
                Master master = new Master();
                master.setName(obj.getString("Name"));
                master.setCategoryid(obj.getString("CountryId"));
                countrymaster.add(master);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        JSONArray arrayempname = DatabaseHelper.getInstance(AppointmentActivity.this).getMaster("empname");
        for (int i = 0; i < arrayempname.length(); i++) {
            try {
                Log.e("empdatadata ", "data " + arrayempname.getJSONObject(i).toString());
                JSONObject obj = new JSONObject(arrayempname.getJSONObject(i).getString("Name"));
                Master master = new Master();
                master.setName(obj.getString("Name"));
                master.setCategoryid(obj.getString("StaffId"));
                empnamemaster.add(master);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        JSONArray arrayoccupation = DatabaseHelper.getInstance(AppointmentActivity.this).getMaster("occupation");
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Master>>() {
        }.getType();
        ArrayList<Master> mdata = gson.fromJson(arrayoccupation.toString(), type);
        if (mdata != null) {
            occupationmster.addAll(mdata);
        }
        ecountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("statemastersize ", "state master " + statemaster.size());
                onShowPopup(ecountry, countrymaster, "country");
            }
        });
        eoccupation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("statemastersize ", "state master " + statemaster.size());
                onShowPopup(eoccupation, occupationmster, "occupation");
            }
        });

        estate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("statemastersize ", "state master " + statemaster.size());
                onShowPopup(estate, statemaster, "state");
            }
        });

        ecity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("citymastersize ", "citymaster " + citymaster.size());
                onShowPopup(ecity, citymaster, "city");
            }
        });
        empname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("empnamemaster ", "empnamemaster " + empnamemaster.size());
                onShowPopup(empname, empnamemaster, "empname");
            }
        });

        earea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("empnamemaster ", "empnamemaster " + empnamemaster.size());
                onShowPopup(earea, areamaster, "area");
            }
        });

        ecountry = findViewById(R.id.edittext_country);
        ephno = findViewById(R.id.edittext_phno);
        ealternatemobile = findViewById(R.id.edittext_alternatemobile);
        ealternateemail = findViewById(R.id.edittext_alternateemail);
        ebirthdate = findViewById(R.id.edittext_dob);
        edoreg = findViewById(R.id.edittext_regdate);
        eremark = findViewById(R.id.edittext_remark);
        empno = findViewById(R.id.edittext_employeeno);
        ecity = findViewById(R.id.edittext_city);
        eweddingdate = findViewById(R.id.edittext_weddingdate);
        bt = findViewById(R.id.button_1);

        eweddingdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opendatepicker(v);
            }
        });

        edoreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opendatepicker(v);
            }
        });

        ebirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opendatepicker(v);
            }
        });

        titleadapter = new ArrayAdapter<Master>(this, android.R.layout.simple_spinner_item, titlemaster); //selected item will look like a spinner set from XML
        titleadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(titleadapter);

        purposeadapter = new ArrayAdapter<Master>(this, android.R.layout.simple_spinner_item, purposemaster); //selected item will look like a spinner set from XML
        purposeadapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        sp2.setAdapter(purposeadapter);

        ConnectionManager.getInstance(AppointmentActivity.this).gettitle();

        if (flag == 1) {
            if (GlobalValues.selectedpt != null) {
                efname.setText(GlobalValues.selectedpt.getFName());
                elname.setText(GlobalValues.selectedpt.getLastName());
                emname.setText(GlobalValues.selectedpt.getMiddleName());
                ebirthdate.setText(GlobalValues.selectedpt.getDOB());

                emobile.setText(GlobalValues.selectedpt.getMobile());
                eageyear.setText(GlobalValues.selectedpt.getAgeYear());
                eagemonth.setText(GlobalValues.selectedpt.getAgeMonth());
                eaddress.setText(GlobalValues.selectedpt.getAddress1());
                earea.setText(GlobalValues.selectedpt.getArea());
                edoreg.setText(GlobalValues.selectedpt.getDateOfjoining());

                for (int i = 0; i < spsex.getCount(); i++) {
                    if (spsex.getItemAtPosition(i).equals(GlobalValues.selectedpt.getGender())) {
                        spsex.setSelection(i);
                        break;
                    }
                }

                for (int i = 0; i < titlemaster.size(); i++) {
                    if (titlemaster.get(i).getName().equals(GlobalValues.selectedpt.getTitle())) {
                        sp1.setSelection(i);
                        break;
                    }
                }
                for (int i = 0; i < spmaritialstatus.getCount(); i++) {
                    if (spmaritialstatus.getItemAtPosition(i).equals(GlobalValues.selectedpt.getMaritalstatus())) {
                        spmaritialstatus.setSelection(i);
                        break;
                    }
                }

            }
        }

        e1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                final int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AppointmentActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                e1.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                Date d = new Date();
                                d.setYear(year);
                                d.setMonth(monthOfYear);
                                d.setDate(dayOfMonth);
                                selectedcal.setTime(d);
                                selectedcal.setTimeInMillis(0);
                                selectedcal.set(year, monthOfYear, dayOfMonth);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        e2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AppointmentActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        e2.setText(selectedHour + ":" + selectedMinute);
                        e2.setText(selectedHour + ":" + selectedMinute);
                        selectedcal.set(Calendar.HOUR_OF_DAY, selectedHour);
                        selectedcal.set(Calendar.MINUTE, selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (efname.getText().toString().length() > 0) {
                        //&& e2.getText().length() > 0 && e3.getText().toString().length() > 0
                        JSONObject obj = new JSONObject();
                        profile = new Patient();
                        profile.setTitle(efname.getText().toString());
                        profile.setFirstName(efname.getText().toString() + " " + emname.getText().toString() + " " + elname.getText().toString());
                        profile.setLastName(elname.getText().toString());
                        profile.setMiddleName(emname.getText().toString());

                        if (emobile.getText().toString().length() > 0) {
                            profile.setMobile(emobile.getText().toString());
                        } else {
                            Toast.makeText(getApplicationContext(), "Please enter mobile", Toast.LENGTH_LONG).show();
                            return;
                        }

                        profile.setAddress1(eaddress.getText().toString());
                        profile.setArea(earea.getText().toString());
                        profile.setGender(spsex.getSelectedItem().toString());
                        profile.setMobile(profile.getMobile());
                        profile.setAgeYear(eageyear.getText().toString());
                        profile.setAgeMonth(eagemonth.getText().toString());
                        //}
                        if (flag == 1) {
                            obj.put("Patientid", GlobalValues.selectedpt.getPatientid());
                        } else {
                            obj.put("Patientid", 0);
                        }


                        obj.put("CustomerId", pref.getString("CustomerId", "0"));
                        obj.put("Department", pref.getString("DepartmentId", "0"));
                        obj.put("providerid", pref.getString("ProviderId", "0"));
                        obj.put("Assistantid", GlobalValues.docid);
                        obj.put("Title", titlemaster.get(sp1.getSelectedItemPosition()).getName());
                        obj.put("FName", efname.getText().toString().trim());
                        obj.put("MiddleName", emname.getText().toString().trim());
                        obj.put("LastName", elname.getText().toString().trim());
                        obj.put("Gender", spsex.getSelectedItem().toString());
                        obj.put("Language", splanguage.getSelectedItem().toString());
                        obj.put("Address1", eaddress.getText().toString());
                        obj.put("Area", earea.getText().toString());
                        obj.put("PinCode", epin.getText().toString());
                        obj.put("Tel1", ephno.getText().toString());
                        obj.put("Mobile", emobile.getText().toString());
                        obj.put("EmailId", eemail.getText().toString());
                        obj.put("AlternateEmailId", ealternateemail.getText().toString());
                        obj.put("Occupation", eoccupation.getText().toString());
                        obj.put("EmployeeNo", empno.getText().toString());
                        obj.put("EmployeeName", empname.getText().toString());
                        obj.put("Remark", eremark.getText().toString());
                        obj.put("AlternateMobile", ealternatemobile.getText().toString());
                        obj.put("UID", eaadharno.getText().toString());
                        obj.put("Maritalstatus", spmaritialstatus.getSelectedItem().toString());
                        obj.put("BloodGroup", spbloodgroup.getSelectedItem().toString());
                        obj.put("panno", epanno.getText().toString());
                        obj.put("IdentificationMark1", eidmark1.getText().toString());
                        obj.put("IdentificationMark2", eidmark2.getText().toString());
                        obj.put("AgeYear", eageyear.getText().toString());
                        obj.put("AgeMonth", eagemonth.getText().toString());
                        JSONArray array = new JSONArray();
                        obj.put("RelatedPatientData", array);
                        obj.put("InsuranceDetails", array);
                        obj.put("DOB", ebirthdate.getText().toString());
                        obj.put("WeddingDate", eweddingdate.getText().toString().trim());
                        obj.put("DateOfjoining", edoreg.getText().toString());
                        obj.put("ReferFrom", "0");
                        obj.put("ReferTo", "0");
                        Master citymaster = (Master) ecity.getTag();
                        Master statemaster = (Master) estate.getTag();
                        Master countrymaster = (Master) ecountry.getTag();
                        obj.put("StateId", statemaster.getCategoryid());
                        obj.put("CountryId", countrymaster.getCategoryid());
                        obj.put("CityId", citymaster.getCategoryid());
                        obj.put("BillingIn", "0");
                        obj.put("Tags", "0");
                        obj.put("PatientContactGroup", "0");
                        obj.put("Height", "0");

                        JSONObject dataobject = new JSONObject();
                        JSONObject root = new JSONObject();
                        JSONObject subroot = new JSONObject();
                        subroot.put("subroot", obj);
                        root.put("root", subroot);
                        dataobject.put("data", root.toString());
                        dataobject.put("PracticeId", pref.getString("CustomerId", "0"));
                        dataobject.put("Patientid", "0");
                        dataobject.put("connectionid", Constants.projectid);
                        Log.e("dataobj ", "dataobj " + dataobject.toString());
                        ConnectionManager.getInstance(AppointmentActivity.this).Save_patient(dataobject.toString());
                       /* obj.put("gender", sp3.getSelectedItem().toString());
                        obj.put("name", efname.getText().toString());
                        obj.put("mobile", profile.getMobile());
                        obj.put("Age", eage.getText().toString());
                        obj.put("Address", profile.getAddress1());
                        obj.put("PracticeId", GlobalValues.branchid);
                        obj.put("DoctorId",GlobalValues.docid);
                        obj.put("connectionid",Constants.projectid);*/
                       /* Log.e("obj ", "obj " + obj.toString());
                        if (InternetUtils.getInstance(AppointmentActivity.this).available()) {
                            genloading("loading...");
                            ConnectionManager.getInstance(AppointmentActivity.this).SavePatient(obj.toString(), 0);
                        } else {
                            Toast.makeText(getApplicationContext(), Constants.offlinedatasavemsg, Toast.LENGTH_LONG).show();
                            profile.setSync(0);
                            profile.setId((int) DatabaseHelper.getInstance(AppointmentActivity.this).savepatient(profile.getcontentvalues()));
                            GlobalValues.selectedpt = profile;
                            if (flag == 1) {
                                Intent intent = new Intent(Constants.BROADCAST_WIZARD);
                                intent.putExtra(Constants.BROADCAST_RESPONSE_CODE, 200);
                                intent.putExtra(Constants.BROADCAST_URL_ACCESS, Connection.Edit_patient.ordinal());
                                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                            } else {
                                ((ActivityCommon) AppointmentActivity.this).gotoptdetails();
                                try {
                                    Intent intent = new Intent(Constants.BROADCAST_WIZARD);
                                    intent.putExtra(Constants.BROADCAST_RESPONSE_CODE, 200);
                                    intent.putExtra(Constants.BROADCAST_URL_ACCESS, Connection.New_Patient_Added.ordinal());
                                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            finish();
                        }*/
                    } else {
                        Toast.makeText(getApplicationContext(), "Please enter all details", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    protected void onResponsed(int statuscode, int accesscode, String data) {
        super.onResponsed(statuscode, accesscode, data);
        if (statuscode == Constants.STATUS_OK) {
            if (accesscode == Connection.GETTitle.ordinal()) {
                try {
                    JSONArray array = new JSONObject(GlobalValues.TEMP_STR).getJSONObject("root").optJSONArray("subroot");
                    if (array != null) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<Master>>() {
                        }.getType();
                        ArrayList<Master> dataset = gson.fromJson(array.toString(), type);
                        titlemaster.addAll(dataset);
                        titleadapter.notifyDataSetChanged();
                    } else {
                        JSONObject array1 = new JSONObject(GlobalValues.TEMP_STR).getJSONObject("root").optJSONObject("subroot");
                        Gson gson = new Gson();
                        Type type = new TypeToken<Master>() {
                        }.getType();
                        Master dataset = gson.fromJson(array1.toString(), type);
                        titlemaster.add(dataset);
                        titleadapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (accesscode == Connection.GETPurpose.ordinal()) {
                try {
                    JSONArray array = new JSONObject(GlobalValues.TEMP_STR).getJSONObject("root").optJSONArray("subroot");
                    if (array != null) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<Master>>() {
                        }.getType();
                        ArrayList<Master> dataset = gson.fromJson(array.toString(), type);
                        purposemaster.addAll(dataset);
                        purposeadapter.notifyDataSetChanged();
                    } else {
                        JSONObject array1 = new JSONObject(GlobalValues.TEMP_STR).getJSONObject("root").optJSONObject("subroot");
                        Gson gson = new Gson();
                        Type type = new TypeToken<Master>() {
                        }.getType();
                        Master dataset = gson.fromJson(array1.toString(), type);
                        purposemaster.add(dataset);
                        purposeadapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (accesscode == Connection.Save_patient.ordinal()) {
                try {
                  /*  JSONArray array = new JSONArray(GlobalValues.TEMP_STR);
                    if (array.length() > 0) {
                        JSONArray array1 = array.getJSONArray(0);
                        if (array1.length() > 0)
                            profile.setPatientid(array1.getJSONObject(0).getInt("Patientid"));
                    }*/
                    JSONObject obj = new JSONObject(GlobalValues.TEMP_STR).getJSONObject("root").optJSONObject("subroot");
                    profile.setPatientid(obj.getInt("Patientid"));
                    profile.setSrno(obj.getString("srno"));
                    profile.setSync(1);
                    profile.setId((int) DatabaseHelper.getInstance(AppointmentActivity.this).savepatient(profile.getcontentvalues()));
                    GlobalValues.selectedpt = profile;
                    if (flag == 1) {
                        Intent intent = new Intent(Constants.BROADCAST_WIZARD);
                        intent.putExtra(Constants.BROADCAST_RESPONSE_CODE, 200);
                        intent.putExtra(Constants.BROADCAST_URL_ACCESS, Connection.Edit_patient.ordinal());
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                        // finish();
                    } else {
                        AppointmentActivity.this.gotoptdetails();
                        try {
                            Intent intent = new Intent(Constants.BROADCAST_WIZARD);
                            intent.putExtra(Constants.BROADCAST_RESPONSE_CODE, 200);
                            intent.putExtra(Constants.BROADCAST_URL_ACCESS, Connection.New_Patient_Added.ordinal());
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void updateTime(int hours, int mins) {
        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";
        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);
        String aTime = new StringBuilder().append(hours).append(':').append(minutes).append(" ").append(timeSet).toString();
        e2.setText(aTime);
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
                try {
                    if (efname.getText().toString().length() > 0) {
                        JSONObject obj = new JSONObject();
                        profile = new Patient();
                    /*    profile.setTitle(efname.getText().toString());
                        profile.setFirstName(efname.getText().toString() + " " + emname.getText().toString() + " " + elname.getText().toString());
                        profile.setLastName(elname.getText().toString());
                        profile.setMiddleName(emname.getText().toString());*/

                        if (emobile.getText().toString().length() > 0) {
                            profile.setMobile(emobile.getText().toString());
                        } else {
                            Toast.makeText(getApplicationContext(), "Please enter mobile", Toast.LENGTH_LONG).show();
                            return false;
                        }

                  /*      profile.setAddress1(eaddress.getText().toString());
                        profile.setArea(earea.getText().toString());
                        profile.setGender(spsex.getSelectedItem().toString());
                        profile.setMobile(profile.getMobile());
                        profile.setAgeYear(eageyear.getText().toString());
                        profile.setAgeMonth(eagemonth.getText().toString());*/
                        ContentValues c = new ContentValues();
                        if (flag == 1) {
                            c.put("Patientid", GlobalValues.selectedpt.getPatientid());
                        } else {
                            c.put("Patientid", 0);
                        }
                        profile.setTitle(titlemaster.get(sp1.getSelectedItemPosition()).getName());
                        profile.setFirstName(efname.getText().toString() + " " + emname.getText().toString() + " " + elname.getText().toString());
                        profile.setFName(efname.getText().toString());
                        profile.setMiddleName(emname.getText().toString());
                        profile.setLastName(elname.getText().toString());
                        profile.setGender(spsex.getSelectedItem().toString());
                        profile.setLanguage(splanguage.getSelectedItem().toString());
                        profile.setAddress1(eaddress.getText().toString());
                        profile.setArea(earea.getText().toString());
                        profile.setPinCode(epin.getText().toString());

                        c.put("Customerid", pref.getString("CustomerId", "0"));
                        c.put("Department", pref.getString("DepartmentId", "0"));
                        c.put("providerid", pref.getString("ProviderId", "0"));
                        c.put("Assistantid", pref.getString("DepartmentId", "0"));
                        c.put("Title", titlemaster.get(sp1.getSelectedItemPosition()).getName());
                        c.put("FName", efname.getText().toString().trim());
                        c.put("MiddleName", emname.getText().toString().trim());
                        c.put("LastName", elname.getText().toString().trim());
                        c.put("Gender", spsex.getSelectedItem().toString());
                        c.put("Language", splanguage.getSelectedItem().toString());
                        c.put("Address1", eaddress.getText().toString());
                        c.put("Area", earea.getText().toString());
                        c.put("PinCode", epin.getText().toString());
                        profile.setTitle(titlemaster.get(sp1.getSelectedItemPosition()).getName());
                        profile.setMobile(emobile.getText().toString());
                        profile.setEmailId(eemail.getText().toString());
                        profile.setAlternateEmailId(ealternateemail.getText().toString());
                        profile.setBloodGroup(spbloodgroup.getSelectedItem().toString());
                        profile.setCustomerid(ephno.getText().toString());
                        profile.setCreate_date(edoreg.getText().toString());
                        profile.setUpdate_date("");
                        //  profile.setDeleted ( ephno.getText().toString());
                        //  profile.setSrno( ephno.getText().toString());
                        profile.setProviderid(Integer.parseInt(pref.getString("ProviderId", "0")));
                        profile.setAssistantid(0);
                        //  profile.setPassword ( ephno.getText().toString());
                        //  profile.setIsActive( ephno.getText().toString());
                        profile.setDepartment(ephno.getText().toString());
                        profile.setLanguage(splanguage.getSelectedItem().toString());
                        profile.setReligion(spreligion.getSelectedItem().toString());
                        profile.setBillingIn("");
                        profile.setPhone(ephno.getText().toString());
                        profile.setOccupation(eoccupation.getText().toString());
                        profile.setEmployeeNo(empno.getText().toString());
                        profile.setEmployeeName(empname.getText().toString());
                        profile.setRemark(eremark.getText().toString());
                        // profile.setTags ( etag.getText().toString());
                        profile.setReferFrom("");
                        profile.setReferTo("");
                        profile.setAlternateMobile(ealternatemobile.getText().toString());
                        // profile.setProfileimage ( ephno.getText().toString());
                        profile.setHeight("");
                        // profile.setAadharno ( eaadharno.getText().toString());
                        //   profile.setPattype ( ephno.getText().toString());
                        profile.setMaritalstatus(spmaritialstatus.getSelectedItem().toString());
                        profile.setWeddingDate(ephno.getText().toString());
                        profile.setDateOfjoining(edoreg.getText().toString());
                        profile.setPanno(epanno.getText().toString());
                        profile.setAgeYear(eageyear.getText().toString());
                        profile.setAgeMonth(eagemonth.getText().toString());
                        // profile.setAge ( ephno.getText().toString());
                        //  profile.setSync( ephno.getText().toString());
                        //  profile.setServerPatientid ( ephno.getText().toString());
                        profile.setTel1(ephno.getText().toString());
                        profile.setUID(eaadharno.getText().toString());
                        profile.setPatientContactGroup("");
                        JSONArray array = new JSONArray();
/*
                        c.put("Tel1", ephno.getText().toString());
                        c.put("Mobile", emobile.getText().toString());
                        c.put("EmailId", eemail.getText().toString());
                        c.put("AlternateEmailId", ealternateemail.getText().toString());
                        c.put("Occupation", eoccupation.getText().toString());
                        c.put("EmployeeNo", empno.getText().toString());
                        c.put("EmployeeName", empname.getText().toString());
                        c.put("Remark", eremark.getText().toString());
                        c.put("AlternateMobile", ealternatemobile.getText().toString());
                        c.put("UID", eaadharno.getText().toString());
                        c.put("Maritalstatus", spmaritialstatus.getSelectedItem().toString());
                        c.put("BloodGroup", spbloodgroup.getSelectedItem().toString());
                        c.put("panno", epanno.getText().toString());
                        c.put("IdentificationMark1", eidmark1.getText().toString());
                        c.put("IdentificationMark2", eidmark2.getText().toString());
                        c.put("AgeYear", eageyear.getText().toString());
                        c.put("AgeMonth", eagemonth.getText().toString());
                        JSONArray array = new JSONArray();
                        c.put("RelatedPatientData", array.toString());
                        c.put("InsuranceDetails", array.toString());
                        c.put("DOB", ebirthdate.getText().toString());
                        c.put("WeddingDate", eweddingdate.getText().toString());
                        c.put("DateOfjoining", edoreg.getText().toString());
                        c.put("ReferFrom", "0");
                        c.put("ReferTo", "0");
                        c.put("StateId", "0");
                        c.put("CountryId", "0");
                        c.put("CityId", "0");
                        c.put("BillingIn", "0");
                        c.put("Tags", "0");
                        c.put("PatientContactGroup", "0");
                        c.put("Height", "0");*/

                        if (InternetUtils.getInstance(AppointmentActivity.this).available()) {
                            if (flag == 1) {
                                obj.put("Patientid", GlobalValues.selectedpt.getPatientid());
                            } else {
                                obj.put("Patientid", 0);
                            }
                            obj.put("CustomerId", pref.getString("CustomerId", "0"));
                            obj.put("Department", pref.getString("DepartmentId", "0"));
                            obj.put("providerid", pref.getString("ProviderId", "0"));
                            obj.put("Assistantid", pref.getString("DepartmentId", "0"));
                            obj.put("Title", titlemaster.get(sp1.getSelectedItemPosition()).getName());
                            obj.put("FName", efname.getText().toString().trim());
                            obj.put("MiddleName", emname.getText().toString().trim());
                            obj.put("LastName", elname.getText().toString().trim());
                            obj.put("Gender", spsex.getSelectedItem().toString());
                            obj.put("Language", splanguage.getSelectedItem().toString());
                            obj.put("Address1", eaddress.getText().toString());
                            obj.put("Area", earea.getText().toString());
                            obj.put("PinCode", epin.getText().toString());
                            obj.put("Tel1", ephno.getText().toString());
                            obj.put("Mobile", emobile.getText().toString());
                            obj.put("EmailId", eemail.getText().toString());
                            obj.put("AlternateEmailId", ealternateemail.getText().toString());
                            obj.put("Occupation", eoccupation.getText().toString());
                            obj.put("EmployeeNo", empno.getText().toString());
                            obj.put("EmployeeName", empname.getText().toString());
                            obj.put("Remark", eremark.getText().toString());
                            obj.put("AlternateMobile", ealternatemobile.getText().toString());
                            obj.put("UID", eaadharno.getText().toString());
                            obj.put("Maritalstatus", spmaritialstatus.getSelectedItem().toString());
                            obj.put("BloodGroup", spbloodgroup.getSelectedItem().toString());
                            obj.put("panno", epanno.getText().toString());
                            obj.put("IdentificationMark1", eidmark1.getText().toString());
                            obj.put("IdentificationMark2", eidmark2.getText().toString());
                            obj.put("AgeYear", eageyear.getText().toString());
                            obj.put("AgeMonth", eagemonth.getText().toString());
                            obj.put("RelatedPatientData", array);
                            obj.put("InsuranceDetails", array);
                            obj.put("DOB", ebirthdate.getText().toString());
                            obj.put("WeddingDate", eweddingdate.getText().toString());
                            obj.put("DateOfjoining", edoreg.getText().toString());
                            obj.put("ReferFrom", "0");
                            obj.put("ReferTo", "0");
                            obj.put("StateId", "0");
                            obj.put("CountryId", "0");
                            obj.put("CityId", "0");
                            obj.put("BillingIn", "0");
                            obj.put("Tags", "0");
                            obj.put("PatientContactGroup", "0");
                            obj.put("Height", "0");

                            JSONObject dataobject = new JSONObject();
                            JSONObject root = new JSONObject();
                            JSONObject subroot = new JSONObject();
                            subroot.put("subroot", obj);
                            root.put("root", subroot);
                            dataobject.put("data", root.toString());
                            dataobject.put("PracticeId", pref.getString("CustomerId", "0"));
                            dataobject.put("Patientid", "0");
                            if (flag == 1) {
                                // c.put("Patientid", GlobalValues.selectedpt.getPatientid());
                                dataobject.put("Patientid", GlobalValues.selectedpt.getPatientid());
                            } else {
                                dataobject.put("Patientid", "0");
                            }
                            dataobject.put("connectionid", Constants.projectid);
                            Log.e("dataobj ", "dataobj " + dataobject.toString());
                            ConnectionManager.getInstance(AppointmentActivity.this).Save_patient(dataobject.toString());
                        } else {
                            Toast.makeText(getApplicationContext(), Constants.offlinedatasavemsg, Toast.LENGTH_LONG).show();
                            profile.setSync(0);
                            profile.setId((int) DatabaseHelper.getInstance(AppointmentActivity.this).savepatient(profile.getcontentvalues()));
                            GlobalValues.selectedpt = profile;
                            if (flag == 1) {
                                Intent intent = new Intent(Constants.BROADCAST_WIZARD);
                                intent.putExtra(Constants.BROADCAST_RESPONSE_CODE, 200);
                                intent.putExtra(Constants.BROADCAST_URL_ACCESS, Connection.Edit_patient.ordinal());
                                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                            } else {
                                AppointmentActivity.this.gotoptdetails();
                                try {
                                    Intent intent = new Intent(Constants.BROADCAST_WIZARD);
                                    intent.putExtra(Constants.BROADCAST_RESPONSE_CODE, 200);
                                    intent.putExtra(Constants.BROADCAST_URL_ACCESS, Connection.New_Patient_Added.ordinal());
                                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            finish();
                        }

                       /* obj.put("gender", sp3.getSelectedItem().toString());
                        obj.put("name", efname.getText().toString());
                        obj.put("mobile", profile.getMobile());
                        obj.put("Age", eage.getText().toString());
                        obj.put("Address", profile.getAddress1());
                        obj.put("PracticeId", GlobalValues.branchid);
                        obj.put("DoctorId",GlobalValues.docid);
                        obj.put("connectionid",Constants.projectid);*/
                       /* Log.e("obj ", "obj " + obj.toString());
                        if (InternetUtils.getInstance(AppointmentActivity.this).available()) {
                            genloading("loading...");
                            ConnectionManager.getInstance(AppointmentActivity.this).SavePatient(obj.toString(), 0);
                        } else {
                            Toast.makeText(getApplicationContext(), Constants.offlinedatasavemsg, Toast.LENGTH_LONG).show();
                            profile.setSync(0);
                            profile.setId((int) DatabaseHelper.getInstance(AppointmentActivity.this).savepatient(profile.getcontentvalues()));
                            GlobalValues.selectedpt = profile;
                            if (flag == 1) {
                                Intent intent = new Intent(Constants.BROADCAST_WIZARD);
                                intent.putExtra(Constants.BROADCAST_RESPONSE_CODE, 200);
                                intent.putExtra(Constants.BROADCAST_URL_ACCESS, Connection.Edit_patient.ordinal());
                                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                            } else {
                                ((ActivityCommon) AppointmentActivity.this).gotoptdetails();
                                try {
                                    Intent intent = new Intent(Constants.BROADCAST_WIZARD);
                                    intent.putExtra(Constants.BROADCAST_RESPONSE_CODE, 200);
                                    intent.putExtra(Constants.BROADCAST_URL_ACCESS, Connection.New_Patient_Added.ordinal());
                                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            finish();
                        }*/
                    } else {
                        Toast.makeText(getApplicationContext(), "Please enter all details", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void opendatepicker(final View v) {
        final Calendar c = Calendar.getInstance();
        final int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(AppointmentActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                ((EditText) v).setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                Calendar c1 = Calendar.getInstance();
                c1.set(Calendar.YEAR, year);
                c1.set(Calendar.MONTH, month);
                c1.set(Calendar.DATE, dayOfMonth);

                long miliSecondForDate1 = c.getTimeInMillis();
                long miliSecondForDate2 = c1.getTimeInMillis();
                // Calculate the difference in millisecond between two dates
                long diffInMilis = miliSecondForDate1 - miliSecondForDate2;
                /*
                 * Now we have difference between two date in form of millsecond we can
                 * easily convert it Minute / Hour / Days by dividing the difference
                 * with appropriate value. 1 Second : 1000 milisecond 1 Hour : 60 * 1000
                 * millisecond 1 Day : 24 * 60 * 1000 milisecond
                 */
               /* long diffInSecond = diffInMilis / 1000;
                long diffInMinute = diffInMilis / (60 * 1000);
                long diffInHour = diffInMilis / (60 * 60 * 1000);*/
                long diffInDays = diffInMilis / (24 * 60 * 60 * 1000);
                int age = c.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
                int month1 = 0;
                if (c.get(Calendar.DAY_OF_MONTH) < c1.get(Calendar.DAY_OF_MONTH)) {
                    age--;

                }
                month1 = c1.get(Calendar.MONTH) - c.get(Calendar.MONTH);
                if (month1 < 0)
                    month1 = month;


                DateCalculator dateCaculator = DateCalculator.calculateAge(c1, c);
                //String age = "Age: " + dateCaculator.getYear() + " Years " + dateCaculator.getMonth() + " Months " + dateCaculator.getDay()+ " Days";

                eageyear.setText("" + dateCaculator.getYear());
                eagemonth.setText("" + dateCaculator.getMonth());
                Log.e("time ", "" + c1.getTime() + " " + c.getTime() + " " + age);
            }
        }, mYear, mMonth, mDay);
        dialog.show();
    }

    public void onShowPopup(final View anchorView, final ArrayList<Master> mdataset, final String masterkey) {
        LayoutInflater layoutInflater = (LayoutInflater) AppointmentActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflatedView = layoutInflater.inflate(R.layout.list_item_popup, null, false);
        final EditText edit = inflatedView.findViewById(R.id.edittext_1);
        ImageView img = inflatedView.findViewById(R.id.imageview_1);
        img.setVisibility(View.GONE);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(AppointmentActivity.this);
        final PopupWindow popWindow = new PopupWindow(AppointmentActivity.this);
        RecyclerView mRecyclerview = inflatedView.findViewById(R.id.recyclerview_1);
        mRecyclerview.setLayoutManager(mLinearLayoutManager);
        final MasterAdapter adapter = new MasterAdapter(AppointmentActivity.this, mdataset, new MasterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Master item) {
                ((EditText) anchorView).setText(item.getName());
                popWindow.dismiss();
            }
        });
        mRecyclerview.setAdapter(adapter);

        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                adapter.filterdata(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        popWindow.setContentView(inflatedView);
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        popWindow.setOutsideTouchable(true);
        popWindow.setWidth(anchorView.getWidth());
        popWindow.setHeight(700);


       /* popWindow.setWindowLayoutMode(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                400dp);*/
    /*    popWindow.setHeight(400);
        popWindow.setWidth(1);*/
        popWindow.setFocusable(true);
        popWindow.showAsDropDown(anchorView);
        popWindow.setFocusable(true);

       /* img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (masterkey.equalsIgnoreCase(Constants.Drug)) {
                    showPopupDrug(anchorView, mdataset,masterkey);
                    popWindow.dismiss();
                } else if (masterkey.equalsIgnoreCase(Constants.Doses)) {
                    onShowPopupdoses(anchorView, mdataset,masterkey);
                    popWindow.dismiss();
                } else if (masterkey.equalsIgnoreCase(Constants.Duration)) {
                    onShowPopupfrequency(anchorView, mdataset,masterkey);
                    popWindow.dismiss();
                } else {
                    if (edit.getText().toString().trim().length() > 0) {
                        long index = DatabaseHelper.getInstance(getActivity()).savemaster(edit.getText().toString(), "0", 0, masterkey);
                        Master master = new Master(edit.getText().toString());
                        master.setTestId("0");
                        master.setLabId("0");
                        mdataset.add(master);
                        templateadapter.notifyDataSetChanged();
                        ((EditText) anchorView).setText(edit.getText().toString().trim());
                        popWindow.dismiss();
                    } else {
                        Toast.makeText(getActivity(), "please enter details", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });*/
    }

}
