package com.healthbank.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.healthbank.DateUtils;
import com.healthbank.R;
import com.healthbank.classes.Appointmentdatav1;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by it1 on 8/8/2018.
 */
public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.viewHolder> {
    Context mContext;
    ArrayList<Appointmentdatav1> mdataset;

    public AppointmentAdapter(Context mContext, ArrayList<Appointmentdatav1> mdataset) {
        this.mContext = mContext;
        this.mdataset = mdataset;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.list_item_appointmentv1, parent, false);
        return new AppointmentAdapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        Appointmentdatav1 apt = mdataset.get(position);
        Calendar cal = Calendar.getInstance();
        try {
            if (apt != null)
                cal.setTime(DateUtils.parseDate(apt.getStartDate(), "dd-MM-yyyy hh:mm a"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.textView1.setText(DateUtils.formatDate(cal.getTime(), "dd MMM yyyy hh:mm a"));
        if (apt.getDescription() != null) {
            if (apt.getDescription().length() > 0) {
                holder.textView2.setText(apt.getDescription());
                holder.textView2.setVisibility(View.VISIBLE);
            } else {
                holder.textView2.setVisibility(View.GONE);
            }
        } else
            holder.textView2.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return mdataset.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView textView1, textView2;

        public viewHolder(View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.textview_1);
            textView2 = itemView.findViewById(R.id.textview_2);
        }
    }
}
