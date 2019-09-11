package com.healthbank.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.healthbank.R;
import com.healthbank.classes.OfflineData;

import java.util.ArrayList;

public class OfflinedataAdapter extends RecyclerView.Adapter<OfflinedataAdapter.ViewHolder> {
    ArrayList<OfflineData> mdataset;
    Context mContext;

    public OfflinedataAdapter(Context mContext, ArrayList<OfflineData> mdataset) {
        this.mContext = mContext;
        this.mdataset = mdataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.list_item_offlinedata, parent, false);
        return new OfflinedataAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txt1.setText(mdataset.get(position).getName());
        holder.txt2.setText(Integer.toString(mdataset.get(position).getCount()));
        int COUNTS = mdataset.get(position).getCount();
        if (COUNTS > 0) {
            holder.txt2.setVisibility(View.VISIBLE);
            holder.txt2.setText("" + COUNTS);
            holder.txt2.measure(0, 0);
            int width = holder.txt2.getMeasuredWidth();
            int height = holder.txt2.getMeasuredHeight();
            if (width > height) {
                height = width;
            } else {
                width = height;
            }
            holder.txt2.setWidth(width);
            holder.txt2.setHeight(width);
            holder.txt2.setGravity(Gravity.CENTER);
        } else {
            holder.txt2.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mdataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt1, txt2;

        public ViewHolder(View itemView) {
            super(itemView);
            txt1 = itemView.findViewById(R.id.textview_1);
            txt2 = itemView.findViewById(R.id.textview_2);

        }
    }
}
