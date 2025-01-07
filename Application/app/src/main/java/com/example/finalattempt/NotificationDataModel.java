package com.example.finalattempt;

public class NotificationDataModel {

    String UName;
    int UserID,NotificationID;
    String Type;
    public  NotificationDataModel(int NotificationID,int UserID,String UName, String Type){
        this.UName=UName;
        this.UserID=UserID;
        this.NotificationID=NotificationID;
        this.Type=Type;



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
    public int getNotificationID() {
        return NotificationID;
    }

    public void setNotificationID(int NorificationID) {
        NotificationID = NorificationID;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

}
