package controllers;

import models.UserInfo;
import service.DatabaseLayer;
import play.*;
import play.mvc.*;
import views.html.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Company extends Controller{
	public static Result index(String id) {
		HashMap<String, Object> map = DatabaseLayer.getAirline(id);

        UserInfo userInfo = UserData.getUserInfoFromDB();

		if(map.size() > 0) {
			Double sum = new Double(map.get("rating_sum").toString());
			Double num = new Double(map.get("rating_num").toString());
			Double rating = .0;
			if(num > 0) {
				rating = sum / num;
			}
        	return ok(company.render(map.get("name").toString(), map.get("iata").toString(), map.get("icao").toString(), rating, userInfo));
        } else
        	return ok(page_not_found.render(userInfo));
	}
}
