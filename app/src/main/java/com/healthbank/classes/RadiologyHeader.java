package com.healthbank.classes;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by it1 on 8/20/2018.
 */

public class RadiologyHeader {
    String Title = "";
    ArrayList<Radiology> mdataset = new ArrayList<>();
    ArrayList<String> imagedata = new ArrayList<>();

    public RadiologyHeader(JSONObject obj) {
        try {
            this.Title = obj.getString("date");
            if (obj.has("imagepath")) {
                JSONArray array = new JSONArray(obj.getString("imagepath"));
                for (int i = 0; i < array.length(); i++) {
                    if(array.getJSONObject(i).has("imagepath"))
                        imagedata.add(array.getJSONObject(i).getString("imagepath"));
                }
            }

            JSONArray array = new JSONArray(obj.getString("jsondata"));
            for (int i = 0; i < array.length(); i++) {
                mdataset.add(new Radiology(array.getJSONObject(i)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public ArrayList<Radiology> getMdataset() {
        return mdataset;
    }

    public void setMdataset(ArrayList<Radiology> mdataset) {
        this.mdataset = mdataset;
    }
}
