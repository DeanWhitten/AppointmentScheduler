package deamwhitten.appointmentscheduler.Utils.Collections;

import deamwhitten.appointmentscheduler.Utils.DataBase_Access.Division_DA;
import deamwhitten.appointmentscheduler.Model.Country;
import deamwhitten.appointmentscheduler.Model.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The type Divisions collections.
 */
public abstract class Divisions_Collections {

	/**
	 * Get all divisions observable list.
	 *
	 * @return the observable list
	 */
	public static ObservableList<Division> getAllDivisions(){
        ObservableList<Division> allDivisions = FXCollections.observableArrayList();
        allDivisions.clear();
        allDivisions.addAll(Division_DA.getAllDivisionsData());
        return allDivisions;
    }


	/**
	 * Get selected division names by country id observable list.
	 *
	 * @param name the name
	 * @return the observable list
	 */
	public static ObservableList<String> getSelectedDivisionNamesByCountryID(String name){
        ObservableList<String> selectedDivisionNamesByCountryID =
                FXCollections.observableArrayList();
        selectedDivisionNamesByCountryID.clear();
        getAllDivisions();
        ObservableList<Country> allCountries = FXCollections.observableArrayList(Counties_Collections.getAllCountries());

        Country selectedCountry = null;
        for (Country country: allCountries){
              if(country.getName().equals(name)){
                  selectedCountry = country;
              }
        }
        for (Division division: getAllDivisions()){
            assert selectedCountry != null;
            if(selectedCountry.getId() == division.getCountryID()){
                 selectedDivisionNamesByCountryID.add(division.getName());
            }
        }
        return selectedDivisionNamesByCountryID;
    }

	/**
	 * Find division name by id string.
	 *
	 * @param id the id
	 * @return the string
	 */
	public static String findDivisionNameById(int id){
        String divisionName = "";
        getAllDivisions();
        for(Division div : getAllDivisions()){
            if(div.getId() == id){
                divisionName = div.getName();
            }
        }
        return divisionName;
    }

	/**
	 * Find division country id by id int.
	 *
	 * @param id the id
	 * @return the int
	 */
	public static int findDivisionCountryIdById(int id){
        int countryID = 0;
        getAllDivisions();
        for(Division div: getAllDivisions()){
            if (div.getId() == id){
                countryID = div.getCountryID();
            }
        }
        return countryID;
    }

	/**
	 * Find division id by name int.
	 *
	 * @param name the name
	 * @return the int
	 */
	public static int findDivisionIdByName(String name){
        int divisionID = 0;
        getAllDivisions();
        for (Division div : getAllDivisions()){
            if(div.getName().equals(name)){
                divisionID = div.getId();
            }
        }
        return divisionID;
    }
}