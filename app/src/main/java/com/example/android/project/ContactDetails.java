package com.example.android.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.Inet4Address;

public class ContactDetails extends AppCompatActivity {
    private String nameS,emailS,numberS;
    String picS;
    ImageView call,chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
        TextView nameTxt = (TextView) findViewById(R.id.nameTxt);
        TextView phontxt = (TextView) findViewById(R.id.numberTxt);
    //    TextView emaitxt = (TextView) findViewById(R.id.emailtxt);
        ImageView profilePic = (ImageView) findViewById(R.id.profile_pic);
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            nameS  = extras.getString("contact_name");
           numberS = extras.getString("contact_number");
       //    emailS  = extras.getString("contact_email");
            picS = extras.getString("contact_image");
        }
        nameTxt.setText(nameS);
        phontxt.setText(numberS);
     //   emaitxt.setText(emailS);
     //   profilePic.setImageResource(Integer.parseInt(picS));
        call = findViewById(R.id.call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent call_intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", numberS, null));
                startActivity(call_intent);
            }
        });
        chat = findViewById(R.id.chat);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("Recipient",nameS);
                smsIntent.putExtra("address",numberS);
                startActivity(smsIntent);
            }
        });

    }
}
