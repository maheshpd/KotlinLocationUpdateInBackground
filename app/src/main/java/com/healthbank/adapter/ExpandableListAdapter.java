package com.healthbank.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthbank.R;
import com.healthbank.classes.AppointmentHeaderData;
import com.healthbank.classes.Appointmentdatav1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by it1 on 5/31/2018.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    ArrayList<AppointmentHeaderData> mdataset;
    ArrayList<AppointmentHeaderData> filteredmdataset = new ArrayList<>();
    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String, List<String>> _listDataChild;
    private HashMap<String, List<String>> _listDataChildfiltered;

    public ExpandableListAdapter(Context context, ArrayList<AppointmentHeaderData> mdataset) {
        this._context = context;
      /*  this._listDataHeader = listDataHeader;
        this._listDataChildfiltered=listChildData;
        this._listDataChild = listChildData;*/

        this.mdataset = mdataset;
        this.filteredmdataset.addAll(mdataset);
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return filteredmdataset.get(groupPosition).getAppointmentdata().get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final Appointmentdatav1 child = ((Appointmentdatav1) getChild(groupPosition, childPosition));
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.fragment_item, null);
            ImageView prescription = convertView.findViewById(R.id.imageview_1);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
               /*  JSONArray array= DatabaseHelper.getInstance(_context).getptdata(child.getPatientid());
                    if(array.length()>0) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<Patient>>() {
                        }.getType();
                        ArrayList<Patient> data = gson.fromJson(array.toString(), type);
                        if(data.size()>0)
                        {
                            GlobalValues.selectedpt=data.get(0);
                            ((ActivityCommon) _context).gotoptdetails();
                        }

                    }*/
                }
            });
        }
        TextView textView1 = convertView.findViewById(R.id.textview_1);
        TextView textView2 = convertView.findViewById(R.id.textview_2);
        textView2.setText(child.getTitle() + "/" + child.getPatientid());
        textView1.setText(child.getStartDate().split(" ")[1] + " " + child.getStartDate().split(" ")[2]);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        /*return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();*/

        return filteredmdataset.get(groupPosition).getAppointmentdata().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        /* return this._listDataHeader.get(groupPosition);*/
        return filteredmdataset.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.filteredmdataset.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = ((AppointmentHeaderData) getGroup(groupPosition)).getName();
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }
        TextView lblListHeader = convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void filterData(String query) {
        query = query.toLowerCase();
        filteredmdataset.clear();
        if (query.isEmpty()) {
            filteredmdataset.addAll(mdataset);
        } else {
            for (AppointmentHeaderData data : mdataset) {
                ArrayList<Appointmentdatav1> aptdataList = data.getAppointmentdata();
                ArrayList<Appointmentdatav1> newList = new ArrayList<Appointmentdatav1>();
                for (Appointmentdatav1 aptdata : aptdataList) {
                    /*if(aptdata.getName().toLowerCase().contains(query) ){
                        newList.add(aptdata);
                    }*/
                }
                if (newList.size() > 0) {
                    AppointmentHeaderData aptheaderdata = new AppointmentHeaderData();
                    aptheaderdata.setAppointmentdata(newList);
                    filteredmdataset.add(aptheaderdata);
                }
            }
        }
        notifyDataSetChanged();
    }

}
