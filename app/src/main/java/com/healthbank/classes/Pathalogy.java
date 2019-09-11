package com.healthbank.classes;

import java.util.ArrayList;

public class Pathalogy {
    String filepath = "";
    String create_date = "";
    String TestName = "";
    String Id = "";
    ArrayList<String> filepathdata = new ArrayList<>();

    public ArrayList<String> getFilepathdata() {
        return filepathdata;
    }

    public void setFilepathdata(ArrayList<String> filepathdata) {
        this.filepathdata = filepathdata;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getTestName() {
        return TestName;
    }

    public void setTestName(String testName) {
        TestName = testName;
    }
}
