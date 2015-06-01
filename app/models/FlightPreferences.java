package models;

/**
 * Created by Virgil Barcan on 02.06.2015.
 */
public class FlightPreferences {

    // TODO Add flight preferences fields
    String nightFlight;
    String stopowersFlight;

    public FlightPreferences() {
        nightFlight = "";
        stopowersFlight = "";
    }

    public FlightPreferences(String nightFlight, String stopowersFlight) {
        this.nightFlight = nightFlight;
        this.stopowersFlight = stopowersFlight;
    }

    public String getNightFlight() {
        return nightFlight;
    }

    public void setNightFlight(String nightFlight) {
        this.nightFlight = nightFlight;
    }

    public String getStopowersFlight() {
        return stopowersFlight;
    }

    public void setStopowersFlight(String stopowersFlight) {
        this.stopowersFlight = stopowersFlight;
    }
}
