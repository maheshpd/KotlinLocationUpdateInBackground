package com.healthbank;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.healthbank.classes.VitalHeader;
import com.healthbank.database.DatabaseHelper;

import org.json.JSONArray;

import java.util.ArrayList;

public class VitalFragment extends Fragment {
   ArrayList<VitalHeader> vitalsdata;
    LinearLayout layout;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if(layout!=null) {
                    layout.removeAllViews();
                    vitalsdata = new ArrayList<>();
                    JSONArray array = DatabaseHelper.getInstance(getActivity()).getlabdata(GlobalValues.selectedpt.getPatientid(), DatabaseHelper.vitals);
                    for (int i = 0; i < array.length(); i++) {
                        try {
                            vitalsdata.add(new VitalHeader(array.getJSONObject(i)));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    TableMainLayout v = new TableMainLayout(getActivity(), vitalsdata);
                    layout.addView(v);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public VitalFragment() {
    }

    public static VitalFragment newInstance(String param1, String param2) {
        VitalFragment fragment = new VitalFragment();
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
        View v = inflater.inflate(R.layout.fragment_vital, container, false);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receiver, new IntentFilter("fragmentlabadded"));
        vitalsdata = new ArrayList<>();
        JSONArray array = DatabaseHelper.getInstance(getActivity()).getlabdata(GlobalValues.selectedpt.getPatientid(), DatabaseHelper.vitals);
        for (int i = 0; i < array.length(); i++) {
            try {
                vitalsdata.add(new VitalHeader(array.getJSONObject(i)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        TableMainLayout tablelayout = new TableMainLayout(getActivity(),vitalsdata);
        layout = v.findViewById(R.id.layout1);
        layout.addView(tablelayout);
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
           if(layout!=null) {
               layout.removeAllViews();
               vitalsdata = new ArrayList<>();
               JSONArray array = DatabaseHelper.getInstance(getActivity()).getlabdata(GlobalValues.selectedpt.getPatientid(), DatabaseHelper.vitals);
               for (int i = 0; i < array.length(); i++) {
                   try {
                       vitalsdata.add(new VitalHeader(array.getJSONObject(i)));
                   } catch (Exception e) {
                       e.printStackTrace();
                   }
               }
               TableMainLayout v = new TableMainLayout(getActivity(), vitalsdata);
               layout.addView(v);
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
