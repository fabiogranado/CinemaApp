package com.example.fabiogranado.cineapp.Model;

public class Products
{
    private String prodName, description, price, size, image, category, rid, date, time;

    public Products()
    {

    }


    public Products(String prodName, String description, String price, String image, String category, String rid, String size, String date, String time) {
        this.prodName = prodName;
        this.description = description;
        this.price = price;
        this.image = image;
        this.category = category;
        this.rid = rid;
        this.date = date;
        this.time = time;
        this.size = size;

    }

    public String getSize() { return size; }

    public void setSize(String size) { this.size = size; }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
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