package com.healthbank.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthbank.HomeActivity;
import com.healthbank.R;
import com.healthbank.classes.HomeData;

import java.util.ArrayList;

/**
 * Created by it1 on 5/31/2018.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.viewHolder> {
    Context mContext;
    ArrayList<HomeData> mdataset = new ArrayList<>();

    public HomeAdapter(Context mContext, ArrayList<HomeData> mdataset) {
        this.mdataset = mdataset;
        this.mContext = mContext;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.list_item_home, parent, false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, final int position) {
        holder.textView.setText(mdataset.get(position).getName());
        holder.imageview.setImageDrawable(mContext.getResources().getDrawable(mdataset.get(position).getId()));
        holder.imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HomeActivity) mContext).gotonext(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mdataset.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView imageview;
        TextView textView;

        public viewHolder(View itemView) {
            super(itemView);
            imageview = itemView.findViewById(R.id.imageview_1);
            textView = itemView.findViewById(R.id.textview_1);
        }
    }

}
