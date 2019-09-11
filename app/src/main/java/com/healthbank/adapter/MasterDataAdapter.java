package com.healthbank.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.healthbank.ActivityCommon;
import com.healthbank.R;

import java.util.ArrayList;

/**
 * Created by it1 on 6/29/2018.
 */

public class MasterDataAdapter extends RecyclerView.Adapter<MasterDataAdapter.viewHolder> {
    ArrayList<String> mdataset;
    Context mContext;

    public MasterDataAdapter(Context mContext, ArrayList<String> mdataset) {
        this.mdataset = mdataset;
        this.mContext = mContext;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.list_item_mastername, parent, false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, final int position) {
        holder.txt.setText(mdataset.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityCommon) mContext).editmaster(position, mdataset.get(position));
            }
        });
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
