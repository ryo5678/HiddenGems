package com.example.hiddengems.profile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

//import com.bumptech.glide.Glide;
//import com.bumptech.glide.Registry;
//import com.bumptech.glide.annotation.GlideModule;
//import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.Glide;
import com.example.hiddengems.Camera_Activity;
import com.example.hiddengems.R;
import com.example.hiddengems.databinding.FragmentProfileBinding;

import com.example.hiddengems.dataModels.Person.*;
//import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.example.hiddengems.search.LocationFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;
    FirebaseUser person;
    private FirebaseAuth mAuth;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    boolean isMod;
    String cID;
    Users user;


    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String id) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString("uid",id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cID = getArguments().getString("uid");
            Log.d("TAG",cID);
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

        if(cID != null) {
            Log.d("TAG","start of cID if");

            DocumentReference docRef = db.collection("users").document(cID);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            user = new Users(document.getString("name")/*, value.getString("photo")*/);
                            Log.d("TAG", String.valueOf(user));
                            binding.profileName.setText(user.getDisplayName());
                            binding.profileLogout.setVisibility(View.GONE);
                            binding.editProfileButton.setVisibility(View.GONE);
                        } else {
                        }
                    } else {
                        Log.d("TAG", "get failed with ", task.getException());
                    }
                }
            });
        } else {
            binding.profileName.setText(person.getDisplayName());
            binding.profileEmail.setText(person.getEmail());
            binding.profileLogout.setVisibility(View.VISIBLE);
            binding.editProfileButton.setVisibility(View.VISIBLE);
        }

        if(person.getUid().equals("YPKp0avJHTPNI30gT7Rcgf9jme62")) {
            isMod = true;
        } else {
            isMod = false;
        }

        if (isMod == true) {
            binding.profileBan.setVisibility(View.VISIBLE);
        } else {
            binding.profileBan.setVisibility(View.GONE);
        }

        if (person.getDisplayName().equals(binding.profileName.getText())) {
            binding.profileReport.setVisibility(View.GONE);
        } else {
            binding.profileReport.setVisibility(View.VISIBLE);
        }

        binding.profileReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Report a user!");


                final EditText input = new EditText(getActivity());

                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);


                builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String reportText;
                        reportText = input.getText().toString();

                        HashMap<String, Object> userReport = new HashMap<>();
                        userReport.put("userID",person.getUid());
                        userReport.put("report",reportText);

                        db.collection("userReports")
                                .add(userReport)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                    }
                                });


                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        binding.profileBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Ban a user!");


                final EditText input = new EditText(getActivity());

                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);


                builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String banText;
                        banText = input.getText().toString();

                        HashMap<String, Object> userBan = new HashMap<>();
                        userBan.put("ban_reason",banText);
                        userBan.put("banned",true);

                        db.collection("users").document(cID)
                                .set(userBan, SetOptions.merge())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                    }
                                });



                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

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

        binding.verified.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                action.requestVerification();
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
   /* @GlideModule
    public class MyAppGlideModule extends AppGlideModule {

        @Override
        public void registerComponents(Context context, Glide glide, Registry registry) {
            // Register FirebaseImageLoader to handle StorageReference
            registry.append(StorageReference.class, InputStream.class,
                    new FirebaseImageLoader.Factory());
        }
    }*/

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
        void requestVerification();
    }
}