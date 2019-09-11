package com.healthbank.adapter;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthbank.ActivityCommon;
import com.healthbank.DateUtils;
import com.healthbank.R;
import com.healthbank.classes.RadiologyHeader;

import java.util.ArrayList;
import java.util.Date;

public class RadiologyDetailsAdapter extends RecyclerView.Adapter<RadiologyDetailsAdapter.viewHolder> {
    Context mContext;
    ArrayList<RadiologyHeader> mdataset;

    public RadiologyDetailsAdapter(Context mContext, ArrayList<RadiologyHeader> mdataset) {
        this.mContext = mContext;
        this.mdataset = mdataset;
    }

    @Override
    public RadiologyDetailsAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item_radiologydetails, parent, false);
        return new RadiologyDetailsAdapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(RadiologyDetailsAdapter.viewHolder holder, final int position) {
        Date date = DateUtils.parseDate(mdataset.get(position).getTitle(), "dd-MM-yyyy");
        holder.txt1.setText(DateUtils.formatDate(date, "dd MMM yyyy"));
        RadiologyAdapter adapter = new RadiologyAdapter(mContext, mdataset.get(position).getMdataset());
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
                ((ActivityCommon) mContext).createRadiologyPdf(mdataset.get(position), 0);
            }
        });

        holder.img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityCommon) mContext).createRadiologyPdf(mdataset.get(position), 1);
            }
        });

        holder.img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityCommon) mContext).createRadiologyPdf(mdataset.get(position), 2);
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

