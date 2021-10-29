package com.example.hiddengems.dataModels;

import android.media.Image;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


public class Locations {

    private static final HashMap<String, ArrayList<Location>> locations = new HashMap<String, ArrayList<Location>>() {{
        put("Locations", new ArrayList<Location>() {{
            add(new Location("Heist Brewery","2909 N Davidson St STE 200, Charlotte, NC 28205","Bar"));
            add(new Location("Neighborhood Theatre", "511 E 36th St, Charlotte, NC 28205", "Venue"));
            add(new Location("Cabo Fish Taco","11611 N Community House Rd, Charlotte, NC 28277", "Restaurant"));
            add(new Location("Arbys","117 Sierra Drive","Restaurant"));
            add(new Location("Arrowhead Park","343 Industry Lane","Park"));
            add(new Location("The Bacon Boys","1776 Merica Blv","Restaurant"));
            add(new Location("The White House"," 1600 Pennsylvania Avenue NW, Washington, DC 20500","Historical"));
        }});
        put("Gems", new ArrayList<Location>() {{
            add(new Location("Heist Brewery","2909 N Davidson St STE 200, Charlotte, NC 28205","Bar"));
            add(new Location("Neighborhood Theatre", "511 E 36th St, Charlotte, NC 28205", "Venue"));
        }});
    }};

    public static ArrayList<Location> getLocations(String key){
        if(locations.containsKey(key)) {
            return locations.get(key);
        } else {
            return new ArrayList<Location>();
        }
    }

    public static class Location implements Serializable {

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


        public Location(String Name, String Address, String Category) {
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

        @Override
        public String toString() {
            return "Gems{" +
                    "Name='" + Name + '\'' +
                    ", Address='" + Address + '\'' +
                    ", Category='" + Category + '\'' +
                    ", Tags=" + Tags +
                    ", startTime=" + startTime +
                    ", endTime=" + endTime +
                    ", Menu=" + Menu +
                    ", Images=" + Images +
                    ", isEvent=" + isEvent +
                    ", startDate=" + startDate +
                    ", endDate=" + endDate +
                    ", Season='" + Season + '\'' +
                    ", Likes=" + Likes +
                    ", currentRating=" + currentRating +
                    ", numberofRatings=" + numberofRatings +
                    ", Comments=" + Comments +
                    ", Reviews=" + Reviews +
                    '}';
        }

        public String getName() {
            return Name;
        }

        public String getAddress() {
            return Address;
        }

        public String getCategory() {
            return Category;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public void setCategory(String Category) {
            this.Category = Category;
        }

        public void addTag(String Tag) {
            this.Tags.add(Tag);
        }

        public void setStartTime(int startTime) {
            this.startTime = startTime;
        }

        public void setEndTime(int endTime) {
            this.endTime = endTime;
        }

        public void addImage(Image pic) {
            this.Images.add(pic);
        }

        public void addMenuItem(menuItem Item) {
            this.Menu.add(Item);
        }

        public void setIsEvent(boolean isEvent) {
            this.isEvent = isEvent;
        }

        public void setStartDate(int startDate) {
            this.startDate = startDate;
        }

        public void setEndDate(int endDate) {
            this.endDate = endDate;
        }

        public void setSeason(String Season) {
            this.Season = Season;
        }

        public int getLikes() {
            return this.Likes;
        }

        public void setLikes(int Likes) {
            this.Likes = Likes;
        }

        public int getCurrentRating() {
            return this.currentRating;
        }

        public void setCurrentRating(int currentRating) {
            this.currentRating = currentRating;
        }

        public int getNumberofRatings() {
            return this.numberofRatings;
        }

        public void setNumberofRatings(int numberofRatings) {
            this.numberofRatings = numberofRatings;
        }

        public void addComment(String comment) {
            this.Comments.add(comment);
        }

        public void addReview(String review) {
            this.Reviews.add(review);
        }
    }



}




