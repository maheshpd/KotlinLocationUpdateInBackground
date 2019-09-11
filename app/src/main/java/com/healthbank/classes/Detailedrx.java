package com.healthbank.classes;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Detailedrx {
    String Title = "";
    ArrayList<Drug> mdataset = new ArrayList<>();
    ArrayList<String> imagedata = new ArrayList<>();
    long visit_no = -1;
    String visitdate = "";
    long dbvisitid = 0;

    public Detailedrx() {

    }

    public Detailedrx(JSONObject obj) {
        try {
            this.Title = obj.getString("date");
            JSONArray array = new JSONArray(obj.getString("jsondata"));
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Drug>>() {
            }.getType();
            ArrayList<Drug> data = gson.fromJson(array.toString(), type);
            mdataset.addAll(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long getDbvisitid() {
        return dbvisitid;
    }

    public void setDbvisitid(long dbvisitid) {
        this.dbvisitid = dbvisitid;
    }

    public long getVisit_no() {
        return visit_no;
    }

    public void setVisit_no(long visit_no) {
        this.visit_no = visit_no;
    }

    public String getVisitdate() {
        return visitdate;
    }

    public void setVisitdate(String visitdate) {
        this.visitdate = visitdate;
    }

    public ArrayList<String> getImagedata() {
        return imagedata;
    }

    public void setImagedata(ArrayList<String> imagedata) {
        this.imagedata = imagedata;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public ArrayList<Drug> getMdataset() {
        return mdataset;
    }

    public void setMdataset(ArrayList<Drug> mdataset) {
        this.mdataset = mdataset;
    }
}
