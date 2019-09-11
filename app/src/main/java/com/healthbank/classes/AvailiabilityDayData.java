package com.healthbank.classes;

import java.util.ArrayList;

/**
 * Created by it1 on 6/6/2018.
 */

public class AvailiabilityDayData {
    String name = "";
    boolean isselected = false;
    ArrayList<AvailiabilityChildData> madaset = new ArrayList<>();

    public AvailiabilityDayData() {

    }

    public AvailiabilityDayData(String name) {
        this.name = name;
    }

    public AvailiabilityDayData(String name, ArrayList<AvailiabilityChildData> madaset) {
        this.name = name;
        this.madaset = madaset;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<AvailiabilityChildData> getMadaset() {
        return madaset;
    }

    public void setMadaset(ArrayList<AvailiabilityChildData> madaset) {
        this.madaset = madaset;
    }

    public boolean isIsselected() {
        return isselected;
    }

    public void setIsselected(boolean isselected) {
        this.isselected = isselected;
    }
}
