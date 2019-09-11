package com.healthbank;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.healthbank.adapter.AppointmentAdapter;
import com.healthbank.classes.Appointmentdatav1;
import com.healthbank.database.DatabaseHelper;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AppointmentsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView recyclerview;
    ArrayList<Appointmentdatav1> mdataset;
    AppointmentAdapter adapter;
    LinearLayoutManager mLayoutmanager;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long id = intent.getLongExtra("id", 0);
            JSONArray array = DatabaseHelper.getInstance(getActivity()).getaptdata(id);
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Appointmentdatav1>>() {
            }.getType();
            ArrayList<Appointmentdatav1> data = gson.fromJson(array.toString(), type);
            if (data.size() > 0)
                mdataset.add(0, data.get(0));
            adapter.notifyDataSetChanged();
        }
    };
    private BroadcastReceiver receiverappointment = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
          //  Log.e("received ", "received receiverappointment");
            JSONArray array = DatabaseHelper.getInstance(getActivity()).getaptdata(GlobalValues.selectedpt.getId());
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Appointmentdatav1>>() {
            }.getType();
            ArrayList<Appointmentdatav1> data = gson.fromJson(array.toString(), type);
            if (data != null) {
                mdataset.addAll(data);
            }
            adapter.notifyDataSetChanged();
        }
    };

    public AppointmentsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AppointmentsFragment newInstance(String param1, String param2) {
        AppointmentsFragment fragment = new AppointmentsFragment();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_appointments, container, false);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receiver, new IntentFilter("fragmentaptadded"));
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receiverappointment, new IntentFilter("getAppointment"));
        recyclerview = v.findViewById(R.id.recyclerview_1);
        mLayoutmanager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(mLayoutmanager);
        mdataset = new ArrayList<>();
        recyclerview.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getContext(), R.drawable.recycler_divider)));
        if (InternetUtils.getInstance(getActivity()).available()) {
            try {
                ConnectionManager.getInstance(getActivity()).getappointments(Integer.toString(GlobalValues.selectedpt.getPatientid()), "0", Integer.toString(GlobalValues.selectedpt.getId()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            JSONArray array = DatabaseHelper.getInstance(getActivity()).getaptdata(GlobalValues.selectedpt.getId());
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Appointmentdatav1>>() {
            }.getType();
            ArrayList<Appointmentdatav1> data = gson.fromJson(array.toString(), type);
            if (data != null) {
                mdataset.addAll(data);
            }
        }
        adapter = new AppointmentAdapter(getActivity(), mdataset);
        recyclerview.setAdapter(adapter);
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiverappointment);
        super.onDestroy();
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
