package com.example.hiddengems.profile;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hiddengems.R;
import com.example.hiddengems.databinding.FragmentHoursChangeBinding;
import com.example.hiddengems.databinding.FragmentLocationRemovalBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class LocationRemovalFragment extends Fragment {

    FragmentLocationRemovalBinding binding;
    String id;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();
    FirebaseAuth mAuth;
    FirebaseUser user;

    public LocationRemovalFragment() {
        // Required empty public constructor
    }

    public static LocationRemovalFragment newInstance() {
        LocationRemovalFragment fragment = new LocationRemovalFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLocationRemovalBinding.inflate(inflater,container,false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Remove Location");

        binding.locationRemovalSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String locationName = binding.locationToBeRemoved.getText().toString();

                if(locationName.isEmpty()) {
                    missingInput(getActivity());
                }
                else {
                    removeLocation(locationName);
                }
            }
        });
    }

    public void removeLocation(String locationName){
        // Remove location from database
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        HashMap<String,Object> locations = new HashMap<>();

        // Generate id first

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        locations.put("location", locationName);

        db.collection("remLocation")
                .add(locations);

        FragmentManager fm = getActivity()
                .getSupportFragmentManager();
        fm.popBackStack("locationRemovalRequest", FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void missingInput(Context context){
        Toast.makeText(context, getString(R.string.missing),Toast.LENGTH_SHORT).show();
    }


}