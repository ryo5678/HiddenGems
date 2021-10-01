package com.example.hiddengems;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class homeScreen extends AppCompatActivity {

    public String name = "Guest";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        this.setTitle("Welcome " + name + "!");
    }
}