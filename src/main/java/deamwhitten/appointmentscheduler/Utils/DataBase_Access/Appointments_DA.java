package deamwhitten.appointmentscheduler.Utils.DataBase_Access;

import deamwhitten.appointmentscheduler.Model.Appointment;
import deamwhitten.appointmentscheduler.Utils.Collections.Contacts_Collections;
import deamwhitten.appointmentscheduler.Utils.Collections.Customers_Collections;
import deamwhitten.appointmentscheduler.Utils.CurrentSession_Handler;
import deamwhitten.appointmentscheduler.Utils.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static deamwhitten.appointmentscheduler.Utils.Time_Handler.stringToCalendar;

public abstract class Appointments_DA {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final ZoneId localZoneID = ZoneId.systemDefault();
    private static final ZoneId utcZoneID = ZoneId.of("UTC");

    //READ- ALL- Appointments
    public static ObservableList<Appointment> getAllAppointmentsData() {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM appointments";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int id = rs.getInt("Appointment_ID");
                String name = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                String start = rs.getString("Start");
                String end = rs.getString("End");
                String createDate = rs.getString("Create_Date");
                String createdBy = rs.getString("Created_By");
                String lastUpdate = rs.getString("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                Calendar createDateCalendar = stringToCalendar(createDate);
                Calendar lastUpdateCalendar = stringToCalendar(lastUpdate);

                //convert utc to local
                LocalDateTime utcStart = LocalDateTime.parse(start, formatter);
                LocalDateTime utcEnd = LocalDateTime.parse(end, formatter);

                //convert utz zone id to local zone id
                ZonedDateTime localZoneStart = utcStart.atZone(utcZoneID).withZoneSameInstant(localZoneID);
                ZonedDateTime localZoneEnd = utcEnd.atZone(utcZoneID).withZoneSameInstant(localZoneID);

                //convert datetime variables to strings for easier manipulation
                String localStart = localZoneStart.format(formatter);
                String localEnd = localZoneEnd.format(formatter);

                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                
                Appointment appResult= new Appointment(id, name, description, location, type, localStart, localEnd, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdatedBy, customerId, userId, contactId);
                allAppointments.add(appResult);
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        return allAppointments;
    }

    public static void writeNewAppointmentDataToDB(int id, String customerName, String contact,
                                                   String type, String title, String description, String location, String start, String end) {
         LocalDateTime startTime = LocalDateTime.parse(start, formatter);
         LocalDateTime endTime = LocalDateTime.parse(end, formatter);

         int customerID = Customers_Collections.getCustomerIDByName(customerName);
         int contactID = Contacts_Collections.getContactIdByName(contact);
         int userID = CurrentSession_Handler.getUserID();
         String createdBy = CurrentSession_Handler.getUserName();
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
         Timestamp sqlStartDT = Timestamp.valueOf(startTime);
         Timestamp sqlEndDT = Timestamp.valueOf(endTime);
        try
        {

            String sql = "insert into appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, " + "Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                    "values ('" + title + "', '" + description + "', '" + location + "', '" + type +
                    "', '" + sqlStartDT + "', '" + sqlEndDT + "', '" + LocalDateTime.now().format(formatter) + "', '" +
                    createdBy + "', '" + LocalDateTime.now().format(formatter) + "', '" + createdBy + "', '" + customerID +"', '" + userID +"', '"+ contactID +"')";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            int rs = ps.executeUpdate();
        }

        catch(SQLException except_sql)
        {
            System.out.println("Error " + except_sql.getMessage());
        }
    }
}