package com.healthbank.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.healthbank.Fonter;
import com.healthbank.GlobalValues;
import com.healthbank.R;
import com.healthbank.classes.Doctor;

import java.util.ArrayList;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.viewholder> {
    Context mContext;
    ArrayList<Doctor> mdataset;
    ArrayList<Doctor> originaldata = new ArrayList<>();
    private OnItemClickListener listener = null;

    public DoctorAdapter(Context mContext, ArrayList<Doctor> mdataset, OnItemClickListener listener) {
        this.mContext = mContext;
        originaldata.addAll(mdataset);
        this.mdataset = mdataset;
        this.listener = listener;
    }

    public DoctorAdapter(Context mContext, ArrayList<Doctor> mdataset) {
        this.mContext = mContext;
        originaldata.addAll(mdataset);
        this.mdataset = mdataset;
    }

    @NonNull
    @Override
    public DoctorAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.list_item_doclist, parent, false);
        return new DoctorAdapter.viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorAdapter.viewholder holder, final int position) {
        holder.txt1.setTypeface(Fonter.getTypefaceregular(mContext));
        holder.txt2.setTypeface(Fonter.getTypefaceregular(mContext));
        holder.txt3.setTypeface(Fonter.getTypefaceregular(mContext));
        holder.txt1.setText(mdataset.get(position).getName());
        if (mdataset.get(position).getQualification() != null) {
            if (mdataset.get(position).getQualification().length() > 0) {
                holder.txt2.setVisibility(View.VISIBLE);
                holder.txt2.setText(mdataset.get(position).getQualification());
            } else
                holder.txt2.setVisibility(View.GONE);
        }

        if (mdataset.get(position).getSpecialization() != null) {
            if (mdataset.get(position).getSpecialization().length() > 0) {
                holder.txt3.setVisibility(View.VISIBLE);
                holder.txt3.setText(mdataset.get(position).getSpecialization());
            } else holder.txt3.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(mdataset.get(position));
                } else {
                    GlobalValues.selectedoctor = mdataset.get(position);
                    ((Activity) mContext).finish();
                }
                //((Activitycommon) mContext).gotodocorlist();
            }
        });
        holder.recyclerview.setVisibility(View.GONE);
      /*  try {
            LinearLayoutManager mLayoutmanager = new LinearLayoutManager(mContext);
            holder.recyclerview.setLayoutManager(mLayoutmanager);
            ArrayList<Day> data=new ArrayList<>();
            ArrayList<Day> maindata=mdataset.get(position).getslots();
            if(maindata!=null)
            {
                for (int i=0;i<maindata.size();i++)
                {
                    if(maindata.get(i).getslots().size()>0)
                    {
                        data.add(maindata.get(i));
                    }
                }
            }

            DayAdapterv1 timeslotadapter = new DayAdapterv1(mContext, data);
            holder.recyclerview.setAdapter(timeslotadapter);
            holder.bt1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GlobalValues.selectedoctor = mdataset.get(position);
                   *//* ((Activitycommon) mContext).gotobookappointment();*//*
                }
            });
        } catch (Exception e) {
            Log.e("error ","error "+e.toString());
            e.printStackTrace();
        }*/
    }

    @Override
    public int getItemCount() {
        return mdataset.size();
    }

    public void filter(String s) {
        mdataset.clear();
        s = s.toUpperCase();
        if (s.length() == 0 || s == null || s.equalsIgnoreCase("null")) {
            mdataset.addAll(originaldata);
        } else {
            for (int i = 0; i < originaldata.size(); i++) {
                if (originaldata.get(i).getName().toUpperCase().contains(s))
                    mdataset.add(originaldata.get(i));
            }
        }
        notifyDataSetChanged();
    }

    public void departmentfilter(String s) {
        mdataset.clear();
        if (s.equalsIgnoreCase("All")) {
            mdataset.addAll(originaldata);
        } else {
            for (int i = 0; i < originaldata.size(); i++) {
                if (originaldata.get(i).getDepartment().equalsIgnoreCase(s))
                    mdataset.add(originaldata.get(i));
            }
        }
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(Doctor item);
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView txt1, txt2, txt3;
        RecyclerView recyclerview;
        Button bt1;

        public viewholder(View itemView) {
            super(itemView);
            txt1 = itemView.findViewById(R.id.textview_1);
            txt2 = itemView.findViewById(R.id.textview_2);
            txt3 = itemView.findViewById(R.id.textview_3);
            recyclerview = itemView.findViewById(R.id.recyclerview_1);
            bt1 = itemView.findViewById(R.id.button_1);
        }
    }
}
