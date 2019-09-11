package com.healthbank.classes;


import org.json.JSONObject;

/**
 * Created by it1 on 8/23/2018.
 */

public class Advicemasterdata {
    String title = "";
    int DoctorId = 0;
    String Code = "";
    int PracticeId = 0;
    String type = "";
    String description = "";
    String Amount = "";
    boolean isselected = false;
    int Itemcount = 1;
    long id = -1;

    public Advicemasterdata() {
    }

    public Advicemasterdata(JSONObject obj) {
        try {
            if (obj.has("Title"))
                this.title = obj.getString("Title");

            if (obj.has("DoctorId"))
                this.DoctorId = obj.getInt("DoctorId");

            if (obj.has("PracticeId"))
                this.PracticeId = obj.getInt("PracticeId");

            if (obj.has("Code"))
                this.Code = obj.getString("Code");

            if (obj.has("type"))
                this.type = obj.getString("type");

            if (obj.has("description"))
                this.description = obj.getString("description");

            if (obj.has("Amount"))
                this.Amount = obj.getString("Amount");

            if (obj.has("id"))
                this.id = obj.getLong("id");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getItemcount() {
        return Itemcount;
    }

    public void setItemcount(int itemcount) {
        Itemcount = itemcount;
    }

    public boolean isIsselected() {
        return isselected;
    }

    public void setIsselected(boolean isselected) {
        this.isselected = isselected;
    }

    @Override
    public String toString() {
        return this.getTitle();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDoctorId() {
        return DoctorId;
    }

    public void setDoctorId(int doctorId) {
        DoctorId = doctorId;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public int getPracticeId() {
        return PracticeId;
    }

    public void setPracticeId(int practiceId) {
        PracticeId = practiceId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        this.Amount = amount;
    }
}
