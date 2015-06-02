package service;

/**
 * Created by Virgil Barcan on 30.05.2015.
 */

import models.*;
import oracle.sql.STRUCT;
import play.db.DB;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is used to handle all the database work required by other classes (by the controllers, for example)
 */
public class DatabaseLayer {

    /**
     * This function is used to check if the data given by the user is valid:
     * - username/email exists in the DB
     * - correct password for the username/email
     * @param userLoginData the user login data (username, email, password)
     * @return 0 = valid, 1 = invalid
     */
    public static int isValidLogin(UserLoginData userLoginData) {
        int result = 0;

        // We know for sure that if the user inserts into either email of username, any of these is different from ""
        String email = userLoginData.getEmail();
        String username = userLoginData.getUsername();
        String password = userLoginData.getPassword();

        String usernameEmail = (email != "") ? email : username;

        // Send an request to the DB to find if the user exists
        // If the user is valid, 0 is returned
        // If the user is invalid, 1 is returned
        // NOT TESTED!!!
        String sqlQuery = "{? = call TRAVLR.IS_VALID_USER(?, ?)}";
        Connection connection = DB.getConnection();
        CallableStatement statement = null;
        try {
            statement = connection.prepareCall(sqlQuery);
            statement.registerOutParameter(1, Types.INTEGER);
            statement.setString(2, usernameEmail);
            statement.setString(3, password);
            statement.execute();

            // if the user exists and the password is correct, result will be 0, otherwise 1
            result = statement.getInt(1);

            System.out.println("isValidLogin result: " + result);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }


    public static int isValidExternal(UserLoginData userLoginData) {
        int result = 0;

        // We know for sure that if the user inserts into either email of username, any of these is different from ""
        String email = userLoginData.getEmail();
        String username = userLoginData.getUsername();
        String password = userLoginData.getPassword();

        String usernameEmail = (email != "") ? email : username;

        // Send an request to the DB to find if the user exists
        // If the user is valid, 0 is returned
        // If the user is invalid, 1 is returned
        // NOT TESTED!!!
        String sqlQuery = "{? = call TRAVLR.IS_VALID_USER_EXTERNAL(?)}";
        Connection connection = DB.getConnection();
        CallableStatement statement = null;
        try {
            statement = connection.prepareCall(sqlQuery);
            statement.registerOutParameter(1, Types.INTEGER);
            statement.setString(2, usernameEmail);
            statement.execute();

            // if the user exists and the password is correct, result will be 0, otherwise 1
            result = statement.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static int isValidExternal(UserRegisterData userRegisterData) {
        int result = 0;

        // We know for sure that if the user inserts into either email of username, any of these is different from ""
        String email = userRegisterData.getEmail();
        String username = userRegisterData.getUsername();
        String password = userRegisterData.getPassword();

        String usernameEmail = (email != "") ? email : username;

        // Send an request to the DB to find if the user exists
        // If the user is valid, 0 is returned
        // If the user is invalid, 1 is returned
        // NOT TESTED!!!
        String sqlQuery = "{? = call TRAVLR.IS_VALID_USER_EXTERNAL(?)}";
        Connection connection = DB.getConnection();
        CallableStatement statement = null;
        try {
            statement = connection.prepareCall(sqlQuery);
            statement.registerOutParameter(1, Types.INTEGER);
            statement.setString(2, usernameEmail);
            statement.execute();

            // if the user exists and the password is correct, result will be 0, otherwise 1
            result = statement.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * This function is used to check if the data given by the user is valid:
     * - the email isn't already used in the DB
     * - the username isn't already usen in the DB
     * @param userRegisterData the user register data (email, username, password)
     * @return 0 = valid, 1 = invalid username, 2 = invalid email
     */
    public static int isValidRegister(UserRegisterData userRegisterData) {
        int result = 0;

        // We know for sure that if the user inserts into either email of username, any of these is different from ""
        String email = userRegisterData.getEmail();
        String username = userRegisterData.getUsername();
        String password = userRegisterData.getPassword();


         // Send an request to the DB to find if the user exists
         // NOT TESTED!!!
         String sqlQuery = "{? = call TRAVLR.IS_VALID_USER(?, ?, ?)}";
         Connection connection = DB.getConnection();
         CallableStatement statement = null;
         try {
             statement = connection.prepareCall(sqlQuery);
             statement.registerOutParameter(1, Types.INTEGER);
             statement.setString(2, email);
             statement.setString(3, username);
             statement.setString(4, password);
             statement.execute();

             // if the user exists and the password is correct, result will be 0, otherwise 1 or 2
             result = statement.getInt(1);
         } catch (SQLException e) {
             e.printStackTrace();
         }

        return result;
    }

    /**
     * This function is used to insert a new user into the DB
     * @param userRegisterData the user register data (email, username, password)
     * @return 0 if the user was inserted successfully, 1 if the user was not inserted successfully, 2 if the user was already created
     */
    public static boolean addUserRegisterDataToDB(UserRegisterData userRegisterData) {
        int result = -1;

        String email = userRegisterData.getEmail();
        String username = userRegisterData.getUsername();
        String password = userRegisterData.getPassword();

        // Add the new user (if possible, that is, if the email or username aren't already in use)
        // NOT TESTED!!!
        String sqlQuery = "{? = call TRAVLR.ADD_USER(?, ?, ?)}";
        Connection connection = DB.getConnection();
        CallableStatement statement = null;
        try {
            statement = connection.prepareCall(sqlQuery);
            statement.registerOutParameter(1, Types.INTEGER);
            statement.setString(2, email);
            statement.setString(3, username);
            statement.setString(4, password);
            statement.execute();

            // if the user was inserted successfully, 0 is returned
            // if the user was not inserted successfully, 1 is returned
            // if the user was already created, 2 is returned
            result = statement.getInt(1);
            System.out.println("result of execution: " + result);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return result == 0;
    }

    /**
     * This function is used to insert/update the user info
     * @param userInfo the user info (firstName, lastName, birthdate, gender)
     * @return
     */
    public static boolean addUserInfoToDB(UserInfo userInfo, String userIdentifier) {
        int result = -1;

        // We know for sure that if the user inserts into either email of username, any of these is different from ""
        String firstName = userInfo.getFirstName();
        String lastName = userInfo.getLastName();
        String birthdate = userInfo.getBirthdate();
        String gender = userInfo.getGender();

        System.out.println("addUserInfoToDB> userIdentifier: " + userIdentifier);
        System.out.println("add this userInfo: " + userInfo);

        // Send an request to the DB to find if the user exists
        // NOT TESTED!!!
         String sqlQuery = "{? = call TRAVLR.ADD_USER_INFO(?, ?, ?, ?, ?)}";
         Connection connection = DB.getConnection();
         CallableStatement statement = null;
         try {
             statement = connection.prepareCall(sqlQuery);
             statement.registerOutParameter(1, Types.INTEGER);
             statement.setString(2, firstName);
             statement.setString(3, lastName);
             statement.setString(4, birthdate);
             statement.setString(5, gender);
             statement.setString(6, userIdentifier);
             statement.execute();

             result = statement.getInt(1);
         } catch (SQLException e) {
             e.printStackTrace();
         }

        return result == 0;
    }

    public static boolean addUserHometownToDB(Address userHometown, String userIdentifier) {
        int result = -1;

        System.out.println("addUserHometownToDB> userIdentifier: " + userIdentifier);
        System.out.println("add this userHometown: " + userHometown.toString());

        // We know for sure that if the user inserts into either email of username, any of these is different from ""
        String country = userHometown.getCountry();
        String state = userHometown.getState();
        String county = userHometown.getCounty();
        String locality = userHometown.getLocality();
        String streetName = userHometown.getStreetName();
        String streetNumber = userHometown.getStreetNumber();

        // Send an request to the DB to find if the user exists
        // NOT TESTED!!!
        String sqlQuery = "{? = call TRAVLR.ADD_USER_HOMETOWN(?, ?, ?, ?, ?, ?, ?)}";
        Connection connection = DB.getConnection();
        CallableStatement statement = null;
        try {
            statement = connection.prepareCall(sqlQuery);
            statement.registerOutParameter(1, Types.INTEGER);
            statement.setString(2, country);
            statement.setString(3, state);
            statement.setString(4, county);
            statement.setString(5, locality);
            statement.setString(6, streetName);
            statement.setString(7, streetNumber);
            statement.setString(8, userIdentifier);
            statement.execute();

            result = statement.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result == 0;
    }

    public static boolean addUserCurrentAddressToDB(Address userCurrentAddress, String userIdentifier) {
        int result = -1;

        System.out.println("addUserCurrentAddressToDB> userIdentifier: " + userIdentifier);
        System.out.println("add this userCurrentAddress: " + userCurrentAddress.toString());

        // We know for sure that if the user inserts into either email of username, any of these is different from ""
        String country = userCurrentAddress.getCountry();
        String state = userCurrentAddress.getState();
        String county = userCurrentAddress.getCounty();
        String locality = userCurrentAddress.getLocality();
        String streetName = userCurrentAddress.getStreetName();
        String streetNumber = userCurrentAddress.getStreetNumber();

        // Send an request to the DB to find if the user exists
        // NOT TESTED!!!
        String sqlQuery = "{? = call TRAVLR.ADD_USER_CURREENT_ADDRESS(?, ?, ?, ?, ?, ?, ?)}";
        Connection connection = DB.getConnection();
        CallableStatement statement = null;
        try {
            statement = connection.prepareCall(sqlQuery);
            statement.registerOutParameter(1, Types.INTEGER);
            statement.setString(2, country);
            statement.setString(3, state);
            statement.setString(4, county);
            statement.setString(5, locality);
            statement.setString(6, streetName);
            statement.setString(7, streetNumber);
            statement.setString(8, userIdentifier);
            statement.execute();

            result = statement.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result == 0;
    }
    
    public static ArrayList<Integer> getFriends(int userId) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        try {
            String query = "SELECT * FROM TABLE(TRAVLR.GET_FRIENDS(" + userId + "))";
            Connection con = DB.getConnection();
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                result.add(rs.getInt(1));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getUsernameFromDb(int userId) {
        String result = null;
        try {
            String query = "SELECT username FROM Users WHERE user_id=" + userId;
            Connection con = DB.getConnection();
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            result = rs.getString(1);
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static ArrayList<String> getUsernameListFromDb(ArrayList<Integer> userIds) {
        ArrayList<String> result = new ArrayList<String>();
        for(int i = 0; i < userIds.size(); i++) {
            result.add(getUsernameFromDb(userIds.get(i)));
        }
        return result;
    }
    
    public static HashMap<Integer, Integer> getAirlinesPerferences(String username) {
        HashMap<Integer, Integer> result = new HashMap<Integer, Integer>();
        try {
            String query = "SELECT airline_id, rating FROM Airline_user a INNER JOIN Users u ON a.user_id = u.user_id WHERE u.username='" + username + "'";
            Connection con = DB.getConnection();
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
                result.put(rs.getInt(1), rs.getInt(2));
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Integer getAirlineByIATA(String airlineName) {
        Integer result = 0;
        try {
            String query = "SELECT airline_id from Airline where iata_code='" + airlineName + "'";
            Connection con = DB.getConnection();
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            if(rs.next())
                result = rs.getInt(1);
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static ArrayList<Address> getAddressesRecommendations() {
        ArrayList<Address> recommendations = new ArrayList<Address>();
        Address userHometown = null;

        String country = "";
        String state = "";
        String county = "";
        String locality = "";
        String streetName = "";
        String streetNumber = "";

        // idiot search
        Statement stmt = null;
        String query = "SELECT CO.name AS COUNTRY, CI.state AS STATE, CI.county AS COUNTY, CI.city_name AS LOCALITY, ST.street_name AS STREET_NAME, ST.street_no AS STREET_NUMBER\n" +
                "FROM COUNTRY CO, CITY CI, STREET ST, ADDRESS A\n" +
                "WHERE A.country_id = CO.country_id AND\n" +
                "      A.city_id = CI.city_id AND\n" +
                "      A.street_id = ST.street_id AND\n" +
                "      CO.country_id = CI.country_id AND\n" +
                "      CI.city_id = ST.city_id";

        System.out.println("query: " + query);

        try {
            Connection connection = DB.getConnection();
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                country = rs.getString("COUNTRY");
                state = rs.getString("STATE");
                county = rs.getString("COUNTY");
                locality = rs.getString("LOCALITY");
                streetName = rs.getString("STREET_NAME");
                streetNumber = rs.getString("STREET_NUMBER");

                userHometown = new Address(country, state, county, locality, streetName, streetNumber);

                recommendations.add(userHometown);

                System.out.println("DatabaseLayer> searchHometown> " + userHometown);
            }
        } catch (SQLException e ) {
            e.printStackTrace();
        }

        return recommendations;
    }


    /*
    public static boolean addNewVisitedPlaceToDB(Address newVisitedPlace, String userIdentifier) {
        int result = -1;

        // We know for sure that if the user inserts into either email of username, any of these is different from ""
        String country = newVisitedPlace.getCountry();
        String state = newVisitedPlace.getState();
        String county = newVisitedPlace.getCounty();
        String locality = newVisitedPlace.getLocality();
        String streetName = newVisitedPlace.getStreetName();
        String streetNumber = newVisitedPlace.getStreetNumber();

        // Send an request to the DB to find if the user exists
        // NOT TESTED!!!
        String sqlQuery = "{? = call TRAVLR.ADD_USER_VISITED_PLACE(?, ?, ?, ?, ?, ?, ?)}";
        Connection connection = DB.getConnection();
        CallableStatement statement = null;
        try {
            statement = connection.prepareCall(sqlQuery);
            statement.registerOutParameter(1, Types.INTEGER);
            statement.setString(2, country);
            statement.setString(3, state);
            statement.setString(4, county);
            statement.setString(5, locality);
            statement.setString(6, streetName);
            statement.setString(7, streetNumber);
            statement.setString(8, userIdentifier);
            statement.execute();

            result = statement.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result == 0;
    }

    public static boolean addNewAirlinePreferenceToDB(Preference newAirlinePreference, String userIdentifier) {
        // String sqlQuery = "{? = call TRAVLR.ADD_USER_AIRLINE(?, ?, ?, ?, ?)}";
    }

    public static boolean addUserFlightPreferencesToDB(Preference flightPreferences, String userIdentifier) {
        // String sqlQuery = "{? = call TRAVLR.ADD_USER_FLIGHT_PREFERENCES(?, ?, ?, ?, ?)}";
    }

    public static boolean addUserRoutePreferencesToDB(Preference routePreferences String userIdentifier) {
        // String sqlQuery = "{? = call TRAVLR.ADD_USER_ROUTE_PREFERENCES(?, ?, ?, ?, ?)}";
    }
    */

    /**
     * This function will be used to get the user info from the DB (FirstName, LastName, Birthdate, Gender)
     * @param userIdentifier the user identifier (to know for whom we search in the DB)
     * @return userInfo object representing the user info
     */
    public static UserInfo getUserInfoFromDB(String userIdentifier) {
        UserInfo userInfo = null;

        System.out.println("getUserInfoFromDB> userIdentifier: " + userIdentifier);

        // get userInfo from DB
        // Send an request to the DB to find if the user exists
        // NOT TESTED!!!
        String sqlQuery = "{? = call TRAVLR.GET_USER_INFO(?)}";
        Connection connection = DB.getConnection();
        CallableStatement statement = null;
        try {
            statement = connection.prepareCall(sqlQuery);
            statement.registerOutParameter(1, Types.STRUCT, "USER_INFO_TYPE");
            statement.setString(2, userIdentifier);
            statement.execute();

            STRUCT result = (STRUCT) statement.getObject(1);

            if (result != null) {
                System.out.println("Received STRUCT: " + result);
                userInfo = new UserInfo();
            }
            else{
                System.out.println("STRUCT null");
            }

            Object[] attributes;
            attributes = result.getAttributes();

            if (attributes != null) {
                System.out.println("attributes: " + attributes);
            }
            else{
                System.out.println("attributes null");
            }

            String firstName = attributes[0].toString();
            String lastName  = attributes[1].toString();
            String birthdate = attributes[2].toString();
            String gender    = attributes[3].toString();

            System.out.println("GET_USER_INFO: " + firstName + " " + lastName + " " + birthdate + " " + gender);

            userInfo.setFirstName(firstName);
            userInfo.setLastName(lastName);
            userInfo.setBirthdate(birthdate);
            userInfo.setGender(gender);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userInfo;
    }

    /**
     * This function will be used to get the user hometown from the DB
     * @param userIdentifier the user identifier (to know for whom we search in the DB)
     * @return address object representing the user's hometown
     */
    public static Address getUserHometownFromDB(String userIdentifier) {
        Address userHometown = null;

        System.out.println("getUserHometownFromDB> userIdentifier: " + userIdentifier);


        // get userInfo from DB
        // Send an request to the DB to find if the user exists
        // NOT TESTED!!!
        String sqlQuery = "{? = call TRAVLR.GET_USER_HOMETOWN(?)}";
        Connection connection = DB.getConnection();
        CallableStatement statement = null;
        try {
            statement = connection.prepareCall(sqlQuery);
            statement.registerOutParameter(1, Types.STRUCT, "ADDRESS_TYPE");
            statement.setString(2, userIdentifier);
            statement.execute();

            STRUCT result = (STRUCT) statement.getObject(1);

            if (result != null) {
                System.out.println("Received STRUCT: " + result);
                userHometown = new Address();
            }
            else{
                System.out.println("STRUCT null");
            }

            Object[] attributes;
            attributes = result.getAttributes();

            if (attributes != null) {
                System.out.println("attributes: " + attributes);
            }
            else{
                System.out.println("attributes null");
            }

            String country = attributes[0].toString();
            String state  = attributes[1].toString();
            String county = attributes[2].toString();
            String locality    = attributes[3].toString();
            String streetName = attributes[4].toString();
            String streetNo = attributes[5].toString();

            System.out.println("GET_USER_HOMETOWN: " + country + " " + state + " " + county + " " + locality + " " + streetName + " " + streetNo);

            userHometown.setCountry(country);
            userHometown.setState(state);
            userHometown.setCounty(county);
            userHometown.setLocality(locality);
            userHometown.setStreetName(streetName);
            userHometown.setStreetNumber(streetNo);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userHometown;
    }

    /**
     * This function will be used to get the user current address from the DB
     * @param userIdentifier the user identifier (to know for whom we search in the DB)
     * @return address object representing the user's current address
     */
    public static Address getUserCurrentAddressFromDB(String userIdentifier) {
        Address userCurrentAddress = null;

        System.out.println("getUserCurrentAddressFromDB> userIdentifier: " + userIdentifier);

        // get userInfo from DB
        // Send an request to the DB to find if the user exists
        // NOT TESTED!!!
        String sqlQuery = "{? = call TRAVLR.GET_USER_CURRENT_ADDRESS(?)}";
        Connection connection = DB.getConnection();
        CallableStatement statement = null;
        try {
            statement = connection.prepareCall(sqlQuery);
            statement.registerOutParameter(1, Types.STRUCT, "ADDRESS_TYPE");
            statement.setString(2, userIdentifier);
            statement.execute();

            STRUCT result = (STRUCT) statement.getObject(1);

            if (result != null) {
                System.out.println("Received STRUCT: " + result);
                userCurrentAddress = new Address();
            }
            else{
                System.out.println("STRUCT null");
            }

            Object[] attributes;
            attributes = result.getAttributes();

            if (attributes != null) {
                System.out.println("attributes: " + attributes);
            }
            else{
                System.out.println("attributes null");
            }

            String country = attributes[0].toString();
            String state  = attributes[1].toString();
            String county = attributes[2].toString();
            String locality    = attributes[3].toString();
            String streetName = attributes[4].toString();
            String streetNo = attributes[5].toString();

            System.out.println("GET_USER_CURRENT_ADDRESS: " + country + " " + state + " " + county + " " + locality + " " + streetName + " " + streetNo);

            userCurrentAddress.setCountry(country);
            userCurrentAddress.setState(state);
            userCurrentAddress.setCounty(county);
            userCurrentAddress.setLocality(locality);
            userCurrentAddress.setStreetName(streetName);
            userCurrentAddress.setStreetNumber(streetNo);


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userCurrentAddress;
    }

    public static AirlinePreferences getUserAirlinePreferencesFromDB(String userIdentifier) {
        AirlinePreferences userAirlinePreferences = null;

        System.out.println("getUserAirlinePreferencesFromDB> userIdentifier: " + userIdentifier);

        // get userInfo from DB
        // Send an request to the DB to find if the user exists
        // NOT TESTED!!!
        String sqlQuery = "{? = call TRAVLR.GET_USER_AIRLINE_PREFERENCES(?)}";
        Connection connection = DB.getConnection();
        CallableStatement statement = null;
        try {
            statement = connection.prepareCall(sqlQuery);
            //TODO define a new SQL Type
            statement.registerOutParameter(1, Types.STRUCT, "ADDRESS_TYPE");
            statement.setString(2, userIdentifier);
            statement.execute();

            STRUCT result = (STRUCT) statement.getObject(1);

            if (result != null) {
                System.out.println("Received STRUCT: " + result);
                userAirlinePreferences = new AirlinePreferences();
            }
            else{
                System.out.println("STRUCT null");
            }

            Object[] attributes;
            attributes = result.getAttributes();

            if (attributes != null) {
                System.out.println("attributes: " + attributes);
            }
            else{
                System.out.println("attributes null");
            }

            String country = attributes[0].toString();

            //System.out.println("GET_USER_CURRENT_ADDRESS: " + country + " " + state + " " + county + " " + locality + " " + streetName + " " + streetNo);

            userAirlinePreferences.setPreferedAirlines(null);


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userAirlinePreferences;
    }

    public static FlightPreferences getUserFlightPreferencesFromDB(String userIdentifier) {
        FlightPreferences userFlightPreferences = null;

        System.out.println("getUserFlightPreferencesFromDB> userIdentifier: " + userIdentifier);

        // get userInfo from DB
        // Send an request to the DB to find if the user exists
        // NOT TESTED!!!
        String sqlQuery = "{? = call TRAVLR.GET_USER_FLIGHT_PREFERENCES(?)}";
        Connection connection = DB.getConnection();
        CallableStatement statement = null;
        try {
            statement = connection.prepareCall(sqlQuery);
            //TODO define a new SQL Type
            statement.registerOutParameter(1, Types.STRUCT, "FLIGHT_PREFERENCES_TYPE");
            statement.setString(2, userIdentifier);
            statement.execute();

            STRUCT result = (STRUCT) statement.getObject(1);

            if (result != null) {
                System.out.println("Received STRUCT: " + result);
                userFlightPreferences = new FlightPreferences();
            }
            else{
                System.out.println("STRUCT null");
            }

            Object[] attributes;
            attributes = result.getAttributes();

            if (attributes != null) {
                System.out.println("attributes: " + attributes);
            }
            else{
                System.out.println("attributes null");
            }

            String nightFlights = attributes[0].toString();
            String stopoversFlights = attributes[1].toString();

            //System.out.println("GET_USER_FLIGHT_PREFERENCES: " + nightFlights + " " + stopoversFlights);

            userFlightPreferences.setNightFlight(nightFlights);
            userFlightPreferences.setStopowersFlight(stopoversFlights);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userFlightPreferences;
    }

    public static RoutePreferences getUserRoutePreferencesFromDB(String userIdentifier) {
        RoutePreferences userRoutePreferences = null;

        System.out.println("getUserRoutePreferencesFromDB> userIdentifier: " + userIdentifier);

        // get userInfo from DB
        // Send an request to the DB to find if the user exists
        // NOT TESTED!!!
        String sqlQuery = "{? = call TRAVLR.GET_USER_ROUTE_PREFERENCES(?)}";
        Connection connection = DB.getConnection();
        CallableStatement statement = null;
        try {
            statement = connection.prepareCall(sqlQuery);
            //TODO define a new SQL Type
            statement.registerOutParameter(1, Types.STRUCT, "ROUTE_PREFERENCES_TYPE");
            statement.setString(2, userIdentifier);
            statement.execute();

            STRUCT result = (STRUCT) statement.getObject(1);

            if (result != null) {
                System.out.println("Received STRUCT: " + result);
                userRoutePreferences = new RoutePreferences();
            }
            else{
                System.out.println("STRUCT null");
            }

            Object[] attributes;
            attributes = result.getAttributes();

            if (attributes != null) {
                System.out.println("attributes: " + attributes);
            }
            else{
                System.out.println("attributes null");
            }

            String cheapestRoute = attributes[0].toString();
            String shortestRoute = attributes[1].toString();
            String mostFriendsSeenRoute = attributes[2].toString();

            //System.out.println("GET_USER_FLIGHT_PREFERENCES: " + nightFlights + " " + stopoversFlights);

            userRoutePreferences.setCheapestRoute(cheapestRoute);
            userRoutePreferences.setShortestRoute(shortestRoute);
            userRoutePreferences.setMostFriendsSeenRoute(mostFriendsSeenRoute);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userRoutePreferences;
    }

    public static boolean isTable(String tableName){
        try {
            String query = "? = Call TRAVLR.ISTABLE(?)";
            Connection con = DB.getConnection();
            CallableStatement stmt = con.prepareCall(query);
            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setString(2, tableName);
            stmt.execute();
            return stmt.getInt(1)>=1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean importSql(String username, String password, String path) {
        String env = null;
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            env = "cmd /c";
        }
        if (System.getProperty("os.name").toLowerCase().contains("linux")) {
            env = "sh -c";
        }

        String cmd = "%s echo exit | sqlplus %s/%s @%s";
        cmd = String.format(cmd, env, username, password, path);

        try {
            Runtime rt = Runtime.getRuntime();
            Process p = rt.exec(cmd);
            p.waitFor();
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static Response query(String q){
        try {
            Connection con = DB.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(q);
            ResultSetMetaData rsmd = null;
            ArrayList<Object[]> rows = new ArrayList<Object[]>();
            
            rsmd = rs.getMetaData();
            int columns = rsmd.getColumnCount();
            String[] columnNames = new String[columns];
            
            for (int c=0; c<columns; ++c){
                columnNames[c] = rsmd.getColumnName(c+1);
            }
            
            while (rs.next()){
                Object[] row = new Object[columns];
                for (int c=1; c<=columns; ++c){
                    Object o = rs.getObject(c);
                    if (o==null)
                        row[c-1] = "NULL";
                    else
                        row[c-1] = o;
                }
                rows.add(row);
            }
            
            Object data[][] = null;
            if (rows.size()>0){
                data = new Object[rows.size()][];
                for (int i=0; i<rows.size(); ++i)
                    data[i] = rows.get(i);
            }
            return new Response(columnNames, data);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
