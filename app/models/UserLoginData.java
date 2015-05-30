package models;

import service.DatabaseLayer;

/**
 * Created by Virgil Barcan on 27.05.2015.
 * This class will hold the data taken from the Login or Register form
 */
public class UserLoginData {

    String email;
    String username;
    String password;

    public UserLoginData(){
        email = "";
        username = "";
        password = "";
    }

    public UserLoginData(String email, String username, String password){
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

        // check if the user is a valid one (if it exists in the DB)
        isValid = DatabaseLayer.isValidUser(this);

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
