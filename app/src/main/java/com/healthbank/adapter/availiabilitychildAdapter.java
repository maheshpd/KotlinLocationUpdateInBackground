package com.healthbank.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthbank.AvailabilityActivity;
import com.healthbank.R;
import com.healthbank.classes.AvailiabilityChildData;

import java.util.ArrayList;

/**
 * Created by it1 on 6/6/2018.
 */

public class availiabilitychildAdapter extends RecyclerView.Adapter<availiabilitychildAdapter.viewHolder> {
    Context mContext;
    ArrayList<AvailiabilityChildData> mdatset;
    int parentpos;
    int childposition;

    public availiabilitychildAdapter(Context mContext, ArrayList<AvailiabilityChildData> mdatset, int parentpos, int childposition) {
        this.mContext = mContext;
        this.mdatset = mdatset;
        this.parentpos = parentpos;
        this.childposition = childposition;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.list_itemchilldavailiability, parent, false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(final viewHolder holder, final int position) {
        holder.txt1.setText(mdatset.get(position).getName());
        holder.txt2.setText(mdatset.get(position).getFromtime() + " - " + mdatset.get(position).getTotime());
        holder.img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AvailabilityActivity) mContext).update(parentpos, childposition, position);
            }
        });

        holder.img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AvailabilityActivity) mContext).delete(parentpos, childposition, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mdatset.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView txt1, txt2;
        ImageView img1, img2;

        public viewHolder(View itemView) {
            super(itemView);
            txt1 = itemView.findViewById(R.id.textview_1);
            txt2 = itemView.findViewById(R.id.textview_2);
            img1 = itemView.findViewById(R.id.imageview_1);
            img2 = itemView.findViewById(R.id.imageview_2);
        }
    }
}
