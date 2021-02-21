package com.example.electricity;


public class zbmodel {
    String name,message,phone,image;
    Long rating;

    public zbmodel() {

    }

    public zbmodel(String name, String message, String phone, String image,Long rating) {
        this.name = name;
        this.message = message;
        this.phone = phone;
        this.image = image;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating= rating;
    }
}
