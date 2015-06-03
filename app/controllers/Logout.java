package controllers;

import models.ErrorMessage;
import models.UserLoginData;
import play.mvc.*;
import views.html.*;

/**
 * Created by Virgil Barcan on 03.06.2015.
 */
public class Logout {

    public static Result logout() {
        //session().clear();
        return Home.index();
    }

}
