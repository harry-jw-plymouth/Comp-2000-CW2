package com.example.finalattempt;

import android.location.Address;

public class FullEmployeeDetails
{
    String First_Name,Last_Name,Role,Gender;
    String HireDate,BirthDate,Address;
    float Salary;


    public FullEmployeeDetails(String First_Name, String Last_Name, String Role, String Gender, String HireDate, String BirthDate, float Salary,String Address){
        this.First_Name=First_Name;
        this.Last_Name=Last_Name;
        this.Role=Role;
        this.Gender=Gender;
        this.HireDate=HireDate;
        this.BirthDate=BirthDate;
        this.Salary=Salary;
        this.Address= Address;
    }
    public String GetAddress(){
        return Address;
    }

    public String getLast_Name() {
        return Last_Name;
    }

    public void setLast_Name(String last_Name) {
        Last_Name = last_Name;
    }

    public String getFirst_Name() {
        return First_Name;
    }

    public void setFirst_Name(String first_Name) {
        First_Name = first_Name;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getHireDate() {
        return HireDate;
    }

    public void setHireDate(String hireDate) {
        HireDate = hireDate;
    }

    public String getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(String birthDate) {
        BirthDate = birthDate;
    }

    public float getSalary() {
        return Salary;
    }

    public void setSalary(float salary) {
        Salary = salary;
    }
}

