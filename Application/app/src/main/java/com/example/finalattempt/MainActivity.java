package com.example.finalattempt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import java.io.Console;
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

            }
        });

        AUName=(EditText)findViewById(R.id.AUserName);
        APassword=(EditText) findViewById(R.id.EPassword);
        ALogInButton=(Button) findViewById(R.id.AdminLogIn);
        AResult=(TextView) findViewById(R.id.AResultText);

        ALogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(GetIfLogInSucessful(AUName.getText().toString(),APassword.getText().toString(),false,AResult)){
                    Intent intent = new Intent(MainActivity.this,adminMainPage.class);
                    startActivity(intent);
                }
                else{
                    APassword.setText("");AUName.setText("");
                }
            }
        });


    }
    public boolean GetIfLogInSucessful(String Username,String password,Boolean IsEmployee,TextView Result){
        if(IsEmployee){
            if(Username.equals(CorrectUserName)){
                Result.setText("username correct");
                if(password.equals(CorrectPassWord))
                {
                    return  true;
                }
                else{
                    Result.setText("Incorrect details entered");
                    return false;
                }
            }
            else{
                Result.setText("Username not found");
                return false;
            }
        }
        else {
            if(Username.equals(CorrectUserName)){
                Result.setText("username correct");
                if(password.equals(CorrectPassWord))
                {
                    return  true;
                }
                else{
                    Result.setText("Incorrect details entered");
                    return false;
                }
            }
            else{
                Result.setText("Username not found");
                return false;
            }
        }
    }
}