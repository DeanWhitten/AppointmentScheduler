package deamwhitten.appointmentscheduler.Utils.Database_Access;
import deamwhitten.appointmentscheduler.Model.User;
import deamwhitten.appointmentscheduler.Utils.CurrentSession_properties;
import deamwhitten.appointmentscheduler.Utils.JDBC;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * The Sign in data access.
 */
public abstract class SignIn_DA {

	/**
	 * Validate user credentials.
	 *
	 * @param userName the username inputted
	 * @param password the password  inputted
	 * @return boolean of whether it is valid credentials
	 */
	public static Boolean validate(String userName, String password) {
        try {
            String sqlQuery = "SELECT * FROM users ";
            PreparedStatement ps = JDBC.connection.prepareStatement(sqlQuery);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                if (rs.getString("User_Name").equals(userName)) {
                    if (rs.getString("Password").equals(password)) {
                        int id =  rs.getInt("User_ID");
                        String name = rs.getString("User_Name");
                        User loggedUser = new User(id, name);
                        CurrentSession_properties.assignSessionUser(loggedUser);
                        return true;
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }

        return false;
    }
}