package com.example.finalattempt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

public class admin_view_and_edit_Employee extends AppCompatActivity {

    TextView Intro;
    EditText FName,Lname,Salary,Role,HDate,Email;
    Button Back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_view_and_edit_employee);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Employee Current=new Employee("","","","","",234F);
        Intent intent = getIntent();
        int CurrentID=Integer.parseInt(intent.getStringExtra("ID"));
        String URL="http://10.224.41.11/comp2000/employees/get/"+ CurrentID;
        RequestQueue RequestQueue= Volley.newRequestQueue(admin_view_and_edit_Employee.this.getApplicationContext());
        Gson gson = new Gson();

        JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Employee employee = gson.fromJson(response.toString(), Employee.class);
                //Current.setFirstname(employee.getFirstname());
                FName.setText(employee.getFirstname());
                Lname.setText(employee.getLastname());
                Salary.setText(employee.getSalary().toString());
                Role.setText(employee.getDepartment());
                HDate.setText(employee.getJoiningdate());
                Email.setText(employee.getEmail());

               // Current.setLastname(employee.getLastname());
                Log.d("EmployeeInfo", "Firstname: " + employee.getFirstname() + ", Salary: " + employee.getSalary());


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("EmployeeError", "Error retrieving employees: " + error.getMessage());

            }
        });
        RequestQueue.add(request);

        Log.d("Got employee ", "Name:"+Current.getFirstname()+" "+Current.getLastname()+" , Salary: "+Current.getSalary()+" , ");


       // Log.d("Response in page",RequestQueue.getResponseDelivery().toString());


        Intro=(TextView) findViewById(R.id.EmployeeIntro);
        Intro.setText("Details of employee with ID:"+CurrentID);
     //   Employee Current=EmployeeService.getEmployeeById(admin_view_and_edit_Employee.this,CurrentID);

        FName=(EditText) findViewById(R.id.FNameView);
        Lname=(EditText) findViewById(R.id.LnameView);
        Salary=(EditText) findViewById(R.id.SalaryView);
        Role=(EditText) findViewById(R.id.roleView);
        HDate=(EditText) findViewById(R.id.HdateView);
        Email=(EditText) findViewById(R.id.EmailView);

      //  FName.setText(Current.getFirstname());
      //  Lname.setText(Current.getLastname());
      //  Salary.setText(Current.getSalary().toString());
      //  Role.setText(Current.getDepartment());
      //  HDate.setText(Current.getJoiningdate());
      //  Email.setText(Current.getEmail());

        Back=(Button) findViewById(R.id.BackButton);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(admin_view_and_edit_Employee.this,adminMainPage.class);
                startActivity(i);
            }
        });







    }
}