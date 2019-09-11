package com.healthbank.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthbank.ActivityCommon;
import com.healthbank.R;
import com.healthbank.classes.FileData;

import java.util.ArrayList;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.viewHolder> {
    Context mContext;
    ArrayList<FileData> mdataset;

    public FileAdapter(Context mContext, ArrayList<FileData> mdataset) {
        this.mContext = mContext;
        this.mdataset = mdataset;
    }

    @Override
    public FileAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item_file, parent, false);
        return new FileAdapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(FileAdapter.viewHolder holder, final int position) {
        holder.txt1.setText(mdataset.get(position).getFilename());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("path ", "path " + mdataset.get(position).getFilepath());
                ((ActivityCommon) mContext).openfile(mdataset.get(position).getFilepath());
            }
        });
        holder.img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mdataset.remove(position);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mdataset.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView txt1;
        ImageView img1, img2;

        public viewHolder(View itemView) {
            super(itemView);
            txt1 = itemView.findViewById(R.id.textview_1);
            img1 = itemView.findViewById(R.id.imageview_1);
            img2 = itemView.findViewById(R.id.img_delete);
        }
    }
}

