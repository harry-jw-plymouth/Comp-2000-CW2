package com.example.finalattempt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.PixelCopy;
import android.view.View;
import android.widget.Button;
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

public class ViewDetails extends AppCompatActivity {
    Button HomeButton;Button EditMyDetails;
    int ID;
    TextView FName, LName, HDate,Role,Salary;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        intent=getIntent();
        String UName=intent.getStringExtra("UName");
        int UserID= intent.getIntExtra("ID",0);
        Log.d("ID","ID: "+ UserID);

        String URL="http://10.224.41.11/comp2000/employees/get/"+ UserID;
        RequestQueue RequestQueue= Volley.newRequestQueue(ViewDetails.this.getApplicationContext());
        Gson gson= new Gson();
        FName=(TextView) findViewById(R.id.FnameView);
        LName=findViewById(R.id.LNameView);
        HDate=findViewById(R.id.HDate);
        Salary=findViewById(R.id.Salary);
        Role=findViewById(R.id.Role);



        JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Employee employee = gson.fromJson(response.toString(), Employee.class);
                FName.setText(employee.getFirstname());
                LName.setText(employee.getLastname());
                Salary.setText(employee.getSalary().toString());
                Role.setText(employee.getDepartment());
                try {
                    HDate.setText(GetDateInCorrectFormat (employee.getJoiningdate()) );
                }catch (Exception e){
                    HDate.setText(employee.getJoiningdate() );
                }
                Log.d("EmployeeInfo", "Firstname: " + employee.getFirstname() + ", Salary: " + employee.getSalary());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Employee error","Error retrieving employee");

            }
        });
        RequestQueue.add(request);


        HomeButton=(Button)findViewById(R.id.BackToHomeId);
        HomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ViewDetails.this, MainActivity2.class);
                intent.putExtra("UName",UName);
                intent.putExtra("ID",UserID);
                startActivity(intent);
            }
        });
        EditMyDetails=(Button)findViewById(R.id.EditDetailsButton2);
        EditMyDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ViewDetails.this,EditDetails.class);
                intent.putExtra("UName",UName);
                intent.putExtra("ID",UserID);
                startActivity(intent);
            }
        });
        Employee Temp=new Employee("Name1","Name2","Name1@Company.com","Marketing","date",3456.34F);
        //Employee Current=EmployeeService.getEmployeeById(ViewDetails.this, ID);

        FName=(TextView) findViewById(R.id.FnameView);

    }
    public String GetDateInCorrectFormat(String Date){
        String Month=""+Date.charAt(8)+Date.charAt(9)+Date.charAt(10);
        String MonthNum=GetMonth(Month);
        String Year=""+Date.charAt(12)+Date.charAt(13)+Date.charAt(14)+Date.charAt(15);

        return Year+"/"+MonthNum+"/"+Date.charAt(5)+Date.charAt(6);
    }
    public String GetMonth(String Month){
        switch (Month){
            case "Jan":
                return "1";
            case "Feb":
                return "2";
            case "Mar":
                return "3";
            case "Apr":
                return "4";
            case "May":
                return "5";
            case "Jun":
                return "6";
            case "Jul":
                return "7";
            case "Aug":
                return "8";
            case "Sep":
                return "9";
            case "Oct":
                return "10";
            case "Nov":
                return "11";
            case "Dec":
                return "12";

        }
        return "";
    }

}