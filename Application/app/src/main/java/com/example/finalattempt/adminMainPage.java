package com.example.finalattempt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.google.gson.Gson;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class adminMainPage extends AppCompatActivity {
    Button SignOut;Button ManageHoliday;Button AddNewEmployee;
    RecyclerView Employees;
    ArrayList<Person> EmployeeListtemp;
    EmployeeDisplayAdapter Adapter;
    RequestQueue queue;
    String URL;
    List<Person>Temp;

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
        URL="http://10.224.41.11/comp2000/employees";
        Gson gson=new Gson();
        Temp=new ArrayList<Person>();
        queue=Volley.newRequestQueue(this);
        StringRequest stringRequest= new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response " , response);
                List<Person> EmployeeList=gson.fromJson(response,new TypeToken<List<Person>>(){}.getType());
                for(Person person:EmployeeList){
                    Log.d("Employeeinfo","Firstname"+person.getFirstname()+",Salary: "+person.getSalary());
                   Temp.add(new Person(person.getId(), person.getFirstname(),person.getLastname(),person.getEmail(),person.getDepartment(),person.getJoiningdate(),person.getSalary()));
                }
                Employees=(RecyclerView)findViewById(R.id.EmployeeRecyclerView);
                Employees.setHasFixedSize(false);
                Employees.setLayoutManager(new LinearLayoutManager(adminMainPage.this));
                Adapter=new EmployeeDisplayAdapter(adminMainPage.this,EmployeeList);
                //Adapter=new EmployeeDisplayAdapter(this,EmployeeListtemp);
                Employees.setAdapter(Adapter);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Result","Error getting: "+error.toString());
            }
        });
        queue.add(stringRequest);
       // RequestQueue queue = Volley.newRequestQueue(this);

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

        EmployeeListtemp =new ArrayList<Person>();
        EmployeeListtemp.add(new Person(0,"Harry","Watton","Hwatton@company.com","Marketing","20/11/24",2345));
        EmployeeListtemp.add(new Person(1,"William","Richards","WRichards@Company.com","Coding","17/10/24", 34534.34F));
        EmployeeListtemp.add(new Person(2, "Alexander","Crook","ACrook@Company.com","Marketing","04/05/24",34452.34F));
        EmployeeListtemp.add(new Person(3,"Owen","Wiffen","OWiffen@Company.com","Finance","30/11/23",341345.34F));
        EmployeeListtemp.add(new Person(4,"Maxwell", "Waterman","MWaterman@company.com","Design","30/09/23",34516.43F));

        Employees=(RecyclerView)findViewById(R.id.EmployeeRecyclerView);
        Employees.setHasFixedSize(false);
        Employees.setLayoutManager(new LinearLayoutManager(this));

      //  Adapter=new EmployeeDisplayAdapter(this,EmployeeListtemp);
        //Adapter=new EmployeeDisplayAdapter(this,EmployeeListtemp);
       // Employees.setAdapter(Adapter);


        RequestQueue queue = Volley.newRequestQueue(this);
        




    }
}