package controllers;

import models.UserRegisterData;
import play.mvc.Controller;
import play.mvc.Result;
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
        return ok(login.render(false));
    }

    public static Result register() {
        // Get the data from the form
        UserRegisterData userLoginData = new UserRegisterData();
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

        userLoginData.setEmail(email);
        userLoginData.setUsername(username);
        userLoginData.setPassword(password);

        System.out.println(userLoginData.toString());

        //if (userLoginData.isValid()) {
            return redirect(controllers.routes.Home.index());
        //}
        //else{
        //    return redirect(controllers.routes.Login.retryLogin());
        //}
    }

    // This will implement AJAX, to update the page without reloading it
    public static Result retryRegister() {

        return ok();
    }
}
