package com.healthbank.classes;

import android.content.ContentValues;

import com.healthbank.database.DatabaseHelper;

public class Template {
    int TotalRecord = 0;
    int TemplateId = 0;
    String TemplateName = "";
    String Category = "";
    int PracticeId = 0;
    int DepartmentId = 0;
    String Layout = "";
    String deleted = "";
    String Speciality = "";

    public ContentValues getContentValues(){
        ContentValues c=new ContentValues();
        c.put(DatabaseHelper.TemplateId,TemplateId);
        c.put(DatabaseHelper.TemplateName,TemplateName);
        c.put(DatabaseHelper.Category,Category);
        c.put(DatabaseHelper.PracticeId,PracticeId);
        c.put(DatabaseHelper.DepartmentId,DepartmentId);
        c.put(DatabaseHelper.Speciality,Speciality);

        return  c;
    }

    public int getTotalRecord() {
        return TotalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        TotalRecord = totalRecord;
    }

    public int getTemplateId() {
        return TemplateId;
    }

    public void setTemplateId(int templateId) {
        TemplateId = templateId;
    }

    public String getTemplateName() {
        return TemplateName;
    }

    public void setTemplateName(String templateName) {
        TemplateName = templateName;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public int getPracticeId() {
        return PracticeId;
    }

    public void setPracticeId(int practiceId) {
        PracticeId = practiceId;
    }

    public int getDepartmentId() {
        return DepartmentId;
    }

    public void setDepartmentId(int departmentId) {
        DepartmentId = departmentId;
    }

    public String getLayout() {
        return Layout;
    }

    public void setLayout(String layout) {
        Layout = layout;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getSpeciality() {
        return Speciality;
    }

    public void setSpeciality(String speciality) {
        Speciality = speciality;
    }

    @Override
    public String toString() {
        return this.TemplateName;
    }
}
