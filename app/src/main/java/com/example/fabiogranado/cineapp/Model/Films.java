package com.example.fabiogranado.cineapp.Model;

public class Films {

    private String filmName, image, fid, time1, time2, time3,  date, time, info;

    public Films(){

    }

    public Films(String filmName, String image, String fid, String time1, String time2, String time3, String date, String time, String info) {
        this.filmName = filmName;
        this.image = image;
        this.fid = fid;
        this.time1 = time1;
        this.time2 = time2;
        this.time3 = time3;
        this.date = date;
        this.time = time;
        this.info = info;
    }

    public String getInfo() { return info; }

    public void setInfo(String info) { this.info = info; }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getTime1() {
        return time1;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }

    public String getTime2() {
        return time2;
    }

    public void setTime2(String time2) {
        this.time2 = time2;
    }

    public String getTime3() {
        return time3;
    }

    public void setTime3(String time3) {
        this.time3 = time3;
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
}



