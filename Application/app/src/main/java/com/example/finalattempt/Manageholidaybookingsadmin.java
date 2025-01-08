package com.example.finalattempt;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.hardware.lights.Light;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import kotlin.contracts.Returns;


public class Manageholidaybookingsadmin extends AppCompatActivity {
    RecyclerView recyclerView;
    HolidayRequestAdapter adapter;
    List<HolidayRequest>holidayRequestList;
    Activity context;
    Button Home;
    Button SelectToDate,SelectFromDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manageholidaybookingsadmin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        holidayRequestList=new ArrayList<>();
        recyclerView=(RecyclerView) findViewById(R.id.HolidaysrecyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        EmployeeDBHelper EDB= new EmployeeDBHelper(Manageholidaybookingsadmin.this);

        List<HolidayRequestDataModel> RawRequests=EDB.getAllHolidayRequests();
        // get requests from db
        adapter=new HolidayRequestAdapter(this,RawRequests);
        // fill recycler view with requests

        Home=(Button) findViewById(R.id.AHPBacktohome);
        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Manageholidaybookingsadmin.this,adminMainPage.class);
                startActivity(intent);
                //back to home on button click
            }
        });
        SelectFromDate=(Button) findViewById(R.id.FromDateSelector);
        SelectFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowCalender("From");
            }
        });
        SelectToDate=(Button) findViewById(R.id.ToDateSelector);
        SelectToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowCalender("To");
            }
        });
    }
    private void ShowCalender(String Source){

    }
    private void Datepicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //month=month+1;
                String date=makeDateString(dayOfMonth,month,year);

            }

        };
        Calendar cal= Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        int day=cal.get(Calendar.DAY_OF_MONTH);

        //int style = AlertDialog.THEME_HOLO_LIGHT;
        //DatePickerDialog= new DatePickerDialog(this,,dateSetListener,year,month,day);
    }
    public String makeDateString(int day,int month, int year){
        return day+" "+month+" "+year;
    }
    private String GetNameOfMonth(int position){
        switch (position){
            case 0:
                return "JAN";
            case 1:
                return "FEB";
            case 2:
                return "MAR";
            case 3:
                return "APR";
            case 4:
                return "MAY";
            case 5:
                return "JUN";
            case 6:
                return "JUL";
            case 7:
                return "AUG";
            case 8:
                return "SEP";
            case 9:
                return "OCT";
            case 10:
                return "NOV";
            case 11:
                return "DEC";
        }
        return "";
    }
    public void OpenDatePicker(View view){

    }
}