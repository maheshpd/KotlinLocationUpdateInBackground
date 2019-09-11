package com.healthbank.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.healthbank.R;
import com.healthbank.classes.Master;

import java.util.ArrayList;

public class MasterAdapter extends RecyclerView.Adapter<MasterAdapter.viewHolder> {
    private final OnItemClickListener listener;
    ArrayList<Master> mdataset = new ArrayList<>();
    ArrayList<Master> originaldata;
    Context mContext;

    public MasterAdapter(Context mContext, ArrayList<Master> mdataset, OnItemClickListener listener) {
        this.originaldata = mdataset;
        this.mdataset.addAll(originaldata);
        this.mContext = mContext;
        this.listener = listener;
    }

    @Override
    public MasterAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.list_item_mastername, parent, false);
        return new MasterAdapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(MasterAdapter.viewHolder holder, final int position) {
        holder.txt.setText(mdataset.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(mdataset.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mdataset.size();
    }

    public void filterdata(String s) {
        mdataset.clear();
        if (s.length() == 0) {
            mdataset.addAll(originaldata);
        } else {
            String[] data = s.split(" ");
            for (int j = 0; j < data.length; j++) {
                for (int i = 0; i < originaldata.size(); i++) {
                    if (originaldata.get(i).getName().toUpperCase().contains(data[j].toUpperCase())) {
                        mdataset.add(originaldata.get(i));
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(Master item);
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView txt;

        public viewHolder(View itemView) {
            super(itemView);
            txt = itemView.findViewById(R.id.textview_1);
        }
    }
}
