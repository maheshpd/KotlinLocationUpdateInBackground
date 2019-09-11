package com.healthbank.classes;

import org.json.JSONObject;

public class EyeData {
    EyespecialityData OD = new EyespecialityData();
    EyespecialityData OS = new EyespecialityData();
    long id = 0;
    String Date = "";

    public EyeData(JSONObject obj) {
        try {
            JSONObject odobj = obj.getJSONObject("OD");
            this.OD = new EyespecialityData(odobj);

            JSONObject osobj = obj.getJSONObject("OS");
            this.OS = new EyespecialityData(osobj);

            if (obj.has("id"))
                this.id = obj.getLong("id");

            if (obj.has("Date"))
                this.Date = obj.getString("Date");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public EyespecialityData getOD() {
        return OD;
    }

    public void setOD(EyespecialityData OD) {
        this.OD = OD;
    }

    public EyespecialityData getOS() {
        return OS;
    }

    public void setOS(EyespecialityData OS) {
        this.OS = OS;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}

