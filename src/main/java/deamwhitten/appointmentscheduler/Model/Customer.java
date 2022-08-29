package deamwhitten.appointmentscheduler.Model;

import java.util.Calendar;

public class Customer {
    private int id;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private int divisionID;

    public Customer(int id, String name, String address, String postalCode, String phone, int divisionID){
        this.setId(id);
        this.setName(name);
        this.setAddress(address);
        this.setPostalCode(postalCode);
        this.setPhone(phone);
        this.setDivisionID(divisionID);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }
}