package deamwhitten.appointmentscheduler.Utils.Database_Access;

import deamwhitten.appointmentscheduler.Model.Division;
import deamwhitten.appointmentscheduler.Utils.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The Division data access.
 */
public abstract class Division_DA {

	/**
	 * Gets all divisions data.
	 *
	 * @return all divisions data as a list
	 */
	public static ObservableList<Division> getAllDivisionsData() {
        ObservableList<Division> allDivisions = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM first_level_divisions";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Division_ID");
                String name = rs.getString("Division");
                int countryID = rs.getInt("Country_ID");

                Division divisionResult = new Division(id, name, countryID);
                allDivisions.add(divisionResult);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allDivisions;
    }


}