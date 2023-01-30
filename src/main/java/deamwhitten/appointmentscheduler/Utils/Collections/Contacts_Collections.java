package deamwhitten.appointmentscheduler.Utils.Collections;

import deamwhitten.appointmentscheduler.Utils.Database_Access.Contact_DA;
import deamwhitten.appointmentscheduler.Model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The type Contacts collections.
 */
public abstract class Contacts_Collections {

	/**
	 * Get all contacts observable list.
	 *
	 * @return the observable list
	 */
	public static ObservableList<Contact> getAllContacts(){
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();
        allContacts.clear();
        allContacts.addAll(Contact_DA.getAllContactsData());
        return allContacts;
    }

	/**
	 * Get all contact names observable list.
	 *
	 * @return the observable list
	 */
	public static ObservableList<String> getAllContactNames(){
        ObservableList<String> allContactNames = FXCollections.observableArrayList();
        allContactNames.clear();
        getAllContacts();
        for (Contact contact: getAllContacts()){
            allContactNames.add(contact.getName());
        }
        return allContactNames;
    }

	/**
	 * Gets contact id by name.
	 *
	 * @param contact the contact
	 * @return the contact id by name
	 */
	public static int getContactIdByName(String contact) {
        getAllContacts();
        int id = 0;
        for(Contact c : getAllContacts()){
            if(c.getName().equals(contact)){
                id = c.getId();
            }
        }
        return id;
    }

    /**
     *
     * NEWLY ADDED JAN 2023
     *
     * **/

    public static ObservableList<Contact> getContactIDListByName(String text) {
        getAllContacts().clear();
        ObservableList<Contact> allContactsOfName = FXCollections.observableArrayList();

        for (Contact c: getAllContacts()){
            if(c.getName().toLowerCase().contains(text.toLowerCase())){
                allContactsOfName.add(c);
            }
        }
        return allContactsOfName;
    }
}