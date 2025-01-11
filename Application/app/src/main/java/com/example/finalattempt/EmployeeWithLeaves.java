package com.example.finalattempt;

public class EmployeeWithLeaves {
    String firstname,lastname,email,department,joiningdate;

    public int getLeaves() {
        return leaves;
    }

    public void setLeaves(int leaves) {
        this.leaves = leaves;
    }

    int leaves;

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    public String getJoiningdate() {
        return joiningdate;
    }

    public void setJoiningdate(String joiningdate) {
        this.joiningdate = joiningdate;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    Float salary;
    public EmployeeWithLeaves(String firstname, String lastname , String email, String department, String joiningdate, Float salary){
        this.firstname=firstname;
        this.lastname=lastname;
        this.email=email;
        this.department=department;
        this.joiningdate=joiningdate;
        this.salary=salary;
    }
}
