package deamwhitten.appointmentscheduler.Controller;

import deamwhitten.appointmentscheduler.Model.Customer;
import deamwhitten.appointmentscheduler.Utils.Collections.Counties_Collections;
import deamwhitten.appointmentscheduler.Utils.Collections.Divisions_Collections;
import deamwhitten.appointmentscheduler.Utils.Window_Handler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Update_Customer_Controller implements Initializable {
    @FXML
    private TextField customerID_input;
    @FXML
    private TextField  name_input;
    @FXML
    private TextField phoneNumber_input;
    @FXML
    private TextField address_input;
    @FXML
    private TextField postalCode_input;
    @FXML
    private ComboBox<String> country_selection;
    @FXML
    private ComboBox<String> division_selection;
    @FXML
    private Label error_label;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Customer selectedCustomer =MainWindow_Controller.selectedCustomer;
        String divisionName =
                Divisions_Collections.findDivisionNameById(selectedCustomer.getDivisionID());
        int countryID =
                Divisions_Collections.findDivionCountryIdById(selectedCustomer.getDivisionID());

        customerID_input.setText(String.valueOf(selectedCustomer.getId()));
        name_input.setText(selectedCustomer.getName());
        phoneNumber_input.setText(selectedCustomer.getPhone());
        address_input.setText(selectedCustomer.getAddress());
        postalCode_input.setText(selectedCustomer.getPostalCode());
        country_selection.getSelectionModel().select(Counties_Collections.findCountryNameById(countryID));
        division_selection.getSelectionModel().select(divisionName);

    }

    @FXML
    public void onUpdateCustomerClick(ActionEvent event) throws IOException {
        Boolean isValidInput = validateInput();
        if(isValidInput){
            Window_Handler.loadWindow("MainWindow_View","Appointment Scheduler", event);
        } else {
            getErrorMsg();
        }
    }

    @FXML
    public void  onCancelUpdateCustomerClick(ActionEvent event) throws IOException {
        Window_Handler.loadWindow("MainWindow_View","Appointment Scheduler", event);
    }

    private void getErrorMsg() {
    }

    private Boolean validateInput() {
        return true;
    }
}