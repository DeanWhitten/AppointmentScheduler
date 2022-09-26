package deamwhitten.appointmentscheduler.Model;

/**
 * Contact Class Model.
 */
public class Contact {
    private int id;
    private String name;
    private String email;

	/**
	 * Instantiates a new Contact.
	 *
	 * @param id    the id of the contact
	 * @param name  the name of the contact
	 * @param email the email of the contact
	 */
	public Contact(int id, String name, String email){
        this.setId(id);
        this.setName(name);
        this.setEmail(email);
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
	 * Gets email.
	 *
	 * @return the email
	 */
	public String getEmail() {
        return email;
    }

	/**
	 * Sets email.
	 *
	 * @param email the email
	 */
	public void setEmail(String email) {
        this.email = email;
    }
}