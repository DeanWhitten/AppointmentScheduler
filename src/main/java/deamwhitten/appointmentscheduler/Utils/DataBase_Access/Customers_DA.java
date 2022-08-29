package deamwhitten.appointmentscheduler.Utils.DataBase_Access;
import deamwhitten.appointmentscheduler.Model.Customer;
import deamwhitten.appointmentscheduler.Utils.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Customers_DA {

    //READ- ALL- Customers
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

}