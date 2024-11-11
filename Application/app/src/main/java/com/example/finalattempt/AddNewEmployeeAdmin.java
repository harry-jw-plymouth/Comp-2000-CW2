package com.example.finalattempt;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;



public class AddNewEmployeeAdmin extends AppCompatActivity {
    Button Save,Discard;
    Spinner GenderSelection,RoleSelection;

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
        ArrayAdapter<CharSequence>Genderadapter=ArrayAdapter.createFromResource(this,R.array.GenderOptions, android.R.layout.simple_spinner_item);
        Genderadapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        GenderSelection.setAdapter(Genderadapter);

        RoleSelection=findViewById(R.id.RoleSpinner);
        ArrayAdapter<CharSequence>RoleAdapter=ArrayAdapter.createFromResource(this,R.array.RoleOptions, android.R.layout.simple_spinner_item);
        RoleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        RoleSelection.setAdapter(RoleAdapter);



    }
    public NewEmployeeDetails GetInputtedValues(){
        EditText FNAme=(EditText)findViewById(R.id.NewEmployeeFirstName);
        String FirstName=FNAme.getText().toString();

        EditText LName=(EditText)findViewById(R.id.NewEmployeeLastName);
        String LastName=LName.getText().toString();

        EditText salary=(EditText)findViewById(R.id.NewEmployeeSalary);
        float Salary = Float.parseFloat( salary.getText().toString());

        String Role = RoleSelection.getSelectedItem().toString();
        String Gender=GenderSelection.getSelectedItem().toString();


        return null;
    }
    public boolean CheckIfAllFieldsValid(){

        return true;
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