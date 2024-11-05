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

public class HolidayMainPage extends AppCompatActivity {
    Button Home;
    Button OpenCalender1;Button OpenCalender2;
    Button MakeRequest;Button ViewBookings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        Intent intent=getIntent();
        int year=intent.getIntExtra("year",1);
        int day=intent.getIntExtra("day",1);
        int month=intent.getIntExtra("month",1);
        int DateSeleced=intent.getIntExtra("DateSelection",1);

        setContentView(R.layout.activity_holiday_main_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Home=(Button)findViewById(R.id.HolidayHomeButton);
        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HolidayMainPage.this,MainActivity2.class);
                startActivity(intent);
            }
        });
        OpenCalender1=(Button)findViewById(R.id.HolidayStartButton);
        OpenCalender1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(HolidayMainPage.this,HolidayCalender.class);
                intent.putExtra("DateSelection",1);
                startActivity(intent);
            }
        });
        OpenCalender2=(Button)findViewById(R.id.HolidayEndButton);
        OpenCalender2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(HolidayMainPage.this,HolidayCalender.class);
                intent.putExtra("DateSelection",2);
                startActivity(intent);
            }
        });
        MakeRequest=(Button)findViewById(R.id.RequestButton);
        MakeRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intent=new Intent(HolidayMainPage.this,RequestCheck.class);
               // startActivity(intent);
                showAlertDialogue("Confirm request","make request with dates specified?","Confirm","Request confirmed,this will be checked by an admin","Back","Request not made",HolidayMainPage.class);
            }
        });
        ViewBookings=(Button)findViewById(R.id.PreviousHolidaybutton);
        ViewBookings.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HolidayMainPage.this,PreviousHolidays.class);
                startActivity(intent);
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
                Toast.makeText(HolidayMainPage.this,PositiveToastText,Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(HolidayMainPage.this,PageToLoadOnConfirm);
                startActivity(intent);
            }
        });
        builder.setNegativeButton(NegativeButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(HolidayMainPage.this,NegativeToastText,Toast.LENGTH_SHORT).show();

            }
        });
        AlertDialog alertDialog= builder.create();
        alertDialog.show();
    }
}