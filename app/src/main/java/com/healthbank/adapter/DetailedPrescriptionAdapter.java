package com.healthbank.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthbank.ActivityCommon;
import com.healthbank.DividerItemDecoration;
import com.healthbank.R;
import com.healthbank.RecyclerItemTouchHelper;
import com.healthbank.RxActivity;
import com.healthbank.classes.DetailPrescriptionData;
import com.healthbank.classes.Drug;

import java.util.ArrayList;

public class DetailedPrescriptionAdapter extends RecyclerView.Adapter<DetailedPrescriptionAdapter.viewHolder> {
    Context mContext;
    ArrayList<DetailPrescriptionData> mdataset = new ArrayList<>();
    ArrayList<DetailPrescriptionData> originaldataset = new ArrayList<>();

    public DetailedPrescriptionAdapter(Context mContext, ArrayList<DetailPrescriptionData> mdataset) {
        this.mContext = mContext;
        this.originaldataset = mdataset;
        mdataset.addAll(originaldataset);
      /*  DetailPrescriptionData d = new DetailPrescriptionData();
        d.setDate("All");
        originaldataset.add(d);*/
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflate = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.list_item_detailedprescription, parent, false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(final viewHolder holder, final int position) {
        LinearLayoutManager mLayoutmanager = new LinearLayoutManager(mContext);
        holder.recyclerview_1.setLayoutManager(mLayoutmanager);
        holder.recyclerview_1.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(mContext, R.drawable.recycler_divider)));

        final PrescriptionAdapter adapter = new PrescriptionAdapter(mContext, mdataset.get(position).getPrescriptiondata());
        holder.recyclerview_1.setAdapter(adapter);
        holder.recyclerview_1.setItemAnimator(new DefaultItemAnimator());

        RecyclerItemTouchHelper.RecyclerItemTouchHelperListener listener = new RecyclerItemTouchHelper.RecyclerItemTouchHelperListener() {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int itemposition) {
                if (viewHolder instanceof PrescriptionAdapter.viewHolder) {
                    String name = mdataset.get(position).getPrescriptiondata().get(viewHolder.getAdapterPosition()).getDrugName();
                    final Drug deletedItem = mdataset.get(position).getPrescriptiondata().get(viewHolder.getAdapterPosition());
                    final int deletedIndex = viewHolder.getAdapterPosition();
                    mdataset.get(position).getPrescriptiondata().remove(deletedIndex);
                    adapter.notifyDataSetChanged();
                    notifyDataSetChanged();
                    Snackbar snackbar = Snackbar.make(((Activity) mContext).getWindow().getDecorView().findViewById(android.R.id.content), name + " removed from prescription!", Snackbar.LENGTH_LONG);
                    snackbar.setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mdataset.get(position).getPrescriptiondata().add(deletedIndex, deletedItem);
                            adapter.notifyDataSetChanged();
                            notifyDataSetChanged();
                        }
                    });
                    snackbar.setActionTextColor(Color.YELLOW);
                    snackbar.show();
                }
            }
        };

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, listener);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(holder.recyclerview_1);

        LinearLayoutManager mLayoutmanager1 = new LinearLayoutManager(mContext);
        holder.recyclerview_2.setLayoutManager(mLayoutmanager1);

        labAdapter adapter1 = new labAdapter(mContext, mdataset.get(position).getLabs(), false);
        holder.recyclerview_2.setAdapter(adapter1);

        LinearLayoutManager mLayoutmanager2 = new LinearLayoutManager(mContext);
        holder.recyclerview_3.setLayoutManager(mLayoutmanager2);

        labAdapter adapter2 = new labAdapter(mContext, mdataset.get(position).getDiagnostics(), false);
        holder.recyclerview_3.setAdapter(adapter2);

        holder.txt4.setText(mdataset.get(position).getAdvice().toString().replace("[", "").replace("]", ""));

        holder.txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((RxActivity) mContext).opendialoglab(mdataset.get(position).getLabs(), "Lab", position);
            }
        });

        holder.txt2.setText(mdataset.get(position).getLabs().toString().replace("[", "").replace("]", ""));

        holder.txt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((RxActivity) mContext).opendialoglab(mdataset.get(position).getDiagnostics(), "Diagnostic", position);
            }
        });
        holder.txt4.setText(mdataset.get(position).getDiagnostics().toString().replace("[", "").replace("]", ""));
        holder.txt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((RxActivity) mContext).opendialogprescription(mdataset.get(position).getPrescriptiondata(), position);
            }
        });

        holder.txt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((RxActivity) mContext).opendialoglab(mdataset.get(position).getAdvice(), "Advice", position);
            }
        });

        holder.txt7.setText(mdataset.get(position).getAdvice().toString().replace("[", "").replace("]", ""));
        holder.txt8.setText(mdataset.get(position).getDate() + "/" + mdataset.get(position).getId());

        holder.txt8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((RxActivity) mContext).updatedate(position);
            }
        });

        holder.img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityCommon) mContext).createPdf(mdataset.get(position), 0);
            }
        });

        holder.img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityCommon) mContext).createPdf(mdataset.get(position), 1);
            }
        });

        holder.img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityCommon) mContext).createPdf(mdataset.get(position), 2);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mdataset.size();
    }

    public void filter(String date) {
        mdataset.clear();
        if (date.equalsIgnoreCase("All")) {
            mdataset.addAll(originaldataset);
        } else {
            for (int i = 0; i < originaldataset.size(); i++) {
                if (originaldataset.get(i).getDate().equalsIgnoreCase(date)) {
                    mdataset.add(originaldataset.get(i));
                }
            }
        }
        notifyDataSetChanged();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerview_1, recyclerview_2, recyclerview_3;
        TextView txt1, txt2, txt3, txt4, txt5, txt6, txt7, txt8;
        ImageView img1, img2, img3;

        public viewHolder(View itemView) {
            super(itemView);
            recyclerview_1 = itemView.findViewById(R.id.recyclerview_1);
            recyclerview_2 = itemView.findViewById(R.id.recyclerview_2);
            recyclerview_3 = itemView.findViewById(R.id.recyclerview_3);
            txt1 = itemView.findViewById(R.id.textview_1);
            txt2 = itemView.findViewById(R.id.textview_2);
            txt3 = itemView.findViewById(R.id.textview_3);
            txt4 = itemView.findViewById(R.id.textview_4);
            txt5 = itemView.findViewById(R.id.textview_5);
            txt6 = itemView.findViewById(R.id.textview_6);
            txt7 = itemView.findViewById(R.id.textview_7);
            txt8 = itemView.findViewById(R.id.textview_8);
            img1 = itemView.findViewById(R.id.imageview_1);
            img2 = itemView.findViewById(R.id.imageview_2);
            img3 = itemView.findViewById(R.id.imageview_3);
        }
    }
}
