package deamwhitten.appointmentscheduler.Utils;

import deamwhitten.appointmentscheduler.Model.*;
import deamwhitten.appointmentscheduler.Utils.Collections.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.time.Month;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

/**
 * The Reports handler.
 */
public abstract class Reports_Handler {

	/**
	 * Get the number of apps per month and type.
	 *
	 * @return a formatted string of the report results
	 */
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
		return reportText;
	}

	/**
	 * Gets contact schedule by contact id report.
	 * lambda to look through all the appointments and print a line of data for each appointment
	 * encountered that is scheduled for a selected contact
	 *
	 * @param contactSelected the contact selected
	 * @return a formatted string of the report results
	 */
	public static String getContactScheduleByContactID_Report(String contactSelected) {
		AtomicReference<String> reportText = new AtomicReference<>("");
		Contact contact = Contacts_Collections.getAllContacts().get(Contacts_Collections.getContactIdByName(contactSelected)-1);
		String contactName = contact.getName();

		reportText.set(reportText + "Customer ID: " + contact.getId() + "\t\t Contact Name: " + contactName + "\t" + "\t" + "Email: " + contact.getEmail() + "\n\n");
		reportText.set(reportText + "appointment ID\t/\ttitle\t/\ttype and description\t/\tstart date and time\t/\tend date and time\t/\tcustomer ID\n");
		reportText.set(reportText + "__________________________________________________________________________________________________________________________________\n");

		//lambda to look through all the appointments and print a line of data for each appointment encountered that is
		//scheduled for a selected contact
		Appointments_Collections.getAllAppointments().forEach(app ->{
			if(app.getContactId() == contact.getId()){
				reportText.set(reportText.get() + app.getId() + "\t / \t" +
						app.getTitle() + "\t / \t" +
						app.getType() + "\t / \t" +
						app.getDescription() + "\t / \t" +
						app.getStart() + "\t / \t" +
						app.getEnd() + "\t / \t" +
						app.getCustomerId() + "\n");
			}
		});

		return reportText.get();
	}


	/**
	 * Get num of customers per location.
	 *
	 * @return a formatted string of the report results
	 */
	public static String getNumOfCustomersPerLocation(){
		String reportText = "";

		for(Country c: Counties_Collections.getAllCountries()){
			reportText = reportText + "--------------------\n" +"\t" + c.getName() + "\n" +
				"--------------------\n";

			for(Division d : Divisions_Collections.getAllDivisions()){
				ObservableList<Customer> customerInCurrent = FXCollections.observableArrayList();
				for(Customer customer : Customers_Collections.getAllCustomers()){
					if(customer.getDivisionID() == d.getId() && d.getCountryID() == c.getId()){
						customerInCurrent.add(customer);
					}
				}

				if(!customerInCurrent.isEmpty()){
					reportText = reportText + d.getName() + ": ";
					reportText= reportText + "(" + customerInCurrent.size() + ") \t\t";
				}
			}
			reportText = reportText + "\n\n";
		}

		return reportText;
	}
}