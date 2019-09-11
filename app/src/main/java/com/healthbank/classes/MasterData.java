package com.healthbank.classes;

import android.util.Log;

import org.json.JSONObject;

/**
 * Created by it1 on 6/29/2018.
 */

public class MasterData {
    String Name = "";
    int Categoryid = 0;
    int SubCategoryid = 0;
    int sortorder = 0;
    int DoctorId = 0;
    String Amount = "";
    String Code = "";
    int PracticeId = 0;
    int parent_id = 0;
    String type = "";
    String abbr = "";

    public MasterData() {
    }

    public MasterData(JSONObject obj) {
        try {
            if (obj.has("Name"))
                this.Name = obj.getString("Name");

            if (obj.has("Categoryid"))
                this.Categoryid = obj.getInt("Categoryid");

            if (obj.has("SubCategoryid"))
                this.SubCategoryid = obj.getInt("SubCategoryid");


            if (obj.has("sortorder"))
                this.sortorder = obj.getInt("sortorder");


            if (obj.has("DoctorId"))
                this.DoctorId = obj.getInt("DoctorId");


            if (obj.has("PracticeId"))
                this.Categoryid = obj.getInt("PracticeId");


            if (obj.has("parent_id"))
                this.Categoryid = obj.getInt("parent_id");

            if (obj.has("Amount"))
                this.Amount = obj.getString("Amount");
            if (obj.has("Code"))
                this.Code = obj.getString("Code");
            if (obj.has("type"))
                this.type = obj.getString("type");
            if (obj.has("abbr"))
                this.abbr = obj.getString("abbr");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("error ", "error " + e.toString());
        }
    }

    @Override
    public String toString() {
        return Name;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getCategoryid() {
        return Categoryid;
    }

    public void setCategoryid(int categoryid) {
        Categoryid = categoryid;
    }

    public int getSubCategoryid() {
        return SubCategoryid;
    }

    public void setSubCategoryid(int subCategoryid) {
        SubCategoryid = subCategoryid;
    }

    public int getSortorder() {
        return sortorder;
    }

    public void setSortorder(int sortorder) {
        this.sortorder = sortorder;
    }

    public int getDoctorId() {
        return DoctorId;
    }

    public void setDoctorId(int doctorId) {
        DoctorId = doctorId;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
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

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

}
