package deamwhitten.appointmentscheduler.Model;

/**
 * Division Class Model.
 */
public class Division {
    private int id;
    private String name;
    private int countryID;

	/**
	 * Instantiates a new Division.
	 *
	 * @param id        the id of the division
	 * @param name      the name of the division
	 * @param countryID the country id division
	 */
	public Division(int id, String name, int countryID){
        this.setId(id);
        this.setName(name);
        this.setCountryID(countryID);
    }

	/**
	 * Gets id.
	 *
	 * @return the id
	 */
	public int getId() {
        return id;
    }

	/**
	 * Sets id.
	 *
	 * @param id the id
	 */
	public void setId(int id) {
        this.id = id;
    }

	/**
	 * Gets name.
	 *
	 * @return the name
	 */
	public String getName() {
        return name;
    }

	/**
	 * Sets name.
	 *
	 * @param name the name
	 */
	public void setName(String name) {
        this.name = name;
    }

	/**
	 * Gets country id.
	 *
	 * @return the country id
	 */
	public int getCountryID() {
        return countryID;
    }

	/**
	 * Sets country id.
	 *
	 * @param countryID the country id
	 */
	public void setCountryID(int countryID) {
        this.countryID = countryID;
    }
}