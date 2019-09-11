package com.healthbank.classes;

import java.util.ArrayList;

public class AvailabilityData {
    String clinicname = "";
    ArrayList<AvailiabilityDayData> Day = new ArrayList<AvailiabilityDayData>() {
    };

    public AvailabilityData() {

    }

    public String getClinicname() {
        return clinicname;
    }

    public void setClinicname(String clinicname) {
        this.clinicname = clinicname;
    }

    public ArrayList<AvailiabilityDayData> getDay() {
        return Day;
    }

    public void setDay(ArrayList<AvailiabilityDayData> day) {
        Day = day;
    }

    public int getselectedpos() {
        for (int i = 0; i < getDay().size(); i++) {
            if (getDay().get(i).isIsselected())
                return i;
        }
        return 0;
    }
}
