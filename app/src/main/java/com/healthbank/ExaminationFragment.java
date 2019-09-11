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

import com.healthbank.adapter.ExaminationAdapter;
import com.healthbank.classes.ExaminationHeader;
import com.healthbank.database.DatabaseHelper;

import org.json.JSONArray;

import java.util.ArrayList;

public class ExaminationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView recyclerview;
    ArrayList<ExaminationHeader> mdataset;
    LinearLayoutManager mLayoutmanager;
    ExaminationAdapter adapter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
   // private OnFragmentInteractionListener mListener;
    private BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                mdataset.clear();
                JSONArray array = DatabaseHelper.getInstance(getActivity()).getlabdata(GlobalValues.selectedpt.getPatientid(),DatabaseHelper.Examination);
                for (int i = 0; i < array.length(); i++) {
                    try {
                        mdataset.add(new ExaminationHeader(array.getJSONObject(i)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            adapter.notifyDataSetChanged();
        }
    };

    public ExaminationFragment() {
        // Required empty public constructor
    }

    public static ExaminationFragment newInstance(String param1, String param2) {
        ExaminationFragment fragment = new ExaminationFragment();
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
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receiver,new IntentFilter("fragmentlabadded"));
        View v = inflater.inflate(R.layout.fragment_examination, container, false);
        try {
            mdataset = new ArrayList<>();
            JSONArray array = DatabaseHelper.getInstance(getActivity()).getlabdata(GlobalValues.selectedpt.getPatientid(),DatabaseHelper.Examination);
            for (int i = 0; i < array.length(); i++) {
                try {
                    mdataset.add(new ExaminationHeader(array.getJSONObject(i)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            recyclerview = v.findViewById(R.id.recyclerview_1);
            recyclerview.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getContext(), R.drawable.recycler_divider)));
            mLayoutmanager = new LinearLayoutManager(getActivity());
            recyclerview.setLayoutManager(mLayoutmanager);
            adapter = new ExaminationAdapter(getActivity(), mdataset);
            recyclerview.setAdapter(adapter);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return v;
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
       /* if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
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
