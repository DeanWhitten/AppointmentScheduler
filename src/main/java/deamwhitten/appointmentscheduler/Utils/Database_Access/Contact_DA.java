package deamwhitten.appointmentscheduler.Utils.Database_Access;

import deamwhitten.appointmentscheduler.Model.Contact;
import deamwhitten.appointmentscheduler.Utils.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Contact data access.
 */
public abstract class Contact_DA {

	/**
	 * Gets all contacts data.
	 *
	 * @return all contacts data in a list
	 */
	public static ObservableList<Contact> getAllContactsData() {
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM contacts";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Contact_ID");
                String name = rs.getString("Contact_Name");
                String email = rs.getString("Email");
                Contact contactResult = new Contact(id, name, email);
                allContacts.add(contactResult);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allContacts;
    }

}