package deamwhitten.appointmentscheduler.Utils;

import deamwhitten.appointmentscheduler.Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * The Current session properties.
 */
public class CurrentSession_properties {
    private static ObservableList<User> activeUser = FXCollections.observableArrayList();

	/**
	 * Assign session user.
	 *
	 * @param userLoggedIn the user logged in
	 */
	public static void assignSessionUser(User userLoggedIn) {
        activeUser.clear();
        activeUser.add(userLoggedIn);

    }

	/**
	 * Activity Logger
	 *
	 * @param userName    the username entered
	 * @param loginResult the login result
	 */
	public static void logActivity(String userName, String loginResult) {
        try {
            File myObj = new File("login_activity.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                try {
                    FileWriter myWriter = new FileWriter("login_activity.txt",
                            true);
                    myWriter.write( userName + " --- " + LocalDate.now() + " --- " + LocalTime.now() + " --- " + loginResult + "\n");
                    myWriter.close();
                    System.out.println("Successfully logged user activity.");
                } catch (IOException e) {
                    System.out.println("An error occurred logging user activity.");
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred logging user activity.");
            e.printStackTrace();
        }
    }

	/**
	 * Get current logged-in user's name.
	 *
	 * @return a string of the user's name
	 */
	public static String getUserName(){
        String userName = null;
        for(User user : activeUser){
            userName = user.getUserName();
        }
        return userName;
    }

	/**
	 * Get logged in user's ID
	 *
	 * @return the int of the user's ID
	 */
	public static int getUserID(){
        int userID = 0;
        for(User user : activeUser){
            userID = user.getUserID();
        }
        return userID;
    }
}