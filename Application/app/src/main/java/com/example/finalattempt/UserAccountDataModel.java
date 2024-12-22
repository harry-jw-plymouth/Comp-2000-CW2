package com.example.finalattempt;

public class UserAccountDataModel {

    int UserId;
    String Email,UserName,PassWord;
    public UserAccountDataModel(int UserId, String Email,String UserName,String PassWord){
        this.UserId=UserId;
        this.Email=Email;
        this.UserName=UserName;
        this.PassWord=PassWord;

    }
    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassWord() {
        return PassWord;
    }

    public void setPassWord(String passWord) {
        PassWord = passWord;
    }
}
