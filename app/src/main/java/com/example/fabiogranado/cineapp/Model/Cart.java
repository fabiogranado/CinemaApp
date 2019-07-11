package com.example.fabiogranado.cineapp.Model;

public class Cart {
    private String rid;
    private String fid;
    private String prodName;
    private String price;
    private String description;
    private String size;
    private String quantity;
    private String discount;
    private String filmName;
    private String poster ;


    public Cart(){


    }

    public Cart(String rid, String prodName, String price, String size, String quantity, String description, String discount, String fid, String filmName, String poster) {

        this.rid = rid;
        this.prodName = prodName;
        this.price = price;
        this.size = size;
        this.quantity = quantity;
        this.discount = discount;
        this.fid = fid;
        this.filmName = filmName;
        this.poster = poster;
        this.description = description;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
