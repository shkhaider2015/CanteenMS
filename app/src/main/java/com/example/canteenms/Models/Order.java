package com.example.canteenms.Models;

public class Order {

    private String dishName;
    private String dishQuantity;
    private String dishPrize;
    private String totalDishPrize;
    private String dishImageUri;
    private String clintLocation;
    private String clintName;
    private String clintUID;
    private String clintPhotoUri;
    private String orderTime;
    private boolean accepted;
    private boolean notify;
    private boolean cancelled;
    private boolean completed;



    public Order()
    {

    }

    public Order(String dishName, String dishQuantity, String dishPrize, String totalDishPrize, String dishImageUri,
                 String clintLocation, String clintName, String clintUID,
                 String clintPhotoUri, String orderTime, boolean accepted, boolean notify,
                 boolean cancelled, boolean completed) {

        this.dishName = dishName;
        this.dishQuantity = dishQuantity;
        this.dishPrize = dishPrize;
        this.totalDishPrize = totalDishPrize;
        this.dishImageUri = dishImageUri;
        this.clintLocation = clintLocation;
        this.clintName = clintName;
        this.clintUID = clintUID;
        this.clintPhotoUri = clintPhotoUri;
        this.orderTime = orderTime;
        this.accepted = accepted;
        this.notify = notify;
        this.cancelled = cancelled;
        this.completed = completed;
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

    public String getDishImageUri() {
        return dishImageUri;
    }

    public String getTotalDishPrize() {
        return totalDishPrize;
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
        return notify;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public boolean isCompleted() {
        return completed;
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
                ", isNotify=" + notify +
                ", isCancelled=" + cancelled +
                ", isCompleted=" + completed +
                '}';
    }
}
