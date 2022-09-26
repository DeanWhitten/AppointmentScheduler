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
    @FXML
    private Label user_msg_label;
    @FXML
    private  Label app_delete_msg_label;
    @FXML
    private TableView<Appointment> appointments_table;
    @FXML
    private TableColumn<Appointment, Integer> app_appID_col;
    @FXML
    private TableColumn<Appointment, Integer> app_customerID_col;
    @FXML
    private TableColumn<Appointment, String> app_contact_col;
    @FXML
    private TableColumn<Appointment, String> app_type_col;
    @FXML
    private TableColumn<Appointment, String> app_title_col;
    @FXML
    private TableColumn<Appointment, String> app_description_col;
    @FXML
    private TableColumn<Appointment, String> app_location_col;
    @FXML
    private TableColumn<Appointment, String> app_start_col;
    @FXML
    private TableColumn<Appointment, String> app_end_col;
    @FXML
    private TableColumn<Appointment, String> app_userID_col;
    @FXML
    private RadioButton all_radio;
    @FXML
    private RadioButton month_radio;
    @FXML
    private RadioButton week_radio;
    @FXML
    private ComboBox<String> filterByCustomer_selection;
    @FXML
    private Label app_error_label;


    @FXML
    private TableView<Customer> customers_table;
    @FXML
    private Label customer_delete_msg_label;
    @FXML
    private TableColumn<Customer,Integer> customer_customerID_col;
    @FXML
    private TableColumn<Customer,String> customer_customerName_col;
    @FXML
    private TableColumn<Customer,String> customer_phoneNum_col  ;
    @FXML
    private TableColumn<Customer,String> customer_address_col ;
    @FXML
    private TableColumn<Customer,String> customer_postal_col ;
    @FXML
    private TableColumn<Customer,Integer> customer_divisionID_col;
    @FXML
    private Label customer_error_label;
    @FXML
    private Label numOfTypesByMonth_report_Label;
    @FXML
    private ComboBox<String> contactSchedule_selection;
    @FXML
    private Label contactSchedule_Results_label;
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