package com.healthbank.classes;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by it1 on 8/9/2018.
 */

public class ExaminationHeader {
    String Title = "";
    ArrayList<Advice> chiefcomplaints = new ArrayList<>();
    ArrayList<Advice> symptoms = new ArrayList<>();
    ArrayList<Advice> vaccines = new ArrayList<>();
    ArrayList<Advice> allergy = new ArrayList<>();
    ArrayList<Advice> diagnosis = new ArrayList<>();

    public ExaminationHeader(JSONObject obj1) {
        try {
            this.Title = obj1.getString("date");

            JSONArray array = new JSONArray(obj1.getString("jsondata"));
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = new JSONObject(array.getString(i));
                JSONArray chiefcomplaints = new JSONArray(obj.getString("chiefcomplaints"));
                for (int j = 0; j < chiefcomplaints.length(); j++) {
                    this.chiefcomplaints.add(new Advice(chiefcomplaints.getJSONObject(j)));
                }

                JSONArray symptoms = new JSONArray(obj.getString(("symptoms")));
                for (int j = 0; j < symptoms.length(); j++) {
                    this.symptoms.add(new Advice(symptoms.getJSONObject(j)));

                }

                JSONArray vaccines = new JSONArray(obj.getString(("vaccines")));
                for (int j = 0; j < vaccines.length(); j++) {
                    this.vaccines.add(new Advice(vaccines.getJSONObject(j)));
                }

                JSONArray allergy = new JSONArray(obj.getString(("allergy")));
                for (int j = 0; j < allergy.length(); j++) {
                    this.allergy.add(new Advice(allergy.getJSONObject(j)));

                }

                JSONArray diagnosis = new JSONArray(obj.getString(("diagnosis")));
                for (int j = 0; j < diagnosis.length(); j++) {
                    this.diagnosis.add(new Advice(diagnosis.getJSONObject(j)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public ArrayList<Advice> getChiefcomplaints() {
        return chiefcomplaints;
    }

    public void setChiefcomplaints(ArrayList<Advice> chiefcomplaints) {
        this.chiefcomplaints = chiefcomplaints;
    }

    public ArrayList<Advice> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(ArrayList<Advice> symptoms) {
        this.symptoms = symptoms;
    }

    public ArrayList<Advice> getVaccines() {
        return vaccines;
    }

    public void setVaccines(ArrayList<Advice> vaccines) {
        this.vaccines = vaccines;
    }

    public ArrayList<Advice> getAllergy() {
        return allergy;
    }

    public void setAllergy(ArrayList<Advice> allergy) {
        this.allergy = allergy;
    }

    public ArrayList<Advice> getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(ArrayList<Advice> diagnosis) {
        this.diagnosis = diagnosis;
    }
}
