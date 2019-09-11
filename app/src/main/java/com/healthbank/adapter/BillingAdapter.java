package com.healthbank.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.healthbank.R;
import com.healthbank.classes.Advicemasterdata;

import java.util.ArrayList;

/**
 * Created by it1 on 6/26/2018.
 */

public class BillingAdapter extends RecyclerView.Adapter<BillingAdapter.viewHolder> {
    ArrayList<Advicemasterdata> mdataset;
    Context mContext;

    public BillingAdapter(Context mContext, ArrayList<Advicemasterdata> mdataset) {
        this.mdataset = mdataset;
        this.mContext = mContext;
    }

    @Override
    public BillingAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.list_item_billing, parent, false);
        return new BillingAdapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(BillingAdapter.viewHolder holder, final int position) {
        holder.txt1.setText(mdataset.get(position).getTitle());
        holder.txt2.setText(mContext.getResources().getString(R.string.char_rupees) + "" + mdataset.get(position).getAmount());
        holder.txt3.setText("" + 0);
        Double price = Double.parseDouble(mdataset.get(position).getAmount()) * 1;
        holder.txt4.setText(mContext.getResources().getString(R.string.char_rupees) + "" + price);
    }

    @Override
    public int getItemCount() {
        return mdataset.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView txt1, txt2, txt3, txt4;

        public viewHolder(View itemView) {
            super(itemView);
            txt1 = itemView.findViewById(R.id.textview_1);
            txt2 = itemView.findViewById(R.id.textview_2);
            txt3 = itemView.findViewById(R.id.textview_3);
            txt4 = itemView.findViewById(R.id.textview_4);
        }
    }
}
