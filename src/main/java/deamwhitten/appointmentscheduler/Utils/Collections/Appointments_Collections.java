package deamwhitten.appointmentscheduler.Utils.Collections;

import deamwhitten.appointmentscheduler.Model.Appointment;
import deamwhitten.appointmentscheduler.Model.Customer;
import deamwhitten.appointmentscheduler.Utils.DataBase_Access.Appointments_DA;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;


public abstract class Appointments_Collections {

    //All Appointments
    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        allAppointments.clear();
        allAppointments.addAll(Appointments_DA.getAllAppointmentsData());
        return allAppointments;
    }

    //This month's Appointments
    public static ObservableList<Appointment> getThisMonthAppointments() {
        ObservableList<Appointment> monthAppointments = FXCollections.observableArrayList();
        monthAppointments.clear();
        LocalDate now = LocalDate.now();
        LocalDate nowPlus1Week = now.plusWeeks(1);

        for (Appointment app : getAllAppointments()) {
            LocalDate rowDate = app.getStart().toLocalDate();
            if (rowDate.isAfter(now.minusDays(1)) && rowDate.isBefore(nowPlus1Week)) {
                monthAppointments.add(app);
            }
        }
        return monthAppointments;
    }

    //This week's Appointments
    public static ObservableList<Appointment> getThisWeekAppointments() {
        ObservableList<Appointment> weekAppointments = FXCollections.observableArrayList();
        weekAppointments.clear();
        LocalDate now = LocalDate.now();
        LocalDate nowPlus1Week = now.plusWeeks(1);

        for (Appointment app : getAllAppointments()) {
            LocalDate rowDate = app.getStart().toLocalDate();
            if (rowDate.isAfter(now.minusDays(1)) && rowDate.isBefore(nowPlus1Week)) {
                weekAppointments.add(app);
            }
        }
        return weekAppointments;
    }

    //Appointments by customers

    public static ObservableList<Appointment> getSelectedCustomerAppointments(String name) {
        ObservableList<Appointment> selectedCustomerAppointments = FXCollections.observableArrayList();
        selectedCustomerAppointments.clear();
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList(Customers_Collections.getAllCustomers());

        Customer selectedCustomer = null;
        for (Customer customer : allCustomers) {
            if (customer.getName().equals(name)) {
                selectedCustomer = customer;
            }
        }
        for (Appointment app : getAllAppointments()) {
            assert selectedCustomer != null;
            if (selectedCustomer.getId() == app.getCustomerId()) {
                selectedCustomerAppointments.add(app);
            }
        }
        return selectedCustomerAppointments;
    }

    //all appointment type options
    public static ObservableList<String> getAllAppointmentTypeOptions() {
        return FXCollections.observableArrayList("Planning Session", "Conference Call", "De-Briefing", "Miscellaneous", "Project Review", "Presentation", "Scrum", "Team Meeting");
    }


}