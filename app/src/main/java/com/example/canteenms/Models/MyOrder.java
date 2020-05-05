package com.example.canteenms.Models;

public class MyOrder {

    private String dishName;
    private String dishQuantity;
    private String clintLocation;
    private boolean isAccepted;

    public MyOrder(String dishName, String dishQuantity, String clintLocation, boolean isAccepted) {
        this.dishName = dishName;
        this.dishQuantity = dishQuantity;
        this.clintLocation = clintLocation;
        this.isAccepted = isAccepted;
    }

    public String getDishName() {
        return dishName;
    }

    public String getDishQuantity() {
        return dishQuantity;
    }

    public String getClintLocation() {
        return clintLocation;
    }

    public boolean isAccepted() {
        return isAccepted;
    }
}
