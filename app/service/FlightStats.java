package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import play.libs.Json;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.fasterxml.jackson.databind.JsonNode;


public class FlightStats {
	private static String appID = "b9a41a78";
	private static String appKey = "794cba8436f4bd984ca7c6d9903fa0e0";
	
	private static String download(String link) throws IOException{
		System.setProperty("http.agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.90 Safari/537.36");
		URL url = new URL(link);
		URLConnection con = url.openConnection();
		con.setRequestProperty("Accept-Charset", "UTF-8");
		
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
		StringBuilder content = new StringBuilder();

		int read;
		while ((read=in.read())!=-1){
			content.append((char)read);
		}
		in.close();
		
		return content.toString();
	}
	
	private static ArrayList<String> getAirports(String place){
		ArrayList<String> places = new ArrayList<String>();

		if (getAirportInfo(place).size()==0){
			String query = "select iata_code from airport where upper(airport_city) like '%s%%'";
			Response resp = DatabaseLayer.query(String.format(query, place));
			if (resp!=null){
				for (Object row[] : resp.getData()){
					places.add(row[0].toString());
				}
			}
		}
		else{
			places.add(place);
		}
		
		return places;
	}
	
	public static ArrayList<HashMap<String, Object>> getFlights(String from, String to, Date date){
		ArrayList<HashMap<String, Object>> flights = new ArrayList<HashMap<String,Object>>();
		ArrayList<String> froms, tos;

		froms = getAirports(from);
		tos = getAirports(to);
		for (int i=0; i<froms.size(); ++i){
			for (int j=0; j<tos.size(); ++j){
				String url = "https://api.flightstats.com/flex/schedules/rest/v1/json/from/%s/to/%s/departing/%s?appId=%s&appKey=%s";
				url = String.format(url, froms.get(i), tos.get(j), new SimpleDateFormat("yyyy/MM/dd").format(date), appID, appKey);
				
				try {
					String doc = download(url);
					JsonNode obj = Json.parse(doc);
					JsonNode flightsArray = obj.get("scheduledFlights");
					for (int k=0; k<flightsArray.size(); ++k){
						HashMap<String, Object> flight = new HashMap<String, Object>();
						flight.putAll(getAirlineInfo(flightsArray.get(k).get("carrierFsCode").asText()));
						flight.put("departure", flightsArray.get(k).get("departureTime").asText());
						flight.put("arrival", flightsArray.get(k).get("arrivalTime").asText());
						flights.add(flight);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return flights;
	}
	
	public static HashMap<String, String> getAirlineInfo(String code) {
		String url = "https://api.flightstats.com/flex/airlines/rest/v1/json/fs/%s?appId=%s&appKey=%s";
		url = String.format(url, code, appID, appKey);
		HashMap<String, String> info = new HashMap<String, String>();

		try {
			String doc = download(url);
			JsonNode obj = Json.parse(doc).get("airline");
			if (obj==null)
				return info;
			
			info.put("iata", obj.get("iata").asText());
			info.put("carrier", obj.get("name").asText());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return info;
	}
	

	public static HashMap<String, String> getAirportInfo(String code) {
		String url = "https://api.flightstats.com/flex/airports/rest/v1/json/fs/%s?appId=%s&appKey=%s";
		url = String.format(url, code, appID, appKey);
		HashMap<String, String> info = new HashMap<String, String>();
		
		try {
			String doc = download(url);
			JsonNode obj = Json.parse(doc).get("airport");
			if (obj==null)
				return info;
			
			info.put("name", obj.get("name").asText());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return info;
	}
}
