package com.example.canteenms.Models;

import java.io.Serializable;

public class Food implements Serializable {

    private String foodName, foodPrice, foodImageUri;
    private boolean availability;

    public Food() {
    }

    public Food(String foodName, String foodPrice, String foodImageUri, boolean availability) {
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.foodImageUri = foodImageUri;
        this.availability = availability;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public String getFoodImageUri() {
        return foodImageUri;
    }

    public boolean isAvailability() {
        return availability;
    }
}
