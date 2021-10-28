package com.example.hiddengems.dataModels;

import android.media.Image;
import java.io.Serializable;
import java.util.ArrayList;

    public class location implements Serializable {

        String Name;
        String Address;
        String Category;
        String[] Tags;
        //operatinghours[] Hours;
        int startTime;
        int endTime;
        menuItem[] Menu;
        Image[] Images;
        boolean isEvent;
        int startDate;
        int endDate;
        String Season;
        int Likes;
        int currentRating;
        int numberofRatings;
        String[] Comments;
        String[] Reviews;

        public location(String Name, String Address, String Category, String[] Tags) {
            this.Name = Name;
            this.Address = Address;
            this.Category = Category;
            this.Tags = Tags;
        }


    }





