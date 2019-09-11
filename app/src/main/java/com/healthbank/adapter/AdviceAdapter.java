package com.healthbank.adapter;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthbank.ActivityCommon;
import com.healthbank.AdviceFragment;
import com.healthbank.DateUtils;
import com.healthbank.R;
import com.healthbank.classes.AdviceHeader;

import java.util.ArrayList;
import java.util.Date;

public class AdviceAdapter extends RecyclerView.Adapter<AdviceAdapter.viewHolder> {
    Context mContext;
    ArrayList<AdviceHeader> mdataset;
    AdviceFragment fragment;

    public AdviceAdapter(Context mContext, ArrayList<AdviceHeader> mdataset, AdviceFragment fragment) {
        this.mContext = mContext;
        this.mdataset = mdataset;
        this.fragment = fragment;

    }

    @Override
    public AdviceAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item_advicedetails, parent, false);
        return new AdviceAdapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdviceAdapter.viewHolder holder, final int position) {
        Date date = DateUtils.parseDate(mdataset.get(position).getTitle(), "dd-MM-yyyy");
        holder.txt1.setText(DateUtils.formatDate(date, "dd MMM yyyy"));
        AdviceDataAdapter adapter = new AdviceDataAdapter(mContext, mdataset.get(position).getMdataset());//,fragment
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        holder.mrecyclerview.setLayoutManager(mLayoutManager);
        holder.mrecyclerview.setAdapter(adapter);

        holder.img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityCommon) mContext).createadvicePdf(mdataset.get(position), 0);
            }
        });

        holder.img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityCommon) mContext).createadvicePdf(mdataset.get(position), 1);
            }
        });

        holder.img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityCommon) mContext).createadvicePdf(mdataset.get(position), 2);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("click", "click " + mdataset.get(position).getTitle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mdataset.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView txt1;
        RecyclerView mrecyclerview;
        ImageView img1, img2, img3;

        public viewHolder(View itemView) {
            super(itemView);
            txt1 = itemView.findViewById(R.id.textview_1);
            mrecyclerview = itemView.findViewById(R.id.recyclerview_1);
            img1 = itemView.findViewById(R.id.imageview_1);
            img2 = itemView.findViewById(R.id.imageview_2);
            img3 = itemView.findViewById(R.id.imageview_3);
        }
    }
}
