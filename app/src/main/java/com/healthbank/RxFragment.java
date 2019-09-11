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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.healthbank.adapter.RxDetailedAdapter;
import com.healthbank.classes.Detailedrx;
import com.healthbank.classes.Drug;
import com.healthbank.classes.LifeStyle;
import com.healthbank.database.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RxFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RxFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RxFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // TODO: Rename and change types of parameters
    ArrayList<Detailedrx> originaldata;
    RecyclerView recyclerview_1;
    RxDetailedAdapter adapter;
    ArrayList<Drug> mdataset;
    BroadcastReceiver rec = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            setdata();
        }
    };
    BroadcastReceiver rec1 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("received ", "received drugupdated");
            int dbvisitid = 0;
            int visitid = intent.getIntExtra("visitid", 0);
            Log.e("visitid ", "visitid " + visitid);
            ArrayList<Drug> drugs = (ArrayList<Drug>) intent.getSerializableExtra("drugdata");
            for (int i = 0; i < originaldata.size(); i++) {
                Log.e("visitid ", "visitid " + visitid + " " + originaldata.get(i).getVisit_no());
                if (originaldata.get(i).getVisit_no() == visitid) {
                    originaldata.get(i).setMdataset(drugs);
                }
            }
            adapter.notifyDataSetChanged();
        }
    };

    public RxFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static RxFragment newInstance(String param1, String param2) {
        RxFragment fragment = new RxFragment();
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
        View v = inflater.inflate(R.layout.fragment_rx, container, false);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(rec, new IntentFilter("drugdata"));
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(rec1, new IntentFilter("drugupdated"));
        originaldata = new ArrayList<>();
        mdataset = new ArrayList<>();
        recyclerview_1 = v.findViewById(R.id.recyclerview_1);
        LinearLayoutManager mLayoutmanager = new LinearLayoutManager(getActivity());
        recyclerview_1.setLayoutManager(mLayoutmanager);
        recyclerview_1.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getActivity(), R.drawable.recycler_divider)));
        adapter = new RxDetailedAdapter(getActivity(), originaldata);
        recyclerview_1.setAdapter(adapter);
      /*  if(InternetUtils.getInstance(getActivity()).available()) {
            ConnectionManager.getInstance(getActivity()).getprescription(Integer.toString(GlobalValues.selectedpt.getPatientid()), "all", 0);
        }else {
            setdata();
        }*/
        setdata();
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    public void setdata() {
        try {
            JSONArray array = DatabaseHelper.getInstance(getActivity()).getdrugdata(GlobalValues.selectedpt.getId());
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = new JSONObject(array.getJSONObject(i).getString("jsondata"));
                Gson gson = new Gson();
                Type type = new TypeToken<Drug>() {
                }.getType();
                Drug data = gson.fromJson(obj.toString(), type);
                mdataset.add(data);
            }
            for (int j = 0; j < mdataset.size(); j++) {
                int i = 0;
                for (i = 0; i < originaldata.size(); i++) {
                    if (originaldata.get(i).getVisit_no() == mdataset.get(j).getVisit_no()) {
                        originaldata.get(i).getMdataset().add(mdataset.get(j));
                        break;
                    }
                }

                if (i == originaldata.size()) {
                    Detailedrx rx = new Detailedrx();
                    rx.setVisit_no(mdataset.get(j).getVisit_no());
                    rx.setVisitdate(mdataset.get(j).getVisitdate());
                    rx.setDbvisitid(mdataset.get(j).getDbvisitid());
                    rx.getMdataset().add(mdataset.get(j));
                    originaldata.add(rx);
                }
            }

            ArrayList<LifeStyle> lifestyledataset = new ArrayList<>();
               /* try {
                    JSONArray array = DatabaseHelper.getInstance(getActivity()).getdata(DatabaseHelper.TABLE_LIFESTYLE);
                    try {
                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<LifeStyle>>() {
                        }.getType();
                        ArrayList<LifeStyle> data = gson.fromJson(array.toString(), type);
                        lifestyledataset.addAll(data);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
/*
                for (int j = 0; j < lifestyledataset.size(); j++) {
                    int i=0;
                    for ( i = 0; i < originaldata.size(); i++) {
                        if (originaldata.get(i).getVisit_no() ==lifestyledataset.get(j).getVisit_no())
                        {
                            originaldata.get(i).getLifestyledata().add(lifestyledataset.get(j));
                            break;
                        }
                    }
                    if(i==originaldata.size())
                    {
                        Detailedrx rx=new Detailedrx();
                        rx.setVisit_no(mdataset.get(j).getVisit_no());
                        rx.setVisitdate(mdataset.get(j).getVisitdate());
                        rx.getLifestyledata().add(lifestyledataset.get(j));
                        originaldata.add(rx);
                    }
                }*/

            adapter.notifyDataSetChanged();
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

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(rec);
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(rec1);
        super.onDestroy();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
