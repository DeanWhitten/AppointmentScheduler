package deamwhitten.appointmentscheduler.Model;

/**
 * Country Class Model.
 */
public class Country {
    private  int id;
    private String name;

	/**
	 * Instantiates a new Country.
	 *
	 * @param id   the id of the country
	 * @param name the name of the country
	 */
	public Country(int id, String name){
        this.setId(id);
        this.setName(name);
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
}