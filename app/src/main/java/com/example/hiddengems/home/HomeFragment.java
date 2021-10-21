package com.example.hiddengems.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.hiddengems.R;

public class HomeFragment extends AppCompatActivity {

    public String name = "Guest";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        this.setTitle("Welcome " + name + "!");
    }
}