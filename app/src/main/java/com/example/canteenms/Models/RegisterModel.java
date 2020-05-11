package com.example.canteenms.Models;

public class RegisterModel {

    private String image, name, email, uid;

    public RegisterModel(String image, String name, String email, String uid) {
        this.image = image;
        this.name = name;
        this.email = email;
        this.uid = uid;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUid() {
        return uid;
    }
}
