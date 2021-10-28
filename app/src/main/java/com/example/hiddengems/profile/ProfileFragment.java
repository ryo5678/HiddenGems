package com.example.hiddengems.profile;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hiddengems.Camera_Activity;
import com.example.hiddengems.R;
import com.example.hiddengems.databinding.FragmentProfileBinding;

import com.example.hiddengems.dataModels.person.*;

public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;
    Users person;


    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(Users person) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putSerializable("Person",person);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            person = (Users)getArguments().getSerializable("Person");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater,container,false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Profile");

        binding.requestTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                action.tagRequest();
            }
        });

        binding.requestOperatingHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                action.operatingHoursRequest();
            }
        });

        binding.contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                action.contactUs();
            }
        });

        binding.requestRemovalLocations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                action.locationRemovalRequest();
            }
        });

        binding.editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { action.editProfile(person); }
        });

        binding.LikedLocations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                action.likedLocationsRequest();
            }
        });

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof profile){
            action = (profile) context;
        }
    }

    public static profile action;

    public interface profile{
        void tagRequest();
        void operatingHoursRequest();
        void contactUs();
        void locationRemovalRequest();
        void editProfile(Users person);
        void likedLocationsRequest();
    }
}