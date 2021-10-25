package com.example.hiddengems;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.hiddengems.databinding.ActivityMainBinding;
import com.example.hiddengems.profile.*;
import com.example.hiddengems.home.*;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements ProfileFragment.profile {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_search, R.id.navigation_add, R.id.navigation_map, R.id.navigation_profile)
                .build();
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        /*getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, new HomeFragment())
                .commit();*/

    }

    @Override
    public void tagRequest() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, RequestTagsFragment.newInstance())
                .addToBackStack("TagRequest")
                .commit();
    }

    @Override
    public void operatingHoursRequest() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, HoursChangeFragment.newInstance())
                .addToBackStack("operatingHoursRequest")
                .commit();
    }

    @Override
    public void contactUs() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, ContactUsFragment.newInstance())
                .addToBackStack("contactUs")
                .commit();
    }
}