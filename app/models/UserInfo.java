package models;

/**
 * Created by Virgil Barcan on 30.05.2015.
 */
public class UserInfo {

    private String firstName;
    private String lastName;
    private String birthdate;
    private String gender;

    private Address hometown;
    private Address currentAddress;

    private FlightPreferences flightPreferences;
    private RoutePreferences routePreferences;

    public UserInfo() {
        firstName = "";
        lastName = "";
        birthdate = "";
        gender = "";
    }

    public UserInfo(String firstName, String lastName, String birthdate, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.gender = gender;
    }

    public UserInfo(String firstName, String lastName, String birthdate, String gender, Address hometown, Address currentAddress, FlightPreferences flightPreferences, RoutePreferences routePreferences) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.gender = gender;
        this.hometown = hometown;
        this.currentAddress = currentAddress;
        this.flightPreferences = flightPreferences;
        this.routePreferences = routePreferences;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Address getHometown() {
        return hometown;
    }

    public void setHometown(Address hometown) {
        this.hometown = hometown;
    }

    public Address getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(Address currentAddress) {
        this.currentAddress = currentAddress;
    }

    public RoutePreferences getRoutePreferences() {
        return routePreferences;
    }

    public void setRoutePreferences(RoutePreferences routePreferences) {
        this.routePreferences = routePreferences;
    }

    public FlightPreferences getFlightPreferences() {
        return flightPreferences;
    }

    public void setFlightPreferences(FlightPreferences flightPreferences) {
        this.flightPreferences = flightPreferences;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", gender='" + gender + '\'' +
                ", hometown=" + hometown +
                ", currentAddress=" + currentAddress +
                ", flightPreferences=" + flightPreferences +
                ", routePreferences=" + routePreferences +
                '}';
    }
}
