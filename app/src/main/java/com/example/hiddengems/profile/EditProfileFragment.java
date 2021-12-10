package com.example.hiddengems.profile;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.hiddengems.R;
import com.example.hiddengems.dataModels.Person;
import com.example.hiddengems.databinding.FragmentEditProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;


public class EditProfileFragment extends Fragment {

    FragmentEditProfileBinding binding;
    FirebaseAuth mAuth;
    FirebaseUser person;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    ActivityResultLauncher<Intent> activityResultLauncher;
    byte[] data;

    public EditProfileFragment() {
        // Required empty public constructor
    }


    public static EditProfileFragment newInstance() {
        EditProfileFragment fragment = new EditProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == -1 && result.getData() != null) {
                            Bundle bundle = result.getData().getExtras();
                            Bitmap bitmap = (Bitmap) bundle.get("data");
                            binding.editPic.setImageBitmap(bitmap);
                            binding.editPic.setDrawingCacheEnabled(true);
                            binding.editPic.buildDrawingCache();
                            Bitmap placeholder = ((BitmapDrawable) binding.editPic.getDrawable()).getBitmap();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            placeholder.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            data = baos.toByteArray();
                        }
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEditProfileBinding.inflate(inflater,container,false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        person = mAuth.getCurrentUser();

        binding.editName.setText(person.getDisplayName());
        binding.editEmail.setText(person.getEmail());

        storageReference.child("images/profile/" + person.getUid().toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getContext()).load(uri).into(binding.editPic);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        binding.editPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CAMERA}, 5);
                } else {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    activityResultLauncher.launch(intent);
                }
            }
        });


        binding.saveProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.editName.getText().toString();
                String email = binding.editEmail.getText().toString();
                //String profilePic = binding.editPic.toString();

                if(name.isEmpty()  || email.isEmpty() /*|| profilePic.isEmpty()*/) {
                    missingInput(getActivity());
                }
                else {
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(name)
                            .build();
                    person.updateProfile(profileUpdates);
                    if (data != null) {
                        UploadTask uploadTask = storageReference.child("images/profile/" + person.getUid().toString()).putBytes(data);
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        Person.Users user = new Person.Users(name,person.getUid());
                        db.collection("users").document(person.getUid())
                                .set(user, SetOptions.merge())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                    }
                                });

                    }

                    action.profile();
                }
            }
        });
    }

    public void missingInput(Context context){
        Toast.makeText(context, getString(R.string.missing),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof profile) {
            action = (profile) context;
        }
    }

    public static profile action;

    public interface profile{
        void profile();
    }




}