package com.healthbank.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthbank.AddBillingFragment;
import com.healthbank.R;
import com.healthbank.classes.Advicemasterdata;

import java.util.ArrayList;

public class SelectedTreatmentAdapter extends RecyclerView.Adapter<SelectedTreatmentAdapter.viewHolder> {
    ArrayList<Advicemasterdata> mdataset;
    Context mContext;
    AddBillingFragment fragment;

    public SelectedTreatmentAdapter(Context mContext, ArrayList<Advicemasterdata> mdataset, AddBillingFragment fragment) {
        this.mdataset = mdataset;
        this.mContext = mContext;
        this.fragment = fragment;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.list_item_selectedtreatment, parent, false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, final int position) {
        holder.txt1.setText(mdataset.get(position).getTitle());
        holder.txt2.setText(mContext.getResources().getString(R.string.char_rupees) + "" + mdataset.get(position).getAmount());
        holder.txt3.setText("" + mdataset.get(position).getItemcount());

        holder.img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mdataset.get(position).setItemcount(mdataset.get(position).getItemcount() + 1);
                notifyDataSetChanged();
                fragment.updatetotalcount();
            }
        });

        holder.img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mdataset.get(position).getItemcount() > 0) {
                    mdataset.get(position).setItemcount(mdataset.get(position).getItemcount() - 1);
                    notifyDataSetChanged();
                    fragment.updatetotalcount();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mdataset.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView txt1, txt2, txt3, txt4;
        ImageView img1, img2;

        public viewHolder(View itemView) {
            super(itemView);
            txt1 = itemView.findViewById(R.id.textview_1);
            txt2 = itemView.findViewById(R.id.textview_2);
            txt3 = itemView.findViewById(R.id.textview_3);
            txt4 = itemView.findViewById(R.id.textview_4);
            img1 = itemView.findViewById(R.id.imageview_1);
            img2 = itemView.findViewById(R.id.imageview_2);
        }
    }
}
