package com.healthbank.classes;

import android.content.ContentValues;

import com.healthbank.database.DatabaseHelper;

public class Patient {
    int Patientid = 0;
    String Addmitionno = "";
    String FName = "";
    String FirstName = "";
    String MiddleName = "";
    String LastName = "";
    String Address1 = "";
    int CountryId = 0;
    int StateId = 0;
    int CityId = 0;
    int id = 0;
    String Area = "";
    String DOB = "";
    String Gender = "";
    String PinCode = "";
    String Title = "";
    String Mobile = "";
    String EmailId = "";
    String AlternateEmailId = "";
    String BloodGroup = "";
    String Customerid = "";
    String create_date = "";
    String update_date = "";
    String deleted = "";
    String srno = "";
    int providerid = 0;
    int Assistantid = 0;
    String password = "";
    int IsActive = 1;
    String Department = "";
    String Language = "";
    String Religion = "";
    String BillingIn = "";
    String Phone = "";
    String Occupation = "";
    String EmployeeNo = "";
    String EmployeeName = "";
    String Remark = "";
    String Tags = "";
    String ReferFrom = "";
    String ReferTo = "";
    String AlternateMobile = "";
    String profileimage = "";
    String height = "";
    String aadharno = "";
    String pattype = "";
    String Maritalstatus = "";
    String WeddingDate = "";
    String DateOfjoining = "";
    String panno = "";
    String AgeYear = "";
    String AgeMonth = "";
    String Age = "";
    int sync = 1;
    int ServerPatientid = 0;
    String Tel1 = "";
    String UID = "";
    String PatientContactGroup = "";

    public String getPatientContactGroup() {
        return PatientContactGroup;
    }

    public void setPatientContactGroup(String patientContactGroup) {
        PatientContactGroup = patientContactGroup;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getTel1() {
        return Tel1;
    }

    public void setTel1(String tel1) {
        Tel1 = tel1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ContentValues getcontentvalues() {
        ContentValues c = new ContentValues();

        c.put(DatabaseHelper.Patientid, this.Patientid);

        c.put(DatabaseHelper.ServerPatientid, ServerPatientid);
        c.put(DatabaseHelper.SYNC, sync);
        if (this.FName != null)
            c.put(DatabaseHelper.FName, this.FName);

        if (this.FirstName != null)
            c.put(DatabaseHelper.FirstName, this.FirstName);

        if (this.MiddleName != null)
            c.put(DatabaseHelper.MiddleName, this.MiddleName);

        if (this.LastName != null)
            c.put(DatabaseHelper.LastName, this.LastName);

        if (this.Address1 != null)
            c.put(DatabaseHelper.Address, this.Address1);


        c.put(DatabaseHelper.CountryId, this.CountryId);


        c.put(DatabaseHelper.StateId, this.StateId);


        c.put(DatabaseHelper.CityId, this.CityId);

        if (this.DOB != null)
            c.put(DatabaseHelper.DOB, this.DOB);

        if (this.Gender != null)
            c.put(DatabaseHelper.Gender, this.Gender);

        if (this.PinCode != null)
            c.put(DatabaseHelper.PinCode, this.PinCode);

        if (this.Mobile != null)
            c.put(DatabaseHelper.Mobile, this.Mobile);

        if (this.EmailId != null)
            c.put(DatabaseHelper.EmailId, this.EmailId);

        if (this.AlternateEmailId != null)
            c.put(DatabaseHelper.AlternateEmailId, this.AlternateEmailId);

        if (this.BloodGroup != null)
            c.put(DatabaseHelper.BloodGroup, this.BloodGroup);

        if (this.Customerid != null)
            c.put(DatabaseHelper.Customerid, this.Customerid);

        if (this.create_date != null)
            c.put(DatabaseHelper.create_date, this.create_date);

        if (this.update_date != null)
            c.put(DatabaseHelper.update_date, this.update_date);

        if (this.deleted != null)
            c.put(DatabaseHelper.deleted, this.deleted);

        if (this.srno != null)
            c.put(DatabaseHelper.Srno, this.srno);

        c.put(DatabaseHelper.providerid, this.providerid);


        c.put(DatabaseHelper.Assistantid, this.Assistantid);

        if (this.BillingIn != null)
            c.put(DatabaseHelper.BillingIn, this.BillingIn);


        c.put(DatabaseHelper.IsActive, this.IsActive);

        if (this.Department != null)
            c.put(DatabaseHelper.Department, this.Department);

        if (this.Language != null)
            c.put(DatabaseHelper.Language, this.Language);

        if (this.Religion != null)
            c.put(DatabaseHelper.Religion, this.Religion);

        if (this.Phone != null)
            c.put(DatabaseHelper.Phone, this.Phone);

        if (this.Occupation != null)
            c.put(DatabaseHelper.Occupation, this.Occupation);

        if (this.EmployeeNo != null)
            c.put(DatabaseHelper.EmployeeNo, this.EmployeeNo);

        if (this.EmployeeName != null)
            c.put(DatabaseHelper.EmployeeName, this.EmployeeName);

        if (this.Remark != null)
            c.put(DatabaseHelper.Remark, this.Remark);

        if (this.Tags != null)
            c.put(DatabaseHelper.Tags, this.Tags);

        if (this.ReferFrom != null)
            c.put(DatabaseHelper.ReferFrom, this.ReferFrom);

        if (this.ReferTo != null)
            c.put(DatabaseHelper.ReferTo, this.ReferTo);

        if (this.AlternateMobile != null)
            c.put(DatabaseHelper.AlternateMobile, this.AlternateMobile);

        if (this.profileimage != null)
            c.put(DatabaseHelper.profileimage, this.profileimage);

        if (this.height != null)
            c.put(DatabaseHelper.Height, this.height);

        if (this.aadharno != null)
            c.put(DatabaseHelper.aadharno, this.aadharno);

        if (this.pattype != null)
            c.put(DatabaseHelper.pattype, this.pattype);

        if (this.Maritalstatus != null)
            c.put(DatabaseHelper.Maritalstatus, this.Maritalstatus);

        if (this.WeddingDate != null)
            c.put(DatabaseHelper.WeddingDate, this.WeddingDate);

        if (this.DateOfjoining != null)
            c.put(DatabaseHelper.DateOfjoining, this.DateOfjoining);

        if (this.panno != null)
            c.put(DatabaseHelper.panno, this.panno);

        if (this.AgeYear != null)
            c.put(DatabaseHelper.AgeYear, this.AgeYear);

        if (this.AgeMonth != null)
            c.put(DatabaseHelper.AgeMonth, this.AgeMonth);

        if (this.Age != null)
            c.put(DatabaseHelper.Age, this.Age);

        if (this.PatientContactGroup != null)
            c.put(DatabaseHelper.PatientContactGroup, this.PatientContactGroup);

        if (this.UID != null)
            c.put(DatabaseHelper.UID, this.UID);

        if (this.Tel1 != null)
            c.put(DatabaseHelper.Tel1, this.Tel1);
        return c;
    }

    public int getServerPatientid() {
        return ServerPatientid;
    }

    public void setServerPatientid(int serverPatientid) {
        ServerPatientid = serverPatientid;
    }

    public int getSync() {
        return sync;
    }

    public void setSync(int sync) {
        this.sync = sync;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        this.Age = age;
    }

    public String getAddmitionno() {
        return Addmitionno;
    }

    public void setAddmitionno(String addmitionno) {
        Addmitionno = addmitionno;
    }

    public int getPatientid() {
        return Patientid;
    }

    public void setPatientid(int patientid) {
        Patientid = patientid;
    }

    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getMiddleName() {
        return MiddleName;
    }

    public void setMiddleName(String middleName) {
        MiddleName = middleName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getAddress1() {
        return Address1;
    }

    public void setAddress1(String address1) {
        Address1 = address1;
    }

    public int getCountryId() {
        return CountryId;
    }

    public void setCountryId(int countryId) {
        CountryId = countryId;
    }

    public int getStateId() {
        return StateId;
    }

    public void setStateId(int stateId) {
        StateId = stateId;
    }

    public int getCityId() {
        return CityId;
    }

    public void setCityId(int cityId) {
        CityId = cityId;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getPinCode() {
        return PinCode;
    }

    public void setPinCode(String pinCode) {
        PinCode = pinCode;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId;
    }

    public String getAlternateEmailId() {
        return AlternateEmailId;
    }

    public void setAlternateEmailId(String alternateEmailId) {
        AlternateEmailId = alternateEmailId;
    }

    public String getBloodGroup() {
        return BloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        BloodGroup = bloodGroup;
    }

    public String getCustomerid() {
        return Customerid;
    }

    public void setCustomerid(String customerid) {
        Customerid = customerid;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(String update_date) {
        this.update_date = update_date;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getSrno() {
        return srno;
    }

    public void setSrno(String srno) {
        this.srno = srno;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIsActive() {
        return IsActive;
    }

    public void setIsActive(int isActive) {
        IsActive = isActive;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public String getReligion() {
        return Religion;
    }

    public void setReligion(String religion) {
        Religion = religion;
    }

    public String getBillingIn() {
        return BillingIn;
    }

    public void setBillingIn(String billingIn) {
        BillingIn = billingIn;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getOccupation() {
        return Occupation;
    }

    public void setOccupation(String occupation) {
        Occupation = occupation;
    }

    public String getEmployeeNo() {
        return EmployeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        EmployeeNo = employeeNo;
    }

    public String getEmployeeName() {
        return EmployeeName;
    }

    public void setEmployeeName(String employeeName) {
        EmployeeName = employeeName;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getTags() {
        return Tags;
    }

    public void setTags(String tags) {
        Tags = tags;
    }

    public String getReferFrom() {
        return ReferFrom;
    }

    public void setReferFrom(String referFrom) {
        ReferFrom = referFrom;
    }

    public String getReferTo() {
        return ReferTo;
    }

    public void setReferTo(String referTo) {
        ReferTo = referTo;
    }

    public String getAlternateMobile() {
        return AlternateMobile;
    }

    public void setAlternateMobile(String alternateMobile) {
        AlternateMobile = alternateMobile;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getAadharno() {
        return aadharno;
    }

    public void setAadharno(String aadharno) {
        this.aadharno = aadharno;
    }

    public String getPattype() {
        return pattype;
    }

    public void setPattype(String pattype) {
        this.pattype = pattype;
    }

    public String getMaritalstatus() {
        return Maritalstatus;
    }

    public void setMaritalstatus(String maritalstatus) {
        Maritalstatus = maritalstatus;
    }

    public String getWeddingDate() {
        return WeddingDate;
    }

    public void setWeddingDate(String weddingDate) {
        WeddingDate = weddingDate;
    }

    public String getDateOfjoining() {
        return DateOfjoining;
    }

    public void setDateOfjoining(String dateOfjoining) {
        DateOfjoining = dateOfjoining;
    }

    public String getPanno() {
        return panno;
    }

    public void setPanno(String panno) {
        this.panno = panno;
    }

    public String getAgeYear() {
        return AgeYear;
    }

    public void setAgeYear(String ageYear) {
        AgeYear = ageYear;
    }

    public String getAgeMonth() {
        return AgeMonth;
    }

    public void setAgeMonth(String ageMonth) {
        AgeMonth = ageMonth;
    }
}
