package com.healthbank.classes;

import java.io.Serializable;
import java.util.ArrayList;

public class Drug implements Serializable {
    String DrugName = "";
    String DrugId = "";
    String Instruction = "";
    String Instructionid = "";
    String DrugQuantity = "";
    String Doses = "";
    String Dosesid = "";
    String DrugUnit = "";
    String BodyPart = "";
    String BodyPartId = "";
    String Duration = "";
    ArrayList<Master> lifestyle = new ArrayList<>();
    ArrayList<Master> lab = new ArrayList<>();
    long visit_no = -1;
    String visitdate = "";
    long dbvisitid = 0;
    String brand = "";
    String Status = "";
    String FromDate = "";
    String ToDate = "";
    String Precriptionid = "0";
    boolean Deleted = false;

    int Durationid = 0;
    int patient_id = 0;
    int practice_id = 0;
    int department_id = 0;
    int UserId = 0;
    int providerid = 0;
    int Assistantid = 0;
    int GeneralId = 0;
    String IPDNo = "";
    String TemplateName = "";
    int presdrugid = 0;
    String PresType = "Discharge";

    public int getDurationid() {
        return Durationid;
    }

    public void setDurationid(int durationid) {
        Durationid = durationid;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public int getPractice_id() {
        return practice_id;
    }

    public void setPractice_id(int practice_id) {
        this.practice_id = practice_id;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getProviderid() {
        return providerid;
    }

    public void setProviderid(int providerid) {
        this.providerid = providerid;
    }

    public int getAssistantid() {
        return Assistantid;
    }

    public void setAssistantid(int assistantid) {
        Assistantid = assistantid;
    }

    public int getGeneralId() {
        return GeneralId;
    }

    public void setGeneralId(int generalId) {
        GeneralId = generalId;
    }

    public String getIPDNo() {
        return IPDNo;
    }

    public void setIPDNo(String IPDNo) {
        this.IPDNo = IPDNo;
    }

    public String getTemplateName() {
        return TemplateName;
    }

    public void setTemplateName(String templateName) {
        TemplateName = templateName;
    }

    public int getPresdrugid() {
        return presdrugid;
    }

    public void setPresdrugid(int presdrugid) {
        this.presdrugid = presdrugid;
    }

    public String getPresType() {
        return PresType;
    }

    public void setPresType(String presType) {
        PresType = presType;
    }

    public boolean isDeleted() {
        return Deleted;
    }

    public void setDeleted(boolean deleted) {
        Deleted = deleted;
    }

    public String getPrecriptionid() {
        return Precriptionid;
    }

    public void setPrecriptionid(String precriptionid) {
        Precriptionid = precriptionid;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getFromDate() {
        return FromDate;
    }

    public void setFromDate(String fromDate) {
        FromDate = fromDate;
    }

    public String getToDate() {
        return ToDate;
    }

    public void setToDate(String toDate) {
        ToDate = toDate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public long getVisit_no() {
        return visit_no;
    }

    public void setVisit_no(long visit_no) {
        this.visit_no = visit_no;
    }

    public long getDbvisitid() {
        return dbvisitid;
    }

    public void setDbvisitid(long dbvisitid) {
        this.dbvisitid = dbvisitid;
    }

    public String getVisitdate() {
        return visitdate;
    }

    public void setVisitdate(String visitdate) {
        this.visitdate = visitdate;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getDrugName() {
        return DrugName;
    }

    public void setDrugName(String drugName) {
        DrugName = drugName;
    }

    public String getDrugId() {
        return DrugId;
    }

    public void setDrugId(String drugId) {
        DrugId = drugId;
    }

    public String getInstruction() {
        return Instruction;
    }

    public void setInstruction(String instruction) {
        Instruction = instruction;
    }

    public String getInstructionid() {
        return Instructionid;
    }

    public void setInstructionid(String instructionid) {
        Instructionid = instructionid;
    }

    public String getDrugQuantity() {
        return DrugQuantity;
    }

    public void setDrugQuantity(String drugQuantity) {
        DrugQuantity = drugQuantity;
    }

    public String getDoses() {
        return Doses;
    }

    public void setDoses(String doses) {
        Doses = doses;
    }

    public String getDosesid() {
        return Dosesid;
    }

    public void setDosesid(String dosesid) {
        Dosesid = dosesid;
    }

    public String getDrugUnit() {
        return DrugUnit;
    }

    public void setDrugUnit(String drugUnit) {
        DrugUnit = drugUnit;
    }

    public String getBodyPart() {
        return BodyPart;
    }

    public void setBodyPart(String bodyPart) {
        BodyPart = bodyPart;
    }

    public String getBodyPartId() {
        return BodyPartId;
    }

    public void setBodyPartId(String bodyPartId) {
        BodyPartId = bodyPartId;
    }

    public ArrayList<Master> getLifestyle() {
        return lifestyle;
    }

    public void setLifestyle(ArrayList<Master> lifestyle) {
        this.lifestyle = lifestyle;
    }

    public ArrayList<Master> getLab() {
        return lab;
    }

    public void setLab(ArrayList<Master> lab) {
        this.lab = lab;
    }
}
