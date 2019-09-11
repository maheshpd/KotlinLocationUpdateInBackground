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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
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
import java.util.List;

public class PrescriptionFilter extends RecyclerView.Adapter<PrescriptionFilter.ViewHolder> implements Filterable {
    private final Context mContext;
    public MyFilter mFilter;
    private List<DetailPrescriptionData> myList;
    private List<DetailPrescriptionData> mFilteredList;

    public PrescriptionFilter(Context mContext, List<DetailPrescriptionData> mList) {
        this.mContext = mContext;
        this.myList = mList;
        this.mFilteredList = new ArrayList<DetailPrescriptionData>();
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilteredList.clear();
            mFilteredList.addAll(this.myList);
            mFilter = new PrescriptionFilter.MyFilter(this, mFilteredList);
        }
        return mFilter;
    }

    @Override
    public PrescriptionFilter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflate = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.list_item_detailedprescription, parent, false);
        return new PrescriptionFilter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final PrescriptionFilter.ViewHolder holder, final int position) {
        LinearLayoutManager mLayoutmanager = new LinearLayoutManager(mContext);
        holder.recyclerview_1.setLayoutManager(mLayoutmanager);
        holder.recyclerview_1.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(mContext, R.drawable.recycler_divider)));

        final PrescriptionAdapter adapter = new PrescriptionAdapter(mContext, myList.get(position).getPrescriptiondata());
        holder.recyclerview_1.setAdapter(adapter);
        holder.recyclerview_1.setItemAnimator(new DefaultItemAnimator());

        RecyclerItemTouchHelper.RecyclerItemTouchHelperListener listener = new RecyclerItemTouchHelper.RecyclerItemTouchHelperListener() {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int itemposition) {
                if (viewHolder instanceof PrescriptionAdapter.viewHolder) {
                    String name = myList.get(position).getPrescriptiondata().get(viewHolder.getAdapterPosition()).getDrugName();
                    // backup of removed item for undo purpose
                    final Drug deletedItem = myList.get(position).getPrescriptiondata().get(viewHolder.getAdapterPosition());
                    final int deletedIndex = viewHolder.getAdapterPosition();
                    myList.get(position).getPrescriptiondata().remove(deletedIndex);
                    adapter.notifyDataSetChanged();
                    notifyDataSetChanged();
                    Snackbar snackbar = Snackbar.make(((Activity) mContext).getWindow().getDecorView().findViewById(android.R.id.content), name + " removed from prescription!", Snackbar.LENGTH_LONG);
                    snackbar.setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            myList.get(position).getPrescriptiondata().add(deletedIndex, deletedItem);
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

        labAdapter adapter1 = new labAdapter(mContext, myList.get(position).getLabs(), false);
        holder.recyclerview_2.setAdapter(adapter1);

        LinearLayoutManager mLayoutmanager2 = new LinearLayoutManager(mContext);
        holder.recyclerview_3.setLayoutManager(mLayoutmanager2);

        labAdapter adapter2 = new labAdapter(mContext, myList.get(position).getDiagnostics(), false);
        holder.recyclerview_3.setAdapter(adapter2);

        holder.txt4.setText(myList.get(position).getAdvice().toString().replace("[", "").replace("]", ""));

        holder.txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((RxActivity) mContext).opendialoglab(myList.get(position).getLabs(), "Lab", position);
            }
        });

        holder.txt2.setText(myList.get(position).getLabs().toString().replace("[", "").replace("]", ""));

        holder.txt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((RxActivity) mContext).opendialoglab(myList.get(position).getDiagnostics(), "Diagnosis", position);
            }
        });
        holder.txt4.setText(myList.get(position).getDiagnostics().toString().replace("[", "").replace("]", ""));
        holder.txt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((RxActivity) mContext).opendialogprescription(myList.get(position).getPrescriptiondata(), position);
            }
        });

        holder.txt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((RxActivity) mContext).opendialoglab(myList.get(position).getAdvice(), "Advice", position);
            }
        });
        holder.txt7.setText(myList.get(position).getAdvice().toString().replace("[", "").replace("]", ""));
        holder.txt8.setText(myList.get(position).getDate() + "/" + myList.get(position).getId());
        holder.txt8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((RxActivity) mContext).updatedate(position);
            }
        });

        holder.img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityCommon) mContext).createPdf(myList.get(position), 0);
            }
        });

        holder.img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityCommon) mContext).createPdf(myList.get(position), 1);
            }
        });

        holder.img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityCommon) mContext).createPdf(myList.get(position), 2);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    private static class MyFilter extends Filter {
        private final PrescriptionFilter myAdapter;
        private final List<DetailPrescriptionData> originalList;
        private final List<DetailPrescriptionData> filteredList;

        private MyFilter(PrescriptionFilter myAdapter, List<DetailPrescriptionData> originalList) {
            this.myAdapter = myAdapter;
            this.originalList = originalList;
            this.filteredList = new ArrayList<DetailPrescriptionData>();
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            filteredList.clear();
            final FilterResults results = new FilterResults();

            if (charSequence.length() == 0) {
                filteredList.addAll(originalList);
            } else {
                final String filterPattern = charSequence.toString().toLowerCase().trim();
                if (filterPattern.equalsIgnoreCase("All")) {
                    filteredList.addAll(originalList);
                } else {
                    for (DetailPrescriptionData user : originalList) {
                        if (user.getDate().toLowerCase().contains(filterPattern)) {
                            filteredList.add(user);
                        }
                    }
                }
            }
            results.values = filteredList;
            results.count = filteredList.size();
            Log.e("count ", "count " + filteredList.size());
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            myAdapter.myList.clear();
            myAdapter.myList.addAll((ArrayList<DetailPrescriptionData>) filterResults.values);
            myAdapter.notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerview_1, recyclerview_2, recyclerview_3;
        TextView txt1, txt2, txt3, txt4, txt5, txt6, txt7, txt8;
        ImageView img1, img2, img3;

        public ViewHolder(View itemView) {
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