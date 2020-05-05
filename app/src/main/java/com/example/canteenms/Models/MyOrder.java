package com.example.canteenms.Models;

public class MyOrder {

    private String dishName;
    private String dishQuantity;
    private String clintLocation;
    private int imageId;
    private boolean isAccepted;

    public MyOrder(String dishName, String dishQuantity, String clintLocation, int imageId, boolean isAccepted) {
        this.dishName = dishName;
        this.dishQuantity = dishQuantity;
        this.clintLocation = clintLocation;
        this.imageId = imageId;
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

    public int getImageId() {
        return imageId;
    }

    public boolean isAccepted() {
        return isAccepted;
    }
}
