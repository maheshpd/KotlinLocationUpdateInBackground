package com.healthbank.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.healthbank.R;
import com.healthbank.classes.MasterData;

import java.util.ArrayList;

/**
 * Created by it1 on 6/29/2018.
 */

public class MasterEditAdapter extends RecyclerView.Adapter<MasterEditAdapter.viewHolder> {
    Context mContext;
    ArrayList<MasterData> mdataset;

    public MasterEditAdapter(Context mContext, ArrayList<MasterData> mdataset) {
        this.mContext = mContext;
        this.mdataset = mdataset;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater infaInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = infaInflater.inflate(R.layout.list_item_mastername, parent, false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        holder.txt.setText(mdataset.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mdataset.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView txt;

        public viewHolder(View itemView) {
            super(itemView);
            txt = itemView.findViewById(R.id.textview_1);
        }
    }
}
