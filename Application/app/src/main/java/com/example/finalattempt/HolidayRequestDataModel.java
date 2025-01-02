package com.example.finalattempt;

public class HolidayRequestDataModel {
    int RequestID;


    int EmployeeId;
    String EmployeeName;
    String Status;



    String StartDate,EndDate;
    HolidayRequestDataModel(int RequestID,int EI,String En,String S,String SD,String ED){
        EmployeeId=EI;
        EmployeeName=En;
        Status=S;
        StartDate=SD;
        EndDate=ED;
        this.RequestID=RequestID;
    }

    public String getEmployeeName() {
        return EmployeeName;
    }

    public void setEmployeeName(String employeeName) {
        EmployeeName = employeeName;
    }
    public int getRequestID() {
        return RequestID;
    }
    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public void setRequestID(int requestID) {
        RequestID = requestID;
    }
    public int getEmployeeId() {
        return EmployeeId;
    }

    public void setEmployeeId(int employeeId) {
        EmployeeId = employeeId;
    }
    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
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
