package controllers;

import models.UserInfo;
import service.DatabaseLayer;
import play.*;
import play.mvc.*;
import views.html.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Airport extends Controller{
	public static Result index(String id) {
		HashMap<String, Object> map = DatabaseLayer.getAirportById(id);

        UserInfo userInfo = UserData.getUserInfoFromDB();

		if(map.size() > 0) {
			Double sum = new Double(map.get("rating_sum").toString());
			Double num = new Double(map.get("rating_num").toString());
			Double rating = .0;
			Double longi = new Double(map.get("long").toString());
			Double lat = new Double(map.get("lat").toString());
			if(num > 0) {
				rating = sum / num;
			}
        	return ok(airport.render(map.get("name").toString(), map.get("city").toString(), map.get("city_code").toString(), map.get("country").toString(), map.get("country_code").toString(), longi, lat, rating, userInfo));
        } else
        	return ok(page_not_found.render(userInfo));
	}
}
