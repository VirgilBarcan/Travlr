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
import models.UserInfo;
import models.Post;
import play.*;
import play.db.*;
import play.mvc.*;
import service.DatabaseLayer;
import service.FlightStats;
import service.Response;
import views.html.*;

public class Home extends Controller
{
    public static Result index() 
    {
        if((session("email") == "" && session("username") == "") || (session("email") == null && session("username") == null)) // User is not logged in, so he doesn't have a home
            return ok(login.render(null));

        String identifier = session("email") != "" ? session("email") : session("username");
        int user_id = DatabaseLayer.getUserID(identifier);

        return Home.user(user_id);
    }

    public static Result user(Integer user_id)
    {
        if(DatabaseLayer.isValidUser(user_id) != -1) // User ID exists!
        {
            String can_post = "";

            if((session("email") == "" && session("username") == "") || (session("email") == null && session("username") == null)) // User is not logged in, so he doesn't have a home
                can_post = "readonly";

            System.out.println("Email: " + session("email"));
            System.out.println("Username: " + session("username"));

            String identifier = DatabaseLayer.getUsernameFromDb(user_id);
            if(identifier == null) identifier = DatabaseLayer.getEmailFromDb(user_id);
            if(identifier == null) System.out.println("BIG ERROR AT identifier at Home.user(" + user_id + ")");

            UserInfo _userInfo = DatabaseLayer.getUserInfoFromDB(identifier);
            String user_full_name = _userInfo.getFirstName() + " " + _userInfo.getLastName();

            int picture_id = DatabaseLayer.getPictureID(user_id);
            String picture_url = "";

            if (picture_id != -1) // The picture_id exists
            {
                picture_url = String.format("/assets/images/profile-images/%d.png", picture_id);

                ArrayList<String> from_name = new ArrayList<String>();
                ArrayList<String> from_date = new ArrayList<String>();
                ArrayList<String> from_message = new ArrayList<String>();
                ArrayList<String> from_picture = new ArrayList<String>();
                ArrayList<String> from_url = new ArrayList<String>();

                Integer totalPosts = DatabaseLayer.getPosts(user_id);

                for(int i = 1; i <= totalPosts; ++i)
                {
                    Post post = DatabaseLayer.getPost(user_id, i);
                    
                    Integer transmitter = post.getTransmitter();

                    String transmitter_identifier = DatabaseLayer.getUsernameFromDb(transmitter);
                    if(transmitter_identifier == null) transmitter_identifier = DatabaseLayer.getEmailFromDb(transmitter);
                    if(transmitter_identifier == null) System.out.println("BIG ERROR AT Home.index()");

                    UserInfo info = DatabaseLayer.getUserInfoFromDB(transmitter_identifier);

                    int transmitter_picture_id = DatabaseLayer.getPictureID(transmitter);
                    String transmitter_picture_url = String.format("/assets/images/profile-images/%d.png", transmitter_picture_id);

                    String transmitter_url = "/home/" + transmitter;

                    from_name.add(info.getFirstName() + " " + info.getLastName());
                    from_date.add(post.getAtDate());
                    from_message.add(post.getMessage());                
                    from_picture.add(transmitter_picture_url);
                    from_url.add(transmitter_url);
                }

                return ok(home.render(picture_url, user_full_name, from_name, from_date, from_message, from_picture, from_url, can_post));
            }        
            else System.out.println(">>>>>>>>>>>>>>>>> PICTURE_ID == -1 for user(" + user_id + ") <<<<<<<<<<<<<<<<<<<<<<<");   
        }

        UserInfo userInfo = UserData.getUserInfoFromDB();
        return ok(page_not_found.render(userInfo));
    }

    public static Result trip() 
    {
        Map<String, String[]> request = request().body().asFormUrlEncoded();

        UserInfo userInfo = UserData.getUserInfoFromDB();

        if (request != null){
            ArrayList<Flight> flights = new ArrayList<Flight>();

            if (!request.containsKey("submit"))
                return ok(trip.render(userInfo));
            if (!request.containsKey("cnt"))
                return ok(trip.render(userInfo));
            int airports = new Integer(request.get("cnt")[0]);
            if (airports<2)
                return ok(trip.render(userInfo));
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
            return ok(trip_completed.render(flights, userInfo));
        }

        return ok(trip.render(userInfo));
    }

    public static Result map() { return ok(map.render()); }
    public static Result importer() {return ok();}
}
