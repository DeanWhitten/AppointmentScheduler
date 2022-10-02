package deamwhitten.appointmentscheduler.Controller;

import deamwhitten.appointmentscheduler.Utils.Collections.*;
import deamwhitten.appointmentscheduler.Utils.CurrentSession_properties;
import deamwhitten.appointmentscheduler.Utils.DataBase_Access.Appointments_DA;
import deamwhitten.appointmentscheduler.Utils.TimeOverlap_Error_Handler;
import deamwhitten.appointmentscheduler.Utils.Window_Handler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.util.ResourceBundle;

/**
 * Add appointment controller.
 */
public class Add_Appointment_Controller implements Initializable {
    /**
     * Text field for the appointment id
     */
    @FXML
    private TextField appID_input;
    /**
     * The Combo box for the customer selection error
     */
    @FXML
    private ComboBox<String> customer_selection;
    /**
     * ComboBox for selecting contact for appointment
     */
    @FXML
    private ComboBox<String> contact_selection;
    /**
     * ComboBox for selecting appointment type
     */
    @FXML
    private ComboBox<String> type_selection;
    /**
     * Test field for appointment title input
     */
    @FXML
    private TextField title_input;
    /**
     * Text area for writing appointment description
     */
    @FXML
    private TextArea description_input;
    /**
     * ComboBox for selecting a country
     */
    @FXML
    private ComboBox<String> country_selection;
    /**
     * ComboBox for selecting a division
     */
    @FXML
    private ComboBox<String> division_selection;
    /**
     * Date picker for selecting appointment date
     */
    @FXML
    private DatePicker date_selection;
    /**
     * Combobox for selecting appointment start time
     */
    @FXML
    private ComboBox<LocalTime> start_selection;
    /**
     * Combobox for selecting appointment end time
     */
    @FXML
    private ComboBox<LocalTime> end_selection;
    /**
     * The error text label
     */
    @FXML
    public static Label error_label;
    /**
     * The user info text label
     */
    @FXML
    private Label userInfo_label;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        error_label.setOpacity(0);
        try {
            appID_input.setText("--auto generated--");
            customer_selection.getItems().addAll(Customers_Collections.getAllCustomersNames());
            type_selection.getItems().addAll(Appointments_Collections.getAllAppointmentTypeOptions());
            contact_selection.getItems().addAll(Contacts_Collections.getAllContactNames());
            userInfo_label.setText("Current User: " + CurrentSession_properties.getUserName() +
                    "\t User ID: " + CurrentSession_properties.getUserID());
            country_selection.getItems().addAll(Counties_Collections.getAllCountiesNames());

            LocalDate date = LocalDate.now();
            date_selection.setValue(date);
            LocalTime startComboStart = LocalTime.of(8, 0);
            ZoneId localZone = ZoneId.systemDefault();
            ZonedDateTime startEST = ZonedDateTime.of(date, startComboStart, ZoneId.of("America/New_York"));
            ZonedDateTime startZDT = startEST.withZoneSameInstant(localZone);
            ZonedDateTime endZDT = startZDT.plusHours(14);

            while (startZDT.isBefore(endZDT)) {
                start_selection.getItems().add(startZDT.toLocalTime());
                startZDT = startZDT.plusMinutes(30);
                end_selection.getItems().add(startZDT.toLocalTime().plusHours(1));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

	/**
	 * On country selected.
     * Clears division selection and makes sure that a country is selected by the user then if
     * a country is selected, that county's respective divisions are loaded into the
     * divisions ComboBox.
	 *
	 * @param event the event when the user selects a country
	 */
	@FXML
    public void onCountrySelected(ActionEvent event) {
        division_selection.getItems().clear();
        String selectedCounty = country_selection.getSelectionModel().getSelectedItem();
        if(selectedCounty != null){
            division_selection.getItems().addAll(Divisions_Collections.getSelectedDivisionNamesByCountryID(selectedCounty));
        }
    }

	/**
	 * On cancel click.
	 * returns user back to the main screen.
     *
	 * @param event the event of user clicking cancel
	 * @throws IOException the io exception thrown
	 */
	@FXML
    public void onCancelClick(ActionEvent event) throws IOException {
        Window_Handler.loadWindow("MainWindow_View","Appointment Scheduler", event);
    }

	/**
	 * On schedule click.
	 * checks to make sure there is a selected start and end time. After it validates the inputs
     * and based on whether the inputs are valid or not, it will either show an error or send the
     * data to be saved to the database and redirects user to the main screen.
     *
	 * @param event the event of the user clicking schedule
	 * @throws Exception the exception
	 */
	@FXML
    public void onScheduleClick(ActionEvent event) throws Exception {
        if(start_selection.getSelectionModel().getSelectedItem() != null && end_selection.getSelectionModel().getSelectedItem() != null){
            Boolean isValidInput = validateInputs();
            if(isValidInput){
                collectInputsAndSendToDA();
                Window_Handler.loadWindow("MainWindow_View","Appointment Scheduler", event);
            }
        }else {
            error_label.setText("Please select appointment times.");
            error_label.setOpacity(1);
        }
    }

	/**
	 * Validate inputs boolean.
	 * checks each input to see if its valid and meets business requirements. Is chained in order
     * of how the form is laid out so that any invalid inputs are given errors in the order that
     * they appear until they all pass validation.
     *
	 * @return the boolean
	 */
	public Boolean validateInputs() {
        Boolean timeOverLap= checkForOverlapTimes();

        LocalDate dateToAdd = date_selection.getValue();
        LocalDateTime start = LocalDateTime.of(dateToAdd, start_selection.getValue());
        LocalDateTime end = LocalDateTime.of(dateToAdd, end_selection.getValue());

        if(!customer_selection.getSelectionModel().isEmpty()){
            if(!contact_selection.getSelectionModel().isEmpty()){
                if(!type_selection.getSelectionModel().isEmpty()){
                    if(!title_input.getText().isEmpty()){
                        if(!description_input.getText().isEmpty()){
                            if(!country_selection.getSelectionModel().isEmpty()){
                                if(!division_selection.getSelectionModel().isEmpty()){
                                    if(!(date_selection.getValue() == null)){
                                        if(!start_selection.getSelectionModel().isEmpty() && !end_selection.getSelectionModel().isEmpty() && !timeOverLap){
                                            if(start.isBefore(end) ){
                                                return true;
                                            } else {
                                                start_selection.getSelectionModel().clearSelection();
                                                start_selection.requestFocus();
                                                end_selection.getSelectionModel().clearSelection();
                                                start_selection.requestFocus();
                                                error_label.setText("Please select a start time that is before the end time.");
                                                error_label.setOpacity(1);
                                                    return false;
                                                }
                                        } else {
                                            start_selection.getSelectionModel().clearSelection();
                                            start_selection.requestFocus();
                                            end_selection.getSelectionModel().clearSelection();
                                            start_selection.requestFocus();
                                            return false;
                                        }
                                    } else {
                                        date_selection.requestFocus();
                                        error_label.setText("Please select an appointment " +
                                                "date.");
                                        error_label.setOpacity(1);
                                        return false;
                                    }
                                } else {
                                    division_selection.requestFocus();
                                    error_label.setText("Please select the division.");
                                    error_label.setOpacity(1);
                                    return false;
                                }
                            } else {
                                country_selection.requestFocus();
                                error_label.setText("Please select the country.");
                                error_label.setOpacity(1);
                                return false;
                            }
                        }else {
                            description_input.requestFocus();
                            error_label.setText("Please input a description.");
                            error_label.setOpacity(1);
                            return false;
                        }
                    }else {
                        title_input.requestFocus();
                        error_label.setText("Please input a title.");
                        error_label.setOpacity(1);
                        return false;
                    }
                }else {
                    type_selection.requestFocus();
                    error_label.setText("Please select the appointment type.");
                    error_label.setOpacity(1);
                    return false;
                }
            }else {
                contact_selection.requestFocus();
                error_label.setText("Please select a contact for the appointment");
                error_label.setOpacity(1);
                return false;
            }
        }else {
            customer_selection.requestFocus();
            error_label.setText("Please select a customer for the appointment");
            error_label.setOpacity(1);
            return false;
        }
    }

    /**
     * Check for overlap times
     * assigns needed parameters to variables to be sent to the TimeOverlap_Error_Handler and
     * returns true or false based on if there is an overlap in time of already existing
     * appointments for selected customer
     *
     * @return a boolean of whether there is an overlap in times for existing appointments for
     * selected customer
     */
    private Boolean checkForOverlapTimes() {
        String customer = customer_selection.getSelectionModel().getSelectedItem();
        LocalDate date = date_selection.getValue();
        LocalDateTime start = LocalDateTime.of(date, start_selection.getSelectionModel().getSelectedItem());
        LocalDateTime end = LocalDateTime.of(date, end_selection.getSelectionModel().getSelectedItem());
        return TimeOverlap_Error_Handler.checkForAppointmentOverlapByCustomer_Add(start, end, Customers_Collections.getCustomerIDByName(customer), error_label);
    }

    /**
     * Collect Inputs and Send to DA
     * Assigns valid inputs to local variables for easy readability then sends the data to the
     * database using the writeNewAppointmentDataToDB method in Appointments_DA
     *
     */
    private void collectInputsAndSendToDA() {
        String customerName = customer_selection.getSelectionModel().getSelectedItem();
        String contact =contact_selection.getSelectionModel().getSelectedItem();
        String type = type_selection.getSelectionModel().getSelectedItem();
        String title = title_input.getText();
        String description = description_input.getText();
        String location = division_selection.getSelectionModel().getSelectedItem() +", " + country_selection.getSelectionModel().getSelectedItem();
        LocalDate dateToAdd = date_selection.getValue();
        LocalDateTime start = LocalDateTime.of(dateToAdd, start_selection.getValue());
        LocalDateTime end = LocalDateTime.of(dateToAdd, end_selection.getValue());

        Appointments_DA.writeNewAppointmentDataToDB( customerName, contact, type, title,
                description, location, start, end);
    }

}