package deamwhitten.appointmentscheduler.Utils.Collections;

import deamwhitten.appointmentscheduler.Model.Appointment;
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
}