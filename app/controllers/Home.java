package controllers;

import java.sql.Connection;


import java.sql.Statement;

import play.*;
import play.db.*;
import play.mvc.*;
import views.html.*;

class Home extends Controller{
    public static Result index() {
		Connection con = DB.getConnection();
		//Statement stmt = con.createStatement();
		
    	//return ok(debug.render(db.connectToDatabase("travlrdb", "travlrdb")));
		return ok(home.render());
	}
}