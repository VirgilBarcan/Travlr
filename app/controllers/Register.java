package controllers;

import models.ErrorMessage;
import models.UserRegisterData;
import play.mvc.Controller;
import play.mvc.Result;
import service.DatabaseLayer;
import views.html.login;

import java.util.Map;

/**
 * Created by Virgil Barcan on 27.05.2015.
 *
 * This class is used to handle the login and register part
 */
public class Register extends Controller {

    /**
     * This method is used to render the login page
     * @return
     */
    public static Result loginRegister() {
        return ok(login.render(null));
    }

    public static Result register() {
        // Get the data from the form
        UserRegisterData userRegisterData = new UserRegisterData();
        String email = "";
        String username = "";
        String password = "";

        Map<String, String[]> request = request().body().asFormUrlEncoded();

        if (request.containsKey("email-register-input"))
            email = request.get("email-register-input")[0];
        if (request.containsKey("username-register"))
            username = request.get("username-register")[0];
        if (request.containsKey("password-register"))
            password = request.get("password-register")[0];

        userRegisterData.setEmail(email);
        userRegisterData.setUsername(username);
        userRegisterData.setPassword(password);

        System.out.println(userRegisterData.toString());

        int errorNo = userRegisterData.isValid();
        if (errorNo == 0) { // valid data
            // put the data in the session cookie for fast retrieval
            addToSession(userRegisterData);

            // put the data in the DB, for persistent storage
            addToDB(userRegisterData);

            // redirect to the homepage
            return redirect(controllers.routes.Home.index());
        }
        else{
            return retryRegister(errorNo);
        }
    }

    // This will implement AJAX, to update the page without reloading it
    public static Result retryRegister(int errorNo) {
        // Clear the session
        session().clear();

        String errorType = findErrorType(errorNo);

        // Print a message on the webpage to ask for a retry
        return ok(login.render(errorType));
    }

    private static void addToSession(UserRegisterData userRegisterData) {
        session("email", userRegisterData.getEmail());
        session("username", userRegisterData.getUsername());
        session("password", userRegisterData.getPassword());
    }

    /**
     * This method is used to add the user to the DB
     * @param userRegisterData the user register data (email, username, password)
     * @return true if the DB was successfully updated, false otherwise
     */
    private static boolean addToDB(UserRegisterData userRegisterData) {
        boolean result = false;

        result = DatabaseLayer.addToDB(userRegisterData);

        return result;
    }


    private static String findErrorType(int errorNo) {
        String errorType = "";

        switch (errorNo){
            case 1: // invalid username
                errorType = ErrorMessage.USERNAME_TAKEN.toString();
                break;
            case 2: // invalid email
                errorType = ErrorMessage.EMAIL_USED.toString();
                break;
            default:
        }

        return errorType;
    }
}
