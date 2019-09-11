package com.healthbank.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.healthbank.R;
import com.healthbank.classes.MasterDataClinicalExamination;

import java.util.ArrayList;

public class AdviceLayoutAdapter extends RecyclerView.Adapter<AdviceLayoutAdapter.ViewHolder> {
    ArrayList<MasterDataClinicalExamination> masterdata = new ArrayList<>();
    Context mContext;
    String hint = "";
    boolean isfocused = false;
    private ArrayList<String> mDataset;

    //  ArrayAdapter<MasterDataClinicalExamination> adapter;
    public AdviceLayoutAdapter(Context mContext, ArrayList<String> myDataset, String hint) {
        mDataset = myDataset;
        this.mContext = mContext;
        this.hint = hint;
        //masterdata = DatabaseHelper.getInstance(mContext).getclinicalexamination(Constants.SYMPTOMS);
        // adapter = new ArrayAdapter<MasterDataClinicalExamination>(mContext, android.R.layout.simple_list_item_1, masterdata);
    }

    @Override
    public AdviceLayoutAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_advicelayout, parent, false);
        ViewHolder vh = new ViewHolder(v, new MyCustomEditTextListener());
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mEditText.setText(mDataset.get(position));
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

        holder.mEditText.addTextChangedListener(holder.myCustomEditTextListener);
        holder.myCustomEditTextListener.updatePosition(position);
        if (position == (mDataset.size() - 1) && isfocused) {
            holder.mEditText.requestFocus();
            isfocused = false;
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public EditText mEditText;
        public MyCustomEditTextListener myCustomEditTextListener;
        ImageView img;

        public ViewHolder(View v, MyCustomEditTextListener myCustomEditTextListener) {
            super(v);
            this.mEditText = v.findViewById(R.id.autocompletetextview_1);
            this.myCustomEditTextListener = myCustomEditTextListener;
            img = v.findViewById(R.id.imageview_1);
            //    this.mEditText.addTextChangedListener(this.myCustomEditTextListener);
            mEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    mDataset.set(getAdapterPosition(), mEditText.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
    }

    public class MyCustomEditTextListener implements TextWatcher {
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
            //   mDataset.set(position, charSequence.toString());

        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }
}
