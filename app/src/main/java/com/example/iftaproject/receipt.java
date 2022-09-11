package com.example.iftaproject;

public class receipt {
    String type;
    String total;
    String date;
    String imageuri;

    public receipt(String type, String total, String date, String imageuri) {
        this.type = type;
        this.total = total;
        this.date = date;
        this.imageuri = imageuri;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
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
