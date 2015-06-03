package models;

public class Post {

    private Integer post_id;
    private Integer transmitter;
    private Integer receiver;
    private Integer availability;
    private String message;
    private String at_date;

    public Post() {
        post_id = -1;
        transmitter = -1;
        receiver = -1;
        availability = -1;
        message = "";
        at_date = "";
    }

    public Post(Integer post_id, Integer transmitter, Integer receiver, Integer availability, String message, String at_date) {
        this.post_id = post_id;
        this.transmitter = transmitter;
        this.receiver = receiver;
        this.availability = availability;
        this.message = message;
        this.at_date = at_date;
    }

    public Integer getPostId() {
        return post_id;
    }

    public void setPostId(Integer post_id) {
        this.post_id = post_id;
    }

    public Integer getTransmitter() {
        return transmitter;
    }

    public void setTransmitter(Integer transmitter) {
        this.transmitter = transmitter;
    }

    public Integer getReceiver() {
        return receiver;
    }

    public void setReceiver(Integer receiver) {
        this.receiver = receiver;
    }

    public Integer getAvailability() {
        return availability;
    }

    public void setAvailability(Integer availability) {
        this.availability = availability;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAtDate() {
        return at_date;
    }

    public void setAtDate(String at_date) {
        this.at_date = at_date;
    }

    public String getStringDatalist() {
        return post_id + ", " + transmitter + ", " + receiver + ", " + availability + ", " + message + ", " + at_date;
    }

    public String toJSON() {
        return "\"post_id\" : " + "\"" + post_id + "\", " +
                "\"transmitter\" : "  + "\""  + transmitter + "\", " +
                "\"receiver\" : "  + "\""  + receiver + "\", " +
                "\"availability\" : "  + "\""  + availability + "\", " +
                "\"message\" : "  + "\""  + message + "\", " +
                "\"at_date\" : "  + "\""  + at_date + "\"";
    }

    @Override
    public String toString() {
        return "Post {" +
                "post_id='" + post_id + '\'' +
                ", transmitter='" + transmitter + '\'' +
                ", receiver='" + receiver + '\'' +
                ", availability='" + availability + '\'' +
                ", message='" + message + '\'' +
                ", at_date='" + at_date + '\'' +
                '}';
    }
}
