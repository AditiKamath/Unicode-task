package com.example.android.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
   private RecyleViewCLickListener mRecycleViewListener;
    Context mContext;
    List<ContactList> listOfContact;

    public ContactAdapter(Context mContext, List<ContactList> listOfContact,RecyleViewCLickListener listener) {
        this.mContext = mContext;
        this.listOfContact = listOfContact;
        this.mRecycleViewListener = listener;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        return new ContactViewHolder(view,mRecycleViewListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        ContactList contactList = listOfContact.get(position);
        holder.name_contact.setText(contactList.getContactName());
        holder.phone_contact.setText(contactList.getPhone());
        if (contactList.getPhoto() != null) {
            Picasso.get().load(contactList.getPhoto()).into(holder.image_contact);
        } else {
            holder.image_contact.setImageResource(R.drawable.contact);
        }

    }

    @Override
    public int getItemCount() {
        return listOfContact.size();
    }


    public class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
       private TextView name_contact, phone_contact;
        CircleImageView image_contact;
        RecyleViewCLickListener recyleViewCLickListener;

        public ContactViewHolder(@NonNull View itemView,RecyleViewCLickListener recyleViewCLickListener) {
            super(itemView);
            name_contact = itemView.findViewById(R.id.name_contact);
            phone_contact = itemView.findViewById(R.id.phone_contact);
            image_contact = itemView.findViewById(R.id.image_contact);
       //     email_contact = itemView.findViewById(R.id.em)
            this.recyleViewCLickListener = recyleViewCLickListener;
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            recyleViewCLickListener.onClick(getAdapterPosition());

        }
    }
    public interface RecyleViewCLickListener{
        void onClick(int position);
    }
  /*  public void filterList(ArrayList<ContactList> filteredList){
        listOfContact = filteredList;
        notifyDataSetChanged();
    }*/



}
