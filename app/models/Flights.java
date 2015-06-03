package models;

import java.util.ArrayList;

public class Flights {
	private ArrayList<Flight> flights;
	
	public Flights(){
		flights = new ArrayList<Flight>();
	}
	
	public void add(Flight flight){
		flights.add(flight);
	}
	
	
}
