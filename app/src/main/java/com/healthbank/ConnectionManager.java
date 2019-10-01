package com.healthbank;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.healthbank.classes.Appointmentdatav1;
import com.healthbank.classes.Connection;
import com.healthbank.classes.Template;
import com.healthbank.database.DatabaseHelper;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static com.healthbank.URLS.Getappointmenthistory;

public class ConnectionManager {
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private static ConnectionManager instance;
    OkHttpClient client;
    private Context context;

    public ConnectionManager(Context context) {
        this.context = context;
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(context.getCacheDir(), cacheSize);

        client = new OkHttpClient();
        client.setConnectTimeout(55, TimeUnit.SECONDS); // connect timeout
        client.setReadTimeout(55, TimeUnit.SECONDS);
        client.setCache(cache);
    }

    public static synchronized ConnectionManager getInstance(Context context) {
        if (instance == null) {
            instance = new ConnectionManager(context);
        }
        return instance;
    }

    private void isDotNet() {
        GlobalValues.TEMP_STR = GlobalValues.TEMP_STR.trim();
        GlobalValues.TEMP_STR = GlobalValues.TEMP_STR.substring(1,GlobalValues.TEMP_STR.length() - 1);
        GlobalValues.TEMP_STR = GlobalValues.TEMP_STR.replace("\\n", "");
        GlobalValues.TEMP_STR = GlobalValues.TEMP_STR.replace("\\", "");
    }

    private String isDotNet(String str) {
        try {
            str = str.trim();
            str = str.substring(1, str.length() - 1);
            str = str.replace("\\", "");
        }catch (Exception e)
        {
            Log.e("error ","errrror "+e.toString());
        }
        return str;
    }

    private void isDotNetSlash() {
        GlobalValues.TEMP_STR = GlobalValues.TEMP_STR.trim();
        GlobalValues.TEMP_STR = GlobalValues.TEMP_STR.substring(1,
                GlobalValues.TEMP_STR.length() - 1);
        GlobalValues.TEMP_STR = GlobalValues.TEMP_STR.replace("\\", "");
    }


    public void sendPushMessage(final String json) {
        if (InternetUtils.getInstance(context).available()) {
            final String headerd = "key="+Constants.apikey;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        RequestBody body = RequestBody.create(Constants.JSON, json);
                        Request request = new Request.Builder()
                                .url("https://fcm.googleapis.com/fcm/send")
                                .header("Authorization", headerd).post(body)
                                .build();
                        Response response = client.newCall(request).execute();
                        // ChatUtils.TEMP_STR = response.body().string();
                        final int code = response.code();
                        Log.e("RESPONSE", "" + code);
                        publishBroadcast(code, Connection.sendrequest.ordinal());
                    } catch (Exception e) {
                        Log.i("RESPONSE", "" + e.toString());
                        return;
                    }
                }
            });
            thread.start();
        }else{
            Log.e("no internet","no internet");
        }
    }

   /* public void getbranchinfo() {
        if (InternetUtils.getInstance(context).available()) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Request request = new Request.Builder().url(
                                URLS.GET_BRANCHINFO(GlobalValues.branchid)).build();
                        Response response = client.newCall(request).execute();
                        GlobalValues.TEMP_STR = response.body().string();
                        isDotNet();
                        final int code = response.code();

                        JSONArray array = new JSONArray(GlobalValues.TEMP_STR);
                        Log.e("data ","data "+array.toString());
                        if (array.length() > 0) {
                            DatabaseHelper.getInstance(context).delete(DatabaseHelper.TABLE_SERVICES);
                            DatabaseHelper.getInstance(context).savedata(DatabaseHelper.TABLE_SERVICES, array.get(0).toString());
                        }

                        if (array.length() > 1) {
                            DatabaseHelper.getInstance(context).delete(DatabaseHelper.TABLE_FACILITIES);
                            DatabaseHelper.getInstance(context).savedata(DatabaseHelper.TABLE_FACILITIES, array.get(1).toString());
                        }

                        if (array.length() > 4) {
                            DatabaseHelper.getInstance(context).delete(DatabaseHelper.TABLE_ABOUT);
                            DatabaseHelper.getInstance(context).savedata(DatabaseHelper.TABLE_ABOUT, array.get(4).toString());
                        }

                        if (array.length() > 5) {
                            DatabaseHelper.getInstance(context).delete(DatabaseHelper.TABLE_IMAGES);
                            DatabaseHelper.getInstance(context).savedata(DatabaseHelper.TABLE_IMAGES, array.get(5).toString());
                        }

                        if (array.length() > 7) {
                            Log.e("dATA","DATAT "+array.get(5).toString());
                            DatabaseHelper.getInstance(context).delete(DatabaseHelper.TABLE_BRANCHDATA);
                            DatabaseHelper.getInstance(context).savedata(DatabaseHelper.TABLE_BRANCHDATA, array.getJSONArray(7).toString());
                            int custid=array.getJSONArray(7).getJSONObject(0).getInt("appcustid");
                            int branchid=array.getJSONArray(7).getJSONObject(0).getInt("CustomerId");
                            SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(context);
                            SharedPreferences.Editor editor=pref.edit();
                            editor.putInt("custid",custid);
                            editor.putInt("branchid",branchid);
                          //  GlobalValues.branchid=branchid;
                            GlobalValues.custid=custid;
                            editor.commit();
                        }
                        if (array.length() > 13) {
                            DatabaseHelper.getInstance(context).delete(DatabaseHelper.TABLE_VIDEO);
                            DatabaseHelper.getInstance(context).savedata(DatabaseHelper.TABLE_VIDEO, array.get(13).toString());
                        }
                        getmedia();
                    } catch (Exception e) {
                        Log.e("error","error "+e.toString());
                        return;
                    }
                }
            });
            thread.start();
        } else {
            publishBroadcast(Constants.STATUS_OK, Connection.NO_INTERNET.ordinal());
            //Log.e("not available ", "net not available ");
        }
    }

    public void getmedia() {
        if (InternetUtils.getInstance(context).available()) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Request request = new Request.Builder().url(URLS.getmedia(GlobalValues.branchid)).build();
                        Response response = client.newCall(request).execute();
                        GlobalValues.TEMP_STR = response.body().string();
                        isDotNet();
                        JSONArray array = new JSONArray(GlobalValues.TEMP_STR);
                        if (array.length() > 0) {
                            DatabaseHelper.getInstance(context).delete(DatabaseHelper.TABLE_NEWS);
                            DatabaseHelper.getInstance(context).savedata(DatabaseHelper.TABLE_NEWS, array.get(0).toString());
                        }

                        if (array.length() > 1) {
                            DatabaseHelper.getInstance(context).delete(DatabaseHelper.TABLE_BLOG);
                            DatabaseHelper.getInstance(context).savedata(DatabaseHelper.TABLE_BLOG, array.get(1).toString());
                        }
                        if (array.length() > 5) {
                            DatabaseHelper.getInstance(context).delete(DatabaseHelper.TABLE_EVENT);
                            Log.e("data ","data "+array.get(5).toString());
                            DatabaseHelper.getInstance(context).savedata(DatabaseHelper.TABLE_EVENT, array.get(5).toString());
                        }
                        final int code = response.code();
                        getdepartment();


                    } catch (Exception e) {
                        Log.e("error ", "" + e.toString());
                        return;
                    }
                }
            });
            thread.start();
        } else {
            publishBroadcast(Constants.STATUS_OK, Connection.NO_INTERNET.ordinal());
            //Log.e("not available ","net not available ");
        }
    }*/

    public void getvaccine() {
        if (InternetUtils.getInstance(context).available()) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Request request = new Request.Builder().url(URLS.GET_BRANCHINFO(GlobalValues.branchid)).build();
                        Response response = client.newCall(request).execute();
                        GlobalValues.TEMP_STR = response.body().string();
                        isDotNet();
                        final int code = response.code();
                        // Log.e("res ", "Success " + code + " " + GlobalValues.TEMP_STR);
                        publishBroadcast(code, Connection.BRANCHINFO.ordinal());
                    } catch (Exception e) {
                        Log.e("error ", "" + e.toString());
                        return;
                    }
                }
            });
            thread.start();
        } else {
            publishBroadcast(Constants.STATUS_OK, Connection.NO_INTERNET.ordinal());
        }
    }

    /* public void getvitals() {
        if (InternetUtils.getInstance(context).available()) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Request request = new Request.Builder().url(URLS.GET_VITALS(GlobalValues.profile.getPatientid(), GlobalValues.branchid)).build();
                        Response response = client.newCall(request).execute();
                        GlobalValues.TEMP_STR = response.body().string();
                        isDotNet();
                        final int code = response.code();
                        publishBroadcast(code, Connection.GET_VITALS.ordinal());
                    } catch (Exception e) {
                        return;
                    }
                }
            });
            thread.start();
        } else {
            publishBroadcast(Constants.STATUS_OK, Connection.NO_INTERNET.ordinal());
        }
    }
   public void getlatestappointments(final String ptid, final String identifire) {
        if (InternetUtils.getInstance(context).available()) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Request request = new Request.Builder().url(URLS.Getappointmenthistory(ptid, identifire)).build();
                        Response response = client.newCall(request).execute();
                        GlobalValues.TEMP_STR = response.body().string();
                        isDotNet();
                        final int code = response.code();
                        try {
                            JSONArray array = new JSONArray(GlobalValues.TEMP_STR);
                            if (array.length() > 0) {
                                JSONArray array1 = array.getJSONArray(0);
                                Gson gson = new Gson();
                                Type type = new TypeToken<ArrayList<Appointment>>() {
                                }.getType();
                                ArrayList<Appointment> data = gson.fromJson(array1.toString(), type);
                                for (int i = 0; i < data.size(); i++) {
                                    ContentValues c = new ContentValues();
                                    c.put(DatabaseHelper.APPOINTMENTID, data.get(i).getAppointment_id());
                                    c.put(DatabaseHelper.DOCNAME, data.get(i).getDocname());
                                    c.put(DatabaseHelper.FIRSTNAME, data.get(i).getFirstName());
                                    c.put(DatabaseHelper.APTDATE, data.get(i).getApttime());
                                    c.put(DatabaseHelper.STATUSMSG, data.get(i).getStatusmsg());
                                    c.put(DatabaseHelper.PURPOSE, data.get(i).getPurpose());
                                    c.put(DatabaseHelper.DESCRIPTION, data.get(i).getDescription());
                                    c.put(DatabaseHelper.SAVEDTIME, DateUtils.getSqliteTime());
                                    Log.e("saved by noti data ", "saved data" + c.toString());
                                    DatabaseHelper.getInstance(context).saveappointment(c);
                                }
                            }
                        } catch (Exception e) {
                            return;
                        }
                        if (identifire.equalsIgnoreCase("0")) {
                            publishBroadcast(code, Connection.BRANCHINFO.ordinal());
                        } else {

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        } else {
            publishBroadcast(Constants.STATUS_OK, Connection.NO_INTERNET.ordinal());
        }
    }
    public void getappointments(final String ptid, final String identifire) {
        if (InternetUtils.getInstance(context).available()) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Request request = new Request.Builder().url(URLS.Getappointmenthistory(ptid, identifire)).build();
                        Response response = client.newCall(request).execute();
                        GlobalValues.TEMP_STR = response.body().string();
                        isDotNet();
                        final int code = response.code();
                        try {
                            JSONArray array = new JSONArray(GlobalValues.TEMP_STR);
                            if (array.length() > 0) {
                                JSONArray array1 = array.getJSONArray(0);
                                Gson gson = new Gson();
                                Type type = new TypeToken<ArrayList<Appointment>>() {
                                }.getType();
                                ArrayList<Appointment> data = gson.fromJson(array1.toString(), type);
                                for (int i = 0; i < data.size(); i++) {
                                    ContentValues c = new ContentValues();
                                    c.put(DatabaseHelper.APPOINTMENTID, data.get(i).getAppointment_id());
                                    c.put(DatabaseHelper.DOCNAME, data.get(i).getDocname());
                                    c.put(DatabaseHelper.FIRSTNAME, data.get(i).getFirstName());
                                    c.put(DatabaseHelper.APTDATE, data.get(i).getApttime());
                                    c.put(DatabaseHelper.STATUSMSG, data.get(i).getStatusmsg());
                                    c.put(DatabaseHelper.PURPOSE, data.get(i).getPurpose());
                                    c.put(DatabaseHelper.DESCRIPTION, data.get(i).getDescription());
                                    c.put(DatabaseHelper.SAVEDTIME, DateUtils.getSqliteTime());
                                    Log.e("saved by noti data ", "saved data" + c.toString());
                                    DatabaseHelper.getInstance(context).saveappointment(c);
                                }
                            }
                        } catch (Exception e) {
                            return;
                        }
                        if (identifire.equalsIgnoreCase("0")) {
                            publishBroadcast(code, Connection.Appointmenthistory.ordinal());
                        } else {

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        } else {
            publishBroadcast(Constants.STATUS_OK, Connection.NO_INTERNET.ordinal());
        }
    }

    public void gethealthrecords(final String visits) {
        if (InternetUtils.getInstance(context).available()) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Request request = new Request.Builder().url(URLS.GET_HEALTHRECORDS(GlobalValues.profile.getPatientid(), visits)).build();
                        Response response = client.newCall(request).execute();
                        GlobalValues.TEMP_STR = response.body().string();
                        isDotNet();
                        final int code = response.code();
                        publishBroadcast(code, Connection.GET_HEALTHRECORDS.ordinal());
                    } catch (Exception e) {
                        return;
                    }
                }
            });
            thread.start();
        } else {
            publishBroadcast(Constants.STATUS_OK, Connection.NO_INTERNET.ordinal());
        }
    }

    public void getdepartment() {
        if (InternetUtils.getInstance(context).available()) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Request request = new Request.Builder().url(URLS.GET_DEPARTMENT(GlobalValues.branchid)).build();
                        Response response = client.newCall(request).execute();
                        GlobalValues.TEMP_STR = response.body().string();
                        isDotNet();
                        final int code = response.code();
                        JSONArray array = new JSONObject(GlobalValues.TEMP_STR).getJSONObject("root").getJSONArray("subroot");
                        DatabaseHelper.getInstance(context).savedata(DatabaseHelper.TABLE_DEPARTMENT, array.toString());
                        if(GlobalValues.profile!=null)
                            ConnectionManager.getInstance(context).getlatestappointments(GlobalValues.profile.getPatientid(),"0");
                        else
                        publishBroadcast(code, Connection.BRANCHINFO.ordinal());
                    } catch (Exception e) {
                        return;
                    }
                }
            });
            thread.start();
        } else {
            publishBroadcast(Constants.STATUS_OK,
                    Connection.NO_INTERNET.ordinal());
        }
    }



    public void getpatientlabresult() {
        if (InternetUtils.getInstance(context).available()) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Request request = new Request.Builder().url(URLS.GET_PATIENTLABRESULT(GlobalValues.profile.getPatientid())).build();
                        Response response = client.newCall(request).execute();
                        GlobalValues.TEMP_STR = response.body().string();
                        isDotNet();
                        final int code = response.code();
                        JSONArray array = new JSONArray(GlobalValues.TEMP_STR);
                        if (array.length() > 0) {
                            DatabaseHelper.getInstance(context).savedata(DatabaseHelper.TABLE_PATHOLOGY, array.getJSONArray(0).toString());
                        }

                        if (array.length() > 1) {
                            DatabaseHelper.getInstance(context).savedata(DatabaseHelper.TABLE_DIAGNOSTICS, array.getJSONArray(1).toString());
                        }

                        if (array.length() > 2) {
                            DatabaseHelper.getInstance(context).savedata(DatabaseHelper.TABLE_OTHERREPORT, array.getJSONArray(2).toString());
                        }
                        Intent intentresults = new Intent(context, ResultsActivity.class);
                        context.startActivity(intentresults);
                        //publishBroadcast(code, Connection.GET_PATIENTLABRESULTS.ordinal());
                    } catch (Exception e) {
                        return;
                    }
                }
            });
            thread.start();
        } else {
            publishBroadcast(Constants.STATUS_OK,
                    Connection.NO_INTERNET.ordinal());
        }
    }

    public void getpaymenthistoryreceipts(final int flag, final String patid, final String billid) {
        //  Log.e("url ","url "+URLS.GET_PAYMENTHISTORY(flag, patid,billid));
        if (InternetUtils.getInstance(context).available()) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Request request = new Request.Builder().url(URLS.GET_PAYMENTHISTORY(flag, patid, billid)).build();
                        Response response = client.newCall(request).execute();
                        GlobalValues.TEMP_STR = response.body().string();
                        isDotNet();
                        final int code = response.code();
                        if (flag == 0) {
                            try {
                                if (billid.equalsIgnoreCase("0"))
                                    DatabaseHelper.getInstance(context).delete(DatabaseHelper.TABLE_BILLING);

                                JSONArray array = new JSONObject(GlobalValues.TEMP_STR).getJSONObject("root").optJSONArray("subroot");
                                if (array != null) {

                                    Gson gson = new Gson();
                                    Type type = new TypeToken<ArrayList<Billing>>() {
                                    }.getType();
                                    ArrayList<Billing> mdataset = gson.fromJson(array.toString(), type);
                                    for (int i = 0; i < mdataset.size(); i++) {
                                        Billing bill = mdataset.get(i);
                                        savebillingdata(bill);
                                    }

                                    //  DatabaseHelper.getInstance(context).savedata(DatabaseHelper.TABLE_BILLING, array.toString());
                                } else {
                                    JSONObject obj = new JSONObject(GlobalValues.TEMP_STR).getJSONObject("root").optJSONObject("subroot");
                                    if (obj != null) {
                                        JSONArray array1 = new JSONArray();
                                        array1.put(obj);
                                        Gson gson = new Gson();
                                        Type type = new TypeToken<Billing>() {
                                        }.getType();
                                        Billing bill = gson.fromJson(obj.toString(), type);
                                        savebillingdata(bill);
                                        // DatabaseHelper.getInstance(context).savedata(DatabaseHelper.TABLE_BILLING, array1.toString());
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            ConnectionManager.getInstance(context).getpaymenthistoryreceipts(1, GlobalValues.profile.getPatientid(), "0");
                        } else {
                            try {
                                JSONArray array = new JSONObject(GlobalValues.TEMP_STR).getJSONObject("root").optJSONArray("subroot");
                                if (array != null) {
                                    DatabaseHelper.getInstance(context).savedata(DatabaseHelper.TABLE_PAYMENTS, array.toString());
                                } else {
                                    JSONObject obj = new JSONObject(GlobalValues.TEMP_STR).getJSONObject("root").optJSONObject("subroot");
                                    if (obj != null) {
                                        JSONArray array1 = new JSONArray();
                                        array1.put(obj);
                                        DatabaseHelper.getInstance(context).savedata(DatabaseHelper.TABLE_PAYMENTS, array1.toString());
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            Intent intent = new Intent(context, PaymentHistoryActivity.class);
                            context.startActivity(intent);
                        }
                        //publishBroadcast(code, Connection.GET_PAYMENTS.ordinal());
                    } catch (Exception e) {
                        return;
                    }
                }
            });
            thread.start();
        } else {
            publishBroadcast(Constants.STATUS_OK,
                    Connection.NO_INTERNET.ordinal());
        }
    }


    public void savebillingdata(Billing bill) {
        ContentValues c = new ContentValues();
        c.put(DatabaseHelper.BILLBOOKID, bill.getId());
        c.put(DatabaseHelper.BILLNO, bill.getBillno());
        c.put(DatabaseHelper.TOTALAMT, bill.getTotalamt());
        c.put(DatabaseHelper.PAIDAMT, bill.getPaidamt());
        c.put(DatabaseHelper.DUEAMT, bill.getDueamt());
        c.put(DatabaseHelper.DOCTORID, bill.getDoctorid());
        c.put(DatabaseHelper.PATIENTID, bill.getPatientid());
        c.put(DatabaseHelper.PRACTICEID, bill.getPracticeid());
        c.put(DatabaseHelper.TAXAMT, bill.getTaxamt());
        c.put(DatabaseHelper.STATUS, bill.getStatus());
        c.put(DatabaseHelper.BILLDATE, bill.getBilldate());
        c.put(DatabaseHelper.FINALAMOUNT, bill.getFinalamount());
        c.put(DatabaseHelper.DISCOUNT, bill.getDiscount());
        c.put(DatabaseHelper.REFERBY, bill.getReferby());
        c.put(DatabaseHelper.REFUNDAMT, bill.getRefundamount());
        DatabaseHelper.getInstance(context).savebillingdata(c);
    }

    public void getvisits() {
        Log.e("url ", "url " + URLS.GET_VISITS(GlobalValues.profile.getPatientid()));
        if (InternetUtils.getInstance(context).available()) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Request request = new Request.Builder().url(URLS.GET_VISITS(GlobalValues.profile.getPatientid())).build();
                        Response response = client.newCall(request).execute();
                        GlobalValues.TEMP_STR = response.body().string();
                        isDotNet();
                        final int code = response.code();
                        publishBroadcast(code, Connection.GET_VISITS.ordinal());
                    } catch (Exception e) {
                        Log.e("error ", "" + e.toString());
                        return;
                    }
                }
            });
            thread.start();
        } else {
            publishBroadcast(Constants.STATUS_OK, Connection.NO_INTERNET.ordinal());
        }
    }
*/
    public void getprescription(final String patientid, final String visittype, final int flag) {
       // Log.e("url ", "url " + URLS.GET_PRESCRIPTION(patientid, visittype, flag));
        if (InternetUtils.getInstance(context).available()) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Request request = new Request.Builder().url(URLS.GET_PRESCRIPTION(patientid, visittype, flag)).build();
                        Response response = client.newCall(request).execute();
                        String res = response.body().string();
                        res = isDotNet(res);
                        final int code = response.code();
                        try {
                            JSONArray array = new JSONArray(res);

                        /*    if (array.length() > 1) {
                                DatabaseHelper.getInstance(context).delete(DatabaseHelper.TABLE_ADVISED);
                                DatabaseHelper.getInstance(context).savedata(DatabaseHelper.TABLE_ADVISED, array.get(0).toString());
                            }
                            if (array.length() > 2) {
                                DatabaseHelper.getInstance(context).delete(DatabaseHelper.TABLE_DIAGNOSIS);
                                DatabaseHelper.getInstance(context).savedata(DatabaseHelper.TABLE_DIAGNOSIS, array.get(1).toString());
                            }*/
                            if (array.length() > 3) {
                                JSONArray array1 = array.getJSONArray(2);
                                Log.e("presc res", "press res " + array1.toString());
                                if (array1 != null) {
                                    DatabaseHelper.getInstance(context).savealldrug(array1, GlobalValues.selectedpt.getPatientid(), GlobalValues.selectedpt.getId());
                                }
                               /* DatabaseHelper.getInstance(context).delete(DatabaseHelper.TABLE_DRUG);
                                DatabaseHelper.getInstance(context).savedata(DatabaseHelper.TABLE_DRUG, array.get(2).toString());*/
                            /*    if(flag==1)
                                {
                                    DatabaseHelper.getInstance(context).delete(DatabaseHelper.TABLE_ACTIVEPRESCRIPTION);
                                    Gson gson=new Gson();
                                    Type type=new TypeToken<ArrayList<Prescription>>(){}.getType();
                                    ArrayList<Prescription> pdata=gson.fromJson(array.getJSONArray(2).toString(),type);
                                    for (int i=0;i<pdata.size();i++)
                                    {
                                        Prescription data=pdata.get(i);
                                        ContentValues c=new ContentValues();
                                        c.put(DatabaseHelper.DRUGNAME, data.getDrugName());
                                        c.put(DatabaseHelper.BRAND, "");
                                        c.put(DatabaseHelper.DOSES, data.getDoses());
                                        c.put(DatabaseHelper.DURATION, data.getDuration());
                                        c.put(DatabaseHelper.INSTRUCTION, data.getInstruction());
                                        c.put(DatabaseHelper.BODYPART, data.getBodyPart());
                                        c.put(DatabaseHelper.DRUGUNIT, data.getDrugUnit());
                                        c.put(DatabaseHelper.DRUGQUANTITY, data.getDrugQuantity());
                                        c.put(DatabaseHelper.VISITNO, data.getVisit_no());
                                        c.put(DatabaseHelper.VISITDATE, data.getVisitdate());
                                        c.put(DatabaseHelper.DRUGID, data.getDrugId());
                                        c.put(DatabaseHelper.ISACTIVE, flag);
                                        c.put(DatabaseHelper.SAVEDTIME, DateUtils.getSqliteTime());
                                        DatabaseHelper.getInstance(context).saveactiveprescriptiondata(c);
                                    }
                                }*/
                            }
                         /*   if (array.length() > 4) {
                                DatabaseHelper.getInstance(context).delete(DatabaseHelper.TABLE_TESTPLAN);
                                DatabaseHelper.getInstance(context).savedata(DatabaseHelper.TABLE_TESTPLAN, array.get(3).toString());
                            }
                            if (array.length() > 5) {
                                DatabaseHelper.getInstance(context).delete(DatabaseHelper.TABLE_COMPLAINTS);
                                DatabaseHelper.getInstance(context).savedata(DatabaseHelper.TABLE_COMPLAINTS, array.get(4).toString());
                            }
                            if (array.length() >= 6) {
                                DatabaseHelper.getInstance(context).delete(DatabaseHelper.TABLE_LIFESTYLE);
                                DatabaseHelper.getInstance(context).savedata(DatabaseHelper.TABLE_LIFESTYLE, array.get(5).toString());
                            }
*/
                          /*  Intent intent = new Intent(context, PrescriptionActivity.class);
                            context.startActivity(intent);*/
                         /*   if (visittype.equalsIgnoreCase("all")) {
                                Intent intent = new Intent("drugdata");
                                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                            } else {*/

                            // }
                            publishBroadcast(code, Connection.GET_PRESCRIPTION.ordinal());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        Log.e("error ", "" + e.toString());
                        return;
                    }
                }
            });
            thread.start();
        } else {
            publishBroadcast(Constants.STATUS_OK, Connection.NO_INTERNET.ordinal());
        }
    }

    public void login(final String json) {
      //  Log.e("json ", "jsonlogin " + json+" "+URLS.Login());
        if (InternetUtils.getInstance(context).available()) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        RequestBody body = RequestBody.create(Constants.JSON, json);
                        Request request = new Request.Builder().url(URLS.Login()).post(body).build();
                        Response response = client.newCall(request).execute();
                        GlobalValues.TEMP_STR = response.body().string();
                        isDotNet();
                         Log.e("res ","res "+GlobalValues.TEMP_STR);
                        final int code = response.code();
                        publishBroadcast(code, Connection.LOGIN.ordinal());
                    } catch (Exception e) {
                        return;
                    }
                }
            });
            thread.start();
        } else {
            publishBroadcast(Constants.STATUS_OK, Connection.NO_INTERNET.ordinal());
        }
    }

    public void searchpatient(final String searchkey, final String from, final String to,final String userid,final String deptid,final String locid) {
        if (InternetUtils.getInstance(context).available()) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Request request = new Request.Builder().url(URLS.searchpatient(searchkey, from, to,userid,deptid,locid)).build();
                        Response response = client.newCall(request).execute();
                        GlobalValues.TEMP_STR = response.body().string();
                        Log.e("Global Values" ,"Global Values"+GlobalValues.TEMP_STR);
                        isDotNet();
                        final int code = response.code();
                        publishBroadcast(code, Connection.SearchPatient.ordinal());
                    } catch (Exception e) {
                        return;
                    }
                }
            });
            thread.start();
        } else {
            publishBroadcast(Constants.STATUS_OK, Connection.NO_INTERNET.ordinal());
        }
    }

    public void BookAppointment(final String json) {
        if (InternetUtils.getInstance(context).available()) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        RequestBody body = RequestBody.create(Constants.JSON, json);
                        Request request = new Request.Builder()
                                .url(URLS.Bookappointment())
                                .post(body).build();
                        Response response = client.newCall(request).execute();
                        GlobalValues.TEMP_STR = response.body().string();
                        //Log.e("res3 ", "res " + GlobalValues.TEMP_STR);
                        isDotNet();
                        final int code = response.code();
                        publishBroadcast(code, Connection.BookAppointment.ordinal());
                    } catch (Exception e) {
                        Log.i(this.getClass().getSimpleName(), "" + e.toString());
                        return;
                    }
                }
            });
            thread.start();
        } else {
            publishBroadcast(Constants.STATUS_OK,
                    Connection.NO_INTERNET.ordinal());
        }
    }


    public void Syncofflinedata() {
        if (InternetUtils.getInstance(context).available()) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONArray patientarray = DatabaseHelper.getInstance(context).getofflinepatient();
                        for (int i = 0; i < patientarray.length(); i++) {
                            patientarray.getJSONObject(i).put("name", patientarray.getJSONObject(i).getString(DatabaseHelper.FirstName));
                            patientarray.getJSONObject(i).put("connectionid", Constants.projectid);
                            String json = patientarray.getJSONObject(i).toString();
                            RequestBody body = RequestBody.create(Constants.JSON, json);
                            Request request = new Request.Builder()
                                    .url(URLS.Save_Patient())
                                    .post(body).build();
                            Response response = client.newCall(request).execute();
                            final int code = response.code();
                            JSONObject obj = new JSONObject(json);
                            String id = obj.getString("id");

                            String res = response.body().string();
                            res = isDotNet(res);
                            JSONArray array = new JSONArray(res);

                            if (array.length() > 0) {
                                JSONArray array1 = array.getJSONArray(0);
                                if (array1.length() > 0) {
                                    int patientid = array1.getJSONObject(0).getInt("Patientid");
                                    Log.e("resptid ", " " + patientid);
                                    ContentValues c = new ContentValues();
                                    c.put(DatabaseHelper.Patientid, patientid);
                                    c.put(DatabaseHelper.SYNC, 1);
                                    DatabaseHelper.getInstance(context).updatepatient(c, id);
                                    ContentValues c1 = new ContentValues();
                                    c1.put(DatabaseHelper.Patientid, patientid);
                                    DatabaseHelper.getInstance(context).updatevisitpatientid(c1, id);
                                    DatabaseHelper.getInstance(context).updatetemplatepatientid(c1, id);
                                    DatabaseHelper.getInstance(context).updateappointmentdbptid(c1, id);
                                }
                            }
                        }

                        JSONArray visitarray = DatabaseHelper.getInstance(context).getofflinevivisits();
                        for (int i = 0; i < visitarray.length(); i++) {
                            visitarray.getJSONObject(i).put("connectionid", Constants.projectid);
                            String json = visitarray.getJSONObject(i).toString();
                            Log.e("resvisitjson", " " + json);
                            RequestBody body = RequestBody.create(Constants.JSON, json);
                            Request request = new Request.Builder()
                                    .url(URLS.AddVisit())
                                    .post(body).build();
                            Response response = client.newCall(request).execute();
                            JSONObject obj = new JSONObject(json);
                            String id = obj.getString("id");

                            String res = response.body().string();
                            res = isDotNet(res);

                            JSONArray array = new JSONArray(res);
                            if (array.length() > 0) {
                                if (array.getJSONArray(0).length() > 0) {
                                    JSONObject objvisit = array.getJSONArray(0).getJSONObject(0);
                                    if (objvisit != null) {
                                        Log.e("resvisitres", " " + objvisit.toString());
                                        ContentValues c = new ContentValues();
                                        c.put(DatabaseHelper.VISITID, objvisit.getInt("id"));
                                        c.put(DatabaseHelper.VISIT, objvisit.getInt("visit"));
                                        c.put(DatabaseHelper.SYNC, 1);
                                        DatabaseHelper.getInstance(context).updatevisit(c, id);

                                        ContentValues c1 = new ContentValues();
                                        c1.put(DatabaseHelper.VISITID, objvisit.getInt("id"));
                                        DatabaseHelper.getInstance(context).updatetemplatevisitid(c1, id);
                                        Log.e("visitadded ", "visitadded ");
                                    }
                                }
                            }
                        }
                        JSONArray array = DatabaseHelper.getInstance(context).getnotsync();
                        for (int i = 0; i < array.length(); i++) {
                            try {
                                JSONObject arrayobj = array.getJSONObject(i);
                                JSONObject obj = new JSONObject(array.getJSONObject(i).getString("jsondata"));
                                if (obj.has("QuestionData")) {
                                    JSONArray array1 = obj.getJSONArray("QuestionData");
                                    JSONObject mainobj = new JSONObject();
                                    mainobj.put("deptid", obj.getString("deptid"));
                                    mainobj.put("locid", obj.getString("locid"));
                                    mainobj.put("VisitNo", arrayobj.getString(DatabaseHelper.VISITID));
                                    mainobj.put("VisitDate", obj.getString("VisitDate"));
                                    mainobj.put("DoctorId", obj.getString("DoctorId"));
                                    mainobj.put("searchid", arrayobj.getString(DatabaseHelper.Patientid));
                                    mainobj.put("Group", obj.getString("Group"));
                                    mainobj.put("TemplateId", obj.getString("TemplateId"));
                                    mainobj.put("TempName", obj.getString("TempName"));
                                    mainobj.put("AddmitionNo", obj.getString("AddmitionNo"));
                                    mainobj.put("connectionid", Constants.projectid);
                                    mainobj.put("data", array1.toString());
                                    //   Log.e("resrefreshdata", "refreshdata " + mainobj.toString());
                                    try {
                                        String json = mainobj.toString();
                                        RequestBody body = RequestBody.create(Constants.JSON, json);
                                        Request request = new Request.Builder()
                                                .url(URLS.SaveTemplateQuestionAnswer())
                                                .post(body).build();
                                        Response response = client.newCall(request).execute();
                                        GlobalValues.TEMP_STR = response.body().string();
                                        isDotNet();
                                        final int code = response.code();
                                        publishBroadcast(code, Connection.SaveTemplateQuestionAnswer.ordinal());
                                        Log.e("res ", "res " + GlobalValues.TEMP_STR);
                                        // if (flag == 1) {
                                        JSONObject obj1 = new JSONObject(json);
                                        DatabaseHelper.getInstance(context).updatetemplatedatasync(obj1.getString("VisitNo"), obj1.getString("searchid"));
                                        //}
                                    } catch (Exception e) {
                                        Log.i(this.getClass().getSimpleName(), "" + e.toString());
                                        return;
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        JSONArray arrayapt = DatabaseHelper.getInstance(context).getofflineappointment();
                        for (int i = 0; i < arrayapt.length(); i++) {
                            try {
                                JSONObject arrayobj = arrayapt.getJSONObject(i);
                                String id = arrayobj.getString("id");
                                arrayobj.put("id", 0);
                                arrayobj.put("searchid", arrayobj.getString(DatabaseHelper.Patientid));
                                arrayobj.put("connectionid", Constants.projectid);
                                Log.e("obj ", "obj " + arrayobj.toString());
                                RequestBody body = RequestBody.create(Constants.JSON, arrayobj.toString());
                                Request request = new Request.Builder()
                                        .url(URLS.Bookappointment())
                                        .post(body).build();
                                Response response = client.newCall(request).execute();
                                GlobalValues.TEMP_STR = response.body().string();
                                isDotNet();
                                final int code = response.code();
                                Log.e("resdata ", "data " + code + " " + GlobalValues.TEMP_STR);
                                ContentValues c = new ContentValues();
                                c.put(DatabaseHelper.SYNC, 1);
                                DatabaseHelper.getInstance(context).updateappointment(c, id);
                            } catch (Exception e) {
                                Log.i(this.getClass().getSimpleName(), "" + e.toString());
                                return;
                            }
                        }
                        publishBroadcast(200, Connection.Offllinedatasaved.ordinal());
                    } catch (Exception e) {
                        Log.i(this.getClass().getSimpleName(), "" + e.toString());
                        return;
                    }

                }
            });
            thread.start();
        } else {
            publishBroadcast(Constants.STATUS_OK,
                    Connection.NO_INTERNET.ordinal());
        }
    }

    public void SavePatient(final String json, final int isofflinesync) {
        Log.e("ptdata",json);
        Log.e("url ",URLS.Save_Patient());
        if (InternetUtils.getInstance(context).available()) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        RequestBody body = RequestBody.create(Constants.JSON, json);
                        Request request = new Request.Builder()
                                .url(URLS.Save_Patient())
                                .post(body).build();
                        Response response = client.newCall(request).execute();
                        String res = response.body().string();
                        res = isDotNet(res);
                        final int code = response.code();
                        Log.e("patient res", "patient res " +code+" "+ res);
                       /* if (isofflinesync == 1 && code == Constants.STATUS_OK) {
                            JSONObject obj = new JSONObject(json);
                            String id = obj.getString("id");

                            String res = response.body().string();
                            res = isDotNet(res);
                            Log.e("id ", "id " + id + " " + res);
                            JSONArray array = new JSONArray(res);
                            if (array.length() > 0) {
                                JSONArray array1 = array.getJSONArray(0);
                                if (array1.length() > 0) {
                                    int patientid = array1.getJSONObject(0).getInt("Patientid");
                                    ContentValues c = new ContentValues();
                                    c.put(DatabaseHelper.Patientid, patientid);
                                    c.put(DatabaseHelper.SYNC, 1);
                                    DatabaseHelper.getInstance(context).updatepatient(c, id);
                                    ContentValues c1 = new ContentValues();
                                    c1.put(DatabaseHelper.Patientid, patientid);
                                    DatabaseHelper.getInstance(context).updatevisitpatientid(c1, id);
                                    DatabaseHelper.getInstance(context).updatetemplatepatientid(c1, id);
                                }
                            }
                        } else {
                            GlobalValues.TEMP_STR = response.body().string();
                            Log.e("res4 ", "res " + GlobalValues.TEMP_STR);
                            isDotNet();
                            publishBroadcast(code, Connection.SavePatient.ordinal());
                        }*/

                    } catch (Exception e) {
                        Log.i(this.getClass().getSimpleName(), "" + e.toString());
                        return;
                    }
                }
            });
            thread.start();
        } else {
            publishBroadcast(Constants.STATUS_OK,
                    Connection.NO_INTERNET.ordinal());
        }
    }

    public void SaveRx(final String json) {
        //    Log.e("data ", "data " + URLS.SaveRx() + " " + json);
        if (InternetUtils.getInstance(context).available()) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        RequestBody body = RequestBody.create(Constants.JSON, json);
                        Request request = new Request.Builder()
                                .url(URLS.SaveRx())
                                .post(body).build();

                        Response response = client.newCall(request).execute();
                        GlobalValues.TEMP_STR = response.body().string();
                          Log.e("GlobalValues ", "GlobalValuesRx " + GlobalValues.TEMP_STR);
                        isDotNet();
                        final int code = response.code();
                        publishBroadcast(code, Connection.SaveRX.ordinal());
                    } catch (Exception e) {
                        Log.i(this.getClass().getSimpleName(), "" + e.toString());
                        return;
                    }
                }
            });
            thread.start();
        } else {
            publishBroadcast(Constants.STATUS_OK,
                    Connection.NO_INTERNET.ordinal());
        }
    }

    public void gettitle() {
        if (InternetUtils.getInstance(context).available()) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Request request = new Request.Builder().url(URLS.GET_Master("title", "\"\"")).build();
                        Response response = client.newCall(request).execute();
                        GlobalValues.TEMP_STR = response.body().string();
                        isDotNet();
                        final int code = response.code();
                        publishBroadcast(code, Connection.GETTitle.ordinal());
                    } catch (Exception e) {
                        Log.e("error ", "" + e.toString());
                        return;
                    }
                }
            });
            thread.start();
        } else {
            publishBroadcast(Constants.STATUS_OK, Connection.NO_INTERNET.ordinal());
        }
    }

    public void getMaster(final ArrayList<String> mdataset) {
        if (InternetUtils.getInstance(context).available()) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        DatabaseHelper.getInstance(context).delete(DatabaseHelper.TABLE_MASTER);
                        for (int i = 0; i < mdataset.size(); i++) {
                            //Log.e("mdataset data ","mdatset "+mdataset.get(i)+" url "+URLS.GET_Master(mdataset.get(i), "\"\""));
                            try {
                                String res="";
                                try {
                                    Request request = new Request.Builder().url(URLS.GET_Master(mdataset.get(i), "\"\"")).build();
                                    Response response = client.newCall(request).execute();
                                     res = response.body().string();
                                    res = isDotNet(res);
                                    final int code = response.code();
                                }catch (Exception e)
                                {
                                    Log.e("errrrror","error "+e.toString()+" "+mdataset.get(i));
                                }

                                Log.e("DBMaster ", "DBMaster " +URLS.GET_Master(mdataset.get(i), "\"\"")+" "+ res);
                                DatabaseHelper.getInstance(context).savemasterdata(mdataset.get(i), res);
                                JSONArray array = new JSONObject(res).getJSONObject("root").optJSONArray("subroot");
                                if (array != null) {
                                    for (int j = 0; j < array.length(); j++) {
                                        Log.e("mdataset.get(i)","data "+mdataset.get(i));
                                        JSONObject obj = array.getJSONObject(j);
                                        if (mdataset.get(i).equalsIgnoreCase("Test")) {
                                            DatabaseHelper.getInstance(context).savemaster(obj.getString("Name"), obj.getString("TestId"), 1, mdataset.get(i));
                                        } else if (mdataset.get(i).equalsIgnoreCase("Lab")) {
                                            DatabaseHelper.getInstance(context).savemaster(obj.getString("Name"), obj.getString("LabId"), 1, mdataset.get(i));
                                        }else if( mdataset.get(i).equalsIgnoreCase("city")){
                                            Log.e("in city ","in city "+obj.toString());
                                            DatabaseHelper.getInstance(context).savemaster(obj.toString(), "0", 1, mdataset.get(i));

                                        }else if (mdataset.get(i).equalsIgnoreCase("state") || mdataset.get(i).equalsIgnoreCase("city") || mdataset.get(i).equalsIgnoreCase("country") || mdataset.get(i).equalsIgnoreCase("area")|| mdataset.get(i).equalsIgnoreCase("contact grp")|| mdataset.get(i).equalsIgnoreCase("empname")) {
                                          Log.e("obj ","obj "+obj.toString());
                                            DatabaseHelper.getInstance(context).savemaster(obj.toString(), "0", 1, mdataset.get(i));
                                        } else if (mdataset.get(i).equalsIgnoreCase("billing grp")) {
                                            DatabaseHelper.getInstance(context).savemaster(obj.toString(), obj.getString("Categoryid"), 1, mdataset.get(i));
                                        }else {
                                            DatabaseHelper.getInstance(context).savemaster(obj.getString("Name"), obj.getString("Categoryid"), 1, mdataset.get(i));
                                        }
                                    }
                                } else {
                                    JSONObject obj = new JSONObject(res).getJSONObject("root").optJSONObject("subroot");
                                    if (mdataset.get(i).equalsIgnoreCase("Test")) {
                                        DatabaseHelper.getInstance(context).savemaster(obj.getString("Name"), obj.getString("TestId"), 1, mdataset.get(i));

                                    } else if (mdataset.get(i).equalsIgnoreCase("Lab")) {
                                        DatabaseHelper.getInstance(context).savemaster(obj.getString("Name"), obj.getString("LabId"), 1, mdataset.get(i));

                                    } else {
                                        DatabaseHelper.getInstance(context).savemaster(obj.getString("Name"), obj.getString("Categoryid"), 1, mdataset.get(i));
                                    }
                                }
                            } catch (Exception e) {
                                Log.e("errorindb1 ","error "+e.toString());
                                e.printStackTrace();
                            }
                        }
                        publishBroadcast(Constants.STATUS_OK, Connection.GET_MASTERDATA.ordinal());
                    } catch (Exception e) {
                        Log.e("errorindb ","error "+e.toString());
                        return;
                    }
                }
            });
            thread.start();
        } else {
            publishBroadcast(Constants.STATUS_OK, Connection.NO_INTERNET.ordinal());
        }
    }

    public void getpurpose() {
        if (InternetUtils.getInstance(context).available()) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Request request = new Request.Builder().url(URLS.GET_Master("purpose", "\"\"")).build();
                        Response response = client.newCall(request).execute();
                        GlobalValues.TEMP_STR = response.body().string();
                        isDotNet();
                        final int code = response.code();
                        publishBroadcast(code, Connection.GETPurpose.ordinal());
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                }
            });
            thread.start();
        } else {
            publishBroadcast(Constants.STATUS_OK, Connection.NO_INTERNET.ordinal());
        }
    }


    public void getQuestionTemplates(final String json) {
        Log.e("templateq","templateq "+json+" "+URLS.getQuestionTemplates());
        if (InternetUtils.getInstance(context).available()) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        RequestBody body = RequestBody.create(Constants.JSON, json);
                        Request request = new Request.Builder()
                                .url(URLS.getQuestionTemplates())
                                .post(body).build();
                        Response response = client.newCall(request).execute();
                        GlobalValues.TEMP_STR = response.body().string();
                        isDotNet();
                        final int code = response.code();
                        publishBroadcast(code, Connection.getQuestionTemplates.ordinal());
                        // Log.e("response ","data "+GlobalValues.TEMP_STR);
                    } catch (Exception e) {
                        Log.i(this.getClass().getSimpleName(), "" + e.toString());
                        return;
                    }
                }
            });
            thread.start();
        } else {
            publishBroadcast(Constants.STATUS_OK,
                    Connection.NO_INTERNET.ordinal());
        }
    }

    public void saveQuestionTemplates(final String json, final int flag) {
        Log.e("urldata ", "json " + json);
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("urldata ", "url " + URLS.SaveTemplateQuestionAnswer());
        if (InternetUtils.getInstance(context).available()) {
            final Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject obj1 = new JSONObject(json);
                        obj1.put("connectionid", Constants.projectid);

                        RequestBody body = RequestBody.create(Constants.JSON, obj1.toString());
                        Request request = new Request.Builder()
                                .url(URLS.SaveTemplateQuestionAnswer())
                                .post(body).build();
                        Response response = client.newCall(request).execute();
                        GlobalValues.TEMP_STR = response.body().string();
                        isDotNet();
                        final int code = response.code();
                        publishBroadcast(code, Connection.SaveTemplateQuestionAnswer.ordinal());
                        Log.e("urldata ", "res " + GlobalValues.TEMP_STR);
                        if (flag == 1) {
                            JSONObject obj = new JSONObject(json);
                            DatabaseHelper.getInstance(context).updatetemplatedatasync(obj.getString("VisitNo"), obj.getString("searchid"));
                        }
                    } catch (Exception e) {
                        Log.i(this.getClass().getSimpleName(), "" + e.toString());
                        return;
                    }
                }
            });
            thread.start();
        } else {
            publishBroadcast(Constants.STATUS_OK,
                    Connection.NO_INTERNET.ordinal());
        }
    }

    public void savepathology(final String json) {
        Log.e("URLS.SavePathology","URLS.SavePathology "+URLS.SavePathology()+" "+json);
        if (InternetUtils.getInstance(context).available()) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        RequestBody body = RequestBody.create(Constants.JSON, json);
                        Request request = new Request.Builder()
                                .url(URLS.SavePathology())
                                .post(body).build();
                        Response response = client.newCall(request).execute();
                        GlobalValues.TEMP_STR = response.body().string();
                        isDotNet();
                        final int code = response.code();
                        publishBroadcast(code, Connection.savepathology.ordinal());
                        Log.e("response ", "data " + GlobalValues.TEMP_STR);
                    } catch (Exception e) {
                        Log.i(this.getClass().getSimpleName(), "" + e.toString());
                        return;
                    }
                }
            });
            thread.start();
        } else {
            publishBroadcast(Constants.STATUS_OK,
                    Connection.NO_INTERNET.ordinal());
        }
    }

    public void GetTemplateQuestionAnswer(final String json) {
        Log.e("urldata ", "data " + URLS.GetTemplateQuestionAnswer());
        Log.e("urldata", "data " + json);
        if (InternetUtils.getInstance(context).available()) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        RequestBody body = RequestBody.create(Constants.JSON, json);
                        Request request = new Request.Builder()
                                .url(URLS.GetTemplateQuestionAnswer())
                                .post(body).build();
                        Response response = client.newCall(request).execute();
                        GlobalValues.TEMP_STR = response.body().string();
                        Log.d( "Respopnsejson: ",GlobalValues.TEMP_STR);
                        isDotNet();
                        final int code = response.code();
                        publishBroadcast(code, Connection.GetTemplateQuestionAnswer.ordinal());
                        //   Log.e("urlresponse ", "data " + GlobalValues.TEMP_STR);
                    } catch (Exception e) {
                        Log.i(this.getClass().getSimpleName(), "" + e.toString());
                        return;
                    }
                }
            });
            thread.start();
        } else {
            publishBroadcast(Constants.STATUS_OK,
                    Connection.NO_INTERNET.ordinal());
        }
    }

    public void AddVisit(final String json, final int flag) {
     //   Log.e("urldata ", "data " + URLS.AddVisit());
     //  Log.e("urldata", "data " + json);
        if (InternetUtils.getInstance(context).available()) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        RequestBody body = RequestBody.create(Constants.JSON, json);
                        Request request = new Request.Builder()
                                .url(URLS.AddVisit())
                                .post(body).build();
                        Response response = client.newCall(request).execute();
                        if (flag == 1) {
                            JSONObject obj = new JSONObject(json);
                            String id = obj.getString("id");

                            String res = response.body().string();
                            res = isDotNet(res);

                            JSONArray array = new JSONArray(res);
                            if (array.length() > 0) {
                                if (array.getJSONArray(0).length() > 0) {
                                    JSONObject objvisit = array.getJSONArray(0).getJSONObject(0);
                                    if (objvisit != null) {
                                        ContentValues c = new ContentValues();
                                        c.put(DatabaseHelper.VISITID, objvisit.getInt("id"));
                                        c.put(DatabaseHelper.VISIT, objvisit.getInt("visit"));
                                        c.put(DatabaseHelper.SYNC, 1);
                                        DatabaseHelper.getInstance(context).updatevisit(c, id);

                                        ContentValues c1 = new ContentValues();
                                        c1.put(DatabaseHelper.VISITID, objvisit.getInt("id"));
                                        DatabaseHelper.getInstance(context).updatetemplatevisitid(c1, id);
                                        Log.e("visitadded ", "visitadded ");
                                    }
                                }
                            }
                        } else {
                            GlobalValues.TEMP_STR = response.body().string();
                            isDotNet();
                            final int code = response.code();
                            publishBroadcast(code, Connection.AddVisit.ordinal());
                            Log.e("urlresponse ", "data " + GlobalValues.TEMP_STR);
                        }
                    } catch (Exception e) {
                        Log.i(this.getClass().getSimpleName(), "" + e.toString());
                        return;
                    }
                }
            });
            thread.start();
        } else {
            publishBroadcast(Constants.STATUS_OK, Connection.NO_INTERNET.ordinal());
        }
    }


    private void publishBroadcast(final int code, final int ordinal) {
        try {
            Intent intent = new Intent(Constants.BROADCAST_WIZARD);
            intent.putExtra(Constants.BROADCAST_RESPONSE_CODE, code);
            intent.putExtra(Constants.BROADCAST_URL_ACCESS, ordinal);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void getdepartment(final String flag) {
        if (InternetUtils.getInstance(context).available()) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                          Log.e("department", "" + URLS.GET_DEPARTMENT(GlobalValues.branchid, flag));
                        Request request = new Request.Builder().url(URLS.GET_DEPARTMENT(GlobalValues.branchid, flag)).build();
                        Response response = client.newCall(request).execute();
                        GlobalValues.TEMP_STR = response.body().string();
                        isDotNet();
                        final int code = response.code();
                        // if (!flag.equalsIgnoreCase("all")) {
                        // Log.e("getdepartment "," "+GlobalValues.TEMP_STR);
                        JSONArray array = new JSONObject(GlobalValues.TEMP_STR).getJSONObject("root").optJSONArray("subroot");
                        if (array == null) {
                            JSONObject obj = new JSONObject(GlobalValues.TEMP_STR).getJSONObject("root").optJSONObject("subroot");
                            array = new JSONArray();
                            if (obj != null) {

                                array.put(obj);
                            }
                        }
                        DatabaseHelper.getInstance(context).savedata(DatabaseHelper.TABLE_DEPARTMENT, array.toString());
                         /*   if (GlobalValues.profile != null)
                                ConnectionManager.getInstance(context).getlatestappointments(GlobalValues.profile.getPatientid(), "0");
                            else*/
                        //      publishBroadcast(code, Connection.BRANCHINFO.ordinal());
                        /*} else {
                            Log.e("publish", "publish");
                            //    publishBroadcast(code, Connection.DEPARTMENT.ordinal());
                        }*/

                    } catch (Exception e) {
                        Log.e("error ", "error " + e.toString());
                        return;
                    }
                    publishBroadcast(200, Connection.GetTemplateQuestionAnswerfreshdata.ordinal());
                    publishBroadcast(200, Connection.DEPARTMENT.ordinal());

                }
            });
            thread.start();
        } else {
            publishBroadcast(Constants.STATUS_OK,
                    Connection.NO_INTERNET.ordinal());
        }
    }

    public void getvisits(final String patientid) {
        if (InternetUtils.getInstance(context).available()) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Request request = new Request.Builder().url(URLS.GET_VISITS(patientid)).build();
                        Response response = client.newCall(request).execute();
                        GlobalValues.TEMP_STR = response.body().string();
                        Log.e("visitdata", "visitdata " + GlobalValues.TEMP_STR);
                        isDotNet();
                        final int code = response.code();
                        publishBroadcast(code, Connection.GET_VISITS.ordinal());
                    } catch (Exception e) {
                        Log.e("error ", "" + e.toString());
                        return;
                    }
                }
            });
            thread.start();
        } else {
            publishBroadcast(Constants.STATUS_OK, Connection.NO_INTERNET.ordinal());
        }
    }

    public void getpathalogy(final String branchid, final String patientid, final String flag) {
        Log.e("url ", "url " + URLS.GET_PATHALOGY(branchid, patientid, flag));
        if (InternetUtils.getInstance(context).available()) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Request request = new Request.Builder().url(URLS.GET_PATHALOGY(branchid, patientid, flag)).build();
                        Response response = client.newCall(request).execute();
                        GlobalValues.TEMP_STR = response.body().string();
                        isDotNet();
                        final int code = response.code();
                        publishBroadcast(code, Connection.GET_Pathalogy.ordinal());
                    } catch (Exception e) {
                        return;
                    }
                }
            });
            thread.start();
        } else {
            publishBroadcast(Constants.STATUS_OK, Connection.NO_INTERNET.ordinal());
        }
    }

    public void getInvestigationmaster(final String branchid, final String type) {
        if (InternetUtils.getInstance(context).available()) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Request request = new Request.Builder().url(URLS.GET_Investigationmaster(branchid, type)).build();
                        Response response = client.newCall(request).execute();
                        GlobalValues.TEMP_STR = response.body().string();
                        isDotNet();
                        final int code = response.code();
                        publishBroadcast(code, Connection.GET_Investigationmaster.ordinal());
                    } catch (Exception e) {
                        Log.e("error ", "" + e.toString());
                        return;
                    }
                }
            });
            thread.start();
        } else {
            publishBroadcast(Constants.STATUS_OK, Connection.NO_INTERNET.ordinal());
        }
    }

    public void GetTemplateQuestionAnswerFreshdata(final ArrayList<Template> mdataset) {
        if (InternetUtils.getInstance(context).available()) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int i = 0; i < mdataset.size(); i++) {
                            try {
                                JSONObject obj = new JSONObject();
                                obj.put("TemplateId", mdataset.get(i).getTemplateId());
                                obj.put("patientid", 0);
                                obj.put("VisitNo", 0);
                                obj.put("connectionid", Constants.projectid);
                                RequestBody body = RequestBody.create(Constants.JSON, obj.toString());
                                Request request = new Request.Builder()
                                        .url(URLS.GetTemplateQuestionAnswer())
                                        .post(body).build();
                                Response response = client.newCall(request).execute();
                                String res = response.body().string();
                                res = isDotNet(res);
                                final int code = response.code();
                                JSONObject answerobjdata = new JSONObject(res).getJSONObject("root").getJSONObject("subroot");
                                DatabaseHelper.getInstance(context).savefreshtemplatedata(mdataset.get(i).getTemplateId(), answerobjdata.toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        getdepartment("all");
                    } catch (Exception e) {
                        Log.i(this.getClass().getSimpleName(), "" + e.toString());
                        getdepartment("all");
                        return;
                    }
                }
            });
            thread.start();
        } else {
            publishBroadcast(Constants.STATUS_OK,
                    Connection.NO_INTERNET.ordinal());
        }
    }

    public void savedrugmaster(final String json) {
        if (InternetUtils.getInstance(context).available()) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject obj = new JSONObject(json);
                        JSONObject dataobject = new JSONObject();
                        JSONObject root = new JSONObject();
                        JSONObject subroot = new JSONObject();
                        subroot.put("subroot", obj);
                        root.put("root", subroot);
                        dataobject.put("data", root.toString());
                        dataobject.put("connectionid", Constants.projectid);
                        Log.e("datasend ", "datasend " + dataobject.toString());

                        RequestBody body = RequestBody.create(Constants.JSON, dataobject.toString());
                        Request request = new Request.Builder()
                                .url(URLS.Save_drugmaster())
                                .post(body).build();
                        Response response = client.newCall(request).execute();
                        GlobalValues.TEMP_STR = response.body().string();
                        isDotNet();
                        final int code = response.code();
                        ContentValues c = new ContentValues();

                        c.put(DatabaseHelper.SYNC, 0);
                        DatabaseHelper.getInstance(context).updatemaster(c, obj.getString("id"));
                        Log.e("response ", "data " + GlobalValues.TEMP_STR);
                    } catch (Exception e) {
                        Log.i(this.getClass().getSimpleName(), "" + e.toString());
                        return;
                    }
                }
            });
            thread.start();
        } else {
            publishBroadcast(Constants.STATUS_OK,
                    Connection.NO_INTERNET.ordinal());
        }
    }

    public void getappointments(final String ptid, final String identifire, final String dbid) {
        if (InternetUtils.getInstance(context).available()) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.e("data ", "dataGetappointmenthistory " + Getappointmenthistory(ptid, identifire));
                        Request request = new Request.Builder().url(Getappointmenthistory(ptid, identifire)).build();
                        Response response = client.newCall(request).execute();
                        GlobalValues.TEMP_STR = response.body().string();
                        isDotNet();
                        final int code = response.code();
                        try {
                            // Log.e("res ", "res " + GlobalValues.TEMP_STR);
                            JSONArray array = new JSONArray(GlobalValues.TEMP_STR);
                            if (array.length() > 0) {
                                JSONArray array1 = array.getJSONArray(0);
                                Gson gson = new Gson();
                                Type type = new TypeToken<ArrayList<Appointmentdatav1>>() {
                                }.getType();
                                ArrayList<Appointmentdatav1> data = gson.fromJson(array1.toString(), type);
                                DatabaseHelper.getInstance(context).saveallappointment(data, ptid, dbid);
                              /*  for (int i = 0; i < data.size(); i++) {
                                    ContentValues c = new ContentValues();
                                    c.put(DatabaseHelper.appointment, data.get(i).getAppointment_id());
                                    c.put(DatabaseHelper.docname, data.get(i).getDocname());
                                    c.put(DatabaseHelper.FirstName, data.get(i).getFirstName());
                                    c.put(DatabaseHelper.startDate, data.get(i).getAptdate());
                                    c.put(DatabaseHelper.status, data.get(i).getStatusmsg());
                                    c.put(DatabaseHelper.purpose, data.get(i).getPurpose());
                                    c.put(DatabaseHelper.DESCRIPTION, data.get(i).getDescription());
                                    c.put(DatabaseHelper.SAVEDTIME, DateUtils.getSqliteTime());

                                }*/
                            }
                        } catch (Exception e) {
                            return;
                        }
                        if (identifire.equalsIgnoreCase("0")) {
                            publishBroadcast(code, Connection.Appointmenthistory.ordinal());
                            try {
                                Intent intent = new Intent("getAppointment");
                                intent.putExtra(Constants.BROADCAST_RESPONSE_CODE, code);
                                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        } else {
            publishBroadcast(Constants.STATUS_OK, Connection.NO_INTERNET.ordinal());
        }
    }


    public void getmenubyspeciality(final String speciality) {
        Log.e("speciality ","speciality "+URLS.GetMenubyspeciality(speciality));
        if (InternetUtils.getInstance(context).available()) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Request request = new Request.Builder().url(URLS.GetMenubyspeciality(speciality)).build();
                        Response response = client.newCall(request).execute();
                        GlobalValues.TEMP_STR = response.body().string();
                        Log.e("visitdata", "visitdata " + GlobalValues.TEMP_STR);
                        isDotNet();
                        DatabaseHelper.getInstance(context).delete("menubyspeciality");
                        DatabaseHelper.getInstance(context).savedata("menubyspeciality",GlobalValues.TEMP_STR);

                        final int code = response.code();
                        publishBroadcast(code, Connection.GetMenuByspeciality.ordinal());
                    } catch (Exception e) {
                        Log.e("error ", "" + e.toString());
                        return;
                    }
                }
            });
            thread.start();
        } else {
            publishBroadcast(Constants.STATUS_OK, Connection.NO_INTERNET.ordinal());
        }
    }

    public void Save_patient(final String json) {
        Log.e("ptdata",json);
        Log.e("url ",URLS.Save_Patient());
        if (InternetUtils.getInstance(context).available()) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        RequestBody body = RequestBody.create(Constants.JSON, json);
                        Request request = new Request.Builder()
                                .url(URLS.Save_patient())
                                .post(body).build();
                        Response response = client.newCall(request).execute();
                        GlobalValues.TEMP_STR = response.body().string();
                        isDotNet();
                        final int code = response.code();
                        publishBroadcast(code, Connection.Save_patient.ordinal());
                           Log.e("response ", "data " + GlobalValues.TEMP_STR);
                    } catch (Exception e) {
                        Log.i(this.getClass().getSimpleName(), "" + e.toString());
                        return;
                    }
                }
            });
            thread.start();
        } else {
            publishBroadcast(Constants.STATUS_OK,
                    Connection.NO_INTERNET.ordinal());
        }
    }
}
