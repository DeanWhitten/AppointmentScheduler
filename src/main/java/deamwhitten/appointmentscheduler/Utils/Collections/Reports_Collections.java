package deamwhitten.appointmentscheduler.Utils.Collections;

import deamwhitten.appointmentscheduler.Model.Appointment;
import deamwhitten.appointmentscheduler.Model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.time.Month;
import java.util.HashMap;

public abstract class Reports_Collections {

	public static String getNumOfAppsPerMonthAndType(){
		String reportText = "";

		for(Month m : Month.values()){
			reportText = reportText + m.name() + ": ";

			ObservableList<Appointment> appInCurrent = FXCollections.observableArrayList();
			for(Appointment app : Appointments_Collections.getAllAppointments()){
				if(app.getStart().getMonthValue() == m.getValue()){
					appInCurrent.add(app);
				}
			}

			if(appInCurrent.isEmpty()){
				reportText = reportText + "(0)" + " \n\n";
			}else {
				ObservableMap<String, Integer> typeMap = FXCollections.observableMap(new HashMap<>());
				for(Appointment app: appInCurrent){
					if(typeMap.containsKey(app.getType())){
						typeMap.put(app.getType(), typeMap.get(app.getType()) + 1 );
					}else {
						typeMap.put(app.getType(), 1);
					}
				}

				reportText = reportText +"(" +appInCurrent.size() + ")"+ "  " + typeMap.toString()
						.replace(",", "\t")
						.replace("{", "")
						.replace("}", "")
						.replace("=", ": ")
						+ "\n\n";
			}
		}
		System.out.println(reportText);

		return reportText;
	}


	public static String getContactScheduleByContactID_Report(int contactSelected) {
		String reportText = "";
		Contact contact = Contacts_Collections.getAllContacts().get(contactSelected);
		String contactName = contact.getName();

		ObservableList<Appointment> appsOfContact = FXCollections.observableArrayList();
		for(Appointment app: Appointments_Collections.getAllAppointments()){
			if(app.getContactId() == contactSelected){
				 appsOfContact.add(app);
			}
		}

		reportText = reportText + "Customer ID: " + contactSelected + "\t\t Contact Name:" + contactName + "\t\t" + "Email:" + contact.getEmail() + "\n\n";
		reportText = reportText + "appointment ID title\t/\ttype and description\t/\tstart date and time\t/\tend date and time\t/\tcustomer ID\n";
		reportText = reportText + "__________________________________________________________________________________________________________________________________\n";
		for(Appointment app : appsOfContact){
			reportText =
					reportText + app.getId() + "\t / \t"+
							app.getTitle() + "\t / \t"+
							app.getType() + "\t / \t"+
							app.getDescription() + "\t / \t"+
							app.getStart() + "\t / \t"+
							app.getEnd() + "\t / \t"+
							app.getCustomerId() + "\n";
		}

		return  reportText;
	}
}