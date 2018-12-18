package com.example.semin.creative_fusion_competition;

public class CalenderFormObject {



    private int year;
    private int month;
    private int day;
    private int status;
    private String comment;


    public CalenderFormObject(int year, int month, int day, int status, String comment) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.status = status;
        this.comment = comment;
    }
    public CalenderFormObject(){

    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


}
