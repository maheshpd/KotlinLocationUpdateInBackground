package com.healthbank.classes;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Day {
    String DayName = "";
    String Day = "";
    JSONObject session;


    public Day(JSONObject obj) {
        try {
            Log.e("obj", "obj " + obj.toString());
            if (obj.has("DayName"))
                this.DayName = obj.getString("DayName");

            if (obj.has("Day"))
                this.Day = obj.getString("Day");
            if (obj.has("session"))
                this.session = obj.getJSONObject("session");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getDayName() {
        return DayName;
    }

    public void setDayName(String dayName) {
        DayName = dayName;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }

    public JSONObject getSession() {
        return session;
    }

    public void setSession(JSONObject session) {
        this.session = session;
    }

    public ArrayList<timingslots> getslots() {
        ArrayList<timingslots> data = new ArrayList<>();
        try {

            if (getSession() != null) {
                Log.e("data222", "data22" + getSession().toString());
                JSONArray array = getSession().getJSONArray("tbl_timingslots");
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<timingslots>>() {
                }.getType();
                ArrayList<timingslots> data1 = gson.fromJson(array.toString(), type);
                if (data1 != null)
                    data.addAll(data1);
            } else Log.e("data ", "data null");
        } catch (Exception e) {
            e.printStackTrace();
            //Log.e("error ","error "+e.toString());
        }
        return data;
    }


}
