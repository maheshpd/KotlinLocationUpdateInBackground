package com.healthbank.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.healthbank.ActivityCommon;
import com.healthbank.DateUtils;
import com.healthbank.InternetUtils;
import com.healthbank.PathalogyActivity;
import com.healthbank.R;
import com.healthbank.classes.Pathalogy;

import java.util.ArrayList;

public class PathalogyAdapter extends RecyclerView.Adapter<PathalogyAdapter.viewholder> {
    Context mContext;
    ArrayList<Pathalogy> mdataset;

    public PathalogyAdapter(Context mContext, ArrayList<Pathalogy> mdataset) {
        this.mContext = mContext;
        this.mdataset = mdataset;
    }

    @Override
    public PathalogyAdapter.viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.list_item_pathalogy, parent, false);
        return new PathalogyAdapter.viewholder(v);
    }

    @Override
    public void onBindViewHolder(PathalogyAdapter.viewholder holder, final int position) {
        holder.txt1.setText(mdataset.get(position).getTestName());
        String date = DateUtils.parseDateNew(mdataset.get(position).getCreate_date(), "yyyy-MM-dd'T'HH:mm:ss", "dd MMM yyyy");
        holder.txt2.setText(date);
        holder.img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PathalogyActivity.selectedpos = position;
                if (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(((Activity) mContext), new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 201);
                    return;
                } else {
                    if (InternetUtils.getInstance(mContext).available()) {
                        ((ActivityCommon) mContext).browsefile();
                    } else {
                        Toast.makeText(mContext, "No Network", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        // if(mdataset.get(position).getFilepath()!=null)
        //if(!mdataset.get(position).getFilepath().equalsIgnoreCase("null")) {
          /*rrayList<String> dataarray = new ArrayList<>();
          String[] data = mdataset.get(position).getFilepath().split(",");
          for (int i = 0; i < data.length; i++) {
              dataarray.add(data[i]);
          }*/
        FileAdapterv1 adapterv1 = new FileAdapterv1(mContext, mdataset.get(position).getFilepathdata(), mdataset.get(position).getId(), mdataset.get(position).getTestName());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        holder.mRecyclerview.setLayoutManager(linearLayoutManager);
        holder.mRecyclerview.setAdapter(adapterv1);
        // }
    }

    @Override
    public int getItemCount() {
        return mdataset.size();
    }

    public void filterData(String query) {

    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView txt1, txt2;
        Button bt1;
        RecyclerView mRecyclerview;
        ImageView img1;

        public viewholder(View itemView) {
            super(itemView);
            txt1 = itemView.findViewById(R.id.textview_1);
            txt2 = itemView.findViewById(R.id.textview_2);
            bt1 = itemView.findViewById(R.id.button_1);
            mRecyclerview = itemView.findViewById(R.id.recyclerview_1);
            img1 = itemView.findViewById(R.id.imageview_1);
        }
    }
}