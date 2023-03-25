package com.example.sundari.accidentinfo;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class UserProfile {

    public String userName, userEmpID, userEmail, userPhone, userOccupation, userRegion , userPassword;
    public Map<String, Boolean> stars = new HashMap<>();


    public UserProfile(){

    }


    public UserProfile(String userName, String userEmpID, String userEmail, String userPhone, String userOccupation, String userRegion, String userPassword){

        this.userName = userName;
        this.userEmpID = userEmpID;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.userOccupation = userOccupation;
        this.userRegion = userRegion;
        this.userPassword = userPassword;

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmpID() {
        return userEmpID;
    }

    public void setUserEmpID(String userEmpID) {
        this.userEmpID = userEmpID;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserOccupation() {
        return userOccupation;
    }

    public void setUserOccupation(String userOccupation) {
        this.userOccupation = userOccupation;
    }

    public String getUserRegion() {
        return userRegion;
    }

    public void setUserRegion(String userRegion) {
        this.userRegion = userRegion;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }




    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userName", userName);
        result.put("userEmpID", userEmpID);
        result.put("userEmail", userEmail);
        result.put("userPhone", userPhone);
        result.put("userOccupation", userOccupation);
        result.put("userRegion", userRegion);
        result.put("userPassword", userPassword);
        result.put("stars", stars);

        return result;
    }


}
