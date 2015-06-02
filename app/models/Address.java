package models;

/**
 * Created by Virgil Barcan on 31.05.2015.
 */
public class Address {

    private String country;
    private String state;
    private String county;
    private String locality;
    private String streetName;
    private String streetNumber;

    public Address() {
        country = "";
        state = "";
        county = "";
        locality = "";
        streetName = "";
        streetNumber = "";
    }

    public Address(String country, String state, String county, String locality, String streetName, String streetNumber) {
        this.country = country;
        this.state = state;
        this.county = county;
        this.locality = locality;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getStringDatalist(){
        return country + ", " + state + ", " + county + ", " + locality + ", " + streetName + ", " + streetNumber;
    }

    public String toJSON() {
        return "\"country\" : " + "\"" + country + "\", " +
                "\"state\" : "  + "\""  + state + "\", " +
                "\"county\" : "  + "\""  + county + "\", " +
                "\"locality\" : "  + "\""  + locality + "\", " +
                "\"street_name\" : "  + "\""  + streetName + "\", " +
                "\"street_number\" : "  + "\""  + streetNumber + "\"";
    }

    @Override
    public String toString() {
        return "Address{" +
                "country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", county='" + county + '\'' +
                ", locality='" + locality + '\'' +
                ", streetName='" + streetName + '\'' +
                ", streetNumber='" + streetNumber + '\'' +
                '}';
    }
}
