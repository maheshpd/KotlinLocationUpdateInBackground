package com.healthbank.classes;

/**
 * Created by it1 on 6/6/2018.
 */

public class AvailiabilityChildData {
    String Name = "";
    String fromtime = "10.00";
    String totime = "10.15";

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getFromtime() {
        return fromtime;
    }

    public void setFromtime(String fromtime) {
        this.fromtime = fromtime;
    }

    public String getTotime() {
        return totime;
    }

    public void setTotime(String totime) {
        this.totime = totime;
    }
}
