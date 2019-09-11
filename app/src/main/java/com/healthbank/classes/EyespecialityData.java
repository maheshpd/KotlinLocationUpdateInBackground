package com.healthbank.classes;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;

public class EyespecialityData {
    UCVAData ucvaData = new UCVAData();
    eyecheckupData PGP = new eyecheckupData();
    eyecheckupData MR = new eyecheckupData();
    eyecheckupData CR = new eyecheckupData();
    String PUPIL = "";
    String IOP = "";
    String EXTEXM = "";
    String ANTSEG = "";
    String FUNDUS = "";

    public EyespecialityData() {

    }

    public EyespecialityData(JSONObject obj) {
        try {
            if (obj.has("UCVA")) {
                Gson gson = new Gson();
                Type typetoken = new TypeToken<UCVAData>() {
                }.getType();
                ucvaData = gson.fromJson(obj.getJSONObject("UCVA").toString(), typetoken);
            }

            if (obj.has("PGP")) {
                Gson gson = new Gson();
                Type typetoken = new TypeToken<eyecheckupData>() {
                }.getType();
                PGP = gson.fromJson(obj.getJSONObject("PGP").toString(), typetoken);
            }
            if (obj.has("MR")) {
                Gson gson = new Gson();
                Type typetoken = new TypeToken<eyecheckupData>() {
                }.getType();
                MR = gson.fromJson(obj.getJSONObject("MR").toString(), typetoken);
            }

            if (obj.has("CR")) {
                Gson gson = new Gson();
                Type typetoken = new TypeToken<eyecheckupData>() {
                }.getType();
                CR = gson.fromJson(obj.getJSONObject("CR").toString(), typetoken);
            }

            if (obj.has("PUPIL")) {
                this.PUPIL = obj.getString("PUPIL");
            }

            if (obj.has("IOP")) {
                this.IOP = obj.getString("IOP");
            }

            if (obj.has("EXTEXM")) {
                this.EXTEXM = obj.getString("EXTEXM");
            }

            if (obj.has("ANTSEG")) {
                this.ANTSEG = obj.getString("ANTSEG");
            }

            if (obj.has("FUNDUS")) {
                this.FUNDUS = obj.getString("FUNDUS");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public UCVAData getUcvaData() {
        return ucvaData;
    }

    public void setUcvaData(UCVAData ucvaData) {
        this.ucvaData = ucvaData;
    }

    public eyecheckupData getPGP() {
        return PGP;
    }

    public void setPGP(eyecheckupData PGP) {
        this.PGP = PGP;
    }

    public eyecheckupData getMR() {
        return MR;
    }

    public void setMR(eyecheckupData MR) {
        this.MR = MR;
    }

    public eyecheckupData getCR() {
        return CR;
    }

    public void setCR(eyecheckupData CR) {
        this.CR = CR;
    }

    public String getPUPIL() {
        return PUPIL;
    }

    public void setPUPIL(String PUPIL) {
        this.PUPIL = PUPIL;
    }

    public String getIOP() {
        return IOP;
    }

    public void setIOP(String IOP) {
        this.IOP = IOP;
    }

    public String getEXTEXM() {
        return EXTEXM;
    }

    public void setEXTEXM(String EXTEXM) {
        this.EXTEXM = EXTEXM;
    }

    public String getANTSEG() {
        return ANTSEG;
    }

    public void setANTSEG(String ANTSEG) {
        this.ANTSEG = ANTSEG;
    }

    public String getFUNDUS() {
        return FUNDUS;
    }

    public void setFUNDUS(String FUNDUS) {
        this.FUNDUS = FUNDUS;
    }
}
