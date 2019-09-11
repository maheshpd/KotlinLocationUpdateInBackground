package com.healthbank;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.healthbank.classes.BillingClass;
import com.healthbank.classes.BillingDetailsAdapter;
import com.healthbank.database.DatabaseHelper;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class BillingDetailsFragment extends Fragment {
    RecyclerView mRecyclerview;
    LinearLayoutManager mLayoutmanager;
    ArrayList<BillingClass> mdataset;
    BillingDetailsAdapter adapter;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                mdataset.clear();
                JSONArray array=DatabaseHelper.getInstance(getActivity()).getdata(DatabaseHelper.Table_billing);
                Gson gson=new Gson();
                Type token=new TypeToken<ArrayList<BillingClass>>(){}.getType();
                mdataset=gson.fromJson(array.toString(),token);
                adapter=new BillingDetailsAdapter(getActivity(),mdataset);
                mRecyclerview.setAdapter(adapter);
            } catch (Exception e) {
                e.printStackTrace();
            }
            adapter.notifyDataSetChanged();
        }
    };

    public BillingDetailsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static BillingDetailsFragment newInstance(String param1, String param2) {
        BillingDetailsFragment fragment = new BillingDetailsFragment();
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
    public void onDestroy() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_billing_details, container, false);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receiver, new IntentFilter("fragmentlabadded"));
        mRecyclerview = v.findViewById(R.id.recyclerview_1);
        mLayoutmanager=new LinearLayoutManager(getActivity());
        mRecyclerview.setLayoutManager(mLayoutmanager);
        mdataset=new ArrayList<>();
        JSONArray array=DatabaseHelper.getInstance(getActivity()).getdata(DatabaseHelper.Table_billing);

        Gson gson=new Gson();
        Type token=new TypeToken<ArrayList<BillingClass>>(){}.getType();
        mdataset=gson.fromJson(array.toString(),token);
        adapter=new BillingDetailsAdapter(getActivity(),mdataset);
        mRecyclerview.setAdapter(adapter);
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
