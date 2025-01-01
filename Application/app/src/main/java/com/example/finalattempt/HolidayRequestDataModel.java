package com.example.finalattempt;

public class HolidayRequestDataModel {
    int RequestID;
    int EmployeeId;
    String EmployeeName,Status;
    String StartDate,EndDate;
    HolidayRequestDataModel(int RequestID,int EI,String En,String S,String SD,String ED){
        EmployeeId=EI;
        EmployeeName=En;
        Status=S;
        StartDate=SD;
        EndDate=ED;
        this.RequestID=RequestID;
    }
    public int getRequestID() {
        return RequestID;
    }

    public void setRequestID(int requestID) {
        RequestID = requestID;
    }
    public String GetName(){
        return EmployeeName;
    }
    public int GetId(){
        return  EmployeeId;
    }
    public String GetIdAsString(){
        return Integer.toString(EmployeeId);
    }
    public String GetStatus() {
        return Status;
    }
    public String GetStartDate(){
        return StartDate;
    }
    public String GetEndDate(){
        return EndDate;
    }
}
