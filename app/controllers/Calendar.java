package controllers;

import models.UserInfo;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.calendar;
import views.html.contact;

public class Calendar extends Controller{
	public static Result calendar() {

        UserInfo userInfo = UserData.getUserInfoFromDB();

        return ok(calendar.render(userInfo));
	}
}
