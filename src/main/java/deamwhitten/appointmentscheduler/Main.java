package deamwhitten.appointmentscheduler;

import deamwhitten.appointmentscheduler.Utils.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * TheMain.
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("View/SignIn_View.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setTitle("Sign In");
        stage.setScene(scene);
        stage.show();
    }

	/**
	 * The entry point of application.
	 * Sets up the Database connection.
	 *
	 * @param args the input arguments
	 */
	public static void main(String[] args) {
        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();
    }
}