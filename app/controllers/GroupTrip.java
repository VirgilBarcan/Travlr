package controllers;

import service.DatabaseLayer;
import play.*;
import play.mvc.*;
import play.data.*;
import views.html.*;
import java.util.ArrayList;

public class GroupTrip extends Controller{
	
	public static Result index() {
		int userId = 183; //TO DO: get logon user
		ArrayList<String> friends = DatabaseLayer.getUsernameListFromDb(DatabaseLayer.getFriends(userId));
        return ok(group_trip.render(friends));
	}

	public static Result postIndex() {
		DynamicForm dynamicForm = Form.form().bindFromRequest();
        return ok("I got data!");
	}
}
