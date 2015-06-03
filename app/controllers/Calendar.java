package controllers;

import models.UserInfo;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.calendar;
import views.html.contact;
import views.html.login;

public class Calendar extends Controller{
	public static Result calendar() {

        if(session("email") == null && session("username") == null) // User is not logged in, so he doesn't have a home
            return ok(login.render(null));

        UserInfo userInfo = UserData.getUserInfoFromDB();

        return ok(calendar.render(userInfo));
	}
}
