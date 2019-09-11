package com.healthbank.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.healthbank.R;
import com.healthbank.classes.BillBookData;

import java.util.ArrayList;

/**
 * Created by it1 on 6/25/2018.
 */

public class BillBookAdapter extends RecyclerView.Adapter<BillBookAdapter.viewHolder> {
    ArrayList<BillBookData> mdataset;
    Context mContext;

    public BillBookAdapter(Context mContext, ArrayList<BillBookData> mdataset) {
        this.mContext = mContext;
        this.mdataset = mdataset;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.list_item_billbook, parent, false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        holder.cb.setText(mdataset.get(position).getDrname());
    }

    @Override
    public int getItemCount() {
        return mdataset.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        CheckBox cb;

        public viewHolder(View itemView) {
            super(itemView);
            cb = itemView.findViewById(R.id.checkbox_1);
        }
    }
}
