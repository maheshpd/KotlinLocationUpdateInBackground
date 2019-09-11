package com.healthbank.classes;

import android.content.ContentValues;
import android.util.Log;

import com.healthbank.database.DatabaseHelper;

public class Visits {
    int visitid = 0;
    int visit = 0;
    String visitdate = "";
    String drname = "";
    boolean isselected = false;
    int sync = 1;
    int DBPatientid = 0;
    int Patientid = 0;
    int id = 0;

    public Visits() {

    }

    public Visits(int visitid) {
        this.visitid = visitid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatientid() {
        return Patientid;
    }

    public void setPatientid(int patientid) {
        Patientid = patientid;
    }

    public ContentValues getcontentvalues() {
        Log.e("data ", "data contentvalues " + DBPatientid + "\n" + visitid + "\n" + visit + "\n" + Patientid + "\n");
        ContentValues c = new ContentValues();
        c.put(DatabaseHelper.VISITID, visitid);
        c.put(DatabaseHelper.VISIT, visit);
        c.put(DatabaseHelper.VISITDATE, visitdate);
        c.put(DatabaseHelper.DRNAME, drname);
        c.put(DatabaseHelper.SYNC, sync);
        c.put(DatabaseHelper.DBPatientid, DBPatientid);
        c.put(DatabaseHelper.Patientid, Patientid);
        return c;
    }

    public int getSync() {
        return sync;
    }

    public void setSync(int sync) {
        this.sync = sync;
    }

    public int getDBPatientid() {
        return DBPatientid;
    }

    public void setDBPatientid(int DBPatientid) {
        this.DBPatientid = DBPatientid;
    }

    public boolean isIsselected() {
        return isselected;
    }

    public void setIsselected(boolean isselected) {
        this.isselected = isselected;
    }

    public int getVisitid() {
        return visitid;
    }

    public void setVisitid(int visitid) {
        this.visitid = visitid;
    }

    public int getVisit() {
        return visit;
    }

    public void setVisit(int visit) {
        this.visit = visit;
    }

    public String getVisitdate() {
        return visitdate;
    }

    public void setVisitdate(String visitdate) {
        this.visitdate = visitdate;
    }

    public String getDrname() {
        return drname;
    }

    public void setDrname(String drname) {
        this.drname = drname;
    }

    @Override
    public String toString() {
        return "Visit " + visit;
    }
}
