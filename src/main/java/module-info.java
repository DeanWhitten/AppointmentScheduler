module deamwhitten.appointmentscheduler {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens deamwhitten.appointmentscheduler to javafx.fxml;
    opens deamwhitten.appointmentscheduler.Controller to javafx.fxml;
    opens deamwhitten.appointmentscheduler.Model to javafx.fxml;
    opens deamwhitten.appointmentscheduler.Utils to javafx.fxml;
    opens deamwhitten.appointmentscheduler.Utils.DataBase_Access to javafx.fxml;



    exports deamwhitten.appointmentscheduler;
    exports deamwhitten.appointmentscheduler.Controller;
    exports deamwhitten.appointmentscheduler.Model;
    exports deamwhitten.appointmentscheduler.Utils.DataBase_Access;


}