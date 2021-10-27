package com.example.hiddengems;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.hiddengems.dataModels.location;
import com.example.hiddengems.databinding.ActivityMainBinding;
import com.example.hiddengems.profile.*;
import com.example.hiddengems.search.SearchScreenFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ProfileFragment.profile {

    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, new SearchScreenFragment())
                .commit();

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
    /*
    public void search() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, SearchScreenFragment.newInstance())
                .addToBackStack("Search")
                .commit();
    }
    */
}