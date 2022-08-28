package deamwhitten.appointmentscheduler.Controller;

import deamwhitten.appointmentscheduler.Utils.DataBase_Access.SignIn_DA;
import deamwhitten.appointmentscheduler.Utils.CurrentSession_Handler;
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
        ResourceBundle user_language;
        Locale current = get_locale();
        user_language = ResourceBundle.getBundle("language", current);
        String userName = userID_input.getText();
        String password = password_input.getText();

        if(!userName.isEmpty() && !password.isEmpty()){
            Boolean isValid = SignIn_DA.validate(userName, password);
            if(isValid){
                CurrentSession_Handler.logActivity(userName, "Successful");
                Window_Handler.loadWindow("MainWindow_View","Appointment Scheduler", event);
            }else {
                CurrentSession_Handler.logActivity(userName, "Failed");
                error_label.setText(user_language.getString("WrongInputs"));
                error_label.setOpacity(1);
            }
        }else {
           error_label.setText(user_language.getString("MissingInputs"));
           error_label.setOpacity(1);
        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ResourceBundle user_language;
        Locale current = get_locale();
        user_language = ResourceBundle.getBundle("language", current);

        title_label.setText(user_language.getString("Welcome"));
        userID_label.setText(user_language.getString("UserID"));
        password_label.setText(user_language.getString("Password"));
        signIn_btn.setText(user_language.getString("SignIn"));
        String locationText = user_language.getString("Location");
        String languageText = user_language.getString("Language");
        location_label.setText(locationText + " " + Locale.getDefault().getDisplayCountry() +"\t" +
                "\t"+ languageText + " " + Locale.getDefault().getDisplayLanguage());

    }
    public static Locale get_locale()
    {
        System.out.println("local Default: " + Locale.getDefault());
        return Locale.getDefault();
    }
}