package models;

import java.util.ArrayList;

import com.google.gson.Gson;

public class Flights {
	private ArrayList<Flight> flights;
	
	public Flights(){
		flights = new ArrayList<Flight>();
	}
	
	public void add(Flight flight){
		flights.add(flight);
	}

	public String toJson() {
		return new Gson().toJson(this);
    }
	
    public String toXml() {
    	StringBuilder xml = new StringBuilder();
    	
    	xml.append("<flights>");
    	for (Flight flight : flights){
    		xml.append(flight.toXml());
    	}
    	xml.append("</flights>");
    	return xml.toString();
    }
}
