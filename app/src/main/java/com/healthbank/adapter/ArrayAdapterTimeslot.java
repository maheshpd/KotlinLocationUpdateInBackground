package com.healthbank.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.healthbank.AllAppointment;
import com.healthbank.DateUtils;
import com.healthbank.R;
import com.healthbank.classes.DBTimeSlot;

import java.util.List;

public class ArrayAdapterTimeslot extends ArrayAdapter<DBTimeSlot> {
    Context context;
    List<DBTimeSlot> slots;
    private OnClickListener clk = new OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            DBTimeSlot slot = (DBTimeSlot) v.getTag();

            if (slot.isAvailable()) {
                Log.e("data", "" + DateUtils.formatDate(slot.getCal().getTime(),
                        "MM/dd/yyyy") + " 00:00:00.000" + " " + DateUtils.formatDate(slot.getCal().getTime(),
                        "hh:mm a") + " " + DateUtils.formatDate(slot.getCal().getTime(),
                        "a").replace(".", "").toUpperCase());
                ((AllAppointment) context).gotocreateappointment(DateUtils.formatDate(slot.getCal().getTime(), "hh:mm a"));
				/*try {
					GlobalValues.getClipboard().put(
							"aptdate",
							DateUtils.formatDate(slot.getCal().getTime(),
									"yyyy-MM-dd")+" 00:00:00.000");

					GlobalValues.getClipboard().put(
							"apttime",
							DateUtils.formatDate(slot.getCal().getTime(),
									"hh:mm"));

					GlobalValues.getClipboard().put(
							"timeType",
							DateUtils.formatDate(slot.getCal().getTime(),
									"a").replace(".", "").toUpperCase());


				} catch (Exception e) {

				}
				gotoNext();*/
            }
        }
    };

    public ArrayAdapterTimeslot(Context context, List<DBTimeSlot> items) {
        super(context, R.layout.list_item_time_slot, items);
        this.context = context;
        slots = items;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        DBTimeSlot rowItem = slots.get(position);
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_time_slot, null);
            holder = new ViewHolder();
            holder.txtTitle = convertView
                    .findViewById(R.id.textview_time_slot);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtTitle.setText(rowItem.getName().replace(".", ""));
        holder.txtTitle.setTag(rowItem);
        holder.txtTitle.setOnClickListener(clk);
        if (rowItem.isAvailable()) {
            holder.txtTitle.setTextColor(context.getResources().getColor(R.color.Green));
        } else {
            holder.txtTitle.setPaintFlags(holder.txtTitle.getPaintFlags()
                    | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.txtTitle.setTextColor(context.getResources().getColor(
                    R.color.Gray));
        }
        return convertView;
    }

    /* private view holder class */
    private class ViewHolder {
        TextView txtTitle;
    }
	
	/*private void gotoNext(){
		((ActivityCommon) context).gotonewappnt();
	}*/
}
