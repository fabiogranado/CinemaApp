package com.example.fabiogranado.cineapp.Model;

public class Orders {

    private String rid, name, email, date, time,  totalAmount;


    public Orders() {

    }

    public Orders(String rid, String name, String email, String date, String time, String oid, String totalAmount) {
        this.rid = rid;
        this.name = name;
        this.email = email;
        this.date = date;
        this.time = time;
        this.totalAmount = totalAmount;
    }

    public String getRid() {return rid;}

    public void setRid(String rid) {this.rid = rid;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}

