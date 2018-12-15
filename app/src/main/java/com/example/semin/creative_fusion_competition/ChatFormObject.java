package com.example.semin.creative_fusion_competition;

public class ChatFormObject {


    private String Sender;
    private String message;
    private String Date;


    public ChatFormObject() {
    }
    public ChatFormObject(String Date, String sender, String message){
        this.Date = Date;
        this.Sender = sender;
        this.message = message;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getSender() {
        return Sender;
    }

    public void setSender(String sender) {
        Sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
