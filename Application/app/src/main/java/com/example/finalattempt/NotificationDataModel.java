package com.example.finalattempt;

public class NotificationDataModel {

    String UName;
    int UserID;
    String Type;
    public  NotificationDataModel(String UName, int UserID,String Type){

    }
    public String getUName() {
        return UName;
    }

    public void setUName(String UName) {
        this.UName = UName;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

}
