package com.example.twotablesinsqlite;

public class Contacts {
    int contactId;
    String empName;
    String designation;
    String mobile;
    String landlineOffice;
    String landlineRes;
    String fax;
    String email;
    String dept_id;


    Contacts(){}

    public Contacts(int contactId, String empName, String designation, String mobile, String landlineOffice, String landlineRes, String fax, String email, String dept_id) {
        this.contactId = contactId;
        this.empName = empName;
        this.designation = designation;
        this.mobile = mobile;
        this.landlineOffice = landlineOffice;
        this.landlineRes = landlineRes;
        this.fax = fax;
        this.email = email;
        this.dept_id = dept_id;
    }


    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLandlineOffice() {
        return landlineOffice;
    }

    public void setLandlineOffice(String landlineOffice) {
        this.landlineOffice = landlineOffice;
    }

    public String getLandlineRes() {
        return landlineRes;
    }

    public void setLandlineRes(String landlineRes) {
        this.landlineRes = landlineRes;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDept_id() {
        return dept_id;
    }

    public void setDept_id(String dept_id) {
        this.dept_id = dept_id;
    }
}
