package com.example.android.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private String nameStr, emailStr, dateStr, contactStr;
    EditText nameInput, dateInput, emailInput, contactInput;
    Button saveButton, clearButton, userButton, contactsButton, weatherButton;
    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dateInput = (EditText) findViewById(R.id.date_input);
        nameInput = (EditText) findViewById(R.id.name_input);
        emailInput = (EditText) findViewById(R.id.email_input);
        contactInput = (EditText) findViewById(R.id.number_input);

        // shared preferences to save data
        sp = getSharedPreferences("MyUser", Context.MODE_PRIVATE);

        saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameStr = nameInput.getText().toString();
                emailStr = emailInput.getText().toString();
                dateStr = dateInput.getText().toString();
                contactStr = contactInput.getText().toString();

                // checking for empty fields
                if (nameStr.isEmpty() || emailStr.isEmpty() || dateStr.isEmpty() || contactStr.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Incomplete Information", Toast.LENGTH_SHORT).show();
                }
                // checking for valid phone numebr
                if (contactStr.length() > 10) {
                    Toast.makeText(MainActivity.this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("name", nameStr);
                    editor.putString("email", emailStr);
                    editor.putString("date", dateStr);
                    editor.putString("contact", contactStr);
                    editor.apply();
                    Toast.makeText(MainActivity.this, "Your information has been saved", Toast.LENGTH_SHORT).show();
                }
            }
        });

        userButton = (Button) findViewById(R.id.user);
        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent save = new Intent(MainActivity.this, Saved.class);
                startActivity(save);
            }
        });
        contactsButton = (Button) findViewById(R.id.contact);
        contactsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent save = new Intent(MainActivity.this, Contact.class);
                startActivity(save);
            }
        });
        clearButton = (Button) findViewById(R.id.clear_button);

        // clear button to reset all the fields
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameStr = nameInput.getText().toString();
                emailStr = emailInput.getText().toString();
                dateStr = dateInput.getText().toString();
                if (nameStr.isEmpty() && emailStr.isEmpty() && dateStr.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Already Empty", Toast.LENGTH_SHORT).show();
                } else {
                    nameInput.setText("");
                    emailInput.setText("");
                    dateInput.setText("");
                    contactInput.setText("");
                }

            }
        });
        weatherButton = (Button) findViewById(R.id.weather_app);
        weatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent weatherIntent = new Intent(MainActivity.this, Weather.class);
                startActivity(weatherIntent);
            }
        });

    }


}