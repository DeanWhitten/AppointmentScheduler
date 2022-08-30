package deamwhitten.appointmentscheduler.Utils.Collections;

import deamwhitten.appointmentscheduler.Utils.DataBase_Access.Division_DA;
import deamwhitten.appointmentscheduler.Model.Country;
import deamwhitten.appointmentscheduler.Model.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Divisions_Collections {
    //All divisions
    public static ObservableList<Division> allDivisions = FXCollections.observableArrayList();
    public static ObservableList<Division> getAllDivisions(){
        allDivisions.clear();
        allDivisions.addAll(Division_DA.getAllDivisionsData());
        return allDivisions;
    }

    //Divisions Names by Country ID
    public static ObservableList<String> selectedDivisionNamesByCountryID =
            FXCollections.observableArrayList();
    public static ObservableList<String> getSelectedDivisionNamesByCountryID(String name){
        selectedDivisionNamesByCountryID.clear();
        getAllDivisions();
        ObservableList<Country> allCountries = FXCollections.observableArrayList(Counties_Collections.getAllCountries());

        Country selectedCountry = null;
        for (Country country: allCountries){
              if(country.getName().equals(name)){
                  selectedCountry = country;
              }
        }
        for (Division division: allDivisions){
            assert selectedCountry != null;
            if(selectedCountry.getId() == division.getCountryID()){
                 selectedDivisionNamesByCountryID.add(division.getName());
            }
        }
        return selectedDivisionNamesByCountryID;
    }


    public static String findDivisionNameById(int id){
        String divisionName = "";
        getAllDivisions();
        for(Division div : allDivisions){
            if(div.getId() == id){
                divisionName = div.getName();
            }
        }
        return divisionName;
    }

    public static int findDivionCountryIdById(int id){
        int countryID = 0;
        getAllDivisions();
        for(Division div: allDivisions){
            if (div.getId() == id){
                countryID = div.getCountryID();
            }
        }
        return countryID;
    }

    public static int findDivisionIdByName(String name){
        int divisionID = 0;
        getAllDivisions();
        for (Division div : allDivisions){
            if(div.getName().equals(name)){
                divisionID = div.getId();
            }
        }
        return divisionID;
    }
}