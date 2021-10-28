package com.example.hiddengems;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.MenuItem;

import com.example.hiddengems.databinding.ActivityMainBinding;
import com.example.hiddengems.profile.*;

import com.example.hiddengems.home.*;
import com.example.hiddengems.search.SearchScreenFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.example.hiddengems.dataModels.person.*;

public class MainActivity extends AppCompatActivity implements ProfileFragment.profile, BottomNavigationView.OnNavigationItemSelectedListener, AddScreenFragment.add {

    ActivityMainBinding binding;
    HomeFragment homeFragment = new HomeFragment();
    SearchScreenFragment searchFragment = new SearchScreenFragment();
    AddScreenFragment addFragment = new AddScreenFragment();
    ProfileFragment profileFragment = new ProfileFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(this);
        navView.setSelectedItemId(R.id.navigation_home);


        /*getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, new HomeFragment())
                .commit();*/

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
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, profileFragment).commit();
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
                .replace(R.id.rootView, LikedLocationsFragment.newInstance())
                .addToBackStack("likedLocationsRequest")
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

    /*
    @Override
    public void search() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, SearchScreenFragment.newInstance())
                .addToBackStack("Search")
                .commit();
    }
    */
}