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

import com.healthbank.classes.City;
import com.healthbank.classes.Country;
import com.healthbank.classes.State;
import com.healthbank.database.DatabaseHelper;

import java.util.Calendar;

public class AddDemographicActivity extends ActivityCommon {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    EditText e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12, e14, e15, e16, e17, e18, e19, e20, e21, e22, e23, e24, e25, e26, e27, e28, e29;
    CircularImageView img1;
    Calendar cal;
    AutoCompleteTextView autotxt1, autotxt2, autotxt3;
    public AdapterView.OnItemClickListener iclk = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View v, int arg2,
                                long arg3) {
            // TODO Auto-generated method stub
            try {
                TextView textview = (TextView) v;
                String text = textview.getText().toString();
                switch (getCurrentId().getId()) {
                    case R.id.autocompletetextview_1:
                        attachState(text);
                        autotxt3.setText("");
                        autotxt2.setText("");
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
    Spinner spinner1, spinner2, spinner3, spinner4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_demographic);
        setmaterialDesign();
        back();
        setTitle("Kirti Sonawane");
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
        e26 = findViewById(R.id.edittext_26);
        e27 = findViewById(R.id.edittext_27);
        e28 = findViewById(R.id.edittext_28);
        e29 = findViewById(R.id.edittext_29);
        autotxt1 = findViewById(R.id.autocompletetextview_1);
        autotxt2 = findViewById(R.id.autocompletetextview_2);
        autotxt3 = findViewById(R.id.autocompletetextview_3);
        bindStates();

        img1 = findViewById(R.id.imageview_1);
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        e8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int mYear = cal.get(Calendar.YEAR);
                int mMonth = cal.get(Calendar.MONTH);
                int mDay = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddDemographicActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                e3.setText("" + (mYear - year));
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddDemographicActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                e21.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        e29.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                final int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddDemographicActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                e29.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
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
            //reporterror(tag,e.toString());
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
            //  Globals.user.setState(stateid);
            autotxt2.setTag(stateid);
            autotxt3.setAdapter(City.getAdapter(stateid, this));
        } catch (Exception e) {
            // reporterror(tag,e.toString());
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
                ContentValues c = new ContentValues();
                c.put(DatabaseHelper.FirstName, e5.getText().toString());
                c.put(DatabaseHelper.FName, e2.getText().toString());
                c.put(DatabaseHelper.MiddleName, e3.getText().toString());
                c.put(DatabaseHelper.LastName, e4.getText().toString());
                c.put(DatabaseHelper.Address, e6.getText().toString());
                c.put(DatabaseHelper.Area, e5.getText().toString());
                c.put(DatabaseHelper.DOB, e2.getText().toString());
                c.put(DatabaseHelper.PinCode, e3.getText().toString());
                c.put(DatabaseHelper.Mobile, e4.getText().toString());
                c.put(DatabaseHelper.AlternateMobile, e6.getText().toString());
                c.put(DatabaseHelper.EmailId, e5.getText().toString());
                c.put(DatabaseHelper.AlternateEmailId, e2.getText().toString());
                c.put(DatabaseHelper.BloodGroup, e3.getText().toString());
                c.put(DatabaseHelper.Language, e4.getText().toString());
                c.put(DatabaseHelper.Religion, e6.getText().toString());
                c.put(DatabaseHelper.Occupation, e5.getText().toString());
                c.put(DatabaseHelper.EmployeeName, e2.getText().toString());
                c.put(DatabaseHelper.EmployeeNo, e3.getText().toString());
                c.put(DatabaseHelper.DateOfjoining, e4.getText().toString());
                c.put(DatabaseHelper.panno, e6.getText().toString());
                c.put(DatabaseHelper.Remark, e5.getText().toString());
                c.put(DatabaseHelper.ReferFrom, e2.getText().toString());
                c.put(DatabaseHelper.ReferTo, e3.getText().toString());
                c.put(DatabaseHelper.Height, e4.getText().toString());
                c.put(DatabaseHelper.aadharno, e6.getText().toString());
                c.put(DatabaseHelper.Maritalstatus, e5.getText().toString());
                c.put(DatabaseHelper.WeddingDate, e2.getText().toString());
                c.put(DatabaseHelper.providerid, GlobalValues.docid);
                c.put(DatabaseHelper.Assistantid, GlobalValues.assistantid);
                c.put(DatabaseHelper.Customerid, GlobalValues.practiceid);
                c.put(DatabaseHelper.deleted, 1);
                c.put(DatabaseHelper.IsActive, 1);
                final Calendar cal = Calendar.getInstance();
                String s = DateUtils.formatDate(cal.getTime(), "yyyy-MM-dd");
                c.put(DatabaseHelper.create_date, s);
                c.put(DatabaseHelper.update_date, s);
                c.put(DatabaseHelper.AgeMonth, cal.get(Calendar.MONTH));
                c.put(DatabaseHelper.AgeYear, cal.get(Calendar.YEAR));
                c.put(DatabaseHelper.pattype, 1);
                c.put(DatabaseHelper.CountryId, (int) autotxt1.getTag());
                c.put(DatabaseHelper.StateId, (int) autotxt2.getTag());
                c.put(DatabaseHelper.CityId, (int) autotxt3.getTag());
                c.put(DatabaseHelper.password, "");
                c.put("Patientid", 0);
                c.put(DatabaseHelper.Title, (String) spinner1.getSelectedItem());
                c.put(DatabaseHelper.BloodGroup, (String) spinner2.getSelectedItem());
                DatabaseHelper.getInstance(AddDemographicActivity.this).savepatient(c);

                Toast.makeText(getApplicationContext(), "Thank You.", Toast.LENGTH_LONG).show();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
