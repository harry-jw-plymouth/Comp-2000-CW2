package com.example.finalattempt;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
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
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
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
        // channel creation for notifications

        DBHelper DB= new DBHelper(MainActivity.this);
        // DB.UpgradeNotificationsTable(DB.getWritableDatabase());
        //   DB.addAdmin(new AdminAccountDataModel("Hwatton","12345"));
       // DB.addAdmin(new AdminAccountDataModel("IAmAnAdmin","123Password"));
        EmployeeDBHelper EDB= new EmployeeDBHelper(MainActivity.this);
      //  EDB.UpgradeNotificationsTable(EDB.getWritableDatabase());
      //  EDB.UpdateWithNewEmployees(MainActivity.this);
        EDB= new EmployeeDBHelper(MainActivity.this);
      //  EDB.DeleteRemovedEmployees(MainActivity.this);
        SQLiteDatabase Temp=EDB.getWritableDatabase();
       // EDB.UpgradeRequestsTable(Temp);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("Thread","Starting thread");
                EmployeeDBHelper EDB= new EmployeeDBHelper(MainActivity.this);
                EDB.UpdateWithNewEmployees(MainActivity.this);
                EDB.DeleteRemovedEmployees(MainActivity.this);
            }
        });//thread for updating employee db to match API


        EUName=(EditText) findViewById(R.id.EUserName);//text input for username
        EPassword=(EditText)findViewById(R.id.EPWord);//text input for password
        Eresult=(TextView)findViewById(R.id.ELogInResult);//text view for log in result


        ELogInButton=(Button)findViewById(R.id.EmployeeLogIn);
        ELogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Eresult.setText(EUName.getText());
                if(GetIfLogInSucessful(EUName.getText().toString(),EPassword.getText().toString(),true,Eresult)){
                    Intent intent=new Intent(MainActivity.this,MainActivity2.class);
                    startActivity(intent);
                }
                EUName.setText("");
                EPassword.setText(""); // clear inputs if log in fails

            }
        });

        AUName=(EditText)findViewById(R.id.AUserName);
        APassword=(EditText) findViewById(R.id.EPassword);
        // text inputs for employee log in
        ALogInButton=(Button) findViewById(R.id.AdminLogIn);
        AResult=(TextView) findViewById(R.id.AResultText);

        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }
        // set up for notifications

        ALogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(GetIfLogInSucessful(AUName.getText().toString(),APassword.getText().toString(),false,AResult)){
                    Intent intent = new Intent(MainActivity.this,adminMainPage.class);
                    intent.putExtra("Name", AUName.getText().toString());
                    startActivity(intent);
                    // on successful sign in load admin main page passing name of signed in admin
                }
                else{
                    APassword.setText("");AUName.setText("");
                    // clear inputs on failed log in
                }
            }
        });
    }
    public boolean GetIfLogInSucessful(String Username,String password,Boolean IsEmployee,TextView Result){
        if(IsEmployee){ // employee log in
            EmployeeDBHelper EDB = new EmployeeDBHelper(MainActivity.this);
            List<UserAccountDataModel> Users= EDB.getAllEmployees(); // get all employees from database

            boolean UNameFound= false;
            for(int i=0;i<Users.size();i++){
                Log.d("Status","NAME: " + Users.get(i).getUserName()+ " PASS: " +Users.get(i).getPassWord());
                if(Username.equals(Users.get(i).getUserName())){
                    Result.setText("Username found"); // set log in result on page
                    UNameFound=true;
                    if(password.equals(Users.get(i).getPassWord())){
                      if(password.equals("123Password")){// pass word is default password for new accounts
                            Log.d("Status" , "new password found");
                            Intent intent= new Intent(MainActivity.this,ChangeNewEmployeePassWord.class);
                            intent.putExtra("ID",Users.get(i).getUserId());
                            intent.putExtra("UName",Users.get(i).getUserName());
                            startActivity(intent);
                            Log.d("Status" , "new password found");
                            //load set pass word page for employee with new account
                            return false;
                        }
                        else{
                            Log.d("Status" , "new password not found");
                            Intent intent= new Intent(MainActivity.this, MainActivity2.class);
                            intent.putExtra("UName",Users.get(i).getUserName());
                            intent.putExtra("test",Users.get(i).getUserId());
                            startActivity(intent);
                            // om successful sign in load employee main page passing the user name and id as extras
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
        else {// admin log in
            DBHelper DB= new DBHelper(MainActivity.this);
            List<AdminAccountDataModel> Admins=DB.getAllAdmins(); // get all admin account details from database

            boolean UNameFound=false;
            for(int i=0;i<Admins.size();i++){
                Log.d("Current", Admins.get(i).getUserName());
                if(Username.equals(Admins.get(i).getUserName())){
                    Result.setText("username correct");
                    UNameFound=true;
                    if(password.equals(Admins.get(i).getPassword()))
                    {
                        Intent intent= new Intent(MainActivity.this,adminMainPage.class);
                        startActivity(intent); //load admin main page for successful sign in
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