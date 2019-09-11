package com.healthbank.classes;

/**
 * Created by it1 on 6/15/2018.
 */

public class Reading {
    String name = "";
    boolean isselected = true;

    public Reading() {

    }
    public Reading(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIsselected() {
        return isselected;
    }

    public void setIsselected(boolean isselected) {
        this.isselected = isselected;
    }
}
