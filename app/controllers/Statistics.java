package controllers;

import models.UserInfo;
import play.*;
import play.mvc.*;
import service.*;
import views.html.*;

import java.util.HashMap;

public class Statistics extends Controller{
	public static Result index(String nr) {
        UserInfo userInfo = UserData.getUserInfoFromDB();
        HashMap<String, Double> harta = DatabaseLayer.topCompanies(nr);
        String message, al;
     	if(harta.size() < Integer.parseInt(nr)) {
     		message = "We do not have enough data! We have rating for " + harta.size() + " companies out of " + nr;
     		al = "alert-warning";
     	}
     	else {
     		message = "We have enough data for this query!";
     		al = "alert-info";
     	}
        return ok(statistics.render(harta, message, al, userInfo));
	}
}
