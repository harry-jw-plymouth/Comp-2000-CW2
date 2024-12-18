package com.example.finalattempt;

public class AdminAccountDataModel {
    String UserName, Password;
    // String UserId;
    public AdminAccountDataModel(String UserName,String Password){
        this.UserName=UserName;
        this.Password=Password;
        //this.UserId=UserId;
    }
    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
