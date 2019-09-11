package com.healthbank.adapter;

import android.content.Context;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.healthbank.AvailabilityActivity;
import com.healthbank.R;
import com.healthbank.classes.AvailabilityData;

import java.util.ArrayList;

public class AvailiabilityAdapter extends RecyclerView.Adapter<AvailiabilityAdapter.viewHolder> {
    Context mContext;
    ArrayList<AvailabilityData> mdataset;

    public AvailiabilityAdapter(Context mContext, ArrayList<AvailabilityData> mdataset) {
        this.mContext = mContext;
        this.mdataset = mdataset;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater minflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = minflater.inflate(R.layout.list_item_availiability, parent, false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, final int position) {
        DayAdapter adapter1 = new DayAdapter(mContext, mdataset.get(position).getDay());
        holder.mrecyclerview1.setLayoutManager(new GridLayoutManager(mContext, 7));
        holder.mrecyclerview1.setAdapter(adapter1);
        LinearLayoutManager mlayoutmanager = new LinearLayoutManager(mContext);
        holder.mrecyclerview2.setLayoutManager(mlayoutmanager);
        if (mdataset.get(position).getDay().size() > position) {
            availiabilitychildAdapter adapter = new availiabilitychildAdapter(mContext, mdataset.get(position).getDay().get(mdataset.get(position).getselectedpos()).getMadaset(), position, mdataset.get(position).getselectedpos());
            holder.mrecyclerview2.setAdapter(adapter);
        }

        holder.b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AvailabilityActivity) mContext).opendialog(position, mdataset.get(position).getselectedpos());
            }
        });
    }


    @Override
    public int getItemCount() {
        return mdataset.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        RecyclerView mrecyclerview1;
        RecyclerView mrecyclerview2;
        Button b1;

        public viewHolder(View itemView) {
            super(itemView);
            mrecyclerview1 = itemView.findViewById(R.id.recyclerview_1);
            mrecyclerview2 = itemView.findViewById(R.id.recyclerview_2);
            b1 = itemView.findViewById(R.id.button_1);
        }
    }
}
