package models;

/**
 * Created by Virgil Barcan on 27.05.2015.
 * This class will hold the data taken from the Login or Register form
 */
public class UserRegisterData {

    private String email;
    private String username;
    private String password;

    public UserRegisterData(){
        email = "";
        username = "";
        password = "";
    }

    public UserRegisterData(String email, String username, String password){
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

    /**
     * This function is used to check if the data given by the user is valid:
     * - username/email exists in the DB
     * - correct password for the username/email
     * @return 0 = valid, 1 = invalid username, 2 = invalid email
     */
    public int isValid() {
        int isValid = 1;
        /*
        // Send an request to the DB to find if the user already exists
        // NOT TESTED!!!
        String sqlQuery = "{? = call IS_VALID_USER(?, ?, ?)}";
        CallableStatement statement = connection.prepareCall(sqlQuery);
        statement.registerOutParameter(1, Types.BOOL);
        isValid = statement.getBOOL(); // ???? Not sure this is the correct type
        statement.setString(2, this.username);
        statement.setString(2, this.email);
        statement.setString(3, this.password);
        statement.execute();

        // if the user exists, this is an error, and the user will have to reintroduce the data in order to have a valid new user
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
