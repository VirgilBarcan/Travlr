package controllers;

import service.DatabaseLayer;
import play.*;
import play.mvc.*;
import play.data.*;
import views.html.*;
import models.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Collections;

import service.FlightStats;
import service.Response;


public class GroupTrip extends Controller{
	
	public static Result index() {


		int userId = 1; //TO DO: get logon user
		ArrayList<String> friends = DatabaseLayer.getUsernameListFromDb(DatabaseLayer.getFriends(userId));

        UserInfo userInfo = UserData.getUserInfoFromDB();

        return ok(group_trip.render(friends, userInfo));
	}

	public static Result postIndex() {
		int userId = 1; //TO DO: get logon user
		ArrayList<String> friends = new ArrayList<String>();
		friends.add(DatabaseLayer.getUsernameFromDb(userId));
		DynamicForm dynamicForm = Form.form().bindFromRequest();

        UserInfo userInfo = UserData.getUserInfoFromDB();

		for(Map.Entry<String, String> entry: dynamicForm.data().entrySet()) {
			if(entry.getKey().contains("friend") == true)
				friends.add(entry.getValue());
		}
		String from = dynamicForm.get("airport0"); 
		String to = dynamicForm.get("airport1");
		Date d = null;
		try {
			d = new SimpleDateFormat("yyyy-MM-dd").parse(dynamicForm.get("date0"));
		} catch(ParseException e) {
			e.printStackTrace();
		}
		ArrayList<Flight> result = computePreferences(friends, FlightStats.getFlights(from, to, d));
		Collections.sort(result);

        return ok(trip_completed.render(result, userInfo));
	}

	private static ArrayList<Flight> computePreferences(ArrayList<String> friends, ArrayList<HashMap<String, Object> > flights) {
		Integer groupTotal = 0;
		HashMap<Integer, Double> result = new HashMap<Integer, Double>();
		ArrayList<Flight> flightsArray = new ArrayList<Flight>();
		Set<String> IATAs = new HashSet<String>();
		for(int i = 0; i < flights.size(); i++) {
			String iata = flights.get(i).get("iata").toString();
			if(IATAs.contains(iata) == false) {
				IATAs.add(iata);
				Integer id = DatabaseLayer.getAirlineByIATA(iata);
				Flight f = new Flight(iata, id, flights.get(i).get("carrier").toString(), flights.get(i).get("departure").toString(), flights.get(i).get("arrival").toString());
				flightsArray.add(f);
				result.put(id, .0);
			}
		}
		for(String friend: friends) {
			HashMap<Integer, Integer> map = DatabaseLayer.getAirlinesPerferences(friend);
			Integer userTotal = computeSum(map);
			groupTotal = groupTotal + userTotal;
			for(Map.Entry<Integer, Integer> entry: map.entrySet()) {
				if(result.get(entry.getKey()) == null)continue;
				result.put(entry.getKey(), result.get(entry.getKey()) + userTotal * entry.getValue());
			}
		}
		if(groupTotal > 0) {
			for(Map.Entry<Integer, Double> entry: result.entrySet()) {
				entry.setValue(entry.getValue() / (1.0 * groupTotal));
			}	
		}
		for(int i = 0; i < flightsArray.size(); i++) {
			flightsArray.get(i).setScore(result.get(flightsArray.get(i).getCompanyId()));
		}
		return flightsArray;
	}

	private static Integer computeSum(HashMap<Integer, Integer> map) {
		int sum = 0;
		for(Map.Entry<Integer, Integer> entry: map.entrySet()) {
			sum = sum + entry.getValue();
		}
		return sum;
	}

}

