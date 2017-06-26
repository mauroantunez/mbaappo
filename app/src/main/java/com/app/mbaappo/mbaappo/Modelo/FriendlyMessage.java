package com.app.mbaappo.mbaappo.Modelo;

public class FriendlyMessage {

    private String sender;
    //private String receptor;
    private String message;
    private String contentType = "";
    private String contentLocation = "";
    private String timestamp = "";

    public FriendlyMessage(){

    }

    //Constructor for plain text message
    public FriendlyMessage(String sender, String message, String time){
        this.sender = sender;
        this.message = message;
        this.timestamp = time;
      //  this.receptor = receptor;

    }




    public String getSender() {
        return sender;
    }
    public String getTimestamp(){return timestamp;}
    public String getMessage() {
        return message;
    }

    public String getContentLocation() {
        return contentLocation;
    }


    public String getContentType() {
        return contentType;
    }
}