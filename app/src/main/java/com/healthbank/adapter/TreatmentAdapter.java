package com.healthbank.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.healthbank.R;
import com.healthbank.classes.Advicemasterdata;

import java.util.ArrayList;

public class TreatmentAdapter extends RecyclerView.Adapter<TreatmentAdapter.viewHolder> {
    ArrayList<Advicemasterdata> mdataset;
    Context mContext;

    public TreatmentAdapter(Context mContext, ArrayList<Advicemasterdata> mdataset) {
        this.mdataset = mdataset;
        this.mContext = mContext;
    }

    @Override
    public TreatmentAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.list_item_procedure, parent, false);
        return new TreatmentAdapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(TreatmentAdapter.viewHolder holder, final int position) {
        holder.cb.setText(mdataset.get(position).getTitle());
        holder.txt.setText(mContext.getResources().getString(R.string.char_rupees) + "" + mdataset.get(position).getAmount());
        holder.cb.setChecked(mdataset.get(position).isIsselected());
        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mdataset.get(position).setIsselected(b);
                // updatedata();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mdataset.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        CheckBox cb;
        TextView txt;

        public viewHolder(View itemView) {
            super(itemView);
            cb = itemView.findViewById(R.id.checkbox_1);
            txt = itemView.findViewById(R.id.textview_1);
        }
    }
}