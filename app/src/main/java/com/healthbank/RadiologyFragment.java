package com.healthbank;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.healthbank.adapter.RadiologyDetailsAdapter;
import com.healthbank.classes.RadiologyHeader;
import com.healthbank.database.DatabaseHelper;

import org.json.JSONArray;

import java.util.ArrayList;
public class RadiologyFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    RecyclerView recyclerview;
    ArrayList<RadiologyHeader> mdataset;
    LinearLayoutManager mLayoutmanager;
    RadiologyDetailsAdapter adapter;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                Log.e("received ","received ");
                mdataset.clear();
                JSONArray array = DatabaseHelper.getInstance(getActivity()).getlabdata(GlobalValues.selectedpt.getPatientid(), DatabaseHelper.Radiology);
                for (int i = 0; i < array.length(); i++) {
                    try {
                        mdataset.add(new RadiologyHeader(array.getJSONObject(i)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            adapter.notifyDataSetChanged();
        }
    };

    public RadiologyFragment() {
        // Required empty public constructor
    }

    public static RadiologyFragment newInstance(String param1, String param2) {
        RadiologyFragment fragment = new RadiologyFragment();
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
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receiver, new IntentFilter("fragmentlabadded"));

        View v = inflater.inflate(R.layout.fragment_radiology, container, false);
        try {
            mdataset = new ArrayList<>();
            JSONArray array = DatabaseHelper.getInstance(getActivity()).getlabdata(GlobalValues.selectedpt.getPatientid(), DatabaseHelper.Radiology);
            for (int i = 0; i < array.length(); i++) {
                try {
                    mdataset.add(new RadiologyHeader(array.getJSONObject(i)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            recyclerview = v.findViewById(R.id.recyclerview_1);
            recyclerview.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getContext(), R.drawable.recycler_divider)));
            mLayoutmanager = new LinearLayoutManager(getActivity());
            recyclerview.setLayoutManager(mLayoutmanager);
            adapter = new RadiologyDetailsAdapter(getActivity(), mdataset);
            recyclerview.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
        super.onDestroy();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
