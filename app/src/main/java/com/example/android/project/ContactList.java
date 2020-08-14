package com.example.android.project;
import java.io.Serializable;

public class ContactList implements Serializable {
   private String contactEmail;
    private String contactName;
    private String phoneNumber;
    private String photo;

    public ContactList(String contactName, String phone,String photo) {
        this.contactName = contactName;
        this.phoneNumber = phone;
        this.photo = photo;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phone) {
        this.phoneNumber = phone;
    }
    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }


}
