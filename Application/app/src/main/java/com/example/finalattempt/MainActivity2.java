package com.example.finalattempt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    TextView textView,Welcome;
    Button button2;Button EditDetailsButton;
    Intent intent;
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

        intent=getIntent();
        String UName=intent.getStringExtra("UName");
        int UserID= Integer.parseInt(intent.getStringExtra("ID"));
        Log.d("ID in main activity2 ",Integer.toString(UserID));
        //Log.d("UName",UName);

        textView=findViewById(R.id.textViewWelcome);
        String text=getIntent().getStringExtra("key");
        ViewDetailsButton=(Button)findViewById(R.id.ViewDetailsButton);
        ViewDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity2.this,ViewDetails.class);
                intent.putExtra("UName",UName);
                intent.putExtra("ID",UserID);
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
                intent.putExtra("ID",UserID);
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
                intent.putExtra("ID",UserID);
                startActivity(intent);
            }
        });

        NotificSettingsButton=(Button)findViewById(R.id.NotifSettingsbutton);
        NotificSettingsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity2.this,Notificationsettings.class);
                intent.putExtra("UName",UName);
                intent.putExtra("ID",UserID);
                startActivity(intent);
            }
        });


        if(text!=null)
        {
            textView.setText(text);
        }



    }
}