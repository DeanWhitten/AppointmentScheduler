package deamwhitten.appointmentscheduler.Utils.DataBase_Access;

import deamwhitten.appointmentscheduler.Model.Appointment;
import deamwhitten.appointmentscheduler.Model.Customer;
import deamwhitten.appointmentscheduler.Utils.Collections.Appointments_Collections;
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
 * Customers data access.
 */
public abstract class Customers_DA {

	/**
	 * Gets all customers data.
	 *
	 * @return all customers data as a list
	 */
	public static ObservableList<Customer> getAllCustomersData() {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM customers";

            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int customerID = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String customerAddress = rs.getString("Address");
                String customerPostalCode = rs.getString("Postal_Code");
                String customerPhone = rs.getString("Phone");
                int divisionID = rs.getInt("Division_ID");
                Customer customer = new Customer(customerID, customerName, customerAddress, customerPostalCode, customerPhone, divisionID);
                allCustomers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allCustomers;
    }

	/**
	 * Write new customer data to db.
	 *
	 * @param customerName the customer name
	 * @param address      the address
	 * @param postal       the postal
	 * @param phone        the phone
	 * @param divisionID   the division id
	 */
	public static void writeNewCustomerDataToDB(String customerName, String address,
                                                String postal, String phone, int divisionID) {
        try {
            String sql = "INSERT INTO customers " +
                        "(Customer_Name, Address, Postal_Code, Phone," +
                        "Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) " +
                        "VALUES(?,?,?,?,?,?,?,?,?) ";

            PreparedStatement ps = JDBC.connection.prepareStatement(sql);

            ps.setString(1, customerName);
            ps.setString(2, address);
            ps.setString(3, postal);
            ps.setString(4, phone);
            ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(6, CurrentSession_properties.getUserName());
            ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(8 , CurrentSession_properties.getUserName());
            ps.setInt(9, divisionID);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	/**
	 * Update customer data to db.
	 *
	 * @param customerID the customer id
	 * @param name       the name
	 * @param address    the address
	 * @param postal     the postal
	 * @param phone      the phone
	 * @param divisionID the division id
	 */
	public static void updateCustomerDataToDB(int customerID, String name, String address,
                                              String postal, String phone, int divisionID) {
        try {
            String sql = "UPDATE customers " +
                    "SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, " +
                    "Last_Update = ?, Last_Updated_By = ?, Division_ID = ?" +
                    " WHERE Customer_ID = ? ";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postal);
            ps.setString(4, phone);
            ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(6, CurrentSession_properties.getUserName());
            ps.setInt(7, divisionID);
            ps.setInt(8, customerID);
            
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	/**
	 * Delete customer.
	 *
	 * @param customerID the id of the customer to be deleted
	 */
	public static void deleteCustomer(int customerID) {
        try {
            for(Appointment app : Appointments_Collections.getAllAppointments()){
                if(app.getCustomerId() == customerID){
                    Appointments_DA.deleteAppointment(app.getId());
                }
            }

            String sql = "DELETE FROM customers WHERE Customer_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, customerID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}