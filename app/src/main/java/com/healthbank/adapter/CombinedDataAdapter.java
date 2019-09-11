package com.healthbank.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthbank.R;
import com.healthbank.classes.Combineddata;

import java.util.ArrayList;

public class CombinedDataAdapter extends RecyclerView.Adapter<CombinedDataAdapter.viewHolder> {
    private final CombinedDataAdapter.OnItemClickListener listener;
    Context mContext;
    ArrayList<Combineddata> mdataset;
    int isprint = 0;

    public CombinedDataAdapter(Context mContext, ArrayList<Combineddata> mdataset, int isprint, CombinedDataAdapter.OnItemClickListener listener) {
        this.mContext = mContext;
        this.mdataset = mdataset;
        this.isprint = isprint;
        this.listener = listener;
    }

    public CombinedDataAdapter(Context mContext, ArrayList<Combineddata> mdataset, int isprint) {
        this.mContext = mContext;
        this.mdataset = mdataset;
        this.isprint = isprint;
        this.listener = null;
    }

    @Override
    public CombinedDataAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item_test, parent, false);
        return new CombinedDataAdapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(CombinedDataAdapter.viewHolder holder, final int position) {
        holder.txt1.setText(mdataset.get(position).getName() + " - " + mdataset.get(position).getBodypart());
        if (isprint == 1) {
            holder.img1.setVisibility(View.GONE);
        } else {
            holder.img1.setVisibility(View.VISIBLE);
        }

        holder.img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mdataset.remove(position);
                notifyDataSetChanged();
                if (listener != null)
                    listener.onItemClick();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mdataset.size();
    }

    public interface OnItemClickListener {
        void onItemClick();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView txt1;
        ImageView img1;

        public viewHolder(View itemView) {
            super(itemView);
            txt1 = itemView.findViewById(R.id.textview_1);
            img1 = itemView.findViewById(R.id.imageview_1);
        }
    }
}
