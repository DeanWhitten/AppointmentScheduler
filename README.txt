Appointment Scheduler
---------------------------------------------------------------------------------------------------------------------
The purpose of this application is to allow the scheduling, updating, and cancellation of appointments along with the
adding, updating, and deletion of customers. This app also has report features that allow the user to view different
reports. This app uses a database and requires the user to login in with valid credentials. All Log in attempts are
logged.
---------------------------------------------------------------------------------------------------------------------
author:                                             Dean Whitten
contact information:                                dwhitt8@my.wgu.edu
student application version:                        V2.0
date:                                               October 1st, 2022
---------------------------------------------------------------------------------------------------------------------
IDE:                                                IntelliJ IDEA 2022.1.3 (Ultimate Edition)
full JDK of version used:                           Oracle OpenJDK version 17.0.2
JavaFX version:                                     JavaFX-SDK-18.0.2
---------------------------------------------------------------------------------------------------------------------
how to run the program

1) Download Program file
2) Open in IntelliJ IDEA
3) go to the file at src/main/java/deamwhitten/appointmentscheduler/Main.java which is the main application file
4) Click run in the IDE tool bar
5) once the login screen is open, you can enter "test" for both the username and password to access the main application
 window
---------------------------------------------------------------------------------------------------------------------
Additional report:

The additional report created is a report that shows the number of customers per location. It has a section for each
country and only shows divisions that have a customer there and the number of customers in that location.
---------------------------------------------------------------------------------------------------------------------
MySQL Connector driver version number:              mysql-connector-java-8.0.30
---------------------------------------------------------------------------------------------------------------------
Locations of lambdas:

1) checkForUpcomingApp()
    in src/main/java/deamwhitten/appointmentscheduler/Controller/SignIn_Controller.java
    -> used to find appointments within 15 minutes

2) getThisMonthAppointments()
    in src/main/java/deamwhitten/appointmentscheduler/Utils/Collections/Appointments_Collections.java
    ->  used to efficiently filter appointments by month

3) getThisWeekAppointments()
    in src/main/java/deamwhitten/appointmentscheduler/Utils/Collections/Appointments_Collections.java
    ->   used to efficiently filter appointments by week

    --NEW LAMBDA that's different from the others for Attempt 2---
4)  getContactScheduleByContactID_Report()
    in src/main/java/deamwhitten/appointmentscheduler/Utils/Reports_Handler.java
    ->  lambda to look through all the appointments and print a line of data for each appointment encountered that is
    scheduled for a selected contact
---------------------------------------------------------------------------------------------------------------------
Location of JavaDoc index:                                JavaDocs/index.html