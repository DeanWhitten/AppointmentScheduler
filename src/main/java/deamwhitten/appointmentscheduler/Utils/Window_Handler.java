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

/**
 * Window handler.
 *
 * used to simplify and keep the controllers clean by handling the windows.
 */
public abstract class Window_Handler {
	/**
	 * Loads windows.
	 *
	 * @param page  the page's name to be loaded
	 * @param title the title for the page being loaded
	 * @param event the event
	 * @throws IOException the io exception
	 */
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