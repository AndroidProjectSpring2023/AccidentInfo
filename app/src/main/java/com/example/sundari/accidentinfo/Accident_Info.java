package com.example.sundari.accidentinfo;

public class Accident_Info {

    private String vName , vAge , reason , location , injuries , incuranceCompany , policy_No;
    private int  fileName;

    public Accident_Info() {
    }

    public Accident_Info(String vName, String vAge , String reason, String location, String injuries, String incuranceCompany, String policy_No, int fileName) {
        this.vName = vName;
        this.vAge = vAge;
        this.reason = reason;
        this.location = location;
        this.injuries = injuries;
        this.incuranceCompany = incuranceCompany;
        this.policy_No = policy_No;
        this.fileName = fileName;
    }

    public String getvName() {
        return vName;
    }

    public void setvName(String vName) {
        this.vName = vName;
    }

    public String getvAge() {
        return vAge;
    }

    public void setvAge(String vAge) {
        this.vAge = vAge;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getInjuries() {
        return injuries;
    }

    public void setInjuries(String injuries) {
        this.injuries = injuries;
    }

    public String getIncuranceCompany() {
        return incuranceCompany;
    }

    public void setIncuranceCompany(String incuranceCompany) {
        this.incuranceCompany = incuranceCompany;
    }

    public String getPolicy_No() {
        return policy_No;
    }

    public void setPolicy_No(String policy_No) {
        this.policy_No = policy_No;
    }

    public int getFileName() {
        return fileName;
    }

    public void setFileName(int fileName) {
        this.fileName = fileName;
    }
}

