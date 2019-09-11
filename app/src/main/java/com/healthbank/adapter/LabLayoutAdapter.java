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

/**
 * Created by it1 on 8/9/2018.
 */

public class LabLayoutAdapter extends RecyclerView.Adapter<LabLayoutAdapter.ViewHolder> {
    Context mContext;
    String hint = "";
    private ArrayList<com.healthbank.classes.Lab> mDataset;

    public LabLayoutAdapter(Context mContext, ArrayList<com.healthbank.classes.Lab> myDataset, String hint) {
        mDataset = myDataset;
        this.mContext = mContext;
        this.hint = hint;
    }

    @Override
    public LabLayoutAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_lablayout, parent, false);
        LabLayoutAdapter.ViewHolder vh = new LabLayoutAdapter.ViewHolder(v, new LabLayoutAdapter.MyCustomEditTextListener1(), new LabLayoutAdapter.MyCustomEditTextListener2(), new LabLayoutAdapter.MyCustomEditTextListener3());
        return vh;
    }

    @Override
    public void onBindViewHolder(LabLayoutAdapter.ViewHolder holder, final int position) {
        holder.myCustomEditTextListener1.updatePosition(holder.getAdapterPosition());
        holder.mEditText1.setText(mDataset.get(holder.getAdapterPosition()).getTest());
        holder.myCustomEditTextListener2.updatePosition(holder.getAdapterPosition());
        holder.mEditText2.setText(mDataset.get(holder.getAdapterPosition()).getResult());

        holder.myCustomEditTextListener3.updatePosition(holder.getAdapterPosition());
        holder.mEditText3.setText(mDataset.get(holder.getAdapterPosition()).getNormal());

        if (position == 0)
            holder.img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_plus));
        else
            holder.img.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_minus));
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position == 0) {
                    mDataset.add(new com.healthbank.classes.Lab());
                    notifyDataSetChanged();
                } else {
                    mDataset.remove(position);
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public EditText mEditText1;
        public LabLayoutAdapter.MyCustomEditTextListener1 myCustomEditTextListener1;

        public EditText mEditText2;
        public LabLayoutAdapter.MyCustomEditTextListener2 myCustomEditTextListener2;

        public EditText mEditText3;
        public LabLayoutAdapter.MyCustomEditTextListener3 myCustomEditTextListener3;
        ImageView img;

        public ViewHolder(View v, LabLayoutAdapter.MyCustomEditTextListener1 myCustomEditTextListener1, LabLayoutAdapter.MyCustomEditTextListener2 myCustomEditTextListener2, LabLayoutAdapter.MyCustomEditTextListener3 myCustomEditTextListener3) {
            super(v);
            this.mEditText1 = v.findViewById(R.id.edittext_1);
            this.myCustomEditTextListener1 = myCustomEditTextListener1;
            this.mEditText1.addTextChangedListener(myCustomEditTextListener1);

            this.mEditText2 = v.findViewById(R.id.edittext_2);
            this.myCustomEditTextListener2 = myCustomEditTextListener2;
            this.mEditText2.addTextChangedListener(myCustomEditTextListener2);

            this.mEditText3 = v.findViewById(R.id.edittext_3);
            this.myCustomEditTextListener3 = myCustomEditTextListener3;
            this.mEditText3.addTextChangedListener(myCustomEditTextListener3);

            img = v.findViewById(R.id.imageview_1);
        }
    }

    private class MyCustomEditTextListener1 implements TextWatcher {
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
            mDataset.get(position).setTest(charSequence.toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }

    private class MyCustomEditTextListener2 implements TextWatcher {
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
            mDataset.get(position).setResult(charSequence.toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }

    private class MyCustomEditTextListener3 implements TextWatcher {
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
            mDataset.get(position).setNormal(charSequence.toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }
}
