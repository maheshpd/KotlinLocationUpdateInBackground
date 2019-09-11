package com.healthbank;

/**
 * Created by it1 on 8/21/2018.
 */

public class AdviceData {
    String Name = "";
    String cost = "";

    public AdviceData(String Name, String Cost) {
        this.Name = Name;
        this.cost = Cost;
    }
 /*   public AdviceData(JSONObject obj) {

    }*/
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}

