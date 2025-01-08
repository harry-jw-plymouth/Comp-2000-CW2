package com.example.finalattempt;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.ReferenceQueue;
import java.util.Calendar;


public class AddNewEmployeeAdmin extends AppCompatActivity {
    Button Save,Discard,PickHireDate;
    Spinner RoleSelection;
    TextView Result,SelectedHDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_new_employee_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Save=(Button)findViewById(R.id.SaveNewEmployee);
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save button
                Person CurrentInputs=GetInputtedValues();
                Log.d("Details obtained","Details obtained succesfully");
                if(CheckIfAllFieldsValid(CurrentInputs)){
                    showAlertDialogueForSuccesfulinputs("Confirm new employee","Confirm creation of new employee?","Confirm","New employee saved","Continue editing","Save aborted", adminMainPage.class,CurrentInputs);
                    //if new user inputs valid open save dialog function
                }
            }
        });
        PickHireDate=(Button)findViewById(R.id.HireDatePickerButton);
        SelectedHDate=(TextView)findViewById(R.id.SelectedHireDate);

        PickHireDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c=Calendar.getInstance();
                int y=c.get(Calendar.YEAR);
                int m =c.get(Calendar.MONTH);
                int d=c.get(Calendar.DAY_OF_MONTH);
                // save selected values

                DatePickerDialog datePickerDialog=new DatePickerDialog(AddNewEmployeeAdmin.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        SelectedHDate.setText(year+"/"+(month+1)+"/"+dayOfMonth);
                        //set text to selected date
                    }
                },y,m,d);
                datePickerDialog.show();
                // show dialog
            }
        });
        Discard = (Button) findViewById(R.id.DiscardNewEmployee);
        // discard button
        Discard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAlertDialogue("Discard new employee?", "All details will be lost", "Discard", "New employee discarded", "Continue editing", "Resumed editing", adminMainPage.class);
                    }
                });

        RoleSelection = findViewById(R.id.RoleSpinner);
        ArrayAdapter<CharSequence> RoleAdapter = ArrayAdapter.createFromResource(this, R.array.RoleOptions, android.R.layout.simple_spinner_item);
        RoleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        RoleSelection.setAdapter(RoleAdapter);
        //set up role selection spinner
    }


    private void showAlertDialogueForSuccesfulinputs(String title, String message, String PositiveButtontext, String PositiveToastText, String NegativeButtonText, String NegativeToastText, Class PageToLoadOnConfirm,Person New) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddNewEmployeeAdmin.this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(PositiveButtontext, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(AddNewEmployeeAdmin.this, PositiveToastText, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddNewEmployeeAdmin.this, PageToLoadOnConfirm);
                EmployeeService.addEmployee(AddNewEmployeeAdmin.this,ConvertToUploadableObject(New));
                startActivity(intent);
                //add new employee to api and load admin main page on successful inputs
            }
        });
        builder.setNegativeButton(NegativeButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(AddNewEmployeeAdmin.this, NegativeToastText, Toast.LENGTH_SHORT).show();
                // returns user to editing on negative button click
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        // show build dialog
    }
    public boolean CheckIfAllFieldsValid(Person CurrentInputs) {
        Result = (TextView) findViewById(R.id.CreateNewemployeeResults);
        if (CurrentInputs.getFirstname().isEmpty()) {
            Result.setText("Please enter a first name");
            return false;
            // if no first name entered prevent new employee creation
        }
        if (CurrentInputs.getLastname().isEmpty()) {
            Result.setText("Please input Last name");
            return false;
            //if not last name entered prevent new employee creatuin
        }
        if (CurrentInputs.getSalary() == 0) {
            Result.setText("Please enter a salary");
            return false;
            // if no salary entered prevent new employee creation
        }
        if(CurrentInputs.getJoiningdate().isEmpty()){
            Result.setText("Please input a hiring date");
            return false;
            // if no joining date entered prevent new employee creation
        }
        if (GetIfContainsDigit(CurrentInputs.getFirstname()) || GetIfContainsDigit(CurrentInputs.getLastname())) {
            Result.setText("Name cannot contain numbers");
            return false;
            // if name entered contains non alphabetical chars prevent new employee creation
        }
        if (CurrentInputs.getDepartment().equals("Select option")) {
            Result.setText("Please select the employees role");
            return false;
            // if no role selected prevent new employee creation
        }
        return true;
        // if no issues allow employee creation
    }
    public boolean GetIfContainsDigit(String ToCheck) {
        char[] StrAsCharArray = ToCheck.toCharArray();
        for (char c : StrAsCharArray) {
            if (Character.isDigit(c)) {
                return true;
                // return true if non alphabetical char in string
            }
        }
        return false;

    }

    private void showAlertDialogue(String title, String message, String PositiveButtontext, String PositiveToastText, String NegativeButtonText, String NegativeToastText, Class PageToLoadOnConfirm) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddNewEmployeeAdmin.this);
        builder.setTitle(title);
        builder.setMessage(message);
        //builder.setCancelable(false);
        // above line prevents alert from closing when area outside box clicked
        builder.setPositiveButton(PositiveButtontext, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(AddNewEmployeeAdmin.this, PositiveToastText, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddNewEmployeeAdmin.this, PageToLoadOnConfirm);
                startActivity(intent);
            }
        });
        builder.setNegativeButton(NegativeButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(AddNewEmployeeAdmin.this, NegativeToastText, Toast.LENGTH_SHORT).show();

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public Person GetInputtedValues() {
        EditText FNAme = (EditText) findViewById(R.id.NewEmployeeFirstName);
        String FirstName = FNAme.getText().toString();
        // get Firstname from edit text

        EditText LName = (EditText) findViewById(R.id.NewEmployeeLastName);
        String LastName = LName.getText().toString();
        // get Lastname from edit text
        Float Salary;

        EditText salary = (EditText) findViewById(R.id.NewEmployeeSalary);
        try {//try to catch inputs causing errors
            Salary = Float.parseFloat(salary.getText().toString());
        } catch (Exception e) {
            Salary = (float) 0;
        }
        // get salary from edit text

        String Role = RoleSelection.getSelectedItem().toString();
        String HireDate = SelectedHDate.getText().toString();
        String email=FirstName.charAt(0)+LastName+"@company.com";
        // get email,role and hire date from inputs

        return new Person(9999, FirstName,LastName,email, Role, HireDate,Salary);
        // return employee with inputted values
        // id is set to 9999 as API will set it when employee is created
    }
    public Employee ConvertToUploadableObject(Person ToConvert){
        return new Employee(ToConvert.getFirstname(),ToConvert.getLastname(),ToConvert.getEmail(),ToConvert.getDepartment(),ToConvert.getJoiningdate(),ToConvert.getSalary());
        //must be made into employee object to be created in api
    }
    public void UploadToApi(Person NewPerson) {
        Employee employee=ConvertToUploadableObject(NewPerson);
        RequestQueue queue = Volley.newRequestQueue(AddNewEmployeeAdmin.this);
        String URL = "http://10.224.41.11/comp2000/employees/add";
        Gson gson = new Gson();

        try {
            JSONObject jsonRequest = new JSONObject(gson.toJson(employee));
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, jsonRequest, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    String message = response.optString("Message", "Employee added successfully");
                    Log.d("Employee service", message);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("EmployeeError", "Error adding employee: " + error.getMessage());

                }
            });
            queue.add(request);

        } catch (JSONException e) {
            Log.e("Employee error", "Invalid JSON format" + e.getMessage());
        }

    }


}
