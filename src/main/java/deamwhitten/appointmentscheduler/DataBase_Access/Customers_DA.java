package deamwhitten.appointmentscheduler.DataBase_Access;
import deamwhitten.appointmentscheduler.Model.Customer;
import deamwhitten.appointmentscheduler.Utils.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;

import static deamwhitten.appointmentscheduler.Utils.Time_Handler.stringToCalendar;

public class Customers_DA {
    //READ- ALL- Customers
    public static ObservableList<Customer> getAllCustomersData() {
        //DBConnection.openConnection();
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM customers";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                int id = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                String createDate = rs.getString("Create_Date");
                String createdBy = rs.getString("Created_By");
                String lastUpdate = rs.getString("Last_Update");
                String lastUpdateby = rs.getString("Last_Updated_By");
                int divisionId = rs.getInt("Division_ID");

                Calendar createDateCalendar = stringToCalendar(createDate);
                Calendar lastUpdateCalendar = stringToCalendar(lastUpdate);


                Customer customerResult = new Customer(id, name, address, postalCode, phone,
                        createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby, divisionId);
                allCustomers.add(customerResult);
            }
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }

        return allCustomers;
    }
}