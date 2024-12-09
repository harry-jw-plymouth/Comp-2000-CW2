package com.example.finalattempt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ViewDetails extends AppCompatActivity {
    Button HomeButton;Button EditMyDetails;
    int ID;
    TextView FName, LName, HDate,Role,Salary;

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

        HomeButton=(Button)findViewById(R.id.BackToHomeId);
        HomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ViewDetails.this, MainActivity2.class);
                startActivity(intent);
            }
        });
        EditMyDetails=(Button)findViewById(R.id.EditDetailsButton2);
        EditMyDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ViewDetails.this,EditDetails.class);
                startActivity(intent);
            }
        });
        Employee Temp=new Employee("Name1","Name2","Name1@Company.com","Marketing","date",3456.34F);
        //Employee Current=EmployeeService.getEmployeeById(ViewDetails.this, ID);

        FName=(TextView) findViewById(R.id.FnameView);
        FName.setText(Temp.firstname);

    }
}