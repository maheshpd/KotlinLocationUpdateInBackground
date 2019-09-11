package com.healthbank.classes;

import org.json.JSONObject;

/**
 * Created by it1 on 8/9/2018.
 */

public class Advice {
    public String Amount = "";
    public boolean isselected = false;
    String Name = "";
    boolean ispaid = false;

    public Advice(JSONObject obj) {
        try {
            this.Name = obj.getString("Name");

            if (obj.has("Amount"))
                this.Amount = obj.getString("Amount");

            if (obj.has("ispaid"))
                this.ispaid = obj.getBoolean("ispaid");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isIsselected() {
        return isselected;
    }

    public void setIsselected(boolean isselected) {
        this.isselected = isselected;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public boolean isIspaid() {
        return ispaid;
    }

    public void setIspaid(boolean ispaid) {
        this.ispaid = ispaid;
    }
}


