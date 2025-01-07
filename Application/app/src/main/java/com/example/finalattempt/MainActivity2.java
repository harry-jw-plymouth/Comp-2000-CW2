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

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    TextView textView,Welcome;
    Button button2;Button EditDetailsButton;
    Button ViewDetailsButton;Button ViewHoliday;Button NotificSettingsButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        };

        Intent intent=getIntent();
        String UName=intent.getStringExtra("UName");
        int UserId=intent.getIntExtra("test",0);
        Log.d("Test","Test: "+UserId);
        makeNotification(UserId,UName);
      //  String UName="";

        //String UserID= intent.getStringExtra("ID");
        //Log.d("ID in main activity2 ",UName);
        //Log.d("UName",UName);
        //String UserID="";

        textView=findViewById(R.id.textViewWelcome);
        String text=getIntent().getStringExtra("key");
        ViewDetailsButton=(Button)findViewById(R.id.ViewDetailsButton);
        ViewDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity2.this,ViewDetails.class);
                intent.putExtra("UName",UName);
                intent.putExtra("ID",UserId);
                startActivity(intent);
            }
        });

        Welcome=findViewById(R.id.textViewWelcome);
        Welcome.setText("Welcome "+ UName);

        ViewHoliday=(Button)findViewById(R.id.ViewHolidayButton);
        ViewHoliday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity2.this,HolidayMainPage.class);
                intent.putExtra("UName",UName);
                intent.putExtra("ID",UserId);
                startActivity(intent);
            }
        });

        button2=(Button)findViewById(R.id.button_2id);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MainActivity2.this, MainActivity.class);
                //intent.putExtra("key",value);
                startActivity(intent);
            }
        });
        EditDetailsButton=(Button)findViewById(R.id.EditDetailsButton);
        EditDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity2.this,EditDetails.class);
                intent.putExtra("UName",UName);
                intent.putExtra("ID",UserId);
                startActivity(intent);
            }
        });

        NotificSettingsButton=(Button)findViewById(R.id.NotifSettingsbutton);
        NotificSettingsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity2.this,Notificationsettings.class);
                intent.putExtra("UName",UName);
                intent.putExtra("ID",UserId);
                startActivity(intent);
            }
        });
        if(text!=null)
        {
            textView.setText(text);
        }
    }
    public void makeNotification(int ID,String UName) {
        String chanelID = "my_channel";
        EmployeeDBHelper EDB= new EmployeeDBHelper(MainActivity2.this);
        List<NotificationDataModel> UsersNotifications=EDB.getAllNotificationsForEmployee(ID);
        for(NotificationDataModel notification:UsersNotifications){
            NotificationCompat.Builder builder = new NotificationCompat.Builder(
                    this, chanelID);
            String Title="";
            String Content="";
            Intent intent=new Intent();
            if(notification.getType().equals("DetailsEditedByAdmin")){
                Title="Details updated";
                intent=new Intent(MainActivity2.this,ViewDetails.class);
                intent.putExtra("ID",ID);
                intent.putExtra("UName", UName);
                Content="An admin has updated your details";
            } else if (notification.getType().equals("HolidayRequestUpdate")) {
                Title="Holiday request response";
                intent=new Intent(MainActivity2.this,PreviousHolidays.class);
                intent.putExtra("ID",ID);
                intent.putExtra("UName", UName);
                Content="An admin has responded too and updated your holiday request";
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
            EDB.deleteNotification(notification.getNotificationID());
        }




    }
}