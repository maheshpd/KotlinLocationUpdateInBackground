package com.healthbank.classes;

import java.util.ArrayList;

public class CountryClass {

    private String id;
    private String name;
    private String CountryName;
    private ArrayList<StateClass> s;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryName() {
        return CountryName;
    }

    public void setCountryName(String countryName) {
        CountryName = countryName;
    }

    public ArrayList<StateClass> getS() {
        return s;
    }

    public void setS(ArrayList<StateClass> s) {
        this.s = s;
    }

}
