package deamwhitten.appointmentscheduler.Model;

/**
 * Customer Class Model.
 */
public class Customer {
    private int id;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private int divisionID;

	/**
	 * Instantiates a new Customer.
	 *
	 * @param id         the id of the customer
	 * @param name       the name of the customer
	 * @param address    the address of the customer
	 * @param postalCode the postal code of the customer
	 * @param phone      the phone number of the customer
	 * @param divisionID the division id of the customer's address
	 */
	public Customer(int id, String name, String address, String postalCode, String phone, int divisionID){
        this.setId(id);
        this.setName(name);
        this.setAddress(address);
        this.setPostalCode(postalCode);
        this.setPhone(phone);
        this.setDivisionID(divisionID);
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
	 * Gets address.
	 *
	 * @return the address
	 */
	public String getAddress() {
        return address;
    }

	/**
	 * Sets address.
	 *
	 * @param address the address
	 */
	public void setAddress(String address) {
        this.address = address;
    }

	/**
	 * Gets postal code.
	 *
	 * @return the postal code
	 */
	public String getPostalCode() {
        return postalCode;
    }

	/**
	 * Sets postal code.
	 *
	 * @param postalCode the postal code
	 */
	public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

	/**
	 * Gets phone.
	 *
	 * @return the phone
	 */
	public String getPhone() {
        return phone;
    }

	/**
	 * Sets phone.
	 *
	 * @param phone the phone
	 */
	public void setPhone(String phone) {
        this.phone = phone;
    }

	/**
	 * Gets division id.
	 *
	 * @return the division id
	 */
	public int getDivisionID() {
        return divisionID;
    }

	/**
	 * Sets division id.
	 *
	 * @param divisionID the division id
	 */
	public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }
}