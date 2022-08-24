package deamwhitten.appointmentscheduler.Utils.Collections;

import deamwhitten.appointmentscheduler.Model.Appointment;
import deamwhitten.appointmentscheduler.DataBase_Access.Appointments_DA;

import deamwhitten.appointmentscheduler.Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Appointment_Collections {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    //All Appointments
    public static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    public static ObservableList<Appointment> getAllAppointments() throws Exception {
        allAppointments.clear();
        allAppointments.addAll(Appointments_DA.getAllAppointmentsData());
        return allAppointments;
    }

    //This month's Appointments
    public static ObservableList<Appointment> monthAppointments = FXCollections.observableArrayList();
    public static ObservableList<Appointment> getThisMonthAppointments(){
        monthAppointments.clear();
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
        weekAppointments.clear();
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
    public static ObservableList<Appointment> getSelectedCustomerAppointments(String name) throws Exception {
        selectedCustomerAppointments.clear();
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList(Customer_Collections.getAllCustomers());

        Customer selectedCustomer = null;
        for(Customer customer: allCustomers){
            if(customer.getName().equals(name)){
                selectedCustomer = customer;
            }
        }
        for (Appointment app : allAppointments){
            assert selectedCustomer != null;
            if(selectedCustomer.getId() == app.getCustomerId()){
                   selectedCustomerAppointments.add(app);
              }
        }
        return selectedCustomerAppointments;
    }


}