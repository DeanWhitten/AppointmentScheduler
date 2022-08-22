package deamwhitten.appointmentscheduler.Utils;

import deamwhitten.appointmentscheduler.Controller.SignIn_Controller;

public abstract class ErrorMSG_Handler {


    public static void showLoginError(String msg) {
        SignIn_Controller.error_label.setText(msg);
        SignIn_Controller.error_label.setOpacity(1);
    }
}
