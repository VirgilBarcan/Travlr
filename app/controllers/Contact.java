package controllers;

import play.*;
import play.mvc.*;
import views.html.*;

public class Contact extends Controller{
	public static Result index() {
        return ok(contact.render());
	}
}
