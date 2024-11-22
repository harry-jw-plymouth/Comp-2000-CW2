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

import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class AddNewEmployeeAdmin extends AppCompatActivity {
    Button Save,Discard;
    Spinner GenderSelection,RoleSelection;
    TextView Result;

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

                FullEmployeeDetails CurrentInputs=GetInputtedValues();
                Log.d("Details obtained","Details obtained succesfully");
                if(CheckIfAllFieldsValid(CurrentInputs)){
                    showAlertDialogue("Confirm new employee","Confirm creation of new employee?","Confirm","New employee saved","Continue editing","Save aborted", adminMainPage.class);
                }

            }
        });

        Discard=(Button)findViewById(R.id.DiscardNewEmployee);
        Discard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialogue("Discard new employee?","All details will be lost","Discard","New employee discarded","Continue editing","Resumed editing", adminMainPage.class);
            }
        });

        GenderSelection=findViewById(R.id.GenderSpinner);
        ArrayAdapter<CharSequence>GenderAdapter=ArrayAdapter.createFromResource(this,R.array.GenderOptions, android.R.layout.simple_spinner_item);
        GenderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        GenderSelection.setAdapter(GenderAdapter);

        RoleSelection=findViewById(R.id.RoleSpinner);
        ArrayAdapter<CharSequence>RoleAdapter=ArrayAdapter.createFromResource(this,R.array.RoleOptions, android.R.layout.simple_spinner_item);
        RoleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        RoleSelection.setAdapter(RoleAdapter);



    }
    public FullEmployeeDetails GetInputtedValues(){
        EditText FNAme=(EditText)findViewById(R.id.NewEmployeeFirstName);
        String FirstName=FNAme.getText().toString();

        EditText LName=(EditText)findViewById(R.id.NewEmployeeLastName);
        String LastName=LName.getText().toString();

        Float Salary;

        EditText salary=(EditText)findViewById(R.id.NewEmployeeSalary);
        try {
            Salary = Float.parseFloat( salary.getText().toString());
        }catch (Exception e){
            Salary= (float) 0;
        }


        String Role = RoleSelection.getSelectedItem().toString();
        String Gender=GenderSelection.getSelectedItem().toString();

        String HireDate="";// temp,will edit when calender date picker working
        String BirthDate="";


        return new FullEmployeeDetails(FirstName,LastName,Role,Gender,HireDate,BirthDate,Salary,"") ;
    }
    public boolean CheckIfAllFieldsValid(FullEmployeeDetails CurrentInputs){
        Result=(TextView) findViewById(R.id.CreateNewemployeeResults);
        if(CurrentInputs.getFirst_Name().isEmpty()){
            Result.setText("Please enter a first name");
            return  false;
        }
        if(CurrentInputs.getLast_Name().isEmpty()){
            Result.setText("Please input Last name");
            return false;
        }
        if(CurrentInputs.getSalary()==0){
            Result.setText("Please enter a salary");
            return false;
        }
        //if(CurrentInputs.getBirthDate().isEmpty()){
          //  Result.setText("Please input a birth date");
          //  return false;
        //}
        //if(CurrentInputs.getHireDate().isEmpty()){
       //       Result.setText("Please input a hiring date");
       //       return false;
       // }
        if(GetIfContainsDigit(CurrentInputs.getFirst_Name())||GetIfContainsDigit(CurrentInputs.getLast_Name())){
            Result.setText("Name cannot contain numbers");
            return false;
        }
        if(CurrentInputs.getRole().equals("Select option")){
            Result.setText("Please select the employees role");
            return false;
        }
        if(CurrentInputs.getGender().equals("Select option")){
            Result.setText("Please select option for employee gender");
            return false;
        }

        //check if hire date>Birthdate
        //
        return true;
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
    private void showAlertDialogue(String title,String message,String PositiveButtontext,String PositiveToastText,String NegativeButtonText,String NegativeToastText,Class PageToLoadOnConfirm) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        //builder.setCancelable(false);
        // above line prevents alert from closing when area outside box clicked
        builder.setPositiveButton(PositiveButtontext, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(AddNewEmployeeAdmin.this,PositiveToastText,Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(AddNewEmployeeAdmin.this,PageToLoadOnConfirm);
                startActivity(intent);
            }
        });
        builder.setNegativeButton(NegativeButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(AddNewEmployeeAdmin.this,NegativeToastText,Toast.LENGTH_SHORT).show();

            }
        });
        AlertDialog alertDialog= builder.create();
        alertDialog.show();
    }
}