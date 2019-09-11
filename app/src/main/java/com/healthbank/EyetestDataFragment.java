package com.healthbank;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.healthbank.adapter.EyesCheckpAdapter;
import com.healthbank.classes.EyeData;
import com.healthbank.database.DatabaseHelper;

import org.json.JSONObject;

import java.util.ArrayList;

public class EyetestDataFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Spinner spinner;
    ArrayList<String> datespinnerdata;
    RecyclerView mRecyclerview;
    Context mContext;
    ArrayAdapter<String> datearrayadapter;
    EyesCheckpAdapter adapter;
    LinearLayoutManager mLayoutManager;
    ArrayList<EyeData> mdataset;
    ArrayList<EyeData> originaldata;
    BroadcastReceiver rec = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mdataset.clear();
            adapter.notifyDataSetChanged();
            updatedata();
        }
    };
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EyetestDataFragment() {

    }

    // TODO: Rename and change types and number of parameters
    public static EyetestDataFragment newInstance(String param1, String param2) {
        EyetestDataFragment fragment = new EyetestDataFragment();
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
        View v = inflater.inflate(R.layout.fragment_eyetest_data, container, false);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(rec, new IntentFilter("eyedataupdated"));
        mRecyclerview = v.findViewById(R.id.recyclerview_1);
        spinner = v.findViewById(R.id.spinner_1);
        datespinnerdata = new ArrayList<>();
        datespinnerdata.add("All");
        datearrayadapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, datespinnerdata);
        spinner.setAdapter(datearrayadapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updatedataaccordingtospinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerview.setLayoutManager(mLayoutManager);
        mdataset = new ArrayList<>();
        originaldata = new ArrayList<>();
        adapter = new EyesCheckpAdapter(getActivity(), mdataset);
        mRecyclerview.setAdapter(adapter);

        updatedata();

        return v;
    }

    private void updatedataaccordingtospinner(){
        mdataset.clear();
        if (datespinnerdata.get(spinner.getSelectedItemPosition()).equalsIgnoreCase("All")) {
            mdataset.addAll(originaldata);
        } else {
            String selecteddate = datespinnerdata.get(spinner.getSelectedItemPosition());
            for (int i = 0; i < originaldata.size(); i++) {
                if (selecteddate.equalsIgnoreCase(originaldata.get(i).getDate()))
                    mdataset.add(originaldata.get(i));
            }
        }
        adapter.notifyDataSetChanged();
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

    private void updatedata() {
        mdataset.clear();
        originaldata.clear();
        datespinnerdata.clear();
        datespinnerdata.add("All");
        ArrayList<String> data = DatabaseHelper.getInstance(getActivity()).geteyespecialitydata(GlobalValues.selectedpt.getPatientid());
        for (int i = 0; i < data.size(); i++) {
            try {
                JSONObject obj1 = new JSONObject(data.get(i));
                JSONObject obj = obj1.getJSONObject("root").getJSONObject("subroot");
                obj.put("id", obj1.getLong("id"));
                obj.put("Date", obj1.getString("Date"));
                EyeData eyedata = new EyeData(obj);
                originaldata.add(eyedata);
                datespinnerdata.add(eyedata.getDate());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        datearrayadapter.notifyDataSetChanged();
        updatedataaccordingtospinner();
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
