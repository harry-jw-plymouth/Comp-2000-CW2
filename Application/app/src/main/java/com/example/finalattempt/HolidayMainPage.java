package com.example.finalattempt;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class HolidayMainPage extends AppCompatActivity {
    Button Home;
    Button OpenCalender1;Button OpenCalender2;
    Button MakeRequest;Button ViewBookings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        Intent intent=getIntent();
        String UName=intent.getStringExtra("UName");
        int UserId=intent.getIntExtra("ID",0); // get details of user signed in

        setContentView(R.layout.activity_holiday_main_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Home=(Button)findViewById(R.id.HolidayHomeButton); // back to home button
        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HolidayMainPage.this,MainActivity2.class);
                intent.putExtra("UName",UName);
                intent.putExtra("test",UserId);
                startActivity(intent); // go back to home
            }
        });
        OpenCalender1=(Button)findViewById(R.id.HolidayStartButton);//holiday start date button
        OpenCalender1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final Calendar c=Calendar.getInstance();
                int y=c.get(Calendar.YEAR);
                int m =c.get(Calendar.MONTH);
                int d=c.get(Calendar.DAY_OF_MONTH);
                //save values
                DatePickerDialog datePickerDialog=new DatePickerDialog(HolidayMainPage.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        OpenCalender1.setText(year+"/"+(month+1)+"/"+dayOfMonth);
                        //display selected date in button
                    }
                },y,m,d);
                datePickerDialog.show();
                // show date picker
            }
        });
        OpenCalender2=(Button)findViewById(R.id.HolidayEndButton);//holiday end date button
        OpenCalender2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final Calendar c=Calendar.getInstance();
                int y=c.get(Calendar.YEAR);
                int m =c.get(Calendar.MONTH);
                int d=c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog=new DatePickerDialog(HolidayMainPage.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        OpenCalender2.setText(year+"/"+(month+1)+"/"+dayOfMonth);
                    }
                },y,m,d);
                datePickerDialog.show();
                //show date picker

            }
        });
        MakeRequest=(Button)findViewById(R.id.RequestButton);
        MakeRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(HolidayMainPage.this);
                builder.setTitle("Request Holiday?");
                builder.setMessage("From " + OpenCalender1.getText().toString()+ " To "+OpenCalender2.getText().toString());
                builder.setPositiveButton("Request", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String StartDate=OpenCalender1.getText().toString();
                        String EndDate=OpenCalender2.getText().toString();
                        HolidayRequestDataModel newRequest= new HolidayRequestDataModel(0,UserId,UName,"Requested",StartDate,EndDate);
                        EmployeeDBHelper db= new EmployeeDBHelper(HolidayMainPage.this);
                        if(db.addHolidayRequest(newRequest)){
                            DBHelper Db=new DBHelper(HolidayMainPage.this);
                            Db.addNotification(new NotificationDataModel(0,UserId,UName,"EmployeeRequestingHoliday") ); // notify admin of new request
                            Toast.makeText(HolidayMainPage.this,"Request sent to admin",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(HolidayMainPage.this,"Request not made,error sending request",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(HolidayMainPage.this,"Request not made",Toast.LENGTH_SHORT).show();
                        //alert user request not made
                    }
                });
                AlertDialog alertDialog= builder.create();
                alertDialog.show(); // open alert dialog
            }
        });
        ViewBookings=(Button)findViewById(R.id.PreviousHolidaybutton);
        ViewBookings.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HolidayMainPage.this,PreviousHolidays.class);
                intent.putExtra("ID",UserId);
                intent.putExtra("UName",UName); // load page displaying holiday bookings
                startActivity(intent);
            }
        });
    }
    private void showAlertDialogue(String title,String message,String PositiveButtontext,String PositiveToastText,String NegativeButtonText,String NegativeToastText,Class PageToLoadOnConfirm,String UName,int ID) {
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