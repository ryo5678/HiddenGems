package com.example.hiddengems.account;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hiddengems.R;
import com.example.hiddengems.databinding.FragmentLoginBinding;
import com.example.hiddengems.databinding.FragmentRegisterBinding;

import com.example.hiddengems.dataModels.Person.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class RegisterFragment extends Fragment {

    FragmentRegisterBinding binding;
    Users user;
    String firstName;
    String lastName;
    String password;
    String confirm;
    String email;
    private FirebaseAuth mAuth;
    private final String TAG = "TAG";

    public RegisterFragment() {
        // Required empty public constructor
    }


    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
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
        binding = FragmentRegisterBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Register For Hidden Gems");

        binding.registerButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstName = binding.registerEditFirst.getText().toString();
                lastName = binding.registerEditLast.getText().toString();
                email = binding.registerEditEmail.getText().toString();
                password = binding.registerEditPassword.getText().toString();
                confirm = binding.registerEditConfirm.getText().toString();
                if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() ||password.isEmpty() || confirm.isEmpty()) {
                    missingInput(getActivity());
                } else {
                    if (password.equals(confirm)) {
                        mAuth = FirebaseAuth.getInstance();
                        mAuth.createUserWithEmailAndPassword(email,password)
                                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        if (task.isSuccessful()) {
                                            Log.d(TAG,"Account created.");
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                    .setDisplayName(firstName + " " + lastName)
                                                    .build();
                                            user.updateProfile(profileUpdates);
                                            action.signUp(user);
                                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                                            List<String> blockedList = new ArrayList<>();
                                            HashMap<String, Object> newUser = new HashMap<>();
                                            newUser.put("UID",user.getUid());
                                            newUser.put("name",firstName + " " + lastName);
                                            newUser.put("photo",user.getPhotoUrl());
                                            newUser.put("banned",false);
                                            newUser.put("ban_reason","");
                                            newUser.put("verified",false);

                                            db.collection("users").document(user.getUid())
                                                    .set(newUser)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {

                                                        }
                                                    });

                                        } else {

                                        }
                                    }
                                });
                    } else {
                        badPassword(getActivity());
                    }
                }
            }
        });
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof register){
            action = (register) context;
        }
    }

    public static register action;

    public interface register{
        void signUp(FirebaseUser user);
    }
    public void missingInput(Context context){
        Toast.makeText(context, getString(R.string.loginMissing),Toast.LENGTH_SHORT).show();
    }
    public void badPassword(Context context){
        Toast.makeText(context, "The passwords do not match. Please try again.",Toast.LENGTH_SHORT).show();
    }
}
