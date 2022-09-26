package deamwhitten.appointmentscheduler.Controller;

import deamwhitten.appointmentscheduler.Model.Appointment;
import deamwhitten.appointmentscheduler.Utils.Collections.Appointments_Collections;
import deamwhitten.appointmentscheduler.Utils.CurrentSession_properties;
import deamwhitten.appointmentscheduler.Utils.DataBase_Access.SignIn_DA;
import deamwhitten.appointmentscheduler.Utils.Window_Handler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Sign in controller.
 */
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

	/**
	 * On sign in click.
	 * Checks if the username and password fields are filled in then checks if the inputs match
     * valid credentials in the database. If the inputs match then the user data is logged for
     * the session and the user is taken to the main window if not then a UI error is displayed
     * prompting the user to check their inputs. Activities are also logged regardless of the
     * validity of the inputs. Once the user successfully logs in appointments are checked to see
     * if the user has any appointments coming up in the next 15 minutes.
     *
	 * @param event the event of clicking sign in
	 * @throws IOException the io exception
	 */
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
                checkForUpcomingApp();
                CurrentSession_properties.logActivity(userName, "Successful");
                Window_Handler.loadWindow("MainWindow_View","Appointment Scheduler", event);
            }else {
                CurrentSession_properties.logActivity(userName, "Failed");
                error_label.setText(user_language.getString("WrongInputs"));
                error_label.setOpacity(1);
            }
        }else {
           error_label.setText(user_language.getString("MissingInputs"));
           error_label.setOpacity(1);
        }
    }

    /**
     * Checks for upcoming appointments for user logged in.
     * An alert message is displayed showing whether there is an upcoming appointment in the
     * next 15 minutes for the user or not. A lambda is used to find if an appointment is within
     * the next 15 minutes.
     */
    private void checkForUpcomingApp() {
        ObservableList<Appointment> AppList = FXCollections.observableArrayList(Appointments_Collections.getAllAppointments());

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime appPlus15 = now.plusMinutes(15);

        // lambda to find if an appointment within 15 minutes
        FilteredList<Appointment> filteredData = new FilteredList<>(AppList);
        filteredData.setPredicate(row -> {
            LocalDateTime rowDate = row.getStart();
            return rowDate.isAfter(now.minusMinutes(1)) && rowDate.isBefore(appPlus15);
        });

        if (! filteredData.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Reminder: You have an appointment starting within the next 15 minutes!\n\t" +
                    "Appointment ID: " + filteredData.get(0).getId() +
                    "\n\tAppointment Date: " + filteredData.get(0).getStart() +
                    "\n\tAppointment Time: " + filteredData.get(0).getStart());
            Optional<ButtonType> result = alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("You have no coming up appointments\nwithin the next 15 " +
                    "minutes");
            Optional<ButtonType> result = alert.showAndWait();
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

	/**
	 * Gets locale data.
	 *
	 * @return the locale data form the users pc
	 */
	public static Locale get_locale()
    {
        return Locale.getDefault();
    }
}