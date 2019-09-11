package com.healthbank.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthbank.R;
import com.healthbank.RxActivity;

import java.util.ArrayList;

/**
 * Created by it1 on 6/13/2018.
 */

public class labAdapter extends RecyclerView.Adapter<labAdapter.viewHolder> {
    ArrayList<String> mdataset = new ArrayList<>();
    Context mContext;
    boolean isdeletevisible = false;

    public labAdapter(Context mContext, ArrayList<String> mdataset, boolean isdeletevisible) {
        this.mContext = mContext;
        this.mdataset = mdataset;
        this.isdeletevisible = isdeletevisible;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflate = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.list_item_lab, parent, false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, final int position) {
        holder.txt.setText(mdataset.get(position));
        if (!isdeletevisible)
            holder.img.setVisibility(View.GONE);
        else
            holder.img.setVisibility(View.VISIBLE);

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mdataset.remove(position);
                notifyDataSetChanged();
                ((RxActivity) mContext).notifydatasetchanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mdataset.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView txt;
        ImageView img;

        public viewHolder(View itemView) {
            super(itemView);
            txt = itemView.findViewById(R.id.textview_1);
            img = itemView.findViewById(R.id.imageview_1);
        }
    }
}
