package com.healthbank.classes;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by it1 on 8/14/2018.
 */

public class VitalHeader {
    String Title = "";
    ArrayList<Vitalsdata> mdataset = new ArrayList<>();
    String time = "";

    public VitalHeader(JSONObject obj) {
        try {
            this.Title = obj.getString("date");
            JSONArray array = new JSONArray(obj.getString("jsondata"));
            for (int i = 0; i < array.length(); i++) {
                this.mdataset.add(new Vitalsdata(array.getJSONObject(i)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public ArrayList<Vitalsdata> getMdataset() {
        return mdataset;
    }

    public void setMdataset(ArrayList<Vitalsdata> mdataset) {
        this.mdataset = mdataset;
    }
}
