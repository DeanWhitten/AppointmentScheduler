package deamwhitten.appointmentscheduler.Utils.DataBase_Access;
import deamwhitten.appointmentscheduler.Utils.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class SignIn_DA {
    public static Boolean validate(String userID, String password) {
        try {
            String sqlQuery = "SELECT * FROM users ";
            PreparedStatement ps = JDBC.connection.prepareStatement(sqlQuery);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                if (rs.getString("User_Name").equals(userID)) {
                    if (rs.getString("Password").equals(password)) {
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
