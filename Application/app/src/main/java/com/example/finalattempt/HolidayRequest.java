package com.example.finalattempt;

import android.view.ViewDebug;

import java.time.LocalDate;

import kotlin.contracts.Returns;

public class HolidayRequest {
    int EmployeeId;
    String EmployeeName,Status;
    String StartDate,EndDate;
    HolidayRequest(int EI,String En,String S,String SD,String ED){
        EmployeeId=EI;
        EmployeeName=En;
        Status=S;
        StartDate=SD;
        EndDate=ED;
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


