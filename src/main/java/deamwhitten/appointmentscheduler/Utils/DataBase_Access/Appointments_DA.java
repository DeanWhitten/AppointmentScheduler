package deamwhitten.appointmentscheduler.Utils.DataBase_Access;

import deamwhitten.appointmentscheduler.Model.Appointment;
import deamwhitten.appointmentscheduler.Utils.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

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

}