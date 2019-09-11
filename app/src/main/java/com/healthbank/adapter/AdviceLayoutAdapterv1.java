package com.healthbank.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.healthbank.R;

import java.util.ArrayList;

public class AdviceLayoutAdapterv1 extends RecyclerView.Adapter<AdviceLayoutAdapterv1.ViewHolder> {
    //  ArrayList<MasterDataClinicalExamination> masterdata = new ArrayList<>();
    Context mContext;
    String hint = "";
    boolean isfocused = false;
    //ArrayAdapter<MasterDataClinicalExamination> adapter;
    private ArrayList<String> mDataset;

    public AdviceLayoutAdapterv1(Context mContext, ArrayList<String> myDataset, String hint) {
        mDataset = myDataset;
        this.mContext = mContext;
        this.hint = hint;
        //  masterdata = DatabaseHelper.getInstance(mContext).getclinicalexamination(Constants.SYMPTOMS);
        //   adapter = new ArrayAdapter<MasterDataClinicalExamination>(mContext, android.R.layout.simple_list_item_1, masterdata);
    }

    @Override
    public AdviceLayoutAdapterv1.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_advicelayout, parent, false);
        AdviceLayoutAdapterv1.ViewHolder vh = new AdviceLayoutAdapterv1.ViewHolder(v, new AdviceLayoutAdapterv1.MyCustomEditTextListener());
        return vh;
    }

    @Override
    public void onBindViewHolder(AdviceLayoutAdapterv1.ViewHolder holder, final int position) {
       /* holder.mEditText.setText(mDataset.get(holder.getAdapterPosition()));
        holder.mEditText.setHint(hint);
        holder.mEditText.setAdapter(adapter);
        holder.mEditText.setThreshold(0);*/
        holder.mEditText.setText(mDataset.get(holder.getAdapterPosition()));
        if (position == 0)
            holder.img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_plus));
        else
            holder.img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_minus));
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position == 0) {
                    mDataset.add("");
                    notifyDataSetChanged();
                    isfocused = true;
                } else {
                    mDataset.remove(position);
                    notifyDataSetChanged();
                }
            }
        });
       /* if (position == (mDataset.size() - 1) && isfocused) {
            holder.mEditText.requestFocus();
            isfocused = false;
        }*/
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public EditText mEditText;
        public AdviceLayoutAdapterv1.MyCustomEditTextListener myCustomEditTextListener;
        ImageView img;

        public ViewHolder(View v, AdviceLayoutAdapterv1.MyCustomEditTextListener myCustomEditTextListener) {
            super(v);
            this.mEditText = v.findViewById(R.id.autocompletetextview_1);
            this.myCustomEditTextListener = myCustomEditTextListener;
            img = v.findViewById(R.id.imageview_1);
            this.mEditText.addTextChangedListener(myCustomEditTextListener);
        }
    }

    private class MyCustomEditTextListener implements TextWatcher {
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            mDataset.set(position, charSequence.toString());
            notifyDataSetChanged();
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }
}

