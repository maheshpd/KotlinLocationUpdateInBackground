package com.healthbank.classes;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by it1 on 8/9/2018.
 */
public class AdviceHeader {
    String Title = "";
    ArrayList<Advice> mdataset = new ArrayList<>();

    public AdviceHeader(JSONObject obj) {
        try {
            this.Title = obj.getString("date");
            JSONArray array = new JSONArray(obj.getString("jsondata"));
            for (int i = 0; i < array.length(); i++) {
                this.mdataset.add(new Advice(array.getJSONObject(i)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public ArrayList<Advice> getMdataset() {
        return mdataset;
    }

    public void setMdataset(ArrayList<Advice> mdataset) {
        this.mdataset = mdataset;
    }
}
