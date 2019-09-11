package com.healthbank.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.healthbank.R;
import com.healthbank.classes.Radiology;

import java.util.ArrayList;

/**
 * Created by it1 on 8/20/2018.
 */

public class RadiologyAdapter extends RecyclerView.Adapter<RadiologyAdapter.viewHolder> {
    Context mContext;
    ArrayList<Radiology> mdataset;

    public RadiologyAdapter(Context mContext, ArrayList<Radiology> mdataset) {
        this.mContext = mContext;
        this.mdataset = mdataset;
    }

    @Override
    public RadiologyAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item_radiology, parent, false);
        return new RadiologyAdapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(RadiologyAdapter.viewHolder holder, int position) {
        holder.txt1.setText(mdataset.get(position).getTitle());
        holder.txt2.setText(mdataset.get(position).getResult());
        //  holder.txt3.setText(mdataset.get(position).getNormal());
    }

    @Override
    public int getItemCount() {
        return mdataset.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView txt1, txt2;

        public viewHolder(View itemView) {
            super(itemView);
            txt1 = itemView.findViewById(R.id.textview_1);
            txt2 = itemView.findViewById(R.id.textview_2);

        }
    }
}

