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

public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;



    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
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
    }
}