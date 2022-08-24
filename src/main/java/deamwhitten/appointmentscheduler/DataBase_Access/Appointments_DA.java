package deamwhitten.appointmentscheduler.DataBase_Access;

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
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static ZoneId localZoneID = ZoneId.systemDefault();
    private static ZoneId utcZoneID = ZoneId.of("UTC");


    //READ- ALL- Appointments
    public static ObservableList<Appointment> getAllAppointmentsData() throws SQLException, Exception{
        //DBConnection.openConnection();
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM appointments";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){

                int appId=rs.getInt("Appointment_ID");
                String appName=rs.getString("Title");
                String appDesc=rs.getString("Description");
                String appLoc=rs.getString("Location");
                String appType=rs.getString("Type");
                String appStart=rs.getString("Start");
                String appEnd=rs.getString("End");
                String createDate=rs.getString("Create_Date");
                String createdBy=rs.getString("Created_By");
                String lastUpdate=rs.getString("Last_Update");
                String lastUpdateby=rs.getString("Last_Updated_By");
                Calendar createDateCalendar=stringToCalendar(createDate);
                Calendar lastUpdateCalendar=stringToCalendar(lastUpdate);

                //convert utc to local
                LocalDateTime utcStart = LocalDateTime.parse(appStart, formatter);
                LocalDateTime utcEnd = LocalDateTime.parse(appEnd, formatter);

                //convert utz zoneid to local zoneid
                ZonedDateTime localZoneStart = utcStart.atZone(utcZoneID).withZoneSameInstant(localZoneID);
                ZonedDateTime localZoneEnd = utcEnd.atZone(utcZoneID).withZoneSameInstant(localZoneID);

                //convert datetime variables to strings for easier manipulation
                String localStart = localZoneStart.format(formatter);
                String localEnd = localZoneEnd.format(formatter);

                int custId=rs.getInt("Customer_ID");
                int userId=rs.getInt("User_ID");
                int contactId=rs.getInt("Contact_ID");
                
                Appointment appResult= new Appointment(appId, appName, appDesc, appLoc, appType, localStart, localEnd, createDateCalendar, createdBy, lastUpdateCalendar, lastUpdateby, custId, userId, contactId);
                allAppointments.add(appResult);
            }
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }

        return allAppointments;
    }




}