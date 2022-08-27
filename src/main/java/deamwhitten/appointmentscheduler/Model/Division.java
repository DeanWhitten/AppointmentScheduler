package deamwhitten.appointmentscheduler.Model;

public class Division {
    private int id;
    private String name;
    private int countryID;

    public Division(int id, String name, int countryID){
        this.setId(id);
        this.setName(name);
        this.setCountryID(countryID);
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

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }
}