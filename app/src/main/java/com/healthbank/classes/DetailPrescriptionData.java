package com.healthbank.classes;

import java.util.ArrayList;

/**
 * Created by it1 on 6/13/2018.
 */

public class DetailPrescriptionData {
    int id = -1;
    String date = "";
    int Patientid = 0;
    int globalpatientid = 0;
    ArrayList<String> labs = new ArrayList<>();
    ArrayList<String> diagnostics = new ArrayList<>();
    ArrayList<Drug> prescriptiondata = new ArrayList<>();
    String referaldoctor = "";
    ArrayList<String> advice = new ArrayList<>();
    int DoctorId = 0;
    int PracticeId = 0;

    public DetailPrescriptionData() {

    }

    public DetailPrescriptionData(ArrayList<String> mdataset, ArrayList<String> diagnosticdata, ArrayList<String> advicedata) {
        this.labs = mdataset;
        this.diagnostics = diagnosticdata;
        this.advice = advicedata;
    }

    public DetailPrescriptionData(ArrayList<String> labs, ArrayList<String> diagnostics, ArrayList<Drug> prescriptiondata, ArrayList<String> advice, String referaldoctor) {
        this.labs = labs;
        this.diagnostics = diagnostics;
        this.prescriptiondata = prescriptiondata;
        this.referaldoctor = referaldoctor;
        this.advice = advice;
    }

    public int getPracticeId() {
        return PracticeId;
    }

    public void setPracticeId(int practiceId) {
        PracticeId = practiceId;
    }

    public int getDoctorId() {
        return DoctorId;
    }

    public void setDoctorId(int doctorId) {
        DoctorId = doctorId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<String> getLabs() {
        return labs;
    }

    public void setLabs(ArrayList<String> labs) {
        this.labs = labs;
    }

    public ArrayList<String> getDiagnostics() {
        return diagnostics;
    }

    public void setDiagnostics(ArrayList<String> diagnostics) {
        diagnostics = diagnostics;
    }

    public ArrayList<Drug> getPrescriptiondata() {
        return prescriptiondata;
    }

    public void setPrescriptiondata(ArrayList<Drug> prescriptiondata) {
        this.prescriptiondata = prescriptiondata;
    }

    public String getReferaldoctor() {
        return referaldoctor;
    }

    public void setReferaldoctor(String referaldoctor) {
        this.referaldoctor = referaldoctor;
    }

    public ArrayList<String> getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        advice = advice;
    }

    public void setAdvice(ArrayList<String> advice) {
        this.advice = advice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPatientid() {
        return Patientid;
    }

    public void setPatientid(int patientid) {
        Patientid = patientid;
    }

    public int getGlobalpatientid() {
        return globalpatientid;
    }

    public void setGlobalpatientid(int globalpatientid) {
        this.globalpatientid = globalpatientid;
    }

    @Override
    public String toString() {
        return getDate();
    }
}
