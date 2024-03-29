package deamwhitten.appointmentscheduler.Utils.Collections;

import deamwhitten.appointmentscheduler.Model.Appointment;
import deamwhitten.appointmentscheduler.Model.Contact;
import deamwhitten.appointmentscheduler.Model.Customer;
import deamwhitten.appointmentscheduler.Utils.Database_Access.Appointments_DA;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.time.LocalDate;


/**
 * Appointments collections.
 */
public abstract class Appointments_Collections {

	/**
	 * Gets all appointments.
	 *
	 * @return the all appointments
	 */
	public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        allAppointments.clear();
        allAppointments.addAll(Appointments_DA.getAllAppointmentsData());
        return allAppointments;
    }

	/**
	 * Gets this month appointments.
	 * Uses a lambda to simplify the code and make it easier to read and work with.
	 * @return this month's appointments
	 */
	public static ObservableList<Appointment> getThisMonthAppointments() {
        LocalDate now = LocalDate.now();
        LocalDate nowPlus1Month = now.plusMonths(1);

        //lambda expression used to efficiently filter appointments by month
        FilteredList<Appointment> filteredData = new FilteredList<>(getAllAppointments());
        filteredData.setPredicate(row -> {
            LocalDate rowDate = LocalDate.from(row.getStart());
            return rowDate.isAfter(now.minusDays(1)) && rowDate.isBefore(nowPlus1Month);
        });
        return filteredData;
    }

	/**
	 * Gets this week appointments.
	 * Uses a lambda to simplify the code and make it easier to read and work with.
	 * @return this week's appointments
	 */
	public static ObservableList<Appointment> getThisWeekAppointments() {
        LocalDate now = LocalDate.now();
        LocalDate nowPlus1Week = now.plusWeeks(1);

        //lambda expression used to efficiently filter appointments by week
        FilteredList<Appointment> filteredData = new FilteredList<>(getAllAppointments());
        filteredData.setPredicate(row -> {
            LocalDate rowDate = LocalDate.from(row.getStart());
            return rowDate.isAfter(now.minusDays(1)) && rowDate.isBefore(nowPlus1Week);
        });
        return filteredData;
    }

	/**
	 * Gets selected customer appointments.
	 *
	 * @param name the name
	 * @return the selected customer's appointments
	 */
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

	/**
	 * Gets all appointment type options.
	 *
	 * @return all appointment type options
	 */
	public static ObservableList<String> getAllAppointmentTypeOptions() {
        return FXCollections.observableArrayList("Planning Session", "Conference Call", "De-Briefing", "Miscellaneous", "Project Review", "Presentation", "Scrum", "Team Meeting");
    }



    /**
     *
     * NEWLY ADDED JAN 2023
     *
     * **/

    public static ObservableList<Appointment> searchAppointments(int id){
        ObservableList<Appointment> foundApps = FXCollections.observableArrayList();

        for(Appointment A_App: getAllAppointments()){
            //Customers
            if(A_App.getCustomerId() == id){
                foundApps.add(A_App);
            }
            //Contacts
            if(A_App.getContactId() == id){
                foundApps.add(A_App);
            }
        }
        return foundApps;
    }

    public static ObservableList<Appointment> searchAppointments(String text){
        ObservableList<Appointment> foundApps = FXCollections.observableArrayList();
        foundApps.clear();

        for(Appointment A_App: getAllAppointments()){
            //Customers
            ObservableList<Customer> customerOFId= FXCollections.observableArrayList();
            customerOFId.clear();
            customerOFId.addAll(Customers_Collections.searchCustomers(text));
            for(Customer c: customerOFId){
                if(A_App.getCustomerId() == c.getId()){
                    foundApps.add(A_App);
                }
            }

            //Contacts
            ObservableList<Contact> contactsOfId = FXCollections.observableArrayList();
            contactsOfId.clear();
            contactsOfId.addAll(Contacts_Collections.getContactIDListByName(text));
            for (Contact c : contactsOfId){
                if(A_App.getContactId() == c.getId()){
                    foundApps.add(A_App);
                }
            }
        }
        return foundApps;
    }
}