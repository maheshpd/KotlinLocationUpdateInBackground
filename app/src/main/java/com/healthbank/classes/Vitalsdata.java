package com.healthbank.classes;

import org.json.JSONObject;

/**
 * Created by it1 on 6/1/2018.
 */

public class Vitalsdata {
    String lowbloodpressure = "";
    String highbloodpressure = "";
    String pulse = "";
    String tempurature = "";
    String respirationrate = "";
    String weight = "";
    String height = "";
    String time = "";

    public Vitalsdata() {

    }

    public Vitalsdata(JSONObject obj) {
        try {
            if (obj.has("lowbloodpressurel"))
                lowbloodpressure = obj.getString("lowbloodpressurel");
            highbloodpressure = obj.getString("highbloodpressure");
            pulse = obj.getString("pulse");
            tempurature = obj.getString("tempurature");
            respirationrate = obj.getString("respirationrate");
            weight = obj.getString("weight");
            height = obj.getString("height");

            if (obj.has("time"))
                this.time = obj.getString("time");

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

    public String getLowbloodpressure() {
        return lowbloodpressure;
    }

    public void setLowbloodpressure(String lowbloodpressure) {
        this.lowbloodpressure = lowbloodpressure;
    }

    public String getHighbloodpressure() {
        return highbloodpressure;
    }

    public void setHighbloodpressure(String highbloodpressure) {
        this.highbloodpressure = highbloodpressure;
    }

    public String getPulse() {
        return pulse;
    }

    public void setPulse(String pulse) {
        this.pulse = pulse;
    }

    public String getTempurature() {
        return tempurature;
    }

    public void setTempurature(String tempurature) {
        this.tempurature = tempurature;
    }

    public String getRespirationrate() {
        return respirationrate;
    }

    public void setRespirationrate(String respirationrate) {
        this.respirationrate = respirationrate;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}

