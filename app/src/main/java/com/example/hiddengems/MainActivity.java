package com.example.hiddengems;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.hiddengems.account.LoginFragment;
import com.example.hiddengems.account.RegisterFragment;
import com.example.hiddengems.dataModels.Locations;
import com.example.hiddengems.databinding.ActivityMainBinding;
import com.example.hiddengems.profile.*;

import com.example.hiddengems.home.*;
import com.example.hiddengems.search.LocationFragment;
import com.example.hiddengems.search.SearchResultsFragment;
import com.example.hiddengems.search.SearchScreenFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.example.hiddengems.dataModels.Person.*;
import com.example.hiddengems.dataModels.Locations.*;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ProfileFragment.profile, AddScreenFragment.add, SearchResultsFragment.location, EditProfileFragment.profile, LoginFragment.login, BottomNavigationView.OnNavigationItemSelectedListener,SearchScreenFragment.results {

    ActivityMainBinding binding;
    Users person2;
    HomeFragment homeFragment = new HomeFragment();
    SearchScreenFragment searchFragment = new SearchScreenFragment();
    AddScreenFragment addFragment = new AddScreenFragment();
    ProfileFragment profileFragment = ProfileFragment.newInstance(person2);
    Locations test = new Locations();
    private final String TAG = "TAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        BottomNavigationView navView = findViewById(R.id.nav_view);

        navView.setVisibility(View.GONE);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainerView, new LoginFragment())
                .commit();


    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.navigation_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, homeFragment).commit();
                return true;
            case R.id.navigation_search:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, searchFragment).commit();
                return true;
            case R.id.navigation_add:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, addFragment).commit();
                return true;
            case R.id.navigation_map:
                return true;
            case R.id.navigation_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, ProfileFragment.newInstance(person2)).commit();
                return true;
        }
        return false;
    }

    @Override
    public void tagRequest() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, RequestTagsFragment.newInstance())
                .addToBackStack("TagRequest")
                .commit();
    }

    @Override
    public void operatingHoursRequest() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, HoursChangeFragment.newInstance())
                .addToBackStack("operatingHoursRequest")
                .commit();
    }

    @Override
    public void contactUs() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, ContactUsFragment.newInstance())
                .addToBackStack("contactUs")
                .commit();
    }

    @Override
    public void locationRemovalRequest() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, LocationRemovalFragment.newInstance())
                .addToBackStack("locationRemovalRequest")
                .commit();
    }

    @Override
    public void editProfile(Users person) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, EditProfileFragment.newInstance(person))
                .addToBackStack("editProfile")
                .commit();
    }

    @Override
    public void likedLocationsRequest() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, LikedLocationsFragment.newInstance())
                .addToBackStack("likedLocationsRequest")
                .commit();
    }

    @Override
    public void login(Users user) {
        person2 = user;
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setVisibility(View.VISIBLE);
        navView.setOnNavigationItemSelectedListener(this);
        navView.setSelectedItemId(R.id.navigation_home);
    }

    @Override
    public void register() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, RegisterFragment.newInstance())
                .addToBackStack("Register")
                .commit();
    }

    @Override
    public void profile(Users person) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, ProfileFragment.newInstance(person))
                .addToBackStack("Profile")
                .commit();
    }

    @Override
    public void searchResults(ArrayList<Location> locations) {
        Log.d(TAG,locations.get(0).toString());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, SearchResultsFragment.newInstance(locations))
                .addToBackStack("SearchResults")
                .commit();
    }

    @Override
    public void showLocation(Location location) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, LocationFragment.newInstance(location))
                .addToBackStack("ShowLocation")
                .commit();
    }
    /*
    @Override
    public void search() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, SearchScreenFragment.newInstance())
                .addToBackStack("Search")
                .commit();
    }
    */

    @Override
    public void addLocation(Location locations) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, LocationFragment.newInstance(locations))
                .addToBackStack("addPage")
                .commit();
    }

    /*
    @Override
    public void reportPage() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, ReportPageFragment.newInstance())
                .addToBackStack("reportPage")
                .commit();
    }*/


}