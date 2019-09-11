package com.healthbank.classes;

public class Profile {
    String CustomerId="0";
    String ProviderId="0";
    String Name="0";
    String BranchName="0";
    String parentBranchId="0";
    String DepartmentId="0";
    String DepartmentName="0";
    String DepartmentParentId="0";
    String DrName="0";

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
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

    public String getBranchName() {
        return BranchName;
    }

    public void setBranchName(String branchName) {
        BranchName = branchName;
    }

    public String getParentBranchId() {
        return parentBranchId;
    }

    public void setParentBranchId(String parentBranchId) {
        this.parentBranchId = parentBranchId;
    }

    public String getDepartmentId() {
        return DepartmentId;
    }

    public void setDepartmentId(String departmentId) {
        DepartmentId = departmentId;
    }

    public String getDepartmentName() {
        return DepartmentName;
    }

    public void setDepartmentName(String departmentName) {
        DepartmentName = departmentName;
    }

    public String getDepartmentParentId() {
        return DepartmentParentId;
    }

    public void setDepartmentParentId(String departmentParentId) {
        DepartmentParentId = departmentParentId;
    }

    public String getDrName() {
        return DrName;
    }

    public void setDrName(String drName) {
        DrName = drName;
    }
}
