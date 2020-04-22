package com.example.canteenms.Models;

import java.io.Serializable;

public class Dish implements Serializable {

    private String dishName;
    private int dishPrize, image;

    public Dish(String dishName, int dishPrize, int image) {
        this.dishName = dishName;
        this.dishPrize = dishPrize;
        this.image = image;
    }

    public String getDishName() {
        return dishName;
    }

    public int getDishPrize() {
        return dishPrize;
    }

    public int getImage() {
        return image;
    }
}
