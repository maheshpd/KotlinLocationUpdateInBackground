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
import com.healthbank.DateUtils;
import com.healthbank.DividerItemDecoration;
import com.healthbank.R;
import com.healthbank.RecyclerItemTouchHelper;
import com.healthbank.classes.Detailedrx;
import com.healthbank.classes.Drug;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by it1 on 8/10/2018.
 */

public class RxDetailedAdapter extends RecyclerView.Adapter<RxDetailedAdapter.viewHolder> {
    Context mContext;
    ArrayList<Detailedrx> mdataset;

    public RxDetailedAdapter(Context mContext, ArrayList<Detailedrx> mdataset) {
        this.mContext = mContext;
        this.mdataset = mdataset;
    }

    @Override
    public RxDetailedAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item_prescriptiondetails, parent, false);
        return new RxDetailedAdapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(RxDetailedAdapter.viewHolder holder, final int position) {
        Date date = DateUtils.parseDate(mdataset.get(position).getTitle(), "dd-MM-yyyy");
        String d = DateUtils.formatDate(date, "dd MMM yyyy");
        holder.txt1.setText(d);

        LinearLayoutManager mLayoutmanager = new LinearLayoutManager(mContext);
        holder.mrecyclerview1.setLayoutManager(mLayoutmanager);
        holder.mrecyclerview1.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(mContext, R.drawable.recycler_divider)));

        final PrescriptionAdapter adapter = new PrescriptionAdapter(mContext, mdataset.get(position).getMdataset());
        holder.mrecyclerview1.setAdapter(adapter);
        holder.mrecyclerview1.setItemAnimator(new DefaultItemAnimator());

        RecyclerItemTouchHelper.RecyclerItemTouchHelperListener listener = new RecyclerItemTouchHelper.RecyclerItemTouchHelperListener() {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int itemposition) {
                if (viewHolder instanceof PrescriptionAdapter.viewHolder) {
                    String name = mdataset.get(position).getMdataset().get(viewHolder.getAdapterPosition()).getDrugName();
                    final Drug deletedItem = mdataset.get(position).getMdataset().get(viewHolder.getAdapterPosition());
                    final int deletedIndex = viewHolder.getAdapterPosition();
                    mdataset.get(position).getMdataset().remove(deletedIndex);
                    adapter.notifyDataSetChanged();
                    notifyDataSetChanged();
                    Snackbar snackbar = Snackbar.make(((Activity) mContext).getWindow().getDecorView().findViewById(android.R.id.content), name + " removed from prescription!", Snackbar.LENGTH_LONG);
                    snackbar.setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mdataset.get(position).getMdataset().add(deletedIndex, deletedItem);
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
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(holder.mrecyclerview1);

        holder.img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityCommon) mContext).createRxPdf(mdataset.get(position), 0);
            }
        });

        holder.img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityCommon) mContext).createRxPdf(mdataset.get(position), 1);
            }
        });

        holder.img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityCommon) mContext).createRxPdf(mdataset.get(position), 2);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mdataset.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView txt1;
        RecyclerView mrecyclerview1, mrecyclerview2;
        ImageView img1, img2, img3;

        public viewHolder(View itemView) {
            super(itemView);
            txt1 = itemView.findViewById(R.id.textview_1);
            mrecyclerview1 = itemView.findViewById(R.id.recyclerview_1);
            mrecyclerview2 = itemView.findViewById(R.id.recyclerview_2);
            img1 = itemView.findViewById(R.id.imageview_1);
            img2 = itemView.findViewById(R.id.imageview_2);
            img3 = itemView.findViewById(R.id.imageview_3);
        }
    }
}
