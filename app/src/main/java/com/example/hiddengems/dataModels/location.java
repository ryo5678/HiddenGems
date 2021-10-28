package com.example.hiddengems.dataModels;

import android.media.Image;
import java.io.Serializable;
import java.util.ArrayList;



public class location implements Serializable {
    public String Name;
    public String Address;
    public String Category;
    public ArrayList<String> Tags;
    //operatinghours[] Hours;
    public int startTime;
    public int endTime;
    public ArrayList<menuItem> Menu;
    public ArrayList<Image> Images;
    public boolean isEvent;
    public int startDate;
    public int endDate;
    public String Season;
    public int Likes;
    public int currentRating;
    public int numberofRatings;
    public ArrayList<String> Comments;
    public ArrayList<String> Reviews;


    public location(String Name, String Address, String Category) {
        this.Name = Name;
        this.Address = Address;
        this.Category = Category;
        startTime = 0;
        endTime = 0;
        isEvent = false;
        startDate = 0;
        endDate = 0;
        Season = "";
        Likes = 0;
        currentRating = 0;
        numberofRatings = 0;
    }
    public void setName(String Name){
        this.Name = Name;
    }
    public void setAddress(String Address){
        this.Address = Address;
    }
    public void setCategory(String Category){
        this.Category= Category;
    }
    public void addTag(String Tag){
        this.Tags.add(Tag);
    }
    public void setStartTime(int startTime){
        this.startTime = startTime;
    }
    public void setEndTime(int endTime){
        this.endTime = endTime;
    }
    public void addImage(Image pic){
        this.Images.add(pic);
    }
    public void addMenuItem(menuItem Item){
        this.Menu.add(Item);
    }
    public void setIsEvent(boolean isEvent){
        this.isEvent = isEvent;
    }
    public void setStartDate(int startDate){
        this.startDate = startDate;
    }
    public void setEndDate(int endDate){
        this.endDate = endDate;
    }
    public void setSeason(String Season){
        this.Season = Season;
    }
    public int getLikes(){
        return this.Likes;
    }
    public void setLikes(int Likes){
        this.Likes = Likes;
    }
    public int getCurrentRating(){
        return this.currentRating;
    }
    public void setCurrentRating(int currentRating){
        this.currentRating = currentRating;
    }
    public int getNumberofRatings(){
        return this.numberofRatings;
    }
    public void setNumberofRatings(int numberofRatings){
        this.numberofRatings = numberofRatings;
    }
    public void addComment(String comment){
        this.Comments.add(comment);
    }
    public void addReview(String review){
        this.Reviews.add(review);
    }



}




