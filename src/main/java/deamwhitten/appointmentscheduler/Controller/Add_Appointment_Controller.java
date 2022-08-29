package deamwhitten.appointmentscheduler.Controller;

import deamwhitten.appointmentscheduler.Utils.Collections.*;
import deamwhitten.appointmentscheduler.Utils.DataBase_Access.Appointments_DA;
import deamwhitten.appointmentscheduler.Utils.Window_Handler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.time.*;
import java.util.ResourceBundle;

public class Add_Appointment_Controller implements Initializable {
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
    private ComboBox<LocalTime> start_selection;
    @FXML
    private ComboBox<LocalTime> end_selection;
    @FXML
    private Label error_label;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            appID_input.setText("--auto generated--");
            customer_selection.getItems().addAll(Customers_Collections.getAllCustomersNames());
            type_selection.getItems().addAll(Appointments_Collections.getAllAppointmentTypeOptions());
            contact_selection.getItems().addAll(Contacts_Collections.getAllContactNames());
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
    public void onScheduleClick(ActionEvent event) throws Exception {
        Boolean isValidInput = validateInputs();
        if(isValidInput){
            collectInputsAndSendToDA();
            Window_Handler.loadWindow("MainWindow_View","Appointment Scheduler", event);
        }
    }

    private Boolean validateInputs() {
        Boolean timeOverLap = checkTimes();
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
                                               }else {
                                                   start_selection.getSelectionModel().clearSelection();
                                                   start_selection.requestFocus();
                                                   end_selection.getSelectionModel().clearSelection();
                                                   start_selection.requestFocus();
                                                   error_label.setText("Please select a start " +
                                                           "time that is before the end time.");
                                                   return false;
                                               }
                                           } else {
                                               start_selection.getSelectionModel().clearSelection();
                                               start_selection.requestFocus();
                                               end_selection.getSelectionModel().clearSelection();
                                               start_selection.requestFocus();
                                               error_label.setText("Please select times that dont" +
                                                       " overlap with customers scheduled " +
                                                       "appointments.");
                                               return false;
                                           }
                                        } else {
                                            date_selection.requestFocus();
                                            error_label.setText("Please select an appointment " +
                                                    "date.");
                                            return false;
                                        }
                                    } else {
                                        division_selection.requestFocus();
                                        error_label.setText("Please select the division.");
                                        return false;
                                    }
                                } else {
                                    country_selection.requestFocus();
                                    error_label.setText("Please select the country.");
                                    return false;
                                }
                            }else {
                                description_input.requestFocus();
                                error_label.setText("Please input a description.");
                                return false;
                            }
                        }else {
                            title_input.requestFocus();
                            error_label.setText("Please input a title.");
                            return false;
                        }
                    }else {
                        type_selection.requestFocus();
                        error_label.setText("Please select the appointment type.");
                        return false;
                    }
                }else {
                    contact_selection.requestFocus();
                    error_label.setText("Please select a contact for the appointment");
                    return false;
                }
            }else {
                customer_selection.requestFocus();
                error_label.setText("Please select a customer for the appointment");
                return false;
            }

    }

    private Boolean checkTimes() {
        String customer = customer_selection.getSelectionModel().getSelectedItem();
        LocalDate date = date_selection.getValue();
        LocalDateTime start = LocalDateTime.of(date, start_selection.getSelectionModel().getSelectedItem());
        LocalDateTime end =
                LocalDateTime.of(date, end_selection.getSelectionModel().getSelectedItem());

        return Appointments_Collections.checkForAppointmentOverlapByCustomer_Add(start, end,
                Customers_Collections.getCustomerIDByName(customer));

    }

    private void collectInputsAndSendToDA() {
        String customerName = customer_selection.getSelectionModel().getSelectedItem();
        String contact =contact_selection.getSelectionModel().getSelectedItem();
        String type = type_selection.getSelectionModel().getSelectedItem();
        String title = title_input.getText();
        String description = description_input.getText();
        String location =
                division_selection.getSelectionModel().getSelectedItem() +", " + country_selection.getSelectionModel().getSelectedItem();
        LocalDate dateToAdd = date_selection.getValue();
        LocalDateTime start = LocalDateTime.of(dateToAdd, start_selection.getValue());
        LocalDateTime end = LocalDateTime.of(dateToAdd, end_selection.getValue());
        Appointments_DA.writeNewAppointmentDataToDB( customerName, contact,
                type, title, description, location, start, end);
    }
}