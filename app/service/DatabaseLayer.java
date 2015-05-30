package service;

/**
 * Created by Virgil Barcan on 30.05.2015.
 */

import models.UserLoginData;
import play.db.DB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

/**
 * This class is used to handle all the database work required by other classes (by the controllers, for example)
 */
public class DatabaseLayer {

    public static boolean isValidUser(UserLoginData userLoginData) {
        boolean result = false;

        // We know for sure that if the user inserts into either email of username, any of these is different from ""
        String email = userLoginData.getEmail();
        String username = userLoginData.getUsername();
        String password = userLoginData.getPassword();

        String usernameEmail = (email != "") ? email : username;

        /**
        // Send an request to the DB to find if the user exists
        // NOT TESTED!!!
        String sqlQuery = "{? = call IS_VALID_USER(?, ?)}";
        Connection connection = DB.getConnection();
        CallableStatement statement = null;
        try {
            statement = connection.prepareCall(sqlQuery);
            statement.registerOutParameter(1, Types.BOOLEAN);
            statement.setString(2, usernameEmail);
            statement.setString(3, password);
            statement.execute();

            // if the user exists and the password is correct, result will be 1, otherwise 0
            result = statement.getBoolean(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        **/

        return result;
    }

}
