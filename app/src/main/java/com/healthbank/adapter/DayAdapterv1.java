package com.healthbank.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.healthbank.R;
import com.healthbank.classes.Day;

import java.util.ArrayList;

public class DayAdapterv1 extends RecyclerView.Adapter<DayAdapterv1.viewHolder> {
    Context mContext;
    ArrayList<Day> mdataset;

    public DayAdapterv1(Context mContext, ArrayList<Day> mdataset) {
        this.mContext = mContext;
        this.mdataset = mdataset;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.list_item_day, parent, false);
        return new DayAdapterv1.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, final int position) {
        try {
            holder.txt1.setText(mdataset.get(position).getDayName());
            GridLayoutManager mLayoutmanager = new GridLayoutManager(mContext, 3);
            holder.recyclerview.setLayoutManager(mLayoutmanager);


            Timeslotadapter timeslotadapter = new Timeslotadapter(mContext, mdataset.get(position).getslots());
            holder.recyclerview.setAdapter(timeslotadapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mdataset.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerview;
        TextView txt1;

        public viewHolder(View itemView) {
            super(itemView);
            recyclerview = itemView.findViewById(R.id.recyclerview_1);
            txt1 = itemView.findViewById(R.id.textview_1);
        }
    }

}
