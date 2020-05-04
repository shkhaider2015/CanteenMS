package com.example.canteenms.Models;

public class Order {

    private String dishName;
    private String dishQuantity;
    private String dishPrize;
    private String clintLocation;
    private String clintName;
    private String clintUID;
    private String clintPhotoUri;
    private boolean accepted;
    private boolean isNotify;


    public Order(String dishName, String dishQuantity, String dishPrize, String clintLocation, String clintName, String clintUID, String clintPhotoUri, boolean accepted, boolean isNotify) {
        this.dishName = dishName;
        this.dishQuantity = dishQuantity;
        this.dishPrize = dishPrize;
        this.clintLocation = clintLocation;
        this.clintName = clintName;
        this.clintUID = clintUID;
        this.clintPhotoUri = clintPhotoUri;
        this.accepted = accepted;
        this.isNotify = isNotify;
    }

    public String getDishName() {
        return dishName;
    }

    public String getDishQuantity() {
        return dishQuantity;
    }

    public String getDishPrize() {
        return dishPrize;
    }

    public String getClintLocation() {
        return clintLocation;
    }

    public String getClintName() {
        return clintName;
    }

    public String getClintUID() {
        return clintUID;
    }

    public String getClintPhotoUri() {
        return clintPhotoUri;
    }

    public boolean isAccepted()
    {
        return accepted;
    }

    public boolean isNotify() {
        return isNotify;
    }
}
