package deamwhitten.appointmentscheduler.Controller;

import deamwhitten.appointmentscheduler.Utils.Collections.Appointment_Collections;
import deamwhitten.appointmentscheduler.Utils.Collections.Customer_Collections;
import deamwhitten.appointmentscheduler.Utils.Window_Handler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Update_Appointment_Controller implements Initializable {
    @FXML
    private TextField appID_input;
    @FXML
    private ComboBox<String> customer_selection;
    @FXML
    private ComboBox<String> contact_selection;
    @FXML
    private TextField type_input;
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
        try {
            appID_input.setText("Auto Generated ID: ");
            customer_selection.getItems().addAll(Customer_Collections.getAllCustomersNames());

        } catch (Exception e) {
            throw new RuntimeException(e);
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
            } else {
               getErrorMsg();
            }
    }

    private Boolean validateInput() {
        return true;
    }

    private void getErrorMsg() {
    }

}