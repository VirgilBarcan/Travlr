package controllers;

import play.*;
import play.mvc.*;
import views.html.*;

public class Help extends Controller {
	public static Result index() {
        return ok(help.render());
	}
}
