package controllers;

import play.*;
import play.data.Form;
import play.mvc.*;
import views.html.*;

/**
 * Created by Virgil Barcan on 29.05.2015.
 */
public class UserData extends Controller {

    public static Result viewUserData() {
        return ok(userData.render());
    }

}
