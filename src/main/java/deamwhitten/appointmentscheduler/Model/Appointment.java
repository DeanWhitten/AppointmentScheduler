package deamwhitten.appointmentscheduler.Model;

import java.time.LocalDateTime;

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
     * Appointments constructor.
     *
     * */
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
     * Fields for the appointments class.
     * */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
}