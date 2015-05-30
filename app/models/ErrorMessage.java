package models;

/**
 * Created by Virgil Barcan on 30.05.2015.
 */
public enum ErrorMessage {
    INVALID_USERNAME_PASSWORD {
        public String toString(){
            return "Invalid username/password! Please try again!";
        }
    },
    USERNAME_TAKEN {
        public String toString(){
            return "The username is already taken! Please try another one!";
        }
    },
    EMAIL_USED {
        public String toString(){
            return "The email is already in our database! If you forgot your password, click Recover!";
        }
    }
}
