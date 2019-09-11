package com.healthbank.classes;

/**
 * Created by it1 on 6/5/2018.
 */

public class PatientData {
    int patid=0;
    String Name="";

    public PatientData(){

    }

    public int getPatid() {
        return patid;
    }

    public void setPatid(int patid) {
        this.patid = patid;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
