package com.healthbank;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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

public class BookAppointmentFragmentv1 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    EditText e1, e2, e3;
    Button bt;
    SharedPreferences pref;
    SharedPreferences.Editor edit;
    Patient profile;
    Calendar selectedcal;
    Spinner sp1, sp2;
    ArrayList<Master> titlemaster;
    ArrayList<Master> purposemaster;
    ArrayAdapter<Master> titleadapter;
    ArrayAdapter<Master> purposeadapter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private BroadcastReceiver responseRec = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            try {
                Bundle b = intent.getExtras();
                int statuscode = b.getInt(Constants.BROADCAST_RESPONSE_CODE);
                int urlaccess = b.getInt(Constants.BROADCAST_URL_ACCESS);
                String data = "";
                Log.e("received ", "received " + urlaccess + " " + Connection.GETPurpose.ordinal() + " " + Connection.BookAppointment.ordinal());
                if (b.containsKey(Constants.BROADCAST_DATA))
                    data = b.getString(Constants.BROADCAST_DATA);
                if (urlaccess == Connection.BookAppointment.ordinal()) {
                    e1.setText("");
                    e2.setText("");
                    Toast.makeText(getActivity(), "Thank You.", Toast.LENGTH_LONG).show();
                    getActivity().finish();
                } else if (urlaccess == Connection.GETPurpose.ordinal()) {
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
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    /*
        private BroadcastReceiver recpurpose = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
               */
/* Bundle b = intent.getExtras();
            int statuscode = b.getInt(Constants.BROADCAST_RESPONSE_CODE);
            int urlaccess = b.getInt(Constants.BROADCAST_URL_ACCESS);
            if(statuscode==Constants.STATUS_OK) {
                try {
                    if(urlaccess==Connection.BookAppointment.ordinal()) {
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
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }*//*

        }
    };
*/
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

    public BookAppointmentFragmentv1() {

    }

    public static BookAppointmentFragmentv1 newInstance(String param1, String param2) {
        BookAppointmentFragmentv1 fragment = new BookAppointmentFragmentv1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_book_appointment_fragmentv1, container, false);

        titlemaster = new ArrayList<>();
        purposemaster = new ArrayList<>();
        selectedcal = Calendar.getInstance();
        pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        edit = pref.edit();

        profile = GlobalValues.selectedpt;
        e1 = v.findViewById(R.id.edittext_1);
        e2 = v.findViewById(R.id.edittext_2);
        e3 = v.findViewById(R.id.edittext_3);
        sp1 = v.findViewById(R.id.spinner_1);
        sp2 = v.findViewById(R.id.spinner_2);

        purposeadapter = new ArrayAdapter<Master>(getActivity(), android.R.layout.simple_spinner_item, purposemaster);
        purposeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp2.setAdapter(purposeadapter);

        bt = v.findViewById(R.id.button_1);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(responseRec, new IntentFilter(Constants.BROADCAST_WIZARD));

        e1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                final int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
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
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        selectedcal.set(Calendar.HOUR_OF_DAY, selectedHour);
                        selectedcal.set(Calendar.MINUTE, selectedMinute);
                        String s = DateUtils.formatDate(selectedcal.getTime(), "hh:mm aa");
                        e2.setText(s);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        try {
            JSONArray array1 = DatabaseHelper.getInstance(getActivity()).getMaster(Constants.purpose);
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Master>>() {}.getType();
            ArrayList<Master> dataset = gson.fromJson(array1.toString(), type);
            if (dataset != null)
                purposemaster.addAll(dataset);

            purposeadapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (e1.getText().toString().length() > 0 && e2.getText().length() > 0 && e3.getText().toString().length() > 0) {
                        JSONObject obj = new JSONObject();
                        obj.put("AppoinmentType", "Existing");
                        obj.put("searchid", profile.getPatientid());
                        try {
                            obj.put("title", profile.getFName());
                            obj.put("MiddleName", profile.getMiddleName());
                            obj.put("LastName", profile.getLastName());
                            obj.put("Area", profile.getArea());
                            obj.put("mobile", profile.getMobile());
                            obj.put("EmailId", profile.getEmailId());
                            obj.put("Address", profile.getAddress1());

                            Date d1 = selectedcal.getTime();
                            String s1 = DateUtils.formatDate(d1, "dd-MM-yyyy HH:mm");
                            String aptdate=DateUtils.formatDate(d1, "dd-MM-yyyy");
                            Log.e("aptdate ","apt date "+aptdate);
                            obj.put("startDate", s1);
                            selectedcal.add(Calendar.MINUTE, 15);
                            Date d = selectedcal.getTime();
                            String s = DateUtils.formatDate(d, "dd-MM-yyyy HH:mm");
                            obj.put("endDate", s);
                            obj.put("purpose", sp2.getSelectedItem().toString());
                            obj.put("DoctorId", GlobalValues.docid);
                            obj.put("type", "work");
                            obj.put("CatagoryId", "0");
                            obj.put("PracticeId", GlobalValues.branchid);
                            obj.put("AppoinmentSource", "Android");
                            obj.put("Titl", profile.getTitle());
                            obj.put("connectionid", Constants.projectid);
                            ContentValues c = new ContentValues();
                            c.put("AppoinmentType", "Existing");
                            c.put(DatabaseHelper.title, profile.getFName());
                            c.put(DatabaseHelper.MiddleName, profile.getMiddleName());
                            c.put(DatabaseHelper.LastName, profile.getLastName());
                            c.put(DatabaseHelper.Area, profile.getArea());
                            c.put(DatabaseHelper.mobile, profile.getMobile());
                            c.put(DatabaseHelper.EmailId, profile.getEmailId());
                            c.put(DatabaseHelper.address, profile.getAddress1());
                            c.put(DatabaseHelper.startDate, e1.getText().toString().trim() + " " + e2.getText().toString().trim());
                            c.put(DatabaseHelper.endDate, s);
                            c.put(DatabaseHelper.purpose, sp2.getSelectedItem().toString());
                            c.put(DatabaseHelper.DoctorId, GlobalValues.docid);
                            c.put(DatabaseHelper.type, "work");
                            c.put(DatabaseHelper.CatagoryId, "0");
                            c.put(DatabaseHelper.PracticeId, GlobalValues.branchid);
                            c.put(DatabaseHelper.AppoinmentSource, "Android");
                            c.put(DatabaseHelper.Titl, profile.getTitle());
                            c.put(DatabaseHelper.aptdate,aptdate);
                            c.put(DatabaseHelper.starttime, e2.getText().toString().trim());
                            c.put(DatabaseHelper.DBPatientid, GlobalValues.selectedpt.getId());
                            c.put("status", "Pending");
                            Log.e("Glo", "" +c.toString());
                            Log.e("cdata ", "data " + c.toString());
                            c.put(DatabaseHelper.Patientid, GlobalValues.selectedpt.getPatientid());
                            if (InternetUtils.getInstance(getActivity()).available()) {
                                c.put(DatabaseHelper.SYNC, 1);
                                DatabaseHelper.getInstance(getActivity()).saveappointment(c);
                                ConnectionManager.getInstance(getActivity()).BookAppointment(obj.toString());
                            } else {
                                c.put(DatabaseHelper.SYNC, 0);

                                DatabaseHelper.getInstance(getActivity()).saveappointment(c);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Please enter all details", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Log.e("error ", "error " + e.toString());
                    e.printStackTrace();
                }
            }
        });
        return v;
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        // getActivity().unregisterReceiver(recpurpose);
        try {
            LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(responseRec);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
