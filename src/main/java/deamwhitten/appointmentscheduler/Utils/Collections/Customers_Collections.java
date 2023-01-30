package deamwhitten.appointmentscheduler.Utils.Collections;

import deamwhitten.appointmentscheduler.Utils.Database_Access.Customers_DA;
import deamwhitten.appointmentscheduler.Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The type Customers collections.
 */
public abstract class Customers_Collections {

	/**
	 * Gets all customers.
	 *
	 * @return the all customers
	 */
	public static ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        allCustomers.clear();
        allCustomers.addAll(Customers_DA.getAllCustomersData());
        return allCustomers;
    }

	/**
	 * Gets all customers names.
	 *
	 * @return the all customers names
	 */
	public static ObservableList<String> getAllCustomersNames() {
        ObservableList<String> allCustomersNames = FXCollections.observableArrayList();
        allCustomersNames.clear();
        getAllCustomers();
        for(Customer customer: getAllCustomers()){
            allCustomersNames.add(customer.getName());
        }
        return allCustomersNames;
    }

	/**
     * Gets customer id by name.
     *
     * @param selectedCustomerName the selected customer name
     * @return the customer id by name
     */
	public static int getCustomerIDByName(String selectedCustomerName) {
        getAllCustomers().clear();
        int customerID = 0;
        for (Customer customer: getAllCustomers()){
            if(customer.getName().equals(selectedCustomerName)){
                 customerID = customer.getId();
            }
        }
        return customerID;
    }

    /**
     *
     * NEWLY ADDED JAN 2023
     *
     * **/


    public static ObservableList<Customer> searchCustomers(String text) {
        getAllCustomers().clear();
        ObservableList<Customer> allCustomersOfName = FXCollections.observableArrayList();

        for (Customer customer: getAllCustomers()){
            if(customer.getName().toLowerCase().contains(text.toLowerCase())){
                allCustomersOfName.add(customer);
            }
        }
        return allCustomersOfName;
    }
}