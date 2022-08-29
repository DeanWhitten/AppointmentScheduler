package deamwhitten.appointmentscheduler.Utils.Collections;

import deamwhitten.appointmentscheduler.Model.Appointment;
import deamwhitten.appointmentscheduler.Utils.DataBase_Access.Appointments_DA;

import deamwhitten.appointmentscheduler.Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.time.LocalDate;

import java.time.LocalDateTime;
import java.util.Optional;


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

    //all appointment start times during business hours
    public static ObservableList<String> getAllBusinessHoursAppointmentStartTimes() {
        ObservableList<String> allBusinessHoursAppointmentStartTimes = FXCollections.observableArrayList();
        allBusinessHoursAppointmentStartTimes.clear();
        for (int h = 8; h <= 21; h++) {
            String newTime;
            if (h < 10) {
                newTime = "0" + h + ":00:00";
            } else {
                newTime = h + ":00:00";
            }
            allBusinessHoursAppointmentStartTimes.add(newTime);
        }
        return allBusinessHoursAppointmentStartTimes;
    }

    //all appointment  end times during business hours

    public static ObservableList<String> getAllBusinessHoursAppointmentEndTimes() {
        ObservableList<String> allBusinessHoursAppointmentEndTimes = FXCollections.observableArrayList();
        allBusinessHoursAppointmentEndTimes.clear();
        for (int h = 9; h <= 22; h++) {
            String newTime;
            if (h < 10) {
                newTime = "0" + h + ":00:00";
            } else {
                newTime = h + ":00:00";
            }
            allBusinessHoursAppointmentEndTimes.add(newTime);
        }
        return allBusinessHoursAppointmentEndTimes;
    }


    public static Boolean checkForAppointmentOverlapByCustomer_Add(LocalDateTime Start,
                                                                   LocalDateTime End,
                                                                   int customerID) {

        for (Appointment a : getAllAppointments()) {
            if (a.getCustomerId() != customerID) {
                continue;
            }
            if ((Start.isAfter(a.getStart()) || Start.isEqual(a.getStart())) && (Start.isBefore(a.getEnd()))) { //1
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Alert");
                alert.setContentText("Start time overlaps a current appointment.");
                Optional<ButtonType> result = alert.showAndWait();
                return true;
            }
            if ((End.isAfter(a.getStart())) && (End.isBefore(a.getEnd()) || End.isEqual(a.getEnd()))) { //2
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Alert");
                alert.setContentText("End time overlaps a current appointment.");
                Optional<ButtonType> result = alert.showAndWait();
                return true;
            }
            if ((Start.isBefore(a.getStart()) || Start.isEqual(a.getStart())) && (End.isAfter(a.getEnd()) || End.isEqual(a.getEnd()))) { //3
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Alert");
                alert.setContentText("Start and end times overlap a current appointment.");
                Optional<ButtonType> result = alert.showAndWait();
                return true;
            }
        }
        return false;
    }

    public static boolean checkForAppointmentOverlapByCustomer_Modify(LocalDateTime Start,
                                                                   LocalDateTime End,
                                                                   int customerID,
                                                                   int appointmentID) {
        for (Appointment a : getAllAppointments()) {
            if (a.getCustomerId() != customerID || a.getId() == appointmentID) {
                continue;
            }
            if ((Start.isAfter(a.getStart()) || Start.isEqual(a.getStart())) && (Start.isBefore(a.getEnd()))) { //1
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Alert");
                alert.setContentText("Start time overlaps a current appointment.");
                Optional<ButtonType> result = alert.showAndWait();
                return true;
            }
            if ((End.isAfter(a.getStart())) && (End.isBefore(a.getEnd()) || End.isEqual(a.getEnd()))) { //2
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Alert");
                alert.setContentText("End time overlaps a current appointment.");
                Optional<ButtonType> result = alert.showAndWait();
                return true;
            }
            if ((Start.isBefore(a.getStart()) || Start.isEqual(a.getStart())) && (End.isAfter(a.getEnd()) || End.isEqual(a.getEnd()))) { //3
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Alert");
                alert.setContentText("Start and end times overlap a current appointment.");
                Optional<ButtonType> result = alert.showAndWait();
                return true;
            }
        }
        return false;
    }
}