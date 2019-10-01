package com.healthbank;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.healthbank.adapter.PatientAdapter;
import com.healthbank.classes.Connection;
import com.healthbank.classes.Patient;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class PatientlistFragment extends Fragment {
    RecyclerView mrecyclerview;
    PatientAdapter adapter;
    LinearLayoutManager mLayoutmanager;
    ArrayList<Patient> mdataset;
    EditText e1;
    String deptid,userid,locid;
    private BroadcastReceiver responseRec = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            try {
                Bundle b = intent.getExtras();
                int urlaccess = b.getInt(Constants.BROADCAST_URL_ACCESS);
                if (urlaccess == Connection.SearchPatient.ordinal()) {
                    mdataset.clear();
                    JSONArray array = new JSONArray(GlobalValues.TEMP_STR);
                    if (array.length() > 0) {
                        JSONArray array1 = array.getJSONArray(0);
                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<Patient>>() {
                        }.getType();
                        mdataset = gson.fromJson(array1.toString(), type);
                        adapter.notifyDataSetChanged();
                        adapter = new PatientAdapter(getActivity(), mdataset);
                        mrecyclerview.setAdapter(adapter);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public PatientlistFragment() {
        // Required empty public constructor
    }

    public static PatientlistFragment newInstance(boolean isallappointment) {
        PatientlistFragment fragment = new PatientlistFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    // TODO: Rename and change types and number of parameters
    public static PatientlistFragment newInstance(String param1, String param2) {
        PatientlistFragment fragment = new PatientlistFragment();
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
        View v = inflater.inflate(R.layout.fragment_patientlist, container, false);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        deptid = preferences.getString("DepartmentId", "");
        userid =  preferences.getString("ProviderId", "");
        locid =  preferences.getString("CustomerId", "");
        e1 = v.findViewById(R.id.edittext_1);
        e1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                ConnectionManager.getInstance(getActivity()).searchpatient(editable.toString(), "1", "1000", userid, deptid, locid);
            }
        });

        mdataset = new ArrayList<>();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(responseRec, new IntentFilter(Constants.BROADCAST_WIZARD));
        ConnectionManager.getInstance(getActivity()).searchpatient("", "1", "1000", userid, deptid, locid);
        mrecyclerview = v.findViewById(R.id.recyclerview_1);
        mLayoutmanager = new LinearLayoutManager(getActivity());
        mrecyclerview.setLayoutManager(mLayoutmanager);
        mrecyclerview.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getActivity(), R.drawable.recycler_divider)));
        adapter = new PatientAdapter(getActivity(), mdataset);
        mrecyclerview.setAdapter(adapter);
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDestroy() {
        try {
            LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(responseRec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
          /*  mdataset.clear();
            JSONArray array = DatabaseHelper.getInstance(getActivity()).getptdata();
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Patient>>() {
            }.getType();
            mdataset = gson.fromJson(array.toString(), type);
            Collections.reverse(mdataset);
            templateadapter = new PatientAdapter(getActivity(), mdataset);
            mrecyclerview.setAdapter(templateadapter);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
