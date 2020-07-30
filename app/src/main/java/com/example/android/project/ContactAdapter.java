package com.example.android.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactAdaptervh> implements Filterable {
    // private RecyleViewCLickListener mRecycleViewListener;
    Context context;
    private List<ContactList> listOfContact;
    private List<ContactList> filteredcontactList;
    SelectedUser selectedUser;

    public ContactAdapter(List<ContactList> listOfContact, SelectedUser selectedUser) {
        //   this.mContext = mContext;
        this.listOfContact = listOfContact;
        this.filteredcontactList = listOfContact;
        this.selectedUser = selectedUser;
        //   this.mRecycleViewListener = listener;
    }

    @NonNull
    @Override
    public ContactAdapter.ContactAdaptervh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ContactAdaptervh(LayoutInflater.from(context).inflate(R.layout.list_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ContactAdaptervh holder, int position) {
        ContactList contactList = listOfContact.get(position);
        holder.name_contact.setText(contactList.getContactName());
        holder.phone_contact.setText(contactList.getPhoneNumber());
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

    @Override
    public Filter getFilter() {
        final Filter filter = new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    filterResults.count = filteredcontactList.size();
                    filterResults.values = filteredcontactList;
                } else {
                    String searchChar = constraint.toString().toLowerCase();
                    List<ContactList> resultData = new ArrayList<>();
                    for (ContactList contactList : filteredcontactList) {
                        if (contactList.getContactName().toLowerCase().contains(constraint))
                            resultData.add(contactList);
                    }
                    filterResults.count = resultData.size();
                    filterResults.values = resultData;
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listOfContact = (List<ContactList>) results.values;
                notifyDataSetChanged();

            }
        };
        return filter;
    }

    public interface SelectedUser {
        void SelectedUser(ContactList contactList);
    }


    public class ContactAdaptervh extends RecyclerView.ViewHolder {
        private TextView name_contact, phone_contact,email_contact;
        CircleImageView image_contact;
      //  RecyleViewCLickListener recyleViewCLickListener;

        public ContactAdaptervh(@NonNull View itemView) {
            super(itemView);
            name_contact = itemView.findViewById(R.id.name_contact);
            phone_contact = itemView.findViewById(R.id.phone_contact);
            image_contact = itemView.findViewById(R.id.image_contact);
            email_contact = itemView.findViewById(R.id.email_detail);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedUser.SelectedUser(listOfContact.get(getAdapterPosition()));
                }
            });

        }

    }
}
     /*   @Override
        public void onClick(View v) {
            recyleViewCLickListener.onClick(getAdapterPosition());

        }

    public interface RecyleViewCLickListener {
        void onClick(int position);
    }*/
  /*  public void filterList(ArrayList<ContactList> filteredList){
        listOfContact = filteredList;
        notifyDataSetChanged();
    }*/


