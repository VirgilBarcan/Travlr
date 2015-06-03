package controllers;

import models.UserInfo;
import play.*;
import play.mvc.*;
import views.html.*;

public class Contact extends Controller{
	public static Result index() {

        UserInfo userInfo = UserData.getUserInfoFromDB();

        return ok(contact.render(userInfo));
	}
}
