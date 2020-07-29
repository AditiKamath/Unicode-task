package com.example.android.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;

import dalvik.system.DexClassLoader;

public class Contact extends AppCompatActivity implements ContactAdapter.RecyleViewCLickListener {
    RecyclerView recyclerView;
    ContactAdapter adapter;
    List<ContactList> listOfContact;
    //  private ContactAdapter.RecyclerViewClickListener listener;
    private ContactList contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_contact);

        recyclerView = findViewById(R.id.contact_recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //  recyclerView.addItemDecoration(null);
        listOfContact = new ArrayList<>();
        adapter = new ContactAdapter(this, listOfContact, this);
        recyclerView.setAdapter(adapter);


        //   setOnCLickListener();
        Dexter.withActivity(this).withPermission(Manifest.permission.READ_CONTACTS).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                if (response.getPermissionName().equals(Manifest.permission.READ_CONTACTS)) {
                    getContacts();
                }
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                Toast.makeText(Contact.this, "Permission needs to be granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).check();

    }

    private void getContacts() {
        try {
            Cursor phoneCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, null, null, null);
            assert phoneCursor != null;
            Bitmap bitmap;
            phoneCursor.moveToFirst();
            while (!phoneCursor.isAfterLast()) {
                String id = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
                String name = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String number = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
          /*  String email = null;
            Cursor ce = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{id}, null);
            if (ce != null && ce.moveToFirst()) {
                email = ce.getString(ce.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                ce.close();
            }*/
                String photoUri = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
                phoneCursor.moveToNext();
                contactList = new ContactList(name, number, photoUri);
                listOfContact.add(contactList);
                adapter.notifyDataSetChanged();
            }
            phoneCursor.close();
            phoneCursor = null;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(int position) {
        listOfContact.get(position);
        Intent intent = new Intent(this, ContactDetails.class);
        intent.putExtra("contact_name", listOfContact.get(position).getContactName());
        intent.putExtra("contact_number", listOfContact.get(position).getPhone());
        //    intent.putExtra("contact_email", listOfContact.get(position).getContactEmail());
        intent.putExtra("contact_image", listOfContact.get(position).getPhoto());
        startActivity(intent);
    }


}
