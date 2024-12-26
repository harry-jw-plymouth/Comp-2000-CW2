package com.example.finalattempt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ChangeNewEmployeePassWord extends AppCompatActivity {
    EditText NewPass,NewPassConfirm;
    TextView Intro;
    Button Submit;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_new_employee_pass_word);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        intent=getIntent();
        int id=intent.getIntExtra("ID",0);
        String UName=intent.getStringExtra("UName");
        Intro=findViewById(R.id.textViewIntro);
        Intro.setText("Welcome to the company "+UName+ ". Please create a password below");

        NewPass=findViewById(R.id.NewPass);
        NewPassConfirm=findViewById(R.id.newPassConfirm);
        Submit=findViewById(R.id.SubmitButton);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(GetIfInputsValid(NewPass.getText().toString(),NewPassConfirm.getText().toString())){
                    intent=new Intent(ChangeNewEmployeePassWord.this,MainActivity2.class);
                    intent.putExtra("ID",id);
                    intent.putExtra("UName",UName);
                    startActivity(intent);

                }
                else{

                }


            }
        });



    }
    public boolean GetIfInputsValid(String Pass,String PassConfirm){
        if(!NewPass.getText().toString().equals(NewPassConfirm.toString())){
            return false;

        }
        if(NewPass.getText().toString().equals("123Password")){
            return false;

        }
        return true;
    }
}