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
import android.widget.Button;
import android.widget.TextView;

import com.healthbank.adapter.AdviceAdapter;
import com.healthbank.classes.AdviceHeader;
import com.healthbank.database.DatabaseHelper;

import org.json.JSONArray;

import java.util.ArrayList;

public class AdviceFragment extends Fragment {
    RecyclerView recyclerview;
    ArrayList<AdviceHeader> mdataset;
    LinearLayoutManager mLayoutmanager;
    AdviceAdapter adapter;
    TextView txt;
    Button b1;
    long totalamount = 0;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                mdataset.clear();
                JSONArray array = DatabaseHelper.getInstance(getActivity()).getlabdata(GlobalValues.selectedpt.getPatientid(), DatabaseHelper.Advice);
                for (int i = 0; i < array.length(); i++) {
                    try {
                        mdataset.add(new AdviceHeader(array.getJSONObject(i)));
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

    public AdviceFragment() {
    }

    public static AdviceFragment newInstance(String param1, String param2) {
        AdviceFragment fragment = new AdviceFragment();
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
        View v = inflater.inflate(R.layout.fragment_advice, container, false);
        txt = v.findViewById(R.id.textview_1);
        b1 = v.findViewById(R.id.button_1);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalValues.advicedata.clear();
                for (int i = 0; i < mdataset.size(); i++) {
                    for (int j = 0; j < mdataset.get(i).getMdataset().size(); j++) {
                        if (mdataset.get(i).getMdataset().get(j).isIsselected())
                            GlobalValues.advicedata.add(mdataset.get(i).getMdataset().get(j));
                    }
                }
                Intent intent = new Intent(getActivity(), PayBillActivity.class);
                startActivity(intent);
            }
        });
        try {
            mdataset = new ArrayList<>();
            JSONArray array = DatabaseHelper.getInstance(getActivity()).getlabdata(GlobalValues.selectedpt.getPatientid(), DatabaseHelper.Advice);
            for (int i = 0; i < array.length(); i++) {
                try {
                    mdataset.add(new AdviceHeader(array.getJSONObject(i)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            recyclerview = v.findViewById(R.id.recyclerview_1);
            recyclerview.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getContext(), R.drawable.recycler_divider)));
            mLayoutmanager = new LinearLayoutManager(getActivity());
            recyclerview.setLayoutManager(mLayoutmanager);
            adapter = new AdviceAdapter(getActivity(), mdataset, this);
            recyclerview.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return v;
    }

    public void updatecost(long cost, int flag) {
        switch (flag) {
            case 0:
                totalamount = totalamount + cost;
                break;
            case 1:
                totalamount = totalamount - cost;
                break;
        }
        if (totalamount > 0) {
            txt.setVisibility(View.VISIBLE);
            txt.setText("Total Payable Amount:" + totalamount);
            b1.setVisibility(View.VISIBLE);
        } else {
            txt.setText("");
            txt.setVisibility(View.GONE);
            b1.setVisibility(View.GONE);
        }
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
