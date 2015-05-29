package controllers;

import models.UserInfo;
import play.*;
import play.data.Form;
import play.mvc.*;
import views.html.*;

import java.util.Map;

/**
 * Created by Virgil Barcan on 29.05.2015.
 */
public class UserData extends Controller {

    public static Result viewUserData() {
        String visibleEdit = "hidden";
        String visibleView = "visible";
        return ok(userData.render(visibleEdit, visibleView, null));
    }

    public static Result editUserData() {
        String visibleEdit = "visible";
        String visibleView = "hidden";

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

        // Add to the database the information given by the user
        boolean result = addToDB(userInfo, Login.getUsernameEmail());

        if (result == false){
            // the update of the DB didn't end up with success
            // ask the user to reinsert the data
        }
        else{
            // the update of the DB did end up with success
            // redirect the user to the same page, but with the fields containing the data
        }

        System.out.println(userInfo.toString());
        System.out.println("usernameEmail: " + Login.getUsernameEmail());

        String userName = firstName + " " + lastName;
        String visibleEdit = "visible";
        String visibleView = "hidden";
        
        return ok(userData.render(visibleEdit, visibleView, userInfo));
    }

    /**
     * This method is used to add the user info to the DB
     * @param userInfo the userInfo (firstName, lastName, birthdate, gender)
     * @param userIdentifier the identifier (either the username or the email, given at login)
     * @return true if the DB was successfully updated, false otherwise
     */
    private static boolean addToDB(UserInfo userInfo, String userIdentifier) {
        boolean result = false;



        return result;
    }

}
