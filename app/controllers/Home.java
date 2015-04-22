package controllers;

import assets.Database;
import play.*;
import play.mvc.*;
import views.html.*;

class Home extends Controller{
    public static Result index() {
    	Database db = new Database();
    	    	
        
    	//return ok(debug.render(db.connectToDatabase("travlrdb", "travlrdb")));
        return ok(debug.render("ok"));
	}
}