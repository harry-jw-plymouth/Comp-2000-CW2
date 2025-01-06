package com.example.finalattempt;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Array;
import java.util.ArrayList;

import kotlin.contracts.Returns;

public class admin_view_and_edit_Employee extends AppCompatActivity {

    TextView Intro,Result;
    EditText FName,Lname,Salary,Role,HDate,Email;
    Button Back,Save;


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
                try {
                    HDate.setText(GetDateInCorrectFormat (employee.getJoiningdate()) );
                }catch (Exception e){
                    HDate.setText(employee.getJoiningdate() );
                }

                Email.setText(employee.getEmail());

               // Current.setLastname(employee.getLastname());
                Log.d("EmployeeInfo", "Firstname: " + employee.getFirstname() + ", Salary: " + employee.getSalary());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("EmployeeError", "Error retrieving employees: " + error.getMessage());
                Toast.makeText(admin_view_and_edit_Employee.this,"Error getting employee details",Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(admin_view_and_edit_Employee.this,adminMainPage.class);
                startActivity(intent);
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
        Result=(TextView)findViewById(R.id.result);

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
        Save=findViewById(R.id.SaveChanges);
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Employee Updated=GetInputs();
                if(Updated!=null){
                    OpenSaveDialog(Updated,CurrentID);
                }

            }
        });
    }
    public EmployeeToPut GetUploadableEmployee(Employee employee,int ID){
        return  new EmployeeToPut(ID,employee.getFirstname(),employee.getLastname(),employee.getEmail(),employee.getDepartment(),employee.getSalary(),employee.getJoiningdate(),30);
    }
    public void OpenSaveDialog(Employee Updated,int ID){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Confirm changes");
        builder.setMessage("Save changes and view?");
        //builder.setCancelable(false);
        // above line prevents alert from closing when area outside box clicked
        builder.setPositiveButton("Save changes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(admin_view_and_edit_Employee.this,"Saving changes",Toast.LENGTH_SHORT).show();
                EmployeeToPut PutableEmployee=GetUploadableEmployee(Updated,ID);
                RequestQueue queue=Volley.newRequestQueue(admin_view_and_edit_Employee.this);
                String url = "http://10.224.41.11/comp2000/employees/edit/"+ID;
                Gson gson= new Gson();
                Log.d("Employee to put","Name:"+PutableEmployee.getFirstname()+" "+ PutableEmployee.getLastname()+" ID: "+PutableEmployee.getId()+" Department: " + PutableEmployee.getDepartment()+" Salary: "+ PutableEmployee.getSalary()+" Joining date: "+ PutableEmployee.getJoiningdate());
                try{
                    JSONObject jsonRequest = new JSONObject(gson.toJson(PutableEmployee));

                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, jsonRequest, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("Employee service", "Employee saved");
                            Intent intent=new Intent(admin_view_and_edit_Employee.this,adminMainPage.class);
                            startActivity(intent);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Employee Error", "Error: " +error);
                        }
                    }
                    );
                    queue.add(request);
                }catch (JSONException e){
                    Log.e("Employee Error", "Invalid JSON format"+ e.getMessage());
                }
            }
        });
        builder.setNegativeButton("Back to editing", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(admin_view_and_edit_Employee.this,"back",Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alertDialog= builder.create();
        alertDialog.show();
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
    public Employee GetInputs(){
        if(FName.getText().toString().isEmpty()){
            Result.setText("Please enter a first name");
            return null;
        }
        if(Lname.getText().toString().isEmpty()){
            Result.setText("Please enter a last name");
            return null;
        }
        if(Salary.getText().toString().isEmpty()){
            Result.setText("Please enter a salary");
            return null;
        }

        return new Employee(FName.getText().toString(),Lname.getText().toString(),Email.getText().toString(),Role.getText().toString(),HDate.getText().toString(),Float.valueOf(Salary.getText().toString()));
    }
}