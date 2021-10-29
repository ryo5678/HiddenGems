package com.example.hiddengems.search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hiddengems.R;

import com.example.hiddengems.dataModels.Locations.*;
import com.example.hiddengems.databinding.FragmentAddScreenBinding;
import com.example.hiddengems.databinding.FragmentLocationBinding;

public class LocationFragment extends Fragment {

    FragmentLocationBinding binding;
    Location location;

    public LocationFragment() {
        // Required empty public constructor
    }


    public static LocationFragment newInstance(Location location) {
        LocationFragment fragment = new LocationFragment();
        Bundle args = new Bundle();
        args.putSerializable("Location",location);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            location = (Location) getArguments().getSerializable("Location");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLocationBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Code goes here
        // binding.(textview).setText(location.getName()) as an example
    }
}