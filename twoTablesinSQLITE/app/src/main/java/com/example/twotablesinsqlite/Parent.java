package com.example.twotablesinsqlite;

public class Parent {

     int pid;
     String parentName;
     String plogo;

    public Parent(){

    }


    public Parent(int pid, String parentName, String plogo) {
        this.pid = pid;
        this.parentName = parentName;
        this.plogo = plogo;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getPlogo() {
        return plogo;
    }

    public void setPlogo(String plogo) {
        this.plogo = plogo;
    }
}
