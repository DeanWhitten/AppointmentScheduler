package deamwhitten.appointmentscheduler.Utils;

import deamwhitten.appointmentscheduler.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public abstract class Window_Handler {
    public static void loadWindow(String page, String title, ActionEvent event)throws IOException {
        page =  page + ".fxml";
        Parent parent = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("View/" + page)));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }
}