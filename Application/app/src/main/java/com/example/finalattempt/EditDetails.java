package com.example.finalattempt;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import dalvik.system.BaseDexClassLoader;

public class EditDetails extends AppCompatActivity {
    EditText Fname, Lname,Email;
    TextView Result,Salary,Date,Role;
    Button HomeButton;Button SaveAndViewButton;Button BackToVIewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent= getIntent();
        String UName=intent.getStringExtra("UName");
        int UserID= intent.getIntExtra("ID",0);
        Log.d("Employee uname",""+UName);
        Log.d("EmployeeID",""+UserID);
        Result=(TextView) findViewById(R.id.ResultText);

        Employee Temp= new Employee("","","","","",1F);
        String URL="http://10.224.41.11/comp2000/employees/get/"+ UserID; // url for get request
        Log.d("URL",URL);
        RequestQueue RequestQueue= Volley.newRequestQueue(EditDetails.this);
        Gson gson = new Gson();
        Fname=(EditText)findViewById(R.id.FNameInput);
        Lname=(EditText)findViewById(R.id.LNameInput);
        Email=(EditText) findViewById(R.id.EmailInput);
        Role=findViewById(R.id.textViewRole);
        Salary=findViewById(R.id.textViewSalary);
        Date=findViewById(R.id.textViewDate);


        JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Employee employee = gson.fromJson(response.toString(), Employee.class);
                Fname.setText(employee.getFirstname());
                Lname.setText(employee.getLastname());
                Email.setText(employee.getEmail());
                Role.setText(employee.getDepartment());
                Salary.setText(employee.getSalary().toString());
                Date.setText(employee.getJoiningdate());
                // load details for employee to edit
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("EmployeeError", "Error retrieving employees: " + error.getMessage());
                Toast.makeText(EditDetails.this,"Error getting employee details",Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(EditDetails.this,MainActivity2.class);
                intent.putExtra("UName",UName);
                intent.putExtra("ID",UserID);
                Log.d("User ID before intent", ""+UserID);
                startActivity(intent);
                //error getting employee details from API loads main page to avoid further errors
            }
        });
        RequestQueue.add(request);
        Log.d("User ID after request", ""+UserID);

        HomeButton=(Button)findViewById(R.id.BackToHomeId);
        HomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(EditDetails.this, MainActivity2.class);
                intent.putExtra("UName",UName);
                intent.putExtra("test",UserID);
                startActivity(intent); // back to home page button
            }
        });
        SaveAndViewButton=(Button)findViewById(R.id.SaveandViewButton);
        SaveAndViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Employee Current=GetEditedDetails(Temp);
                Log.d("New Details", Current.getFirstname()+Current.getLastname());
                if(Current!=null){
                    showAlertDialogueForSaveButton(Current,UName,UserID); // get user to confirm changes
                }
            }
        });
        BackToVIewButton=(Button)findViewById(R.id.backtoviewbutton);
        BackToVIewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(EditDetails.this);
                builder.setTitle("Discard changes?");
                builder.setMessage("Discard changes and return to editing?");
                builder.setPositiveButton("Continue and discard", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(EditDetails.this,"Changes Discarded",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(EditDetails.this,ViewDetails.class);
                        intent.putExtra("UName",UName);
                        intent.putExtra("ID",UserID);
                        startActivity(intent); // employee discards changes and returns to view details page
                    }
                });
                builder.setNegativeButton("Back to editing", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(EditDetails.this,"Back",Toast.LENGTH_SHORT).show();
                        //employee goes back to editing and closes dialog box
                    }
                });
                AlertDialog alertDialog= builder.create();
                alertDialog.show(); //open dialog
            }
        });
    }
    private void showAlertDialogueForSaveButton(Employee Current,String UName,int UserID){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Confirm changes");
        builder.setMessage("Save changes and view?");
        //builder.setCancelable(false);
        // above line prevents alert from closing when area outside box clicked
        builder.setPositiveButton("Save changes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(EditDetails.this,"Save changes",Toast.LENGTH_SHORT).show();
                EmployeeToPut PutableEmployee=GetUploadableEmployee(Current,UserID);
                RequestQueue queue= Volley.newRequestQueue(EditDetails.this);
                String url="http://10.224.41.11/comp2000/employees/edit/"+UserID;
                Gson gson= new Gson();
                try {
                    JSONObject jsonRequest = new JSONObject(gson.toJson(PutableEmployee));
                    JsonObjectRequest request= new JsonObjectRequest(Request.Method.PUT, url, jsonRequest, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("Employee service", "Employee saved");
                            //employee changes saved
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Employee Error", "Error: " +error);
                            //error in saving
                        }
                    });
                    queue.add(request);

                }catch (JSONException e){
                    Log.e("Employee Error","Invalid JSON format" + e.getMessage());
                }
                EmployeeDBHelper db= new EmployeeDBHelper(EditDetails.this);
                String NewUName=GetUserName(Current.getFirstname(),Current.getLastname());
                db.ChangeUserName(UserID,NewUName);


                Intent intent=new Intent(EditDetails.this,DetailsEditedConfirmation.class);

                intent.putExtra("NewFname",Current.getFirstname());
                intent.putExtra("NewLname",Current.getLastname());
                intent.putExtra("NewEmail",Current.getEmail());
                intent.putExtra("UName",UName);
                intent.putExtra("ID",UserID);
                startActivity(intent); //open edit confirmation page
            }
        });
        builder.setNegativeButton("Back to editing", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(EditDetails.this,"back",Toast.LENGTH_SHORT).show();
                //employee returns to edititng
            }
        });
        AlertDialog alertDialog= builder.create();
        alertDialog.show();
    }
    public Employee GetEditedDetails(Employee Current){
        Fname=(EditText)findViewById(R.id.FNameInput);
        String FirstName=Fname.getText().toString();
        Lname=(EditText)findViewById(R.id.LNameInput);
        String LastName=Lname.getText().toString();
        Email=(EditText) findViewById(R.id.EmailInput);
        String NewEmail=Email.getText().toString();
        if(Fname.getText().toString().isEmpty()){
            Result.setText("Please enter a first name");
            return null;
        }
        if(Lname.getText().toString().isEmpty()){
            Result.setText("Please enter a last name");
            return null;
        }
        if(GetIfContainsDigit(Fname.getText().toString())|| GetIfContainsDigit(Lname.getText().toString())){
            Result.setText("Name cannot contain digits");
            return null;
            // returns null if invalid inputs
        }
        Log.d("Fname",FirstName);
        Log.d("Lname",LastName);
        Log.d("Email",NewEmail);
        Float newSalary= Float.valueOf(Salary.getText().toString());
        String FixedDate=GetDateInCorrectFormat(Date.getText().toString());
        String NewRole=Role.getText().toString();

        return new Employee(FirstName,LastName,NewEmail,NewRole,FixedDate,newSalary);
        // employee returned with new values
    }
    public EmployeeToPut GetUploadableEmployee(Employee employee,int ID){
        return  new EmployeeToPut(ID,employee.getFirstname(),employee.getLastname(),employee.getEmail(),employee.getDepartment(),employee.getSalary(),employee.getJoiningdate(),30);
        //convert employee to putable format
    }
    public boolean GetIfContainsDigit(String ToCheck){
        char[] StrAsCharArray=ToCheck.toCharArray();
        for(char c : StrAsCharArray){
            if(Character.isDigit(c)){
                return true;
            }
        }
        return false;
    }
    //public boolean GetIfInputsValid(EmployeeDetails)
    public String GetDateInCorrectFormat(String Date){
        Log.d("Date:",Date);
        String Month=""+Date.charAt(8)+Date.charAt(9)+Date.charAt(10);
        String MonthNum=GetMonth(Month);
        String Year=""+Date.charAt(12)+Date.charAt(13)+Date.charAt(14)+Date.charAt(15);

        return Year+"/"+MonthNum+"/"+Date.charAt(5)+Date.charAt(6);
        // function converts date to strict date outlined by api
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
        // returns month in correct format
    }

    private void showAlertDialogue(String title,String message,String PositiveButtontext,String PositiveToastText,String NegativeButtonText,String NegativeToastText,Class PageToLoadOnConfirm,String UName,int UserID) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        //builder.setCancelable(false);
        // above line prevents alert from closing when area outside box clicked
        builder.setPositiveButton(PositiveButtontext, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(EditDetails.this,PositiveToastText,Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(EditDetails.this,PageToLoadOnConfirm);
                intent.putExtra("UName",UName);
                intent.putExtra("ID",UserID);
                startActivity(intent);
            }
        });
        builder.setNegativeButton(NegativeButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(EditDetails.this,NegativeToastText,Toast.LENGTH_SHORT).show();

            }
        });
        AlertDialog alertDialog= builder.create();
        alertDialog.show();
    }
    public String GetUserName(String FName,String LName){
        try{
            return FName.charAt(0)+LName;
        }catch (Exception e){
            return FName+LName;
        }
        // return user name formatted to match other employee user names
    }
}