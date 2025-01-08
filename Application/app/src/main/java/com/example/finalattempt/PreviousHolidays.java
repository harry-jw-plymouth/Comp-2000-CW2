package com.example.finalattempt;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PreviousHolidays extends AppCompatActivity {
    Button Back; Button Confirmed; Button Denied; Button Requested;
    RecyclerView recyclerView;
    List<HolidayRequest> holidayRequestList;
    HolidayRequestAdapterEmployee adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_previous_holidays);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent=getIntent();
        String UName=intent.getStringExtra("UName");
        int UserId=intent.getIntExtra("ID",0);

        holidayRequestList=new ArrayList<>();
        recyclerView=findViewById(R.id.HolidaysrecyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // set up recycler view to display employees
        EmployeeDBHelper EDB= new EmployeeDBHelper(PreviousHolidays.this);

        List<HolidayRequestDataModel> RawRequests=EDB.getAllHolidayRequestsByID(UserId);
        // get all holiday requests from db for current employee
        for(HolidayRequestDataModel RR:RawRequests){
            holidayRequestList.add(new HolidayRequest(RR.getEmployeeId(),RR.getEmployeeName(),RR.getStatus(),RR.getStartDate(),RR.GetEndDate()));
            // for each item got from db, store in format displayable in recycler view
        }
        adapter=new HolidayRequestAdapterEmployee(this,RawRequests);
        recyclerView.setAdapter(adapter); // set recycler view

        Back=(Button)findViewById(R.id.PrevHolidayHomeButton);
        Back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(PreviousHolidays.this,HolidayMainPage.class);
                intent.putExtra("ID",UserId);
                intent.putExtra("UName",UName);
                startActivity(intent);
                // back to holiday main page on button click
            }
        });
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
                Toast.makeText(PreviousHolidays.this,PositiveToastText,Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(PreviousHolidays.this,PageToLoadOnConfirm);
                startActivity(intent);
            }
        });
        builder.setNegativeButton(NegativeButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(PreviousHolidays.this,NegativeToastText,Toast.LENGTH_SHORT).show();

            }
        });
        AlertDialog alertDialog= builder.create();
        alertDialog.show();
    }
}