package deamwhitten.appointmentscheduler.Model;

/**
 * User Class Model.
 */
public class User {
    private int userID;
    private String userName;

	/**
	 * Instantiates a new User.
	 *
	 * @param id   the id of the user
	 * @param name the name of the user
	 */
	public User(int id, String name){
        this.userID = id;
        this.userName =name;
    }

	/**
	 * Gets user id.
	 *
	 * @return the user id
	 */
	public int getUserID() {
        return userID;
    }

	/**
	 * Sets user id.
	 *
	 * @param userID the user id
	 */
	public void setUserID(int userID) {
        this.userID = userID;
    }

	/**
	 * Gets user name.
	 *
	 * @return the user name
	 */
	public String getUserName() {
        return userName;
    }

	/**
	 * Sets user name.
	 *
	 * @param userName the user name
	 */
	public void setUserName(String userName) {
        this.userName = userName;
    }
}