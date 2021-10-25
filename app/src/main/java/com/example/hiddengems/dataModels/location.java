package com.example.hiddengems.dataModels;

import android.media.Image;
import android.provider.ContactsContract;

import java.util.ArrayList;

public class location {
    public String Name;
    public String Address;
    String Category;
    ArrayList<String> Tags;
    //operatinghours[] Hours;
    int startTime;
    int endTime;
    ArrayList<menuItem> Menu;
    ArrayList<Image> Images;
    boolean isEvent;
    int startDate;
    int endDate;
    String Season;
    int Likes;
    String Rating;
    ArrayList<String> Comments;
    ArrayList<String> Reviews;

    public location(String name, String address, String category, String[] tags) {
        Name = name;
        Address = address;
        Category = category;
        for(int i = 0; i < tags.length ; i++) {
            Tags.add(tags[i]);
        }
        startTime = 0;
        endTime = 0;
        Menu = null;
        Images = null;
        isEvent = false;
        startDate = 0;
        endDate = 0;
        Season = "";
        Likes = 0;
        Rating = "";
        Comments = null;
        Reviews = null;
    }

}



