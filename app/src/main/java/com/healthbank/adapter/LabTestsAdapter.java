package com.healthbank.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.healthbank.R;
import com.healthbank.classes.Lab;

import java.util.ArrayList;

/**
 * Created by it1 on 8/9/2018.
 */
public class LabTestsAdapter extends RecyclerView.Adapter<LabTestsAdapter.viewHolder> {
    Context mContext;
    ArrayList<Lab> mdataset;

    public LabTestsAdapter(Context mContext, ArrayList<Lab> mdataset) {
        this.mContext = mContext;
        this.mdataset = mdataset;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item_labtest, parent, false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        holder.txt1.setText(mdataset.get(position).getTest());
        holder.txt2.setText(mdataset.get(position).getResult());
        holder.txt3.setText(mdataset.get(position).getNormal());
    }

    @Override
    public int getItemCount() {
        return mdataset.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView txt1, txt2, txt3;

        public viewHolder(View itemView) {
            super(itemView);
            txt1 = itemView.findViewById(R.id.textview_1);
            txt2 = itemView.findViewById(R.id.textview_2);
            txt3 = itemView.findViewById(R.id.textview_3);
        }
    }
}
