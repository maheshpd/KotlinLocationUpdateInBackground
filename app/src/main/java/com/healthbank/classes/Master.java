package com.healthbank.classes;

public class Master {
    String Categoryid = "0";
    String Name = "";
    String TestId = "0";
    String LabId = "0";
    String LabName = "0";
    String filepath = "";
    String type = "";
    int issync = 0;

    public Master() {
    }

    public Master(String name) {
        this.Name = name;
    }

    public String getCategoryid() {
        return Categoryid;
    }

    public void setCategoryid(String categoryid) {
        Categoryid = categoryid;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public String toString() {
        return Name;
    }

    public String getTestId() {
        return TestId;
    }

    public void setTestId(String testId) {
        TestId = testId;
    }

    public String getLabId() {
        return LabId;
    }

    public void setLabId(String labId) {
        LabId = labId;
    }

    public String getLabName() {
        return LabName;
    }

    public void setLabName(String labName) {
        LabName = labName;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
}
