package deamwhitten.appointmentscheduler.Controller;

import deamwhitten.appointmentscheduler.Model.Customer;
import deamwhitten.appointmentscheduler.Utils.Collections.Counties_Collections;
import deamwhitten.appointmentscheduler.Utils.Collections.Divisions_Collections;
import deamwhitten.appointmentscheduler.Utils.DataBase_Access.Customers_DA;
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
        error_label.setOpacity(0);
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

        country_selection.setItems(Counties_Collections.getAllCountiesNames());
    }
    @FXML
    public void onCountrySelected(ActionEvent event) {
        division_selection.getSelectionModel().clearSelection();
        division_selection.requestFocus();
        String selectedCounty = country_selection.getSelectionModel().getSelectedItem();
        if(selectedCounty != null){
            division_selection.getItems().addAll(Divisions_Collections.getSelectedDivisionNamesByCountryID(selectedCounty));
        }

    }
    
    @FXML
    public void onUpdateCustomerClick(ActionEvent event) throws IOException {
        Boolean isValidInput = validateInput();
        if(isValidInput){
            collectInputsAndSendToDA();
            Window_Handler.loadWindow("MainWindow_View","Appointment Scheduler", event);
        }
    }

    @FXML
    public void  onCancelUpdateCustomerClick(ActionEvent event) throws IOException {
        Window_Handler.loadWindow("MainWindow_View","Appointment Scheduler", event);
    }

    private Boolean validateInput() {
        if(!name_input.getText().isEmpty()){
            if(!phoneNumber_input.getText().isEmpty()){
                if(!address_input.getText().isEmpty()){
                    if(!postalCode_input.getText().isEmpty()){
                        if(!country_selection.getSelectionModel().getSelectedItem().isEmpty()){
                            if(!division_selection.getSelectionModel().getSelectedItem().isEmpty()){
                                return true;
                            }else {
                                division_selection.requestFocus();
                                error_label.setText("Please select a division");
                                error_label.setOpacity(1);
                                return false;
                            }
                        }else {
                            country_selection.requestFocus();
                            error_label.setText("Please select a country");
                            error_label.setOpacity(1);
                            return false;
                        }
                    }else {
                        postalCode_input.requestFocus();
                        error_label.setText("Please input a postal code");
                        error_label.setOpacity(1);
                        return false;
                    }
                }else{
                    address_input.requestFocus();
                    error_label.setText("Please input a address");
                    error_label.setOpacity(1);
                    return false;
                }
            }else{
                phoneNumber_input.requestFocus();
                error_label.setText("Please input a phone number");
                error_label.setOpacity(1);
                return false;
            }
        }else {
            name_input.requestFocus();
            error_label.setText("Please input a name");
            error_label.setOpacity(1);
            return false;
        }
    }

    private void collectInputsAndSendToDA() {
        int customerID = Integer.parseInt(customerID_input.getText());
        String name = name_input.getText();
        String address = address_input.getText();
        String postal = postalCode_input.getText();
        String phone = phoneNumber_input.getText();
        int divisionID = Divisions_Collections.findDivisionIdByName(division_selection.getSelectionModel().getSelectedItem());

        Customers_DA.updateCustomerDataToDB(customerID,name,address,postal,phone,divisionID);
    }
}