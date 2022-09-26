package deamwhitten.appointmentscheduler.Model;

import java.time.LocalDateTime;

/**
 * Appointment Class Model.
 */
public class Appointment {
    private int id;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private int customerId;
    private int userId;
    private int contactId;

	/**
	 * Instantiates a new Appointment.
	 *
	 * @param appointmentID          the appointment id
	 * @param appointmentTitle       the appointment title
	 * @param appointmentDescription the appointment description
	 * @param appointmentLocation    the appointment location
	 * @param appointmentType        the appointment type
	 * @param start                  the start time of the appointment
	 * @param end                    the end time of the appointment
	 * @param customerId             the customer id for the appointment
	 * @param userId                 the user id for the appointment
	 * @param contactId              the contact id for the appointment
	 */
	public Appointment(int appointmentID, String appointmentTitle, String appointmentDescription,
                       String appointmentLocation, String appointmentType, LocalDateTime start, LocalDateTime end, int customerId,
                       int userId, int contactId) {

        this.setId(appointmentID);
        this.setTitle(appointmentTitle);
        this.setDescription(appointmentDescription);
        this.setLocation(appointmentLocation);
        this.setType(appointmentType);
        this.setStart(start);
        this.setEnd(end);
        this.setCustomerId(customerId);
        this.setUserId(userId);
        this.setContactId(contactId);
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
	 * Gets title.
	 *
	 * @return the title
	 */
	public String getTitle() {
        return title;
    }

	/**
	 * Sets title.
	 *
	 * @param title the title
	 */
	public void setTitle(String title) {
        this.title = title;
    }

	/**
	 * Gets description.
	 *
	 * @return the description
	 */
	public String getDescription() {
        return description;
    }

	/**
	 * Sets description.
	 *
	 * @param description the description
	 */
	public void setDescription(String description) {
        this.description = description;
    }

	/**
	 * Gets location.
	 *
	 * @return the location
	 */
	public String getLocation() {
        return location;
    }

	/**
	 * Sets location.
	 *
	 * @param location the location
	 */
	public void setLocation(String location) {
        this.location = location;
    }

	/**
	 * Gets type.
	 *
	 * @return the type
	 */
	public String getType() {
        return type;
    }

	/**
	 * Sets type.
	 *
	 * @param type the type
	 */
	public void setType(String type) {
        this.type = type;
    }

	/**
	 * Gets start.
	 *
	 * @return the start
	 */
	public LocalDateTime getStart() {
        return start;
    }

	/**
	 * Sets start.
	 *
	 * @param start the start
	 */
	public void setStart(LocalDateTime start) {
        this.start = start;
    }

	/**
	 * Gets end.
	 *
	 * @return the end
	 */
	public LocalDateTime getEnd() {
        return end;
    }

	/**
	 * Sets end.
	 *
	 * @param end the end
	 */
	public void setEnd(LocalDateTime end) {
        this.end = end;
    }

	/**
	 * Gets customer id.
	 *
	 * @return the customer id
	 */
	public int getCustomerId() {
        return customerId;
    }

	/**
	 * Sets customer id.
	 *
	 * @param customerId the customer id
	 */
	public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

	/**
	 * Gets user id.
	 *
	 * @return the user id
	 */
	public int getUserId() {
        return userId;
    }

	/**
	 * Sets user id.
	 *
	 * @param userId the user id
	 */
	public void setUserId(int userId) {
        this.userId = userId;
    }

	/**
	 * Gets contact id.
	 *
	 * @return the contact id
	 */
	public int getContactId() {
        return contactId;
    }

	/**
	 * Sets contact id.
	 *
	 * @param contactId the contact id
	 */
	public void setContactId(int contactId) {
        this.contactId = contactId;
    }
}