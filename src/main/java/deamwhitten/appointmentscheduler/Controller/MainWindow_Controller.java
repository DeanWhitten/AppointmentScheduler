package deamwhitten.appointmentscheduler.Controller;


import deamwhitten.appointmentscheduler.Model.Appointment;
import deamwhitten.appointmentscheduler.Model.Customer;
import deamwhitten.appointmentscheduler.Utils.Collections.Appointments_Collections;
import deamwhitten.appointmentscheduler.Utils.Collections.Contacts_Collections;
import deamwhitten.appointmentscheduler.Utils.Collections.Customers_Collections;
import deamwhitten.appointmentscheduler.Utils.Reports_Handler;
import deamwhitten.appointmentscheduler.Utils.CurrentSession_properties;
import deamwhitten.appointmentscheduler.Utils.DataBase_Access.Appointments_DA;
import deamwhitten.appointmentscheduler.Utils.DataBase_Access.Customers_DA;
import deamwhitten.appointmentscheduler.Utils.Window_Handler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static deamwhitten.appointmentscheduler.Utils.Collections.Appointments_Collections.getAllAppointments;
import static javafx.collections.FXCollections.observableArrayList;

/**
 * Main window controller.
 */
public class MainWindow_Controller implements Initializable {
	/**
	 * Label to display message to user
	 */
	@FXML
    private Label user_msg_label;
	/**
	 * Label to display message in when an appointment is canceled successfully
	 */
    @FXML
    private  Label app_delete_msg_label;
	/**
	 * Table view for displaying appointments in
	 */
    @FXML
    private TableView<Appointment> appointments_table;
	/**
	 * Appointments Table column for appointment ID
	 */
    @FXML
    private TableColumn<Appointment, Integer> app_appID_col;
	/**
	 * Appointments Table column for customer ID
	 */
    @FXML
    private TableColumn<Appointment, Integer> app_customerID_col;
	/**
	 * Appointments Table column for appointment contact
	 */
    @FXML
    private TableColumn<Appointment, String> app_contact_col;
	/**
	 * Appointments Table column for appointment type
	 */
    @FXML
    private TableColumn<Appointment, String> app_type_col;
	/**
	 * Appointments Table column appointment title
	 */
    @FXML
    private TableColumn<Appointment, String> app_title_col;
	/**
	 * Appointments Table column for appointment description
	 */
    @FXML
    private TableColumn<Appointment, String> app_description_col;
	/**
	 * Appointments Table column for appointment location
	 */
    @FXML
    private TableColumn<Appointment, String> app_location_col;
	/**
	 * Appointments Table column for appointment start time
	 */
    @FXML
    private TableColumn<Appointment, String> app_start_col;
	/**
	 * Appointments Table column for appointment end time
	 */
    @FXML
    private TableColumn<Appointment, String> app_end_col;
	/**
	 * Appointments Table column for appointment user ID
	 */
    @FXML
    private TableColumn<Appointment, String> app_userID_col;
	/**
	 * Appointments radio button for all appointments
	 */
    @FXML
    private RadioButton all_radio;
	/**
	 * Appointments radio button for appointments of this month
	 */
    @FXML
    private RadioButton month_radio;
	/**
	 * Appointments radio button for appointments of this week
	 */
    @FXML
    private RadioButton week_radio;
	/**
	 * ComboBox for selecting a customer to filter appointments in the appointments' table by
	 */
    @FXML
    private ComboBox<String> filterByCustomer_selection;
	/**
	 * Error Label for showing errors on the appointments tab
	 */
    @FXML
    private Label app_error_label;


	/**
	 * Table View for displaying customers in
	 */
    @FXML
    private TableView<Customer> customers_table;
	/**
	 * Label to display message in when a customer is deleted successfully
	 */
    @FXML
    private Label customer_delete_msg_label;
	/**
	 * Customer table column for customer ID
	 */
    @FXML
    private TableColumn<Customer,Integer> customer_customerID_col;
	/**
	 * Customer table column for customer name
	 */
    @FXML
    private TableColumn<Customer,String> customer_customerName_col;
	/**
	 * Customer table column for customer phone number
	 */
    @FXML
    private TableColumn<Customer,String> customer_phoneNum_col  ;
	/**
	 * Customer table column for customer address
	 */
    @FXML
    private TableColumn<Customer,String> customer_address_col ;
	/**
	 * Customer table column for customer postal code
	 */
    @FXML
    private TableColumn<Customer,String> customer_postal_col ;
	/**
	 * Customer table column for customer division ID
	 */
    @FXML
    private TableColumn<Customer,Integer> customer_divisionID_col;
	/**
	 * Error label for displaying error messages on the customer's tab
	 */
	@FXML
    private Label customer_error_label;
	/**
	 * Label for inserting the number of appointments by types and month report data into
	 */
    @FXML
    private Label numOfTypesByMonth_report_Label;
	/**
	 * ComboBox for selecting the contact to generate a schedule report for
	 */
	@FXML
    private ComboBox<String> contactSchedule_selection;
	/**
	 * Label for inserting contacts' appointment schedule report data into
	 */
    @FXML
    private Label contactSchedule_Results_label;
	/**
	 * Label for inserting the number of appointments per location report data into
	 */
    @FXML
    private Label appPerLocation_Results_label;

	/**
	 * The constant selectedAppointment that stores user selected appointment.
	 */
	public static Appointment selectedAppointment;
	/**
	 * The constant selectedCustomer that stores user selected appointment.
	 */
	public static Customer selectedCustomer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resetUI();
        numOfTypesByMonth_report_Label.setText(Reports_Handler.getNumOfAppsPerMonthAndType());
        appPerLocation_Results_label.setText(Reports_Handler.getNumOfCustomersPerLocation());
        try {
            user_msg_label.setText("Welcome, " + CurrentSession_properties.getUserName());
            loadTables();
            filterByCustomer_selection.getItems().addAll(observableArrayList(Customers_Collections.getAllCustomersNames()));
            contactSchedule_selection.getItems().addAll(observableArrayList(Contacts_Collections.getAllContactNames()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

	/**
	 * On appointment radio selection.
	 * When a user selects a radio button on the appointment tab, the error UI messages are reset,
	 * thee filtered customer selection is cleared then the appropriate appointments are loaded
	 * based on radio button selection.
	 */
	@FXML
    public void onAppointmentRadioSelection() {
        resetUI();
        filterByCustomer_selection.getSelectionModel().clearSelection();

       if(all_radio.isSelected()){
          appointments_table.setItems(getAllAppointments());
       }
       if (month_radio.isSelected()) {
           appointments_table.setItems(Appointments_Collections.getThisMonthAppointments());
       }
       if (week_radio.isSelected()){
           appointments_table.setItems(Appointments_Collections.getThisWeekAppointments());
       }
    }

	/**
	 * On filter by customer selected.
	 * Filters appointments based on selected customer.
	 */
	@FXML
    public void onFilterByCustomerSelected() {
        resetUI();
        appointments_table.setItems(Appointments_Collections.getSelectedCustomerAppointments(filterByCustomer_selection.getValue()));
    }

	/**
	 * On schedule appointment click.
	 * Opens window that allows user to schedule an appointment.
	 *
	 * @param event the event of scheduled appointment click
	 * @throws IOException the io exception
	 */
	@FXML
    public void onScheduleAppointmentClick(ActionEvent event) throws IOException {
        resetUI();
        Window_Handler.loadWindow("Add_Appointment_View", "Schedule Appointment", event);
    }

	/**
	 * On update appointment click.
	 * Checks to make sure an appointment is selected then if one is selected sends the user to
	 * the update window otherwise if an appointment is not selected an error is shown.
	 *
	 * @param event the event of the user clicking update appointment
	 * @throws IOException the io exception
	 */
	@FXML
    public void onUpdateAppointmentClick(ActionEvent event) throws IOException {
        resetUI();
        selectedAppointment =appointments_table.getSelectionModel().getSelectedItem();
           try {
               if( selectedAppointment != null) {
                   Window_Handler.loadWindow("Update_Appointment_View", "Update Appointment Details", event);
               }
               if (selectedAppointment==null) {
                   app_error_label.setText("Select an Appointment to update");
                   app_error_label.setOpacity(1);
               }
           }catch (NullPointerException e)
           {
               e.printStackTrace();
           }
    }

	/**
	 * On cancel appointment click.
	 * Resets the UI error messages, then tries to check if an appointment is selected to cancel.
	 * If there isn't a selected appointment an error message is shown. If an appointment is
	 * selected then an alert is given to the user to confirm the cancellation.
	 */
	@FXML
    public void onCancelAppointmentClick(){
        resetUI();
        selectedAppointment =appointments_table.getSelectionModel().getSelectedItem();
        try {
            if( selectedAppointment != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Alert");
                alert.setContentText("You are about to delete " + selectedAppointment.getType() + " appointment " + selectedAppointment.getId() + ". \n Click OK to continue this action otherwise click cancel. ");
                Optional<ButtonType> result = alert.showAndWait();

                if(result.get() == ButtonType.OK){
                    Appointments_DA.deleteAppointment(appointments_table.getSelectionModel().getSelectedItem().getId());

                    String successMsg = "You Successfully canceled a $type appointment $appId.";
                    app_delete_msg_label.setText(successMsg.replace("$type",
                            selectedAppointment.getType() ).replace("$appId",
                            String.valueOf(selectedAppointment.getId())));
                    app_delete_msg_label.setOpacity(1);
                    appointments_table.setItems(getAllAppointments());
                }

            }else{
                app_error_label.setText("Select an Appointment to cancel");
                app_error_label.setOpacity(1);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

	/**
	 * On add new customer click.
	 *  Opens the window to create a new customer
	 *
	 * @param event the event of the user clicking add.
	 *
	 * @throws IOException the io exception
	 */
	@FXML
    public void onAddNewCustomerClick(ActionEvent event) throws IOException {
        resetUI();
        Window_Handler.loadWindow("Add_Customer_View", "Add New Customer", event);
    }

	/**
	 * On update customer click.
	 * Resets the UI error messages, then tries to check if a customer is selected. If a customer
	 * is not selected then an error message is displayed otherwise the user is redirected to a
	 * window to update customer data.
	 *
	 * @param event the event of user clicking update
	 * @throws IOException the io exception
	 */
	@FXML
    public void onUpdateCustomerClick(ActionEvent event) throws IOException {
        resetUI();
        selectedCustomer = customers_table.getSelectionModel().getSelectedItem();
        try {
            if( selectedCustomer != null) {
                Window_Handler.loadWindow("Update_Customer_View", "Update Customer Details", event);
            }
            if (selectedCustomer == null) {
                customer_error_label.setText("Select a customer to update");
                customer_error_label.setOpacity(1);
            }
        }catch (NullPointerException e)
        {
            e.printStackTrace();
        }
    }

	/**
	 * On delete customer click.
	 * Resets the UI error messages, then tries to check if a customer is selected to delete.
	 * If there isn't a selected customer an error message is shown. If a customer is
	 * selected then an alert is given to the user to confirm the deletion.
	 */
	@FXML
    public void onDeleteCustomerClick(){
        resetUI();
        selectedCustomer = customers_table.getSelectionModel().getSelectedItem();
        try {
            if( selectedCustomer != null) {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Alert");
                alert.setContentText("You are about to delete " + selectedCustomer.getName() +"'s" +
                        " data and all their currently scheduled appointments."  +
                        ". \n " +
                        "Click OK to " +
                        "continue this action otherwise click cancel. ");
                Optional<ButtonType> result = alert.showAndWait();

                if(result.get() == ButtonType.OK){
                    Customers_DA.deleteCustomer(selectedCustomer.getId());
                    String successMsg = "You Successfully deleted customer data for $name.";
                    customer_delete_msg_label.setText(successMsg.replace("$name", selectedCustomer.getName()));
                    customer_delete_msg_label.setOpacity(1);
                    customers_table.setItems(Customers_Collections.getAllCustomers());
                    appointments_table.setItems(Appointments_Collections.getAllAppointments());
                }
            }else{
                customer_error_label.setText("Select a customer to delete");
                customer_error_label.setOpacity(1);
            }
        }catch (NullPointerException e)
        {
            e.printStackTrace();
        }
    }

	/**
	 * On schedule report click.
	 * Checks that a contact is selected, if it's not empty a report of the contact's appointment
	 * schedule is displayed
	 */
	@FXML
    public void onScheduleReportClick(){
        String contactSelected =  contactSchedule_selection.getSelectionModel().getSelectedItem();

        if(!contactSchedule_selection.getSelectionModel().isEmpty()){
            contactSchedule_Results_label.setText(Reports_Handler.getContactScheduleByContactID_Report(contactSelected));
        }
    }

	/**
	 *Table Loader
	 * Sets up the cell value factories and sets items to the tables for the tables on the
	 * customers and appointments tab.
	 */
    private void loadTables(){
        //Appointments Table
        app_appID_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        app_customerID_col.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        app_contact_col.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        app_title_col.setCellValueFactory(new PropertyValueFactory<>("title"));
        app_description_col.setCellValueFactory(new PropertyValueFactory<>("description"));
        app_location_col.setCellValueFactory(new PropertyValueFactory<>("location"));
        app_type_col.setCellValueFactory(new PropertyValueFactory<>("type"));
        app_start_col.setCellValueFactory(new PropertyValueFactory<>("start"));
        app_end_col.setCellValueFactory(new PropertyValueFactory<>("end"));
        app_userID_col.setCellValueFactory(new PropertyValueFactory<>("UserId"));

        //Customers Table
        customer_customerID_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        customer_customerName_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        customer_phoneNum_col.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customer_address_col.setCellValueFactory(new PropertyValueFactory<>("address"));
        customer_postal_col.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customer_divisionID_col.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
        try {
            appointments_table.setItems(getAllAppointments());
            customers_table.setItems(Customers_Collections.getAllCustomers());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

	/**
	 * UI message resets
	 */
    private  void resetUI(){
         app_delete_msg_label.setOpacity(0);
         app_error_label.setOpacity(0);
         customer_delete_msg_label.setOpacity(0);
         customer_error_label.setOpacity(0);
    }
}