package com.healthbank.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthbank.ActivityCommon;
import com.healthbank.R;
import com.healthbank.classes.HomeData;

import java.util.ArrayList;

/**
 * Created by it1 on 6/8/2018.
 */

public class PatientDetailsAdapter extends RecyclerView.Adapter<PatientDetailsAdapter.viewHolder> {
    ArrayList<HomeData> mdataset;
    Context mContext;

    public PatientDetailsAdapter(Context mContext, ArrayList<HomeData> mdataset) {
        this.mContext = mContext;
        this.mdataset = mdataset;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_patientdetails, parent, false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, final int position) {
        try {
            final HomeData data = mdataset.get(position);
            holder.cardView.setCardBackgroundColor(mContext.getResources().getColor(data.getBackgroundcolor()));
            holder.txt.setText(data.getVisiblename());
            holder.img.setImageDrawable(mContext.getResources().getDrawable(data.getId()));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((ActivityCommon) mContext).gotonext(data.getTemplate_group());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mdataset.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView txt;
        ImageView img;

        public viewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardview_1);
            txt = itemView.findViewById(R.id.textview_1);
            img = itemView.findViewById(R.id.imageview_1);
        }
    }

}
