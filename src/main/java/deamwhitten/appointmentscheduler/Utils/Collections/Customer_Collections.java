package deamwhitten.appointmentscheduler.Utils.Collections;

import deamwhitten.appointmentscheduler.DataBase_Access.Customers_DA;
import deamwhitten.appointmentscheduler.Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Customer_Collections {
    //All Customers
    public static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    public static ObservableList<Customer> getAllCustomers() {
        allCustomers.clear();
        allCustomers.addAll(Customers_DA.getAllCustomersData());
        return allCustomers;
    }

    public static ObservableList<String> allCustomersNames = FXCollections.observableArrayList();
    public static ObservableList<String> getAllCustomersNames() {
        allCustomersNames.clear();
        getAllCustomers();
        for(Customer customer: allCustomers){
            allCustomersNames.add(customer.getName());
        }

        return allCustomersNames;
    }
}