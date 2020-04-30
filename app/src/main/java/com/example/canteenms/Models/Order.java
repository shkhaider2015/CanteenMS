package com.example.canteenms.Models;

public class Order {

    private String dishName;
    private String dishQuantity;
    private String dishPrize;
    private String clintLocation;
    private String clintName;
    private String clintUID;
    private boolean accepted;


    public Order(String dishName, String dishQuantity, String dishPrize, String clintLocation, String clintName, String clintUID, boolean accepted) {
        this.dishName = dishName;
        this.dishQuantity = dishQuantity;
        this.dishPrize = dishPrize;
        this.clintLocation = clintLocation;
        this.clintName = clintName;
        this.clintUID = clintUID;
        this.accepted = accepted;
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
    public boolean isAccepted()
    {
        return accepted;
    }
}
