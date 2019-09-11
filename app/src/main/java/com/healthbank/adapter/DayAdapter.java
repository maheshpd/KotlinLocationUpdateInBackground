package com.healthbank.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.healthbank.AvailabilityActivity;
import com.healthbank.R;
import com.healthbank.classes.AvailiabilityDayData;

import java.util.ArrayList;

/**
 * Created by it1 on 6/6/2018.
 */

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.viewholder> {
    Context mContext;
    ArrayList<AvailiabilityDayData> mdataset;

    public DayAdapter(Context mContext, ArrayList<AvailiabilityDayData> mdataset) {
        this.mContext = mContext;
        this.mdataset = mdataset;
    }

    @Override
    public viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.list_item_day, parent, false);
        return new viewholder(v);
    }

    @Override
    public void onBindViewHolder(viewholder holder, final int position) {
        holder.txt1.setText(mdataset.get(position).getName());
        if (mdataset.get(position).isIsselected()) {
            holder.txt1.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
            holder.txt1.setTextColor(mContext.getResources().getColor(R.color.White));
        } else {
            holder.txt1.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.edittextshape_grayborder));
            holder.txt1.setTextColor(mContext.getResources().getColor(R.color.textcolor));
        }

        holder.txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < mdataset.size(); i++) {
                    mdataset.get(i).setIsselected(false);
                }
                mdataset.get(position).setIsselected(true);
                notifyDataSetChanged();
                ((AvailabilityActivity) mContext).updateadapter();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mdataset.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView txt1;

        public viewholder(View itemView) {
            super(itemView);
            txt1 = itemView.findViewById(R.id.textview_1);
        }
    }
}
