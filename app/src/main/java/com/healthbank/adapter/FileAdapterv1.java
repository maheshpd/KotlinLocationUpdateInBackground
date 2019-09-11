package com.healthbank.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.healthbank.ActivityCommon;
import com.healthbank.InternetUtils;
import com.healthbank.PathalogyActivity;
import com.healthbank.R;

import java.util.ArrayList;

public class FileAdapterv1 extends RecyclerView.Adapter<FileAdapterv1.viewHolder> {
    Context mContext;
    ArrayList<String> mdataset;
    String name = "";
    String id = "";

    public FileAdapterv1(Context mContext, ArrayList<String> mdataset, String id, String name) {
        this.mContext = mContext;
        this.mdataset = mdataset;
        this.id = id;
        this.name = name;
    }

    @Override
    public FileAdapterv1.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_item_file, parent, false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(FileAdapterv1.viewHolder holder, final int position) {
        holder.txt1.setVisibility(View.GONE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    PathalogyActivity.path = mdataset.get(position);
                    ActivityCompat.requestPermissions(((Activity) mContext), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 202);
                    return;
                } else {
                    if (InternetUtils.getInstance(mContext).available()) {
                        ((ActivityCommon) mContext).openurlfile(mdataset.get(position));
                    } else {
                        Toast.makeText(mContext, "No Network", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        holder.img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (InternetUtils.getInstance(mContext).available()) {
                    mdataset.remove(position);
                    ((PathalogyActivity) mContext).updatedata(mdataset, id, name);
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(mContext, "No Network", Toast.LENGTH_LONG).show();
                }
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
