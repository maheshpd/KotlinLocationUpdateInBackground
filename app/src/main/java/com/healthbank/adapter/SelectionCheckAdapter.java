package com.healthbank.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.healthbank.R;
import com.healthbank.classes.Reading;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by it1 on 6/15/2018.
 */

public class SelectionCheckAdapter extends RecyclerView.Adapter<SelectionCheckAdapter.viewholder> {
    LinkedHashMap<String, ArrayList<Reading>> mdataset;
    Context mContext;
    List<List<Reading>> l;

    public SelectionCheckAdapter(LinkedHashMap<String, ArrayList<Reading>> mdataset, Context mContext) {
        this.mContext = mContext;
        this.mdataset = mdataset;
        l = new ArrayList<List<Reading>>(mdataset.values());
    }

    @Override
    public viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.list_item_selcetioncheckadapter, parent, false);
        return new viewholder(v);
    }

    @Override
    public void onBindViewHolder(viewholder holder, final int position) {
        holder.cb.setText(l.get(position).get(0).getName());
        holder.cb.setChecked(l.get(position).get(0).isIsselected());

        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                // Log.e("position ","position "+position+" "+l.get(position).get(0).isIsselected());
                l.get(position).get(0).setIsselected(b);
                // Log.e("position ","position "+position+" "+l.get(position).get(0).isIsselected());
                // notifyDataSetChanged();
                //  mdataset.get(l.get(position).toString()).get(0).setIsselected(b);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mdataset.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        CheckBox cb;

        public viewholder(View itemView) {
            super(itemView);
            cb = itemView.findViewById(R.id.checkbox_1);
        }
    }
}
