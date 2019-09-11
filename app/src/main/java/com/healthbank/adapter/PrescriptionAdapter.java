package com.healthbank.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.healthbank.Fonter;
import com.healthbank.R;
import com.healthbank.classes.Drug;

import java.util.ArrayList;

/**
 * Created by it1 on 6/1/2018.
 */

public class PrescriptionAdapter extends RecyclerView.Adapter<PrescriptionAdapter.viewHolder> {
    Context mContext;
    ArrayList<Drug> mdataset;

    public PrescriptionAdapter(Context mContext, ArrayList<Drug> mdataset) {
        this.mContext = mContext;
        this.mdataset = mdataset;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.list_item_prescription, parent, false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        Drug data = mdataset.get(position);
        holder.txt1.setTypeface(Fonter.getTypefaceregular(mContext));
        holder.txt2.setTypeface(Fonter.getTypefaceregular(mContext));
        holder.txt3.setTypeface(Fonter.getTypefaceregular(mContext));
        holder.txt4.setTypeface(Fonter.getTypefaceregular(mContext));
        holder.txt5.setTypeface(Fonter.getTypefaceregular(mContext));
        holder.txt7.setTypeface(Fonter.getTypefacebold(mContext));

        holder.txt1.setText(data.getDrugName());//+ " (" + data.getDrugQuantity() + data.getDrugUnit() + ")"
        holder.txt2.setText(data.getBrand());
        holder.txt3.setText(data.getBodyPart());
        holder.txt4.setText(data.getDoses());
        holder.txt5.setText(data.getDuration());
        holder.txt7.setText(data.getInstruction());
    }

    @Override
    public int getItemCount() {
        return mdataset.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        public TextView name, description, price;
        public ImageView thumbnail;
        public RelativeLayout viewBackground, viewForeground;
        TextView txt1, txt2, txt3, txt4, txt5, txt6, txt7;

        public viewHolder(View itemView) {
            super(itemView);
            txt1 = itemView.findViewById(R.id.textview_1);
            txt2 = itemView.findViewById(R.id.textview_2);
            txt3 = itemView.findViewById(R.id.textview_3);
            txt4 = itemView.findViewById(R.id.textview_4);
            txt5 = itemView.findViewById(R.id.textview_5);
            txt6 = itemView.findViewById(R.id.textview_6);
            txt7 = itemView.findViewById(R.id.textview_7);
            viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.view_foreground);
        }
    }
}
