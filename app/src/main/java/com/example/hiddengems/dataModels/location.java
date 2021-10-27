package com.example.hiddengems.dataModels;

import android.media.Image;
import java.io.Serializable;
import java.util.ArrayList;


public class location implements Serializable {
    public String Name;
    public String Address;
    public String Category;
    public String[] Tags;
    //operatinghours[] Hours;
    public int startTime;
    public int endTime;
    public menuItem[] Menu;
    public Image[] Images;
    public boolean isEvent;
    public int startDate;
    public int endDate;
    public String Season;
    public int Likes;
    public int currentRating;
    public int numberofRatings;
    public String[] Comments;
    public String[] Reviews;


    public location(String Name, String Address, String Category, String[] Tags) {
        this.Name = Name;
        this.Address = Address;
        this.Category = Category;
        this.Tags = Tags;
    }



}




