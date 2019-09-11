package com.healthbank.classes;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Doctor implements Serializable {
    String hospitalemr_type_default = "";
    String hospitalemr_type = "";
    String emr_id = "";
    String software_type = "";
    String emr_type = "";
    String commision_type = "";
    String EmrSpeciality = "";
    String Designation = "";
    String IspatientSearch = "";
    String theme = "";
    String ProviderId = "";
    String Name = "";
    String Role = "";
    String CustomerId = "";
    String branchname = "";
    String parentBranchId = "";
    ArrayList<loc> loc = new ArrayList<>();


    String EmailID = "";
    String Gender = "";
    String department = "";
    JSONObject Apptiming;
    String Qualification = "";
    String Specialization = "";
    String StaffId = "0";


    public Doctor(JSONObject obj) {
        try {
            Log.e("docobj ", "docobj " + obj.toString());
            if (obj.has("hospitalemr_type_default"))
                this.hospitalemr_type_default = obj.getString("hospitalemr_type_default");

            if (obj.has("hospitalemr_type"))
                this.hospitalemr_type = obj.getString("hospitalemr_type");

            if (obj.has("emr_id"))
                this.emr_id = obj.getString("emr_id");

            if (obj.has("software_type"))
                this.software_type = obj.getString("software_type");

            if (obj.has("emr_type"))
                this.emr_type = obj.getString("emr_type");

            if (obj.has("commision_type"))
                this.commision_type = obj.getString("commision_type");

            if (obj.has("EmrSpeciality"))
                this.EmrSpeciality = obj.getString("EmrSpeciality");

            if (obj.has("hospitalemr_type_default"))
                this.hospitalemr_type_default = obj.getString("hospitalemr_type_default");

            if (obj.has("Designation"))
                this.Designation = obj.getString("Designation");

            if (obj.has("IspatientSearch"))
                this.IspatientSearch = obj.getString("IspatientSearch");

            if (obj.has("theme"))
                this.theme = obj.getString("theme");

            if (obj.has("ProviderId"))
                this.ProviderId = obj.getString("ProviderId");

            if (obj.has("Name"))
                this.Name = obj.getString("Name");

            if (obj.has("Role"))
                this.Role = obj.getString("Role");

            if (obj.has("CustomerId"))
                this.CustomerId = obj.getString("CustomerId");

            if (obj.has("branchname"))
                this.branchname = obj.getString("branchname");

            if (obj.has("parentBranchId"))
                this.parentBranchId = obj.getString("parentBranchId");

            if (obj.has("loc")) {
                JSONObject objloc = obj.optJSONObject("loc");
                if (objloc != null) {
                    loc Loc = new loc(objloc);
                    this.loc.add(Loc);
                } else {
                    JSONArray array = obj.optJSONArray("loc");
                    for (int i = 0; i < array.length(); i++) {
                        loc Loc = new loc(array.getJSONObject(i));
                        this.loc.add(Loc);
                    }
                }
            }
        } catch (Exception e) {
            Log.e("error ", "error " + e.toString());
            e.printStackTrace();
        }

        try {
            if (obj.has("Name")) {
                this.Name = obj.getString("Name");
            }

            if (obj.has("EmailID")) {
                this.EmailID = obj.getString("EmailID");
            }

            if (obj.has("Gender")) {
                this.Gender = obj.getString("Gender");
            }

            if (obj.has("StaffId"))
                this.StaffId = obj.getString("StaffId");

            if (obj.has("Apptiming")) {
                this.Apptiming = obj.getJSONObject("Apptiming");
                Log.e("apttm", "apttm " + this.Apptiming.toString());
            }

            if (obj.has("Qualification")) {
                if (!obj.getString("Qualification").equalsIgnoreCase("null"))
                    this.Qualification = obj.getString("Qualification");
            }

            if (obj.has("Specialization")) {
                if (!obj.getString("Specialization").equalsIgnoreCase("null"))
                    this.Specialization = obj.getString("Specialization");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("error ", "error " + e.toString());
        }
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmailID() {
        return EmailID;
    }

    public void setEmailID(String emailID) {
        EmailID = emailID;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public JSONObject getApptiming() {
        return Apptiming;
    }

    public void setApptiming(JSONObject apptiming) {
        Apptiming = apptiming;
    }

    public ArrayList<Day> getslots() {
        ArrayList<Day> data = new ArrayList<>();
        try {
            if (getApptiming() != null) {
                Log.e("apt time ", "apt time " + getApptiming());
                JSONArray array = getApptiming().getJSONArray("dy");
                for (int i = 0; i < array.length(); i++) {
                    Day d = new Day(array.getJSONObject(i));
                    data.add(d);
                }
            } else Log.e("data ", "data null" + this.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public String getQualification() {
        return Qualification;
    }

    public void setQualification(String qualification) {
        Qualification = qualification;
    }

    public String getSpecialization() {
        return Specialization;
    }

    public void setSpecialization(String specialization) {
        Specialization = specialization;
    }

    public String getStaffId() {
        return StaffId;
    }

    public void setStaffId(String staffId) {
        StaffId = staffId;
    }

    public String getHospitalemr_type_default() {
        return hospitalemr_type_default;
    }

    public void setHospitalemr_type_default(String hospitalemr_type_default) {
        this.hospitalemr_type_default = hospitalemr_type_default;
    }

    public String getHospitalemr_type() {
        return hospitalemr_type;
    }

    public void setHospitalemr_type(String hospitalemr_type) {
        this.hospitalemr_type = hospitalemr_type;
    }

    public String getEmr_id() {
        return emr_id;
    }

    public void setEmr_id(String emr_id) {
        this.emr_id = emr_id;
    }

    public String getSoftware_type() {
        return software_type;
    }

    public void setSoftware_type(String software_type) {
        this.software_type = software_type;
    }

    public String getEmr_type() {
        return emr_type;
    }

    public void setEmr_type(String emr_type) {
        this.emr_type = emr_type;
    }

    public String getCommision_type() {
        return commision_type;
    }

    public void setCommision_type(String commision_type) {
        this.commision_type = commision_type;
    }

    public String getEmrSpeciality() {
        return EmrSpeciality;
    }

    public void setEmrSpeciality(String emrSpeciality) {
        EmrSpeciality = emrSpeciality;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }

    public String getIspatientSearch() {
        return IspatientSearch;
    }

    public void setIspatientSearch(String ispatientSearch) {
        IspatientSearch = ispatientSearch;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getProviderId() {
        return ProviderId;
    }

    public void setProviderId(String providerId) {
        ProviderId = providerId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname;
    }

    public String getParentBranchId() {
        return parentBranchId;
    }

    public void setParentBranchId(String parentBranchId) {
        this.parentBranchId = parentBranchId;
    }

    public ArrayList<com.healthbank.classes.loc> getLoc() {
        return loc;
    }

    public void setLoc(ArrayList<com.healthbank.classes.loc> loc) {
        this.loc = loc;
    }

    @Override
    public String toString() {
        return branchname;
    }
}


