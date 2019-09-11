package com.healthbank.classes;

import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by it1 on 11/13/2017.
 */

public class Appointmentdata implements Serializable {
    long id = 0;
    String ptname = "kirti";
    String mobnumber = "";
    String drname = "";
    String category = "";
    String procedure = "";
    String notes = "";
    String ptnotificationmode = "";
    String drnotificationmode = "";
    String date = "";
    String starttime = "";
    String endtime = "";
    Calendar startcal = Calendar.getInstance();
    Calendar endcal = Calendar.getInstance();
    String dateformat = "MM/dd/yy";
    String timeformat = "hh:mm a";
    boolean issection = false;
    String appointmentid = "0";
    String patientid = "0";
    String status = "confirm";
    boolean isslotavailable = true;

    public Appointmentdata() {

    }

    public Appointmentdata(JSONObject obj) {
        try {
            this.ptname = obj.getString("ptname");
            this.mobnumber = obj.getString("mobnumber");
            this.drname = obj.getString("drname");
            this.date = obj.getString("date");
            this.starttime = obj.getString("starttime");
            this.endtime = obj.getString("endtime");
            this.category = obj.getString("category");
            this.procedure = obj.getString("procedure");
            this.notes = obj.getString("notes");
            this.drnotificationmode = obj.getString("drnotificationmode");
            this.ptnotificationmode = obj.getString("ptnotificationmode");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean getAvailable(Calendar cal, ArrayList<Appointmentdata> data) {
        String myFormat = "hh:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        String starttm = sdf.format(cal.getTime());
        for (int i = 0; i < data.size(); i++) {
            if (starttm.equalsIgnoreCase(data.get(i).getStarttime())) {
                return false;
            }
        }
        return true;
    }

    public String getDateformat() {
        return dateformat;
    }

    public void setDateformat(String dateformat) {
        this.dateformat = dateformat;
    }

    public String getTimeformat() {
        return timeformat;
    }

    public void setTimeformat(String timeformat) {
        this.timeformat = timeformat;
    }

    public boolean isIssection() {
        return issection;
    }

    public void setIssection(boolean issection) {
        this.issection = issection;
    }

    public boolean isIsslotavailable() {
        return isslotavailable;
    }

    public void setIsslotavailable(boolean isslotavailable) {
        this.isslotavailable = isslotavailable;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPtname() {
        return ptname;
    }

    public void setPtname(String ptname) {
        this.ptname = ptname;
    }

    public String getMobnumber() {
        return mobnumber;
    }

    public void setMobnumber(String mobnumber) {
        this.mobnumber = mobnumber;
    }

    public String getDrname() {
        return drname;
    }

    public void setDrname(String drname) {
        this.drname = drname;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProcedure() {
        return procedure;
    }

    public void setProcedure(String procedure) {
        this.procedure = procedure;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPtnotificationmode() {
        return ptnotificationmode;
    }

    public void setPtnotificationmode(String ptnotificationmode) {
        this.ptnotificationmode = ptnotificationmode;
    }

    public String getDrnotificationmode() {
        return drnotificationmode;
    }

    public void setDrnotificationmode(String drnotificationmode) {
        this.drnotificationmode = drnotificationmode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Calendar getStartcal() {
        return startcal;
    }

    public void setStartcal(Calendar startcal) {
        this.startcal = startcal;
    }

    public Calendar getEndcal() {
        endcal = Calendar.getInstance();

        /*try {
            endcal.setTime(Calendar.DATE, new SimpleDateFormat(dateformat).parse(starttime));
        }catch (Exception e)
        {
            e.printStackTrace();
        }*/
        return endcal;
    }

    public void setEndcal(Calendar endcal) {
        this.endcal = endcal;
    }

    public String getStarttime() {
        return starttime;

    }

    public void setStarttime(String starttime) {

        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getAppointmentid() {
        return appointmentid;
    }

    public void setAppointmentid(String appointmentid) {
        this.appointmentid = appointmentid;
    }

    public String getPatientid() {
        return patientid;
    }

    public void setPatientid(String patientid) {
        this.patientid = patientid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
