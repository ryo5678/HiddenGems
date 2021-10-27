package com.example.hiddengems.dataModels;

import java.io.Serializable;

public class menuItem implements Serializable {
    String dishName;
    int price;
    int calories;
    String[] Allergies;

    public menuItem(String dish, int Price, int Calories, String[] allergies) {
        this.dishName = dish;
        this.price = Price;
        this.calories = Calories;
        this.Allergies = allergies;
    }

}
