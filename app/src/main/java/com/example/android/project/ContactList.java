package com.example.android.project;
import java.io.Serializable;

public class ContactList implements Serializable {
   private String contactEmail;
    private String contactName;
    private String phone;
    private String photo;

    public ContactList(String contactName,String phone,String contactEmail,String photo) {
        this.contactName = contactName;
        this.phone = phone;
        this.contactEmail = contactEmail;
        this.photo = photo;
    }

    public ContactList(String contactName, String phone,String photo) {
        this.contactName = contactName;
        this.phone = phone;
       this.contactEmail = contactEmail;
        this.photo = photo;
        ///  this.id = id;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
        this.phone = photo;
    }


}
