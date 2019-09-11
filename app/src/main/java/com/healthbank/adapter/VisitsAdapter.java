package com.healthbank.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.healthbank.DateUtils;
import com.healthbank.Fonter;
import com.healthbank.R;
import com.healthbank.classes.Visits;

import java.util.ArrayList;

public class VisitsAdapter extends RecyclerView.Adapter<VisitsAdapter.viewholder> {
    Context mContext;
    ArrayList<Visits> mdataset;
    int flag = 0;

    public VisitsAdapter(Context mContext, ArrayList<Visits> mdataset, int flag) {
        this.mContext = mContext;
        this.mdataset = mdataset;
        this.flag = flag;
    }

    @NonNull
    @Override
    public VisitsAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.list_item_visits, parent, false);
        return new VisitsAdapter.viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final VisitsAdapter.viewholder holder, final int position) {
        holder.txt1.setTypeface(Fonter.getTypefaceregular(mContext));
        holder.txt2.setTypeface(Fonter.getTypefaceregular(mContext));
        if (mdataset.get(position).getVisitid() == 0) {
            holder.txt1.setText("All");
            holder.txt1.setVisibility(View.VISIBLE);
            holder.txt1.setTypeface(Fonter.getTypefaceregular(mContext));
            holder.cb.setVisibility(View.GONE);
            holder.txt2.setVisibility(View.GONE);
            holder.txt3.setVisibility(View.GONE);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //   ((ActivityCommon)mContext).gotoTemplateActivity(Integer.toString(mdataset.get(position).getVisitid()),mdataset.get(position).getVisitdate());
                 /*   if(flag==0)
                    ((ActivityCommon) mContext).getprescriptiondata(mdataset.get(position).getVisitid(), flag);
                    else {
                        String visitid="";
                        for (int i=1;i<mdataset.size();i++)
                        {
                            visitid=visitid+mdataset.get(i).getVisitid();
                        }
                        ((ActivityCommon) mContext).getprescriptiondata(visitid, flag);
                    }*/
                }
            });
        } else {
            holder.txt1.setVisibility(View.GONE);
            holder.cb.setVisibility(View.GONE);
            holder.cb.setTypeface(Fonter.getTypefaceregular(mContext));
            holder.txt3.setTypeface(Fonter.getTypefacesemibold(mContext));
            holder.txt2.setTypeface(Fonter.getTypefacesemibold(mContext));
            holder.txt2.setVisibility(View.VISIBLE);
            if (mdataset.get(position).getDrname() != null)
                if (!mdataset.get(position).getDrname().equalsIgnoreCase("null")) {
                    holder.txt3.setVisibility(View.VISIBLE);
                    holder.txt3.setText(mdataset.get(position).getDrname());
                } else
                    holder.txt3.setVisibility(View.GONE);
            else
                holder.txt3.setVisibility(View.GONE);
            holder.txt2.setTypeface(Fonter.getTypefaceregular(mContext));
            String date = DateUtils.parseDateNew(mdataset.get(position).getVisitdate(), "yyyy-MM-dd'T'HH:mm:ss", "dd MMM yyyy");
            String s = "Visit " + mdataset.get(position).getVisit();

            if (mdataset.get(position).getDrname() != null)
               /* if(!mdataset.get(position).getDrname().equalsIgnoreCase("null"))
                s = s+" with " + mdataset.get(position).getDrname();*/
                holder.txt4.setText(s);
            holder.txt2.setText(date);
            holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    mdataset.get(position).setIsselected(b);
                }
            });

            holder.cb.setChecked(mdataset.get(position).isIsselected());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //((ActivityCommon)mContext).gotoTemplateActivity(Integer.toString(mdataset.get(position).getVisitid()),mdataset.get(position).getVisitdate());

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mdataset.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView txt1, txt2, txt3, txt4;
        CheckBox cb;

        public viewholder(View itemView) {
            super(itemView);
            txt1 = itemView.findViewById(R.id.textview_1);
            txt2 = itemView.findViewById(R.id.textview_2);
            txt3 = itemView.findViewById(R.id.textview_3);
            txt4 = itemView.findViewById(R.id.textview_4);
            cb = itemView.findViewById(R.id.checkbox_1);
        }
    }
}