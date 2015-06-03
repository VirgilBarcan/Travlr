package controllers;

import java.io.StringWriter;
import java.sql.Connection;


import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import models.Flight;
import models.Flights;
import models.UserInfo;
import play.*;
import play.db.*;
import play.mvc.*;
import service.DatabaseLayer;
import service.FlightStats;
import service.Response;
import views.html.*;

import javax.xml.bind.*;

import com.google.gson.Gson;

public class REST extends Controller {
	
	private static String ToXml(Object o) {
		return "";
	}
	
    public static Result index(String url){
        if (url.equals("")){
            return ok("rest/{format}/{service}/...");
        }
        else{
            String tokens[] = url.split("/");
            if (tokens[0].equals("flights"))
                return flights(tokens[1], tokens[2], tokens[3], tokens[4]);
        }
        
        return ok("rest/{format}/{service}/...");
    }
    
	public static Result flights(String format, String from, String to, String date){
		
		ArrayList<HashMap<String, Object>> flights = null;
		Flights flightss = new Flights();
		
		try {
			flights = FlightStats.getFlights(from, to, new SimpleDateFormat("yyyy-MM-dd").parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
			return ok("");
		}
		
		for (HashMap<String, Object> flight : flights){
        	flightss.add(new Flight(flight.get("iata").toString(), 0, flight.get("carrier").toString(), flight.get("departure").toString(), flight.get("arrival").toString()));
        }
		
		if (format.equals("json")){
			return ok(flightss.toJson());
		}
		if (format.equals("xml")){
			return ok(flightss.toXml());
		}
		
		return ok("");
	}
}
