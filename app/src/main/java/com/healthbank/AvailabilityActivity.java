package com.healthbank;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.healthbank.adapter.AvailiabilityAdapter;
import com.healthbank.classes.AvailabilityData;
import com.healthbank.classes.AvailiabilityChildData;
import com.healthbank.classes.AvailiabilityDayData;

import java.util.ArrayList;
import java.util.Calendar;

public class AvailabilityActivity extends ActivityCommon {
    RecyclerView recyclerview;
    ArrayList<AvailabilityData> mdataset;
    AvailiabilityAdapter adapter;
    LinearLayoutManager mLayoutmanager;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availability);
        setmaterialDesign();
        back();
        setTitle("Availiability");

        b1 = findViewById(R.id.button_1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addnewdata();
            }
        });

        mdataset = new ArrayList<>();
        recyclerview = findViewById(R.id.recyclerview_1);
        mLayoutmanager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(mLayoutmanager);

        ArrayList<AvailiabilityChildData> data = new ArrayList<>();
        data.add(new AvailiabilityChildData());
        data.add(new AvailiabilityChildData());
        data.add(new AvailiabilityChildData());

        ArrayList<AvailiabilityDayData> daydata = new ArrayList<>();
        daydata.add(new AvailiabilityDayData("MON", data));
        AvailiabilityDayData d = new AvailiabilityDayData();
        d.setName("TUE");
        d.setMadaset(data);
        d.setIsselected(true);
        daydata.add(d);

        daydata.add(new AvailiabilityDayData("WED"));
        daydata.add(new AvailiabilityDayData("THU", data));
        daydata.add(new AvailiabilityDayData("FRI"));
        daydata.add(new AvailiabilityDayData("SAT", data));
        daydata.add(new AvailiabilityDayData("SUN", data));
        AvailabilityData availabilityData = new AvailabilityData();
        availabilityData.setDay(daydata);
        mdataset.add(availabilityData);
        adapter = new AvailiabilityAdapter(this, mdataset);
        recyclerview.setAdapter(adapter);
    }

    public void addnewdata() {
        ArrayList<AvailiabilityDayData> daydata = new ArrayList<>();
        daydata.add(new AvailiabilityDayData("MON"));
        AvailiabilityDayData d = new AvailiabilityDayData();
        d.setName("TUE");
        d.setIsselected(true);
        daydata.add(d);
        daydata.add(new AvailiabilityDayData("WED"));
        daydata.add(new AvailiabilityDayData("THU"));
        daydata.add(new AvailiabilityDayData("FRI"));
        daydata.add(new AvailiabilityDayData("SAT"));
        daydata.add(new AvailiabilityDayData("SUN"));
        AvailabilityData availabilityData = new AvailabilityData();
        availabilityData.setDay(daydata);
        mdataset.add(availabilityData);
        adapter.notifyDataSetChanged();
    }

    public void updateadapter() {
        adapter.notifyDataSetChanged();
    }

    public void delete(int parentpos, int pos, int childpos) {
        try {
            mdataset.get(parentpos).getDay().get(pos).getMadaset().remove(childpos);
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(final int parentpos, final int pos, final int childpos) {
        try {
            AvailiabilityChildData a = mdataset.get(parentpos).getDay().get(pos).getMadaset().get(childpos);
            LayoutInflater inflater = LayoutInflater.from(this);
            View dialog_layout = inflater.inflate(R.layout.add_schedule, null);
            final EditText e1 = dialog_layout.findViewById(R.id.edittext_1);
            final EditText e2 = dialog_layout.findViewById(R.id.edittext_2);
            final EditText e3 = dialog_layout.findViewById(R.id.edittext_3);
            e1.setText(a.getName());
            e2.setText(a.getFromtime());
            e3.setText(a.getTotime());
            e2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(AvailabilityActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            e2.setText(selectedHour + ":" + selectedMinute);
                        }
                    }, hour, minute, true);//Yes 24 hour time
                    mTimePicker.setTitle("Select Time");
                    mTimePicker.show();
                }
            });

            e3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(AvailabilityActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            e3.setText(selectedHour + ":" + selectedMinute);
                        }
                    }, hour, minute, true);//Yes 24 hour time
                    mTimePicker.setTitle("Select Time");
                    mTimePicker.show();
                }
            });

            AlertDialog.Builder db = new AlertDialog.Builder(AvailabilityActivity.this);
            db.setView(dialog_layout);
            db.setTitle("Add Schedule");
            db.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (e1.getText().toString().length() > 0 && e2.getText().toString().length() > 0 && e3.getText().toString().length() > 0) {
                        AvailiabilityChildData a = mdataset.get(parentpos).getDay().get(pos).getMadaset().get(childpos);
                        a.setName(e1.getText().toString());
                        a.setFromtime(e2.getText().toString());
                        a.setTotime(e3.getText().toString());

                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getApplicationContext(), "please enter all details", Toast.LENGTH_LONG).show();
                    }
                }
            });
            db.show();
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void opendialog(final int pos, final int childpos) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialog_layout = inflater.inflate(R.layout.add_schedule, null);
        final EditText e1 = dialog_layout.findViewById(R.id.edittext_1);
        final EditText e2 = dialog_layout.findViewById(R.id.edittext_2);
        final EditText e3 = dialog_layout.findViewById(R.id.edittext_3);

        e2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(AvailabilityActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                e2.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        e3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(AvailabilityActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                e3.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        AlertDialog.Builder db = new AlertDialog.Builder(AvailabilityActivity.this);
        db.setView(dialog_layout);
        db.setTitle("Add Schedule");
        db.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (e1.getText().toString().length() > 0 && e2.getText().toString().length() > 0 && e3.getText().toString().length() > 0) {
                    AvailiabilityChildData child = new AvailiabilityChildData();
                    child.setName(e1.getText().toString());
                    child.setFromtime(e2.getText().toString());
                    child.setFromtime(e3.getText().toString());
                    mdataset.get(pos).getDay().get(childpos).getMadaset().add(child);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getApplicationContext(), "please enter all details", Toast.LENGTH_LONG).show();
                }
            }
        });
        db.show();
    }
}
