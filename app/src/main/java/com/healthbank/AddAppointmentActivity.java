package com.healthbank;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.healthbank.classes.City;
import com.healthbank.classes.Connection;
import com.healthbank.classes.Country;
import com.healthbank.classes.Patient;
import com.healthbank.classes.State;
import com.healthbank.database.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Calendar;

public class AddAppointmentActivity extends ActivityCommon {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    EditText e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12, e14, e15, e16, e17, e18, e19, e20, e21, e22, e23, e24, e25, e27, e28;
    CircularImageView img1;
    Calendar cal;
    AutoCompleteTextView autotxt1, autotxt2, autotxt3;
    public AdapterView.OnItemClickListener iclk = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> arg0, View v, int arg2, long arg3) {
            // TODO Auto-generated method stub
            try {
                TextView textview = (TextView) v;
                String text = textview.getText().toString();
                switch (getCurrentId().getId()) {
                    case R.id.autocompletetextview_1:
                        attachState(text);
                        autotxt2.setText("");
                        autotxt3.setText("");
                        break;
                    case R.id.autocompletetextview_2:
                        attachCity(text);
                        autotxt3.setText("");
                        break;
                    case R.id.autocompletetextview_3:
                        autotxt3.setTag(City.getid(text, getApplicationContext()));
                        break;
                }
            } catch (Exception e) {
                // reporterror(tag,e.toString());
            }
        }
    };
    String countrytxt = "";
    Spinner spinner1, spinner2, spinner3, spinner4, spinner5, spinner6, spinner7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);
        setmaterialDesign();
        back();
        attachUI();
    }

    public void attachUI() {
        cal = Calendar.getInstance();
        e1 = findViewById(R.id.edittext_1);
        e2 = findViewById(R.id.edittext_2);
        e3 = findViewById(R.id.edittext_3);
        e4 = findViewById(R.id.edittext_4);
        e5 = findViewById(R.id.edittext_5);
        e6 = findViewById(R.id.edittext_6);
        e7 = findViewById(R.id.edittext_7);
        e8 = findViewById(R.id.edittext_8);
        e9 = findViewById(R.id.edittext_9);
        e10 = findViewById(R.id.edittext_10);
        e11 = findViewById(R.id.edittext_11);
        e12 = findViewById(R.id.edittext_12);
        e14 = findViewById(R.id.edittext_14);
        e15 = findViewById(R.id.edittext_15);
        e16 = findViewById(R.id.edittext_16);
        e17 = findViewById(R.id.edittext_17);
        e18 = findViewById(R.id.edittext_18);
        e19 = findViewById(R.id.edittext_19);
        e20 = findViewById(R.id.edittext_20);
        e21 = findViewById(R.id.edittext_21);
        e22 = findViewById(R.id.edittext_22);
        e23 = findViewById(R.id.edittext_23);
        e24 = findViewById(R.id.edittext_24);
        e25 = findViewById(R.id.edittext_25);
        e27 = findViewById(R.id.edittext_27);
        e28 = findViewById(R.id.edittext_28);

        autotxt1 = findViewById(R.id.autocompletetextview_1);
        autotxt2 = findViewById(R.id.autocompletetextview_2);
        autotxt3 = findViewById(R.id.autocompletetextview_3);

        autotxt1.setTag("0");
        autotxt2.setTag("0");
        autotxt3.setTag("0");
        bindStates();

        spinner1 = findViewById(R.id.spinner_1);
        spinner2 = findViewById(R.id.spinner_2);
        spinner3 = findViewById(R.id.spinner_3);
        spinner4 = findViewById(R.id.spinner_4);
        spinner5 = findViewById(R.id.spinner_5);
        spinner6 = findViewById(R.id.spinner_6);
        spinner7 = findViewById(R.id.spinner_7);
        img1 = findViewById(R.id.imageview_1);

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        e9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int mYear = cal.get(Calendar.YEAR);
                int mMonth = cal.get(Calendar.MONTH);
                int mDay = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddAppointmentActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                e10.setText("" + (mYear - year));
                                e9.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        e21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                final int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddAppointmentActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                e21.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        e27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                final int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddAppointmentActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                e27.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        e5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddAppointmentActivity.this, AllAppointment.class);
                intent.putExtra("ptname", e1.getText().toString());
                intent.putExtra("mobileno", e2.getText().toString());
                intent.putExtra("patientid", 0);
                intent.putExtra("isedit", false);
                startActivity(intent);
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onResponsed(int statuscode, int accesscode, String data) {
        super.onResponsed(statuscode, accesscode, data);
        if (statuscode == Constants.STATUS_OK) {
            if (accesscode == Connection.TimeUpdated.ordinal()) {
                try {
                    JSONObject obj = new JSONObject(data);
                    String date1 = obj.getString("date");
                    String time = obj.getString("time");
                    String dateString = date1 + " " + time;
                    e5.setText(dateString);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void bindStates() {
        try {
            autotxt1.setAdapter(Country.getAdapter(this));
            autotxt1.setOnItemClickListener(iclk);
            autotxt1.setText(countrytxt);
            attachState(countrytxt);
            autotxt2.setOnItemClickListener(iclk);
            autotxt3.setOnItemClickListener(iclk);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View getCurrentId() {
        return this.getCurrentFocus();
    }

    private void attachState(String text) {
        try {
            String countryid = Country.getid("India", this.getApplicationContext());
            //  Globals.user.setCountryid(countryid);
            autotxt1.setTag(countryid);
            autotxt2.setAdapter(State.getAdapter(countryid, this));
        } catch (Exception e) {
            //reporterror(tag,e.toString());
        }
    }

    private void attachCity(String text) {
        try {
            String stateid = State.getid(text, this.getApplicationContext());
            autotxt2.setTag(stateid);
            autotxt3.setAdapter(City.getAdapter(stateid, this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            img1.setImageBitmap(imageBitmap);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_save:
                String firstname = "";
                if (e2.getText().toString().length() > 0)
                    firstname = firstname + e2.getText().toString().trim() + " ";
                if (e3.getText().toString().length() > 0)
                    firstname = firstname + e3.getText().toString().trim() + " ";
                if (e4.getText().toString().length() > 0)
                    firstname = firstname + e4.getText().toString().trim() + " ";
                if (firstname.length() <= 0) {
                    Toast.makeText(getApplicationContext(), "Please enter name", Toast.LENGTH_LONG).show();
                } else {
                    long ptid = DatabaseHelper.getInstance(AddAppointmentActivity.this).isexist(e2.getText().toString().trim() + " " + e3.getText().toString().trim() + " " + e4.getText().toString().trim());
                    if (ptid == -1) {
                        try {
                            ContentValues c = new ContentValues();
                            c.put(DatabaseHelper.FirstName, firstname.trim());
                            c.put(DatabaseHelper.FName, e2.getText().toString());
                            c.put(DatabaseHelper.MiddleName, e3.getText().toString());
                            c.put(DatabaseHelper.LastName, e4.getText().toString());
                            c.put(DatabaseHelper.Address, e6.getText().toString());
                            c.put(DatabaseHelper.Area, e7.getText().toString());
                            c.put(DatabaseHelper.DOB, e9.getText().toString());
                            c.put(DatabaseHelper.PinCode, e3.getText().toString());
                            c.put(DatabaseHelper.Mobile, e11.getText().toString());
                            c.put(DatabaseHelper.AlternateMobile, e12.getText().toString());
                            c.put(DatabaseHelper.EmailId, e14.getText().toString());
                            c.put(DatabaseHelper.AlternateEmailId, e15.getText().toString());
                            c.put(DatabaseHelper.BloodGroup, spinner4.getSelectedItem().toString());
                            c.put(DatabaseHelper.Language, e16.getText().toString());
                            c.put(DatabaseHelper.Religion, e17.getText().toString());
                            c.put(DatabaseHelper.Occupation, e18.getText().toString());
                            c.put(DatabaseHelper.EmployeeName, e19.getText().toString());
                            c.put(DatabaseHelper.EmployeeNo, e20.getText().toString());
                            c.put(DatabaseHelper.DateOfjoining, e21.getText().toString());
                            c.put(DatabaseHelper.panno, e22.getText().toString());
                            c.put(DatabaseHelper.Remark, e23.getText().toString());
                            c.put(DatabaseHelper.ReferFrom, (String) spinner5.getSelectedItem());
                            c.put(DatabaseHelper.ReferTo, (String) spinner6.getSelectedItem());
                            c.put(DatabaseHelper.Height, e24.getText().toString());
                            c.put(DatabaseHelper.aadharno, e25.getText().toString());
                            c.put(DatabaseHelper.Maritalstatus, spinner7.getSelectedItem().toString());
                            c.put(DatabaseHelper.WeddingDate, e27.getText().toString());
                            c.put(DatabaseHelper.providerid, GlobalValues.docid);
                            c.put(DatabaseHelper.Assistantid, GlobalValues.assistantid);
                            c.put(DatabaseHelper.Customerid, GlobalValues.practiceid);
                            c.put(DatabaseHelper.deleted, 0);
                            c.put(DatabaseHelper.IsActive, 1);
                            final Calendar cal = Calendar.getInstance();
                            String s = DateUtils.formatDate(cal.getTime(), "yyyy-MM-dd");
                            c.put(DatabaseHelper.create_date, s);
                            c.put(DatabaseHelper.update_date, s);
                            c.put(DatabaseHelper.pattype, 1);
                            c.put(DatabaseHelper.CountryId, Integer.parseInt(autotxt1.getTag().toString()));
                            c.put(DatabaseHelper.StateId, Integer.parseInt(autotxt2.getTag().toString()));
                            c.put(DatabaseHelper.CityId, Integer.parseInt(autotxt3.getTag().toString()));
                            c.put(DatabaseHelper.password, "");
                            c.put(DatabaseHelper.Patientid, 0);
                            c.put(DatabaseHelper.Title, (String) spinner2.getSelectedItem());
                            c.put(DatabaseHelper.Gender, (String) spinner3.getSelectedItem());
                            c.put(DatabaseHelper.BloodGroup, (String) spinner4.getSelectedItem());
                            c.put(DatabaseHelper.BillingIn, "");
                            c.put(DatabaseHelper.Phone, "");
                            c.put(DatabaseHelper.Remark, e28.getText().toString());
                            c.put(DatabaseHelper.Tags, "");

                            ptid = DatabaseHelper.getInstance(AddAppointmentActivity.this).savepatient(c);
                            JSONArray array = DatabaseHelper.getInstance(AddAppointmentActivity.this).getptdata(ptid);
                            JSONObject obj = array.getJSONObject(0);
                            Gson gson = new Gson();
                            Type type = new TypeToken<Patient>() {
                            }.getType();
                            GlobalValues.selectedpt = gson.fromJson(obj.toString(), type);
                            gotoptdetails();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Patient allready exist", Toast.LENGTH_LONG).show();
                    }

              /*  SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy hh:mm aa");
                Date convertedDate = new Date();
                Calendar cal1 = Calendar.getInstance();
                String val = "";
                try {
                    convertedDate = dateFormat.parse(e5.getText().toString());
                    Log.e("data ", "data " + convertedDate.getHours() + " " + convertedDate.getMinutes());
                    val = convertedDate.getHours() + "." + convertedDate.getMinutes();
                    cal1.setTime(convertedDate);
                    cal1.add(Calendar.MINUTE, 15);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String endtime = dateFormat.format(cal1.getTime());
                ContentValues c = new ContentValues();
                c.put(DatabaseHelper.Patientid, ptid);
                c.put(DatabaseHelper.identifier, "");
                c.put(DatabaseHelper.isAllDay, 0);
                c.put(DatabaseHelper.start, e5.getText().toString());
                c.put(DatabaseHelper.end, endtime);
                c.put(DatabaseHelper.calendar, "");
                c.put(DatabaseHelper.title, spinner2.getSelectedItem().toString());
                c.put(DatabaseHelper.description, e23.getText().toString());
                c.put(DatabaseHelper.purpose, e28.getText().toString());
                c.put(DatabaseHelper.status, "Confirm");
                c.put(DatabaseHelper.catagory, "");
                c.put(DatabaseHelper.name, e2.getText().toString().trim() + " " + e3.getText().toString().trim() + " " + e4.getText().toString().trim());
                c.put(DatabaseHelper.starttime, val);
                c.put(DatabaseHelper.aptdate, e5.getText().toString().split(" ")[0]);
                c.put(DatabaseHelper.DoctorId, GlobalValues.docid);
                c.put(DatabaseHelper.RoomId, 0);
                c.put(DatabaseHelper.OperatoryId, 0);
                c.put(DatabaseHelper.ipdid, 0);

                c.put(DatabaseHelper.type, "");
                c.put(DatabaseHelper.CancelRemark, "");
                c.put(DatabaseHelper.Cancelreason, "");
                c.put(DatabaseHelper.DepartmentId, 0);
                c.put(DatabaseHelper.PracticeId, GlobalValues.practiceid);

                c.put(DatabaseHelper.UserId, GlobalValues.docid);
                c.put(DatabaseHelper.generalid, 0);
                c.put(DatabaseHelper.Canceldate, "");
                DatabaseHelper.getInstance(AddAppointmentActivity.this).saveappointment(c);*/
              // Toast.makeText(getApplicationContext(), "allready exist", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
