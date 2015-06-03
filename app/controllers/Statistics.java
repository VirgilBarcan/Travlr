package controllers;

import models.UserInfo;
import play.*;
import play.mvc.*;
import service.*;
import views.html.*;

import java.util.HashMap;

public class Statistics extends Controller{
	public static Result index() {
        UserInfo userInfo = UserData.getUserInfoFromDB();
        HashMap<String, Double> harta = DatabaseLayer.topCompanies("5");
        return ok(statistics.render(harta, userInfo));
	}
}
