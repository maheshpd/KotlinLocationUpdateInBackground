package com.healthbank;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthbank.classes.Appointmentdata;
import com.healthbank.classes.Appointmentdatav1;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RecentAppointmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<Appointmentdatav1> mValues;
    SimpleDateFormat sd1 = new SimpleDateFormat("dd MMM yy", Locale.US);
    SimpleDateFormat sd = new SimpleDateFormat("MM/dd/yy", Locale.US);
    RecentAppointmentFragment fragment;

    public RecentAppointmentAdapter(List<Appointmentdatav1> items, RecentAppointmentFragment fragment) {
        mValues = items;
        this.fragment=fragment;
    }

    @Override
    public int getItemViewType(int position) {
        /*if (mValues.get(position).isIssection())
            return 1;*/
        //else
         return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ViewHolder view = (ViewHolder) holder;
        view.mItem = mValues.get(position);
        view.textView1.setText(mValues.get(position).getStartDate().trim().replace(" ", "\n"));
        view.textView2.setText(mValues.get(position).getFirstName());
        view.textView5.setText(mValues.get(position).getStatus());
        if (!mValues.get(position).getPurpose().equalsIgnoreCase(""))
            view.textView3.setText(mValues.get(position).getPurpose());
        else
            view.textView3.setText("notes not specified");
        if (mValues.get(position).isIsheader()) {
            try {
                Date date1 = sd.parse(mValues.get(position).getAptdate());
                ((ViewHolder) holder).textView4.setText(sd1.format(date1));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
        public Appointmentdatav1 mItem;
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

    public class ViewHolderSection extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView textView1;
        public Appointmentdata mItem;

        public ViewHolderSection(View view) {
            super(view);
            mView = view;
            textView1 = view.findViewById(R.id.textview_1);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + textView1.getText() + "'";
        }
    }
}
