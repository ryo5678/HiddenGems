package com.example.hiddengems.dataModels;

import java.io.Serializable;

public class menuItem implements Serializable {
    String dishName;
    float price;
    int calories;
    String[] Allergies;

    public menuItem(String dish, float Price, int Calories, String[] allergies) {
        this.dishName = dish;
        this.price = Price;
        this.calories = Calories;
        this.Allergies = allergies;
    }

}
