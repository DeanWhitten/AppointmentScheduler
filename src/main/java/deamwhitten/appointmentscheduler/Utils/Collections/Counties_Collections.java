package deamwhitten.appointmentscheduler.Utils.Collections;

import deamwhitten.appointmentscheduler.Utils.DataBase_Access.Country_DA;
import deamwhitten.appointmentscheduler.Model.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Counties_Collections {
    //All countries
    public static ObservableList<Country> allCountries = FXCollections.observableArrayList();
    public static ObservableList<Country> getAllCountries(){
        allCountries.clear();
        allCountries.addAll(Country_DA.getAllCountriesData());
        return allCountries;
    }

    public static ObservableList<String> allCountiesNames = FXCollections.observableArrayList();
    public static ObservableList<String> getAllCountiesNames(){
        allCountiesNames.clear();
        getAllCountries();
        for(Country country : allCountries){
            allCountiesNames.add(country.getName());
        }
        return allCountiesNames;
    }

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