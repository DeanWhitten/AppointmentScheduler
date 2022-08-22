package deamwhitten.appointmentscheduler.Model;

import java.util.Calendar;

public class Appointment {
    private int id;
    private String title;
    private String description;
    private String location;
    private String type;
    private String start;
    private String end;
    private Calendar createDate;
    private String createdBy;
    private Calendar lastUpdate;
    private String lastUpdateBy;
    private int customerId;
    private int userId;
    private int contactId;

    public Appointment(int appId, String appTitle, String appDesc, String appLocation, String appType, String appStart, String appEnd, Calendar createDate, String createdBy, Calendar lastUpdate, String lastUpdateBy, int customerId, int userId, int contactId) {
        this.setId(appId);
        this.setTitle(appTitle);
        this.setDescription(appDesc);
        this.setLocation(appLocation);
        this.setType(appType);
        this.setStart(appStart);
        this.setEnd(appEnd);

        this.setCreateDate(createDate);
        this.setCreatedBy(createdBy);
        this.setLastUpdate(lastUpdate);
        this.setLastUpdateBy(lastUpdateBy);

        this.setCustomerId(customerId);
        this.setUserId(userId);
        this.setContactId(contactId);
    }


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

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public Calendar getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Calendar createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Calendar getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Calendar lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
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
