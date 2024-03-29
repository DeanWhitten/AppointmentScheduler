package deamwhitten.appointmentscheduler.Utils.Database_Access;

import deamwhitten.appointmentscheduler.Model.Country;
import deamwhitten.appointmentscheduler.Utils.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The Country data access.
 */
public abstract class Country_DA {

	/**
	 * Gets all countries data.
	 *
	 * @return all countries data in a list
	 */
	public static ObservableList<Country> getAllCountriesData() {
        ObservableList<Country> allCountries = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM countries";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Country_ID");
                String name = rs.getString("Country");

                Country countryResult = new Country(id, name);
                allCountries.add(countryResult);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allCountries;
    }
}