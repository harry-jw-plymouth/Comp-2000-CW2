package com.example.finalattempt;

import java.time.LocalDate;

import kotlin.contracts.Returns;

public class HolidayRequest {
    int EmployeeId;
    String EmployeeName,Status;
    LocalDate StartDate,EndDate;
    HolidayRequest(int EI,String En,String S){
        EmployeeId=EI;
        EmployeeName=En;
        Status=S;
        // StartDate=SD;
        // EndDate=ED;
    }
    public String GetName(){
        return EmployeeName;
    }
    public int GetId(){
        return  EmployeeId;
    }
    public String GetStatus() {
        return Status;
    }
}


