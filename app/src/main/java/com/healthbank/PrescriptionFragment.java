package com.healthbank;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.healthbank.adapter.PrescriptionAdapter;
import com.healthbank.classes.Drug;

import java.util.ArrayList;

public class PrescriptionFragment extends Fragment {
    //  private OnFragmentInteractionListener mListener;
    RecyclerView recyclerView;
    LinearLayoutManager mLayoutmanager;
    PrescriptionAdapter adapter;
    ArrayList<Drug> mdataset;

    public PrescriptionFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PrescriptionFragment newInstance(String param1, String param2) {
        PrescriptionFragment fragment = new PrescriptionFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_prescription, container, false);
        recyclerView = v.findViewById(R.id.recyclerview_1);
        mLayoutmanager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutmanager);
        recyclerView.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getActivity(), R.drawable.recycler_divider)));
        mdataset = new ArrayList<>();
        mdataset.add(new Drug());
        mdataset.add(new Drug());
        mdataset.add(new Drug());
        mdataset.add(new Drug());
        mdataset.add(new Drug());
        adapter=new PrescriptionAdapter(getActivity(),mdataset);
        recyclerView.setAdapter(adapter);
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
        // mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
