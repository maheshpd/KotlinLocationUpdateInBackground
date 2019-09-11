package com.healthbank.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.healthbank.R;
import com.healthbank.classes.Vitalsdata;

import java.util.ArrayList;

/**
 * Created by it1 on 6/1/2018.
 */

public class VitalAdapter extends RecyclerView.Adapter<VitalAdapter.viewHolder> {
    Context mContext;
    ArrayList<Vitalsdata> mdataset;

    public VitalAdapter(Context mContext, ArrayList<Vitalsdata> mdataset) {
        this.mContext = mContext;
        this.mdataset = mdataset;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater minflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = minflater.inflate(R.layout.list_item_vital, parent, false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mdataset.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        public viewHolder(View itemView) {
            super(itemView);
        }
    }
}
