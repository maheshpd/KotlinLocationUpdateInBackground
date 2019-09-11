package com.healthbank.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthbank.ActivityCommon;
import com.healthbank.R;
import com.healthbank.classes.EyeData;
import com.healthbank.classes.EyespecialityData;
import com.healthbank.database.DatabaseHelper;

import java.util.ArrayList;

public class EyesCheckpAdapter extends RecyclerView.Adapter<EyesCheckpAdapter.viewHolder> {
    Context mContext;
    ArrayList<EyeData> mdataset;

    public EyesCheckpAdapter(Context mContext, ArrayList<EyeData> mdataset) {
        this.mContext = mContext;
        this.mdataset = mdataset;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_item_eyecheckup, parent, false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, final int position) {
        EyespecialityData od = mdataset.get(position).getOD();
        EyespecialityData os = mdataset.get(position).getOS();

        holder.txt1.setText(od.getUcvaData().getD());
        holder.txt2.setText(od.getUcvaData().getN());
        holder.txt3.setText(od.getUcvaData().getPH());
        holder.txt4.setText(od.getUcvaData().getVN());
        holder.txt5.setText(od.getUcvaData().getAdd());
        holder.txt6.setText(od.getUcvaData().getNV());

        holder.txt7.setText(os.getUcvaData().getD());
        holder.txt8.setText(os.getUcvaData().getN());
        holder.txt9.setText(os.getUcvaData().getPH());
        holder.txt10.setText(os.getUcvaData().getVN());
        holder.txt11.setText(os.getUcvaData().getAdd());
        holder.txt12.setText(os.getUcvaData().getNV());

        holder.txt13.setText(od.getPGP().getSph());
        holder.txt14.setText(od.getPGP().getCyl());
        holder.txt15.setText(od.getPGP().getAxis());
        holder.txt16.setText(od.getPGP().getVN());
        holder.txt17.setText(od.getPGP().getAdd());
        holder.txt18.setText(od.getPGP().getNV());

        holder.txt19.setText(os.getPGP().getSph());
        holder.txt20.setText(os.getPGP().getCyl());
        holder.txt21.setText(os.getPGP().getAxis());
        holder.txt22.setText(os.getPGP().getVN());
        holder.txt23.setText(os.getPGP().getAdd());
        holder.txt24.setText(os.getPGP().getNV());

        holder.txt25.setText(od.getMR().getSph());
        holder.txt26.setText(od.getMR().getCyl());
        holder.txt27.setText(od.getMR().getAxis());
        holder.txt28.setText(od.getMR().getVN());
        holder.txt29.setText(od.getMR().getAdd());
        holder.txt30.setText(od.getMR().getNV());

        holder.txt31.setText(os.getMR().getSph());
        holder.txt32.setText(os.getMR().getCyl());
        holder.txt33.setText(os.getMR().getAxis());
        holder.txt34.setText(os.getMR().getVN());
        holder.txt35.setText(os.getMR().getAdd());
        holder.txt36.setText(os.getMR().getNV());

        holder.txt37.setText(od.getCR().getSph());
        holder.txt38.setText(od.getCR().getCyl());
        holder.txt39.setText(od.getCR().getAxis());
        holder.txt40.setText(od.getCR().getVN());
        holder.txt41.setText(od.getCR().getAdd());
        holder.txt42.setText(od.getCR().getNV());

        holder.txt43.setText(os.getCR().getSph());
        holder.txt44.setText(os.getCR().getCyl());
        holder.txt45.setText(os.getCR().getAxis());
        holder.txt46.setText(os.getCR().getVN());
        holder.txt47.setText(os.getCR().getAdd());
        holder.txt48.setText(os.getCR().getNV());

        holder.txt49.setText(od.getPUPIL());
        holder.txt51.setText(od.getIOP());
        holder.txt53.setText(od.getEXTEXM());
        holder.txt55.setText(od.getANTSEG());
        holder.txt57.setText(od.getFUNDUS());

        holder.txt50.setText(os.getPUPIL());
        holder.txt52.setText(os.getIOP());
        holder.txt53.setText(os.getEXTEXM());
        holder.txt56.setText(os.getANTSEG());
        holder.txt58.setText(os.getFUNDUS());

        holder.txtedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ActivityCommon) mContext).gotoupdateeyedetails(mdataset.get(position).getId());
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper.getInstance(mContext).delete(mdataset.get(position).getId());
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
        TextView txt1, txt2, txt3, txt4, txt5, txt6, txt7, txt8, txt9, txt10, txt11, txt12, txt13, txt14, txt15, txt16, txt17, txt18, txt19, txt20, txt21, txt22, txt23, txt24, txt25, txt26, txt27, txt28, txt29, txt30, txt31, txt32, txt33, txt34, txt35, txt36, txt37, txt38, txt39, txt40, txt41, txt42, txt43, txt44, txt45, txt46, txt47, txt48, txt49, txt50, txt51, txt52, txt53, txt54, txt55, txt56, txt57, txt58, txtedit;
        ImageView delete;

        public viewHolder(View itemView) {
            super(itemView);
            txtedit = itemView.findViewById(R.id.textview_edit);
            txt1 = itemView.findViewById(R.id.textview_1);
            txt2 = itemView.findViewById(R.id.textview_2);
            txt3 = itemView.findViewById(R.id.textview_3);
            txt4 = itemView.findViewById(R.id.textview_4);
            txt5 = itemView.findViewById(R.id.textview_5);
            txt6 = itemView.findViewById(R.id.textview_6);
            txt7 = itemView.findViewById(R.id.textview_7);
            txt8 = itemView.findViewById(R.id.textview_8);
            txt9 = itemView.findViewById(R.id.textview_9);
            txt10 = itemView.findViewById(R.id.textview_10);

            txt11 = itemView.findViewById(R.id.textview_11);
            txt12 = itemView.findViewById(R.id.textview_12);
            txt13 = itemView.findViewById(R.id.textview_13);
            txt14 = itemView.findViewById(R.id.textview_14);
            txt15 = itemView.findViewById(R.id.textview_15);
            txt16 = itemView.findViewById(R.id.textview_16);
            txt17 = itemView.findViewById(R.id.textview_17);
            txt18 = itemView.findViewById(R.id.textview_18);
            txt19 = itemView.findViewById(R.id.textview_19);
            txt20 = itemView.findViewById(R.id.textview_20);


            txt21 = itemView.findViewById(R.id.textview_21);
            txt22 = itemView.findViewById(R.id.textview_22);
            txt23 = itemView.findViewById(R.id.textview_23);
            txt24 = itemView.findViewById(R.id.textview_24);
            txt25 = itemView.findViewById(R.id.textview_25);
            txt26 = itemView.findViewById(R.id.textview_26);
            txt27 = itemView.findViewById(R.id.textview_27);
            txt28 = itemView.findViewById(R.id.textview_28);
            txt29 = itemView.findViewById(R.id.textview_29);
            txt30 = itemView.findViewById(R.id.textview_30);

            txt31 = itemView.findViewById(R.id.textview_31);
            txt32 = itemView.findViewById(R.id.textview_32);
            txt33 = itemView.findViewById(R.id.textview_33);
            txt34 = itemView.findViewById(R.id.textview_34);
            txt35 = itemView.findViewById(R.id.textview_35);
            txt36 = itemView.findViewById(R.id.textview_36);
            txt37 = itemView.findViewById(R.id.textview_37);
            txt38 = itemView.findViewById(R.id.textview_38);
            txt39 = itemView.findViewById(R.id.textview_39);
            txt40 = itemView.findViewById(R.id.textview_40);

            txt41 = itemView.findViewById(R.id.textview_41);
            txt42 = itemView.findViewById(R.id.textview_42);
            txt43 = itemView.findViewById(R.id.textview_43);
            txt44 = itemView.findViewById(R.id.textview_44);
            txt45 = itemView.findViewById(R.id.textview_45);
            txt46 = itemView.findViewById(R.id.textview_46);
            txt47 = itemView.findViewById(R.id.textview_47);
            txt48 = itemView.findViewById(R.id.textview_48);
            txt49 = itemView.findViewById(R.id.textview_49);
            txt50 = itemView.findViewById(R.id.textview_50);

            txt51 = itemView.findViewById(R.id.textview_51);
            txt52 = itemView.findViewById(R.id.textview_52);
            txt53 = itemView.findViewById(R.id.textview_53);
            txt54 = itemView.findViewById(R.id.textview_54);
            txt55 = itemView.findViewById(R.id.textview_55);
            txt56 = itemView.findViewById(R.id.textview_56);
            txt57 = itemView.findViewById(R.id.textview_57);
            txt58 = itemView.findViewById(R.id.textview_58);

            delete = itemView.findViewById(R.id.ic_delete);
        }
    }
}
