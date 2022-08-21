package deamwhitten.appointmentscheduler.Controller;

import deamwhitten.appointmentscheduler.Utils.DataBase_Access.SignIn_DA;
import deamwhitten.appointmentscheduler.Utils.Window_Handler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class SignIn_Controller implements Initializable {
    @FXML
    private Label title_label;
    @FXML
    private Label userID_label;
    @FXML
    private Label password_label;
    @FXML
    private TextField userID_input;
    @FXML
    private TextField password_input;
    @FXML
    private Label error_label;
    @FXML
    private Button signIn_btn;
    @FXML
    private Label location_label;

    @FXML
    public void onSignInClick(ActionEvent event) throws IOException{
        String userID = userID_input.getText();
        String password = password_input.getText();

        if(!userID.isEmpty() && !password.isEmpty()){
            Boolean isValid = SignIn_DA.validate(userID, password);
            if(isValid){
                Window_Handler.loadWindow("MainWindow_View_1","Appointment Scheduler", event);
            }else {
                showLoginError("Incorrect user name or password.");
            }
        }else {
            showLoginError("Please input user name and password to login.");
        }
    }

    private void showLoginError(String msg) {
        error_label.setText(msg);
        error_label.setOpacity(1);
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
       location_label.setText("Location: " + Locale.getDefault().getDisplayCountry() +"\t\tLanguage: " + Locale.getDefault().getDisplayLanguage());
    }
}