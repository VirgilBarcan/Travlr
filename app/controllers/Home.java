package controllers;

import java.sql.Connection;


import java.sql.Statement;

import play.*;
import play.db.*;
import play.mvc.*;
import views.html.*;

public class Home extends Controller{

    public static Result index() {
        //Comment the connection for now
        //Connection connection = DB.getConnection();
        //Statement stmt = connection.createStatement();

        return ok(home.render());
    }

    public static Result trip() {
        return ok(trip.render());
    }

    public static Result map() { return ok(map.render()); }
}