package deamwhitten.appointmentscheduler.Controller;

import deamwhitten.appointmentscheduler.Model.Appointment;
import deamwhitten.appointmentscheduler.Utils.Collections.*;
import deamwhitten.appointmentscheduler.Utils.Window_Handler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Update_Appointment_Controller implements Initializable {
    @FXML
    private TextField appID_input;
    @FXML
    private ComboBox<String> customer_selection;
    @FXML
    private ComboBox<String> contact_selection;
    @FXML
    private ComboBox<String> type_selection;
    @FXML
    private TextField title_input;
    @FXML
    private TextArea description_input;
    @FXML
    private ComboBox<String> country_selection;
    @FXML
    private ComboBox<String> division_selection;
    @FXML
    private DatePicker date_selection;
    @FXML
    private ComboBox<String> start_selection;
    @FXML
    private ComboBox<String> end_selection;
    @FXML
    private Label error_label;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Appointment selectedApp = MainWindow_Controller.selectedAppointment;
        try {
            customer_selection.getItems().addAll(Customers_Collections.getAllCustomersNames());
            type_selection.getItems().addAll(Appointments_Collections.getAllAppointmentTypeOptions());
            contact_selection.getItems().addAll(Contacts_Collections.getAllContactNames());
            country_selection.getItems().addAll(Counties_Collections.getAllCountiesNames());
            start_selection.getItems().addAll(Appointments_Collections.getAllBusinessHoursAppointmentStartTimes());
            end_selection.getItems().addAll(Appointments_Collections.getAllBusinessHoursAppointmentEndTimes());

            appID_input.setText(String.valueOf(selectedApp.getId()));
            customer_selection.getSelectionModel().select(selectedApp.getCustomerId()-1);
            contact_selection.getSelectionModel().select(selectedApp.getContactId()-1);
            type_selection.getSelectionModel().select(selectedApp.getType());
            title_input.setText(selectedApp.getTitle());
            description_input.setText(selectedApp.getDescription());
            country_selection.getSelectionModel().select(selectedApp.getLocation());
            division_selection.getSelectionModel().select(selectedApp.getLocation());
            date_selection.setValue(LocalDate.parse(selectedApp.getStart().substring(0,10)));
            start_selection.getSelectionModel().select(selectedApp.getStart().substring(11, 19));
            end_selection.getSelectionModel().select(selectedApp.getEnd().substring(11, 19));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onCountrySelected(ActionEvent event) {
        division_selection.getItems().clear();
        String selectedCounty = country_selection.getSelectionModel().getSelectedItem();
        if(selectedCounty != null){
            division_selection.getItems().addAll(Divisions_Collections.getSelectedDivisionNamesByCountryID(selectedCounty));
        }

    }

    @FXML
    public void onCancelClick(ActionEvent event) throws IOException {
        Window_Handler.loadWindow("MainWindow_View","Appointment Scheduler", event);
    }
    @FXML
    public void onUpdateAppClick(ActionEvent event) throws IOException {
            Boolean isValidInput = validateInput();

            if(isValidInput){
                Window_Handler.loadWindow("MainWindow_View","Appointment Scheduler", event);
            }
    }

    private Boolean validateInput() {
        return true;
    }

}