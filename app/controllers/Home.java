package controllers;

import java.sql.Connection;


import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import play.*;
import play.db.*;
import play.mvc.*;
import service.DatabaseLayer;
import service.FlightStats;
import service.Response;
import views.html.*;

public class Home extends Controller{

    public static Result index() {
        //Comment the connection for now
        //Connection connection = DB.getConnection();
        //Statement stmt = connection.createStatement();

        return ok(home.render());
    }

    public static Result trip() {
    	Map<String, String[]> request = request().body().asFormUrlEncoded();
    	if (request != null){
    		StringBuilder flights = new StringBuilder();
    		
    		if (!request.containsKey("submit"))
    	        return ok(trip.render());
    		if (!request.containsKey("cnt"))
    	        return ok(trip.render());
    		int airports = new Integer(request.get("cnt")[0]);
    		if (airports<2)
    	        return ok(trip.render());
    		for (int i=1; i<airports; ++i){
    			String from = null;
    			String to = null;
    			String date = null;
    			String field = null;
    			
    			field = String.format("airport%d", i-1);
    			if (request.containsKey(field)){
    				String temp = request.get(field)[0];
    				if (temp.length()>=2 && temp.length()<=3)
    					from = temp;
    			}
    			field = String.format("date%d", i-1);
    			if (request.containsKey(field)){
    				date = request.get(field)[0];
    			}
    			field = String.format("airport%d", i);
    			if (request.containsKey(field)){
    				String temp = request.get(field)[0];
    				if (temp.length()>=2 && temp.length()<=3)
    					to = temp;
    			}
    			if (from!=null && date!=null && to!=null){
    				Date d = null;
					try {
						d = new SimpleDateFormat("yyyy-MM-dd").parse(date);
					} catch (ParseException e) {
						e.printStackTrace();
					}
    				if (d!=null)
    					flights.append(FlightStats.getFlights(from, to, d).toString());
    			}
    		}
    		return ok(flights.toString());
    	}
        return ok(trip.render());
    }

    public static Result map() { return ok(map.render()); }
    public static Result importer() {return ok();}
}
