package com.healthbank.classes;

import android.util.Log;

import org.json.JSONObject;

/**
 * Created by it1 on 8/23/2018.
 */

public class LabMasterData {
    String title = "";
    int DoctorId = 0;
    String Code = "";
    int PracticeId = 0;
    String type = "";
    String description = "";
    String normalvalue = "";

    public LabMasterData() {

    }

    public LabMasterData(JSONObject obj) {
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

            if (obj.has("normalvalue"))
                this.normalvalue = obj.getString("normalvalue");

            Log.e("normal value ", "normal value " + this.normalvalue);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("error ", "error " + e.toString());
        }
    }

    public String getNormalvalue() {
        return normalvalue;
    }

    public void setNormalvalue(String normalvalue) {
        this.normalvalue = normalvalue;
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
}
