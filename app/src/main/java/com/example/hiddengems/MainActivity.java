package com.example.hiddengems;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.hiddengems.databinding.ActivityMainBinding;
import com.example.hiddengems.profile.*;

public class MainActivity extends AppCompatActivity implements ProfileFragment.profile {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, new ProfileFragment())
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
    public void locationRemovalRequest() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, LocationRemovalFragment.newInstance())
                .addToBackStack("locationRemovalRequest")
                .commit();
    }

    /*@Override
    public void reportPage() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, ReportPageFragment.newInstance())
                .addToBackStack("reportPage")
                .commit();
    }*/
}