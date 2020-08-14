package com.example.android.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.Inet4Address;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactDetails extends AppCompatActivity {
   TextView user_name,user_number,user_email;
    ImageView call;
    CircleImageView imageView;
    ImageView chat;
    Uri photo;
    String savedName,numberS,emailS,photoS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
        user_name = (TextView) findViewById(R.id.name_detail);
        user_number = (TextView) findViewById(R.id.number_detail);
        imageView = (CircleImageView)findViewById(R.id.profile_pic);
        Intent intent = getIntent();

        if(intent.getExtras()!=null){
            ContactList contactList = (ContactList) intent.getSerializableExtra("data");
            savedName = contactList.getContactName();
            user_name.setText(contactList.getContactName());
            numberS = contactList.getPhoneNumber();
            user_number.setText(contactList.getPhoneNumber());
            emailS= contactList.getContactEmail();
            photoS = contactList.getPhoto();
            if(photoS!=null) {
                photo = Uri.parse(photoS);
                if (photo != null)
                    imageView.setImageURI(photo);
            }

        }


        call = findViewById(R.id.call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent call_intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", numberS, null));
                startActivity(call_intent);
            }
        });
        // intent for message
        chat = findViewById(R.id.chat);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("Recipient",savedName);
                smsIntent.putExtra("address",numberS);
                startActivity(smsIntent);
            }
        });

    }
}
