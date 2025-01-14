package com.example.finalattempt;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.Month;
import java.util.Calendar;
import java.util.Date;

public class HolidayMainPage extends AppCompatActivity {
    Button Home;
    TextView FName,LName,Date,Salary,Email,Role;
    int SDay,SMonth,SYear,EDay,EMonth,EYear;
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
        TextView Welcome=findViewById(R.id.editdetailsbox);
        TextView LeaveBox=findViewById(R.id.Leaves);
        FName= findViewById(R.id.FName);
        LName= findViewById(R.id.LName);
        Salary=findViewById(R.id.Sal);
        Date=findViewById(R.id.Date);
        Email=findViewById(R.id.Email);
        Role=findViewById(R.id.RoleView);

        Gson gson=new Gson();
        RequestQueue RequestQueue= Volley.newRequestQueue(HolidayMainPage.this);
        String URL="http://10.224.41.11/comp2000/employees/get/"+ UserId;
        JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                EmployeeWithLeaves employee = gson.fromJson(response.toString(), EmployeeWithLeaves.class);
                Log.d("Leaves",""+employee.getLeaves());
                int Leaves=employee.getLeaves();
                Welcome.setText("Below you can edit and view your annual leave bookings. Please note, you will not be able to make requests that leave you with more than 30 days booked off " +
                        "you currently have "+ Leaves+" days left to book");
                LeaveBox.setText(Integer.toString(Leaves));
                Salary.setText(Float.toString(employee.getSalary()));
         //       FName.setText(employee.getFirstname());
           //     LName.setText(employee.getLastname());
              //  Log.d("Date",employee.getJoiningdate());
                //Date.setText("");
             //   Email.setText(employee.getEmail());
               // Role.setText(employee.getDepartment());


                Log.d("LEaves in request",employee.getLeaves()+"");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Employee error","Error retrieving employee");
                //error getting employee
            }
        });
       // int Leaves=Integer.parseInt(Welcome.getText().toString());
       // Log.d("Leaves after call",Leaves+"");
        RequestQueue.add(request);
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
                //final int SDay=d;
                //save values
                DatePickerDialog datePickerDialog=new DatePickerDialog(HolidayMainPage.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        OpenCalender1.setText(year+"/"+(month+1)+"/"+dayOfMonth);
                        SDay=dayOfMonth;
                        SMonth=month;
                        SYear=year;
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
                        EYear=year;
                        EMonth=month;
                        EDay=dayOfMonth;
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
                int Leaves=Integer.parseInt(LeaveBox.getText().toString());
                String StartDate=OpenCalender1.getText().toString();
                String EndDate=OpenCalender2.getText().toString();
                if(!StartDate.isEmpty()&&!EndDate.isEmpty()){
                    if (GetIfEndDateBeforeStartDate(SDay,SMonth,SYear,EDay,EMonth,EYear)){
                        Toast.makeText(HolidayMainPage.this,"End date must be after start date",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        int Remaining=GetLeavesAfterBooking(SDay,SMonth,SYear,EDay,EMonth,EYear,Leaves);
                        if(0<Remaining){
                            AlertDialog.Builder builder=new AlertDialog.Builder(HolidayMainPage.this);
                            builder.setTitle("Request Holiday?");
                            builder.setMessage("From " + OpenCalender1.getText().toString()+ " To "+OpenCalender2.getText().toString()+" " +
                                    "You will have " +GetLeavesAfterBooking(SDay,SMonth,SYear,EDay,EMonth,EYear,Leaves)+ " leave days remaining after booking");
                            builder.setPositiveButton("Request", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String StartDate=OpenCalender1.getText().toString();
                                    String EndDate=OpenCalender2.getText().toString();


                                    HolidayRequestDataModel newRequest= new HolidayRequestDataModel(0,UserId,UName,"Requested",StartDate,EndDate);
                                    EmployeeDBHelper db= new EmployeeDBHelper(HolidayMainPage.this);
                                    if(db.addHolidayRequest(newRequest)){
                                        //EmployeeWithLeaves emp=new EmployeeWithLeaves(FName.getText().toString(),LName.getText().toString(),Email.getText().toString(),Role.getText().toString(),Date.getText().toString(),Float.parseFloat(Salary.getText().toString()) );
                                        //emp.setLeaves(Remaining);
                                        //EmployeeService.updateEmployee(HolidayMainPage.this,UserId, emp);
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
                        else{
                            Toast.makeText(HolidayMainPage.this,"Not enough leaves",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else {
                    Toast.makeText(HolidayMainPage.this,"Please select dates before making request",Toast.LENGTH_SHORT).show();
                }
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
    public int GetLeavesAfterBooking(int SDay,int SMonth,int SYear,int EDay,int EMonth,int EYear,int Leaves){
        int DaysinMonth=GetDaysInMonth(SMonth);
        int days=0,months=0,years=0;
        if(SDay>EDay){
            EMonth--;
            days=DaysinMonth-(SDay-EDay);
        }
        else{
            days=EDay-SDay;
        }
        if(SMonth>EMonth){
            EYear--;
            months=12-(SMonth-EMonth);
        }
        else {
            months=EMonth-SMonth;
        }
        int Remaining=Leaves-((months*30)+days)-1;

        return  Remaining;
    }
    int GetDaysInMonth(int Month){
        if (Month==1){
            return 31;
        }
        if (Month==2){
            return 28;
        }
        if (Month==3){
            return 31;
        }
        if (Month==4){
            return 30;
        }
        if (Month==5){
            return 31;
        }
        if (Month==6){
            return 30;
        }
        if (Month==7){
            return 31;
        }
        if (Month==8){
            return 31;
        }
        if (Month==9){
            return 30;
        }
        if (Month==10){
            return 31;
        }
        if (Month==11){
            return 30;
        }
        if (Month==12){
            return 31;
        }
        return 30;
    }
    public boolean GetIfEndDateBeforeStartDate(int SDay,int SMonth,int SYear,int EDay,int EMonth,int EYear){
        if (SYear==EYear){
            if (SMonth==EMonth){
                if(SDay<=EDay){
                    return false;
                }
                else {
                    return true;
                }
            }
            else if(SMonth>EMonth){
                return true;
            }
            else {
                return true;
            }

        }
        else if (SYear<EYear){
            return false;

        }
        return true;
    }
    public String GetDateInCorrectFormat(String Date){
        Log.d("Date:",Date);
        String Month=""+Date.charAt(8)+Date.charAt(9)+Date.charAt(10);
        String MonthNum=GetMonth(Month);
        String Year=""+Date.charAt(12)+Date.charAt(13)+Date.charAt(14)+Date.charAt(15);

        return Year+"/"+MonthNum+"/"+Date.charAt(5)+Date.charAt(6);
        // function converts date to strict date outlined by api
    }
    public String GetMonth(String Month){
        switch (Month){
            case "Jan":
                return "1";
            case "Feb":
                return "2";
            case "Mar":
                return "3";
            case "Apr":
                return "4";
            case "May":
                return "5";
            case "Jun":
                return "6";
            case "Jul":
                return "7";
            case "Aug":
                return "8";
            case "Sep":
                return "9";
            case "Oct":
                return "10";
            case "Nov":
                return "11";
            case "Dec":
                return "12";

        }
        return "";
        // returns month in correct format
    }
}