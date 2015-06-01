package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import play.api.libs.json.JsArray;
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
	
	public static String getFlights(String from, String to, Date date){
		String url = "https://api.flightstats.com/flex/schedules/rest/v1/json/from/%s/to/%s/departing/%s?appId=%s&appKey=%s";
		url = String.format(url, from, to, new SimpleDateFormat("yyyy/MM/dd").format(date), appID, appKey);
		ArrayList<HashMap<String, Object>> flights = new ArrayList<HashMap<String,Object>>();
		
		try {
			String doc = download(url);
			JsonNode obj = Json.parse(doc);
			JsonNode flightsArray = obj.get("scheduledFlights");
			for (int i=0; i<flightsArray.size(); ++i){
				HashMap<String, Object> flight = new HashMap<String, Object>();
				flight.put("carrier", getCarrier(flightsArray.get(i).get("carrierFsCode").asText()));
				flight.put("departure", flightsArray.get(i).get("departureTime").asText());
				flight.put("arrival", flightsArray.get(i).get("arrivalTime").asText());
				flights.add(flight);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return flights.toString();
	}
	
	public static String getCarrier(String code) {
		String url = "https://api.flightstats.com/flex/airlines/rest/v1/json/fs/%s?appId=%s&appKey=%s";
		url = String.format(url, code, appID, appKey);

		try {
			String doc = download(url);
			JsonNode obj = Json.parse(doc);
			return obj.get("airline").get("name").asText();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return code;
	}
}
