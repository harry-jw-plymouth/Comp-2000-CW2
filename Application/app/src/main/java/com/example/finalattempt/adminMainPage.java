package com.example.finalattempt;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class adminMainPage extends AppCompatActivity {
    Button SignOut;
    Button ManageHoliday;
    Button AddNewEmployee;
    RecyclerView Employees;
    ArrayList<Person> EmployeeListtemp;
    EmployeeDisplayAdapter Adapter;
    RequestQueue queue;
    String URL;
    TextView Welcome;
    List<Person> Temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_main_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }

        URL = "http://10.224.41.11/comp2000/employees";
        Gson gson = new Gson();
        Temp = new ArrayList<Person>();
        queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response ", response);
                List<Person> EmployeeList = gson.fromJson(response, new TypeToken<List<Person>>() {
                }.getType());
                for (Person person : EmployeeList) {
                    Log.d("Employeeinfo", "Id: " + person.getId() + " Firstname " + person.getFirstname() + ",Salary: " + person.getSalary());
                    Temp.add(new Person(person.getId(), person.getFirstname(), person.getLastname(), person.getEmail(), person.getDepartment(), person.getJoiningdate(), person.getSalary()));
                }
                Employees = (RecyclerView) findViewById(R.id.EmployeeRecyclerView);
                Employees.setHasFixedSize(false);
                Employees.setLayoutManager(new LinearLayoutManager(adminMainPage.this));
                Adapter = new EmployeeDisplayAdapter(adminMainPage.this, EmployeeList);
                //Adapter=new EmployeeDisplayAdapter(this,EmployeeListtemp);
                Employees.setAdapter(Adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Result", "Error getting: " + error.toString());
            }
        });
        Intent intent = getIntent();
        String Name = intent.getStringExtra("Name");
        makeNotification();
        Welcome = findViewById(R.id.Welcome);
        Welcome.setText("Welcome " + Name);

        queue.add(stringRequest);

        SignOut = (Button) findViewById(R.id.AdminMainPageSignOutButton);
        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminMainPage.this, MainActivity.class);
                startActivity(intent);
            }
        });

        ManageHoliday = (Button) findViewById(R.id.AMPViewAndManageHolidayButton);
        ManageHoliday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminMainPage.this, Manageholidaybookingsadmin.class);
                startActivity(intent);
            }
        });

        AddNewEmployee = (Button) findViewById(R.id.AMPAddNewEmployeeButton);
        AddNewEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminMainPage.this, AddNewEmployeeAdmin.class);
                startActivity(intent);
            }
        });

        Employees = (RecyclerView) findViewById(R.id.EmployeeRecyclerView);
        Employees.setHasFixedSize(false);
        Employees.setLayoutManager(new LinearLayoutManager(this));


        RequestQueue queue = Volley.newRequestQueue(this);
    }

    public void makeNotification() {
        String chanelID = "my_channel";
        DBHelper DB = new DBHelper(adminMainPage.this);
        List<NotificationDataModel> UsersNotifications = DB.getAllNotifications();
        for (NotificationDataModel notification : UsersNotifications) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(
                    this, chanelID);
            String Title = "";
            String Content = "";
            Intent intent = new Intent();
            if (notification.getType().equals("EmployeeRequestingHoliday")) {
                Title = "New holiday request";
                intent = new Intent(adminMainPage.this, Manageholidaybookingsadmin.class);
                Content = "new holiday request from " + notification.getUName() + " (ID:" + notification.getUserID() + ")";
            } else if (notification.getType().equals("HolidayRequestUpdate")) {
                Title = "Holiday request response";
                intent = new Intent(adminMainPage.this, PreviousHolidays.class);
                Content = "new holiday request from " + notification.getUName() + " (ID:" + notification.getUserID() + ")";
            }

            builder.setSmallIcon(R.drawable.standardnotification)
                    .setContentTitle(Title)
                    .setContentText(Content)
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            // Intent intent = new Intent(this, MainActivity2.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("data", "data from main activity");


            PendingIntent pendingIntent = PendingIntent.getActivity(
                    this, 0, intent, PendingIntent.FLAG_MUTABLE);

            builder.setContentIntent(pendingIntent);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel notificationChannel =
                        notificationManager.getNotificationChannel(chanelID);

                if (notificationChannel == null) {
                    int importance = NotificationManager.IMPORTANCE_HIGH;

                    notificationChannel = new NotificationChannel(
                            chanelID, "My Notification", importance);

                    notificationChannel.setLightColor(Color.BLUE);
                    notificationChannel.enableVibration(true);
                    notificationManager.createNotificationChannel(notificationChannel);

                }
            }

            notificationManager.notify(notification.getNotificationID(), builder.build());
            DB.deleteNotification(notification.getNotificationID());
        }
    }
}
