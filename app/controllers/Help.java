package controllers;

import models.UserInfo;
import models.UserLoginData;
import play.*;
import play.mvc.*;
import views.html.*;

public class Help extends Controller {
	public static Result index() {

        UserInfo userInfo = UserData.getUserInfoFromDB();

        return ok(help.render(userInfo));
	}
}
