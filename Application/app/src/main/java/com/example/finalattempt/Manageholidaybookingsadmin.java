package com.example.finalattempt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
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
import java.util.List;



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

        holidayRequestList.add(new HolidayRequest(0,"Harry Watton","Requested","10/10/24","12/10/24"));
        holidayRequestList.add(new HolidayRequest(5,"William Richards","Approved","15/12/24","25/12/24"));
        holidayRequestList.add(new HolidayRequest(4,"Owen Wiffen","Declined","16/1/24","20/2/24"));
        holidayRequestList.add(new HolidayRequest(0,"Harry Watton","Requested","10/10/24","12/10/24"));
        holidayRequestList.add(new HolidayRequest(5,"William Richards","Approved","15/12/24","25/12/24"));
        holidayRequestList.add(new HolidayRequest(4,"Owen Wiffen","Declined","16/1/24","20/2/24"));
        holidayRequestList.add(new HolidayRequest(0,"Harry Watton","Requested","10/10/24","12/10/24"));
        holidayRequestList.add(new HolidayRequest(5,"William Richards","Approved","15/12/24","25/12/24"));
        holidayRequestList.add(new HolidayRequest(4,"Owen Wiffen","Declined","16/1/24","20/2/24"));

        adapter=new HolidayRequestAdapter(this,holidayRequestList);
        recyclerView.setAdapter(adapter);

        Home=(Button) findViewById(R.id.AHPBacktohome);
        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Manageholidaybookingsadmin.this,adminMainPage.class);
                startActivity(intent);
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
}