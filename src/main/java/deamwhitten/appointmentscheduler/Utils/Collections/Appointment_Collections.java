package deamwhitten.appointmentscheduler.Utils.Collections;

import deamwhitten.appointmentscheduler.Model.Appointment;
import deamwhitten.appointmentscheduler.DataBase_Access.Appointments_DA;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Appointment_Collections {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    //All Appointments
    public static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    public static ObservableList<Appointment> getAllAppointments() throws Exception {
        allAppointments.addAll(Appointments_DA.getAllAppointmentsData());
        return allAppointments;
    }

    //This month's Appointments
    public static ObservableList<Appointment> monthAppointments = FXCollections.observableArrayList();
    public static ObservableList<Appointment> getThisMonthAppointments(){
        LocalDate now = LocalDate.now();
        LocalDate nowPlus1Week = now.plusWeeks(1);
        
         for(Appointment app : allAppointments){
             LocalDate rowDate = LocalDate.parse(app.getStart(), formatter);
             if(rowDate.isAfter(now.minusDays(1)) && rowDate.isBefore(nowPlus1Week)) {
                 monthAppointments.add(app);
             }
         }
        return monthAppointments;
    }

    //This week's Appointments
    public static ObservableList<Appointment> weekAppointments = FXCollections.observableArrayList();
    public static ObservableList<Appointment> getThisWeekAppointments(){
        LocalDate now = LocalDate.now();
        LocalDate nowPlus1Month = now.plusMonths(1);

        for(Appointment app : allAppointments){
            LocalDate rowDate = LocalDate.parse(app.getStart(), formatter);
            if(rowDate.isAfter(now.minusDays(1)) && rowDate.isBefore(nowPlus1Month)) {
                monthAppointments.add(app);
            }
        }
        return weekAppointments;
    }

    //Appointments by customers
    public static ObservableList<Appointment> selectedCustomerAppointments = FXCollections.observableArrayList();
    public static ObservableList<Appointment> getSelectedCustomerAppointments(int customerID){
        for (Appointment app : allAppointments){
            if (app.getCustomerId() == customerID){
                selectedCustomerAppointments.add(app);
            }
        }
        return selectedCustomerAppointments;
    }


}
