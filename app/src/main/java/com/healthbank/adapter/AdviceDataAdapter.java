package com.healthbank.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.healthbank.R;
import com.healthbank.classes.Advice;

import java.util.ArrayList;

/**
 * Created by it1 on 8/9/2018.
 */

public class AdviceDataAdapter extends RecyclerView.Adapter<AdviceDataAdapter.viewHolder> {
    Context mContext;
    ArrayList<Advice> mdataset;
    //   AdviceFragment fragment;

    /*   public AdviceDataAdapter(Context mContext, ArrayList<Advice> mdataset, AdviceFragment fragment) {
           this.mContext = mContext;
           this.mdataset = mdataset;
           this.fragment = fragment;
       }*/
    public AdviceDataAdapter(Context mContext, ArrayList<Advice> mdataset) {
        this.mContext = mContext;
        this.mdataset = mdataset;
    }

    @Override
    public AdviceDataAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item_advicedata, parent, false);
        return new AdviceDataAdapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdviceDataAdapter.viewHolder holder, final int position) {
        holder.txt1.setText(mdataset.get(position).getName());
        if (mdataset.get(position).getAmount().length() > 0 && !mdataset.get(position).getAmount().equalsIgnoreCase("0"))
            holder.txt2.setText(mContext.getResources().getString(R.string.char_rupees) + mdataset.get(position).getAmount());
        else holder.txt2.setText("");

        if (mdataset.get(position).isIspaid() || mdataset.get(position).getAmount().length() <= 0)
            holder.cb1.setVisibility(View.GONE);
        else
            holder.cb1.setVisibility(View.VISIBLE);

        holder.cb1.setChecked(mdataset.get(position).isIsselected());

        holder.cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int flag = 1;
                if (b) {
                    flag = 0;
                }
                mdataset.get(position).setIsselected(b);
                notifyDataSetChanged();
                //fragment.updatecost(Long.parseLong(mdataset.get(position).getAmount()), flag);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mdataset.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView txt1, txt2;
        CheckBox cb1;

        public viewHolder(View itemView) {
            super(itemView);
            txt1 = itemView.findViewById(R.id.textview_1);
            txt2 = itemView.findViewById(R.id.textview_2);
            cb1 = itemView.findViewById(R.id.checkbox_1);
        }
    }
}