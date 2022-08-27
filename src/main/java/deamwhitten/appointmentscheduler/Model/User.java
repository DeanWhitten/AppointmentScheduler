package deamwhitten.appointmentscheduler.Model;

public class User {
    private int userID;
    private String userName;

    public User(int id, String name){
        this.userID = id;
        this.userName =name;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}