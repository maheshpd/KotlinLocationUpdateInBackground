package com.healthbank.classes;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by it1 on 8/20/2018.
 */

public class Radiology {
    String Title = "";
    String Result = "";
    ArrayList<String> mdataset = new ArrayList<>();

    public Radiology() {

    }

    public Radiology(JSONObject obj) {
        try {
            this.Title = obj.getString("Title");
            this.Result = obj.getString("Result");

            if (obj.has("imagepath")) {
                JSONArray array = obj.getJSONArray("imagepath");
                for (int i = 0; i < array.length(); i++) {
                    mdataset.add(array.getJSONObject(i).getString("imagepath"));
                }
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

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    public ArrayList<String> getMdataset() {
        return mdataset;
    }

    public void setMdataset(ArrayList<String> mdataset) {
        this.mdataset = mdataset;
    }
}
