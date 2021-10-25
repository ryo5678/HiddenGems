package com.example.hiddengems.dataModels;

import android.app.Application;

import java.util.ArrayList;

public class LocationList extends Application {

    public ArrayList<location> AllLocations;

    public ArrayList<location> getAllLocations() {
        return AllLocations;
    }

    public void AddLocation(location newLocation) {
        AllLocations.add(newLocation);
    }

}
