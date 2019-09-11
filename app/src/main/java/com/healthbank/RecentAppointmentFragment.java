package com.healthbank;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.healthbank.adapter.ExpandableListAdapter;
import com.healthbank.classes.AppointmentHeaderData;
import com.healthbank.classes.Appointmentdatav1;
import com.healthbank.database.DatabaseHelper;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class RecentAppointmentFragment extends Fragment {
    private static final String ARG_COLUMN_COUNT = "column-count";
    RecyclerView recyclerView;
    RecentAppointmentAdapter adapter;
    SimpleDateFormat sd = new SimpleDateFormat("MM/dd/yy", Locale.US);
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    EditText e1;
    ArrayList<AppointmentHeaderData> mdataset;
    BroadcastReceiver rec = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        /*    Bundle b = intent.getExtras();
            int statuscode = b.getInt(Constants.BROADCAST_RESPONSE_CODE);
            int accesscode = b.getInt(Constants.BROADCAST_URL_ACCESS);
            String mdata = "";
            if (b.containsKey(Constants.BROADCAST_DATA))
                mdata = b.getString(Constants.BROADCAST_DATA);
            if (b.containsKey(Constants.BROADCAST_DATA))
                mdata = b.getString(Constants.BROADCAST_DATA);
            if (statuscode == Constants.STATUS_OK) {
                if (accesscode == Connection.Appointmentcreated.ordinal()) {

                } else if (accesscode == Connection.Appointmentcanceled.ordinal()) {
                    for (int i = 0; i < data.size(); i++) {
                        if (data.get(i).getAppointmentid().equalsIgnoreCase(mdata)) ;
                        {
                            data.get(i).setStatus("cancel");
                            adapter.notifyItemChanged(i);
                            break;
                        }
                    }
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);
                } else if (accesscode == Connection.Appointmentupdated.ordinal()) {

                }
            }*/
        }

    };
    private OnListFragmentInteractionListener mListener;
    private boolean isallappointment = false;

    public RecentAppointmentFragment() {

    }

    public static RecentAppointmentFragment newInstance(boolean isallappointment) {
        RecentAppointmentFragment fragment = new RecentAppointmentFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_COLUMN_COUNT, isallappointment);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isallappointment = getArguments().getBoolean(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_item_appointment, container, false);
        registerreceiver();
        expListView = view.findViewById(R.id.lvExp);
        // preparing list data
        mdataset = new ArrayList<>();
        prepareListData();

        e1 = view.findViewById(R.id.edittext_1);
        e1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                listAdapter.filterData(editable.toString());
            }
        });
        // setting list adapter
        listAdapter = new ExpandableListAdapter(getActivity(), mdataset);
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getActivity(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
             /*   Toast.makeText(getActivity(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();*/
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
            /*    Toast.makeText(getActivity(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();*/

            }
        });
     //   expListView.expandGroup(0);
        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
               /* Toast.makeText(
                        getActivity(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();*/
                return false;
            }
        });
        return view;
    }

    private void prepareListData() {
        mdataset.clear();
        Calendar cal = Calendar.getInstance();
        if (!isallappointment) {
            for (int i = 0; i < 7; i++) {
                cal.add(Calendar.DATE, 1);
                AppointmentHeaderData d = new AppointmentHeaderData();
                String s = DateUtils.formatDate(cal.getTime(), "dd MMM yyyy");
                String s1=DateUtils.formatDate(cal.getTime(), "dd-MM-yyyy");
                d.setName(s);
                ArrayList<Appointmentdatav1> aptdata = new ArrayList<>();
                JSONArray array = DatabaseHelper.getInstance(getActivity()).getaptdata(s1);
                Log.e("data11111 ","data "+array.toString());
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<Appointmentdatav1>>() {
                }.getType();
                aptdata = gson.fromJson(array.toString(), type);

                d.setAppointmentdata(aptdata);
                if(aptdata.size()>0)
                mdataset.add(d);
            }
        } else {
            AppointmentHeaderData d = new AppointmentHeaderData();
            String s = DateUtils.formatDate(cal.getTime(), "dd MMM yyyy");
            d.setName(s);
            String s1=DateUtils.formatDate(cal.getTime(), "dd-MM-yyyy");
            ArrayList<Appointmentdatav1> aptdata = new ArrayList<>();
            JSONArray array = DatabaseHelper.getInstance(getActivity()).getaptdata(s1);
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Appointmentdatav1>>() {
            }.getType();
            aptdata = gson.fromJson(array.toString(), type);

            d.setAppointmentdata(aptdata);
            mdataset.add(d);
        }
        listAdapter = new ExpandableListAdapter(getActivity(), mdataset);
        expListView.setAdapter(listAdapter);

        if (mdataset.size() > 0)
            expListView.expandGroup(0);
    }

    @Override
    public void onResume() {
        super.onResume();
        prepareListData();
    }

    @Override
    public void onDestroy() {
        try {
            getActivity().unregisterReceiver(rec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    private void registerreceiver() {
        getActivity().registerReceiver(rec, new IntentFilter("fragupdateapt"));
    }

    /*CustomBottomSheetDialogFragment f;

    public void showdialog(int position) {
        f = CustomBottomSheetDialogFragment.newInstance(data.get(position));
        f.show(getActivity().getSupportFragmentManager(), "Dialog");
    }*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name

    }
}
