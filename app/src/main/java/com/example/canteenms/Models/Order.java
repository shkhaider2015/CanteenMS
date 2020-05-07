package com.example.canteenms.Models;

public class Order {

    private String dishName;
    private String dishQuantity;
    private String dishPrize;
    private String clintLocation;
    private String clintName;
    private String clintUID;
    private String clintPhotoUri;
    private String orderTime;
    private boolean accepted;
    private boolean isNotify;
    private boolean isCancelled;
    private boolean isCompleted;

    public Order()
    {

    }

    public Order(String dishName, String dishQuantity, String dishPrize,
                 String clintLocation, String clintName, String clintUID,
                 String clintPhotoUri, String orderTime, boolean accepted, boolean isNotify,
                 boolean isCancelled, boolean isCompleted) {

        this.dishName = dishName;
        this.dishQuantity = dishQuantity;
        this.dishPrize = dishPrize;
        this.clintLocation = clintLocation;
        this.clintName = clintName;
        this.clintUID = clintUID;
        this.clintPhotoUri = clintPhotoUri;
        this.orderTime = orderTime;
        this.accepted = accepted;
        this.isNotify = isNotify;
        this.isCancelled = isCancelled;
        this.isCompleted = isCompleted;
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

    public String getOrderTime() {
        return orderTime;
    }

    public boolean isAccepted()
    {
        return accepted;
    }

    public boolean isNotify() {
        return isNotify;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    @Override
    public String toString() {
        return "Order{" +
                "dishName='" + dishName + '\'' +
                ", dishQuantity='" + dishQuantity + '\'' +
                ", dishPrize='" + dishPrize + '\'' +
                ", clintLocation='" + clintLocation + '\'' +
                ", clintName='" + clintName + '\'' +
                ", clintUID='" + clintUID + '\'' +
                ", clintPhotoUri='" + clintPhotoUri + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", accepted=" + accepted +
                ", isNotify=" + isNotify +
                ", isCancelled=" + isCancelled +
                ", isCompleted=" + isCompleted +
                '}';
    }
}
