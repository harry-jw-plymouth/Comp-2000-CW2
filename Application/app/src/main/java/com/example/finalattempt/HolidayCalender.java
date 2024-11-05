package com.example.finalattempt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HolidayCalender extends AppCompatActivity {
    Button Back;
    CalendarView Calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        int CSelected=intent.getIntExtra("DateSelection",1);
        //int CSelected=intent.getIntExtra("DateSelection");
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_holiday_calender);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Back=(Button)findViewById(R.id.HolidayCalenderBackButton);
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HolidayCalender.this,HolidayMainPage.class);
                startActivity(intent);
            }
        });
        Calendar=(CalendarView) findViewById(R.id.CalendarViewForPrevHolidays);
        Calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Intent intent=new Intent(HolidayCalender.this,HolidayMainPage.class);
                intent.putExtra("year",year);
                intent.putExtra("month",month);
                intent.putExtra("day",dayOfMonth);
                intent.putExtra("DateSelection",CSelected);
                startActivity(intent);
            }
        });
    }
}