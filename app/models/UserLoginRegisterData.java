package models;

/**
 * Created by Virgil Barcan on 27.05.2015.
 * This class will hold the data taken from the Login or Register form
 */
public class UserLoginRegisterData {

    String email;
    String username;
    String password;

    public UserLoginRegisterData(){
        email = "";
        username = "";
        password = "";
    }

    public UserLoginRegisterData(String email, String username, String password){
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isValid() {
        boolean isValid = false;
        // We know for sure that if the user inserts into either email of username, any of these is different from ""
        String usernameEmail = (email != "") ? email : username;
        /*
        // Send an request to the DB to find if the user exists
        // NOT TESTED!!!
        String sqlQuery = "{? = call IS_VALID_USER(?, ?)}";
        CallableStatement statement = connection.prepareCall(sqlQuery);
        statement.registerOutParameter(1, Types.BOOL);
        isValid = statement.getBOOL(); // ???? Not sure this is the correct type
        statement.setString(2, usernameEmail);
        statement.setString(3, this.password);
        statement.execute();

        // if the user exists and the password is correct, result will be 1, otherwise 0
        */

        return isValid;
    }

    @Override
    public String toString() {
        return "UserLoginRegisterData{" +
                "password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
