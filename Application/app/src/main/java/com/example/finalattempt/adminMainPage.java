package com.example.finalattempt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class adminMainPage extends AppCompatActivity {
    Button SignOut;Button ManageHoliday;Button AddNewEmployee;
    RecyclerView Employees;
    ArrayList<EmployeeDetails>EmployeeList;
    EmployeeDisplayAdapter Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_main_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SignOut=(Button)findViewById(R.id.AdminMainPageSignOutButton);
        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminMainPage.this,MainActivity.class);
                startActivity(intent);
            }
        });

        ManageHoliday=(Button) findViewById(R.id.AMPViewAndManageHolidayButton);
        ManageHoliday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(adminMainPage.this,Manageholidaybookingsadmin.class);
                startActivity(intent);
            }
        });

        AddNewEmployee=(Button) findViewById(R.id.AMPAddNewEmployeeButton);
        AddNewEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(adminMainPage.this, AddNewEmployeeAdmin.class);
                startActivity(intent);
            }
        });

        EmployeeList=new ArrayList<EmployeeDetails>();
        EmployeeList.add(new EmployeeDetails(0,"Harry Watton"));
        EmployeeList.add(new EmployeeDetails(1,"William Richards"));
        EmployeeList.add(new EmployeeDetails(2, "Alexander Crook"));
        EmployeeList.add(new EmployeeDetails(3,"Owen Wiffen"));
        EmployeeList.add(new EmployeeDetails(4,"Maxwell Waterman"));

        Employees=(RecyclerView)findViewById(R.id.EmployeeRecyclerView);
        Employees.setHasFixedSize(false);
        Employees.setLayoutManager(new LinearLayoutManager(this));

        Adapter=new EmployeeDisplayAdapter(this,EmployeeList);
        Employees.setAdapter(Adapter);




    }
}