package com.example.android.project;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

//import android.widget.Toolbar;

public class Contact extends AppCompatActivity implements ContactAdapter.SelectedUser {
    RecyclerView recyclerView;
    ContactAdapter adapter;
    List<ContactList> listOfContact;
    Toolbar toolbar1;
    //  private ContactList contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        toolbar1 = findViewById(R.id.tool_bar);
        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        setSupportActionBar(toolbar1);
        this.getSupportActionBar().setTitle("");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        listOfContact = new ArrayList<>();
        adapter = new ContactAdapter(listOfContact, this);
        recyclerView.setAdapter(adapter);


        //   get permission for reading contacts
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
          /*  HashSet<String> normalizedNumbersAlreadyFound = new HashSet<>();
            int indexOfNormalizedNumber = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER);
            int indexOfDisplayName = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            int indexOfDisplayNumber = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);*/
            phoneCursor.moveToFirst();
            while (!phoneCursor.isAfterLast()) {
                    String id = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
                   String name = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String number = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String email = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
               /* Cursor ce = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{id}, null);
                if (ce != null && ce.moveToFirst()) {
                    email = ce.getString(ce.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                    ce.close();*/
                    String photoUri = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI));
                    phoneCursor.moveToNext();
                    ContactList contactList = new ContactList(name, number, photoUri);
                //    ContactList emailList = new ContactList(name,number,email,photoUri);
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
    public void SelectedUser(ContactList contactList) {
        startActivity(new Intent(Contact.this, ContactDetails.class).putExtra("data", contactList));

    }

    /*   @Override
       public void onClick(int position) {
           listOfContact.get(position);
           Intent intent = new Intent(this, ContactDetails.class);
           intent.putExtra("contact_name", listOfContact.get(position).getContactName());
           intent.putExtra("contact_number", listOfContact.get(position).getPhoneNumber());
           //    intent.putExtra("contact_email", listOfContact.get(position).getContactEmail());
           intent.putExtra("contact_image", listOfContact.get(position).getPhoto());
           startActivity(intent);
       }*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_view);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        //   SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.search_view) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}