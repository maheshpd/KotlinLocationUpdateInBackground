package com.healthbank;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.healthbank.adapter.AdviceLayoutAdapter;
import com.healthbank.classes.Advicemasterdata;
import com.healthbank.classes.Connection;
import com.healthbank.database.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class AddAdviceFragment extends android.support.v4.app.Fragment {
    TextView txt1;
    Button b1;
    RecyclerView recyclerview;
    LinearLayoutManager mLayoutmanager;
    AdviceLayoutAdapter adapter;
    ArrayList<String> advicedata;
    LinearLayout layout;
    ArrayAdapter<Advicemasterdata> adviceadapter;
    ArrayList<Advicemasterdata> masterdata;

    public AddAdviceFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AddAdviceFragment newInstance(String param1, String param2) {
        AddAdviceFragment fragment = new AddAdviceFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_advice, container, false);
        DatabaseHelper.getInstance(getActivity()).getlabdata(GlobalValues.selectedpt.getPatientid(), DatabaseHelper.Advice);
        advicedata = new ArrayList<>();
        advicedata.add("");
        recyclerview = v.findViewById(R.id.recyclerview_1);
        mLayoutmanager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(mLayoutmanager);
        adapter = new AdviceLayoutAdapter(getActivity(), advicedata, "Advice");
        recyclerview.setAdapter(adapter);
        txt1 = v.findViewById(R.id.textview_1);
        b1 = v.findViewById(R.id.button_1);
        layout = v.findViewById(R.id.layout_1);
        masterdata = new ArrayList<>();
        masterdata = DatabaseHelper.getInstance(getActivity()).getadvicemasterdata();
        adviceadapter = new ArrayAdapter<Advicemasterdata>(getActivity(), android.R.layout.simple_list_item_1, masterdata);
        addview();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ContentValues c = new ContentValues();
                    JSONArray array = new JSONArray();
                    for (int i = 0; i < layout.getChildCount(); i++) {
                        View v = layout.getChildAt(i);
                        JSONObject obj = new JSONObject();
                        AutoCompleteTextView autotxt = v.findViewById(R.id.autocompletetextview_1);
                        EditText e1 = v.findViewById(R.id.edittext_1);
                        obj.put(DatabaseHelper.NAME, autotxt.getText().toString());
                        obj.put(DatabaseHelper.AMOUNT, e1.getText().toString());
                        obj.put("ispaid", false);
                        array.put(obj);
                        ContentValues cvalues = new ContentValues();
                        cvalues.put(DatabaseHelper.Title, autotxt.getText().toString());
                        cvalues.put(DatabaseHelper.Description, "");
                        cvalues.put(DatabaseHelper.type, Constants.ADVICE);
                        cvalues.put(DatabaseHelper.DOCTORID, GlobalValues.docid);
                        cvalues.put(DatabaseHelper.CODE, "0");
                        cvalues.put(DatabaseHelper.PRACTICEID, GlobalValues.practiceid);
                        cvalues.put(DatabaseHelper.SAVEDTIME, DateUtils.getSqliteTime());
                        cvalues.put(DatabaseHelper.AMOUNT, e1.getText().toString());
                        long id = DatabaseHelper.getInstance(getActivity()).saveadvicemaster(Constants.ADVICE, cvalues);

                        if (id != -1){
                            Advicemasterdata masterData = new Advicemasterdata();
                            masterData.setTitle(autotxt.getText().toString());
                            masterData.setDescription("");
                            masterData.setType(Constants.ADVICE);
                            masterData.setDoctorId(GlobalValues.docid);
                            masterData.setCode("0");
                            masterData.setPracticeId(GlobalValues.practiceid);
                            masterData.setAmount(e1.getText().toString());
                            masterdata.add(masterData);
                            adviceadapter = new ArrayAdapter<Advicemasterdata>(getActivity(), android.R.layout.simple_list_item_1, masterdata);
                            adviceadapter.notifyDataSetChanged();
                        }
                    }

                    c.put(DatabaseHelper.Patientid, GlobalValues.selectedpt.getPatientid());
                    c.put(DatabaseHelper.DOCTORID, GlobalValues.docid);
                    c.put(DatabaseHelper.Date, txt1.getText().toString());
                    c.put(DatabaseHelper.JSONDATA, array.toString());
                    DatabaseHelper.getInstance(getActivity()).savelab(c, txt1.getText().toString(), GlobalValues.selectedpt.getPatientid(), DatabaseHelper.Advice);
                    advicedata.clear();
                    advicedata.add("");
                    adapter.notifyDataSetChanged();
                    Intent intent = new Intent("fragmentlabadded");
                    intent.putExtra(Constants.BROADCAST_URL_ACCESS, Connection.appointmentadded.ordinal());
                    LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                    layout.removeAllViews();
                    addview();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Calendar cal = Calendar.getInstance();
        final int mYear = cal.get(Calendar.YEAR);
        int mMonth = cal.get(Calendar.MONTH);
        int mDay = cal.get(Calendar.DAY_OF_MONTH);
        String m = "" + (mMonth + 1);
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String m = "" + monthOfYear;
                                String d = "" + dayOfMonth;
                                if ((monthOfYear + 1) < 10)
                                    m = "0" + m;

                                if (dayOfMonth < 10)
                                    d = "0" + d;

                                txt1.setText(d + "-" + m + "-" + mYear);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        return v;
    }

    public void addview() {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.layout_addadvice, null);
        AutoCompleteTextView autotxt = v.findViewById(R.id.autocompletetextview_1);
        final EditText e1 = v.findViewById(R.id.edittext_1);
        TextView t1 = v.findViewById(R.id.textview_1);
        t1.setText(getActivity().getResources().getString(R.string.char_rupees));

        autotxt.setAdapter(adviceadapter);
        autotxt.setThreshold(0);

        autotxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Advicemasterdata advice = (Advicemasterdata) adapterView.getAdapter().getItem(i);
                e1.setText(advice.getAmount());
            }
        });

        autotxt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Advicemasterdata advice = new Advicemasterdata();
                e1.setText(advice.getAmount());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ImageView img = v.findViewById(R.id.imageview_1);
        img.setTag(layout.getChildCount());
        if (layout.getChildCount() == 0) {
            img.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_plus));
        } else {
            img.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_minus));
        }

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int pos = (int) view.getTag();
                    if (pos == 0) {
                        addview();
                    } else {
                        layout.removeViewAt(pos);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        layout.addView(v);
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
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
