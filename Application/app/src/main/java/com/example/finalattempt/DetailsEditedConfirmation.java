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
    TextView FullNameView,LnameView,AddressView;


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
        String UName=intent.getStringExtra("UName");
        int UserID= intent.getIntExtra("ID",0);
        String NewFname=intent.getStringExtra("NewFname");
        String NewLname=intent.getStringExtra("NewLname");
        String NewEmail=intent.getStringExtra("NewEmail"); // get new details from details edited page

        FullNameView=(TextView)findViewById(R.id.NewFnameView);
        FullNameView.setText(NewFname);
        LnameView=(TextView)findViewById(R.id.NewLnameView);
        LnameView.setText(NewLname);
        AddressView=(TextView)findViewById(R.id.NewEmailView);
        AddressView.setText(NewEmail);
        // set textviews to display updated details

        Home=(Button)findViewById(R.id.homeButton);
        Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsEditedConfirmation.this, MainActivity2.class);
                intent.putExtra("test",UserID);
                intent.putExtra("UName",UName);
                startActivity(intent);
                // back to home button
            }
        });
        ViewDetails=(Button)findViewById(R.id.DetailsEditedBackToViewbutton);
        ViewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsEditedConfirmation.this,ViewDetails.class);
                intent.putExtra("ID",UserID);
                intent.putExtra("UName",UName);
                startActivity(intent);
                // back to view details button
            }
        });

    }
}