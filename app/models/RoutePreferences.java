package models;

/**
 * Created by Virgil Barcan on 02.06.2015.
 */
public class RoutePreferences {

    // TODO Add route preferences fields
    String cheapestRoute;
    String shortestRoute;
    String mostFriendsSeenRoute;

    public RoutePreferences() {
        cheapestRoute = "";
        shortestRoute = "";
        mostFriendsSeenRoute = "";
    }

    public RoutePreferences(String cheapestRoute, String shortestRoute, String mostFriendsSeenRoute) {
        this.cheapestRoute = cheapestRoute;
        this.shortestRoute = shortestRoute;
        this.mostFriendsSeenRoute = mostFriendsSeenRoute;
    }

    public String getCheapestRoute() {
        return cheapestRoute;
    }

    public void setCheapestRoute(String cheapestRoute) {
        this.cheapestRoute = cheapestRoute;
    }

    public String getShortestRoute() {
        return shortestRoute;
    }

    public void setShortestRoute(String shortestRoute) {
        this.shortestRoute = shortestRoute;
    }

    public String getMostFriendsSeenRoute() {
        return mostFriendsSeenRoute;
    }

    public void setMostFriendsSeenRoute(String mostFriendsSeenRoute) {
        this.mostFriendsSeenRoute = mostFriendsSeenRoute;
    }
}
