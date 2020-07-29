package com.example.android.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class Saved extends AppCompatActivity {
    private String nameS,emailS,dateS,contact;
    TextView sName,sEmail,sDate,sNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);

        sName = (TextView) findViewById(R.id.s_name);
        sEmail = (TextView) findViewById(R.id.s_email);
        sDate = (TextView) findViewById(R.id.s_date);
        sNumber = (TextView) findViewById(R.id.s_number);
        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyUser", Context.MODE_PRIVATE);
        nameS = sp.getString("name","");
        emailS = sp.getString("email","");
        dateS = sp.getString("date","");
        contact = sp.getString("contact","");
        sName.setText(nameS);
        sEmail.setText(emailS);
        sDate.setText(dateS);
        sNumber.setText(contact);


    }
}
