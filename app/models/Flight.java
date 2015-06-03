package models;

import com.google.gson.Gson;

public class Flight implements Comparable<Flight>  {
	private String companyIATA;
	private Integer companyId;
	private String departure;
	private String arrival;
	private String companyName;
	private Double score;

	public Flight(String companyIATA, Integer companyId, String companyName, String departure, String arrival) {
		this.companyIATA = companyIATA;
		this.companyId = companyId;
		this.departure = departure;
		this.companyName = companyName;
		this.arrival = arrival;
		this.score = .0;
	}

	@Override
	public int compareTo(Flight f) {
		if(this.score >= f.score)
			return -1;
		else
			return 1;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public String getCompanyIATA() {
		return companyIATA;
	}

	public String getDeparture() {
		return departure;
	}

	public String getArrival() {
		return arrival;
	}

	public Double getScore() {
		return score;
	}

	public String getCompanyName() {
		return companyName;
	}
	
	public String toJson() {
		return new Gson().toJson(this);
    }

    public String toXml() {
    	StringBuilder xml = new StringBuilder();
    	
    	xml.append("<flight>");
    	xml.append("<companyIATA>");
    	xml.append(companyIATA);
    	xml.append("</companyIATA>");
    	xml.append("<companyId>");
    	xml.append(companyId);
    	xml.append("</companyId>");
    	xml.append("<departure>");
    	xml.append(departure);
    	xml.append("</departure>");
    	xml.append("<companyName>");
    	xml.append(companyName);
    	xml.append("</companyName>");
    	xml.append("<arrival>");
    	xml.append(arrival);
    	xml.append("</arrival>");
    	xml.append("<score>");
    	xml.append(score);
    	xml.append("</score>");
    	xml.append("</flight>");
    	return xml.toString();
    }
}
