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
	
	public static Result flights(String format, String from, String to, String date){
		
		ArrayList<HashMap<String, Object>> flights = null;
		try {
			flights = FlightStats.getFlights(from, to, new SimpleDateFormat("yyyy/MM/dd").parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
			return ok("");
		}
		
		if (format.equals("json")){
			return ok(new Gson().toJson(flights));
		}
		if (format.equals("xml")){
			StringWriter str = new StringWriter();
			JAXBContext jaxbContext;
			try {
				jaxbContext = JAXBContext.newInstance(Flight.class);
				Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
				jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				
                for (HashMap<String, Object> flight : flights){
                	jaxbMarshaller.marshal(new Flight(flight.get("iata").toString(), 0, flight.get("carrier").toString(), flight.get("departure").toString(), flight.get("arrival").toString()), str);
                }
			} catch (JAXBException e) {
				e.printStackTrace();
			}
			return ok("");
		}
		
		return ok("");
	}
}
