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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.healthbank.adapter.LabDetailsAdapter;
import com.healthbank.classes.LabHeader;
import com.healthbank.database.DatabaseHelper;

import org.json.JSONArray;

import java.util.ArrayList;
/*
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LabFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LabFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView recyclerview;
    ArrayList<LabHeader> mdataset;
    LinearLayoutManager mLayoutmanager;
    LabDetailsAdapter adapter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //  private OnFragmentInteractionListener mListener;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                mdataset.clear();
                JSONArray array = DatabaseHelper.getInstance(getActivity()).getlabdata(GlobalValues.selectedpt.getPatientid(), DatabaseHelper.lab);
                for (int i = 0; i < array.length(); i++) {
                    try {
                        mdataset.add(new LabHeader(array.getJSONObject(i)));
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

    public LabFragment() {
        // Required empty public constructor
    }

    public static LabFragment newInstance(String param1, String param2) {
        LabFragment fragment = new LabFragment();
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
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receiver, new IntentFilter("fragmentlabadded"));
        View v = inflater.inflate(R.layout.fragment_lab, container, false);
        try {
            mdataset = new ArrayList<>();
            JSONArray array = DatabaseHelper.getInstance(getActivity()).getlabdata(GlobalValues.selectedpt.getPatientid(), DatabaseHelper.lab);
            for (int i = 0; i < array.length(); i++) {
                try {
                    mdataset.add(new LabHeader(array.getJSONObject(i)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            recyclerview = v.findViewById(R.id.recyclerview_1);
            recyclerview.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getContext(), R.drawable.recycler_divider)));
            mLayoutmanager = new LinearLayoutManager(getActivity());
            recyclerview.setLayoutManager(mLayoutmanager);
            adapter = new LabDetailsAdapter(getActivity(), mdataset);
            recyclerview.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return v;
    }

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //   mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
