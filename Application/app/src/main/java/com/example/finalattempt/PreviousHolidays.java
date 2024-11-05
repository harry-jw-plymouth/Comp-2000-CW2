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

public class PreviousHolidays extends AppCompatActivity {
    Button Back; Button Confirmed; Button Denied; Button Requested;

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
        Back=(Button)findViewById(R.id.PrevHolidayHomeButton);
        Back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(PreviousHolidays.this,HolidayMainPage.class);
                startActivity(intent);
            }
        });
        Confirmed=(Button)findViewById(R.id.ApprovedHoliday);
        Confirmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialogue("Holiday on [Date]","Status:approved","Back to holiday booking","","Back to booked holidays","", HolidayMainPage.class);
            }
        });
        Requested=(Button) findViewById(R.id.RequestedHoliday);
        Requested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialogue("Holiday on [Date]","Status:awaiting approval","Back to holiday booking","","Back to booked holidays","", HolidayMainPage.class);
            }
        });
        Denied=(Button) findViewById(R.id.DeniedHoliday);
        Denied.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialogue("Holiday on [Date]","Status:denied","Back to holiday booking","","Back to booked holidays","", HolidayMainPage.class);
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