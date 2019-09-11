package com.healthbank.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.healthbank.ActivityCommon;
import com.healthbank.Constants;
import com.healthbank.R;
import com.healthbank.classes.Connection;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by it1 on 8/10/2018.
 */

public class AdapterImages extends RecyclerView.Adapter<AdapterImages.viewholder> {
    Context mContext;
    ArrayList<String> mDataset;
    boolean iseditable = true;

    public AdapterImages(Context mContext, ArrayList<String> mDataset, boolean iseditable) {
        this.mContext = mContext;
        this.mDataset = mDataset;
        this.iseditable = iseditable;
    }

    @Override
    public viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_item_images, parent, false);
        return new viewholder(v);
    }

    @Override
    public void onBindViewHolder(viewholder holder, final int position) {
        try {
            String filePath = mDataset.get(position);
            File image = new File(filePath);
            String extension = "";
            int lastDotPosition = filePath.lastIndexOf('.');
            if (lastDotPosition > 0) {
                String string3 = filePath.substring(lastDotPosition + 1);
                extension = string3.toLowerCase();
            }
            if (extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("jpeg")) {
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(), bmOptions);
                holder.img.setImageBitmap(bitmap);
            } else {
                holder.img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_pdf));
            }
        } catch (Exception e) {
            Log.e("error ", "error " + e.toString());
        }

        if (iseditable)
            holder.deleteimg.setVisibility(View.VISIBLE);
        else
            holder.deleteimg.setVisibility(View.GONE);

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ActivityCommon) mContext).openfile(mDataset.get(position));
            }
        });

        holder.deleteimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mDataset.remove(position);
                    notifyDataSetChanged();
                    try {
                        Intent intent = new Intent(Constants.BROADCAST_WIZARD);
                        intent.putExtra(Constants.BROADCAST_RESPONSE_CODE, 200);
                        intent.putExtra(Constants.BROADCAST_URL_ACCESS, Connection.Filedeleted.ordinal());
                        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        ImageView img, deleteimg;

        public viewholder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgThumb);
            deleteimg = itemView.findViewById(R.id.deleteImage);
        }
    }

}
