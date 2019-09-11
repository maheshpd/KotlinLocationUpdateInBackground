package com.healthbank;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.healthbank.classes.Connection;
import com.healthbank.database.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddVitalFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddVitalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddVitalFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    EditText e1, e2, e3, e4, e5, e6, e7;
    Button bt;
    TextView txt1,txt2;
    int hour = 0;
    int   minute = 0;
    View.OnClickListener clk = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                JSONArray array = new JSONArray();
                JSONObject obj = new JSONObject();
                obj.put("lowbloodpressurel", e1.getText().toString());
                obj.put("highbloodpressure", e2.getText().toString());
                obj.put("pulse", e3.getText().toString());
                obj.put("tempurature", e4.getText().toString());
                obj.put("respirationrate", e5.getText().toString());
                obj.put("weight", e6.getText().toString());
                obj.put("height", e7.getText().toString());
                obj.put("time", txt2.getText().toString());
                array.put(obj);

                JSONArray imagearray = new JSONArray();
                ContentValues c = new ContentValues();
                c.put(DatabaseHelper.Patientid, GlobalValues.selectedpt.getPatientid());
                c.put(DatabaseHelper.DOCTORID, GlobalValues.docid);
                c.put(DatabaseHelper.Date, txt1.getText().toString());
                c.put(DatabaseHelper.JSONDATA, array.toString());
                c.put(DatabaseHelper.Imagepath, imagearray.toString());
                DatabaseHelper.getInstance(getActivity()).savelab(c, txt1.getText().toString(),GlobalValues.selectedpt.getPatientid(), DatabaseHelper.vitals);
                DatabaseHelper.getInstance(getActivity()).getlabdata(GlobalValues.selectedpt.getPatientid(), DatabaseHelper.vitals);
                e1.setText("");
                e2.setText("");
                e3.setText("");
                e4.setText("");
                e5.setText("");
                e6.setText("");
                e7.setText("");

                Intent intent = new Intent("fragmentlabadded");
                intent.putExtra(Constants.BROADCAST_URL_ACCESS, Connection.appointmentadded.ordinal());
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    // TODO: Rename and change types of parameters
    private String mParam1;
    // private OnFragmentInteractionListener mListener;
    private String mParam2;
    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
            // TODO Auto-generated method stub
             hour = hourOfDay;
             minute = minutes;
            updateTime(hour, minute);
        }
    };

    public AddVitalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddVitalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddVitalFragment newInstance(String param1, String param2) {
        AddVitalFragment fragment = new AddVitalFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private static String utilTime(int value) {
        if (value < 10)
            return "0" + value;
        else
            return String.valueOf(value);
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
        View v = inflater.inflate(R.layout.fragment_add_vital, container, false);
        e1 = v.findViewById(R.id.edittext_1);
        e2 = v.findViewById(R.id.edittext_2);
        e3 = v.findViewById(R.id.edittext_3);
        e4 = v.findViewById(R.id.edittext_4);
        e5 = v.findViewById(R.id.edittext_5);
        e6 = v.findViewById(R.id.edittext_6);
        e7 = v.findViewById(R.id.edittext_7);
        bt = v.findViewById(R.id.button_1);

        txt1 = v.findViewById(R.id.textview_1);
        txt2= v.findViewById(R.id.textview_2);
        txt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new TimePickerDialog(getActivity(), timePickerListener, hour, minute,
                        false).show();
            }
        });
        Calendar cal = Calendar.getInstance();
        final int mYear = cal.get(Calendar.YEAR);
        int mMonth = cal.get(Calendar.MONTH);
        int mDay = cal.get(Calendar.DAY_OF_MONTH);
        String m = "" +(mMonth+1);
        String d = "" + mDay;
        if ((mMonth + 1) < 10)
            m = "0" + m;

        if (mDay < 10)
            d = "0" + d;

        txt1.setText(d + "-" + m + "-" + mYear);

        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                final int mYear = cal.get(Calendar.YEAR);
                int mMonth = cal.get(Calendar.MONTH);
                int mDay = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String m = "" + monthOfYear;
                        String d = "" + dayOfMonth;
                        if ((monthOfYear + 1) < 10)
                            m = "0" + (monthOfYear + 1);

                        if (dayOfMonth < 10)
                            d = "0" + dayOfMonth;

                        txt1.setText(d + "-" + m + "-" + mYear);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        bt.setOnClickListener(clk);

        final Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        updateTime(hour, minute);

        return v;
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
        // Append in a StringBuilder
        String aTime = new StringBuilder().append(hours).append(':')
                .append(minutes).append(" ").append(timeSet).toString();
        txt2.setText(aTime);
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
       /* if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }*/
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
