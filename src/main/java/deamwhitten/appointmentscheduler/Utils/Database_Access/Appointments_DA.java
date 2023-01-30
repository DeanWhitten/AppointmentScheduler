package deamwhitten.appointmentscheduler.Utils.Database_Access;

import deamwhitten.appointmentscheduler.Model.Appointment;
import deamwhitten.appointmentscheduler.Utils.Collections.Contacts_Collections;
import deamwhitten.appointmentscheduler.Utils.Collections.Customers_Collections;
import deamwhitten.appointmentscheduler.Utils.CurrentSession_properties;
import deamwhitten.appointmentscheduler.Utils.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;


/**
 * Appointments data access.
 */
public abstract class Appointments_DA {

	/**
	 * Gets all appointments data.
	 *
	 * @return all appointments data in a list
	 */
	public static ObservableList<Appointment> getAllAppointmentsData() {

        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        try {
            String sql = "SELECT a.Appointment_ID, a.Title, a.Description, a.location, a.Type, a.Start, a.End, a.Customer_ID, a.User_ID, a.Contact_ID " +
                    "FROM appointments AS a " +
                    "INNER JOIN customers AS c ON a.Customer_ID = c.Customer_ID " +
                    "INNER JOIN users AS u ON a.User_ID = u.User_ID " +
                    "INNER JOIN contacts AS con ON a.Contact_ID = con.Contact_ID";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentID = rs.getInt("Appointment_ID");
                String appointmentTitle = rs.getString("Title");
                String appointmentDescription = rs.getString("Description");
                String appointmentLocation = rs.getString("Location");
                String appointmentType = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                Appointment appointment = new Appointment(appointmentID, appointmentTitle, appointmentDescription, appointmentLocation, appointmentType, start, end, customerID, userID, contactID);
                allAppointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allAppointments;
    }

	/**
	 * Write new appointment data to db.
	 *
	 * @param customerName the customer name
	 * @param contact      the contact
	 * @param type         the type
	 * @param title        the title
	 * @param description  the description
	 * @param location     the location
	 * @param start        the start
	 * @param end          the end
	 */
	public static void writeNewAppointmentDataToDB(String customerName, String contact,
                                                   String type, String title, String description,
                                                   String location, LocalDateTime start, LocalDateTime end) {
        try {
            String sql = "INSERT INTO appointments " +
                    "(Title, Description, Location, Type, Start, End," +
                    "Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID,Contact_ID) " +
                    "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?) ";

            PreparedStatement ps = JDBC.connection.prepareStatement(sql);

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, Timestamp.valueOf(start));
            ps.setTimestamp(6, Timestamp.valueOf(end));
            ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(8 , CurrentSession_properties.getUserName());
            ps.setTimestamp(9,Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(10,CurrentSession_properties.getUserName());
            ps.setInt(11, Customers_Collections.getCustomerIDByName(customerName));
            ps.setInt(12, CurrentSession_properties.getUserID());
            ps.setInt(13, Contacts_Collections.getContactIdByName(contact));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	/**
	 * Update appointment data to db.
	 *
	 * @param appointmentID the appointment id
	 * @param title         the title
	 * @param description   the description
	 * @param location      the location
	 * @param type          the type
	 * @param start         the start
	 * @param end           the end
	 * @param customerID    the customer id
	 * @param userID        the user id
	 * @param contactID     the contact id
	 */
	public static void updateAppointmentDataToDB(int appointmentID, String title,
                                                 String description, String location, String type,
                                                 LocalDateTime start, LocalDateTime end,
                                                 int customerID, int userID, int contactID) {
        try {
            String sql = "UPDATE appointments " +
                    "SET Title = ?, Description = ? ,  Location = ?, Type = ?, Start = ?, End = ?, " +
                    "Customer_ID = ? , User_ID = ? , Contact_ID = ?, Last_Update = ? , Last_Updated_By = ? " +
                    "WHERE Appointment_ID = ? ";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);

            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, Timestamp.valueOf(start));
            ps.setTimestamp(6, Timestamp.valueOf(end));
            ps.setInt(7, customerID);
            ps.setInt(8, userID);
            ps.setInt(9, contactID);
            ps.setTimestamp(10, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(11, CurrentSession_properties.getUserName());
            ps.setInt(12, appointmentID);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	/**
	 * Delete appointment.
	 *
	 * @param appointmentID the id of the appointment to be deleted
	 */
	public static void deleteAppointment(int appointmentID) {
        try {
            String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, appointmentID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}