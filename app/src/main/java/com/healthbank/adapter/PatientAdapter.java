package com.healthbank.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthbank.ActivityCommon;
import com.healthbank.GlobalValues;
import com.healthbank.R;
import com.healthbank.classes.Patient;

import java.util.ArrayList;

/**
 * Created by it1 on 6/5/2018.
 */
public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.viewholder> {
    Context mContext;
    ArrayList<Patient> mdataset;
    ArrayList<Patient> originalData = new ArrayList<>();

    public PatientAdapter(Context mContext, ArrayList<Patient> mdataset) {
        this.mContext = mContext;
        this.mdataset = mdataset;
        this.originalData.addAll(mdataset);
    }

    @Override
    public viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.list_item_patient, parent, false);
        return new viewholder(v);
    }

    @Override
    public void onBindViewHolder(viewholder holder, final int position) {
        if (mdataset.get(position).getFirstName().length() > 2)
            holder.txt1.setText(mdataset.get(position).getFirstName().substring(0, 1).toUpperCase());
        holder.txt2.setText(mdataset.get(position).getFirstName());
        holder.txt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalValues.selectedpt = mdataset.get(position);
                ((ActivityCommon) mContext).gotoptdetails();
            }
        });

        holder.img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalValues.selectedpt = mdataset.get(position);
                ((ActivityCommon) mContext).gotoptdetails();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalValues.selectedpt = mdataset.get(position);
                ((ActivityCommon) mContext).gotoptdetails();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mdataset.size();
    }

    public void filterData(String query) {
        query = query.toLowerCase();
        mdataset.clear();
        if (query.isEmpty()) {
            mdataset.addAll(originalData);
        } else {
            for (Patient data : originalData) {
                if (data.getFirstName().toLowerCase().contains(query)) {
                    mdataset.add(data);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView txt1, txt2, txt3;
        ImageView img1;

        public viewholder(View itemView) {
            super(itemView);
            txt1 = itemView.findViewById(R.id.textview_1);
            txt2 = itemView.findViewById(R.id.textview_2);
            txt3 = itemView.findViewById(R.id.textview_3);
            img1 = itemView.findViewById(R.id.imageview_1);
        }
    }
}
