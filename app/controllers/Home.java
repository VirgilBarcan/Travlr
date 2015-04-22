package controllers;

import play.*;
import play.mvc.*;
import views.html.*;

class Home extends Controller{
    public static Result index() {
        return ok(login.render("Home"));
	}
}