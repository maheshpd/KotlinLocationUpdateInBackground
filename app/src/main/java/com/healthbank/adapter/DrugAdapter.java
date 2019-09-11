package com.healthbank.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.healthbank.R;
import com.healthbank.classes.Drug;

import java.util.ArrayList;

public class DrugAdapter extends RecyclerView.Adapter<DrugAdapter.viewHolder> {
    Context mContext;
    ArrayList<Drug> mdataset;
    int isprint = 0;

    public DrugAdapter(Context mContext, ArrayList<Drug> mdataset, int isprint) {
        this.mContext = mContext;
        this.mdataset = mdataset;
        this.isprint = isprint;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.list_item_drug, null);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, final int position) {
        Drug data = mdataset.get(position);
        holder.txt1.setText(data.getDrugName());// + " ("+data.getDrugUnit() + ")"
        holder.txt2.setVisibility(View.GONE);
        holder.txt3.setText(data.getBodyPart());
        holder.txt4.setText(data.getDoses());
        holder.txt5.setText(data.getDuration());
        holder.txt7.setText(data.getInstruction());
        if (isprint == 1) {
            holder.imgdelete.setVisibility(View.GONE);
        } else {
            holder.imgdelete.setVisibility(View.VISIBLE);
        }

        holder.imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdataset.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mdataset.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        public TextView name, description, price;
        public ImageView thumbnail, imgdelete;
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
            imgdelete = itemView.findViewById(R.id.img_delete);
        }
    }
}
