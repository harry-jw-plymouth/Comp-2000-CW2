package com.example.finalattempt;

public class Person {
    int id;
    String firstname,lastname,email,department,joiningdate;
    float salary;
    public Person(int id,String firstname,String lastname,String email,String department,String joiningdate,float salary){
        this.id=id;
        this.firstname=firstname;
        this.lastname=lastname;
        this.email=email;
        this.department=department;
        this.joiningdate=joiningdate;
        this.salary=salary;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getJoiningdate() {
        return joiningdate;
    }

    public void setJoiningdate(String joiningdate) {
        this.joiningdate = joiningdate;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }




}
