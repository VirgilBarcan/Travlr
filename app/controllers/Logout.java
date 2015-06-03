package controllers;

import models.ErrorMessage;
import models.UserLoginData;
import play.mvc.*;
import views.html.*;

/**
 * Created by Virgil Barcan on 03.06.2015.
 */
public class Logout extends  Controller{

    public static Result logout() {

        if(session("email") == null && session("username") == null) // User is not logged in, so he doesn't have a home
            return ok(login.render(null));

        session().clear();
        return Home.index();
    }

}
