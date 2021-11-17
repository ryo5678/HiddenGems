package com.example.hiddengems.search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hiddengems.R;

import com.example.hiddengems.dataModels.Locations.*;
import com.example.hiddengems.databinding.FragmentAddScreenBinding;
import com.example.hiddengems.databinding.FragmentLocationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class LocationFragment extends Fragment {

    FragmentLocationBinding binding;
    Location location;
    String id;
    List<Integer> ratings = new ArrayList<>();
    int rating = 0;

    public LocationFragment() {
        // Required empty public constructor
    }


    public static LocationFragment newInstance(String id) {
        LocationFragment fragment = new LocationFragment();
        Bundle args = new Bundle();
        args.putString("Location",id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getString("Location");
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

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("locations").document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        ArrayList<String> tempList = (ArrayList<String>) document.get("ratings");
                        for (int i = 0; i < ratings.size(); i++){
                            if (i == 0) {

                            }else if (i%2 == 0) {

                            } else {
                                ratings.add(Integer.parseInt(tempList.get(i)));
                            }

                        }
                        location = new Location(document.getString("Name"),document.getString("Address")
                                ,document.getString("Category"),document.getString("Description"),
                                document.getString("time"),rating,ratings.size(),
                                (ArrayList<String>)document.get("Tags"));


                        getActivity().setTitle(location.getName());

                        binding.locationViewName.setText(location.getName());
                        binding.locationViewAddress.setText(location.getAddress());
                        binding.locationViewCategory.setText(location.getCategory());
                    } else {
                    }
                } else {
                }
            }
        });

        // Code goes here
        // binding.(textview).setText(location.getName()) as an example
    }
}