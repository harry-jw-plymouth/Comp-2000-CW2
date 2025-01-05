package com.example.finalattempt;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Button button1;
    Button ELogInButton;Button ALogInButton;
    TextView Eresult;TextView AResult;
    EditText EPassword; EditText EUName;EditText APassword; EditText AUName;
    String CorrectUserName="Hwatton";String CorrectPassWord="12345";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
        String channelID = "my_channel";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,channelID);
        makeNotification();

        DBHelper DB= new DBHelper(MainActivity.this);
        DB.addAdmin(new AdminAccountDataModel("Hwatton","12345"));
       // DB.addAdmin(new AdminAccountDataModel("IAmAnAdmin","123Password"));
        EmployeeDBHelper EDB= new EmployeeDBHelper(MainActivity.this);
        EDB.UpdateWithNewEmployees(MainActivity.this);
        EDB= new EmployeeDBHelper(MainActivity.this);
        EDB.DeleteRemovedEmployees(MainActivity.this);
        SQLiteDatabase Temp=EDB.getWritableDatabase();
       // EDB.UpgradeRequestsTable(Temp);


        EUName=(EditText) findViewById(R.id.EUserName);
        EPassword=(EditText)findViewById(R.id.EPWord);
        Eresult=(TextView)findViewById(R.id.ELogInResult);


        ELogInButton=(Button)findViewById(R.id.EmployeeLogIn);
        ELogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Eresult.setText(EUName.getText());
                //EUName.setText("");
                if(GetIfLogInSucessful(EUName.getText().toString(),EPassword.getText().toString(),true,Eresult)){
                    Intent intent=new Intent(MainActivity.this,MainActivity2.class);
                    startActivity(intent);
                }
                EUName.setText("");
                EPassword.setText("");

            }
        });

        AUName=(EditText)findViewById(R.id.AUserName);
        APassword=(EditText) findViewById(R.id.EPassword);
        ALogInButton=(Button) findViewById(R.id.AdminLogIn);
        AResult=(TextView) findViewById(R.id.AResultText);

        ALogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeNotification();
            //    if(GetIfLogInSucessful(AUName.getText().toString(),APassword.getText().toString(),false,AResult)){
                   // Intent intent = new Intent(MainActivity.this,adminMainPage.class);
                  //  intent.putExtra("Name", AUName.getText().toString());
                    //startActivity(intent);
              //  }
               // else{
                 //   APassword.setText("");AUName.setText("");
                //}
                makeNotification();
            }
        });


    }
    private void createNotificationChannel() {

    }
    public void makeNotification() {
        Log.d("Status","Making notification");

        String chanelID = "my_channel";

        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                this, chanelID);

        builder.setSmallIcon(R.drawable.standardnotification)
                .setContentTitle("My notification")
                .setContentText("New approval request!")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent intent = new Intent(this, MainActivity2.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("UName", "Nots test");
        intent.putExtra("ID", 0);




        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, intent, PendingIntent.FLAG_MUTABLE);

        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());



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
        notificationManager.notify(0, builder.build());
        Log.d("End", "end of notification");
    }



    public boolean GetIfLogInSucessful(String Username,String password,Boolean IsEmployee,TextView Result){
        if(IsEmployee){
            EmployeeDBHelper EDB = new EmployeeDBHelper(MainActivity.this);
            List<UserAccountDataModel> Users= EDB.getAllEmployees();

            boolean UNameFound= false;
            for(int i=0;i<Users.size();i++){
                Log.d("Status","NAME: " + Users.get(i).getUserName()+ " PASS: " +Users.get(i).getPassWord());
                if(Username.equals(Users.get(i).getUserName())){

                    Result.setText("Username found");
                    UNameFound=true;
                    if(password.equals(Users.get(i).getPassWord())){
                      if(password.equals("123Password")){
                            Log.d("Status" , "new password found");
                            Intent intent= new Intent(MainActivity.this,ChangeNewEmployeePassWord.class);
                            intent.putExtra("ID",Users.get(i).getUserId());
                            intent.putExtra("UName",Users.get(i).getUserName());
                            startActivity(intent);
                            Log.d("Status" , "new password found");
                            return false;
                        }
                        else{
                            Log.d("Status" , "new password not found");
                            Intent intent= new Intent(MainActivity.this, MainActivity2.class);
                            intent.putExtra("UName",Users.get(i).getUserName());
                            intent.putExtra("test",Users.get(i).getUserId());
                           // String IDToPut=Integer.toString(Users.get(i).getUserId());
                         //   intent.putExtra("ID",IDToPut);
                            startActivity(intent);
                           // Bundle extras = new Bundle();
                          //  String UsernameTemp=Users.get(i).getUserName();

                         //   extras.putString("ID",IDToPut);
                           // extras.putString("UName",UsernameTemp);
                            //intent.putExtras(extras);
                          //
                           // intent.putExtra("ID",IDToPut);
                            //Log.d("ID",IDToPut);

                        }
                        return false;
                    }
                    else{
                        Result.setText("Incorrect details entered");
                        return false;
                    }
                }
            }
            if(!UNameFound){
                Result.setText("Username not found");
                return false;
            }
            return false;
        }
        else {

            DBHelper DB= new DBHelper(MainActivity.this);
            List<AdminAccountDataModel> Admins=DB.getAllAdmins();

            boolean UNameFound=false;
            for(int i=0;i<Admins.size();i++){
                Log.d("Current", Admins.get(i).getUserName());
                if(Username.equals(Admins.get(i).getUserName())){
                    Result.setText("username correct");
                    UNameFound=true;
                    if(password.equals(Admins.get(i).getPassword()))
                    {
                        Intent intent= new Intent(MainActivity.this,adminMainPage.class);
                        startActivity(intent);
                        return  true;
                    }
                    else{
                        Result.setText("Incorrect details entered");
                        return false;
                    }
                }
            }
            if(!UNameFound){
                Result.setText("Username not found");
                return false;
            }
            return false;
        }
    }
}