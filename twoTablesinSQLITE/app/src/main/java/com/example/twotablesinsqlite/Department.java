package com.example.twotablesinsqlite;

public class Department {

    int id;
    String deptName;
    String home;
    String logo;
//   byte[] image;


    Department(){

    }


    public Department(int id, String deptName, String home, String logo) {
        this.id = id;
        this.deptName = deptName;
        this.home=home;
        this.logo = logo;
//        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String dLogo) {
        this.logo = dLogo;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }


}
