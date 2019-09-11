package com.healthbank.classes;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by it1 on 8/8/2018.
 */

public class Lab {
    String Test = "";
    String Result = "";
    String Normal = "";
    ArrayList<String> mdataset = new ArrayList<>();

    public Lab() {

    }

    public Lab(JSONObject obj) {
        try {
            this.Test = obj.getString("Test");
            this.Result = obj.getString("Result");
            this.Normal = obj.getString("Normal");

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

    public String getTest() {
        return Test;
    }

    public void setTest(String test) {
        Test = test;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    public String getNormal() {
        return Normal;
    }

    public void setNormal(String normal) {
        Normal = normal;
    }


    public ArrayList<String> getMdataset() {
        return mdataset;
    }

    public void setMdataset(ArrayList<String> mdataset) {
        this.mdataset = mdataset;
    }
}
