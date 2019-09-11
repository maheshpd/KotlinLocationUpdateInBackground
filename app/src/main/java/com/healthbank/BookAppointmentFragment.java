package com.healthbank;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.healthbank.classes.Connection;
import com.healthbank.database.DatabaseHelper;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BookAppointmentFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    DatePicker picker;
    TextView txt1;
    RadioGroup rg;
    Button bt;
    EditText e1;
    BroadcastReceiver rec = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                String data = "";
                Bundle b = intent.getExtras();
                if (b.containsKey(Constants.BROADCAST_DATA))
                    data = b.getString(Constants.BROADCAST_DATA);
                JSONObject obj = new JSONObject(data);
                String date = obj.getString("date");
                String time = obj.getString("time");
                txt1.setText(time);
                Date d = DateUtils.parseDate(date, "dd-MM-yyyy");
                Calendar cal = Calendar.getInstance();
                cal.setTime(d);
                picker.updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    private String mParam1;
    private String mParam2;
    private int hour;
    private int minute;

    public BookAppointmentFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static BookAppointmentFragment newInstance(String param1, String param2) {
        BookAppointmentFragment fragment = new BookAppointmentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
   /* private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
            // TODO Auto-generated method stub
            hour = hourOfDay;
            minute = minutes;
            updateTime(hour, minute);
        }
    };*/

  /*  private static String utilTime(int value) {
        if (value < 10)
            return "0" + String.valueOf(value);
        else
            return String.valueOf(value);
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    // Used to convert 24hr format to 12hr format with AM/PM values
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
        String aTime = new StringBuilder().append(hours).append(':')
                .append(minutes).append(" ").append(timeSet).toString();
        txt1.setText(aTime);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_book_appointment, container, false);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(rec, new IntentFilter("slotselected"));
        txt1 = v.findViewById(R.id.textview_1);
        rg = v.findViewById(R.id.radiogroup_1);
        picker = v.findViewById(R.id.datepicker);
        e1 = v.findViewById(R.id.edittext_1);
        final Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        updateTime(hour, minute);

        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                int dayOfMonth = picker.getDayOfMonth();
                int monthOfYear = picker.getMonth();
                int year = picker.getYear();
                String d = "" + dayOfMonth;
                String m = "" + monthOfYear;
                if (dayOfMonth < 10)
                    d = "0" + dayOfMonth;

                if ((monthOfYear + 1) < 10)
                    m = "0" + (monthOfYear + 1);

                String date = (d + "-" + m + "-" + year);
                Intent intent = new Intent(getActivity(), AllAppointment.class);
                intent.putExtra("isedit", false);
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });

        bt = v.findViewById(R.id.button_1);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int dayOfMonth = picker.getDayOfMonth();
                int monthOfYear = picker.getMonth();
                int year = picker.getYear();
                String d = "" + dayOfMonth;
                String m = "" + monthOfYear;
                if (dayOfMonth < 10)
                    d = "0" + dayOfMonth;

                if ((monthOfYear + 1) < 10)
                    m = "0" + (monthOfYear + 1);

                String date = (d + "-" + m + "-" + year);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy hh:mm aa");
                Date convertedDate = new Date();
                Calendar cal1 = Calendar.getInstance();
                String val = "";
                String s = "";
                try {
                    convertedDate = dateFormat.parse((dayOfMonth + "-" + (monthOfYear + 1) + "-" + year) + " " + txt1.getText().toString());
                    s = DateUtils.formatDate(convertedDate, "dd MM yyyy");
                    Log.e("data ", "data " + s + " " + convertedDate.getHours() + " " + convertedDate.getMinutes());
                    val = convertedDate.getHours() + "." + convertedDate.getMinutes();
                    cal1.setTime(convertedDate);
                    cal1.add(Calendar.MINUTE, 15);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String endtime = dateFormat.format(cal1.getTime());
                ContentValues c = new ContentValues();
                c.put(DatabaseHelper.Patientid, GlobalValues.selectedpt.getSrno());
                c.put(DatabaseHelper.identifier, "");
                c.put(DatabaseHelper.isAllDay, 0);
                c.put(DatabaseHelper.start, date + " " + txt1.getText().toString());
                c.put(DatabaseHelper.end, endtime);
                c.put(DatabaseHelper.calendar, "");
                c.put(DatabaseHelper.title, "Mr.");
                c.put(DatabaseHelper.description, e1.getText().toString());
                c.put(DatabaseHelper.purpose, "");
                c.put(DatabaseHelper.status, "Confirm");
                c.put(DatabaseHelper.catagory, "");
                c.put(DatabaseHelper.name, GlobalValues.selectedpt.getFirstName());
                c.put(DatabaseHelper.starttime, val);
                c.put(DatabaseHelper.aptdate, date);
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
                JSONObject obj = new JSONObject();
                try {
 /*   obj.put("title", GlobalValues.selectedpt.getTitle());
    obj.put("MiddleName", GlobalValues.selectedpt.getMiddleName());
    obj.put("LastName", GlobalValues.selectedpt.getLastName());
    obj.put("Area", GlobalValues.selectedpt.getArea());
    obj.put("Mobile", GlobalValues.selectedpt.getMobile());
    obj.put("EmailId", GlobalValues.selectedpt.getEmailId());
    obj.put("Address", GlobalValues.selectedpt.getAddress1());
    obj.put("startDate", e1.getText().toString().trim() + " " + e2.getText().toString().trim());

    obj.put("endDate", s);
    obj.put("purpose", s.getSelectedItem().toString());
    obj.put("DoctorId", GlobalValues.docid);
    obj.put("type", "work");
    obj.put("CatagoryId", "0");
    obj.put("PracticeId", Globals.branchid);
    obj.put("AppoinmentSource", "Android");
    obj.put("connectionid", com.healthdata.classes.Constants.projectid);*/
                } catch (Exception e) {
                    e.printStackTrace();
                }
                long id = DatabaseHelper.getInstance(getActivity()).saveappointment(c);
                Intent intent = new Intent("fragmentaptadded");
                intent.putExtra("id", id);
                intent.putExtra(Constants.BROADCAST_URL_ACCESS, Connection.appointmentadded.ordinal());
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);

                final Calendar c1 = Calendar.getInstance();
                hour = c1.get(Calendar.HOUR_OF_DAY);
                minute = c1.get(Calendar.MINUTE);
                updateTime(hour, minute);
                e1.setText("");
                Toast.makeText(getActivity(), "Thank You.", Toast.LENGTH_LONG).show();
            }
        });
        return v;
    }

    @Override
    public void onDestroy() {
        try {
            LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(rec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
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
        // mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
