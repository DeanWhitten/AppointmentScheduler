package deamwhitten.appointmentscheduler.Utils.Collections;

import deamwhitten.appointmentscheduler.Utils.DataBase_Access.Contact_DA;
import deamwhitten.appointmentscheduler.Model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Contacts_Collections {
    //All contacts
    public static ObservableList<Contact> allContacts = FXCollections.observableArrayList();
    public static ObservableList<Contact> getAllContacts(){
        allContacts.clear();
        allContacts.addAll(Contact_DA.getAllContactsData());
        return allContacts;
    }

    //All contact names
    public static ObservableList<String> allContactNames = FXCollections.observableArrayList();
    public static ObservableList<String> getAllContactNames(){
        allContactNames.clear();
        getAllContacts();
        for (Contact contact: allContacts){
            allContactNames.add(contact.getName());
        }
        return allContactNames;
    }

    public static int getContactIdByName(String contact) {
        getAllContacts();
        int id = 0;
        for(Contact c : allContacts){
            if(c.getName().equals(contact)){
                id = c.getId();
            }
        }
        return id;
    }
}