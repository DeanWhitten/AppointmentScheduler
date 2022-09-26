package deamwhitten.appointmentscheduler.Utils.Collections;

import deamwhitten.appointmentscheduler.Utils.DataBase_Access.Country_DA;
import deamwhitten.appointmentscheduler.Model.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The type Counties collections.
 */
public abstract class Counties_Collections {

	/**
	 * Get all countries observable list.
	 *
	 * @return the observable list
	 */
	public static ObservableList<Country> getAllCountries(){
        ObservableList<Country> allCountries = FXCollections.observableArrayList();
        allCountries.clear();
        allCountries.addAll(Country_DA.getAllCountriesData());
        return allCountries;
    }

	/**
	 * Get all counties names observable list.
	 *
	 * @return the observable list
	 */
	public static ObservableList<String> getAllCountiesNames(){
        ObservableList<String> allCountiesNames = FXCollections.observableArrayList();
        allCountiesNames.clear();
        getAllCountries();
        for(Country country : getAllCountries()){
            allCountiesNames.add(country.getName());
        }
        return allCountiesNames;
    }

	/**
	 * Find country name by id string.
	 *
	 * @param id the id
	 * @return the string
	 */
	public static String findCountryNameById(int id){
      String countryName = null;
      getAllCountries();
      for (Country country : getAllCountries()){
          if(country.getId() == id){
              countryName = country.getName();
          }
      }
      return countryName;
    }
}