package com.example.iftaproject;

public class receipt {
    String data;
    String date;
    String imageuri;

    public receipt(String data, String date, String imageuri) {
        this.data = data;
        this.date = date;
        this.imageuri = imageuri;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageuri() {
        return imageuri;
    }

    public void setImageuri(String imageuri) {
        this.imageuri = imageuri;
    }
}
