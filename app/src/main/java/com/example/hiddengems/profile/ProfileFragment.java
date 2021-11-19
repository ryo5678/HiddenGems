package com.example.hiddengems.profile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.hiddengems.Camera_Activity;
import com.example.hiddengems.R;
import com.example.hiddengems.databinding.FragmentProfileBinding;

import com.example.hiddengems.dataModels.Person.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;

public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;
    FirebaseUser person;
    private FirebaseAuth mAuth;


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

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        person = mAuth.getCurrentUser();


        binding.profileName.setText(person.getDisplayName());
        binding.profileEmail.setText(person.getEmail());
        /*
        File imgFile = new File(person.getProfilePic());
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            binding.profileImage.setImageBitmap(myBitmap);
        }*/


        binding.profileLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                action.logout();
            }
        });
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
            public void onClick(View view) { action.editProfile(); }
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
        void editProfile();
        void likedLocationsRequest();
        void logout();
    }
}