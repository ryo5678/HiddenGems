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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ProfileFragment.profile, RegisterFragment.register, AddScreenFragment.add, SearchResultsFragment.location, EditProfileFragment.profile, LoginFragment.login, BottomNavigationView.OnNavigationItemSelectedListener,SearchScreenFragment.results {

    ActivityMainBinding binding;
    HomeFragment homeFragment = new HomeFragment();
    SearchScreenFragment searchFragment = new SearchScreenFragment();
    AddScreenFragment addFragment = new AddScreenFragment();
    Locations test = new Locations();
    private final String TAG = "TAG";
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        BottomNavigationView navView = findViewById(R.id.nav_view);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() == null){
            navView.setVisibility(View.GONE);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, new LoginFragment())
                    .commit();
        } else {
            navView.setVisibility(View.VISIBLE);
            navView.setOnNavigationItemSelectedListener(this);
            navView.setSelectedItemId(R.id.navigation_home);
        }


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
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new ProfileFragment()).commit();
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
    public void editProfile() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, new EditProfileFragment())
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
    public void logout() {
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setVisibility(View.GONE);
        FirebaseAuth.getInstance().signOut();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, LoginFragment.newInstance())
                .addToBackStack("Login")
                .commit();
    }

    @Override
    public void login(FirebaseUser user) {
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
    public void profile() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, new ProfileFragment())
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
    public void showLocation(String id) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, LocationFragment.newInstance(id))
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
    public void addLocation(String id) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, LocationFragment.newInstance(id))
                .addToBackStack("addPage")
                .commit();
    }

    @Override
    public void signUp(FirebaseUser user) {
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setVisibility(View.VISIBLE);
        navView.setOnNavigationItemSelectedListener(this);
        navView.setSelectedItemId(R.id.navigation_home);
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