package com.healthbank.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.healthbank.R;
import com.healthbank.classes.BillingDetails;

import java.util.ArrayList;

public class PartialPaidBillAdapter extends RecyclerView.Adapter<PartialPaidBillAdapter.viewHolder> {
    ArrayList<BillingDetails> mdataset;
    Context mContext;
    String rupee = "";

    public PartialPaidBillAdapter(ArrayList<BillingDetails> mdataset, Context mContext) {
        this.mdataset = mdataset;
        this.mContext = mContext;
        rupee = mContext.getResources().getString(R.string.char_rupees);
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lilst_item_partialbillingdetails, parent, false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        holder.txt1.setText(mdataset.get(position).getItem());
        Double totalCost = Double.parseDouble(mdataset.get(position).getUnit()) * Double.parseDouble(mdataset.get(position).getUnitcost());
        holder.txt2.setText(mdataset.get(position).getUnit() + " x " + mdataset.get(position).getUnitcost());
        holder.txt3.setText(rupee + totalCost);
    }

    @Override
    public int getItemCount() {
        return mdataset.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView txt1, txt2, txt3;

        public viewHolder(View itemView) {
            super(itemView);
            txt1 = itemView.findViewById(R.id.textview_1);
            txt2 = itemView.findViewById(R.id.textview_2);
            txt3 = itemView.findViewById(R.id.textview_3);
        }
    }
}
