package com.healthbank.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.healthbank.R;
import com.healthbank.classes.Advicemasterdata;

import java.util.ArrayList;

/**
 * Created by it1 on 6/25/2018.
 */
public class ProcedureAdapter extends RecyclerView.Adapter<ProcedureAdapter.viewHolder> {
    Context mContext;
    ArrayList<Advicemasterdata> filteredmdataset = new ArrayList<>();
    ArrayList<Advicemasterdata> mdataset;

    public ProcedureAdapter(Context mContext, ArrayList<Advicemasterdata> mdataset) {
        this.mdataset = mdataset;
        this.mContext = mContext;
        filteredmdataset.addAll(mdataset);
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.list_item_procedure, parent, false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        holder.cb.setText(filteredmdataset.get(position).getTitle());
        holder.txt.setText(mContext.getResources().getString(R.string.char_rupees) + "" + mdataset.get(position).getAmount());
    }

    @Override
    public int getItemCount() {
        return filteredmdataset.size();
    }

    public void filter(String s) {
        filteredmdataset.clear();
        if (s.length() == 0) {
            filteredmdataset.addAll(mdataset);
        } else {
            for (int i = 0; i < mdataset.size(); i++) {
                if (mdataset.get(i).getTitle().contains(s))
                    filteredmdataset.add(mdataset.get(i));
            }
        }
        notifyDataSetChanged();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        CheckBox cb;
        TextView txt;

        public viewHolder(View itemView) {
            super(itemView);
            cb = itemView.findViewById(R.id.checkbox_1);
            txt = itemView.findViewById(R.id.textview_1);
        }
    }
}
