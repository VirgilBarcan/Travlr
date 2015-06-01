package controllers;

import models.Address;
import models.UserInfo;
import play.*;
import play.data.Form;
import play.mvc.*;
import service.DatabaseLayer;
import views.html.*;

import java.util.Map;

/**
 * Created by Virgil Barcan on 29.05.2015.
 */
public class UserData extends Controller {

    public static Result viewUserData() {
        String visibleEdit = "hidden";
        String visibleView = "visible";

        UserInfo userInfo = null;
        Address userHometown = null;
        Address userCurrentAddress = null;

        //look for more info about the user in the DB
        userInfo = getUserInfoFromDB();

        userHometown = getUserHometownFromDB();
        userInfo.setHometown(userHometown);

        userCurrentAddress = getUserCurrentAddressFromDB();
        userInfo.setCurrentAddress(userCurrentAddress);

        System.out.println("viewEditData> userInfo: " + userInfo.toString());

        return ok(userData.render(visibleEdit, visibleView, userInfo));
    }

    public static Result editUserData() {
        String visibleEdit = "visible";
        String visibleView = "hidden";

        UserInfo userInfo = null;
        Address userHometown = null;
        Address userCurrentAddress = null;

        //look for more info about the user in the DB
        userInfo = getUserInfoFromDB();

        userHometown = getUserHometownFromDB();
        userInfo.setHometown(userHometown);

        userCurrentAddress = getUserCurrentAddressFromDB();
        userInfo.setCurrentAddress(userCurrentAddress);

        System.out.println("userEditData> userInfo: " + userInfo.toString());

        return ok(userData.render(visibleEdit, visibleView, userInfo));
    }

    public static Result retryEditUserData() {
        String visibleEdit = "visible";
        String visibleView = "hidden";

        session().clear();

        return ok(userData.render(visibleEdit, visibleView, null));
    }

    public static Result editUserInfo() {
        UserInfo userInfo = new UserInfo();
        String firstName = "";
        String lastName = "";
        String birthdate = "";
        String gender = "";

        Map<String, String[]> request = request().body().asFormUrlEncoded();

        if (request.containsKey("input-first-name"))
            firstName = request.get("input-first-name")[0];
        if (request.containsKey("input-last-name"))
            lastName = request.get("input-last-name")[0];
        if (request.containsKey("input-birthdate"))
            birthdate = request.get("input-birthdate")[0];
        if (request.containsKey("radio-gender"))
            gender = request.get("radio-gender")[0];

        userInfo.setFirstName(firstName);
        userInfo.setLastName(lastName);
        userInfo.setBirthdate(birthdate);
        userInfo.setGender(gender);

        // Add the received data to the session
        addToSession(userInfo);

        // Add to the database the information given by the user
        boolean addToDB = addUserInfoToDB(userInfo);

        if (addToDB == false){
            // the update of the DB didn't end up with success
            // ask the user to reinsert the data

            String visibleEdit = "visible";
            String visibleView = "hidden";

            return retryEditUserData();
        }
        else{
            // the update of the DB did end up with success
            // redirect the user to the same page, but with the fields containing the data

            String visibleEdit = "visible";
            String visibleView = "hidden";

            return showWithUserInfo(userInfo);
            //return redirect(controllers.routes.UserData.editUserData());
        }
    }

    public static Result editUserHometown() {
        Address userHometown = new Address();
        String country = "";
        String state = "";
        String county = "";
        String locality = "";
        String streetName = "";
        String streetNumber = "";

        Map<String, String[]> request = request().body().asFormUrlEncoded();

        if (request.containsKey("input-hometown-country"))
            country = request.get("input-hometown-country")[0];
        if (request.containsKey("input-hometown-state"))
            state = request.get("input-hometown-state")[0];
        if (request.containsKey("input-hometown-county"))
            county = request.get("input-hometown-county")[0];
        if (request.containsKey("input-hometown-locality"))
            locality = request.get("input-hometown-locality")[0];
        if (request.containsKey("input-hometown-street-name"))
            streetName = request.get("input-hometown-street-name")[0];
        if (request.containsKey("input-hometown-street-number"))
            streetNumber = request.get("input-hometown-street-number")[0];

        userHometown.setCountry(country);
        userHometown.setState(state);
        userHometown.setCounty(county);
        userHometown.setLocality(locality);
        userHometown.setStreetName(streetName);
        userHometown.setStreetNumber(streetNumber);

        // Add the received data to the session
        //addUserHometownToSession(userHometown);

        // Add to the database the information given by the user
        boolean addToDB = addUserHometownToDB(userHometown);

        if (addToDB == false){
            // the update of the DB didn't end up with success
            // ask the user to reinsert the data

            String visibleEdit = "visible";
            String visibleView = "hidden";

            return retryEditUserData();
        }
        else{
            // the update of the DB did end up with success
            // redirect the user to the same page, but with the fields containing the data

            String visibleEdit = "visible";
            String visibleView = "hidden";

            return showWithHometown(userHometown);
            //return redirect(controllers.routes.UserData.editUserData());
        }
    }

    public static Result editUserCurrentAddress() {
        Address currentAddress = new Address();
        String country = "";
        String state = "";
        String county = "";
        String locality = "";
        String streetName = "";
        String streetNumber = "";

        Map<String, String[]> request = request().body().asFormUrlEncoded();

        if (request.containsKey("input-hometown-country"))
            country = request.get("input-hometown-country")[0];
        if (request.containsKey("input-hometown-state"))
            state = request.get("input-hometown-state")[0];
        if (request.containsKey("input-hometown-county"))
            county = request.get("input-hometown-county")[0];
        if (request.containsKey("input-hometown-locality"))
            locality = request.get("input-hometown-locality")[0];
        if (request.containsKey("input-hometown-street-name"))
            streetName = request.get("input-hometown-street-name")[0];
        if (request.containsKey("input-hometown-street-number"))
            streetNumber = request.get("input-hometown-street-number")[0];

        currentAddress.setCountry(country);
        currentAddress.setState(state);
        currentAddress.setCounty(county);
        currentAddress.setLocality(locality);
        currentAddress.setStreetName(streetName);
        currentAddress.setStreetNumber(streetNumber);

        // Add the received data to the session
        //addUserHometownToSession(userHometown);

        // Add to the database the information given by the user
        boolean addToDB = addUserCurrentAddressToDB(currentAddress);

        if (addToDB == false){
            // the update of the DB didn't end up with success
            // ask the user to reinsert the data

            String visibleEdit = "visible";
            String visibleView = "hidden";

            return retryEditUserData();
        }
        else{
            // the update of the DB did end up with success
            // redirect the user to the same page, but with the fields containing the data

            String visibleEdit = "visible";
            String visibleView = "hidden";

            return redirect(controllers.routes.UserData.editUserData());
        }
    }


    private static Result showWithUserInfo(UserInfo userInfo) {
        String visibleEdit = "visible";
        String visibleView = "hidden";

        return ok(userData.render(visibleEdit, visibleView, userInfo));
    }

    /**
     * This page is used to reload the edit user data page with the hometown added
     * @param userHometown
     * @return
     */
    private static Result showWithHometown(Address userHometown) {
        String visibleEdit = "visible";
        String visibleView = "hidden";

        UserInfo userInfo = getUserInfoFromSession();
        userInfo.setHometown(userHometown);

        return ok(userData.render(visibleEdit, visibleView, userInfo));
    }


    /**
     * This method is used to add the user data to the session, for fast access
     * @param userInfo user data
     */
    private static void addToSession(UserInfo userInfo) {
        session("userFirstName", userInfo.getFirstName());
        session("userLastName", userInfo.getLastName());
        session("userBirthdate", userInfo.getBirthdate());
        session("userGender", userInfo.getGender());
    }

    private static void addUserHometownToSession(Address userHometown) {
        session("userHometownCountry", userHometown.getCountry());
        session("userHometownState", userHometown.getState());
        session("userHometownCounty", userHometown.getCounty());
        session("userHometownLocality", userHometown.getLocality());
        session("userHometownStreetName", userHometown.getStreetName());
        session("userHometownStreetNumber", userHometown.getStreetNumber());
    }

    private static UserInfo getUserInfoFromSession() {
        String firstName = session("userFirstName");
        String lastName = session("userLastName");
        String birthdate = session("userBirthdate");
        String gender = session("userGender");

        UserInfo userInfo = null;

        if (firstName != null && lastName != null && birthdate != null && gender != null) {
            userInfo = new UserInfo();
            userInfo.setFirstName(firstName);
            userInfo.setLastName(lastName);
            userInfo.setBirthdate(birthdate);
            userInfo.setGender(gender);
        }

        return userInfo;
    }

    /**
     * This method is used to add the user info to the DB
     * @param userInfo the userInfo (firstName, lastName, birthdate, gender)
     * @return true if the DB was successfully updated, false otherwise
     */
    private static boolean addUserInfoToDB(UserInfo userInfo) {
        boolean result = false;

        String email = session("email");
        String username = session("username");
        String password = session("password");

        String userIdentifier = (email != null ? email : username);

        // add user info to the DB
        result = DatabaseLayer.addUserInfoToDB(userInfo, userIdentifier);

        return result;
    }


    private static boolean addUserHometownToDB(Address userHometown) {
        boolean result = false;

        String email = session("email");
        String username = session("username");
        String password = session("password");

        String userIdentifier = email;
        if (email.equals("")) userIdentifier = username;

        // add user info to the DB
        result = DatabaseLayer.addUserHometownToDB(userHometown, userIdentifier);

        return result;
    }

    private static boolean addUserCurrentAddressToDB(Address userHometown) {
        boolean result = false;

        String email = session("email");
        String username = session("username");
        String password = session("password");

        String userIdentifier = email;
        if (email.equals("")) userIdentifier = username;

        // add user info to the DB
        result = DatabaseLayer.addUserCurrentAddressToDB(userHometown, userIdentifier);

        return result;
    }

    private static UserInfo getUserInfoFromDB() {
        UserInfo userInfo = null;

        String email = session("email");
        String username = session("username");
        String password = session("password");

        String userIdentifier = email;
        if (email.equals("")) userIdentifier = username;

        //get user info from the DB
        userInfo = DatabaseLayer.getUserInfoFromDB(userIdentifier);

        return userInfo;
    }

    private static Address getUserHometownFromDB() {
        Address userHometown = null;

        String email = session("email");
        String username = session("username");
        String password = session("password");

        String userIdentifier = email;
        if (email.equals("")) userIdentifier = username;

        //get user info from the DB
        userHometown = DatabaseLayer.getUserHometownFromDB(userIdentifier);

        return userHometown;
    }

    private static Address getUserCurrentAddressFromDB() {
        Address userCurrentAddress = null;

        String email = session("email");
        String username = session("username");
        String password = session("password");

        String userIdentifier = email;
        if (email.equals("")) userIdentifier = username;

        //get user info from the DB
        userCurrentAddress = DatabaseLayer.getUserCurrentAddressFromDB(userIdentifier);

        return userCurrentAddress;
    }

}
