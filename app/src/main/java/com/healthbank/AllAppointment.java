package com.healthbank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.healthbank.adapter.LIstViewAdapter;
import com.healthbank.classes.Appointmentdata;
import com.healthbank.classes.Connection;
import com.healthbank.classes.DBTimeSlot;
import com.healthbank.classes.TreeNode;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AllAppointment extends ActivityCommon {
    public String[] groups = {"Morning", "Afternoon", "Evening"};
    Calendar aptCal;
    ImageButton nxt, prv;
    String doc_id;
    SharedPreferences sharedPreferences;
    ExpandableListView expandableListView;
    TextView selected_date;
    LIstViewAdapter treeViewAdapter;
    int lastExpandedPosition = 1;
    String interval;
    List<TreeNode> treeNode;
    ArrayList<Appointmentdata> appointmentdata;
    Calendar today;
    String ptname, mobileno, patientid;
    boolean isnewpatient = false;
    boolean isedit = false;
    String date = "";
    private OnClickListener clk = new OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            aptCal.add(Calendar.DATE, (Integer) v.getTag());
            long diff = aptCal.getTimeInMillis() - today.getTimeInMillis();
            long days = diff / (24 * 60 * 60 * 1000);

            if (days >= 15) {
                aptCal.add(Calendar.DATE, -1);
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy",
                        Locale.getDefault());
                selected_date.setText(dateFormat.format(aptCal.getTime()));
                getDataFromDb();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allappointment);
        setmaterialDesign();
        back();

        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Bundle b = getIntent().getExtras();

	/*	if(b.containsKey("isnewpatient"))
            isnewpatient=b.getBoolean("isnewpatient");
		if(b.containsKey("ptname"))
			ptname=b.getString("ptname");

		if(b.containsKey("mobileno"))
			mobileno=b.getString("mobileno");

		if(b.containsKey("patientid"))
			patientid=b.getString("patientid");

		if(b.containsKey("isedit"))
			this.isedit=b.getBoolean("isedit");*/



        ptname = GlobalValues.selectedpt.getFirstName();
        mobileno = GlobalValues.selectedpt.getMobile();
        patientid = Integer.toString(GlobalValues.selectedpt.getPatientid());

        ((TextView) findViewById(R.id.textViewname)).setText(ptname);
        ((TextView) findViewById(R.id.textViewmobile)).setText(mobileno);
        appointmentdata = new ArrayList<Appointmentdata>();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        interval = "15";
        doc_id = Integer.toString(GlobalValues.docid);
        selected_date = findViewById(R.id.textview_1);
        nxt = findViewById(R.id.button_action_next);
        nxt.setTag(1);
        prv = findViewById(R.id.button_action_previous);
        prv.setTag(-1);
        nxt.setOnClickListener(clk);
        prv.setOnClickListener(clk);
        aptCal = Calendar.getInstance(Locale.getDefault());
        today = Calendar.getInstance(Locale.getDefault());
        selected_date.setText(DateUtils.formatDate(aptCal.getTime(),
                "dd MMM yyyy"));

        if (b.containsKey("date")) {
            this.date = b.getString("date");
            Date d = DateUtils.parseDate(date, "dd-MM-yyyy");
            aptCal.setTime(d);
            selected_date.setText(DateUtils.formatDate(aptCal.getTime(),
                    "dd MMM yyyy"));
        }

        expandableListView = this
                .findViewById(R.id.expandableListView);
        treeNode = new ArrayList<TreeNode>();
        treeViewAdapter = new LIstViewAdapter(this, treeNode);
        expandableListView.setAdapter(treeViewAdapter);
        expandableListView.setOnGroupExpandListener(new OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    expandableListView
                            .collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });
        try {
            expandableListView.expandGroup(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getDataFromDb();
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        getDataFromDb();
    }

    private void getDataFromDb() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy",
                Locale.getDefault());
        appointmentdata.clear();
        //appointmentdata = DatabaseHelper.getInstance(AllAppointment.this).getappointmentData("table_appointment", dateFormat.format(aptCal.getTime()));
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                setdata();
            }
        });
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    private String getDayAbr() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("EEE",
                Locale.getDefault());
        return dateFormatter.format(aptCal.getTime());
    }

    public void setdata() {
        treeNode.clear();
        ArrayList<ArrayList<DBTimeSlot>> a = new ArrayList<ArrayList<DBTimeSlot>>();
		/*String session= GlobalValues.profile.getSession();
		Gson gson=new Gson();
		ArrayList<String> list = gson.fromJson(session, new TypeToken<ArrayList<String>> () {}.getType());*/
        for (int i = 0; i < 1; i++) {
            a.add(gettimeslot("09:00am_09:00pm | 09:00am_09:00pm"));
        }
        for (int i = 0; i < a.size(); i++) {
            TreeNode node = new TreeNode();
            node.parent = groups[i];
            node.childs.add(1);
            node.subchilds = a.get(i);
            treeNode.add(node);
        }
        treeViewAdapter.notifyDataSetChanged();
    }

    private ArrayList<DBTimeSlot> gettimeslot(String session) {
        ArrayList<DBTimeSlot> timelist = new ArrayList<DBTimeSlot>();
        Calendar frm = Calendar.getInstance(Locale.getDefault());
        frm.set(Calendar.HOUR_OF_DAY, 9);
        frm.set(Calendar.MINUTE, 00);
        Calendar to = Calendar.getInstance(Locale.getDefault());
        to.set(Calendar.HOUR_OF_DAY, 18);
        to.set(Calendar.MINUTE, 00);
        while (frm.before(to)) {
            Calendar cal = Calendar.getInstance(Locale.getDefault());
            cal.setTime(frm.getTime());
            DBTimeSlot slot = new DBTimeSlot();
            slot.setAvailable(Appointmentdata.getAvailable(frm,
                    appointmentdata));
            slot.setCal(cal);
            slot.setName(DateUtils.formatDate(frm.getTime(), "hh:mma"));
            timelist.add(slot);
            frm.add(Calendar.MINUTE, Integer.parseInt(interval));
        }
        return timelist;
    }

    public void gotocreateappointment(String time) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy",
                    Locale.getDefault());
			/*Intent intent = new Intent(Constants.BROADCAST_WIZARD);
			intent.putExtra(Constants.BROADCAST_RESPONSE_CODE, Constants.STATUS_OK);
			intent.putExtra(Constants.BROADCAST_URL_ACCESS, Connection.TimeUpdated.ordinal());
			JSONObject obj = new JSONObject();
			obj.put("time", time);
			obj.put("date", dateFormat.format(aptCal.getTime()));
			intent.putExtra(Constants.BROADCAST_DATA, obj.toString());
			LocalBroadcastManager.getInstance(AllAppointment.this).sendBroadcast(intent);*/
            Intent intent = new Intent("slotselected");
            intent.putExtra(Constants.BROADCAST_RESPONSE_CODE, Constants.STATUS_OK);
            intent.putExtra(Constants.BROADCAST_URL_ACCESS, Connection.TimeUpdated.ordinal());
            JSONObject obj = new JSONObject();
            obj.put("time", time);
            obj.put("date", dateFormat.format(aptCal.getTime()));
            intent.putExtra(Constants.BROADCAST_DATA, obj.toString());
            LocalBroadcastManager.getInstance(AllAppointment.this).sendBroadcast(intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onResponsed(int statuscode, int accesscode, String data) {
        super.onResponsed(statuscode, accesscode, data);
		/*if (statuscode == Constants.STATUS_OK) {
			if (accesscode == Connection.Appointmentcreated.ordinal()) {
				try {
					finish();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}*/
    }
}
