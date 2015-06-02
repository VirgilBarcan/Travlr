package controllers;

import java.sql.Connection;


import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import models.Flight;
import play.*;
import play.db.*;
import play.mvc.*;
import service.DatabaseLayer;
import service.FlightStats;
import service.Response;
import views.html.*;

public class Home extends Controller{

    public static Result index() {
        return ok(home.render());
    }

    public static Result trip() {
        Map<String, String[]> request = request().body().asFormUrlEncoded();
        if (request != null){
            ArrayList<Flight> flights = new ArrayList<Flight>();
            
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
                    from = request.get(field)[0];
                }
                field = String.format("date%d", i-1);
                if (request.containsKey(field)){
                    date = request.get(field)[0];
                }
                field = String.format("airport%d", i);
                if (request.containsKey(field)){
                    to = request.get(field)[0];
                }
                if (from!=null && date!=null && to!=null){
                    Date d = null;
                    try {
                        d = new SimpleDateFormat("yyyy-MM-dd").parse(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (d!=null){
                        ArrayList<HashMap<String, Object>> routes = FlightStats.getFlights(from, to, d);
                        for (HashMap<String, Object> route : routes){
                            flights.add(new Flight(route.get("iata").toString(), 0, route.get("carrier").toString(), route.get("departure").toString(), route.get("arrival").toString()));
                        }
                    }
                }
            }
            return ok(trip_completed.render(flights));
        }

        return ok(trip.render());
    }

    public static Result map() { return ok(map.render()); }
    public static Result importer() {return ok();}
}
