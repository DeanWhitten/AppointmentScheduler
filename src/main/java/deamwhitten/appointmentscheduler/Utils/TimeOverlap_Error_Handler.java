package deamwhitten.appointmentscheduler.Utils;

import deamwhitten.appointmentscheduler.Model.Appointment;
import javafx.scene.control.Label;

import java.time.LocalDateTime;

import static deamwhitten.appointmentscheduler.Controller.Update_Appointment_Controller.error_label;
import static deamwhitten.appointmentscheduler.Utils.Collections.Appointments_Collections.getAllAppointments;

public class TimeOverlap_Error_Handler {




	public static Boolean checkForAppointmentOverlapByCustomer_Add(LocalDateTime Start,
																   LocalDateTime End,
																   int customerID, Label error_label) {
		for (Appointment a : getAllAppointments()) {
			if (a.getCustomerId() != customerID) {
				continue;
			}
			if ((Start.isAfter(a.getStart()) || Start.isEqual(a.getStart())) && (Start.isBefore(a.getEnd()))) { //1
				error_label.setText("Start time overlaps a current appointment.");
				error_label.setOpacity(1);
				return true;
			}
			if ((End.isAfter(a.getStart())) && (End.isBefore(a.getEnd()) || End.isEqual(a.getEnd()))) { //2
				error_label.setText("End time overlaps a current appointment.");
				error_label.setOpacity(1);
				return true;
			}
			if ((Start.isBefore(a.getStart()) || Start.isEqual(a.getStart())) && (End.isAfter(a.getEnd()) || End.isEqual(a.getEnd()))) { //3
				error_label.setText("Start and end times overlap a current appointment.");
				error_label.setOpacity(1);
				return true;
			}
		}
		return false;
	}

	public static boolean checkForAppointmentOverlapByCustomer_Modify(LocalDateTime Start,
																	  LocalDateTime End,
																	  int customerID,
																	  int appointmentID) {
		Label errorLabel = error_label;
		for (Appointment a : getAllAppointments()) {
			if (a.getCustomerId() != customerID || a.getId() == appointmentID) {
				continue;
			}
			if ((Start.isAfter(a.getStart()) || Start.isEqual(a.getStart())) && (Start.isBefore(a.getEnd()))) { //1
				errorLabel.setText("Start time overlaps a current appointment.");
				error_label.setOpacity(1);
			}
			if ((End.isAfter(a.getStart())) && (End.isBefore(a.getEnd()) || End.isEqual(a.getEnd()))) { //2
				errorLabel.setText("End time overlaps a current appointment.");
				error_label.setOpacity(1);
				return true;
			}
			if ((Start.isBefore(a.getStart()) || Start.isEqual(a.getStart())) && (End.isAfter(a.getEnd()) || End.isEqual(a.getEnd()))) { //3
				errorLabel.setText("Start and end times overlap a current appointment.");
				error_label.setOpacity(1);
				return true;
			}
		}
		return false;
	}
}