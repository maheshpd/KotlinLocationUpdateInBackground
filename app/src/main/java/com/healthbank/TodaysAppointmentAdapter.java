package com.healthbank;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthbank.classes.Appointmentdata;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by it1 on 1/4/2018.
 */

public class TodaysAppointmentAdapter  extends RecyclerView.Adapter<TodaysAppointmentAdapter.ViewHolder> {
    private final List<Appointmentdata> mValues;
    TodaysAppointmentFragment fragment;
    SimpleDateFormat sd1 = new SimpleDateFormat("dd MMM yy", Locale.US);
    SimpleDateFormat sd = new SimpleDateFormat("MM/dd/yy", Locale.US);
    public TodaysAppointmentAdapter(List<Appointmentdata> items,TodaysAppointmentFragment fragment) {
        mValues = items;
        this.fragment=fragment;
    }

    @Override
    public TodaysAppointmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new TodaysAppointmentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TodaysAppointmentAdapter.ViewHolder holder, final int position) {
        TodaysAppointmentAdapter.ViewHolder view = holder;
      /*  view.mItem = mValues.get(position);
        view.textView1.setText(mValues.get(position).getStarttime().trim().replace(" ", "\n"));
        view.textView2.setText(mValues.get(position).getPtname());
        view.textView5.setText(mValues.get(position).getStatus());
        if (!mValues.get(position).getNotes().equalsIgnoreCase(""))
            view.textView3.setText(mValues.get(position).getNotes());
        else
            view.textView3.setText("notes not specified");
        if (mValues.get(position).isIssection()) {
            try {
                Date date1 = sd.parse(mValues.get(position).getDate());
                ((TodaysAppointmentAdapter.ViewHolder) holder).textView4.setText(sd1.format(date1));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
        view.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // fragment.showdialog(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView textView1;
        public final TextView textView2;
        public final TextView textView3;
        public final TextView textView4;
        public final TextView textView5;
        public  final ImageView img;
        public ViewHolder(View view) {
            super(view);
            mView = view;
            textView1 = view.findViewById(R.id.textview_1);
            textView2 = view.findViewById(R.id.textview_2);
            textView3 = view.findViewById(R.id.textview_3);
            textView4 = view.findViewById(R.id.textview_4);
            textView5 = view.findViewById(R.id.textview_5);
            img= view.findViewById(R.id.imageview_1);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + textView1.getText() + "'";
        }
    }

}
