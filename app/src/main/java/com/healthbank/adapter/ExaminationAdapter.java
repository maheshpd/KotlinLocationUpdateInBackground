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
import com.healthbank.classes.ExaminationHeader;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by it1 on 8/9/2018.
 */

public class ExaminationAdapter extends RecyclerView.Adapter<ExaminationAdapter.viewHolder> {
    Context mContext;
    ArrayList<ExaminationHeader> mdataset;

    public ExaminationAdapter(Context mContext, ArrayList<ExaminationHeader> mdataset) {
        this.mContext = mContext;
        this.mdataset = mdataset;
    }

    @Override
    public ExaminationAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item_examinationdetails, parent, false);
        return new ExaminationAdapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(ExaminationAdapter.viewHolder holder, final int position) {
        Date date = DateUtils.parseDate(mdataset.get(position).getTitle(), "dd-MM-yyyy");
        holder.txt1.setText(DateUtils.formatDate(date, "dd MMM yyyy"));
        AdviceDataAdapter adapter1 = new AdviceDataAdapter(mContext, mdataset.get(position).getChiefcomplaints());
        LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(mContext);
        holder.mrecyclerview1.setLayoutManager(mLayoutManager1);
        holder.mrecyclerview1.setAdapter(adapter1);

        AdviceDataAdapter adapter2 = new AdviceDataAdapter(mContext, mdataset.get(position).getSymptoms());
        LinearLayoutManager mLayoutManager2 = new LinearLayoutManager(mContext);
        holder.mrecyclerview2.setLayoutManager(mLayoutManager2);
        holder.mrecyclerview2.setAdapter(adapter2);

        AdviceDataAdapter adapter3 = new AdviceDataAdapter(mContext, mdataset.get(position).getVaccines());
        LinearLayoutManager mLayoutManager3 = new LinearLayoutManager(mContext);
        holder.mrecyclerview3.setLayoutManager(mLayoutManager3);
        holder.mrecyclerview3.setAdapter(adapter3);

        AdviceDataAdapter adapter4 = new AdviceDataAdapter(mContext, mdataset.get(position).getAllergy());
        LinearLayoutManager mLayoutManager4 = new LinearLayoutManager(mContext);
        holder.mrecyclerview4.setLayoutManager(mLayoutManager4);
        holder.mrecyclerview4.setAdapter(adapter4);

        AdviceDataAdapter adapter5 = new AdviceDataAdapter(mContext, mdataset.get(position).getDiagnosis());
        LinearLayoutManager mLayoutManager5 = new LinearLayoutManager(mContext);
        holder.mrecyclerview5.setLayoutManager(mLayoutManager5);
        holder.mrecyclerview5.setAdapter(adapter5);

        holder.img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityCommon) mContext).createexaminationPdf(mdataset.get(position), 0);
            }
        });

        holder.img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityCommon) mContext).createexaminationPdf(mdataset.get(position), 1);
            }
        });

        holder.img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityCommon) mContext).createexaminationPdf(mdataset.get(position), 2);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mdataset.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView txt1;
        RecyclerView mrecyclerview1, mrecyclerview2, mrecyclerview3, mrecyclerview4, mrecyclerview5;
        ImageView img1, img2, img3;

        public viewHolder(View itemView) {
            super(itemView);
            txt1 = itemView.findViewById(R.id.textview_1);
            mrecyclerview1 = itemView.findViewById(R.id.recyclerview_1);
            mrecyclerview2 = itemView.findViewById(R.id.recyclerview_2);
            mrecyclerview3 = itemView.findViewById(R.id.recyclerview_3);
            mrecyclerview4 = itemView.findViewById(R.id.recyclerview_4);
            mrecyclerview5 = itemView.findViewById(R.id.recyclerview_5);
            img1 = itemView.findViewById(R.id.imageview_1);
            img2 = itemView.findViewById(R.id.imageview_2);
            img3 = itemView.findViewById(R.id.imageview_3);
        }
    }
}
