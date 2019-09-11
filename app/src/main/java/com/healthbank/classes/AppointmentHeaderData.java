package com.healthbank.classes;

import java.util.ArrayList;

/**
 * Created by it1 on 6/28/2018.
 */

public class AppointmentHeaderData {
    ArrayList<Appointmentdatav1> appointmentdata = new ArrayList<>();
    String name = "Morning";

    public ArrayList<Appointmentdatav1> getAppointmentdata() {
        return appointmentdata;
    }

    public void setAppointmentdata(ArrayList<Appointmentdatav1> appointmentdata) {
        this.appointmentdata = appointmentdata;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
