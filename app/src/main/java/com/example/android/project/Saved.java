package com.example.android.project;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Saved extends AppCompatActivity {
    private String savedName, savedEmail, savedDate, savedNumber;
    TextView sName, sEmail, sDate, sNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);

        sName = (TextView) findViewById(R.id.s_name);
        sEmail = (TextView) findViewById(R.id.s_email);
        sDate = (TextView) findViewById(R.id.s_date);
        sNumber = (TextView) findViewById(R.id.s_number);
        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyUser", Context.MODE_PRIVATE);

        // retrieving data from shared preferences using key -value pairs
        savedName = sp.getString("name", "Name : ");
        savedEmail = sp.getString("email", "Email :");
        savedDate = sp.getString("date", "BirthDate : ");
        savedNumber = sp.getString("contact", "Contact Details : ");
        sName.setText("Name : " + savedName);
        sEmail.setText("Email Address : " + savedEmail);
        sDate.setText("Birth Date : " + savedDate);
        sNumber.setText("Contact Details :  " + savedNumber);

    }
}
