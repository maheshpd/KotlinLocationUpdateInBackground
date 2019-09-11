package com.healthbank.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.healthbank.R;
import com.healthbank.classes.timingslots;

import java.util.ArrayList;

public class Timeslotadapter extends RecyclerView.Adapter<Timeslotadapter.viewholder> {
    Context mContext;
    ArrayList<timingslots> mdataset;

    public Timeslotadapter(Context mContext, ArrayList<timingslots> mdataset) {
        this.mContext = mContext;
        this.mdataset = mdataset;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.list_item_timeslot, parent, false);
        return new viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
  /*      holder.txt1.setTypeface(Fonter.getTypefacebold(mContext));
        holder.txt2.setTypeface(Fonter.getTypefaceregular(mContext));*/
        holder.txt1.setText(mdataset.get(position).getDayName().substring(0, 3) + " " + mdataset.get(position).getStartTime() + "-" + mdataset.get(position).getEndTime());
        //holder.txt2.setText(mdataset.get(position).getStartTime() + "-" + mdataset.get(position).getEndTime());
        holder.txt2.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return mdataset.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView txt1, txt2;

        public viewholder(View itemView) {
            super(itemView);
            txt1 = itemView.findViewById(R.id.textview_1);
            txt2 = itemView.findViewById(R.id.textview_2);
        }
    }
}
