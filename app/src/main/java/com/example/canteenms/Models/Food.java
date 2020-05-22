package com.example.canteenms.Models;

import java.io.Serializable;

public class Food {

    String foodName, foodPrice, foodImageUri;

    public Food() {
    }

    public Food(String foodName, String foodPrice, String foodImageUri) {
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.foodImageUri = foodImageUri;
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
}
