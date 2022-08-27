package deamwhitten.appointmentscheduler.Utils;

import deamwhitten.appointmentscheduler.Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class CurrentSession_Handler {
    private static ObservableList<User> activeUser = FXCollections.observableArrayList();

    public static void assignSessionUser(User userLoggedIn) {
        activeUser.clear();
        activeUser.add(userLoggedIn);

    }

    public static void logActivity(String userName, String loginResult) {
        try {
            File myObj = new File("src/main/resources/login_activity.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                try {
                    FileWriter myWriter = new FileWriter("src/main/resources/login_activity.txt");
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

    public static String getUserName(){
        String userName = null;
        for(User user : activeUser){
            userName = user.getUserName();
        }
        return userName;
    }

    public static int getUserID(){
        int userID = 0;
        for(User user : activeUser){
            userID = user.getUserID();
        }
        return userID;
    }
}