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
    ArrayList<EmployeeDetails> EmployeeListtemp;
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

        queue=Volley.newRequestQueue(this);
        StringRequest stringRequest= new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response " , response);
                List<Person> EmployeeList=gson.fromJson(response.toString(),new TypeToken<List<Person>>(){}.getType());
                for(Person person:EmployeeList){
                    Log.d("Employeeinfo","Firstname"+person.getFirstname()+",Salary: "+person.getSalary());
                   // Temp.add(person);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Result","Error getting: "+error.toString());
            }
        });
        queue.add(stringRequest);

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

        EmployeeListtemp =new ArrayList<EmployeeDetails>();
        EmployeeListtemp.add(new EmployeeDetails(0,"Harry Watton"));
        EmployeeListtemp.add(new EmployeeDetails(1,"William Richards"));
        EmployeeListtemp.add(new EmployeeDetails(2, "Alexander Crook"));
        EmployeeListtemp.add(new EmployeeDetails(3,"Owen Wiffen"));
        EmployeeListtemp.add(new EmployeeDetails(4,"Maxwell Waterman"));

        Employees=(RecyclerView)findViewById(R.id.EmployeeRecyclerView);
        Employees.setHasFixedSize(false);
        Employees.setLayoutManager(new LinearLayoutManager(this));

        //Adapter=new EmployeeDisplayAdapter(this,EmployeeList);
        //Adapter=new EmployeeDisplayAdapter(this,EmployeeListtemp);
        //Employees.setAdapter(Adapter);


        RequestQueue queue = Volley.newRequestQueue(this);
        




    }
}