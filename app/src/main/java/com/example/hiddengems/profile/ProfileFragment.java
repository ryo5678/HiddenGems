package com.example.hiddengems.profile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.example.hiddengems.Camera_Activity;
import com.example.hiddengems.R;
import com.example.hiddengems.databinding.FragmentProfileBinding;

import com.example.hiddengems.dataModels.Person.*;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.InputStream;

public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;
    FirebaseUser person;
    private FirebaseAuth mAuth;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();


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

        storageReference.child("images/profile/" + person.getUid().toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getContext()).load(uri).into(binding.profileImage);
            }
        });


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

        binding.MyGems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                action.gems();
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
    @GlideModule
    public class MyAppGlideModule extends AppGlideModule {

        @Override
        public void registerComponents(Context context, Glide glide, Registry registry) {
            // Register FirebaseImageLoader to handle StorageReference
            registry.append(StorageReference.class, InputStream.class,
                    new FirebaseImageLoader.Factory());
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
        void gems();
    }
}