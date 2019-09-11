package com.healthbank.classes;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.healthbank.ActivityCommon;
import com.healthbank.R;

import java.util.ArrayList;

/**
 * Created by it1 on 8/29/2018.
 */

public class BillingDetailsAdapter extends RecyclerView.Adapter<BillingDetailsAdapter.viewHolder> {
    Context mContext;
    ArrayList<BillingClass> mdataset;
    String rupee = "";

    public BillingDetailsAdapter(Context mContext, ArrayList<BillingClass> mdataset) {
        this.mContext = mContext;
        this.mdataset = mdataset;
        rupee = mContext.getResources().getString(R.string.char_rupees);
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item_billingdetail, parent, false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, final int position) {
        BillingClass bill = mdataset.get(position);
        holder.txt1.setText(bill.getBillno());
        holder.txt2.setText(bill.getBilldate());
        holder.txt3.setText(rupee + bill.getFinalamount());
        holder.txt4.setText(rupee + bill.getPaidamt());
        holder.txt5.setText(rupee + bill.getDueamt());

        if (Double.parseDouble(bill.getFinalamount()) == Double.parseDouble(bill.getPaidamt())) {
            holder.txt6.setText("Paid");
            holder.layout1.setVisibility(View.GONE);
            holder.layout2.setVisibility(View.GONE);
        } else {
            holder.txt6.setText("Pay now");
            holder.layout1.setVisibility(View.VISIBLE);
            holder.layout2.setVisibility(View.VISIBLE);

            holder.txt6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((ActivityCommon) mContext).gotopartialpayment(mdataset.get(position));
                }
            });
        }


        holder.img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityCommon) mContext).createBillingPdf(mdataset.get(position), 0);
            }
        });

        holder.img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityCommon) mContext).createBillingPdf(mdataset.get(position), 1);
            }
        });

        holder.img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityCommon) mContext).createBillingPdf(mdataset.get(position), 2);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mdataset.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView txt1, txt2, txt3, txt4, txt5, txt6;
        ImageView img1, img2, img3;
        LinearLayout layout1, layout2;

        public viewHolder(View itemView) {
            super(itemView);
            txt1 = itemView.findViewById(R.id.textview_1);
            txt2 = itemView.findViewById(R.id.textview_2);
            txt3 = itemView.findViewById(R.id.textview_3);
            txt4 = itemView.findViewById(R.id.textview_4);
            txt5 = itemView.findViewById(R.id.textview_5);
            txt6 = itemView.findViewById(R.id.textview_6);

            img1 = itemView.findViewById(R.id.imageview_1);
            img2 = itemView.findViewById(R.id.imageview_2);
            img3 = itemView.findViewById(R.id.imageview_3);
            layout1 = itemView.findViewById(R.id.layout_1);
            layout2 = itemView.findViewById(R.id.layout_2);
        }
    }
}
