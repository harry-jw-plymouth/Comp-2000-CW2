package com.example.finalattempt;

public class EmployeeDetails {
    int id;
    String Name;
    public EmployeeDetails(int id,String Name){
        this.id=id;
        this.Name=Name;
    }
    public int Getid(){
        return this.id;
    }
    public String GetName(){
        return this.Name;
    }
}
