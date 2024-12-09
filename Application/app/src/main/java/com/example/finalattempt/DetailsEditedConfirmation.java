package com.example.finalattempt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetailsEditedConfirmation extends AppCompatActivity {
    Button Home;
    Button ViewDetails;
    //Bundle bundle=getIntent().getExtras();
   // String NewFname=getIntent().getStringExtra("NewFname");
    TextView FullNameView,GenderView,LnameView,AddressView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_details_edited_confirmation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent=getIntent();
        String NewFname=intent.getStringExtra("NewFname");
        String NewLname=intent.getStringExtra("NewLname");
        String NewEmail=intent.getStringExtra("NewEmail");

        FullNameView=(TextView)findViewById(R.id.FnameView);
        String FullName=NewFname+" "+NewLname;
        FullNameView.setText(FullName);
//GenderView=(TextView)findViewById(R.id.GenderView);
        //GenderView.setText(intent.getStringExtra("NewGender"));
        LnameView=(TextView)findViewById(R.id.LnameView);
        LnameView.setText(intent.getStringExtra("NewLnam2"));
        AddressView=(TextView)findViewById(R.id.EmailAddressView);
        AddressView.setText(intent.getStringExtra("NewAddress"));

        Home=(Button)findViewById(R.id.homeButton);
        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsEditedConfirmation.this, MainActivity2.class);
                startActivity(intent);
            }
        });
        ViewDetails=(Button)findViewById(R.id.DetailsEditedBackToViewbutton);
        ViewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsEditedConfirmation.this,ViewDetails.class);
                startActivity(intent);
            }
        });

    }
}