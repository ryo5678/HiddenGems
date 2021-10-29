package com.example.hiddengems.profile;

import android.app.Person;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hiddengems.R;

import com.example.hiddengems.dataModels.Person.*;
import com.example.hiddengems.databinding.FragmentEditProfileBinding;
import com.example.hiddengems.databinding.FragmentProfileBinding;


public class EditProfileFragment extends Fragment {

    Users person;
    FragmentEditProfileBinding binding;

    public EditProfileFragment() {
        // Required empty public constructor
    }


    public static EditProfileFragment newInstance(Users person) {
        EditProfileFragment fragment = new EditProfileFragment();
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
        // Inflate the layout for this fragment
        binding = FragmentEditProfileBinding.inflate(inflater,container,false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.editName.setText(person.getDisplayName());
        binding.editEmail.setText(person.getEmail());

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
                    person.setName(name);
                    person.setEmail(email);
                    //person.setProfilePic(profilePic);
                    action.profile(person);
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
        void profile(Users person);
    }




}