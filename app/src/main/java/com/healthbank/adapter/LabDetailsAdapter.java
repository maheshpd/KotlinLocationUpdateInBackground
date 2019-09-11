package com.healthbank.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthbank.ActivityCommon;
import com.healthbank.DateUtils;
import com.healthbank.R;
import com.healthbank.classes.LabHeader;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by it1 on 8/8/2018.
 */

public class LabDetailsAdapter extends RecyclerView.Adapter<LabDetailsAdapter.viewHolder> {
    Context mContext;
    ArrayList<LabHeader> mdataset;

    public LabDetailsAdapter(Context mContext, ArrayList<LabHeader> mdataset) {
        this.mContext = mContext;
        this.mdataset = mdataset;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item_labdetails, parent, false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, final int position) {
        Date date = DateUtils.parseDate(mdataset.get(position).getTitle(), "dd-MM-yyyy");
        holder.txt1.setText(DateUtils.formatDate(date, "dd MMM yyyy"));
        LabTestsAdapter adapter = new LabTestsAdapter(mContext, mdataset.get(position).getMdataset());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        holder.mrecyclerview1.setLayoutManager(mLayoutManager);
        holder.mrecyclerview1.setAdapter(adapter);
        LinearLayoutManager mLinearlayoutmanager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        holder.mrecyclerview2.setLayoutManager(mLinearlayoutmanager);
        AdapterImages adapterimages = new AdapterImages(mContext, mdataset.get(position).getImagedata(), false);
        holder.mrecyclerview2.setAdapter(adapterimages);

        holder.img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityCommon) mContext).createLabPdf(mdataset.get(position), 0);
            }
        });

        holder.img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityCommon) mContext).createLabPdf(mdataset.get(position), 1);
            }
        });

        holder.img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityCommon) mContext).createLabPdf(mdataset.get(position), 2);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mdataset.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView txt1;
        RecyclerView mrecyclerview1, mrecyclerview2;
        ImageView img1, img2, img3;

        public viewHolder(View itemView) {
            super(itemView);
            txt1 = itemView.findViewById(R.id.textview_1);
            mrecyclerview1 = itemView.findViewById(R.id.recyclerview_1);
            mrecyclerview2 = itemView.findViewById(R.id.recyclerview_2);

            img1 = itemView.findViewById(R.id.imageview_1);
            img2 = itemView.findViewById(R.id.imageview_2);
            img3 = itemView.findViewById(R.id.imageview_3);
        }
    }
}
