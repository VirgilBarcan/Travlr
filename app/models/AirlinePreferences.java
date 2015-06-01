package models;

import java.util.ArrayList;

/**
 * Created by Virgil Barcan on 02.06.2015.
 */
public class AirlinePreferences {

    ArrayList<Airline> preferedAirlines;

    public AirlinePreferences() {
        preferedAirlines = new ArrayList<Airline>();
    }

    public AirlinePreferences(ArrayList<Airline> preferedAirlines) {
        this.preferedAirlines = preferedAirlines;
    }

    public ArrayList<Airline> getPreferedAirlines() {
        return preferedAirlines;
    }

    public void setPreferedAirlines(ArrayList<Airline> preferedAirlines) {
        this.preferedAirlines = preferedAirlines;
    }
}
