package deamwhitten.appointmentscheduler.Controller;


import deamwhitten.appointmentscheduler.Model.Appointment;
import deamwhitten.appointmentscheduler.Utils.Collections.Appointment_Collections;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindow_Controller implements Initializable {
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
    private ToggleGroup appointmentsFilterGroup;
    @FXML
    private RadioButton all_radio;
    @FXML
    private RadioButton month_radio;
    @FXML
    private RadioButton week_radio;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadTables();
    }
    
    //appointment radios filters - All, Month, Week
    public void onAppointmentRadioSelection(ActionEvent event) throws Exception {
       if(all_radio.isSelected()){
          appointments_table.setItems(Appointment_Collections.getAllAppointments());
       }
       if (month_radio.isSelected()) {
           appointments_table.setItems(Appointment_Collections.getThisMonthAppointments());
       }
       if (week_radio.isSelected()){
           appointments_table.setItems(Appointment_Collections.getThisWeekAppointments());
       }
    }
    //appointment customer filter
    //schedule appointment
    //update appointment
    //cancel appointment



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
        try {
            appointments_table.setItems(Appointment_Collections.getAllAppointments());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //CustomersTables

        //Reports - 3
    }
}
