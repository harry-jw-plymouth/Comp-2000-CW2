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
    Spinner GenderSelection,RoleSelection;
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
                Log.d("Button clicked","Button cicked successfully");

                Person CurrentInputs=GetInputtedValues();
                Log.d("Details obtained","Details obtained succesfully");
                if(CheckIfAllFieldsValid(CurrentInputs)){
                    showAlertDialogueForSuccesfulinputs("Confirm new employee","Confirm creation of new employee?","Confirm","New employee saved","Continue editing","Save aborted", adminMainPage.class,CurrentInputs);
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

                DatePickerDialog datePickerDialog=new DatePickerDialog(AddNewEmployeeAdmin.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        SelectedHDate.setText(year+"/"+(month+1)+"/"+dayOfMonth);
                    }
                },y,m,d);
                datePickerDialog.show();
            }
        });
        Discard = (Button) findViewById(R.id.DiscardNewEmployee);
        Discard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAlertDialogue("Discard new employee?", "All details will be lost", "Discard", "New employee discarded", "Continue editing", "Resumed editing", adminMainPage.class);
                    }
                });

      //  GenderSelection = findViewById(R.id.GenderSpinner);
      //  ArrayAdapter<CharSequence> GenderAdapter = ArrayAdapter.createFromResource(this, R.array.GenderOptions, android.R.layout.simple_spinner_item);
      //  GenderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
     //   GenderSelection.setAdapter(GenderAdapter);

        RoleSelection = findViewById(R.id.RoleSpinner);
        ArrayAdapter<CharSequence> RoleAdapter = ArrayAdapter.createFromResource(this, R.array.RoleOptions, android.R.layout.simple_spinner_item);
        RoleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        RoleSelection.setAdapter(RoleAdapter);
    }


    private void showAlertDialogueForSuccesfulinputs(String title, String message, String PositiveButtontext, String PositiveToastText, String NegativeButtonText, String NegativeToastText, Class PageToLoadOnConfirm,Person New) {
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
                EmployeeService Uploader=new EmployeeService();
                EmployeeService.addEmployee(AddNewEmployeeAdmin.this,ConvertToUploadableObject(New));
                //EmployeeService.addEmployee(AddNewEmployeeAdmin.this,new Employee("testname","TestName","test@gmail.com","Marketing","2023-01-15",50000F));
               // UploadToApi(New);
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
    public boolean CheckIfAllFieldsValid(Person CurrentInputs) {
        Result = (TextView) findViewById(R.id.CreateNewemployeeResults);
        if (CurrentInputs.getFirstname().isEmpty()) {
            Result.setText("Please enter a first name");
            return false;
        }
        if (CurrentInputs.getLastname().isEmpty()) {
            Result.setText("Please input Last name");
            return false;
        }
        if (CurrentInputs.getSalary() == 0) {
            Result.setText("Please enter a salary");
            return false;
        }
        //if(CurrentInputs.getBirthDate().isEmpty()){
        //  Result.setText("Please input a birth date");
        //  return false;
        //}
        if(CurrentInputs.getJoiningdate().isEmpty()){
            Result.setText("Please input a hiring date");
            return false;
        }
        if (GetIfContainsDigit(CurrentInputs.getFirstname()) || GetIfContainsDigit(CurrentInputs.getLastname())) {
            Result.setText("Name cannot contain numbers");
            return false;
        }
        if (CurrentInputs.getDepartment().equals("Select option")) {
            Result.setText("Please select the employees role");
            return false;
        }
        // if (CurrentInputs.get().equals("Select option")) {
        //   Result.setText("Please select option for employee gender");
        //  return false;
        //}

        //check if hire date>Birthdate
        //
        return true;
    }
    public boolean GetIfContainsDigit(String ToCheck) {
        char[] StrAsCharArray = ToCheck.toCharArray();
        for (char c : StrAsCharArray) {
            if (Character.isDigit(c)) {
                return true;
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
        Log.d("Fname inputted",  FirstName);

        EditText LName = (EditText) findViewById(R.id.NewEmployeeLastName);
        String LastName = LName.getText().toString();

        Float Salary;

        EditText salary = (EditText) findViewById(R.id.NewEmployeeSalary);
        try {
            Salary = Float.parseFloat(salary.getText().toString());
        } catch (Exception e) {
            Salary = (float) 0;
        }


        String Role = RoleSelection.getSelectedItem().toString();
        //String Gender = GenderSelection.getSelectedItem().toString();

        String HireDate = SelectedHDate.getText().toString();// temp,will edit when calender date picker working
        String BirthDate = "";

        String email=FirstName.charAt(0)+LastName+"@company.com";


        return new Person(9999, FirstName,LastName,email, Role, HireDate,Salary);
    }
    public Employee ConvertToUploadableObject(Person ToConvert){
        return new Employee(ToConvert.getFirstname(),ToConvert.getLastname(),ToConvert.getEmail(),ToConvert.getDepartment(),ToConvert.getJoiningdate(),ToConvert.getSalary());
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
